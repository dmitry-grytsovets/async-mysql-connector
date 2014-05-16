package org.async.mysql.jdbc;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.async.mysql.MysqlDefs;
import org.async.mysql.Utils;
import org.async.mysql.protocol.packets.Field;

public class BinaryResultSet extends AbstractResultSet<byte[][]> {
	private static final Logger logger = Logger.getLogger("org.async.mysql.jdbc.BinaryResultSet");
	private static Calendar calendar = Calendar.getInstance();

	public BinaryResultSet(long fieldCount) {
		super(fieldCount);
	}

	@Override
	protected Object[] unpack(byte[][] next) {
		unpackedRow = new Object[fields.length];
		for (int i = 0, e = fields.length; i < e; i++) {
			try {
				Field f = fields[i];
				byte[] data = next[i];
				if (data == null) {
					unpackedRow[i] = null;
				} else
					switch (f.getType()) {
					case MysqlDefs.FIELD_TYPE_TINY:
					case MysqlDefs.FIELD_TYPE_SHORT:
					case MysqlDefs.FIELD_TYPE_LONG:
					case MysqlDefs.FIELD_TYPE_INT24:
					case MysqlDefs.FIELD_TYPE_LONGLONG:
					case MysqlDefs.FIELD_TYPE_YEAR:
						unpackedRow[i] = Utils.readLong(data, 0, data.length);
						break;
					case MysqlDefs.FIELD_TYPE_FLOAT:
						unpackedRow[i] = Float.intBitsToFloat((int) Utils
								.readLong(data, 0, data.length));
						break;
					case MysqlDefs.FIELD_TYPE_DOUBLE:
						unpackedRow[i] = Double.longBitsToDouble(Utils
								.readLong(data, 0, data.length));
						break;

					case MysqlDefs.FIELD_TYPE_TIME:
					case MysqlDefs.FIELD_TYPE_DATE:
					case MysqlDefs.FIELD_TYPE_DATETIME:
					case MysqlDefs.FIELD_TYPE_TIMESTAMP:
						unpackedRow[i] = unpackDate(data, f.getType());
						break;
					case MysqlDefs.FIELD_TYPE_TINY_BLOB:
					case MysqlDefs.FIELD_TYPE_MEDIUM_BLOB:
					case MysqlDefs.FIELD_TYPE_LONG_BLOB:
					case MysqlDefs.FIELD_TYPE_BLOB:
					case MysqlDefs.FIELD_TYPE_VAR_STRING:
					case MysqlDefs.FIELD_TYPE_VARCHAR:
					case MysqlDefs.FIELD_TYPE_STRING:
						unpackedRow[i] = new String(data);
						break;
					// case MysqlDefs.FIELD_TYPE_DECIMAL:
					// case MysqlDefs.FIELD_TYPE_NEW_DECIMAL:
					}
			} catch (Exception ex) {
				if (logger.isLoggable(Level.SEVERE)) {
					logger.log(Level.SEVERE,ex.getMessage(),ex);
				}
			}
		}
		return unpackedRow;
	}

	private Date unpackDate(byte[] data, int type) {
		if (data.length == 8) {
			calendar.set(Calendar.YEAR, 0);
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			calendar.set(Calendar.HOUR_OF_DAY, data[5]);
			calendar.set(Calendar.MINUTE, data[6]);
			calendar.set(Calendar.SECOND, data[7]);
			return new Time(calendar.getTime().getTime());
		} else {
			calendar.set(Calendar.YEAR, ((data[1] & 0xFF) << 8)
					+ (data[0] & 0xFF));
			calendar.set(Calendar.MONTH, data[2] & 0xFF);
			calendar.set(Calendar.DAY_OF_MONTH, data[3] & 0xFF);
			calendar.set(Calendar.HOUR_OF_DAY, data[4] & 0xFF);
			calendar.set(Calendar.MINUTE, data[5] & 0xFF);
			calendar.set(Calendar.SECOND, data[6] & 0xFF);
			if (type == MysqlDefs.FIELD_TYPE_DATETIME
					|| type == MysqlDefs.FIELD_TYPE_TIMESTAMP) {
				if (data.length == 11) {
					Timestamp timestamp = new Timestamp(calendar.getTime()
							.getTime());
					timestamp.setNanos((int) Utils.readLong(data, 7, 4));
					return timestamp;
				} else {
					return new java.sql.Timestamp(calendar.getTime().getTime());
				}
			} else {
				return new java.sql.Date(calendar.getTime().getTime());
			}

		}
	}

}
