package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.Parameter;

public class ParameterAssembler implements PacketAssembler<Parameter>{

	public Parameter process(int step, ByteBuffer buffer, Parameter packet, Object message) {
		if(packet==null) {
			packet=new Parameter();
		}
		if(step==0) {
			packet.setType((int)Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==1) {
			packet.setFlags((int)Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==2) {
			packet.setDecimals((int) Utils.readLong(buffer.array(),0,buffer.limit()));
		} else if(step==3) {
			packet.setLength((int) Utils.readLong(buffer.array(),0,buffer.limit()));
		}
		return packet;
	}

}
