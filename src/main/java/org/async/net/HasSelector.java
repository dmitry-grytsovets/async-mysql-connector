package org.async.net;

import java.nio.channels.Selector;

public interface HasSelector {
	Selector getSelector();
}
