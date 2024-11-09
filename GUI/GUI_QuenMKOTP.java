package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import entity.CodeEmail;
import entity.TaiKhoan;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Button;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.mail.internet.*;
import javax.mail.*;

public class GUI_QuenMKOTP extends JFrame  implements ActionListener  {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4 , txtCode;
	private Color normalColor;
	private Color pressedColor;
	private JTextField txtNhạpMKM , txtNhapOTP;
	private JButton btnDoiMKM , btnXcNn;

	private static String EMAIL_USERNAME = "hieusachtunhan.htnd@gmail.com";
	private static String EMAIL_PASSWORD = "jtne shlp qoxc ajzy";
	private List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	private TaiKhoan_Dao tk_dao2;
	private String maNV = "USER";
	private String email = "";
	
	private String otpInPut = "";
	private String otpInMail = "test";
	private String maNhanVien = "";
	private JFrame frame = new JFrame();
	
//	private List<String> codeOtp = tk_dao.returnAllCodeOtp();
	
//	Email của người muốn nhận
	private String RECIPIENT_EMAIL;
	private JTextField txtNhaplaiMKM;
	private boolean result_checkOtp = false;
	
	public CodeEmail code_mail;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_QuenMKOTP guiQuenMK = new GUI_QuenMKOTP();
					guiQuenMK.setLocationRelativeTo(null);
					guiQuenMK.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_QuenMKOTP() {
		
		ConnectDB.getInstance().connect();
		
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_QuenMKOTP.class.getResource("/imgs/qls.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Trang chủ");
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
		
		
	       JPopupMenu popupMenu = new JPopupMenu();
	        JMenuItem menuItemThem = new JMenuItem("Thêm khách hàng");
	        menuItemThem.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
	        menuItemThem.setForeground(new Color(255, 255, 255)); // Set text color
	        menuItemThem.setBackground(new Color(136, 74, 57)); // Set background color
	        menuItemThem.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Set font
	        menuItemThem.setPreferredSize(new Dimension(163, 58));
	        popupMenu.add(menuItemThem);
	        
	        JMenuItem menuItemCapNhat = new JMenuItem("Cập nhật khách hàng");
	        menuItemCapNhat.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
	        menuItemCapNhat.setForeground(new Color(255, 255, 255));
	        menuItemCapNhat.setBackground(new Color(136, 74, 57));
	        menuItemCapNhat.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        menuItemCapNhat.setPreferredSize(new Dimension(163, 58));
	        popupMenu.add(menuItemCapNhat);
	        
	        JMenuItem menuItemTimKiem = new JMenuItem("Tìm khách hàng");
	        menuItemTimKiem.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
	        menuItemTimKiem.setForeground(new Color(255, 255, 255));
	        menuItemTimKiem.setBackground(new Color(136, 74, 57));
	        menuItemTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        menuItemTimKiem.setPreferredSize(new Dimension(163, 58));
	        popupMenu.add(menuItemTimKiem);

	        menuItemThem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Add your logic for "Thêm" here
	            }
	        });

	        menuItemCapNhat.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Add your logic for "Cập nhật" here
	            }
	        });

	        menuItemTimKiem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Add your logic for "tìm kiếm" here
	            }
	        });
		
		
	     JPopupMenu popupMenuSach = new JPopupMenu();
	        JMenuItem menuItemThemSach = new JMenuItem("Thêm sách");
	        menuItemThemSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
	        menuItemThemSach.setForeground(new Color(255, 255, 255)); 
	        menuItemThemSach.setBackground(new Color(136, 74, 57)); 
	        menuItemThemSach.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
	        menuItemThemSach.setPreferredSize(new Dimension(157, 58));
	        popupMenuSach.add(menuItemThemSach);
	        
	        JMenuItem menuItemCapNhatSach = new JMenuItem("Cập nhật sách");
	        menuItemCapNhatSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
	        menuItemCapNhatSach.setForeground(new Color(255, 255, 255)); 
	        menuItemCapNhatSach.setBackground(new Color(136, 74, 57));
	        menuItemCapNhatSach.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
	        menuItemCapNhatSach.setPreferredSize(new Dimension(157, 58));
	        popupMenuSach.add(menuItemCapNhatSach);
	        
	        JMenuItem menuItemTimKiemSach = new JMenuItem("Tìm kiếm sách");
	        menuItemTimKiemSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
	        menuItemTimKiemSach.setForeground(new Color(255, 255, 255)); 
	        menuItemTimKiemSach.setBackground(new Color(136, 74, 57)); 
	        menuItemTimKiemSach.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
	        menuItemTimKiemSach.setPreferredSize(new Dimension(157, 58));
	        popupMenuSach.add(menuItemTimKiemSach);
	        
	        JMenuItem menuItemXoaSach = new JMenuItem("Xóa sách");
	        menuItemXoaSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/delete_img.png")));
	        menuItemXoaSach.setForeground(new Color(255, 255, 255)); 
	        menuItemXoaSach.setBackground(new Color(136, 74, 57)); 
	        menuItemXoaSach.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
	        menuItemXoaSach.setPreferredSize(new Dimension(157, 58));
	        popupMenuSach.add(menuItemXoaSach);
	        menuItemThemSach.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               
	            }
	        });
	        popupMenuSach.add(menuItemThemSach);

	      
	       
	        menuItemCapNhatSach.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	              
	            }
	        });
	        popupMenuSach.add(menuItemCapNhatSach);

	      
	        
	        menuItemTimKiemSach.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            
	            }
	        });
	        popupMenuSach.add(menuItemTimKiemSach);

	       
	       
	        menuItemXoaSach.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	             
	            }
	        });
	        popupMenuSach.add(menuItemXoaSach);
		
		JPopupMenu popupMenuHoaDon = new JPopupMenu();
		JMenuItem menuItemTaoHoaDon = new JMenuItem("Tạo hóa đơn");
		menuItemTaoHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemTaoHoaDon.setForeground(new Color(255, 255, 255));
		menuItemTaoHoaDon.setBackground(new Color(136, 74, 57));
		menuItemTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemTaoHoaDon.setPreferredSize(new Dimension(163, 58));
		popupMenuHoaDon.add(menuItemTaoHoaDon);

		
		JMenuItem menuItemXemChiTietHoaDon = new JMenuItem("Xem chi tiết hóa đơn");
		menuItemXemChiTietHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/payment_img.png")));
		menuItemXemChiTietHoaDon.setForeground(new Color(255, 255, 255));
		menuItemXemChiTietHoaDon.setBackground(new Color(136, 74, 57));
		menuItemXemChiTietHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemXemChiTietHoaDon.setPreferredSize(new Dimension(163, 58));
		popupMenuHoaDon.add(menuItemXemChiTietHoaDon);

		menuItemTaoHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		      
		    }
		});

		menuItemXemChiTietHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});
	
		
		
		JPopupMenu popupMenuNhanVien = new JPopupMenu();
		JMenuItem menuItemThemNhanVien = new JMenuItem("Thêm nhân viên");
		menuItemThemNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemThemNhanVien.setForeground(new Color(255, 255, 255));
		menuItemThemNhanVien.setBackground(new Color(136, 74, 57));
		menuItemThemNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemThemNhanVien.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemThemNhanVien);

		
		JMenuItem menuItemCapNhatNhanVien = new JMenuItem("Cập nhật nhân viên");
		menuItemCapNhatNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
		menuItemCapNhatNhanVien.setForeground(new Color(255, 255, 255));
		menuItemCapNhatNhanVien.setBackground(new Color(136, 74, 57));
		menuItemCapNhatNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemCapNhatNhanVien.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemCapNhatNhanVien);

		
		JMenuItem menuItemTimKiemNhanVien = new JMenuItem("Tìm nhân viên");
		menuItemTimKiemNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
		menuItemTimKiemNhanVien.setForeground(new Color(255, 255, 255));
		menuItemTimKiemNhanVien.setBackground(new Color(136, 74, 57));
		menuItemTimKiemNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemTimKiemNhanVien.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemTimKiemNhanVien);

		
		JMenuItem menuItemXoaNhanVien = new JMenuItem("Xóa nhân viên");
		menuItemXoaNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/delete_img.png")));
		menuItemXoaNhanVien.setForeground(new Color(255, 255, 255));
		menuItemXoaNhanVien.setBackground(new Color(136, 74, 57));
		menuItemXoaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemXoaNhanVien.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemXoaNhanVien);

	
		JMenuItem menuItemXemNhanVienBiXoa = new JMenuItem("Xem nhân viên bị xóa");
		menuItemXemNhanVienBiXoa.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/erase_img.png")));
		menuItemXemNhanVienBiXoa.setForeground(new Color(255, 255, 255));
		menuItemXemNhanVienBiXoa.setBackground(new Color(136, 74, 57));
		menuItemXemNhanVienBiXoa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemXemNhanVienBiXoa.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemXemNhanVienBiXoa);

		menuItemXemNhanVienBiXoa.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Tạo một thể hiện của GUI_NhanVien
		        GUI_XemTTNVbixoa guiXemTTNVbixoa = new GUI_XemTTNVbixoa();

		        // Ẩn giao diện hiện tại 
		        setVisible(false);

		        // Hiển thị giao diện mới
		        guiXemTTNVbixoa.setVisible(true);
		    }
		});

		menuItemThemNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		    }
		});

		menuItemCapNhatNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		    }
		});

		menuItemTimKiemNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		 
		    }
		});

		menuItemXoaNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    
		    }
		});

		menuItemXemNhanVienBiXoa.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		  
		    }
		});
	
		
		
		JPopupMenu popupMenuNCC = new JPopupMenu();
	
		JMenuItem menuItemThemNCC = new JMenuItem("Thêm NCC");
		menuItemThemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemThemNCC.setForeground(new Color(255, 255, 255));
		menuItemThemNCC.setBackground(new Color(136, 74, 57));
		menuItemThemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemThemNCC.setPreferredSize(new Dimension(163, 58));
		popupMenuNCC.add(menuItemThemNCC);

		
		JMenuItem menuItemCapNhatNCC = new JMenuItem("Cập nhật NCC");
		menuItemCapNhatNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
		menuItemCapNhatNCC.setForeground(new Color(255, 255, 255));
		menuItemCapNhatNCC.setBackground(new Color(136, 74, 57));
		menuItemCapNhatNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemCapNhatNCC.setPreferredSize(new Dimension(163, 58));
		popupMenuNCC.add(menuItemCapNhatNCC);

	
		JMenuItem menuItemTimKiemNCC = new JMenuItem("Tìm NCC");
		menuItemTimKiemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
		menuItemTimKiemNCC.setForeground(new Color(255, 255, 255));
		menuItemTimKiemNCC.setBackground(new Color(136, 74, 57));
		menuItemTimKiemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemTimKiemNCC.setPreferredSize(new Dimension(163, 58));
		popupMenuNCC.add(menuItemTimKiemNCC);

		menuItemThemNCC.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

		menuItemCapNhatNCC.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

		menuItemTimKiemNCC.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		     
		    }
		});
		
		JPopupMenu popupMenuTrGip = new JPopupMenu();

	
		JMenuItem menuItemHuongDan = new JMenuItem("Hướng dẫn sử dụng");
		menuItemHuongDan.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/manual_img.png")));
		menuItemHuongDan.setForeground(new Color(255, 255, 255));
		menuItemHuongDan.setBackground(new Color(136, 74, 57));
		menuItemHuongDan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemHuongDan.setPreferredSize(new Dimension(163, 58));
		popupMenuTrGip.add(menuItemHuongDan);
