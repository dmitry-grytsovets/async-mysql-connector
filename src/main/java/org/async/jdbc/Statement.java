package org.async.jdbc;

import java.sql.SQLException;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface Statement {
	void executeUpdate(String sql, SuccessCallback callback) throws SQLException;

	void executeQuery(String sql, ResultSetCallback callback) throws SQLException;
	
	void executeCall(String sql, ResultSetCallback rsCallback, SuccessCallback sucCallback) throws SQLException;
}
