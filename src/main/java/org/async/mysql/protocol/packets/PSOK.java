package org.async.mysql.protocol.packets;

import org.async.mysql.jdbc.PreparedStatementImpl;
import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;
import org.async.mysql.protocol.Protocol;

public class PSOK implements Packet {
	private int statementId;
	private int columns;
	private int parameters;

	public int getStatementId() {
		return statementId;
	}

	public void setStatementId(int statementId) {
		this.statementId = statementId;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getParameters() {
		return parameters;
	}

	public void setParameters(int parameters) {
		this.parameters = parameters;
	}

	public void onSuccess(Parser parser) {
		if (parameters > 0) {
			for (int i = 0; i < parameters; i++) {
				parser.getWaitFor().add(Protocol.FIELD_PACKET);
			}
			parser.getWaitFor().add(Protocol.ROW_DATA_BINARY);
		}
		for (int i = 0; i < columns; i++) {
			parser.getWaitFor().add(Protocol.FIELD_PACKET);
		}
		PreparedStatementImpl pstmt = (PreparedStatementImpl) parser
				.getMessage();
		if (columns > 0) {
			pstmt.nextState();
			parser.getWaitFor().add(Protocol.ROW_DATA_BINARY);
		}
		pstmt.init(statementId, columns, parameters);
	}
}
