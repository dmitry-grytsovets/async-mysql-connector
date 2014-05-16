package org.async.jdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface PreparedStatement {
	/**
	 * @param PreparedQuery
	 * @param ResultSetCallback
	 * @throws SQLException
	 */
	void executeQuery(PreparedQuery query, ResultSetCallback callback)
			throws SQLException;

	/**
	 * @param PreparedQuery
	 * @param SuccessCallback
	 * @throws SQLException
	 */
	void executeUpdate(PreparedQuery query, SuccessCallback callback)
			throws SQLException;

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setByte(int idx, Byte b);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setShort(int idx, Short s);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setInteger(int idx, Integer i);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setLong(int idx, Long l);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setFloat(int idx, Float f);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setDouble(int idx, Double d);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setTime(int idx, Time time);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setDate(int idx, Date date);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setTimestamp(int idx, Timestamp timestamp);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setString(int idx, String string);

	/**
	 * @param parameterIndex
	 * @param paremeterValue
	 */
	void setBytes(int idx, byte[] bytes);

	/**
	 * @throws SQLException
	 */
	void close() throws SQLException;

	/**
	 * @throws SQLException
	 */
	void reset() throws SQLException;

}
