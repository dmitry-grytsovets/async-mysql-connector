package org.async.mysql.protocol.packets;

import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;

public class Error implements Packet {
	private int errno;
	private String sqlState;
	private String message;

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

	public String getSqlState() {
		return sqlState;
	}

	public void setSqlState(String sqlState) {
		this.sqlState = sqlState;
	}

	public void onSuccess(Parser parser) {

	}
}
