package org.async.jdbc;


public interface ConnectionPool {

	AsyncConnection getConnection();
}
