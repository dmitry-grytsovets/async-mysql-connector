package org.async.mysql.protocol;

import java.nio.ByteBuffer;

public interface PacketAssembler<T> {
	T process(int step, ByteBuffer buffer,T packet, Object message);
}
