package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.ResultSetHeader;

public class ResultSetHeaderAssembler implements PacketAssembler<ResultSetHeader> {
	private boolean binary;
	public ResultSetHeaderAssembler(boolean binary) {
		super();
		this.binary=binary;
	}

	public ResultSetHeader process(int step, ByteBuffer buffer,
			ResultSetHeader packet, Object message) {
		if(packet==null) {
			packet=new ResultSetHeader(binary);
		}
		if(step==0) {
			packet.setFieldCount(Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==1) {
			packet.setExtra(Utils.readLong(buffer.array(),0,buffer.limit()));
		}
		return packet;
	}

}
