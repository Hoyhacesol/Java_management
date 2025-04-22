import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Haksa2 extends JFrame {
	
	JMenuBar mb = null;        //메뉴 바
	JMenu studentMenu = null;  //학생관리 메뉴
	JMenuItem studentInfoMenuItem = null; //학생정보 메뉴아이템
	
	JMenu bookMenu=null;   //도서관리 메뉴
	JMenuItem bookRentMenuItem=null; //대출현황 메뉴아이템
	
	JPanel panel; //메뉴별로 화면이 출력되는 패널
	
	public Haksa2() {
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.mb=new JMenuBar();
		this.studentMenu=new JMenu("학생관리");
		this.studentInfoMenuItem=new JMenuItem("학생정보");
		this.studentInfoMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("클릭");
				panel.removeAll(); // 모든컴포넌트삭제
				panel.revalidate(); // 다시활성화
				panel.repaint(); // 다시그림
				panel.add(new Student()); // 학생정보패널을 생성.추가.
				panel.setLayout(null); // 레이아웃은 사용 안함	
			}});
		this.studentMenu.add(this.studentInfoMenuItem);
		this.mb.add(this.studentMenu);
		
		this.bookMenu= new JMenu("도서관리");
		this.bookRentMenuItem=new JMenuItem("대출현황");
		this.bookRentMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); // 모든컴포넌트삭제
				panel.revalidate(); // 다시활성화
				panel.repaint(); // 다시그림
				panel.add(new BookRent()); // 대출현황패널을 생성.추가.
				panel.setLayout(null); // 레이아웃은 사용 안함
			}});
		this.bookMenu.add(this.bookRentMenuItem);
		this.mb.add(this.bookMenu);
		
		this.setJMenuBar(mb);
		
		//panel을 프레임에 추가
		this.panel=new JPanel();
		this.add(this.panel);
		
		
		this.setSize(300,500);
		this.setVisible(true);
	}

	public static void main(String[] args) {
			new Haksa2();

	}

}
