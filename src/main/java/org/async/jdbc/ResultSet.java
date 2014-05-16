package org.async.jdbc;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface ResultSet {
	Byte getByte(int idx);

	Short getShort(int idx);

	Integer getInteger(int idx);

	Long getLong(int idx);

	Float getFloat(int idx);

	Double getDouble(int idx);

	Time getTime(int idx);

	Date getDate(int idx);

	Timestamp getTimestamp(int idx);

	String getString(int idx);

	byte[] getBytes(int idx);

	boolean hasNext();

	void next();

	Byte getByte(String name);

	Short getShort(String name);

	Integer getInteger(String name);

	Long getLong(String name);

	Float getFloat(String name);

	Double getDouble(String name);

	Time getTime(String name);

	Date getDate(String name);

	Timestamp getTimestamp(String name);

	String getString(String name);

	byte[] getBytes(String name);

}
