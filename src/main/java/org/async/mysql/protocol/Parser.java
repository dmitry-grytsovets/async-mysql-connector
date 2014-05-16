package org.async.mysql.protocol;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.async.jdbc.Callback;
import org.async.mysql.Utils;
import org.async.utils.log.LogUtils;

public class Parser {
	private static final Logger logger = Logger.getLogger("org.async.mysql.protocol.Parser");
	private Protocol protocol = new Protocol41();
	private List<Integer> waitFor = new LinkedList<Integer>();
	private int dataIdx = 0;
	private ByteBuffer buffer = ByteBuffer.allocate(65536);
	private int packetSize = -1;
	private int itemSize = 0;
	private int skip = 0;
	// private int packetNum;// TODO check packetNum
	private Byte firstByte = null;
	private Packet packet = null;
	private Object message = null;
	private Callback callback;
	private boolean isNull = false;

	public Packet parse(ByteBuffer in) {
		while (in.remaining() > 0) {
			if (skip > 0) {
				// TODO do this in on step
				in.get();
				skip--;
			} else {
				if (packetSize > 0 && firstByte == null) {
					firstByte = in.array()[in.position()];
				}

				if (buffer.position() == 0) {
					itemSize = packetSize < 0 ? 4 : protocol.getPacketMap(
							waitFor.get(0), firstByte)
							.getSize(dataIdx, message);
					if (logger.isLoggable(Level.FINER)) {
						logger.finer("identifing packet : " + waitFor.get(0)
								+ " : " + (firstByte==null?null:(int)(firstByte&0xFF)));
						logger.finer("identifing data size dataIdx: " + dataIdx);
					}
					if (itemSize != 0 && itemSize != Integer.MAX_VALUE
							&& itemSize != Integer.MIN_VALUE) {
						if (logger.isLoggable(Level.FINER)) {
							logger.finer("Fixed size item found: " + itemSize);
						}
						buffer.limit(itemSize);
					}
				}
				if (itemSize == 0) {
					if (logger.isLoggable(Level.FINER)) {
						logger.finer("Reading Null Terminated String");
					}
					readNullTerminated(in);
				} else if (itemSize == Protocol.LENGTH_CODED_STRING
						&& buffer.limit() == buffer.capacity()) {
					if (logger.isLoggable(Level.FINER)) {
						logger.finer("Reading Length Coded String");
					}
					readLengthCodedString(in);
				} else if (itemSize == Protocol.LENGTH_CODED_BINARY
						&& buffer.limit() == buffer.capacity()) {
					if (logger.isLoggable(Level.FINER)) {
						logger.finer("Reading Length Coded Binary");
					}
					readLengthCodedBinary(in);
				} else {
					int length = buffer.remaining();
					if (length > in.remaining()) {
						length = in.remaining();
					}
					buffer.put(in.array(), in.position(), length);
					packetSize -= length;
					in.position(in.position() + length);

				}
				if (buffer.limit() == buffer.position()) {
					try {
						if (packetSize < 0) {
							packetSize = (int) Utils.readLong(buffer.array(),
									0, 3);
							if (logger.isLoggable(Level.FINER)) {
								logger.finer("Packet size:"
										+ packetSize);
							}
						} else {
							PacketMap<Packet> map = protocol.getPacketMap(
									waitFor.get(0), firstByte);
							packet = map.getAssembler().process(dataIdx,
									isNull ? null : buffer, packet, message);

							if (logger.isLoggable(Level.FINER)) {
								logger.finer("Assemble Packet "
										+ packet.getClass() + " dataIdx: "
										+ dataIdx + " Assembler: "
										+ map.getAssembler());
							}
							if (logger.isLoggable(Level.FINEST)) {
								logger.finest("Data:\n"
										+ LogUtils.dump(buffer, 16, 4, 16,2));
							}
							dataIdx++;
							isNull = false;
							if (dataIdx == map.size() || packetSize == 0) {
								if (packetSize != 0) {
									skip = packetSize;
								}
								if (logger.isLoggable(Level.FINER)) {
									logger.finer("Packet Completed. Skip: "
											+ skip + " bytes");
								}
								dataIdx = 0;
								firstByte = null;
								packetSize = -1;
								waitFor.remove(0);
								Packet rs = packet;
								packet.onSuccess(this);
								packet = null;
								return rs;
							} else {

							}

						}
					} finally {
						buffer.clear();
					}
				}

			}
		}
		return null;
	}

	private void readNullTerminated(ByteBuffer in) {
		for (int i = 0, e = in.remaining(); i < e; i++) {
			byte b = in.get();
			packetSize--;
			if (b != 0) {
				buffer.put(b);
			}
			if (b == 0 || packetSize == 0) {
				buffer.limit(buffer.position());
				break;
			}

		}
	}

	private void readLengthCodedBinary(ByteBuffer in) {
		packetSize--;
		buffer.put(in.get());
		byte[] ar = buffer.array();
		int f = ar[0] & 0xFF;
		buffer.clear();
		if (f == 251) {
			buffer.limit(0);
			isNull = true;
			if (logger.isLoggable(Level.FINER)) {
				logger.finer("Length Coded Binary is NULL");
			}

		} else if (f > 251) {
			if (f == 252) {
				buffer.limit(2);
				if (logger.isLoggable(Level.FINER)) {
					logger.finer("Length Coded Binary Length: 2 bytes");
				}
			} else if (f == 253) {
				buffer.limit(3);
				if (logger.isLoggable(Level.FINER)) {
					logger.finer("Length Coded Binary Length: 3 bytes");
				}
			} else if (f == 254) {
				buffer.limit(8);
				if (logger.isLoggable(Level.FINER)) {
					logger.finer("Length Coded Binary Length: 8 bytes");
				}
			}

		} else {
			if (logger.isLoggable(Level.FINER)) {
				logger.finer("Length Coded Binary Length: 1 byte");
			}
			buffer.limit(1);
			buffer.put((byte) f);

		}
	}

	private void readLengthCodedString(ByteBuffer in) {
		packetSize--;
		buffer.put(in.get());
		byte[] ar = buffer.array();
		int f = ar[0] & 0xFF;
		if (f == 251) {
			isNull = true;
			buffer.clear();
			buffer.limit(0);
		} else if (f > 251) {
			if (buffer.position() > f - 250) {
				int limit = (int) Utils.readLong(ar, 1, f - 250);
				buffer.clear();
				buffer.limit(limit);
				if (logger.isLoggable(Level.FINER)) {
					logger.finer("Length Coded String Length: " + limit
							+ " bytes");
				}
			}
		} else {
			buffer.clear();
			buffer.limit(f);
		}
	}

	public List<Integer> getWaitFor() {
		return waitFor;
	}

	public void setWaitFor(List<Integer> waitFor) {
		this.waitFor = waitFor;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

}
