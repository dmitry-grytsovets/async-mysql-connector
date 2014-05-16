package org.async.jdbc;

import java.sql.SQLException;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface PreparedQuery {

	/**
	 * @param PreparedStatement
	 * @throws SQLException
	 */
	void query(PreparedStatement pstmt) throws SQLException;

}
