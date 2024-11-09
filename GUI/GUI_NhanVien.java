 package GUI;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.Toolkit;
import com.toedter.calendar.JDateChooser;

import ConnectDB.ConnectDB;
import DAO.NhanVien_Dao;
import DAO.TaiKhoan_Dao;
import entity.NhanVien;
import entity.TaiKhoan;


public class GUI_NhanVien extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMaNv;
	private JTable table_nv;
	private JTextField textField_4;
	private JTextField txtHoTen;
	private JTextField txtDiaChi;
	private JTextField txtSdt;
	private JTextField txtChucVu , txtCCCD , txtLyDo;
	JButton btnThem , btnXoa , btnSua , btn_XemttNV,btnLammoi , btnSearch , btnThangChuc;
	JRadioButton rdbtn_nam , rdbtn_nu , rdbtn_ngungHd , rdbtn_dangHd;
	JDateChooser dateChooser;
	private NhanVien_Dao nv_Dao = new NhanVien_Dao();
	private List<NhanVien> dsnv = new ArrayList<NhanVien>();
	private DefaultTableModel DataModel;
	private List<NhanVien> dsnvDangHoatDong;
	private List<TaiKhoan> dstk = new ArrayList<>();
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	private JFrame frame = new JFrame();
	
	private String maNV = "USER";
	private String chucVu = "NhanVien";
	private String hoTenNhanVien = "User Name";
	
	private DefaultComboBoxModel<String> cboModelTK;
	private JComboBox<String> comboBox;
	private boolean trangThai;
	private boolean gioitinh;
	private JTextField tf_email;
	private int idNhanVien;
	
	
//	Button group 2 la btn group cho gioi tinh , btnGroup group theo trang thai
	private ButtonGroup buttonGroup , buttonGroup_2;
	
	/**
	 * Launch the application.
	 */
	
	
	

	/**
	 * Create the frame.
	 */
	public GUI_NhanVien() {
		
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		dstk = tk_dao.getAllTaiKhoan();
		for (TaiKhoan taiKhoan : dstk) {
			if(taiKhoan.isTrangThai() == true) {
				maNV  = taiKhoan.getMaNV();
				chucVu = taiKhoan.getChucVu();
				hoTenNhanVien = taiKhoan.getHoTenNhanVien();
			}
		}

		
//		Sự kiện đóng chương trình KHI ấn nút x để thoát chương trình thì nó sẽ tự động đăng xuất
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                	tk_dao.dangXuat(maNV);
                    System.exit(0);
                }
            }
        });
		
		
		
		this.addKeyListener(new KeyAdapter() {
			 public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_F1) { //F1: Khách Hàng  
	                	GUI_KhacHang guiKhachHang = new GUI_KhacHang();
	               	 	guiKhachHang.setVisible(true);
	               	 	dispose();
	               	 	
	                }else if(e.getKeyCode() == KeyEvent.VK_F2) { //F2 : Sách 
	                	GUI_Sach guiSach = new GUI_Sach();
	                	guiSach.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F3) { //F3 : Tác Giả 
	                	GUI_TacGia guiTacGia = new GUI_TacGia();
	                	guiTacGia.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F4) { //F4 : Nhà Xuất Bản 
	                	GUI_NhaXuatBan guiNXB = new GUI_NhaXuatBan();
	                	guiNXB.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F5) { //F5 : Thể Loại 
	                	GUI_TheLoai theLoai = new GUI_TheLoai();
	                	theLoai.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F6) { //F6 : Tạo Hóa Đơn 
	                	GUI_TaoHoaDon guiTaoHD = new GUI_TaoHoaDon();
	                	guiTaoHD.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F7) { //F7 : Xem Chi Tiết Hóa Đơn
	                	GUI_XemChiTietHoaDon guiChiTietHD = new GUI_XemChiTietHoaDon();
	                	guiChiTietHD.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F8) { //F8 : Quản Lý Nhân Viên 
	                	GUI_NhanVien guiNhanVien = new GUI_NhanVien();
	                	guiNhanVien.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F9) { //F9 : Xem Thông Tin Nhân Viên Bị Xóa 
	                	GUI_XemTTNVbixoa guiNhanVienBiXoa = new GUI_XemTTNVbixoa();
	                	guiNhanVienBiXoa.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F10) { //F10 : Nhà Cung Cấp 
	                	GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
	                	guiNCC.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F11) { //F11 : Hướng Dẫn Sử Dụng 
	                	try {
							Desktop.getDesktop().browse(new URL("https://hieunhan1919.github.io/ptudwebhelp/").toURI());
						} catch (IOException | URISyntaxException e1) {
							e1.printStackTrace();
						}
	                	
//	                	dispose();
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F12) { //F12 Đổi Mật Khẩu
	                	GUI_DoiMK guiDoiMK = new GUI_DoiMK();
	                	guiDoiMK.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                	GUI_TrangChu guiTrangChu = new GUI_TrangChu();
	                	guiTrangChu.setVisible(true);
	                	dispose();
	                }
	         }
			
		});
		
		

		this.setFocusable(true); // Để bắt sự kiện từ frame
		this.requestFocus(); // Tập trung vào frame để nhận sự kiện từ bàn phím
		
		
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_NhanVien.class.getResource("/imgs/qls.png")));
		setTitle("Quản lý nhân viên");
		setBounds(100, 100, 1170, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//		Date calendar
		dateChooser = new JDateChooser();
		dateChooser.setBounds(248, 242, 239, 35);
		contentPane.add(dateChooser);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(17, 57, 70));
		panel.setBounds(0, 0, 157, 75);
		contentPane.add(panel);
		
		 JLabel lblNewLabel_1 = new JLabel("");
		 panel.add(lblNewLabel_1);
		 lblNewLabel_1.setForeground(new Color(255, 255, 255));
		 lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		 lblNewLabel_1.setBackground(new Color(17, 57, 70));
		 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel = new JLabel("Quản lý nhân viên");
		lblNewLabel.setForeground(new Color(234, 198, 150));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBounds(408, 0, 387, 68);
		contentPane.add(lblNewLabel);
		
		JButton btnKhachHang = new JButton("Khách hàng");
		btnKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
		btnKhachHang.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/customer1.png")));
		btnKhachHang.setForeground(new Color(255, 255, 255));
		btnKhachHang.setBackground(new Color(17, 57, 70));
		btnKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnKhachHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
			}
		});
		btnKhachHang.setBounds(0, 73, 157, 58);
		btnKhachHang.setContentAreaFilled(false);
		btnKhachHang.setFocusPainted(false);
		btnKhachHang.setOpaque(true);
		contentPane.add(btnKhachHang);
		
		btnKhachHang.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnKhachHang.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnKhachHang.setBackground(new Color(17, 57, 70));
		    }
		});
		
		
	       JPopupMenu popupMenu = new JPopupMenu();
	       JMenuItem menuItemThem = new JMenuItem("QL Khách Hàng (F1)");
	        menuItemThem.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/loyal-customer.png")));
	        menuItemThem.setForeground(new Color(255, 255, 255)); // Set text color
	        menuItemThem.setBackground(new Color(136, 74, 57)); // Set background color
	        menuItemThem.setFont(new Font("Tahoma", Font.PLAIN, 12)); // Set font
	        menuItemThem.setPreferredSize(new Dimension(157, 58));
	        menuItemThem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GUI_KhacHang guiKH = new GUI_KhacHang();
					guiKH.setVisible(true);
		            dispose();

					
				}
			});
	        popupMenu.add(menuItemThem);
	        
//	        JMenuItem menuItemCapNhat = new JMenuItem("Cập nhật khách hàng");
//	        menuItemCapNhat.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/updated_img.png")));
//	        menuItemCapNhat.setForeground(new Color(255, 255, 255));
//	        menuItemCapNhat.setBackground(new Color(136, 74, 57));
//	        menuItemCapNhat.setFont(new Font("Tahoma", Font.PLAIN, 12));
//	        menuItemCapNhat.setPreferredSize(new Dimension(163, 58));
//	        menuItemCapNhat.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					GUI_KhacHang guiKH = new GUI_KhacHang();
//					guiKH.setVisible(true);
//		            dispose();
//
//				}
//			});
//	        popupMenu.add(menuItemCapNhat);
//	        
//	        JMenuItem menuItemTimKiem = new JMenuItem("Tìm khách hàng");
//	        menuItemTimKiem.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/loupe_img.png")));
//	        menuItemTimKiem.setForeground(new Color(255, 255, 255));
//	        menuItemTimKiem.setBackground(new Color(136, 74, 57));
//	        menuItemTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
//	        menuItemTimKiem.setPreferredSize(new Dimension(163, 58));
//	        menuItemTimKiem.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					GUI_KhacHang guiKH = new GUI_KhacHang();
//					guiKH.setVisible(true);
//		            dispose();
//
//				}
//			});
//	        popupMenu.add(menuItemTimKiem);



	        btnKhachHang.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Show the pop-up menu when the "Khách hàng" button is clicked
	                popupMenu.show(btnKhachHang, 0, btnKhachHang.getHeight());
	            }
	        });

	        menuItemThem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Add your logic for "Thêm" here
	            }
	        });

