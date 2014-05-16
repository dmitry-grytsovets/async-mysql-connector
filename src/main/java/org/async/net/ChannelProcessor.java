package org.async.net;

import java.nio.channels.SelectionKey;

public interface ChannelProcessor {
	void accept(SelectionKey key);

	void read(SelectionKey key);

	void write(SelectionKey key);

	void close(SelectionKey key);

	void connect(SelectionKey key);

	boolean isFree(SelectionKey key);

	boolean isService(SelectionKey key);

}
