package org.async.jdbc;

import java.sql.SQLException;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface AsyncConnection {
	/**
	 * Creates a <code>Statement</code> object for sending SQL statements to
	 * the database.
	 *
	 * @return a new default <code>Statement</code> object
	 * @throws SQLException
	 */
	Statement createStatement() throws SQLException;

	/**
	 * Creates a <code>PreparedStatement</code> object for sending
	 * parameterized SQL statements to the database.
	 *
	 * @param sql
	 *            an SQL statement that may contain one or more '?' IN parameter
	 *            placeholders
	 * @return a new default <code>PreparedStatement</code> object containing
	 *         the pre-compiled SQL statement
	 * @throws SQLException
	 */
	PreparedStatement prepareStatement(String sql) throws SQLException;

	/**
	 * Releases this <code>Connection</code> object's database and resources
	 * immediately instead of waiting for them to be automatically released.
	 *
	 * @throws SQLException
	 */

	void close() throws SQLException;

	/**
	 * @return how many queries are waiting
	 */
	int load();

	/**
	 * @return true if connection is closed
	 */
	boolean isClosed();
}