//	        menuItemCapNhat.addActionListener(new ActionListener() {
//	            public void actionPerformed(ActionEvent e) {
//	                // Add your logic for "Cập nhật" here
//	            }
//	        });
//
//	        menuItemTimKiem.addActionListener(new ActionListener() {
//	            public void actionPerformed(ActionEvent e) {
//	                // Add your logic for "tìm kiếm" here
//	            }
//	        });
		
	        JButton btnSach = new JButton("Sách\r\n");
	        btnSach.setHorizontalAlignment(SwingConstants.LEADING);
			btnSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/sach_img.png")));
			btnSach.setForeground(new Color(255, 255, 255));
			btnSach.setBackground(new Color(17, 57, 70));
			btnSach.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnSach.setBounds(156, 73, 166, 58);
			btnSach.setContentAreaFilled(false);
			btnSach.setFocusPainted(false);
			btnSach.setOpaque(true);
			contentPane.add(btnSach);
			
			btnSach.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseEntered(MouseEvent e) {
			    	btnSach.setBackground(new Color(195,129,84));
			    }

			    @Override
			    public void mouseExited(MouseEvent e) {
			    	btnSach.setBackground(new Color(17, 57, 70));
			    }
			});
			
			
		     JPopupMenu popupMenuSach = new JPopupMenu();
		        JMenuItem menuItemThemSach = new JMenuItem("QL Sách (F2)");
		        menuItemThemSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/execution.png")));
		        menuItemThemSach.setForeground(new Color(255, 255, 255)); 
		        menuItemThemSach.setBackground(new Color(136, 74, 57)); 
		        menuItemThemSach.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
		        menuItemThemSach.setPreferredSize(new Dimension(165, 58));
		        menuItemThemSach.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GUI_Sach gui_Sach = new GUI_Sach();
						gui_Sach.setVisible(true);
			            dispose();

					}
				});
		        popupMenuSach.add(menuItemThemSach);
		        
		        JMenuItem menuItemThemTg = new JMenuItem("QL Tác giả (F3)");
		        menuItemThemTg.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/shakespeare.png")));
		        menuItemThemTg.setForeground(new Color(255, 255, 255)); 
		        menuItemThemTg.setBackground(new Color(136, 74, 57));
		        menuItemThemTg.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
		        menuItemThemTg.setPreferredSize(new Dimension(165, 58));
		        menuItemThemTg.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GUI_TacGia gui_Tg = new GUI_TacGia();
						gui_Tg.setVisible(true);
			            dispose();

					}
				});
		        popupMenuSach.add(menuItemThemTg);
		        
		        JMenuItem menuItemThemNXB = new JMenuItem("QL Nhà Xuất Bản (F4)");
		        menuItemThemNXB.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/publisher.png")));
		        menuItemThemNXB.setForeground(new Color(255, 255, 255)); 
		        menuItemThemNXB.setBackground(new Color(136, 74, 57)); 
		        menuItemThemNXB.setFont(new Font("Tahoma", Font.PLAIN, 12)); 
		        menuItemThemNXB.setPreferredSize(new Dimension(165, 58));
		        menuItemThemNXB.addActionListener(new ActionListener() {
		  				public void actionPerformed(ActionEvent e) {
		  					GUI_NhaXuatBan gui_NXB = new GUI_NhaXuatBan();
		  					gui_NXB.setVisible(true);
				            dispose();

		  				}
		  			});
		        popupMenuSach.add(menuItemThemNXB);
		        
		        JMenuItem menuItemThemTL = new JMenuItem("QL Thể Loại (F5)");
		        menuItemThemTL.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/TLBook.png")));
		        menuItemThemTL.setForeground(new Color(255, 255, 255)); 
		        menuItemThemTL.setBackground(new Color(136, 74, 57)); 
		        menuItemThemTL.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
		        menuItemThemTL.setPreferredSize(new Dimension(157, 58));
		        menuItemThemTL.addActionListener(new ActionListener() {
		  				public void actionPerformed(ActionEvent e) {
		  					GUI_TheLoai gui_TheLoai = new GUI_TheLoai();
		  					gui_TheLoai.setVisible(true);
				            dispose();

		  				}
		  			});
		        popupMenuSach.add(menuItemThemTL);
		        
		        btnSach.addActionListener(new ActionListener() {
		        	  public void actionPerformed(ActionEvent e) {
			                
		        		  popupMenuSach.show(btnSach, 0, btnSach.getHeight());
			            }
			        });
		        menuItemThemSach.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		               
		            }
		        });
		        popupMenuSach.add(menuItemThemSach);

		      
		       
		        menuItemThemTg.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		              
		            }
		        });
		        popupMenuSach.add(menuItemThemTg);

		      
		        
		        menuItemThemNXB.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            
		            }
		        });
		        popupMenuSach.add(menuItemThemNXB);

		       
		       
		        menuItemThemTL.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		             
		            }
		        });
		        popupMenuSach.add(menuItemThemTL);

		       
		        btnSach.setComponentPopupMenu(popupMenuSach);
	   

		
		
		
		JButton btnHoaDon = new JButton("Hóa đơn");
		btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
		btnHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/bill_imng.png")));
		btnHoaDon.setForeground(new Color(255, 255, 255));
		btnHoaDon.setBackground(new Color(17, 57, 70));
		btnHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHoaDon.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHoaDon.setBounds(322, 73, 157, 58);
		btnHoaDon.setContentAreaFilled(false);
		btnHoaDon.setFocusPainted(false);
		btnHoaDon.setOpaque(true);
		contentPane.add(btnHoaDon);
		
		btnHoaDon.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnHoaDon.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnHoaDon.setBackground(new Color(17, 57, 70));
		    }
		});
		
		JPopupMenu popupMenuHoaDon = new JPopupMenu();
		JMenuItem menuItemTaoHoaDon = new JMenuItem("Tạo hóa đơn (F6)");
		menuItemTaoHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/bill.png")));
		menuItemTaoHoaDon.setForeground(new Color(255, 255, 255));
		menuItemTaoHoaDon.setBackground(new Color(136, 74, 57));
		menuItemTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemTaoHoaDon.setPreferredSize(new Dimension(155, 58));
		menuItemTaoHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_TaoHoaDon guiHoaDon = new GUI_TaoHoaDon();
				guiHoaDon.setVisible(true);
				dispose();
				
			}
		});
		popupMenuHoaDon.add(menuItemTaoHoaDon);

		
		JMenuItem menuItemXemChiTietHoaDon = new JMenuItem("Xem chi tiết hóa đơn");
		menuItemXemChiTietHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/Xembill.png")));
		menuItemXemChiTietHoaDon.setForeground(new Color(255, 255, 255));
		menuItemXemChiTietHoaDon.setBackground(new Color(136, 74, 57));
		menuItemXemChiTietHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemXemChiTietHoaDon.setPreferredSize(new Dimension(155, 58));
		menuItemXemChiTietHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_XemChiTietHoaDon guiqlyHoaDon = new GUI_XemChiTietHoaDon();
				guiqlyHoaDon.setVisible(true);
				dispose();
				
			}
		});
		popupMenuHoaDon.add(menuItemXemChiTietHoaDon);

	
		btnHoaDon.setComponentPopupMenu(popupMenuHoaDon);


		btnHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		     
		        popupMenuHoaDon.show(btnHoaDon, 0, btnHoaDon.getHeight());
		    }
		});

		menuItemTaoHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		      
		    }
		});

		menuItemXemChiTietHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});
		
		JButton btnNhanVIen = new JButton("Nhân viên");
		btnNhanVIen.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/management.png")));
		btnNhanVIen.setForeground(new Color(255, 255, 255));
		btnNhanVIen.setBackground(new Color(17, 57, 70));
		btnNhanVIen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNhanVIen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				GUI_NhanVien guiNV = new GUI_NhanVien();
