package org.async.mysql.protocol.packets;

import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;

public class OK implements Packet {
	private long affectedRows;
	private long insertId;
	private int serverStatus;
	private String message;

	public long getAffectedRows() {
		return affectedRows;
	}

	public void setAffectedRows(long affectedRows) {
		this.affectedRows = affectedRows;
	}

	public long getInsertId() {
		return insertId;
	}

	public void setInsertId(long insertId) {
		this.insertId = insertId;
	}

	public int getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(int serverStatus) {
		this.serverStatus = serverStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public void onSuccess(Parser parser) {

	}
}
