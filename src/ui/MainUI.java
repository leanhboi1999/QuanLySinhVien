package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import model.LopHoc;
import model.SinhVien;
import service.LopHocService;
import service.SinhVienService;

@SuppressWarnings("serial")
public class MainUI extends JFrame {
	DefaultMutableTreeNode root;
	JTree treeLop;
	DefaultTableModel dtmSinhVien;
	JTable tblSinhVien;
	JTextField txtMa, txtTen, txtTuoi;
	JButton btnThem, btnLuu, btnXoa;
	JMenuItem mnuNew, mnuEdit, mnuXoa;
	JPopupMenu popup;

	ArrayList<LopHoc> dsLopHoc;
	ArrayList<SinhVien> dsSinhVien;
	LopHocService lopService;
	LopHoc selectedLop;
	SinhVien selectedSv;
	SinhVienService sinhVienService;

	public MainUI(String title) {
		setTitle(title);
		addControls();
		addEvents();
		hienThiDanhSachLop();
	}

	private void hienThiDanhSachLop() {
		if (lopService == null) {
			lopService = new LopHocService();
			dsLopHoc = lopService.layToanBoLop();
			root.removeAllChildren();
			for (LopHoc lop : dsLopHoc) {
				DefaultMutableTreeNode nodeLop = new DefaultMutableTreeNode(lop);
				root.add(nodeLop);
			}
			treeLop.expandRow(0);
		}

	}

	private void addEvents() {
		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLyXoaSinhVien();

			}
		});

		mnuNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LopUI ui = new LopUI("Thêm lớp mới");
				ui.showWindows();
				hienThiDanhSachLop();
				treeLop.updateUI();

			}
		});

		treeLop.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row = treeLop.getClosestRowForLocation(e.getX(), e.getY());
					treeLop.setSelectionRow(row);
					popup.show(e.getComponent(), e.getX(), getY());
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode nodeselected = (DefaultMutableTreeNode) treeLop.getLastSelectedPathComponent();
				if (nodeselected == null)
					return;
				if (nodeselected.getLevel() == 0)
					return;
				if (sinhVienService == null)
					sinhVienService = new SinhVienService();
				selectedLop = (LopHoc) nodeselected.getUserObject();
				if (selectedLop != null)
					dsSinhVien = sinhVienService.layToanBoSinhVien(selectedLop.getMaLop());
				hienThiSinhVIen();
			}
		});

		tblSinhVien.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblSinhVien.getSelectedRow();
				if (row == -1)
					return;
				selectedSv = dsSinhVien.get(row);
				txtMa.setText(selectedSv.getMa());
				txtTen.setText(selectedSv.getTen());
				txtTuoi.setText(selectedSv.getTuoi() + "");

			}
		});
	}

	protected void xuLyXoaSinhVien() {
		if (selectedSv == null)
			return;
		int ret = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa sinh viên này", "Hỏi xóa",
				JOptionPane.YES_NO_OPTION);
		if(ret==JOptionPane.YES_OPTION) {
			if(sinhVienService==null) 
				sinhVienService =new SinhVienService();
			if(sinhVienService.xoaSinhVien(selectedSv)>0) {
				if (selectedLop != null)
					dsSinhVien = sinhVienService.layToanBoSinhVien(selectedLop.getMaLop());
				hienThiSinhVIen();
			}
				
		}
	}

	protected void hienThiSinhVIen() {
		dtmSinhVien.setRowCount(0);
		for (SinhVien sv : dsSinhVien) {
			Vector<Object> vec = new Vector<Object>();
			vec.add(sv.getMa());
			vec.add(sv.getTen());
			vec.add(sv.getTuoi());
			vec.add(sv.getMalop());
			dtmSinhVien.addRow(vec);
		}

	}

	private void addControls() {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnLeft = new JPanel();
		pnLeft.setPreferredSize(new Dimension(300, 0));
		JPanel pnRight = new JPanel();
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
		con.add(sp, BorderLayout.CENTER);

		pnLeft.setLayout(new BorderLayout());
		root = new DefaultMutableTreeNode("Danh sách lớp");
		treeLop = new JTree(root);
		JScrollPane scTree = new JScrollPane(treeLop, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnLeft.add(scTree, BorderLayout.CENTER);

		pnRight.setLayout(new BorderLayout());
		JPanel pnTopOfRight = new JPanel();
		pnTopOfRight.setLayout(new BorderLayout());
		pnTopOfRight.setPreferredSize(new Dimension(0, 300));
		JPanel pnBotOfRight = new JPanel();
		JSplitPane spSinhVien = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnTopOfRight, pnBotOfRight);
		pnRight.add(spSinhVien, BorderLayout.CENTER);

		dtmSinhVien = new DefaultTableModel();
		dtmSinhVien.addColumn("Mã sinh viên");
		dtmSinhVien.addColumn("Tên sinh viên");
		dtmSinhVien.addColumn("Tuổi sinh viên");
		dtmSinhVien.addColumn("Mã lớp");
		tblSinhVien = new JTable(dtmSinhVien);
		JScrollPane scTable = new JScrollPane(tblSinhVien, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTopOfRight.add(scTable, BorderLayout.CENTER);

		pnBotOfRight.setLayout(new BoxLayout(pnBotOfRight, BoxLayout.Y_AXIS));

		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMa = new JLabel("Mã sinh viên:");
		txtMa = new JTextField(18);
		pnMa.add(lblMa);
		pnMa.add(txtMa);
		pnBotOfRight.add(pnMa);

		JPanel pnTen = new JPanel();
		pnTen.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTen = new JLabel("Tên sinh viên:");
		txtTen = new JTextField(18);
		pnTen.add(lblTen);
		pnTen.add(txtTen);
		pnBotOfRight.add(pnTen);

		JPanel pnTuoi = new JPanel();
		pnTuoi.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTuoi = new JLabel("Tuổi sinh viên:");
		txtTuoi = new JTextField(18);
		pnTuoi.add(lblTuoi);
		pnTuoi.add(txtTuoi);
		pnBotOfRight.add(pnTuoi);

		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnThem = new JButton("Thêm mới");
		btnThem.setIcon(new ImageIcon("Images/new.png"));
		btnLuu = new JButton("Lưu");
		btnLuu.setIcon(new ImageIcon("Images/save.png"));
		btnXoa = new JButton("Xóa");
		btnXoa.setIcon(new ImageIcon("Images/remove.png"));
		pnButton.add(btnThem);
		pnButton.add(btnLuu);
		pnButton.add(btnXoa);
		pnBotOfRight.add(pnButton);
		lblMa.setPreferredSize(lblTuoi.getPreferredSize());
		lblTen.setPreferredSize(lblTuoi.getPreferredSize());

		setupMenu();
	}

	private void setupMenu() {
		mnuNew = new JMenuItem("Thêm lớp mới");
		mnuNew.setIcon(new ImageIcon("images/add_lop.png"));
		mnuEdit = new JMenuItem("Sửa lớp này");
		mnuEdit.setIcon(new ImageIcon("images/edit_lop.png"));
		mnuXoa = new JMenuItem("Xóa lớp này");
		mnuXoa.setIcon(new ImageIcon("images/remove_lop.png"));
		popup = new JPopupMenu();
		popup.add(mnuNew);
		popup.add(mnuEdit);
		popup.addSeparator();
		popup.add(mnuXoa);
	}

	@SuppressWarnings("static-access")
	public void showWindows() {
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
}
