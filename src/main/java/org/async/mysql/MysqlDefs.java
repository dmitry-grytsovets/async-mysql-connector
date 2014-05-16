package org.async.mysql;

import org.async.mysql.protocol.Protocol;

public class MysqlDefs {
	public static int CLIENT_LONG_PASSWORD = 1;
	public static int CLIENT_FOUND_ROWS = 2;
	public static int CLIENT_LONG_FLAG = 4;
	public static int CLIENT_CONNECT_WITH_DB = 8;
	public static int CLIENT_NO_SCHEMA = 16;
	public static int CLIENT_COMPRESS = 32;
	public static int CLIENT_ODBC = 64;
	public static int CLIENT_LOCAL_FILES = 128;
	public static int CLIENT_IGNORE_SPACE = 256;
	public static int CLIENT_PROTOCOL_41 = 512;
	public static int CLIENT_INTERACTIVE = 1024;
	public static int CLIENT_SSL = 2048;
	public static int CLIENT_IGNORE_SIGPIPE = 4096;
	public static int CLIENT_TRANSACTIONS = 8192;
	public static int CLIENT_RESERVED = 16384;
	public static int CLIENT_SECURE_CONNECTION = 32768;
	public static int CLIENT_MULTI_STATEMENTS = 65536;
	public static int CLIENT_MULTI_RESULTS = 131072;

	public static int COM_SLEEP = 0x00; // (none, this is an internal thread
										// state)
	public static int COM_QUIT = 0x01; // mysql_close
	public static int COM_INIT_DB = 0x02; // mysql_select_db
	public static int COM_QUERY = 0x03; // mysql_real_query
	public static int COM_FIELD_LIST = 0x04; // mysql_list_fields
	public static int COM_CREATE_DB = 0x05; // mysql_create_db (deprecated)
	public static int COM_DROP_DB = 0x06; // mysql_drop_db (deprecated)
	public static int COM_REFRESH = 0x07; // mysql_refresh
	public static int COM_SHUTDOWN = 0x08; // mysql_shutdown
	public static int COM_STATISTICS = 0x09; // mysql_stat
	public static int COM_PROCESS_INFO = 0x0a; // mysql_list_processes
	public static int COM_CONNECT = 0x0b; // (none, this is an internal thread
											// state)
	public static int COM_PROCESS_KILL = 0x0c; // mysql_kill
	public static int COM_DEBUG = 0x0d; // mysql_dump_debug_info
	public static int COM_PING = 0x0e; // mysql_ping
	public static int COM_TIME = 0x0f; // (none, this is an internal thread
										// state)
	public static int COM_DELAYED_INSERT = 0x10; // (none, this is an
													// internal thread state)
	public static int COM_CHANGE_USER = 0x11; // mysql_change_user
	public static int COM_BINLOG_DUMP = 0x12; // (used by slave server /
												// mysqlbinlog)
	public static int COM_TABLE_DUMP = 0x13; // (used by slave server to get
												// master table)
	public static int COM_CONNECT_OUT = 0x14; // (used by slave to log
												// connection to master)
	public static int COM_REGISTER_SLAVE = 0x15; // (used by slave to
													// register to master)
	public static int COM_STMT_PREPARE = 0x16; // mysql_stmt_prepare
	public static int COM_STMT_EXECUTE = 0x17; // mysql_stmt_execute
	public static int COM_STMT_SEND_LONG_DATA = 0x18; // mysql_stmt_send_long_data
	public static int COM_STMT_CLOSE = 0x19; // mysql_stmt_close
	public static int COM_STMT_RESET = 0x1a; // mysql_stmt_reset
	public static int COM_SET_OPTION = 0x1b; // mysql_set_server_option
	public static int COM_STMT_FETCH = 0x1c; // mysql_stmt_fetch




	public static final int FIELD_TYPE_DATE = 10;

	public static final int FIELD_TYPE_DATETIME = 12;

	// Data Types
	public static final int FIELD_TYPE_DECIMAL = 0;

	public static final int FIELD_TYPE_DOUBLE = 5;

	public static final int FIELD_TYPE_ENUM = 247;

	public static final int FIELD_TYPE_FLOAT = 4;

	public static final int FIELD_TYPE_GEOMETRY = 255;

	public static final int FIELD_TYPE_INT24 = 9;

	public static final int FIELD_TYPE_LONG = 3;

	public static final int FIELD_TYPE_LONG_BLOB = 251;

	public static final int FIELD_TYPE_LONGLONG = 8;

	public static final int FIELD_TYPE_MEDIUM_BLOB = 250;

	public static final int FIELD_TYPE_NEW_DECIMAL = 246;

	public static final int FIELD_TYPE_NEWDATE = 14;

	public static final int FIELD_TYPE_NULL = 6;

	public static final int FIELD_TYPE_SET = 248;

	public static final int FIELD_TYPE_SHORT = 2;

	public static final int FIELD_TYPE_STRING = 254;

	public static final int FIELD_TYPE_TIME = 11;

	public static final int FIELD_TYPE_TIMESTAMP = 7;

	public static final int FIELD_TYPE_TINY = 1;

	// Older data types
	public static final int FIELD_TYPE_TINY_BLOB = 249;

	public static final int FIELD_TYPE_VAR_STRING = 253;

	public static final int FIELD_TYPE_BLOB = 252;

	public static final int FIELD_TYPE_VARCHAR = 15;

	// Newer data types
	public static final int FIELD_TYPE_YEAR = 13;


	public static final long LENGTH_BLOB = 65535;

	public static final long LENGTH_LONGBLOB = 4294967295L;

	public static final long LENGTH_MEDIUMBLOB = 16777215;

	public static final long LENGTH_TINYBLOB = 255;

	public static int getSize(int type) {

		switch (type) {
		case MysqlDefs.FIELD_TYPE_TINY:
			return 1;
		case MysqlDefs.FIELD_TYPE_SHORT:
			return 2;
		case MysqlDefs.FIELD_TYPE_LONG:
			return 4;
		case MysqlDefs.FIELD_TYPE_LONGLONG:
			return 8;
		case MysqlDefs.FIELD_TYPE_FLOAT:
			return 4;
		case MysqlDefs.FIELD_TYPE_DOUBLE:
			return 8;
		case MysqlDefs.FIELD_TYPE_TIME:
		case MysqlDefs.FIELD_TYPE_DATE:
		case MysqlDefs.FIELD_TYPE_DATETIME:
		case MysqlDefs.FIELD_TYPE_TIMESTAMP:
		case MysqlDefs.FIELD_TYPE_VAR_STRING:
		case MysqlDefs.FIELD_TYPE_STRING:
		case MysqlDefs.FIELD_TYPE_VARCHAR:
		case MysqlDefs.FIELD_TYPE_DECIMAL:
		case MysqlDefs.FIELD_TYPE_NEW_DECIMAL:
		case MysqlDefs.FIELD_TYPE_BLOB:
			return Protocol.LENGTH_CODED_STRING;
		}
		throw new IllegalArgumentException("type="+type+" is not supported");
	}

}
