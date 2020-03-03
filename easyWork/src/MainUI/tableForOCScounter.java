package MainUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class tableForOCScounter extends JFrame {
	public String[][] data = new String[6][2];
	private String[] title = {"返回代码","返回次数"};
	public void loadData(ResultSet rs) throws SQLException {

		for(int i=0;rs.next();i++)
			{
				data[i][0] = String.valueOf(rs.getInt("RESULT_CODE"));
				data[i][1] = String.valueOf(rs.getInt("CNT"));
			}
	}
	public void showTable() {

		JTable jTable = new JTable(data,title);
		JScrollPane jsp = new JScrollPane(jTable);
		this.setTitle("OSC返回代码情况表");
		this.setBounds(300, 300, 300, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(jsp,BorderLayout.CENTER);
	}

}
