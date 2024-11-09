package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.ChiTietHoaDon_Dao;
import DAO.HoaDon_Dao;
import DAO.KhachHang_DAO;
import DAO.NhanVien_Dao;
import DAO.Sach_DAO;
import DAO.TaiKhoan_Dao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.Mycombo_MaKH;
import entity.Mycombo_MaNV;
import entity.Mycombo_MaSach;
import entity.Mycombo_TenSach;
import entity.NhaCungCap;
import entity.NhaXuatBan;
import entity.NhanVien;
import entity.Sach;
import entity.TacGia;
import entity.TaiKhoan;
import entity.TheLoai;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Button;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

public class GUI_TaoHoaDon extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField tfMhd;
	private JTextField tfMnv;
	private JTable table_sanPhamTrongHoaDon;
	private JTextField tfMq;
	private JTextField tfMkh;
	private JTextField tfNl;
	private JTextField textField_6;
	private JTextField jtMs;
	private JTextField jtTens;
	private JTextField jtSl;
	private JTextField jtDg;
	private JTextField jtVAT;
	private JTextField jtUd;
	private JTextField jtTht;
	private JTextField jtTt;
	private JTextField jtTn;
	private JTextField jtTthua;
	private int idHoaDon;
	private JComboBox<String> comboxMaKH , comboxMaNV , comboxMaSach , comboxMaQuay , comboxTenSach;
	
	private List<KhachHang> dskh = new ArrayList<KhachHang>();
	private List<NhanVien> dsnv = new ArrayList<NhanVien>();
	private List<Sach> dsSach = new ArrayList<Sach>();
	private List<HoaDon> dshd;
	private List<ChiTietHoaDon> ds_cthd = new ArrayList<ChiTietHoaDon>();
	private List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();

	
	
	private KhachHang_DAO kh_dao = new KhachHang_DAO();
	private NhanVien_Dao nv_dao = new NhanVien_Dao();
	private Sach_DAO sach_dao = new Sach_DAO();
	private HoaDon_Dao hd_dao = new HoaDon_Dao();
	private ChiTietHoaDon_Dao cthd_dao = new ChiTietHoaDon_Dao();
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	
	private JButton btnThemSp , btnTaoHd;
	private DefaultTableModel DataModel = new DefaultTableModel();
	JLabel lbHinhAnh = new JLabel("Hinh Anh Ở Đây");
	JButton btn_xoaSanPhamTrongHoaDon;
	protected JFileChooser browseImageFile;
	protected String selectedImagePath;
	private String fileImgPath;
	private double vat;
	private double uuDai;
	private double tongTienTrongHoaDon;
	
	private String maNhanVienHienTai = "User";
	private String chucVuHienTai = "";
	private String hoTenNhanVien = "User Name";
	
	private JFrame frame = new JFrame();
	
	private List<ChiTietHoaDon> dsSanPhamTrongHoaDon = new ArrayList<ChiTietHoaDon>();
	
	private String soLuongNhap = "";
	private String tienNhan = "";
	
	
	private int soLuongSachTrongKho;
	private String maSachTrongKho;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_TaoHoaDon frame = new GUI_TaoHoaDon();
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
	public GUI_TaoHoaDon() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
//		Get Nhan Vien Hien Tai
		dstk = tk_dao.getAllTaiKhoan();
		for (TaiKhoan tk : dstk) {
			if(tk.isTrangThai() == true) {
				maNhanVienHienTai = tk.getMaNV();
				hoTenNhanVien = tk.getHoTenNhanVien();
				chucVuHienTai = tk.getChucVu();
			}
		}
		
