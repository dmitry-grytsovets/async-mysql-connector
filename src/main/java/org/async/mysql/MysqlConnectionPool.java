package org.async.mysql;

import java.io.IOException;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.async.jdbc.AsyncConnection;
import org.async.jdbc.ConnectionPool;
import org.async.jdbc.SuccessCallback;
import org.async.mysql.protocol.packets.OK;
import org.async.net.HasSelector;

public class MysqlConnectionPool implements ConnectionPool  {
	private static Logger logger=Logger.getLogger("org.async.web.db.mysql.MysqlConnectionPool");
	private List<AsyncConnection> connections;
	private String user;
	private String password;
	private String database;
	private String host;
	private int port;
	private Selector selector;
	private SuccessCallback callback;
	private int index;
	private int size;

	public MysqlConnectionPool() {

	}

	@Override
	public AsyncConnection getConnection() {
		if (connections != null) {
			AsyncConnection connection = connections.get(index);
			index = ++index % connections.size();
			return connection;
		}
		return null;
	}

	public void init() {
		if (callback == null) {
			callback = new SuccessCallback() {

				@Override
				public void onSuccess(OK ok) {
					if(logger.isLoggable(Level.INFO)) {
						logger.info("Succefully Connected");
					}
				}

				@Override
				public void onError(SQLException e) {
					if(logger.isLoggable(Level.SEVERE)) {
						logger.log(Level.SEVERE,e.getMessage(),e);
					}
				}

			};
		}
		connections = new ArrayList<AsyncConnection>(size);
		for (int i = 0; i < size; i++) {
			try {
				MysqlConnection connection = new MysqlConnection(host, port, user, password,
						database, selector, callback);
				connections.add(connection);
			} catch (IOException e) {

				throw new RuntimeException(e);
			}
		}
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setServer(HasSelector server) {
		this.selector = server.getSelector();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SuccessCallback getCallback() {
		return callback;
	}

	public void setCallback(SuccessCallback callback) {
		this.callback = callback;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