// 		ấn vào hướng dẫn sẽ hiện ra trang web hướng dẫn sử dụng 
		menuItemHuongDan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://hieunhan1919.github.io/ptudwebhelp/").toURI());
				}
				catch(Exception ex){}
			}
		});
		
		JMenuItem menuItemDoiMatKhau = new JMenuItem("Đổi mật khẩu");
		menuItemDoiMatKhau.setIcon(new ImageIcon("/imgs/reload_img.png"));
		menuItemDoiMatKhau.setForeground(new Color(255, 255, 255));
		menuItemDoiMatKhau.setBackground(new Color(136, 74, 57));
		menuItemDoiMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemDoiMatKhau.setPreferredSize(new Dimension(163, 58));
		popupMenuTrGip.add(menuItemDoiMatKhau);

		menuItemHuongDan.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

		menuItemDoiMatKhau.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		
		    }
		});

		
	
		
		JLabel lblNewLabel_3 = new JLabel("QUÊN MẬT KHẨU");
		lblNewLabel_3.setForeground(new Color(255, 194, 111));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBackground(new Color(255, 194, 111));
		lblNewLabel_3.setBounds(313, 91, 520, 100);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Nhập mã OTP được gửi trong mail để đổi mật khẩu mới");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setForeground(new Color(255, 194, 111));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_1.setBackground(new Color(255, 194, 111));
		lblNewLabel_3_1.setBounds(313, 169, 520, 49);
		contentPane.add(lblNewLabel_3_1);
		

		txtNhapOTP = new JTextField();
		txtNhapOTP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNhapOTP.setForeground(new Color(0, 0, 0));
		txtNhapOTP.setBackground(new Color(255, 255, 255));
		txtNhapOTP.setText("Nhập mã OTP");
		txtNhapOTP.setColumns(10);
		txtNhapOTP.setBounds(335, 234, 275, 29);
		contentPane.add(txtNhapOTP);
		
		txtNhapOTP.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
