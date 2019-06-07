package ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.LopHoc;
import service.LopHocService;

public class LopUI extends JDialog {
	JTextField txtMaLop, txtTenLop;
	JButton btnLuu, btnThoat;
	
	public LopUI(String title) {
		setTitle(title);
		addControls();
		addEvents();
	}

	private void addEvents() {
		btnThoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		
		btnLuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLyLuuLop();
				
			}
		});
		
	}

	protected void xuLyLuuLop() {
		try {
			LopHoc lopHoc=new LopHoc();
			lopHoc.setMaLop(txtMaLop.getText());
			lopHoc.setTenLop(txtTenLop.getText());
			LopHocService lopHocService=new LopHocService();
			int x=lopHocService.LuuLop(lopHoc);
			if(x>0) {
				JOptionPane.showMessageDialog(null, "Lưu lớp mới thành công");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addControls() {
		Container con=getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		JPanel pnTitle=new JPanel();
		JLabel lblTitle=new JLabel("Nhập thông tin mới");
		pnTitle.add(lblTitle);
		con.add(pnTitle);
		
		JPanel pnMaLop=new JPanel();
		JLabel lblMaLop= new JLabel("Mã lớp");
		txtMaLop=new JTextField(18);
		pnMaLop.add(lblMaLop);
		pnMaLop.add(txtMaLop);
		con.add(pnMaLop);
		
		JPanel pnTenLop=new JPanel();
		JLabel lblTenLop= new JLabel("Tên lớp");
		txtTenLop=new JTextField(18);
		pnTenLop.add(lblTenLop);
		pnTenLop.add(txtTenLop);
		con.add(pnTenLop);
		
		JPanel pnButton=new JPanel();
		btnLuu=new JButton("Lưu");
		btnThoat=new JButton("Thoát");
		pnButton.add(btnLuu);
		pnButton.add(btnThoat);
		con.add(pnButton);
		
		
	}
	
	public void showWindows() {
		this.setSize(400,300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}
}
