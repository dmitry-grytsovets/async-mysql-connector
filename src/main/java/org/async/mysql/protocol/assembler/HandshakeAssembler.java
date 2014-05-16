package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.Handshake;

public class HandshakeAssembler implements PacketAssembler<Handshake>{

	public Handshake process(int step, ByteBuffer buffer, Handshake packet, Object message) {
		if(packet==null) {
			packet=new Handshake();
		}
		if(step==0) {
			packet.setProtocolVersion((int) Utils.readLong(buffer.array(),0,1));
		} else if(step==1) {
			packet.setServerVersion(new String(buffer.array(),0,buffer.limit()));
		} else if(step==2) {
			packet.setThreadId((int) Utils.readLong(buffer.array(),0,4));
		} else if(step==3) {
			System.arraycopy(buffer.array(),0,packet.getScramble(),0,buffer.limit());
		} else if(step==5) {
			packet.setServerCapabilities((int) Utils.readLong(buffer.array(),0,2));
		} else if(step==6) {
			packet.setServerLanguage((byte) Utils.readLong(buffer.array(),0,1));
		} else if(step==7) {
			packet.setServerStatus((int) Utils.readLong(buffer.array(),0,2));
		} else if(step==9) {
			System.arraycopy(buffer.array(),0,packet.getScramble(),8,buffer.limit());
		}
		return packet;
	}

}
