package org.async.mysql.protocol;


public abstract class Protocol {
	public static final int LENGTH_CODED_BINARY = Integer.MIN_VALUE;
	public static final int LENGTH_CODED_STRING = Integer.MAX_VALUE;
	public static final int HAND_SHAKE = 1;
	public static final int ROW_DATA = 2;
	public static final int FIELD_PACKET = 3;
	public static final int RESULT_SET_PACKET = 4;
	public static final int PSOK_PACKET = 5;
	public static final int PS_PARAM_PACKET = 6;
	public static final int ROW_DATA_BINARY = 7;
	public static final int RESULT_SET_PACKET_BINARY = 8;
	public static final int SUCCESS_PACKET = 9;
	public static final int REPEAT = -1;
	public static final int DYNAMIC = Integer.MIN_VALUE;

	public abstract PacketMap<Packet> getPacketMap(int type, Byte firstByte);

}
