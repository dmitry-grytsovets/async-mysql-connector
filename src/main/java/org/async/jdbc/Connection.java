package org.async.jdbc;

import java.sql.SQLException;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface Connection {
	/**
	 * @param SQL
	 * @throws SQLException
	 */
	void query(String query) throws SQLException;

	/**
	 * @param SQL
	 * @throws SQLException
	 */
	void update(String query) throws SQLException;


}
