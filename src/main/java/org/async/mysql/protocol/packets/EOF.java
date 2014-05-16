package org.async.mysql.protocol.packets;

import org.async.mysql.protocol.HasState;
import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;

public class EOF implements Packet {
	private int warningCount;
	private int statusFlags;

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public int getStatusFlags() {
		return statusFlags;
	}

	public void setStatusFlags(int statusFlags) {
		this.statusFlags = statusFlags;
	}

	public void onSuccess(Parser parser) {
		HasState st = (HasState) parser.getMessage();
		st.nextState();
	}

}