//				guiNV.setVisible(true);
//				dispose();
				
			}
		});
		btnNhanVIen.setBounds(478, 73, 165, 58);
		btnNhanVIen.setContentAreaFilled(false);
		btnNhanVIen.setFocusPainted(false);
		btnNhanVIen.setOpaque(true);
		contentPane.add(btnNhanVIen);
		
		
		btnNhanVIen.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnNhanVIen.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnNhanVIen.setBackground(new Color(17, 57, 70));
		    }
		});
		
	
		JPopupMenu popupMenuNhanVien = new JPopupMenu();
		JMenuItem menuItemThemNhanVien = new JMenuItem("QL Nhân Viên (F8)");
		menuItemThemNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/recruitment.png")));
		menuItemThemNhanVien.setForeground(new Color(255, 255, 255));
		menuItemThemNhanVien.setBackground(new Color(136, 74, 57));
		menuItemThemNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemThemNhanVien.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemThemNhanVien);

		
//		JMenuItem menuItemCapNhatNhanVien = new JMenuItem("Cập nhật nhân viên");
//		menuItemCapNhatNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
//		menuItemCapNhatNhanVien.setForeground(new Color(255, 255, 255));
//		menuItemCapNhatNhanVien.setBackground(new Color(136, 74, 57));
//		menuItemCapNhatNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		menuItemCapNhatNhanVien.setPreferredSize(new Dimension(163, 58));
//		popupMenuNhanVien.add(menuItemCapNhatNhanVien);
//
//		
//		JMenuItem menuItemTimKiemNhanVien = new JMenuItem("Tìm nhân viên");
//		menuItemTimKiemNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
//		menuItemTimKiemNhanVien.setForeground(new Color(255, 255, 255));
//		menuItemTimKiemNhanVien.setBackground(new Color(136, 74, 57));
//		menuItemTimKiemNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemTimKiemNhanVien.setPreferredSize(new Dimension(163, 58));
//		popupMenuNhanVien.add(menuItemTimKiemNhanVien);
//
//		
//		JMenuItem menuItemXoaNhanVien = new JMenuItem("Xóa nhân viên");
//		menuItemXoaNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/delete_img.png")));
//		menuItemXoaNhanVien.setForeground(new Color(255, 255, 255));
//		menuItemXoaNhanVien.setBackground(new Color(136, 74, 57));
//		menuItemXoaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemXoaNhanVien.setPreferredSize(new Dimension(163, 58));
//		popupMenuNhanVien.add(menuItemXoaNhanVien);

	
		JMenuItem menuItemXemNhanVienBiXoa = new JMenuItem("Xem nhân viên bị xóa");
		menuItemXemNhanVienBiXoa.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/personal-information.png")));
		menuItemXemNhanVienBiXoa.setForeground(new Color(255, 255, 255));
		menuItemXemNhanVienBiXoa.setBackground(new Color(136, 74, 57));
		menuItemXemNhanVienBiXoa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemXemNhanVienBiXoa.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemXemNhanVienBiXoa);

		menuItemXemNhanVienBiXoa.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Tạo một thể hiện của GUI_NhanVien
		        GUI_XemTTNVbixoa guiXemTTNVbixoa = new GUI_XemTTNVbixoa();
		        
		        // Hiển thị giao diện mới
		        guiXemTTNVbixoa.setVisible(true);
		        
		        // Ẩn giao diện hiện tại 
		        dispose();

		    }
		});
		btnNhanVIen.setComponentPopupMenu(popupMenuNhanVien);

		
		btnNhanVIen.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        popupMenuNhanVien.show(btnNhanVIen, 0, btnNhanVIen.getHeight());
		    }
		});

		menuItemThemNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		    }
		});

//		menuItemCapNhatNhanVien.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		        
//		    }
//		});
//
//		menuItemTimKiemNhanVien.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		 
//		    }
//		});
//
//		menuItemXoaNhanVien.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		    
//		    }
//		});

		menuItemXemNhanVienBiXoa.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		  
		    }
		});
		
		JButton btnNhaCungCap = new JButton("Nhà cung cấp");
		btnNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhaCungCap.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/supplier.png")));
		btnNhaCungCap.setForeground(new Color(255, 255, 255));
		btnNhaCungCap.setBackground(new Color(17, 57, 70));
		btnNhaCungCap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNhaCungCap.setBounds(642, 73, 157, 58);
		btnNhaCungCap.setContentAreaFilled(false);
		btnNhaCungCap.setFocusPainted(false);
		btnNhaCungCap.setOpaque(true);
		
		btnNhaCungCap.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
					guiNCC.setVisible(true);
					dispose();
				}
				
			}
		});
		
		
		contentPane.add(btnNhaCungCap);
		
		btnNhaCungCap.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnNhaCungCap.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnNhaCungCap.setBackground(new Color(17, 57, 70));
		    }
		});
		
		
		
		JPopupMenu popupMenuNCC = new JPopupMenu();
		JMenuItem menuItemThemNCC = new JMenuItem("QL NCC (F10)");
		menuItemThemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemThemNCC.setForeground(new Color(255, 255, 255));
		menuItemThemNCC.setBackground(new Color(136, 74, 57));
		menuItemThemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemThemNCC.setPreferredSize(new Dimension(163, 58));
		menuItemThemNCC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
					guiNCC.setVisible(true);
					dispose();
				}
				
			}
		});
		popupMenuNCC.add(menuItemThemNCC);

		
//		JMenuItem menuItemCapNhatNCC = new JMenuItem("Cập nhật NCC");
//		menuItemCapNhatNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
//		menuItemCapNhatNCC.setForeground(new Color(255, 255, 255));
//		menuItemCapNhatNCC.setBackground(new Color(136, 74, 57));
//		menuItemCapNhatNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemCapNhatNCC.setPreferredSize(new Dimension(163, 58));
//		menuItemCapNhatNCC.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GUI_NhaCungCap guiNhaCungCap = new GUI_NhaCungCap();
//				guiNhaCungCap.setVisible(true);
//				dispose();
//				
//			}
//		});
//		popupMenuNCC.add(menuItemCapNhatNCC);
//
//	
//		JMenuItem menuItemTimKiemNCC = new JMenuItem("Tìm NCC");
//		menuItemTimKiemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
//		menuItemTimKiemNCC.setForeground(new Color(255, 255, 255));
//		menuItemTimKiemNCC.setBackground(new Color(136, 74, 57));
//		menuItemTimKiemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemTimKiemNCC.setPreferredSize(new Dimension(163, 58));
//		menuItemTimKiemNCC.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GUI_NhaCungCap guiNhaCungCap = new GUI_NhaCungCap();
//				guiNhaCungCap.setVisible(true);
//				dispose();
//				
//			}
//		});
//		popupMenuNCC.add(menuItemTimKiemNCC);


		
		btnNhaCungCap.setComponentPopupMenu(popupMenuNCC);

	
		btnNhaCungCap.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        popupMenuNCC.show(btnNhaCungCap, 0, btnNhaCungCap.getHeight());
		    }
		});

		menuItemThemNCC.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

