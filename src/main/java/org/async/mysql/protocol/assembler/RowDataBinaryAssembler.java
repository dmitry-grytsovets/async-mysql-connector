package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.jdbc.AbstractResultSet;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.RowDataBinary;

public class RowDataBinaryAssembler implements PacketAssembler<RowDataBinary> {

	public RowDataBinary process(int step, ByteBuffer buffer,
			RowDataBinary packet, Object message) {
		AbstractResultSet<?> rs=(AbstractResultSet<?>) message;
		if (packet == null) {
			packet = new RowDataBinary(rs.getFields().length);
		}
		if (step > 1) {
			int offset=getOffset(step, packet.getNullBitMap());
			byte[] ar = new byte[buffer.limit()];
			System.arraycopy(buffer.array(), 0, ar, 0, buffer.limit());
			packet.getData()[step-2+offset] = ar;
		} else if(step==1) {
			System.arraycopy(buffer.array(),0, packet.getNullBitMap(),0, packet.getNullBitMap().length);
		} else {
			//first byte is always 0??
		}
		return packet;
	}

	private int getOffset(int step, byte[] nullBitMap) {
		int bit = 4; // first two bits are reserved for future use
		int nullMaskPos = 0;
		int offset=0;
		for (int i = 0; i <= step-2; i++) {
			if((nullBitMap[nullMaskPos] & bit) > 0) {
				offset++;
			}
			if (((bit <<= 1) & 255) == 0) {
				bit = 1;
				nullMaskPos++;
			}
		}
		return offset;
	}

}
