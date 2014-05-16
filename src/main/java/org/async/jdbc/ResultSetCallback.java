package org.async.jdbc;

/**
 * @author Dmitry Grytsovets
 *
 */
public interface ResultSetCallback extends Callback{
	void onResultSet(ResultSet rs);
}