//		menuItemCapNhatNCC.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		   
//		    }
//		});
//
//		menuItemTimKiemNCC.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		     
//		    }
//		});
		
		JButton btnTroGiup = new JButton("Trợ giúp");
		btnTroGiup.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/question-mark.png")));
		btnTroGiup.setForeground(new Color(255, 255, 255));
		btnTroGiup.setBackground(new Color(17, 57, 70));
		btnTroGiup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTroGiup.setBounds(796, 73, 180, 58);
		btnTroGiup.setContentAreaFilled(false);
		btnTroGiup.setFocusPainted(false);
		btnTroGiup.setOpaque(true);
		contentPane.add(btnTroGiup);
		
		btnTroGiup.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnTroGiup.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnTroGiup.setBackground(new Color(17, 57, 70));
		    }
		});
		
		JPopupMenu popupMenuTrGip = new JPopupMenu();

	
		JMenuItem menuItemHuongDan = new JMenuItem("Hướng dẫn sử dụng (F11)");
		menuItemHuongDan.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/guide.png")));
		menuItemHuongDan.setForeground(new Color(255, 255, 255));
		menuItemHuongDan.setBackground(new Color(136, 74, 57));
		menuItemHuongDan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemHuongDan.setPreferredSize(new Dimension(178, 58));
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
		
		JMenuItem menuItemDoiMatKhau = new JMenuItem("Đổi mật khẩu (F12)");
		menuItemDoiMatKhau.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/reset-password.png")));
		menuItemDoiMatKhau.setForeground(new Color(255, 255, 255));
		menuItemDoiMatKhau.setBackground(new Color(136, 74, 57));
		menuItemDoiMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemDoiMatKhau.setPreferredSize(new Dimension(178, 58));
		menuItemDoiMatKhau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_DoiMK guiDMK = new GUI_DoiMK();
				guiDMK.setVisible(true);
				dispose();
				
			}
		});
		popupMenuTrGip.add(menuItemDoiMatKhau);

		
		btnTroGiup.setComponentPopupMenu(popupMenuTrGip);

	
		btnTroGiup.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		        popupMenuTrGip.show(btnTroGiup, 0, btnTroGiup.getHeight());
		    }
		});

		menuItemHuongDan.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

		menuItemDoiMatKhau.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		
		    }
		});
		
		JButton btnThoat = new JButton("Trở về trang chủ");
		btnThoat.setHorizontalAlignment(SwingConstants.LEFT);
		btnThoat.setIcon(new ImageIcon(GUI_TrangChu.class.getResource("/imgs/exit_img.png")));
		btnThoat.setForeground(new Color(255, 255, 255));
		btnThoat.setBackground(new Color(17, 57, 70));
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThoat.setBounds(976, 73, 210, 58);
		btnThoat.setContentAreaFilled(false);
		btnThoat.setFocusPainted(false);
		btnThoat.setOpaque(true);
		contentPane.add(btnThoat);
		
		btnThoat.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnThoat.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnThoat.setBackground(new Color(17, 57, 70));
		    }
		});
		
		btnThoat.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Đóng giao diện hiện tại (GUI_NhanVien)
		        dispose();

		        // Hiển thị lại giao diện GUI_TrangChu
		        GUI_TrangChu guiTrangChu = new GUI_TrangChu();
		        guiTrangChu.setVisible(true);
		    }
		});
		
		JButton btnDangXuat = new JButton("Đăng xuất");
		btnDangXuat.setHorizontalAlignment(SwingConstants.LEFT);
		btnDangXuat.setIcon(new ImageIcon(GUI_TrangChu.class.getResource("/imgs/logout_img.png")));
		btnDangXuat.setForeground(new Color(255, 255, 255));
		btnDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDangXuat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDangXuat.setBackground(new Color(17, 57, 70));
		btnDangXuat.setBounds(976, 35, 179, 40);
		btnDangXuat.setContentAreaFilled(false);
		btnDangXuat.setFocusPainted(false);
		btnDangXuat.setOpaque(true);
		contentPane.add(btnDangXuat);
		
		btnDangXuat.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnDangXuat.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnDangXuat.setBackground(new Color(17, 57, 70));
		    }
		});

		btnDangXuat.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất!", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		        	GUI_DangNhap lg = new GUI_DangNhap();
		            lg.setVisible(true);
		            tk_dao.dangXuat(maNV);
		            dispose();
		        }
		    }
		});
		
		JPanel pnUser = new JPanel();
		pnUser.setBackground(new Color(17, 57, 70));
		pnUser.setBounds(976, 0, 179, 35);
		contentPane.add(pnUser);
		pnUser.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(107, 11, 0, 0);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblNewLabel_1_1.setBackground(new Color(17, 57, 70));
		pnUser.add(lblNewLabel_1_1);
		
		JLabel lb_chucVu = new JLabel("Chức Vụ :");
		lb_chucVu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lb_chucVu.setForeground(new Color(255, 255, 255));
		lb_chucVu.setBounds(29, 19, 78, 13);
		pnUser.add(lb_chucVu);
		
		JLabel lb_chucVuCuaNhanVien = new JLabel(chucVu);
		lb_chucVuCuaNhanVien.setForeground(new Color(255, 255, 255));
		lb_chucVuCuaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lb_chucVuCuaNhanVien.setBounds(89, 20, 59, 13);
		pnUser.add(lb_chucVuCuaNhanVien);
		
		JLabel lbUserName = new JLabel(hoTenNhanVien);
		lbUserName.setForeground(new Color(255, 255, 255));
		lbUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbUserName.setBounds(89, 2, 101, 13);
		pnUser.add(lbUserName);
		
		JLabel lblUser = new JLabel(maNV);
		lblUser.setIcon(new ImageIcon(GUI_KhacHang.class.getResource("/imgs/user_img.png")));
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUser.setForeground(new Color(255, 255, 255));
		lblUser.setBounds(0, 0, 108, 17);
		pnUser.add(lblUser);

	     Timer timer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	                lblNewLabel_1.setText("" + sdf.format(new Date()));
	            }
	        });
	        timer.start();
	   
	        this.setVisible(true);
	        this.setLocationRelativeTo(null);
		
		
	        ////////////////////////Body
	 	   

			
			
			JLabel lblMaNv = new JLabel("Mã Nhân Viên:");
			lblMaNv.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblMaNv.setForeground(new Color(234, 198, 150));
			lblMaNv.setBounds(90, 141, 151, 44);
			contentPane.add(lblMaNv);
			
			JLabel lblHoTen = new JLabel("Họ tên nhân viên:");
			lblHoTen.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblHoTen.setForeground(new Color(234, 198, 150));
			lblHoTen.setBounds(90, 188, 165, 44);
			contentPane.add(lblHoTen);
			
			JLabel lblSdt = new JLabel("SĐT:");
			lblSdt.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblSdt.setForeground(new Color(234, 198, 150));
			lblSdt.setBounds(666, 147, 73, 32);
			contentPane.add(lblSdt);
			
			JLabel lblChucVu = new JLabel("Chức vụ:");
			lblChucVu.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblChucVu.setForeground(new Color(234, 198, 150));
			lblChucVu.setBounds(657, 201, 95, 38);
			contentPane.add(lblChucVu);
			
			txtMaNv = new JTextField();
			txtMaNv.setEditable(false);
			idNhanVien = getIDNhanVienDaCo();
			
			if(idNhanVien < 9) {
				txtMaNv.setText("NV00" + idNhanVien);
			}else if(idNhanVien > 9 && idNhanVien < 99) {
				txtMaNv.setText("NV0" + idNhanVien);
			}else if(idNhanVien >= 100) {
				txtMaNv.setText("NV" + idNhanVien);
			}
			
			txtMaNv.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtMaNv.setBounds(248, 146, 239, 32);
			contentPane.add(txtMaNv);
			txtMaNv.setColumns(10);
			
			JLabel lblTinhTrang = new JLabel("Tình trạng:");
			lblTinhTrang.setForeground(new Color(234, 198, 150));
			lblTinhTrang.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblTinhTrang.setBounds(90, 349, 105, 32);
			contentPane.add(lblTinhTrang);
			

			buttonGroup = new ButtonGroup(); 
	        rdbtn_dangHd = new JRadioButton("Đang hoạt động");
	        rdbtn_dangHd.setSelected(true);
	        rdbtn_dangHd.setToolTipText("11");
	        rdbtn_dangHd.setOpaque(false);
	        rdbtn_dangHd.setForeground(new Color(234, 198, 150));
	        rdbtn_dangHd.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        rdbtn_dangHd.setBounds(227, 349, 165, 32);
	        contentPane.add(rdbtn_dangHd);
	        buttonGroup.add(rdbtn_dangHd); 

	        rdbtn_ngungHd = new JRadioButton("Ngưng hoạt động");
	        rdbtn_ngungHd.setOpaque(false);
	        rdbtn_ngungHd.setForeground(new Color(234, 198, 150));
	        rdbtn_ngungHd.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        rdbtn_ngungHd.setBounds(408, 350, 187, 31);
	        contentPane.add(rdbtn_ngungHd);
	        buttonGroup.add(rdbtn_ngungHd); 
			
	       
	        rdbtn_dangHd.setEnabled(false);
	        rdbtn_ngungHd.setEnabled(false);
	        rdbtn_dangHd.setSelected(true);
	        
	        
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 486, 1161, 97);
			String[] headers = "Mã NV; Tên NV; Ngày sinh; Địa Chỉ; Tinh Trạng; SĐT; Chức vụ; Email ; Giới tính ; CCCD".split(";");
			DataModel = new DefaultTableModel (headers,0);
			scrollPane.setViewportView(table_nv = new JTable(DataModel));
			table_nv.setRowHeight(25);
			table_nv.setAutoCreateRowSorter(true);
			table_nv.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			contentPane.add(scrollPane);
			
			
			table_nv.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int row = table_nv.getSelectedRow();
					assert row >= 0;
					napNhanVienVaoTextfields();
				}
			});
			
			
			
			btn_XemttNV = new JButton("Xem thông tin nhân viên bị xóa");
			btn_XemttNV.setBackground(new Color(17, 57, 70));
			btn_XemttNV.setForeground(new Color(234, 198, 150));
			btn_XemttNV.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btn_XemttNV.setBounds(17, 593, 314, 44);
			contentPane.add(btn_XemttNV);
			
			btn_XemttNV.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        // Tạo một thể hiện của GUI_NhanVien
			        GUI_XemTTNVbixoa guiXemTTNVbixoa = new GUI_XemTTNVbixoa();
			        
			        // Ẩn giao diện hiện tại 

			        // Hiển thị giao diện mới
			        guiXemTTNVbixoa.setVisible(true);
			        dispose();
			    }
			});
			
			cboModelTK = new DefaultComboBoxModel<String>();
			comboBox = new JComboBox<String>(cboModelTK);
			comboBox.setForeground(new Color(249, 224, 187));
			comboBox.setBackground(new Color(17, 57, 70));
			comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
			comboBox.setBounds(690, 593, 135, 44);
			contentPane.add(comboBox);
			updateJComboBox();
			
			textField_4 = new JTextField();
			textField_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
			textField_4.setBounds(824, 593, 142, 44);
			contentPane.add(textField_4);
			textField_4.setColumns(10);
			
			btnSearch = new JButton("");
			btnSearch.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/search_img.png")));
			btnSearch.setBackground(new Color(17, 57, 70));
			btnSearch.setBounds(965, 593, 45, 44);
			contentPane.add(btnSearch);
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Object o = e.getSource();
					if (o.equals(btnSearch) || o.equals(textField_4)) {
						xoaBang();
						if (comboBox.getSelectedIndex() == 0)
							timTheoMa();

						if (comboBox.getSelectedIndex() == 1)
							try {
								if (textField_4.getText().matches("( *)")) {
									updateTable();
									JOptionPane.showMessageDialog(null, "Nhập tên cần tìm!");
									textField_4.setText("");
									textField_4.requestFocus();

								} else {
									timTheoTen();
								}

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						if (comboBox.getSelectedIndex() == 2)
							try {
								if (textField_4.getText().matches("( *)")) {
									updateTable();
									JOptionPane.showMessageDialog(null, "Nhập số điện thoại cần tìm!");
									textField_4.setText("");
									textField_4.requestFocus();

								} else {
									timTheoSoDT();
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						if (o.equals(comboBox)) {
							textField_4.selectAll();
							textField_4.requestFocus();
						}

					}
				}
			});
			
			btnLammoi = new JButton("Làm mới");
			 btnLammoi.setForeground(new Color(249, 224, 187));
			 btnLammoi.setFont(new Font("Tahoma", Font.PLAIN, 14));
			 btnLammoi.setBackground(new Color(17, 57, 70));
			 btnLammoi.setBounds(420, 593, 133, 42);
			contentPane.add(btnLammoi);
						

			
			JLabel label = new JLabel("New label");
			label.setBounds(169, 72, 36, 32);
			contentPane.add(label);
			
			txtHoTen = new JTextField();
			txtHoTen.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtHoTen.setColumns(10);
			txtHoTen.setBounds(248, 195, 239, 32);
			contentPane.add(txtHoTen);
			
			txtDiaChi = new JTextField();
			txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtDiaChi.setColumns(10);
			txtDiaChi.setBounds(248, 293, 239, 32);
			contentPane.add(txtDiaChi);
			
			txtSdt = new JTextField();
			txtSdt.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtSdt.setColumns(10);
			txtSdt.setBounds(749, 147, 239, 32);
			contentPane.add(txtSdt);
			
			txtChucVu = new JTextField();
			txtChucVu.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtChucVu.setColumns(10);
			txtChucVu.setBounds(749, 204, 239, 32);
			txtChucVu.setEditable(false);
			contentPane.add(txtChucVu);
			txtChucVu.setText("Nhân Viên Bán Hàng");
			
			txtCCCD = new JTextField();
			txtCCCD.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtCCCD.setColumns(10);
			txtCCCD.setBounds(749, 315, 239, 32);
			contentPane.add(txtCCCD);
			
			JLabel lblGioitinh = new JLabel("Giới tính:");
			lblGioitinh.setForeground(new Color(234, 198, 150));
			lblGioitinh.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblGioitinh.setBounds(657, 255, 95, 38);
			contentPane.add(lblGioitinh);
			
			buttonGroup_2 = new ButtonGroup(); 
			
