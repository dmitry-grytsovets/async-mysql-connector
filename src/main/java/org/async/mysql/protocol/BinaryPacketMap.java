package org.async.mysql.protocol;

import org.async.mysql.MysqlDefs;
import org.async.mysql.jdbc.AbstractResultSet;
import org.async.mysql.protocol.packets.Field;

public class BinaryPacketMap<T extends Packet> extends PacketMap<T> {
	public BinaryPacketMap(int[] sizes, PacketAssembler<T> assembler) {
		super(sizes, assembler);
	}

	@Override
	public int getSize(int idx, Object message) {
		AbstractResultSet<?> rs = (AbstractResultSet<?>) message;
		if (idx == 0) {
			return 1;
		} else if (idx == 1) {
			return (int) ((rs.getFields().length + 9) / 8);
		} else {
			Field field = rs.getFields()[idx - 2];
			return MysqlDefs.getSize(field.getType());
			// field.getLength();
		}
		// return super.getSize(idx, message);
	}

}
