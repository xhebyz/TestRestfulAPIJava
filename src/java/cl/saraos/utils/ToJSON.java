package cl.saraos.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.ResultSet;

import org.owasp.esapi.ESAPI;

public class ToJSON {
	/**
	 * 
	 * @param rs
	 *            ResultSet from DB to be parsed into a JSONArray and returned
	 *            to the client
	 * @return Simply returns a JSONArray representing the resultset passed in
	 * @throws Exception
	 *             An exception is thrown is any occur
	 */
	public JSONArray toJSONArray(ResultSet rs) throws Exception {

		JSONArray json = new JSONArray();
		String temp = null;

		try {
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			// loop through the ResultSet
			while (rs.next()) {
				// how many columns in DB
				int numColumns = rsmd.getColumnCount();
				// Converting each row in the ResultSet to a JSON Object
				JSONObject obj = new JSONObject();
				// iterating over all columns and inserting them into the JSON
				// Object
				for (int i = 1; i < numColumns + 1; i++) {
					String column_name = rsmd.getColumnName(i);
					if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
						obj.put(column_name, rs.getArray(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
						obj.put(column_name, rs.getInt(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
						obj.put(column_name, rs.getBoolean(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
						obj.put(column_name, rs.getBlob(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
						obj.put(column_name, rs.getDouble(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
						obj.put(column_name, rs.getFloat(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
						obj.put(column_name, rs.getInt(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
						obj.put(column_name, rs.getNString(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
						temp = rs.getString(column_name); // saving column data
															// to temp variable
						temp = ESAPI.encoder().canonicalize(temp); // decoding
																	// data to
																	// base
																	// state
						temp = ESAPI.encoder().encodeForHTML(temp); // encoding
																	// to be
																	// browser
																	// safe
						obj.put(column_name, temp); // putting data into JSON
													// object
					} else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
						obj.put(column_name, rs.getInt(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
						obj.put(column_name, rs.getInt(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
						obj.put(column_name, rs.getDate(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
						obj.put(column_name, rs.getTimestamp(column_name));
					} else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
						obj.put(column_name, rs.getBigDecimal(column_name));
					} else {
						obj.put(column_name, rs.getObject(column_name));
					}
				}// end for loop
				// here we've inserted all DB rows in our json object, to be
				// transfered over HTTP requests.
				json.put(obj);
			}// end while

		} catch (Exception e) {
			e.printStackTrace();
		}
		// The whole DB resultset in a different format, ready to be sent to
		// client via http requests.
		return json; // return JSON array
	}
}
