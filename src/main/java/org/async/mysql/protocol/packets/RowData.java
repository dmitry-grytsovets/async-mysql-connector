package org.async.mysql.protocol.packets;

import java.util.Arrays;

import org.async.mysql.jdbc.StringResultSet;
import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;
import org.async.mysql.protocol.Protocol;

public class RowData implements Packet {
	private String[] data;

	public RowData(int size) {
		super();
		data = new String[size];
	}

	public void onSuccess(Parser parser) {
		StringResultSet rs=(StringResultSet) parser.getMessage();
		rs.getData().add(data);
		parser.getWaitFor().add(0,Protocol.ROW_DATA);
	}

	public Object[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}
