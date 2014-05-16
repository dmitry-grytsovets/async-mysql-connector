package org.async.mysql;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;



public class Utils {
	// TODO session calendar
	public static long readLong(byte[] ar, int offset,int length) {
		long rs = 0;
		for (int i = 0; i < length; i++) {
			rs += (long)(ar[offset+i] & 0xFF) << (i * 8);
		}
		return rs;
	}
	
	

	private static Calendar calendar = Calendar.getInstance();


	public static void writeLong(ByteBuffer out, long value, int length) {
		for (int i = 0; i < length; i++) {
			out.put((byte) ((value >> (i * 8)) & 0xFF));
		}
	}

	public static void filler(ByteBuffer out, int length) {
		for (int i = 0; i < length; i++) {
			out.put((byte) 0);
		}
	}

	public static void nullTerminated(ByteBuffer out, String str) {
		out.put(str.getBytes());
		out.put((byte) 0);

	}

	public static void lengthEncoded(ByteBuffer out, String str) {
		lengthEncodedString(out, str.getBytes());

	}

	public static void lengthEncodedString(ByteBuffer out, byte[] data) {
		if (data == null) {
			out.put((byte) 251);
		} else {
			if (data.length > 250) {
				if(data.length>0xFFFFFF) {
					out.put((byte)(254));
					Utils.writeLong(out,data.length, 4);
				} else if(data.length>0xFFFF) {
					out.put((byte)(253));
					Utils.writeLong(out,data.length, 3);
				} else  {
					out.put((byte)(252));
					Utils.writeLong(out,data.length, 2);
				}
				out.put(data);
			} else {
				out.put((byte) data.length);
				out.put(data);
			}

		}

	}

	public static void write(ByteBuffer out, int type, Object value) {
		switch (type) {
		case MysqlDefs.FIELD_TYPE_TINY:
		case MysqlDefs.FIELD_TYPE_SHORT:
		case MysqlDefs.FIELD_TYPE_LONG:
		case MysqlDefs.FIELD_TYPE_LONGLONG:
			Utils.writeLong(out, ((Number) value).longValue(), MysqlDefs
					.getSize(type));
			return;
		case MysqlDefs.FIELD_TYPE_FLOAT:
			Utils.writeLong(out, Float.floatToIntBits(((Number) value)
					.floatValue()), 4);
			return;
		case MysqlDefs.FIELD_TYPE_DOUBLE:
			Utils.writeLong(out, Double.doubleToLongBits(((Number) value)
					.doubleValue()), 8);
			return;
		case MysqlDefs.FIELD_TYPE_TIME:
			Date old = calendar.getTime();
			calendar.setTime((Date) value);
			try {
				writeLong(out, 8L, 6); // length
				out.put((byte) calendar.get(Calendar.HOUR_OF_DAY));
				out.put((byte) calendar.get(Calendar.MINUTE));
				out.put((byte) calendar.get(Calendar.SECOND));
			} finally {
				calendar.setTime(old);
			}
			return;
		case MysqlDefs.FIELD_TYPE_DATE:
			storeDate(out, (Date) value, type);
			return;
		case MysqlDefs.FIELD_TYPE_DATETIME:
		case MysqlDefs.FIELD_TYPE_TIMESTAMP:
			storeDate(out, (Date) value, type);
			return;
		case MysqlDefs.FIELD_TYPE_VAR_STRING:
		case MysqlDefs.FIELD_TYPE_STRING:
		case MysqlDefs.FIELD_TYPE_VARCHAR:
		case MysqlDefs.FIELD_TYPE_DECIMAL:
		case MysqlDefs.FIELD_TYPE_NEW_DECIMAL:
			lengthEncoded(out, (String) value);
			return;
		}
	}

	private static void storeDate(ByteBuffer out, Date date, int type) {
		Date old = calendar.getTime();
		calendar.setTime(date);
		try {
			int length = (type == MysqlDefs.FIELD_TYPE_DATE) ? 7 : 11;
			out.put((byte) length);

			writeLong(out, calendar.get(Calendar.YEAR), 2);
			out.put((byte) (calendar.get(Calendar.MONTH) + 1));
			out.put((byte) calendar.get(Calendar.DAY_OF_MONTH));
			if (type == MysqlDefs.FIELD_TYPE_DATE) {
				filler(out, 3);
			} else {
				out.put((byte) calendar.get(Calendar.HOUR_OF_DAY));
				out.put((byte) calendar.get(Calendar.MINUTE));
				out.put((byte) calendar.get(Calendar.SECOND));
				if (date instanceof Timestamp) {
					writeLong(out, ((Timestamp) date).getNanos(), 4);
				} else {
					filler(out, 4);
				}
			}
		} finally {
			calendar.setTime(old);
		}

	}
}