//		Sự kiện đóng chương trình KHI ấn nút x để thoát chương trình thì nó sẽ tự động đăng xuất
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                	tk_dao.dangXuat(maNhanVienHienTai);
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
	                	if(chucVuHienTai.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_NhanVien guiNhanVien = new GUI_NhanVien();
		                	guiNhanVien.setVisible(true);
		                	dispose();
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F9) { //F9 : Xem Thông Tin Nhân Viên Bị Xóa 
	                	if(chucVuHienTai.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_XemTTNVbixoa guiNhanVienBiXoa = new GUI_XemTTNVbixoa();
		                	guiNhanVienBiXoa.setVisible(true);
		                	dispose();
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F10) { //F10 : Nhà Cung Cấp 
	                	if(chucVuHienTai.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
		                	guiNCC.setVisible(true);
		                	dispose();;
	                	}
	                	
	                	
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
		
		
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_TaoHoaDon.class.getResource("/imgs/qls.png")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tạo hóa đơn");
		setBounds(100, 100, 1150, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

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
		
		JLabel lblNewLabel = new JLabel("Tạo Hóa Đơn");
		lblNewLabel.setForeground(new Color(234, 198, 150));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBounds(460, 0, 387, 68);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chucVuHienTai.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
					guiNhanVien.setVisible(true);
					dispose();
				}
				
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
				if(chucVuHienTai.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_XemTTNVbixoa guiNhanVienBiXoa = new GUI_XemTTNVbixoa();
					guiNhanVienBiXoa.setVisible(true);
					dispose();
				}
				
			}
		});
		btnNhanVIen.setComponentPopupMenu(popupMenuNhanVien);
		
		menuItemThemNhanVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chucVuHienTai.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
					guiNhanVien.setVisible(true);
					dispose();
				}
				
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
				if(chucVuHienTai.trim().equals("Nhân Viên Bán Hàng")) {
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
				if(chucVuHienTai.trim().equals("Nhân Viên Bán Hàng")) {
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
		
		JButton btnThoat = new JButton("Thoát (Alt + 4)");
		btnThoat.setIcon(new ImageIcon(GUI_TrangChu.class.getResource("/imgs/exit_img.png")));
		btnThoat.setForeground(new Color(255, 255, 255));
		btnThoat.setBackground(new Color(17, 57, 70));
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThoat.setBounds(976, 73, 179, 58);
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
		            tk_dao.dangXuat(maNhanVienHienTai);
		            dispose();
		        }
		    }
		});
		JPanel pnUser = new JPanel();
		pnUser.setBackground(new Color(17, 57, 70));
		pnUser.setBounds(976, 0, 160, 40);
		contentPane.add(pnUser);
		pnUser.setLayout(null);
		
		
		
		JLabel lb_chucVu = new JLabel("Chức Vụ :");
		lb_chucVu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lb_chucVu.setForeground(new Color(255, 255, 255));
		lb_chucVu.setBounds(29, 19, 78, 13);
		pnUser.add(lb_chucVu);
		
		JLabel lb_chucVuCuaNhanVien = new JLabel(chucVuHienTai);
		lb_chucVuCuaNhanVien.setForeground(new Color(255, 255, 255));
		lb_chucVuCuaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lb_chucVuCuaNhanVien.setBounds(89, 20, 59, 13);
		pnUser.add(lb_chucVuCuaNhanVien);
		
		JLabel lbUserName = new JLabel(hoTenNhanVien);
		lbUserName.setForeground(new Color(255, 255, 255));
		lbUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbUserName.setBounds(89, 2, 101, 13);
		pnUser.add(lbUserName);
		
		JLabel lblUser = new JLabel(maNhanVienHienTai);
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
        
        
        this.setLocationRelativeTo(null);
        
		
		JLabel lblNewLabel_2 = new JLabel("THÔNG TIN HÓA ĐƠN");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_2.setForeground(new Color(234, 198, 150));
		lblNewLabel_2.setBounds(20, 175, 1095, 42);
		contentPane.add(lblNewLabel_2);
		
		
		
		
		JLabel lbMhd = new JLabel("Mã HD:");
		lbMhd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbMhd.setForeground(new Color(234, 198, 150));
		lbMhd.setBounds(10, 136, 88, 37);
		contentPane.add(lbMhd);
		
		JLabel lbMkh = new JLabel("Mã KH:");
		lbMkh.setForeground(new Color(234, 198, 150));
		lbMkh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbMkh.setBounds(235, 136, 88, 37);
		contentPane.add(lbMkh);
		
		JLabel lbMnv = new JLabel("Mã NV:");
		lbMnv.setForeground(new Color(234, 198, 150));
		lbMnv.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbMnv.setBounds(468, 136, 88, 37);
		contentPane.add(lbMnv);
		
		
		
		JLabel lbMq = new JLabel("Mã quầy:");
		lbMq.setForeground(new Color(234, 198, 150));
		lbMq.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbMq.setBounds(706, 136, 88, 37);
		contentPane.add(lbMq);
		
		JLabel lbNl = new JLabel("Ngày lập:");
		lbNl.setForeground(new Color(234, 198, 150));
		lbNl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbNl.setBounds(923, 136, 88, 37);
		contentPane.add(lbNl);
		
