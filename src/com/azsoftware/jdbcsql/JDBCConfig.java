package com.azsoftware.jdbcsql;

import java.util.ResourceBundle;

public class JDBCConfig {

	private static ResourceBundle jdbcConfig = ResourceBundle.getBundle(JDBCConfig.class.getSimpleName());

	public static String getDriver(String dbms) {
		return jdbcConfig.getString(dbms+"_driver");
	}

	public static String getUrl(String dbms, String host, Integer port, String database) {
		String url = jdbcConfig.getString(dbms+"_url");
		url = url.replace("host", host);
		url = url.replace("dbname", database);
		if (port>0) {
			url = url.replace("port", port.toString());
		} else {
			url = url.replace(":port", "");
		}

		return url;
	}

}
