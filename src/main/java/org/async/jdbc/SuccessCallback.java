package org.async.jdbc;

import org.async.mysql.protocol.packets.OK;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface SuccessCallback extends Callback {
	void onSuccess(OK ok);
}