//				tf_maNhanVien.setText("");
				
			}
		});
		
		
		
		txtNhạpMKM = new JTextField();
		txtNhạpMKM.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNhạpMKM.setForeground(new Color(0, 0, 0));
		txtNhạpMKM.setBackground(new Color(255, 255, 255));
		txtNhạpMKM.setText("Nhập mật khẩu mới");
		txtNhạpMKM.setColumns(10);
		txtNhạpMKM.setBounds(335, 299, 478, 32);
		contentPane.add(txtNhạpMKM);
		
		
		txtNhạpMKM.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				txtNhạpMKM.setText("");
				
			}
		});
		
		
		
		
		btnDoiMKM = new JButton("Đổi mật khẩu mới");
		btnDoiMKM.setForeground(new Color(249, 224, 187));
		btnDoiMKM.setBackground(new Color(17, 57, 70));
		
		btnDoiMKM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		txtNhaplaiMKM = new JTextField();
		txtNhaplaiMKM.setText("Nhập lại mật khẩu mới");
		txtNhaplaiMKM.setForeground(Color.BLACK);
		txtNhaplaiMKM.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNhaplaiMKM.setColumns(10);
		txtNhaplaiMKM.setBackground(Color.WHITE);
		txtNhaplaiMKM.setBounds(335, 363, 478, 32);
		contentPane.add(txtNhaplaiMKM);
		btnDoiMKM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDoiMKM.setBounds(335, 424, 478, 54);
		contentPane.add(btnDoiMKM);
		
		

		txtNhaplaiMKM.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				txtNhaplaiMKM.setText("");
				
			}
		});	
		
		JButton btnTrolai = new JButton("Trở lại đăng nhập");
		btnTrolai.setForeground(new Color(249, 224, 187));
		btnTrolai.setBackground(new Color(17, 57, 70));
		
		btnTrolai.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTrolai.setBounds(335, 506, 478, 54);
		btnTrolai.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_DangNhap gui_DangNhap = new GUI_DangNhap();
				gui_DangNhap.setVisible(true);
				dispose();
				
			}
		});
		
		btnXcNn = new JButton("Xác nhận");
		btnXcNn.setForeground(new Color(249, 224, 187));
		btnXcNn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnXcNn.setBackground(new Color(17, 57, 70));
		btnXcNn.setBounds(630, 230, 183, 32);
		contentPane.add(btnXcNn);
		contentPane.add(btnTrolai);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setIcon(new ImageIcon(GUI_QuenMKOTP.class.getResource("/imgs/box1.jpg")));
		lblNewLabel_2.setBounds(314, 106, 520, 493);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("l");
		lblNewLabel_4.setBackground(new Color(204, 0, 153));
		lblNewLabel_4.setIcon(new ImageIcon(GUI_QuenMKOTP.class.getResource("/imgs/main2_img.jpg")));
		lblNewLabel_4.setBounds(0, 0, 1143, 663);
		contentPane.add(lblNewLabel_4);
		
		txtCode = new JTextField();
		
		
		btnXcNn.addActionListener(this);
		btnDoiMKM.addActionListener(this);
		
		
	}
