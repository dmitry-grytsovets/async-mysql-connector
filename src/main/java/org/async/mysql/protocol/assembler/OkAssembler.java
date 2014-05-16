package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.OK;

public class OkAssembler implements PacketAssembler<OK>{

	public OK process(int step, ByteBuffer buffer, OK packet, Object message) {
		if(packet==null) {
			packet=new OK();
		}
		if(step==1) {
			packet.setAffectedRows(Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==2) {
			packet.setInsertId(Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==3) {
			packet.setServerStatus((int) Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==4) {
			packet.setMessage(new String(buffer.array()));
		}
		return packet;
	}

}