//							 X   Y    width hight							
		lbHinhAnh.setBounds(944, 183, 182, 178);
		
		
		contentPane.add(lbHinhAnh);
		
		JLabel lbMs = new JLabel("Mã sách:");
		lbMs.setForeground(new Color(234, 198, 150));
		lbMs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbMs.setBounds(27, 243, 88, 28);
		contentPane.add(lbMs);
		
		JLabel lbTs = new JLabel("Tên sách:");
		lbTs.setForeground(new Color(234, 198, 150));
		lbTs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTs.setBounds(299, 243, 88, 28);
		contentPane.add(lbTs);
		
		JLabel lbSl = new JLabel("Số lượng:");
		lbSl.setForeground(new Color(234, 198, 150));
		lbSl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbSl.setBounds(27, 309, 88, 28);
		contentPane.add(lbSl);
		
		JLabel lbDg = new JLabel("Đơn giá:");
		lbDg.setForeground(new Color(234, 198, 150));
		lbDg.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbDg.setBounds(650, 243, 88, 28);
		contentPane.add(lbDg);
		
		JLabel lbVAT = new JLabel("VAT:");
		lbVAT.setForeground(new Color(234, 198, 150));
		lbVAT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbVAT.setBounds(323, 309, 88, 28);
		contentPane.add(lbVAT);
		
		JLabel lbUd = new JLabel("Ưu đãi:");
		lbUd.setForeground(new Color(234, 198, 150));
		lbUd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbUd.setBounds(27, 539, 88, 28);
		contentPane.add(lbUd);
		
		JLabel lbTht = new JLabel("Thành tiền:");
		lbTht.setForeground(new Color(234, 198, 150));
		lbTht.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTht.setBounds(650, 309, 105, 28);
		contentPane.add(lbTht);
		
		JLabel lbTt = new JLabel("Tổng tiền:");
		lbTt.setForeground(new Color(234, 198, 150));
		lbTt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTt.setBounds(325, 539, 105, 28);
		contentPane.add(lbTt);
		
		JLabel lbTn = new JLabel("Tiền nhận:");
		lbTn.setForeground(new Color(234, 198, 150));
		lbTn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTn.setBounds(652, 539, 105, 28);
		contentPane.add(lbTn);
		
		JLabel lbTthua = new JLabel("Tiền thừa:");
		lbTthua.setForeground(new Color(234, 198, 150));
		lbTthua.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTthua.setBounds(923, 539, 105, 28);
		contentPane.add(lbTthua);
		
		
//		hinhAnh.setIcon(new ImageIcon(GUI_TaoHoaDon.class.getResource("/imgs/ttdgbn.jpg")));
//		hinhAnh.setForeground(new Color(234, 198, 150));
//		hinhAnh.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		hinhAnh.setBounds(94, 157, 129, 140);
		contentPane.add(lbHinhAnh);
		
//		Ma Hoa Don
		tfMhd = new JTextField();
		tfMhd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfMhd.setColumns(10);
		tfMhd.setBounds(81, 140, 123, 28);
		contentPane.add(tfMhd);
		
		
		comboxMaKH = new JComboBox<String>();
		
		for (Mycombo_MaKH kh : kh_dao.getmaKH()) {
			String dataMaKH = kh.getMaKH();
			comboxMaKH.addItem(dataMaKH);
		}
		
