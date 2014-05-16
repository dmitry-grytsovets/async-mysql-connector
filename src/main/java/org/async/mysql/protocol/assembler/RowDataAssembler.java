package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.jdbc.AbstractResultSet;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.RowData;

public class RowDataAssembler implements PacketAssembler<RowData> {


	public RowData process(int step, ByteBuffer buffer, RowData packet,
			Object message) {
		if(packet==null) {
			AbstractResultSet<?> rs=(AbstractResultSet<?>) message;
			packet=new RowData(rs.getFields().length);
		}
		packet.getData()[step]=buffer==null?null:new String(buffer.array(),0,buffer.limit());
		return packet;
	}

}
