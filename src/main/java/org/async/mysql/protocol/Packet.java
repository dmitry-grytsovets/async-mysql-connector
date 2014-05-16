package org.async.mysql.protocol;

public interface Packet {
	void onSuccess(Parser parser);
}