//			Radiobutton nam
			rdbtn_nam = new JRadioButton("Nam");
			rdbtn_nam.setToolTipText("11");
			rdbtn_nam.setFont(new Font("Tahoma", Font.PLAIN, 18));
			rdbtn_nam.setForeground(new Color(249, 224, 187));
			rdbtn_nam.setOpaque(false);
			rdbtn_nam.setBounds(769, 258, 87, 32);
			contentPane.add(rdbtn_nam);
			buttonGroup_2.add(rdbtn_nam); 
			
//			Radiobutton nu
			rdbtn_nu = new JRadioButton("Nữ");
			rdbtn_nu.setForeground(new Color(249, 224, 187));
			rdbtn_nu.setFont(new Font("Tahoma", Font.PLAIN, 18));
			rdbtn_nu.setOpaque(false);
			rdbtn_nu.setBounds(857, 258, 87, 32);
			contentPane.add(rdbtn_nu);
			buttonGroup_2.add(rdbtn_nu);
			
			JLabel lblNgaySinh = new JLabel("Ngày sinh:");
			lblNgaySinh.setForeground(new Color(234, 198, 150));
			lblNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNgaySinh.setBounds(90, 242, 165, 44);
			contentPane.add(lblNgaySinh);
			
			JLabel lblDiaChi = new JLabel("Địa chỉ:");
			lblDiaChi.setForeground(new Color(234, 198, 150));
			lblDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblDiaChi.setBounds(90, 291, 165, 44);
			contentPane.add(lblDiaChi);
			
			JLabel lblCCCD = new JLabel("CCCD:");
			lblCCCD.setForeground(new Color(234, 198, 150));
			lblCCCD.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblCCCD.setBounds(657, 312, 95, 38);
			contentPane.add(lblCCCD);
			
			btnThem = new JButton("Thêm");
			btnThem.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnThem.setForeground(new Color(249, 224, 187));
			btnThem.setBackground(new Color(17, 57, 70));
			btnThem.setBounds(191, 437, 132, 39);
			contentPane.add(btnThem);
			
			btnSua = new JButton("Sửa");
			btnSua.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnSua.setForeground(new Color(249, 224, 187));
			btnSua.setBackground(new Color(17, 57, 70));
			btnSua.setBounds(376, 437, 132, 39);
			contentPane.add(btnSua);
			
			btnXoa = new JButton("Xóa");
			btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnXoa.setForeground(new Color(249, 224, 187));
			btnXoa.setBackground(new Color(17, 57, 70));
			btnXoa.setBounds(562, 437, 132, 39);
			contentPane.add(btnXoa);
			
			JLabel lblEmail = new JLabel("Email:");
			lblEmail.setForeground(new Color(234, 198, 150));
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblEmail.setBounds(657, 374, 95, 38);
			contentPane.add(lblEmail);
			
			tf_email = new JTextField();
			tf_email.setFont(new Font("Tahoma", Font.PLAIN, 18));
			tf_email.setColumns(10);
			tf_email.setBounds(749, 377, 239, 32);
			contentPane.add(tf_email);
			
			btnThangChuc = new JButton("Thăng Chức");
			btnThangChuc.setForeground(new Color(249, 224, 187));
			btnThangChuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnThangChuc.setBackground(new Color(17, 57, 70));
			btnThangChuc.setBounds(749, 437, 132, 39);
			contentPane.add(btnThangChuc);
			
			
			
			JLabel lblNewLabel_4 = new JLabel("");
			lblNewLabel_4.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/gui_ncc.png")));
			lblNewLabel_4.setBounds(0, 0, 1161, 672);
			contentPane.add(lblNewLabel_4);
			
			
			
			btnThem.addActionListener(this);
			btnSua.addActionListener(this);
			btnXoa.addActionListener(this);
			btnThangChuc.addActionListener(this);
			btnLammoi.addActionListener(this);
			
			dsnvDangHoatDong = nv_Dao.getNhanVienDangHoatDong();
			loadDataToTable();
			
