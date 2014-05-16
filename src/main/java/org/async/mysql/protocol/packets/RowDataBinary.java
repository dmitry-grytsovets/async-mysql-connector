package org.async.mysql.protocol.packets;

import org.async.mysql.jdbc.BinaryResultSet;
import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;
import org.async.mysql.protocol.Protocol;

public class RowDataBinary implements Packet {
	private byte[] nullBitMap;
	private byte[][] data;

	public RowDataBinary(int size) {
		super();
		data = new byte[size][];
		nullBitMap = new byte[(size + 9) / 8];
	}

	public byte[] getNullBitMap() {
		return nullBitMap;
	}

	public void setNullBitMap(byte[] nullBitMap) {
		this.nullBitMap = nullBitMap;
	}

	public byte[][] getData() {
		return data;
	}

	public void setData(byte[][] data) {
		this.data = data;
	}

	public void onSuccess(Parser parser) {
		BinaryResultSet rs=(BinaryResultSet) parser.getMessage();
		rs.getData().add(data);
		parser.getWaitFor().add(0,Protocol.ROW_DATA_BINARY);
	}

}