//		comboxMaKH.setColumns(10);
		comboxMaKH.setBounds(307, 140, 123, 28);
		contentPane.add(comboxMaKH);

		tfMnv = new JTextField();
		
		tfMnv.setEditable(false);
		tfMnv.setText(maNhanVienHienTai);
		
		tfMnv.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		tfMnv.setColumns(10);
	
		tfMnv.setBounds(547, 140, 123, 28);
		contentPane.add(tfMnv);
		
		
		
		comboxMaQuay = new JComboBox<String>();
		comboxMaQuay.addItem("Q1");
		comboxMaQuay.addItem("Q2");
		comboxMaQuay.addItem("Q3");
		comboxMaQuay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboxMaQuay.setBounds(784, 140, 123, 28);
		contentPane.add(comboxMaQuay);
		
		
		LocalDate currentDate = LocalDate.now();

        // Format lai date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
		
		tfNl = new JTextField();
		tfNl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfNl.setColumns(10);
		tfNl.setBounds(1009, 140, 123, 28);
		tfNl.setEditable(false);
		tfNl.setText("" + formattedDate);
		contentPane.add(tfNl);
		
		
		
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
//		jtMs = new JTextField();
		
		comboxMaSach = new JComboBox<String>();
		comboxMaSach.addItem("MS000");
		for (Mycombo_MaSach maSach : sach_dao.getmaSach()) {
			comboxMaSach.addItem(maSach.getMaSach());
		}
		comboxMaSach.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		jtMs.setColumns(10);
		comboxMaSach.setBounds(126, 243, 123, 28);
		contentPane.add(comboxMaSach);
		
		
		comboxTenSach = new JComboBox<String>();
		comboxTenSach.addItem("Chọn 1 Sách");
		for (Mycombo_TenSach tenSach : sach_dao.getAllTenSach()) {
			comboxTenSach.addItem(tenSach.getTenSach());
		}
		comboxTenSach.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		comboxTenSach.setColumns(10);
		comboxTenSach.setBounds(382, 243, 229, 28);
		
		
		
		comboxTenSach.addItemListener(new ItemListener() {
		
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
                    String tenSach = comboxTenSach.getSelectedItem().toString();
                    hienThiThongTinSach(tenSach);
				}				
				
			}
		});
		
		
		contentPane.add(comboxTenSach);
		
		jtSl = new JTextField();
		jtSl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtSl.setColumns(10);
		jtSl.setBounds(126, 309, 123, 28);
		jtSl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi người dùng ấn Enter
                // Mất focus của trường văn bản
            	jtSl.setFocusable(false);
            	jtSl.setFocusable(true);
            	isMatch(jtSl.getText());

            }
        });
		contentPane.add(jtSl);
		
		jtDg = new JTextField();
		jtDg.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtDg.setColumns(10);
		jtDg.setBounds(748, 243, 123, 28);
		contentPane.add(jtDg);
		
		jtVAT = new JTextField();
		jtVAT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtVAT.setColumns(10);
		jtVAT.setBounds(382, 309, 123, 28);
		contentPane.add(jtVAT);
		
		jtUd = new JTextField();
		jtUd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtUd.setColumns(10);
		jtUd.setBounds(126, 539, 123, 28);
		contentPane.add(jtUd);
		jtUd.setEditable(false);
		
		
		jtTht = new JTextField();
		jtTht.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtTht.setColumns(10);
		jtTht.setBounds(748, 309, 123, 28);
		contentPane.add(jtTht);
		
		jtTt = new JTextField();
		jtTt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtTt.setColumns(10);
		jtTt.setBounds(421, 539, 123, 28);
		contentPane.add(jtTt);
		
		jtTn = new JTextField();
		jtTn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtTn.setColumns(10);
		jtTn.setBounds(748, 539, 123, 28);
		contentPane.add(jtTn);
		
		
		
		jtTthua = new JTextField();
		jtTthua.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jtTthua.setColumns(10);
		jtTthua.setBounds(1009, 539, 123, 28);
		contentPane.add(jtTthua);
		
//		Table San Pham Vao Hoa Don 372, 240, 711, 202
//		"Mã sách", "Tên sách", "Số lượng", "Đơn giá", "VAT", "Ưu đãi", "Thành tiền"
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(209, 371, 786, 142);
		String[] headers= "Mã sách; Tên sách ; Số lượng; Đơn giá; Thành tiền".split(";");
		DataModel=new DefaultTableModel(headers,0);
		
		scrollPane.setViewportView(table_sanPhamTrongHoaDon=new JTable(DataModel));
		table_sanPhamTrongHoaDon.setRowHeight(25);
		table_sanPhamTrongHoaDon.setAutoCreateRowSorter(true);
		table_sanPhamTrongHoaDon.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//table.setPreferredSize(new Dimension(500, 300));
		contentPane.add(scrollPane);
		
		
		table_sanPhamTrongHoaDon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				loadSanPhamLenHoaDon();
			}
		});
		
		
		btnTaoHd = new JButton("Tạo hóa đơn");
		btnTaoHd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTaoHd.setBackground(new Color(17, 57, 70));
		btnTaoHd.setForeground(new Color(234, 198, 150));
		btnTaoHd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTaoHd.setBounds(910, 589, 216, 44);
		contentPane.add(btnTaoHd);
		
		btnThemSp = new JButton("Thêm sản phẩm vào hóa đơn");
		btnThemSp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		
		btnThemSp.setBackground(new Color(17, 57, 70));
		btnThemSp.setForeground(new Color(234, 198, 150));
		btnThemSp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnThemSp.setBounds(27, 590, 269, 44);
		contentPane.add(btnThemSp);
		
		
		
		btn_xoaSanPhamTrongHoaDon = new JButton("Xóa Sản Phẩm");
		btn_xoaSanPhamTrongHoaDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_xoaSanPhamTrongHoaDon.setForeground(new Color(234, 198, 150));
		btn_xoaSanPhamTrongHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_xoaSanPhamTrongHoaDon.setBackground(new Color(17, 57, 70));
		btn_xoaSanPhamTrongHoaDon.setBounds(356, 589, 269, 45);
		contentPane.add(btn_xoaSanPhamTrongHoaDon);
		
		JButton btnLmMi = new JButton("Làm mới");
		btnLmMi.setForeground(new Color(234, 198, 150));
		btnLmMi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLmMi.setBackground(new Color(17, 57, 70));
		btnLmMi.setBounds(686, 589, 173, 45);
		contentPane.add(btnLmMi);
		
		JLabel lblNewLabel_4 = new JLabel("l");
		lblNewLabel_4.setIcon(new ImageIcon(GUI_TaoHoaDon.class.getResource("/imgs/gui_ncc.png")));
		lblNewLabel_4.setBounds(0, 0, 1143, 684);
		contentPane.add(lblNewLabel_4);
		
		idHoaDon = getIDHoaDon();
		
		if(idHoaDon < 10) {
			tfMhd.setText("HD00" + idHoaDon);
		}else if (idHoaDon >= 10 && idHoaDon < 100) {
			tfMhd.setText("HD0" + idHoaDon);
		}else if(idHoaDon >= 100) {
			tfMhd.setText("HD" + idHoaDon);
		}
		
		tfMhd.setEditable(false);
