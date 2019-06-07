package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import io.FileFactory;
import model.DangNhap;
import service.DangNhapService;

@SuppressWarnings("serial")
public class DangNhapUI extends JFrame {
	JTextField txtUsername;
	JPasswordField txtPassword;
	JButton btnLogin, btnExit;
	JCheckBox chkSave;

	public DangNhapUI(String title) {
		setTitle(title);
		addControls();
		addEvents();
		hienThiLaiThongTinDangNhap();
	}

	private void hienThiLaiThongTinDangNhap() {
		File f = new File("login.data");
		if(f.exists()) {
			Object data=FileFactory.readData("login.data");
			if(data!=null) {
				DangNhap dn=(DangNhap) data;
				txtUsername.setText(dn.getUsername());
				txtPassword.setText(dn.getPassword());
				chkSave.setSelected(true);
			}
		}
	}

	private void addEvents() {
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLyDangNhap();
			}
		});
	}

	@SuppressWarnings("deprecation")
	protected void xuLyDangNhap() {
		DangNhapService dnService = new DangNhapService();
		DangNhap dn = dnService.login(txtUsername.getText(), txtPassword.getText());
		if (dn != null) {
			if (chkSave.isSelected()) {
				FileFactory.saveData(dn, "login.data");
			} else {
				FileFactory.saveData(null, "login.data");
			}
			MainUI ui = new MainUI("Quản trị sinh viên");
			ui.showWindows();
		} else {
			JOptionPane.showMessageDialog(null, "Đăng nhập thất bại");
		}
	}

	private void addControls() {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		con.add(pnNorth, BorderLayout.NORTH);
		JPanel pnCenter = new JPanel();
		con.add(pnCenter, BorderLayout.CENTER);
		JPanel pnSouth = new JPanel();
		con.add(pnSouth, BorderLayout.SOUTH);

		JLabel lblTitle = new JLabel("Đăng nhập hệ thống");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
		lblTitle.setForeground(Color.RED);
		pnNorth.add(lblTitle);

		pnCenter.setLayout(new BorderLayout());
		JPanel pnImage = new JPanel();
		JLabel lblIcon = new JLabel(new ImageIcon("Images/login.png"));
		pnImage.add(lblIcon);
		pnCenter.add(pnImage, BorderLayout.WEST);

		JPanel pnUser = new JPanel();
		pnUser.setLayout(new BoxLayout(pnUser, BoxLayout.Y_AXIS));
		pnCenter.add(pnUser);
		JPanel pnUsername = new JPanel();
		pnUsername.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblUsername = new JLabel("Tài khoản");
		txtUsername = new JTextField(18);
		pnUsername.add(lblUsername);
		pnUsername.add(txtUsername);
		pnUser.add(pnUsername);

		JPanel pnPassword = new JPanel();
		pnPassword.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPassword = new JLabel("Mật khẩu");
		txtPassword = new JPasswordField(18);
		pnPassword.add(lblPassword);
		pnPassword.add(txtPassword);
		pnUser.add(pnPassword);

		JPanel pnSave = new JPanel();
		chkSave = new JCheckBox("Lưu thông tin đăng nhập");
		pnSave.add(chkSave);
		pnUser.add(pnSave);
		btnLogin = new JButton("Dăng nhập");
		btnLogin.setIcon(new ImageIcon("Images/action.png"));
		btnExit = new JButton("Thoát");
		btnExit.setIcon(new ImageIcon("Images/exit.png"));
		lblPassword.setPreferredSize(lblUsername.getPreferredSize());
		pnSouth.add(btnLogin);
		pnSouth.add(btnExit);

		TitledBorder borderUser = new TitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Thông tin đăng nhập");
		pnUser.setBorder(borderUser);
	}

	public void showWindows() {
		this.setSize(480, 270);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
