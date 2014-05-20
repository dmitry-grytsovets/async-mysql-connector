package org.async.mysql.protocol.packets;

import org.async.mysql.MysqlDefs;
import org.async.mysql.jdbc.AbstractResultSet;
import org.async.mysql.protocol.HasState;
import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;
import org.async.mysql.protocol.Protocol;

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
		
		if ((statusFlags & MysqlDefs.SERVER_MORE_RESULTS_EXISTS) > 0
				&& st.isOver() && (st instanceof AbstractResultSet<?>)) {
			parser.getWaitFor().add(0, Protocol.SUCCESS_PACKET);
		}
	}

}
