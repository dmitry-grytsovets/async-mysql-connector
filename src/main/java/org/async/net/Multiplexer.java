package org.async.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class Multiplexer {
	protected Selector selector;

	public Multiplexer() throws IOException {
		super();
		this.selector = SelectorProvider.provider().openSelector();
	}

	public void select() throws IOException {
		if ((selector.select()) > 0) {
			process();
		}

	}

	public void select(int timeout) throws IOException {
		if ((selector.select(timeout)) > 0) {
			process();
		}

	}

	private void process() {
		Set<SelectionKey> keys = selector.selectedKeys();
		Iterator<SelectionKey> i = keys.iterator();
		while (i.hasNext()) {
			SelectionKey key = i.next();
			i.remove();
			if (!key.isValid()) {
				continue;
			}
			ChannelProcessor channelProcessor = (ChannelProcessor) key
					.attachment();
			if (key.isAcceptable()) {
				channelProcessor.accept(key);
			} else if (key.isReadable()) {
				channelProcessor.read(key);
			} else if (key.isWritable()) {
				channelProcessor.write(key);
			} else if (key.isConnectable()) {
				channelProcessor.connect(key);
			}

		}
	}

	public Selector getSelector() {
		return selector;
	}
}
