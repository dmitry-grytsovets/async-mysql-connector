package org.async.mysql.protocol.packets;

import org.async.jdbc.ResultSet;
import org.async.mysql.jdbc.AbstractResultSet;
import org.async.mysql.jdbc.PreparedStatementImpl;
import org.async.mysql.protocol.Packet;
import org.async.mysql.protocol.Parser;

public class Field implements Packet {
	private String catalog;
	private String db;
	private String table;
	private String orgTable;
	private String name;
	private String orgName;
	private int charsetNum;
	private int length;
	private int type;
	private int flags;
	private int decimals;
	private long def;

	public void onSuccess(Parser parser) {
		if (parser.getMessage() != null
				&& parser.getMessage() instanceof ResultSet) {
			AbstractResultSet<?> rs = (AbstractResultSet<?>) parser
					.getMessage();
			Field[] fields = rs.getFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i] == null) {
					fields[i] = this;
					return;
				}
			}
		} else if (parser.getMessage() != null
				&& parser.getMessage() instanceof PreparedStatementImpl) {
			PreparedStatementImpl pstmt = (PreparedStatementImpl) parser
					.getMessage();
			Field[] fields = pstmt.getState() == PreparedStatementImpl.FIELDS ? pstmt
					.getFields()
					: pstmt.getParams();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i] == null) {
					fields[i] = this;
					return;
				}
			}
		}

	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getOrgTable() {
		return orgTable;
	}

	public void setOrgTable(String orgTable) {
		this.orgTable = orgTable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getCharsetNum() {
		return charsetNum;
	}

	public void setCharsetNum(int charsetNum) {
		this.charsetNum = charsetNum;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public long getDef() {
		return def;
	}

	public void setDef(long def) {
		this.def = def;
	}

	@Override
	public String toString() {
		return "{name: " + name + ", table: " + table + ", db: " + db
				+ ", orgTable: " + orgTable + ", orgName: " + orgName
				+ ", type: " + type + ", catalog: " + catalog + ", length:"
				+ length+", charsetNum: "+charsetNum+", decimals: "+decimals+", def: "+def+", flags: "+flags+"}";
	}

}
