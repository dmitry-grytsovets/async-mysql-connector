package org.async.mysql.protocol.assembler;

import java.nio.ByteBuffer;

import org.async.mysql.Utils;
import org.async.mysql.protocol.PacketAssembler;
import org.async.mysql.protocol.packets.PSOK;

public class PSOkAssembler implements PacketAssembler<PSOK> {

	public PSOK process(int step, ByteBuffer buffer, PSOK packet, Object message) {
		if (packet == null) {
			packet = new PSOK();
		}
		if (step == 1) {
			packet.setStatementId((int) Utils.readLong(buffer.array(), 0,
					buffer.limit()));
		} else if (step == 2) {
			packet.setColumns((int) Utils.readLong(buffer.array(), 0, buffer
					.limit()));
		} else if (step == 3) {
			packet.setParameters((int) Utils.readLong(buffer.array(), 0, buffer
					.limit()));
		}
		return packet;
	}

}
