package org.async.mysql.protocol;

import org.async.mysql.protocol.assembler.EOFAssembler;
import org.async.mysql.protocol.assembler.ErrorAssembler;
import org.async.mysql.protocol.assembler.FieldAssembler;
import org.async.mysql.protocol.assembler.HandshakeAssembler;
import org.async.mysql.protocol.assembler.OkAssembler;
import org.async.mysql.protocol.assembler.PSOkAssembler;
import org.async.mysql.protocol.assembler.ParameterAssembler;
import org.async.mysql.protocol.assembler.ResultSetHeaderAssembler;
import org.async.mysql.protocol.assembler.RowDataAssembler;
import org.async.mysql.protocol.assembler.RowDataBinaryAssembler;
import org.async.mysql.protocol.packets.EOF;
import org.async.mysql.protocol.packets.Error;
import org.async.mysql.protocol.packets.Field;
import org.async.mysql.protocol.packets.Handshake;
import org.async.mysql.protocol.packets.OK;
import org.async.mysql.protocol.packets.PSOK;
import org.async.mysql.protocol.packets.Parameter;
import org.async.mysql.protocol.packets.ResultSetHeader;
import org.async.mysql.protocol.packets.RowData;
import org.async.mysql.protocol.packets.RowDataBinary;

public class Protocol41 extends Protocol {

	public static final PacketMap<Handshake> HAND_SHAKE_PACKET = new PacketMap<Handshake>(
			new int[] { 1, 0, 4, 8, 1, 2, 1, 2, 13, 13 },
			new HandshakeAssembler());

	public static final PacketMap<OK> OK_PACKET = new PacketMap<OK>(new int[] {
			1, LENGTH_CODED_BINARY, LENGTH_CODED_BINARY, 2, 2, 0 },
			new OkAssembler());
	public static final PacketMap<Error> ERROR_PACKET = new PacketMap<Error>(
			new int[] { 1, 2, 1, 5, 0 }, new ErrorAssembler());

	public static final PacketMap<ResultSetHeader> RESULT_SET_HEADER_PACKET = new PacketMap<ResultSetHeader>(
			new int[] { LENGTH_CODED_BINARY, LENGTH_CODED_BINARY },
			new ResultSetHeaderAssembler(false));

	public static final PacketMap<ResultSetHeader> RESULT_SET_HEADER_PACKET_BIN = new PacketMap<ResultSetHeader>(
			new int[] { LENGTH_CODED_BINARY, LENGTH_CODED_BINARY },
			new ResultSetHeaderAssembler(true));

	public static final PacketMap<EOF> EOF_PACKET = new PacketMap<EOF>(
			new int[] { 1, 2, 2 }, new EOFAssembler());

	public static final PacketMap<Field> FIELD_DATA_PACKET = new PacketMap<Field>(
			new int[] { Protocol.LENGTH_CODED_STRING,
					Protocol.LENGTH_CODED_STRING, Protocol.LENGTH_CODED_STRING,
					Protocol.LENGTH_CODED_STRING, Protocol.LENGTH_CODED_STRING,
					Protocol.LENGTH_CODED_STRING, 1, 2, 4, 1, 2, 1, 2,
					Protocol.LENGTH_CODED_BINARY }, new FieldAssembler());

	public static final PacketMap<RowData> ROW_PACKET = new PacketMap<RowData>(
			new int[] { Protocol.LENGTH_CODED_STRING, Protocol.REPEAT },
			new RowDataAssembler());

	public static final BinaryPacketMap<RowDataBinary> ROW_BIN_PACKET = new BinaryPacketMap<RowDataBinary>(
			new int[] {},
			new RowDataBinaryAssembler());

	public static final PacketMap<PSOK> PS_OK_PACKET = new PacketMap<PSOK>(
			new int[] { Protocol.LENGTH_CODED_STRING, 4, 2, 2 }, new PSOkAssembler());

	public static final PacketMap<Parameter> PARAM_PACKET = new PacketMap<Parameter>(
			new int[] { 3,2, 2, 1, 4 }, new ParameterAssembler());

	@SuppressWarnings("unchecked")
	@Override
	public PacketMap<Packet> getPacketMap(int type, Byte firstByte) {

		if (type == ROW_DATA_BINARY) {
			return getDataPacket(firstByte, (PacketMap) ROW_BIN_PACKET);
		} else if (type == ROW_DATA) {
			return getDataPacket(firstByte, (PacketMap) ROW_PACKET);
		} else if (type == FIELD_PACKET) {
			return (PacketMap) FIELD_DATA_PACKET;
		} else if (type == RESULT_SET_PACKET) {
			return getDataPacket(firstByte,
					(PacketMap) RESULT_SET_HEADER_PACKET);
		} else if (type == RESULT_SET_PACKET_BINARY) {
			return getDataPacket(firstByte,
					(PacketMap) RESULT_SET_HEADER_PACKET_BIN);
		} else if(type==SUCCESS_PACKET) {
				return  getDataPacket(firstByte,(PacketMap) OK_PACKET);
		} else if (type == PSOK_PACKET) {
			return getDataPacket(firstByte,(PacketMap) PS_OK_PACKET);
		} else if (type == PS_PARAM_PACKET) {
			return (PacketMap) PARAM_PACKET;
		} else if (type == HAND_SHAKE) {
			return (PacketMap) HAND_SHAKE_PACKET;
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	private PacketMap<Packet> getDataPacket(Byte firstByte,
			PacketMap<Packet> def) {
		int c = firstByte & 0xFF;
		if (c == 255)
			return (PacketMap) ERROR_PACKET;
		else if (c == 254)
			return (PacketMap) EOF_PACKET;
		return def;
	}

}
