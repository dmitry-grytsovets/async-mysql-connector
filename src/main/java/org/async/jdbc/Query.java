package org.async.jdbc;

import java.sql.SQLException;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface Query {
	/**
	 * @param Connection
	 * @throws SQLException
	 */
	void query(Connection connection) throws SQLException;
}
