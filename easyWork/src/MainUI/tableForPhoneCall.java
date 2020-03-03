package MainUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class tableForPhoneCall extends JFrame {
	public String[][] data = new String[1000][7];
	private String[] title = {"主叫号码","账单号码","主叫区号","被叫区号","被叫号码","开始通话时间","时长"};
	public void loadData(ResultSet rs) throws SQLException {

		for(int i=0;rs.next();i++)
			{
				data[i][0] = rs.getString("CALLING_NBR");
				data[i][1] = rs.getString("BILLING_NBR");
				data[i][2] = rs.getString("CALLING_AREA_CODE");
				data[i][3] = rs.getString("CALLED_AREA_CODE");
				data[i][4] = rs.getString("CALLED_NBR");
				data[i][5] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("START_DATE")).toString();
				data[i][6] = String.valueOf(rs.getInt("DURATION"));
			}
	}
	public void showTable() {

		JTable jTable = new JTable(data,title);
		JScrollPane jsp = new JScrollPane(jTable);
		this.setTitle("通话详单表");
		this.setBounds(300, 300, 300, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(jsp,BorderLayout.CENTER);
	}

}
