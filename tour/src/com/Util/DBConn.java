package com.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	private static Connection conn = null;
	
	private DBConn() {
	}
	
	public static Connection getConnection() {
		String url ="jdbc:oracle:thin:@211.238.142.194:1521:XE";
		String user="semi";
		String pwd ="java$!";
		
		if(conn==null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, pwd);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return conn;
	}
	
	
	public static void close() {
		if(conn==null)
			return;
		try {
			if(!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		conn=null;
	}
}