//		jtTens.setEditable(false);
		jtVAT.setEditable(false);
		jtDg.setEditable(false);
		jtTht.setEditable(false);
		jtTt.setEditable(false);
		jtTthua.setEditable(false);
		
		
		btnThemSp.addActionListener(this);
		btnTaoHd.addActionListener(this);
		
		
//		Add su kien focus vao tf_soluong
		jtSl.addFocusListener(new FocusListener() {
			
			
			@Override
			public void focusLost(FocusEvent e) {
				String soLuongNhap = jtSl.getText();
				
				double result_thanhTien = tinhThanhTien();
				
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
				String formattedThanhTien = decimalFormat.format(result_thanhTien);
				jtTht.setText(formattedThanhTien);
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
				double result_thanhTien = tinhThanhTien();
				if(result_thanhTien == 1) {
					jtTht.setText("");
				}
				
			}
		});
		
		
		btn_xoaSanPhamTrongHoaDon.addActionListener(this);
		
//		Sự Kiện Tính Tiền Thừa Cho Khách Hàng 
		jtTn.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
				String tienNhanNhap = jtTn.getText();
				double tienNhan = Double.parseDouble(jtTn.getText());
				double tongTienCuaHoaDon = tongTienTrongHoaDon;
				
				
				double tienThua = 0;
								
				if(tienNhan > tongTienTrongHoaDon) {
					tienThua = tienNhan - tongTienTrongHoaDon;
					
					DecimalFormat decimalFormat = new DecimalFormat("#.##");
					String formattedTienThua = decimalFormat.format(tienThua);
					jtTthua.setText("" + formattedTienThua + "đ");
				}else if (tienNhan < tongTienTrongHoaDon) {
					JOptionPane.showMessageDialog(null, "Tiền Nhận Phải > Tổng Tiền", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		jtTn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi người dùng ấn Enter
                // Mất focus của trường văn bản
				jtTn.setFocusable(false);
				jtTn.setFocusable(true);
				
				
				String tienNhan = jtTn.getText();
				System.out.println(tienNhan);
				
				if(tienNhan.equals("")) {
					JOptionPane.showMessageDialog(null, "Tiền Nhận  Không Được Để Trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else if(!tienNhan.trim().matches("\\d*")) {
					JOptionPane.showMessageDialog(null, "Tiền Nhận Không Hợp Lệ Tiền Nhận Phải Là Số", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					 
				}
            }
		});
		
		
	}
//	Kết Thúc Giao Diện
	
	
	