//			
		}
		

		/*
		 * Thực hiện sự kiện trên table nhân viên
		 */
		
		
	public void xoaAllDataTrongBang() {
		for (int i = 0 ; i < table_nv.getRowCount() ; i++) {
			DataModel.removeRow(i);
			i--;
		}
	}
	
	
	
//Function Load Data Tu CSDL len table	
	
		public void loadDataToTable() {
//			Lay Nhung dsnv Dang Hoat Dong Them Vao Bang
			dsnvDangHoatDong = nv_Dao.getNhanVienDangHoatDong();
			for (NhanVien dsNhanVien : dsnvDangHoatDong) {
				
				String maNV = dsNhanVien.getMaNV();
				String hoTenNV = dsNhanVien.getHoTenNV();
				Date ngaySinh = dsNhanVien.getNgaySinh();
				String diaChi = dsNhanVien.getDiaChi();
				String sdt = dsNhanVien.getSdt();
				String chucVu = dsNhanVien.getChucVu();
				
				//Gioi Tinh
				boolean gender = dsNhanVien.isGioiTinh();
				String gioiTinh;
				if(gender == true) {
					gioiTinh = "Nam";
				}else {
					gioiTinh = "Nu";
				}
				
				String cccd = dsNhanVien.getCccd();
				
				//Tinh Trang
				boolean status = dsNhanVien.isTinhTrang();
				String tinhTrang;
				if(status == true) {
					tinhTrang = "Dang Hoat Dong";
				}else {
					tinhTrang = "Khong Hoat Dong";
				}
				
				String email = dsNhanVien.getEmail();
				
				
				Object[] rowData = {maNV , hoTenNV , ngaySinh , diaChi , tinhTrang , sdt , chucVu ,  email  , gioiTinh , cccd };
				DataModel.addRow(rowData);
			}
		}
	
	
//		Function này dùng để lấy ID nhân viên để làm mã nhân viên tăng dần
		public int getIDNhanVienDaCo() {
			int i = 1;
			dsnv = nv_Dao.getAllNhanVien();
			for (NhanVien nhanVien : dsnv) {
				i++;
			}
			return i;
		}
	
	
	
	
//		Function này  xác nhận xóa nhân viên
		public void showThongBaoXacNhanXoaNhanVien() {
	        JDialog dialog2 = new JDialog(frame, "Xác Nhận Xoá Nhân Viên", true);
	       
	        Box div_xacNhan = Box.createVerticalBox();
	        Box div_btn_xacNhan = Box.createHorizontalBox();
	        
	        JLabel lb_xacNhanMuonXoa = new JLabel("Bạn có chắc muốn xóa nhân viên này ?");
	        JButton btn_xacNhan = new JButton("Xác Nhận");
	        JButton btn_huy = new JButton("Huy");
	       
	        
	        div_xacNhan.add(lb_xacNhanMuonXoa);
	        
	        div_btn_xacNhan.add(Box.createHorizontalStrut(80));
	        div_btn_xacNhan.add(btn_xacNhan);
	        div_btn_xacNhan.add(Box.createHorizontalStrut(20));
	        div_btn_xacNhan.add(btn_huy);
	        
	        
	       
			
	        btn_xacNhan.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {           	
	            	int r = table_nv.getSelectedRow();
	            	if(r != -1) {
//	            		Chuc Vu r,6
	            		String chucVu = DataModel.getValueAt(r, 6).toString();
	            		
	            		if(chucVu.trim().equals("Nhân Viên")  || chucVu.trim().equals("Nhân Viên Bán Hàng")  ) {
	            			nv_Dao.xoaNhanVien(DataModel.getValueAt(r, 0).toString() , txtLyDo.getText());
		            		JOptionPane.showMessageDialog(frame, "Xoa Nhan Vien Thanh Cong" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		                    dialog2.dispose(); // Đóng dialog sau khi ấn OK
		            		DataModel.removeRow(r);
	            		}else {
	            			JOptionPane.showMessageDialog(frame, "Bạn Không Thể Xóa Nhân Viên Quản Lý" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	            			dialog2.dispose(); // Đóng dialog sau khi ấn OK
	            		}
	            		
	            		
	            	}else {
	            		JOptionPane.showMessageDialog(frame, "Chon 1 Nhan Vien De Xoa" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	            	}
	            	         
	            }
	        });
	        
	        
	        btn_huy.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 dialog2.dispose();
					
				}
			});
	        
	        div_xacNhan.add(Box.createHorizontalStrut(60));
	        div_xacNhan.add(lb_xacNhanMuonXoa);
	        div_xacNhan.add(Box.createHorizontalStrut(60));
	        div_xacNhan.add(div_btn_xacNhan);
	        
	        dialog2.getContentPane().add(div_xacNhan , BorderLayout.NORTH);
	        dialog2.setSize(400, 150);
	        dialog2.setLocationRelativeTo(frame);
	        dialog2.setVisible(true);
	        
		}
		
		
		
