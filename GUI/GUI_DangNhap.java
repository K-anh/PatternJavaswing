package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.TaiKhoan_Dao;
import GUI.GUI_NhaCungCap;
import GUI.GUI_NhanVien;
import entity.TaiKhoan;

import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Button;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class GUI_DangNhap extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txttaikhoan;
	private JPasswordField txtmatkhau;
	//private Connection connection;
	ResultSet rs;
	private String position;
	private String employeeName;
	private JButton btndangnhap , btnQuenMK;
	
	private List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	private JFrame frame = new JFrame();

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_DangNhap frame = new GUI_DangNhap();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_DangNhap() {
		ConnectDB.getInstance().connect();
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_DangNhap.class.getResource("/imgs/qls.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Trang Login");
		setBounds(100, 100, 1150, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GIÁ TRỊ THẬT CỦA MỘT CUỐN SÁCH KHÔNG THỂ NẰM HẾT Ở TRANG BÌA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(234, 198, 150));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(0, 0, 1143, 68);
		contentPane.add(lblNewLabel);
		
		
	       
		
		JLabel lblNewLabel_3 = new JLabel("ĐĂNG NHẬP");
		lblNewLabel_3.setBackground(new Color(17, 57, 70));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(249, 224, 187));
		lblNewLabel_3.setBounds(359, 95, 414, 100);
		contentPane.add(lblNewLabel_3);
		
		
		txttaikhoan = new JTextField();
		txttaikhoan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txttaikhoan.setForeground(new Color(0, 0, 0));
		txttaikhoan.setBackground(new Color(255, 255, 255));
		txttaikhoan.setColumns(10);
		txttaikhoan.setBounds(506, 205, 242, 33);
		contentPane.add(txttaikhoan);
		
		JLabel lblNewLabel_2 = new JLabel("Tài Khoản:");
		lblNewLabel_2.setForeground(new Color(249, 224, 187));
		lblNewLabel_2.setBackground(new Color(17, 57, 70));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(401, 204, 95, 35);
		contentPane.add(lblNewLabel_2);
		
		txtmatkhau = new JPasswordField();
		txtmatkhau.setForeground(Color.BLACK);
		txtmatkhau.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtmatkhau.setColumns(10);
		txtmatkhau.setBackground(Color.WHITE);
		txtmatkhau.setBounds(506, 285, 242, 33);
		contentPane.add(txtmatkhau);
		JLabel lblNewLabel_2_1 = new JLabel("Mật Khẩu:");
		lblNewLabel_2_1.setBackground(new Color(17, 57, 70));
		lblNewLabel_2_1.setForeground(new Color(249, 224, 187));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(401, 283, 95, 35);
		contentPane.add(lblNewLabel_2_1);
		
		
		

		btndangnhap = new JButton("Đăng Nhập");
		btndangnhap.setForeground(new Color(249, 224, 187));
		btndangnhap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btndangnhap.setBackground(new Color(17, 57, 70));
		btndangnhap.setBounds(455, 375, 223, 60);
		
		contentPane.add(btndangnhap);
		
		
		btnQuenMK = new JButton("Quên mật khẩu ?");
		btnQuenMK.setForeground(new Color(249, 224, 187));
		btnQuenMK.setBackground(new Color(17, 57, 70));
		btnQuenMK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnQuenMK.setBounds(454, 455, 223, 60);
		btnQuenMK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_QuenMK guiquenMK = new GUI_QuenMK();
				guiquenMK.setVisible(true);
				dispose();
				
			}
		});
		contentPane.add(btnQuenMK);
		
		JLabel box_dangnhap = new JLabel("New label");
		box_dangnhap.setForeground(new Color(0, 0, 0));
		box_dangnhap.setIcon(new ImageIcon(GUI_DangNhap.class.getResource("/imgs/box1.jpg")));
		box_dangnhap.setBounds(359, 95, 414, 493);
		contentPane.add(box_dangnhap);
		
		JLabel nen = new JLabel("");
		nen.setBackground(new Color(204, 0, 153));
		nen.setIcon(new ImageIcon(GUI_DangNhap.class.getResource("/imgs/main2_img.jpg")));
		nen.setBounds(10, 0, 1143, 663);
		contentPane.add(nen);
		btndangnhap.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		
		
		
	}
//	Ket thuc giao dien
	
	
//	Function xu ly
	
//	Function dang nhap
	public boolean dangNhap() {
		dstk = tk_dao.getAllTaiKhoan();
		
		
		String tenTK = txttaikhoan.getText();
		char[] password = txtmatkhau.getPassword();
		String mk = new String(password);
		
		
		boolean checkDangNhap = false;
		
		for (TaiKhoan taiKhoan : dstk) {
			boolean taiKhoanDung = tenTK.equalsIgnoreCase(taiKhoan.getMaNV());
			boolean matKhauDung = mk.equalsIgnoreCase(taiKhoan.getMk());
			if(taiKhoanDung == true && matKhauDung == true) {
				System.out.println("Dang Nhap Thanh Cong");
				tk_dao.dangNhap(taiKhoan.getMaNV());
				checkDangNhap = true;
			}
		}
		
		
		
		
		return checkDangNhap;
		
	}
	
//	Function dang xuat
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btndangnhap)) {
			if (txttaikhoan.getText().equals("") || txtmatkhau.getPassword().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn cần điền đầy đủ thông tin!");

			} else {
				
				boolean resultLogin = dangNhap();
				if(resultLogin == true) {
					GUI_TrangChu guiTrangChu = new GUI_TrangChu();
					GUI_DangNhap guiDn = new GUI_DangNhap();
					
					guiTrangChu.setVisible(true);
					guiTrangChu.setLocationRelativeTo(null);
					dispose();
					
				}else {
					JOptionPane.showMessageDialog(null, "Thông Tin Đăng Nhập Không Hợp Lệ");
				}
			}
		}
		
		
		
	}
}
	
	
	
	 

