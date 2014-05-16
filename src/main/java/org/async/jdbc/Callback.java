package org.async.jdbc;

import java.sql.SQLException;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface Callback {
	/**
	 * @param SQLException
	 */
	void onError(SQLException e);
}