//		Function này thông báo xóa nhân viên
		
		public void showThongBaoXoaNhanVien() {
			
	        JDialog dialog = new JDialog(frame, "Xoá Nhân Viên", true);
	        Box div_XoaNhanVien = Box.createHorizontalBox();
	      
	       JLabel label_xoaNV = new JLabel("Nhập Lý Do Xóa Nhân Viên Này:");
	       
	       div_XoaNhanVien.add(label_xoaNV);
	       
	       div_XoaNhanVien.add(Box.createHorizontalStrut(30));
	       txtLyDo  = new JTextField();
	       div_XoaNhanVien.add(txtLyDo);
	       
	       JButton btnOK = new JButton("OK");
	       div_XoaNhanVien.add(btnOK);
	       
	       
	       
	       btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				if(o.equals(btnOK)) {
					showThongBaoXacNhanXoaNhanVien();
					dialog.dispose();
				}
				
			}
		});
	       dialog.getContentPane().add(div_XoaNhanVien , BorderLayout.NORTH);
	        dialog.setSize(400, 150);
	        dialog.setLocationRelativeTo(frame);
	        dialog.setVisible(true);
	    }
		
		
		public void thangChucChoNhanVien() {
			int row = table_nv.getSelectedRow();
			
			if(row != -1) {
				String maNhanVienCanThangChuc = table_nv.getValueAt(row, 0).toString();
				nv_Dao.thangChuc(maNhanVienCanThangChuc);
			}
			
			txtChucVu.setText("Quản Lý");
		}
		
	       

	       
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			
//			Action them
			if(o.equals(btnThem)) {
				if(checkEmpty()) {
				String maNV = txtMaNv.getText();
				String hoTen = txtHoTen.getText();
				java.util.Date ngayDuocChon = dateChooser.getDate();
				java.sql.Date sqlDate = new java.sql.Date(ngayDuocChon.getTime());
				
				String diaChi = txtDiaChi.getText();
				String sdt = txtSdt.getText();
				String chucVu = txtChucVu.getText();
				
				
				boolean gioiTinh = true;
				
				if(rdbtn_nam.isSelected()) {
					gioiTinh = true;
				}else if(rdbtn_nu.isSelected()) {
					gioiTinh = false;
				}
				
				String cccd = txtCCCD.getText();
				
				boolean trangThai = true;
				if(rdbtn_dangHd.isSelected()) {
					trangThai = true;
				}else if(rdbtn_ngungHd.isSelected()) {
					trangThai = false;
				}
				
				String email = tf_email.getText();
				String lyDo = "";
				
				
				NhanVien nv = new NhanVien(maNV, hoTen, sqlDate, diaChi, sdt, chucVu, gioiTinh, cccd, trangThai, email , lyDo);
				
				nv_Dao.addNhanVien(nv);
				
//				Data add do bang 
				
				String gioiTinhTrongTable = "";
				if(gioiTinh == true) {
					gioiTinhTrongTable = "Nam";
				}else if(gioiTinh == false) {
					gioiTinhTrongTable = "Nu";
				}
				
				String trangThaiTrongTable = "";
				if(trangThai == true) {
					trangThaiTrongTable = "Dang Hoat Dong";
				}else if(trangThai == false) {
					trangThaiTrongTable = "Khong Hoat Dong";
				}
				
//				maNV , hoTenNV , ngaySinh , diaChi , tinhTrang , sdt , chucVu ,  email  , gioiTinh , cccd
				
				Object[] rowData = {maNV , hoTen , sqlDate , diaChi , trangThaiTrongTable , sdt , chucVu ,  email,  gioiTinhTrongTable , cccd };
				DataModel.addRow(rowData);
				
				idNhanVien = getIDNhanVienDaCo();
				
				
				
				JOptionPane.showMessageDialog(null, "Thêm Nhân Viên Thành Công" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				
				if(idNhanVien < 9) {
					txtMaNv.setText("NV00" + idNhanVien);
				}else if(idNhanVien > 9 && idNhanVien < 99) {
					txtMaNv.setText("NV0" + idNhanVien);
				}else if(idNhanVien >= 100) {
					txtMaNv.setText("NV" + idNhanVien);
				}
				
				try {
					lamMoi();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			}
			
//			End Acction them
			
			
			
			
			
//			Start Action xóa
			if(o.equals(btnXoa)) {
				showThongBaoXoaNhanVien();
				xoaBang();
				loadDataToTable();
			}
		
			
//			End Action Xóa	
			
			
//			Action sửa 
			if(o.equals(btnSua)) {
				if(checkEmpty()) {
				int row = table_nv.getSelectedRow();
		        if (row < 0) {
		            JOptionPane.showMessageDialog(btnSua, "Vui lòng chọn một nhân viên để cập nhật!");
		            return;
		        }
		        
		        
		        String maNV = txtMaNv.getText();
				String hoTen = txtHoTen.getText();
				java.util.Date ngayDuocChon = dateChooser.getDate();
				java.sql.Date sqlDate = new java.sql.Date(ngayDuocChon.getTime());
				
//				Duoc Phep Sua
				String diaChi = txtDiaChi.getText();
				
//				Duoc Phep Sua
				String sdt = txtSdt.getText();
				
//				Duoc Phep Sua
				String chucVu = txtChucVu.getText();
				
				boolean gioiTinh = true;
				
				if(rdbtn_nam.isSelected()) {
					gioiTinh = true;
				}else if(rdbtn_nu.isSelected()) {
					gioiTinh = false;
				}
				
				String cccd = txtCCCD.getText();
				
				boolean trangThai = true;
				if(rdbtn_dangHd.isSelected()) {
					trangThai = true;
				}else if(rdbtn_ngungHd.isSelected()) {
					trangThai = false;
				}
				
//				Duoc Phep Sua
				String email = tf_email.getText();
				
				String lyDo = "";
				
				
//				String maNhanVien , String diaChiNew , String sdtNew , String chucVuNew ,String emailNew
				
				nv_Dao.updateNhanVien(maNV , diaChi , sdt , chucVu , email);
				
				
				
				String gioiTinhTrongTable = "";
				if(gioiTinh == true) {
					gioiTinhTrongTable = "Nam";
				}else if(gioiTinh == false) {
					gioiTinhTrongTable = "Nu";
				}
				
				String trangThaiTrongTable = "";
				if(trangThai == true) {
					trangThaiTrongTable = "Đang Hoạt Động";
				}else if(trangThai == false) {
					trangThaiTrongTable = "Không Hoạt Động";
				}
				
				xoaBang();
				
		
				loadDataToTable();
			      
		        JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công!");
			}
			}else if(o.equals(btnThangChuc)) {
				thangChucChoNhanVien();
				xoaAllDataTrongBang();
				loadDataToTable();
				
			}else if(o.equals(btnLammoi)) {
				try {
					lamMoi();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}
			
		
		
		private void napNhanVienVaoTextfields() {
			int row = table_nv.getSelectedRow();
			if (row >= 0) {
				txtMaNv.setText((String) table_nv.getValueAt(row, 0));
				txtMaNv.setEditable(false);
				
				txtHoTen.setText((String) table_nv.getValueAt(row, 1));
				txtHoTen.setEditable(false);
				
				 // Lấy ngày tháng từ cột thích hợp của bảng
		        java.util.Date ngaySinh = (java.util.Date) table_nv.getValueAt(row, 2);
		        
		        
		        // Đặt ngày tháng vào dateChooser
		        dateChooser.setDate(ngaySinh);
		        dateChooser.setEnabled(false);
		        
		        
				txtDiaChi.setText((String) table_nv.getValueAt(row, 3));
				
				
				txtSdt.setText((String) table_nv.getValueAt(row, 5));
				txtChucVu.setText((String) table_nv.getValueAt(row, 6));
				tf_email.setText((String) table_nv.getValueAt(row, 7));
				
//				Gioi Tinh La o so 8
				String gioiTinhTrongBang =  (String) table_nv.getValueAt(row, 8);
				boolean gioiTinhDuocChon = false;
				if(gioiTinhTrongBang.trim().equals("Nam")) {
					gioiTinhDuocChon = true;
				}
				
				if(gioiTinhDuocChon == true) {
					rdbtn_nam.setSelected(true);
					rdbtn_nam.setEnabled(false);
					rdbtn_nu.setEnabled(false);
				}else {
					rdbtn_nu.setSelected(true);
					rdbtn_nam.setEnabled(false);
					rdbtn_nu.setEnabled(false);
				}
				
				rdbtn_dangHd.setEnabled(false);
				rdbtn_ngungHd.setEnabled(false);
		
				
				
				txtCCCD.setText((String) table_nv.getValueAt(row, 9));
				txtCCCD.setEditable(false);
				
				
				
			    
			}
		}
		
		private void updateJComboBox() {
			cboModelTK.addElement("Tìm Mã Nhân Viên");
			cboModelTK.addElement("Tìm Tên Nhân Viên");
			
		}
		private void timTheoSoDT() throws SQLException {
			table_nv.clearSelection();
			String soDT = textField_4.getText();
			
			NhanVien nv=new NhanVien();
			
			 NhanVien_Dao nvdao=new NhanVien_Dao();
			nv = nvdao.getNVTheoSDT(soDT);
			
			String gioiTinhTrongTable = "";
			if(gioitinh == true) {
				gioiTinhTrongTable = "Nam";
			}else if(gioitinh == false) {
				gioiTinhTrongTable = "Nu";
			}
			
			boolean trangThai = nv.isTinhTrang();
			String trangThaiTrongTable = trangThai ? "Dang Hoat Dong" : "Ngung Hoat Dong";
			
			if (nv == null) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy Nhân Viên! ");
			} else {
				// Chuyển đổi ngày sinh thành một chuỗi để hiển thị
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        String ngaySinhStr = dateFormat.format(nv.getNgaySinh());

				String[] row = {nv.getMaNV(), nv.getHoTenNV(), ngaySinhStr,nv.getDiaChi(),trangThaiTrongTable,nv.getSdt(),nv.getChucVu(),gioiTinhTrongTable,nv.getCccd() };
				DataModel.addRow(row);
			}
			table_nv.setModel(DataModel);
		}

		private void timTheoTen() throws SQLException {
			table_nv.clearSelection();
			String ten = textField_4.getText();
			
			NhanVien_Dao nvdao=new NhanVien_Dao();
			//List<NhanVien> listnv = nvdao.getNVTheoTen(ten);
			List<NhanVien> listnv=nvdao.getNVTheoTen(ten);
			
			String gioiTinhTrongTable = "";
			
			
			//String trangThaiTrongTable = "";
			
			if (listnv.size() == 0) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy tên Nhan vien theo ten! ");

			} else {
				for (NhanVien nv : listnv) {

					java.util.Date ngaySinh = nv.getNgaySinh();
					if(nv.isGioiTinh()== true) {
						gioiTinhTrongTable = "Nam";
					}else {
						gioiTinhTrongTable = "Nu";
					}
					
					boolean trangThai = nv.isTinhTrang();
					String trangThaiTrongTable = trangThai ? "Dang Hoat Dong" : "Ngung Hoat Dong";
					

			        Object[] row = {nv.getMaNV(), nv.getHoTenNV(), ngaySinh ,nv.getDiaChi(),trangThaiTrongTable,nv.getSdt(),nv.getChucVu(), nv.getEmail() , gioiTinhTrongTable,nv.getCccd() };
					DataModel.addRow(row);
				}

				table_nv.setModel(DataModel);
			}
		}
		
		private void timTheoMa() {
			table_nv.clearSelection();
			String manv = textField_4.getText();
			NhanVien nv=new NhanVien();
			
			NhanVien_Dao nvdao=new NhanVien_Dao();	
			 
			nv = nvdao.getNVTheoMa(manv);

			 
			 String gioiTinhTrongTable = "";
				if(nv.isGioiTinh() == true) {
					gioiTinhTrongTable = "Nam";
				}else if(nv.isGioiTinh() == false) {
					gioiTinhTrongTable = "Nu";
				}
				
				boolean trangThai = nv.isTinhTrang();
				String trangThaiTrongTable = trangThai ? "Dang Hoat Dong" : "Ngung Hoat Dong";
				
			if (nv == null) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy Nhân Viên ");
			} else {
				java.util.Date ngaySinh = nv.getNgaySinh();

		        	        
				Object[] row = {nv.getMaNV(), nv.getHoTenNV(), ngaySinh ,nv.getDiaChi(),trangThaiTrongTable,nv.getSdt(),nv.getChucVu(), nv.getEmail() , gioiTinhTrongTable,nv.getCccd() };
				DataModel.addRow(row);
			}
		}
		
		
		
		private void xoaBang() {
			for (int j = 0; j < table_nv.getRowCount(); j++) {
				DataModel.removeRow(j);
				j--;
			}
		}
		
		private void updateTable() {
			DataModel.setRowCount(0);
			for(NhanVien n: nv_Dao.getNhanVienDangHoatDong()){
				String gioiTinhTrongTable = "";
				if(n.isGioiTinh() == true) {
					gioiTinhTrongTable = "Nam";
				}else if(n.isGioiTinh() == false) {
					gioiTinhTrongTable = "Nu";
				}
				
				boolean trangThai = n.isTinhTrang();
				String trangThaiTrongTable = trangThai ? "Dang Hoat Dong" : "Ngung Hoat Dong";
				
				Object[] rowData= {n.getMaNV(),n.getHoTenNV(),n.getNgaySinh(),n.getDiaChi(),trangThaiTrongTable,n.getSdt(),n.getChucVu(),n.getEmail(),gioiTinhTrongTable,n.getCccd()};
					DataModel.addRow(rowData);
			}
		}
		
		public void lamMoi() throws SQLException {
			if(idNhanVien < 9) {
				txtMaNv.setText("NV00" + idNhanVien);
			}else if(idNhanVien > 9 && idNhanVien < 99) {
				txtMaNv.setText("NV0" + idNhanVien);
			}else if(idNhanVien >= 100) {
				txtMaNv.setText("NV" + idNhanVien);
			}
			
			txtHoTen.setText("");
			txtDiaChi.setText("");
			txtSdt.setText("");
			txtCCCD.setText("");
			txtChucVu.setText("Nhân Viên Bán Hàng");
			dateChooser.setDate(null);
			tf_email.setText("");
			rdbtn_dangHd.setSelected(false);
			buttonGroup_2.clearSelection();
			textField_4.setText("");
			
			txtHoTen.setEditable(true);
			dateChooser.setEnabled(true);
			rdbtn_nam.setEnabled(true);
			rdbtn_nu.setEnabled(true);
			txtCCCD.setEditable(true);
			
			xoaBang();
			updateTable();
			
			
		}
		
		
		
		
		
		public static void main(String[] args) {
			GUI_NhanVien frame = new GUI_NhanVien();
			frame.setLocationRelativeTo(null);
//			frame.setVisible(true);		
		}
		
		
		//Ràng buộc dữ liệu khi nhập
			private Boolean checkEmpty() {
				String ma = txtMaNv.getText();
				String tennv = txtHoTen.getText();
				String diaChi = txtDiaChi.getText();
				String sdt = txtSdt.getText();
				String chucVu = txtChucVu.getText();
				String   cccd = txtCCCD.getText();
				String email = tf_email.getText();
				 
				Date currentDate = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentDate);
				int currentYear = cal.get(Calendar.YEAR);

				java.util.Date ngayDuocChon = dateChooser.getDate();
				if (ngayDuocChon != null) {
				    Calendar calNgaySinh = Calendar.getInstance();
				    calNgaySinh.setTime(ngayDuocChon);
				    int ngaySinhYear = calNgaySinh.get(Calendar.YEAR);

				    if (currentYear - ngaySinhYear < 18) {
				    	 JOptionPane.showMessageDialog(this, "Nhân Viên phải >18 tuổi!", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
					        dateChooser.setDate(null); // Xóa ngày sinh trên JCalendar
					        return false;
				    }
				}
			    
			   
				
				
				if (ma.trim().equals("")) {
					return showErrorTx(txtMaNv, "Mã Nhân Viên không được để trống");

				} else if (!(ma.length() > 0 && ma.matches("^(NV|QL)[0-9]{3}$"))) {
					JOptionPane.showMessageDialog(null, "Không đúng định dạng" + "\nVui lòng nhập theo VD: NV001 || QL001");
					txtMaNv.selectAll();
					txtMaNv.requestFocus();
					return false;
					
					
				} else if (tennv.trim().equals("")) {
					return showErrorTx(txtHoTen,"Tên Nhân Viên không được để trống");
				}else if(!tennv.matches("[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ\"\r\n"
						+ "				+ \"fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu\"\r\n"
						+ "				+ \"UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ ]+")) {
					JOptionPane.showMessageDialog(this, "Nhập sai tên. Tên không chứa số hoặc các ký tự đặc biệt", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
					txtHoTen.selectAll();
					txtHoTen.requestFocus();
					return false;
					
					
				} else if (diaChi.trim().equals("")) {
					return showErrorTx(txtDiaChi, "Địa Chỉ không được để trống");
				
				} else if (sdt.trim().equals("")) {
					return showErrorTx(txtSdt, "SDT không được để trống");
				}else if(sdt.length() < 0 || !sdt.matches("^0[0-9]{9}")) {
						JOptionPane.showMessageDialog(this, "Số Điện Thoại Nhập Vào Không Hợp Lệ !", "Thông báo",
								JOptionPane.ERROR_MESSAGE);
						txtSdt.selectAll();
						txtSdt.requestFocus();
						return false;
						
				}else if (nv_Dao.isSDTTonTai(sdt)) {
				        JOptionPane.showMessageDialog(this, "SDT đã tồn tại. Vui lòng chọn số khác.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
				        txtSdt.selectAll();
				        txtSdt.requestFocus();
				        return false;
				} else if (chucVu.trim().equals("")) {
					return showErrorTx(txtChucVu, "Chức vụ không được để trống");
				
				}else if (cccd.trim().equals("")) {
					return showErrorTx(txtCCCD, "CCCD không được để trống");
				} else if (!cccd.matches("^[0-9]{12}$")) {
			        JOptionPane.showMessageDialog(this, "CCCD phải chứa đúng 12 chữ số", "Thông báo",
			                JOptionPane.ERROR_MESSAGE);
			        txtCCCD.selectAll();
			        txtCCCD.requestFocus();
			        return false;
			    } else if (nv_Dao.isCCTonTai(cccd)) {
			        JOptionPane.showMessageDialog(this, "CCCD đã tồn tại. Vui lòng chọn số khác.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
			        txtCCCD.selectAll();
			        txtCCCD.requestFocus();
			        return false;
				}else if(email.trim().equals("")) {
					return showErrorTx(tf_email, "Email không được để trống");
				}else if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
					JOptionPane.showMessageDialog(this, "Email Nhập Khong Hop Le", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
					tf_email.selectAll();
					tf_email.requestFocus();
					return false;
				}else if (nv_Dao.isEmailTonTai(email)) {
			        JOptionPane.showMessageDialog(this, "Email đã tồn tại. Vui lòng nhập email khác.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
			        txtCCCD.selectAll();
			        txtCCCD.requestFocus();
			        return false;
				
				}
				return true;
			}
			
			
			
			
			private Boolean showErrorTx(JTextField tx, String errorInfo) {
				JOptionPane.showMessageDialog(tx, errorInfo);
				tx.requestFocus();
				return false;
			}
}
