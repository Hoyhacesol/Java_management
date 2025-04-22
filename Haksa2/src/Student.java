import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Student extends JPanel {
	//field
	JTextField txtId;
	JTextField txtName;
	JTextField txtDept;
	JTextField txtAddress;
	
	//테이블 , 모델
	DefaultTableModel model = null;
	JTable table = null;
	
	// CRUD
	JButton btnInsert = null; //등록 Create
	JButton btnSelect = null; //읽기 (Read)
	JButton btnUpdate = null; //수정 Update
	JButton btnDelete = null; //삭제 Delete
	
	JButton btnSearch = null; //학번으로 학생 검색
	
	public Student() {
		
		//layout설정
				this.setLayout(new FlowLayout());
				
				this.add(new JLabel("학번"));
				this.txtId=new JTextField(15);
				this.add(this.txtId);
				
				this.btnSearch=new JButton("검색");
				this.btnSearch.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Connection conn = null;
						try {
							//oracle jdbc driver 로드
							Class.forName("oracle.jdbc.driver.OracleDriver");
							conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","KIM","1234");
							System.out.println("연결완료");
							
							//Statement객체 생성
							Statement stmt=conn.createStatement();
							// select
							ResultSet rs = stmt.executeQuery("select * from student2 where id='"+txtId.getText()+"'");
							
							//JTable 초기화
							model.setNumRows(0); //model의 행의수를 0 으로 설정
							
							
							while(rs.next()) {
								String[] row = new String[4]; //행
								row[0]= rs.getString("id");
								row[1]= rs.getString("name");
								row[2]= rs.getString("dept");
								row[3]= rs.getString("address");
								model.addRow(row); //모델의 추가
								
								txtId.setText(rs.getString("id"));
								txtName.setText(rs.getString("name"));
								txtDept.setText(rs.getString("dept"));
								txtAddress.setText(rs.getString("address"));
							}
							rs.close();
							stmt.close();
							conn.close();
							
						} catch(Exception e1) {
							e1.printStackTrace();
						}
						
					}});
				this.add(this.btnSearch);
				
				this.add(new JLabel("이름"));
				this.txtName=new JTextField(20);
				this.add(this.txtName);
				
				this.add(new JLabel("학과"));
				this.txtDept=new JTextField(20);
				this.add(this.txtDept);
				
				this.add(new JLabel("주소"));
				this.txtAddress=new JTextField(20);
				this.add(this.txtAddress);

				
				String[] colname = {"학번","이름","학과","주소"}; //column명
				this.model = new DefaultTableModel(colname,0);
				this.table = new JTable(model); //바인딩
				this.table.setPreferredScrollableViewportSize(new Dimension(250,270));
				JScrollPane sp = new JScrollPane(this.table);
				this.table.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						table = (JTable) e.getComponent();
						model=(DefaultTableModel) table.getModel();
						txtId.setText((String)model.getValueAt(table.getSelectedRow(), 0));
						txtName.setText((String)model.getValueAt(table.getSelectedRow(), 1));
						txtDept.setText((String)model.getValueAt(table.getSelectedRow(), 2));
						txtAddress.setText((String)model.getValueAt(table.getSelectedRow(), 3));
					}

					@Override
					public void mousePressed(MouseEvent e) {	}
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}});
				this.add(sp);
				
				
				
				
				////////////////////////////추가
				this.btnInsert = new JButton("등록");
				this.btnInsert.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Connection conn = null;
						try {
							//oracle jdbc driver 로드
							Class.forName("oracle.jdbc.driver.OracleDriver");
							conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","KIM","1234");
							System.out.println("연결완료");
							
							//Statement객체 생성
							Statement stmt=conn.createStatement();
							//insert
							stmt.executeUpdate("insert into student2 values('"+txtId.getText()+"','"+txtName.getText()+"','"+txtDept.getText()+"','"+txtAddress.getText()+"')");
							// select
							ResultSet rs = stmt.executeQuery("select * from student2");
							while(rs.next()) {
								System.out.println(rs.getString("id"));
								System.out.println(rs.getString("name"));
								System.out.println(rs.getString("dept"));
								System.out.println(rs.getString("address"));
							}
							rs.close();
							stmt.close();
							conn.close();
							
							list();
							resetTextField();//텍스트필드초기화
							
						} catch(Exception e1) {
							e1.printStackTrace();
						}
					}});
				this.add(this.btnInsert);
				
				////////////////////////////목록
				this.btnSelect= new JButton("목록");
				this.btnSelect.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						list();
					}});
				this.add(this.btnSelect);
				
				////////////////////////////수정
				this.btnUpdate = new JButton("수정");
				this.btnUpdate.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Connection conn=null;
						try {
							// oracle jdbc driver 로드
							Class.forName("oracle.jdbc.driver.OracleDriver");
							conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kim","1234");
							System.out.println("연결완료");
							
							//statement객체생성
							Statement stmt=conn.createStatement();
							
							stmt.executeUpdate("update student2 set name='"+txtName.getText()+"',dept='"+txtDept.getText()+"',address='"+txtAddress.getText()+"' where id='"+txtId.getText()+"'");
							
							//select
							ResultSet rs=stmt.executeQuery("select * from student2");
							while(rs.next()) {
								System.out.println(rs.getString("id"));
								System.out.println(rs.getString("name"));
								System.out.println(rs.getString("dept"));
								System.out.println(rs.getString("address"));
							}
							rs.close();
							stmt.close();
							conn.close();
							
							list();
							resetTextField();//텍스트필드초기화
						}catch(Exception e1) {
							e1.printStackTrace();
						}	
						
					}});
				this.add(this.btnUpdate);
				
				////////////////////////////삭제
				this.btnDelete = new JButton("삭제");
				this.btnDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?","confirm", JOptionPane.YES_NO_OPTION);
						if(result == JOptionPane.YES_OPTION) {
							Connection conn=null;
							try {
								//oracle jdbc driver 로드
								Class.forName("oracle.jdbc.driver.OracleDriver");
								conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","KIM","1234");
								System.out.println("연결완료");
								
								//Statement객체 생성
								Statement stmt=conn.createStatement();

								//delete
								stmt.executeUpdate("delete from student2 where id='"+txtId.getText()+"'");
								// select
								ResultSet rs = stmt.executeQuery("select * from student2");
								while(rs.next()) {
									System.out.println(rs.getString("id"));
									System.out.println(rs.getString("name"));
									System.out.println(rs.getString("dept"));
									System.out.println(rs.getString("address"));
								}
								rs.close();
								stmt.close();
								conn.close();
								
								list();
								resetTextField();//텍스트필드초기화
								
							} catch(Exception e1) {
								e1.printStackTrace();
							}
						}
					}});
				this.add(this.btnDelete);
				
				this.setSize(280,500);
				this.setVisible(true);
		
	}
	
	//목록추가
	public void list() {
		Connection conn = null;
		try {
			//oracle jdbc driver 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","KIM","1234");
			System.out.println("연결완료");
			
			//Statement객체 생성
			Statement stmt=conn.createStatement();
			// select
			ResultSet rs = stmt.executeQuery("select * from student2");
			
			//JTable 초기화
			model.setNumRows(0); //model의 행의수를 0 으로 설정
			
			
			while(rs.next()) {
				String[] row = new String[4]; //행
				row[0]= rs.getString("id");
				row[1]= rs.getString("name");
				row[2]= rs.getString("dept");
				row[3]= rs.getString("address");
				model.addRow(row); //모델의 추가
			}
			rs.close();
			stmt.close();
			conn.close();
			
			
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void resetTextField() {
		this.txtId.setText("");
		this.txtName.setText("");
		this.txtDept.setText("");
		this.txtAddress.setText("");
	}

}

