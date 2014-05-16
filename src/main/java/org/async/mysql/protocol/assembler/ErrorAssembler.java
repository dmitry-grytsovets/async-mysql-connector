package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.Error;

public class ErrorAssembler implements PacketAssembler<Error> {

	public Error process(int step, ByteBuffer buffer, Error packet, Object message) {
		if (packet == null) {
			packet = new Error();
		}
		if (step == 1) {
			packet.setErrno((int) Utils.readLong(buffer.array(), 0, 2));
		} else if (step == 3) {
			packet.setSqlState(new String(buffer.array(),0,buffer.limit()));
		}
		else if (step == 4) {
			packet.setMessage(new String(buffer.array(),0,buffer.limit()));
		}
		return packet;
	}
}
