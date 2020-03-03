package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectorForOCSDB_T5 {
	static String driverClass="oracle.jdbc.driver.OracleDriver";
	static String url="jdbc:oracle:thin:@135.32.61.20:1522:ocsdb2";
	static String user="ocs";
	static String pwd="ocs_PP09";
	public static Connection getConnectionForOCSDB_T5() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("connect successfully");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}


}
