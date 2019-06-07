package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleService {
	protected Connection conn;
	public OracleService () {
		try {
			String strConn = "jdbc:oracle:thin:@localhost:1521/orcl";
			conn = DriverManager.getConnection(strConn, "LEARN", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
