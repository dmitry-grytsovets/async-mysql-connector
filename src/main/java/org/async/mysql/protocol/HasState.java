package org.async.mysql.protocol;

public interface HasState {
	void nextState();

	boolean isOver();
}
