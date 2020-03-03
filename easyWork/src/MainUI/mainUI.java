//中国电信云南分公司
//省公司本部 企业信息化部
//白金晔开发
//OCS简易查询系统
//版本Ver.1.0.2
package MainUI;

import java.awt.EventQueue;
import Connector.connectorForBill_T5;
import Connector.connectorForOCSDB_T5;
import JFreeChartClasses.forDataSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import com.sun.net.httpserver.Authenticator.Result;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Toolkit;

public class mainUI {

	private JFrame frmOcs;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frmOcs.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOcs = new JFrame();
		frmOcs.setResizable(false);
		frmOcs.setIconImage(Toolkit.getDefaultToolkit().getImage(mainUI.class.getResource("/junit/swingui/icons/ok.gif")));
		frmOcs.getContentPane().setBackground(Color.CYAN);
		frmOcs.getContentPane().setForeground(Color.BLACK);
		
		textField = new JTextField();
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.WHITE);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.setText("");
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("\u8BF7\u8F93\u5165\u8981\u67E5\u8BE2\u7684\u7535\u8BDD\u53F7\u7801");
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.BLACK);
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField_1.setText("");
			}
		});
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("\u8BF7\u8F93\u5165\u533A\u53F7");
		textField_1.setBackground(Color.WHITE);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("OCS\u5168\u7701\u8BDD\u52A1\u91CF\u68C0\u67E5");
		btnNewButton.addActionListener(new ActionListener() {//从这里开始监听第一个按钮
			public void actionPerformed(ActionEvent e) {
				Connection conn = connectorForBill_T5.getConnectionForBill_T5();
				ResultSet rs = null;
				Statement ps = null;
				try {//调用ORACLE的API实现查询移动语音用户OCS统计情况
					ps = conn.createStatement();
					rs = ps.executeQuery("select start_date,sum(cnt),sum(dur) from bill.t_account_billcdr_call where  switch_id in (201802,9262) and product_id  in (779,833,6600024000) and  start_date>=trunc(last_day(add_months(sysdate,-3) )+1) and  to_char(start_date,'yyyymmdd')<to_char(sysdate,'yyyymmdd')\r\n" + 
							"group by start_date\r\n" + 
							"order by start_date");//获得结果集
					forDataSet fds = new forDataSet();
					for(int i=0;i<49;i++)
						rs.next();
					while(rs.next()) {
						fds.GetDataset(rs.getInt("SUM(CNT)"), "SUM(CNT)", rs.getDate("START_DATE").toString().substring(5));
						fds.GetDataset(rs.getInt("SUM(DUR)"), "SUM(DUR)", rs.getDate("START_DATE").toString().substring(5));
					}
					fds.drawPic("移动语音用户OCS统计", "日期", "数量/费用");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//以下是返回移动数据用户OCS统计
				try {
					ps = conn.createStatement();
					rs = ps.executeQuery("select start_date,sum(cnt)/100,sum(amount_gb)  from bill.t_account_billcdr_data where switch_id in (201803,201748) and product_id   in (779,833,6600024000) and  start_date>=trunc(last_day(add_months(sysdate,-3) )+1)  and  to_char(start_date,'yyyymmdd')<to_char(sysdate,'yyyymmdd')\r\n" + 
							"group by start_date\r\n" + 
							"order by start_date");//获得结果集
					forDataSet fds = new forDataSet();
					for(int i=0;i<49;i++)
						rs.next();
					while(rs.next()) {
						fds.GetDataset(rs.getInt("SUM(CNT)/100"), "SUM(CNT)/100", rs.getDate("START_DATE").toString().substring(5));
						fds.GetDataset(rs.getInt("SUM(AMOUNT_GB)"), "SUM(AMOUNT_GB)", rs.getDate("START_DATE").toString().substring(5));
					}
					fds.drawPic("移动数据用户OCS统计", "日期", "数据流量(GB)/费用");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//以下是返回移动短信用户OCS统计
				try {
					ps = conn.createStatement();
					rs = ps.executeQuery("select start_date, sum(cnt),sum(charge)/100 from bill.t_account_billcdr_value where switch_id in (201749,201804) and product_id   in (779,833,6600024000) and  start_date>=trunc(last_day(add_months(sysdate,-3) )+1) and  to_char(start_date,'yyyymmdd')<to_char(sysdate,'yyyymmdd')\r\n" + 
							"group by start_date\r\n" + 
							"order by start_date");//获得结果集
					forDataSet fds = new forDataSet();
					for(int i=0;i<49;i++)
						rs.next();
					while(rs.next()) {
						fds.GetDataset(rs.getInt("SUM(CNT)"), "SUM(CNT)", rs.getDate("START_DATE").toString().substring(5));
						fds.GetDataset(rs.getInt("SUM(CHARGE)/100"), "SUM(CHARGE)/100", rs.getDate("START_DATE").toString().substring(5));
					}
					fds.drawPic("移动短信用户OCS统计", "日期", "条数/费用");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//以下是移动增值用户OCS统计
				try {
					ps = conn.createStatement();
					rs = ps.executeQuery("select start_date, sum(cnt),sum(charge)/100 from bill.t_account_billcdr_value where switch_id in (201750,201810) and product_id   in (779,833,6600024000) and  start_date>=trunc(last_day(add_months(sysdate,-3) )+1) and  to_char(start_date,'yyyymmdd')<to_char(sysdate,'yyyymmdd')\r\n" + 
							"group by start_date\r\n" + 
							"order by start_date");//获得结果集
					forDataSet fds = new forDataSet();
					for(int i=0;i<49;i++)
						rs.next();
					while(rs.next()) {
						fds.GetDataset(rs.getInt("SUM(CNT)"), "SUM(CNT)", rs.getDate("START_DATE").toString().substring(5));
						fds.GetDataset(rs.getInt("SUM(CHARGE)/100"), "SUM(CHARGE)/100", rs.getDate("START_DATE").toString().substring(5));
					}
					fds.drawPic("移动增值用户OCS统计", "日期", "条数/费用");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		
		JButton btnNewButton_1 = new JButton("\u4E24\u5206\u949F\u5185OCS\u4EA4\u4E92\u67E5\u8BE2");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = connectorForOCSDB_T5.getConnectionForOCSDB_T5();
				Statement ps = null;
				ResultSet rs = null;
				String date = new SimpleDateFormat("yyyyMMddHH").format(new Date()).toString();
				try {

					ps = conn.createStatement();
					rs = ps.executeQuery("select/*+parallel(a,20)*/b.result_code,count(*) cnt from ocs.Ocp_Process_Ccr_Log_"+date+" a, ocs.Ocp_Process_Cca_Log_"+date+" b\r\n" + 
							"               where b.start_time > sysdate - 120 / 86400\r\n" + 
							"                 and a.seq_id = b.seq_id(+)\r\n" + 
							"                 and a.origin_host in\r\n" + 
							"                      ( select  substr(c.origin_host ,1,length(a.origin_host))\r\n" + 
							"                        from bss_comm.ocs_permission_host@dblink_bss_comm_t5   c \r\n" + 
							"                       where des_host_name in                                  \r\n" + 
							"                             ('CCG',\r\n" + 
							"                              'SCP',\r\n" + 
							"                              'PGW',\r\n" + 
							"                              'C网_AAA',\r\n" + 
							"                              '固网_AAA',\r\n" + 
							"                              'ISMP',\r\n" + 
							"                              'IPTV',\r\n" + 
							"                              '中兴短信1',\r\n" + 
							"                              '中兴短信2'\r\n" + 
							"                              ))\r\n" + 
							"               group by b.result_code");
					tableForOCScounter tfoc = new tableForOCScounter();
					tfoc.loadData(rs);
					tfoc.showTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBackground(Color.WHITE);
		
		JButton btnNewButton_2 = new JButton("\u6839\u636E\u7535\u8BDD\u53F7\u7801\u8FDB\u884C\u8BE6\u5355\u67E5\u8BE2");
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String number = textField.getText();
				String areaNumber = textField_1.getText();
				if(number.isEmpty()||number.equals("请输入要查询的电话号码")) {

					JOptionPane.showMessageDialog(null, "请输入电话号码", "电话号码不能为空",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(areaNumber.isEmpty()||areaNumber.equals("请输入区号")) {

					JOptionPane.showMessageDialog(null, "请输入区号", "区号不能为空",JOptionPane.WARNING_MESSAGE);
					return;
				}
				Connection conn = connectorForBill_T5.getConnectionForBill_T5();
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
				String sDate = format.format(date).toString().substring(2);
				try {//此处为话单查询
					ResultSet rs = conn.createStatement().executeQuery("select * from call_event_1"+sDate+areaNumber.substring(1)+" where serv_id in (select acc_prod_inst_id from prod_inst where acc_num = '"+number+"')");
					tableForPhoneCall tfpc = new tableForPhoneCall();
					tfpc.loadData(rs);
					tfpc.showTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {//此处为短信单查询
					ResultSet rs = conn.createStatement().executeQuery("select * from value_added_event_1"+sDate+areaNumber.substring(1)+" where serv_id in (select acc_prod_inst_id from prod_inst where acc_num = '"+number+"')");
					tableForMessage tfm = new tableForMessage();
					tfm.loadData(rs);
					tfm.showTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {//此处为数据流量短信单查询
					ResultSet rs = conn.createStatement().executeQuery("select * from data_event_1"+sDate+areaNumber.substring(1)+" where serv_id in (select acc_prod_inst_id from prod_inst where acc_num = '"+number+"')");
					tableForData tfd = new tableForData();
					tfd.loadData(rs);
					tfd.showTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmOcs.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
				.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 472, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
				.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textField)
						.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
		);
		frmOcs.getContentPane().setLayout(groupLayout);
		frmOcs.setTitle("OCS\u6570\u636E\u5E93\u7B80\u6613\u67E5\u8BE2");
		frmOcs.setBounds(100, 100, 948, 504);
		frmOcs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
