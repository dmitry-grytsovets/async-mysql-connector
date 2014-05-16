package org.async.mysql.jdbc;


import java.sql.SQLException;

import org.async.jdbc.Callback;
import org.async.jdbc.Connection;
import org.async.jdbc.Query;
import org.async.mysql.SilentQuery;

public interface InnerConnection extends Connection {
	void query(Query query, Callback callback) throws SQLException;

	void query(SilentQuery query) throws SQLException;

	void executeQuery(int statementId, int[] types, Object[] data)
			throws SQLException;

	void executeUpdate(int statementId, int[] types, Object[] data)
			throws SQLException;

	void prepare(String sql) throws SQLException;

	void close(int statementId) throws SQLException;

	void reset(int statementId) throws SQLException;

	long getConnected();
}