//	Ket thuc phan giao dien
	
	
//	Function tạo sinh mã ngẫu nhiên gồm 6 ký tự trong đó chứa ít nhất 1 chữ hoa , 1 chữ thường , 1 ký tự số và 1 ký tự đặc biệt
	private static String generateRandomCode() {
		SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()_+-=";

        // Chọn ngẫu nhiên một ký tự từ mỗi loại
        char randomUpper = uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length()));
        char randomLower = lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length()));
        char randomDigit = digits.charAt(random.nextInt(digits.length()));
        char randomSpecial = specialChars.charAt(random.nextInt(specialChars.length()));

        // Thêm ngẫu nhiên một ký tự từ mỗi loại
        sb.append(randomUpper);
        sb.append(randomLower);
        sb.append(randomDigit);
        sb.append(randomSpecial);

        // Thêm ngẫu nhiên hai ký tự còn lại
        for (int i = 0; i < 2; i++) {
            String allChars = uppercaseLetters + lowercaseLetters + digits + specialChars;
            char randomChar = allChars.charAt(random.nextInt(allChars.length()));
            sb.append(randomChar);
        }
        
        

        return sb.toString();
    }
	
	
	
	private static void sendCodeEmail(String codeRandom , String maNV , String emailNguoiNhan) {
		
		 	MyAuthenticator authenticator = new MyAuthenticator(EMAIL_USERNAME, EMAIL_PASSWORD);
		 	
		 	System.out.println(emailNguoiNhan);
		 	Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
			props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");


			Session session = Session.getInstance(props, authenticator);
			MimeMessage msg = new MimeMessage(session);
			
			try {
	            // Tạo đối tượng Message
	            Message message = new MimeMessage(session);
	            // Đặt người gửi
	            message.setFrom(new InternetAddress("hieusachtunhan.htnd@gmail.com"));
	            
	            
	            // Đặt người nhận
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailNguoiNhan));
	            // Đặt tiêu đề
	            message.setSubject("Hiệu Sách Tư Nhân HTND");

	            // Đặt nội dung của email
	            String emailContent = "Mật Khẩu Mới Của Bạn Là :" + codeRandom;
	            message.setText(emailContent);

	            // Gửi email
	            Transport.send(message);

	            System.out.println("Email đã được gửi thành công!");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }	    
	}
	    
	
	public boolean checkOtp(String otpInput) {
		System.out.println("OTP Hien Tai : " + otpInput);
		
		
		
		for (TaiKhoan taiKhoan : tk_dao.getAllTaiKhoan()) {
			System.out.println("OTP Trong DB" + taiKhoan.getOTP());
			if(taiKhoan.getOTP().trim().equals(otpInput)) {
				maNhanVien = taiKhoan.getMaNV();
				return true;
			}
		}		
		return false;
	}
    
	
	
  
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnXcNn)) {
			String otpInput = txtNhapOTP.getText().toString();
			result_checkOtp = checkOtp(otpInput);
			
			System.out.println(result_checkOtp);
			
			
			if(result_checkOtp == false) {
				JOptionPane.showMessageDialog(null, "Mã OTP Không Hợp Lệ", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
			}else if(result_checkOtp == true) {
				JOptionPane.showMessageDialog(null, "Mã OTP Hợp Lệ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		}else if(o.equals(btnDoiMKM)) {
			String tf_mkm = txtNhạpMKM.getText().toString();
			String tf_nhapLaiMkM = txtNhaplaiMKM.getText().toString();
			
			String otpInput = txtNhapOTP.getText().toString();
			result_checkOtp = checkOtp(otpInput);
			
			if(result_checkOtp == false) {
				JOptionPane.showMessageDialog(null, "Mã OTP Không Hợp Lệ", "Thông báo", JOptionPane.ERROR_MESSAGE);
			}else {
				if (tf_mkm.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Mật Khẩu Mới", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
				}else if(tf_nhapLaiMkM.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Lại Mật Khẩu Mới", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
				}else if(!tf_mkm.trim().equals(tf_nhapLaiMkM)) {
					JOptionPane.showMessageDialog(null, "Mật Khẩu Mới Không Khớp", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Đổi Mật Khẩu Thành Công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					tk_dao.doiMatKhau(maNhanVien, tf_mkm);
					GUI_DangNhap guiDN = new GUI_DangNhap();
					guiDN.setVisible(true);
					guiDN.setLocationRelativeTo(null);
					dispose();
				}
			}
			
			
			
		}
		
		
		
		
		
	}
}