// Ràng Buộc trường số lượng , tiền nhận phải là kiểu số 
	public boolean isMatch(String soLuongNhap) {
		String soLuong = soLuongNhap;
		
		if(soLuong.trim().equals("")) {
			 JOptionPane.showMessageDialog(null, "Số Lượng Không Được Để Trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else if(!soLuong.trim().matches("\\d*")) {
			 JOptionPane.showMessageDialog(null, "Số Lượng Phải Nhập Là Ký Tự Số 0-9", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean isMatchTienNhan(String tienNhanDaNhap) {
		String tienNhanNhap = tienNhanDaNhap;
		
		double tienNhan = Double.parseDouble(tienNhanNhap);
		double tongTienTrongHoaDonDaTinh = tongTienTrongHoaDon;
		
		double tienThua= 0;
		
		
		if(tienNhanNhap.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Tiền Nhận  Không Được Để Trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else if(!tienNhanNhap.trim().matches("\\d*")) {
			JOptionPane.showMessageDialog(null, "Tiền Nhận Không Hợp Lệ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			 return false;
		}else if (tienNhan < tongTienTrongHoaDon) {
			JOptionPane.showMessageDialog(null, "Tiền Nhận Phải > Tổng Tiền", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
//	Load San Pham Duoc Chon Le Hoa Don
	public void loadSanPhamLenHoaDon() {
		int r = table_sanPhamTrongHoaDon.getSelectedRow();
		if(r  >= 0) {
			String maHD = tfMhd.getText();
			String maSach = table_sanPhamTrongHoaDon.getValueAt(r, 0).toString();
			String tenSach = table_sanPhamTrongHoaDon.getValueAt(r, 1).toString();
			int soLuong = (int) table_sanPhamTrongHoaDon.getValueAt(r, 2);
			String donGia =  (String) table_sanPhamTrongHoaDon.getValueAt(r, 3);
			String thanhTien = (String) table_sanPhamTrongHoaDon.getValueAt(r, 4);
			
			comboxMaSach.setSelectedItem(maSach);
			comboxTenSach.setSelectedItem(tenSach);
			jtSl.setText(""+soLuong);
			jtDg.setText("" + donGia);
			jtTht.setText("" + thanhTien);
			
			
			xoaSachTrongHoaDon(maSach);
			
			DataModel.removeRow(r);
			
			
			
			tongTienTrongHoaDon = tinhTongTienTrongHoaDon();
			
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			String formattedTongTien = decimalFormat.format(tongTienTrongHoaDon);

			
			jtTt.setText(formattedTongTien + "đ");
			
		}
	}
	
	
	
	
	
//Function Hiển Thị Thông Tin Khi Chọn Tên Sách
	
	public void hienThiThongTinSach(String tenSach) {
		dsSach = sach_dao.getAllSach();
		
		for (Sach sach : dsSach) {
			if(sach.getTenSach().equalsIgnoreCase(tenSach)) {
				comboxMaSach.setSelectedItem(sach.getMaSach());
				
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
				String formattedDonGia = decimalFormat.format(sach.getDonGiaNhap());
				jtDg.setText(formattedDonGia);
				
				
				
				String hinhSach = sach.getHinhanh();
				ImageIcon imageIcon = new ImageIcon(hinhSach);
	            Image image = imageIcon.getImage();
	            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	            lbHinhAnh.setIcon(new ImageIcon(scaledImage));
				
			}
		}
		
	}
	
	
	
//	Function Tính Thành Tiền
	public double tinhThanhTien() {
		if(!jtSl.getText().trim().equals("")) {
			int soLuong = Integer.parseInt(jtSl.getText());
			double donGia = Double.parseDouble(jtDg.getText());
			
			double thanhTien = soLuong * donGia;
			return thanhTien;
		}
		return 1;
	}
	
	
	
	
//	Function dung de lay sach tuong ung cho hoa don
	public Sach timSachChoHoaDon(String imgPath) {
		dsSach = sach_dao.getAllSach();
		for (Sach sach : dsSach) {
			if(sach.getHinhanh().equalsIgnoreCase(imgPath)) {
//				String maSach, String tenSach, NhaCungCap nhacungcap, double donGiaNhap, int soLuong, TheLoai theloai,
//				NhaXuatBan nhaxuatban, TacGia tacgia, String hinhanh
				String maSach  = sach.getMaSach();
				String tenSach = sach.getTenSach();
				NhaCungCap ncc = sach.getNhacungcap();
				double donGiaNhap = sach.getDonGiaNhap();
				int soLuong = sach.getSoLuong();
				TheLoai theLoai = sach.getTheloai();
				NhaXuatBan nxb = sach.getNhaxuatban();
				TacGia tg = sach.getTacgia();
				String hinhAnh = sach.getHinhanh();
				return new Sach(maSach, tenSach, ncc, donGiaNhap, soLuong, theLoai, nxb, tg, hinhAnh);
			}
		}
		return null;
	}
	
	

	
	
	
	public int getIDHoaDon() {
		dshd = hd_dao.getAllHoaDon();
		if(dshd.isEmpty()) {
			return 1;
		}else {
			int i = 1;
			for (HoaDon dsHoaDonDaDuocTai : dshd) {
				i++;
			}
			return i;
		}	
	}
	
	
	public double tinhTongTienTrongHoaDon() {
		int item = 0;
		
		List<Sach> dsSachMienThue = new ArrayList<Sach>();
		
		boolean tatCaDeuLaSachMienVAT = true;
		
		double tongTienSanPham = 0;
		for (ChiTietHoaDon chiTietHoaDon : ds_cthd) {
			tongTienSanPham = chiTietHoaDon.getThanhTien() + tongTienSanPham;
			item++;	 
		}
		
		
		for (ChiTietHoaDon cthd : ds_cthd) {
			tatCaDeuLaSachMienVAT = checkTatCaLaHangMienThue(cthd.getMaSach());
			if(tatCaDeuLaSachMienVAT == false) {
				tatCaDeuLaSachMienVAT = false;
				break;
			}
		}
		
		if(tatCaDeuLaSachMienVAT == true) {
			jtVAT.setText("0%");
			vat = 0;
		}else {
			jtVAT.setText("8%");
			vat = 0.08;
		}
		
		
//		System.out.println(tatCaDeuLaSachMienVAT);
		
		if(item < 3) {
			jtUd.setText("5%");
			 uuDai  = 0.05;
		}else if(item >= 3 && item < 10) {
			jtUd.setText("10%");
			 uuDai = 0.1;
		}else if(item >= 10 && item <=20) {
			jtUd.setText("15%");
			 uuDai = 0.15;
		}else if(item > 20) {
			jtUd.setText("30%");
			 uuDai = 0.3;
		}
		
		double tongTienTrongHoaDon = tongTienSanPham - (tongTienSanPham * uuDai ) + (tongTienSanPham * vat);
		return tongTienTrongHoaDon;
	}
	
	
	
	
	
	public boolean checkTatCaLaHangMienThue(String maSachTrongChiTietHoaDon) {
		
		dsSach = sach_dao.getAllSach();
		
		for (Sach sach : dsSach) {
			if(sach.getMaSach().equalsIgnoreCase(maSachTrongChiTietHoaDon) &&  (sach.getTheloai().getTenTheLoai().equalsIgnoreCase("SGK") || sach.getTheloai().getTenTheLoai().equalsIgnoreCase("Giao Trinh") )  ) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean themSachVaoHoaDon(ChiTietHoaDon cthd) {
		for (ChiTietHoaDon dscthd : ds_cthd) {
			if(dscthd.getMaSach().trim().equals(cthd.getMaSach())) {
				return false;
			}
		}
		
		ds_cthd.add(cthd);
		return true;
		
	}
	
	
	public boolean xoaSachTrongHoaDon(String maSach) {
		
		ChiTietHoaDon temp = null;
		
		for (ChiTietHoaDon dscthd : ds_cthd) {
			if(dscthd.getMaSach().trim().equals(maSach)) {
				temp = dscthd;
			}
		}
		
		ds_cthd.remove(temp);
		return true;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThemSp)) {
			
			soLuongNhap = jtSl.getText().toString();
			
			
			if(isMatch(soLuongNhap) == true) {
				String maHD = tfMhd.getText();
				String maSach = comboxMaSach.getSelectedItem().toString();
				String tenSach = comboxTenSach.getSelectedItem().toString();
				int soLuong = Integer.parseInt(jtSl.getText()) ;
				float donGia = Float.parseFloat(jtDg.getText());
				double thanhTien = Double.parseDouble(jtTht.getText());
				
				
				if(maSach.equalsIgnoreCase("MS000")) {
					JOptionPane.showMessageDialog(frame, "Mã Sách Không Hợp Lệ" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else if(tenSach.equals("Chọn 1 Sách")) {
					JOptionPane.showMessageDialog(frame, "Tên Sách Không Hợp Lệ" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					
					int idHoaDonHienTai = 1;
					
					for (HoaDon hdHienTai : hd_dao.getAllHoaDon()) {
						idHoaDonHienTai++;
					}
					
					
					String maHDHienTai = "";
					
					if(idHoaDonHienTai < 10) {
						maHDHienTai = "HD00" + idHoaDonHienTai;
					}else if (idHoaDonHienTai >= 10 && idHoaDonHienTai < 100) {
						maHDHienTai = "HD0" + idHoaDonHienTai;
					}else if(idHoaDon >= 100) {
						maHDHienTai = "HD" + idHoaDonHienTai;
					}
				
					
					
				List<Sach> dsSach = sach_dao.getAllSach();
				for (Sach sach : dsSach) {
					if(maSach.equals(sach.getMaSach())) {
						soLuongSachTrongKho = sach.getSoLuong();
						if(soLuongSachTrongKho == 0) {
							JOptionPane.showMessageDialog(null, "Hết Hàng !", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
						}else if(soLuongSachTrongKho < soLuong) {
							JOptionPane.showMessageDialog(null, "Số Lượng Sách Không Đủ Để Bán", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
						}else {
							if(ds_cthd.isEmpty()) {
								DecimalFormat decimalFormat = new DecimalFormat("#.##");
								String donGiaFormatted = decimalFormat.format(donGia);
								String thanhTienFormatted = decimalFormat.format(thanhTien);
								
								dsSanPhamTrongHoaDon.add(new ChiTietHoaDon(maHD, maSach, tenSach, soLuong, donGia, thanhTien));
								ds_cthd.add(new ChiTietHoaDon(maHD, maSach, tenSach, soLuong, donGia, thanhTien));
								
								
								Object[] row_sanPham = {maSach , tenSach , soLuong , donGiaFormatted , thanhTienFormatted};
								DataModel.addRow(row_sanPham);
								tongTienTrongHoaDon = tinhTongTienTrongHoaDon();
								
								String formattedTongTien = decimalFormat.format(tongTienTrongHoaDon);
								jtTt.setText("" + formattedTongTien + "đ");
								
							}else {
								DecimalFormat decimalFormat = new DecimalFormat("#.##");
								String tenSachTrongHoaDon =  comboxTenSach.getSelectedItem().toString();
								
								if((themSachVaoHoaDon(new ChiTietHoaDon(maHD, maSach, tenSach, soLuong, donGia, thanhTien))) == false) {
									JOptionPane.showMessageDialog(null, "Sách Này Đã Tồn Tại Trong Hóa Đơn" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
								}else {
									String donGiaFormatted = decimalFormat.format(donGia);
									String thanhTienFormatted = decimalFormat.format(thanhTien);
									Object[] row_sanPham = {maSach , tenSach , soLuong , donGiaFormatted , thanhTienFormatted};
									DataModel.addRow(row_sanPham);
									
									tongTienTrongHoaDon = tinhTongTienTrongHoaDon();
									
									String formattedTongTien = decimalFormat.format(tongTienTrongHoaDon);
									jtTt.setText("" + formattedTongTien + "đ");
								}
								
							}
						}
						
					}
				}
			
					
			}
				
				
				
			
				jtTn.setText("");
				jtTthua.setText("");
				
				
			
		}
			
			
		
			
			
		}else if(o.equals(btnTaoHd)) {
				
				String tienNhan = "" + jtTn.getText();
				
				if(tienNhan.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Tiền Nhận Không Được Để Trống" , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					boolean succes = isMatchTienNhan(jtTn.getText());
					if(succes == true) {
						String maHD = tfMhd.getText();
						String maNv = tfMnv.getText();
						String maKh = (String) comboxMaKH.getSelectedItem();
						String vat_tren_tf = jtVAT.getText();
						
//						VAT vao db
						float vat_vao_db = 0;
						if(vat_tren_tf.trim().equals("8%")) {
							 vat_vao_db = (float) 0.08;
						}else if (vat_tren_tf.trim().equals("0%")) {
							vat_vao_db = (float) 0.0;
						}
						
						

						String maQuay = (String) comboxMaQuay.getSelectedItem();
						
						String uuDai_tren_tf = jtUd.getText();
						float uuDaiVaoDB = 0;
						if(uuDai_tren_tf.equals("5%")) {
							uuDaiVaoDB = (float) 0.05;
						}else if (uuDai_tren_tf.equals("10%")) {
							uuDaiVaoDB = (float) 0.1;
						}else if(uuDai_tren_tf.trim().equals("15%")) {
							uuDaiVaoDB = (float) 0.15;
						}else if(uuDai_tren_tf.trim().equals("30%")) {
							uuDaiVaoDB = (float) 0.3;
						}
						
						double tongTien = tinhTongTienTrongHoaDon();
						hd_dao.addHoaDon(maHD, maNv, maKh, vat_vao_db, maQuay, uuDaiVaoDB, tongTien);
						
						
//						Kết thục việc thêm hóa đơn vào database
						
						
						
//						Them Chi Tiet Hoa Don vao database (maHD , maSach , tenSach , soLuong , donGia ,  thanhTien	)
						
					
						for (ChiTietHoaDon cthd : ds_cthd) {				
							cthd_dao.addChiTietHoaDon(cthd);
						}
						
						dsSanPhamTrongHoaDon = new ArrayList<ChiTietHoaDon>();
						ds_cthd = new ArrayList<ChiTietHoaDon>();			
						idHoaDon += 1;
						
						if(idHoaDon < 10) {
							tfMhd.setText("HD00" + idHoaDon);
						}else if (idHoaDon >= 10 && idHoaDon < 100) {
							tfMhd.setText("HD0" + idHoaDon);
						}else if(idHoaDon >= 100) {
							tfMhd.setText("HD" + idHoaDon);
						}
						
						DataModel.setRowCount(0);
						jtSl.setText("");
						jtDg.setText("");
						jtUd.setText("");
						jtTt.setText("");
						jtTht.setText("");
						jtVAT.setText("");
						jtTn.setText("");
						jtTthua.setText("");
						JOptionPane.showMessageDialog(null, "Tạo Hóa Đơn Thành Công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
		}

				
			
		}else if(o.equals(btn_xoaSanPhamTrongHoaDon)) {
			loadSanPhamLenHoaDon();
			
		}
		
	}
}
