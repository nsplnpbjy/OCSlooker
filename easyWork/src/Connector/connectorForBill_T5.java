package Connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectorForBill_T5 {
	static String driverClass="oracle.jdbc.driver.OracleDriver";
	static String url="jdbc:oracle:thin:@135.32.61.20:1522:bill1";
	static String user="bss_bill";
	static String pwd="9W22Q66h";
	public static Connection getConnectionForBill_T5() {
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
