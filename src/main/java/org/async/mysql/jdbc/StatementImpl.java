package org.async.mysql.jdbc;

import java.sql.SQLException;

import org.async.jdbc.Connection;
import org.async.jdbc.Query;
import org.async.jdbc.ResultSetCallback;
import org.async.jdbc.Statement;
import org.async.jdbc.SuccessCallback;

public class StatementImpl implements Statement {
	private InnerConnection connection;

	public StatementImpl(InnerConnection connection) {
		super();
		this.connection = connection;
	}

	public void executeQuery(final String sql, ResultSetCallback callback)
			throws SQLException {
		connection.query(new Query() {

			public void query(Connection connection) throws SQLException {
				connection.query(sql);

			}

		}, callback);

	}

	public void executeUpdate(final String sql, SuccessCallback callback)
			throws SQLException {
		connection.query(new Query() {

			public void query(Connection connection) throws SQLException {
				connection.update(sql);

			}

		}, callback);

	}

}
