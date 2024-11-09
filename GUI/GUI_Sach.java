package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import DAO.Sach_DAO;
import DAO.TacGia_DAO;
import DAO.TaiKhoan_Dao;
import DAO.TheLoai_DAO;

import entity.KhachHang;

import entity.NhaCungCap;
import entity.NhaXuatBan;
import entity.NhanVien;
import entity.Sach;
import entity.TacGia;
import entity.TaiKhoan;
import entity.TheLoai;
import DAO.KhachHang_DAO;
import DAO.NCC_DAO;
import DAO.NXB_DAO;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Button;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

public class GUI_Sach extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	protected static final Component ActionListener = null;
	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField txtMaSach;
	private JTextField txtTenSach;
	private JTextField txtDonGia;
	private JTextField txtsoLuong;
	private JTable table_Sach;
	protected JFileChooser browseImageFile;
	String filename = "";
	protected String selectedImagePath="";
	private JLabel lblIMGXM;
	private Sach_DAO sa=new Sach_DAO();
	private List<Sach> dss = new ArrayList<Sach>();
	DefaultTableModel DataModel;
	JComboBox comboBox_TheLoai;
	JComboBox comboBox_MaNCC;
	JComboBox comboBox_NXB;
	JComboBox comboBox_MaTG;
	
	JButton btnLayAnhTTXM_;
	
	
	private NCC_DAO nhacungcap_dao;
	private TheLoai_DAO theloai_dao;
	private NXB_DAO nhaxuatban_dao;
	private TacGia_DAO tacgia_dao;
	JButton btnThem;
	JButton btnCapNhat,btnXoa,btnLammoi;
	//JTable table;
	private DefaultComboBoxModel<String> cboModelTK;
	private JComboBox<String> comboBox;
	
	private List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	private JFrame frame = new JFrame();
	private String maNV = "USER";
	private String chucVu = "";
	private String hoTenNhanVien = "User name";
	
	private int idSach;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Sach frame = new GUI_Sach();
					Sach_DAO sa=new Sach_DAO();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_Sach() {
		
		
		ConnectDB.getInstance().connect();
		
		
		dstk = tk_dao.getAllTaiKhoan();
		dstk = tk_dao.getAllTaiKhoan();
		for (TaiKhoan taiKhoan : dstk) {
			if(taiKhoan.isTrangThai() == true) {
				maNV  = taiKhoan.getMaNV();
				chucVu = taiKhoan.getChucVu();
				hoTenNhanVien = taiKhoan.getHoTenNhanVien();
			}
		}
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
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
	                	if(chucVu.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_NhanVien guiNhanVien = new GUI_NhanVien();
		                	guiNhanVien.setVisible(true);
		                	dispose();
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F9) { //F9 : Xem Thông Tin Nhân Viên Bị Xóa 
	                	if(chucVu.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_XemTTNVbixoa guiNhanVienBiXoa = new GUI_XemTTNVbixoa();
		                	guiNhanVienBiXoa.setVisible(true);
		                	dispose();
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F10) { //F10 : Nhà Cung Cấp 
	                	if(chucVu.equals("Nhân Viên Bán Hàng")) {
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
		
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_Sach.class.getResource("/imgs/qls.png")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Quản lý sách");
		setBounds(100, 100, 1150, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 157, 75);
		panel.setBackground(new Color(17, 57, 70));
		contentPane.add(panel);
		
		 JLabel lblNewLabel_1 = new JLabel("");
		 panel.add(lblNewLabel_1);
		 lblNewLabel_1.setForeground(new Color(255, 255, 255));
		 lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		 lblNewLabel_1.setBackground(new Color(17, 57, 70));
		 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel = new JLabel("Quản lý sách");
		lblNewLabel.setBounds(450, 0, 382, 68);
		lblNewLabel.setForeground(new Color(234, 198, 150));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
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
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
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
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
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
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
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
        
        
        this.setLocationRelativeTo(null);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        
        lblIMGXM = new JLabel();
        //lblIMGXM.setForeground(new Color(255, 255, 255));
        //lblIMGXM.setBackground(new Color(255, 255, 255));
        lblIMGXM.setBounds(140, 127, 118, 129);
		ImageIcon imageicon = new ImageIcon(selectedImagePath);
		lblIMGXM.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		lblIMGXM.setIcon(imageicon);
		lblIMGXM.setBorder(BorderFactory.createLineBorder(Color.black));
		Image image = imageicon.getImage().getScaledInstance(lblIMGXM.getWidth(), lblIMGXM.getHeight(),
				Image.SCALE_SMOOTH);
		lblIMGXM.setIcon(new ImageIcon(image));
		contentPane.add(lblIMGXM);
        
        
         btnLayAnhTTXM_ = new JButton("Lấy Ảnh");
        btnLayAnhTTXM_.setBounds(10, 181, 96, 24);

		btnLayAnhTTXM_.setAlignmentY(Component.TOP_ALIGNMENT);
		
//		Su Kien Lay Anh
		btnLayAnhTTXM_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(
						"C:\\Users\\luong\\OneDrive\\Desktop\\BaoCaoCK\\DA_swing_HieuSach\\src\\imgs\\hinhSach");
				jfc.setDialogTitle("Chọn thư mục: ");
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
				jfc.addChoosableFileFilter(fnef);
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedImageFile = jfc.getSelectedFile();
					selectedImagePath = selectedImageFile.getAbsolutePath();
					JOptionPane.showMessageDialog(null, selectedImagePath);

					ImageIcon ii = new ImageIcon(selectedImagePath);

					Image image = ii.getImage().getScaledInstance(lblIMGXM.getWidth(), lblIMGXM.getHeight(),
							Image.SCALE_SMOOTH);

					lblIMGXM.setIcon(new ImageIcon(image));
				}

			}
		});
		
		contentPane.add(btnLayAnhTTXM_);
		
		
		
		
		
		JLabel lblMaSach = new JLabel("Mã Sách");
		lblMaSach.setBounds(10, 259, 102, 32);
		lblMaSach.setForeground(new Color(234, 198, 150));
		lblMaSach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblMaSach);
		txtMaSach = new JTextField();
		txtMaSach.setBounds(128, 259, 149, 32);
		txtMaSach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(txtMaSach);
		txtMaSach.setColumns(10);
		idSach = getIDSach();
		
		if(idSach < 10) {
			txtMaSach.setText("MS00" + idSach);
		}else if (idSach >= 10 && idSach < 100) {
			txtMaSach.setText("MS0" + idSach);
		}else if(idSach >= 100) {
			txtMaSach.setText("MS" + idSach);
		}
		
		txtMaSach.setEditable(false);
		
		
		JLabel lblTenSach = new JLabel("Tên sách");
		lblTenSach.setBounds(10, 292, 102, 32);
		lblTenSach.setForeground(new Color(234, 198, 150));
		lblTenSach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblTenSach);
		txtTenSach = new JTextField();
		txtTenSach.setBounds(128, 301, 149, 29);
		txtTenSach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTenSach.setColumns(10);
		contentPane.add(txtTenSach);
		
		JLabel lblMaNCC = new JLabel("Mã NCC");
		lblMaNCC.setBounds(10, 334, 102, 32);
		lblMaNCC.setForeground(new Color(234, 198, 150));
		lblMaNCC.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblMaNCC);
		comboBox_MaNCC = new JComboBox();
		comboBox_MaNCC.setBounds(128, 337, 149, 24);
		//comboBox_MaNCC.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5"}));
		comboBox_MaNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(comboBox_MaNCC);
		LoadComboBoxNCC();
		
		
		
		JLabel lblDonGiaNhap = new JLabel("Đơn giá nhập");
		lblDonGiaNhap.setBounds(10, 371, 118, 32);
		lblDonGiaNhap.setForeground(new Color(234, 198, 150));
		lblDonGiaNhap.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblDonGiaNhap);
		txtDonGia = new JTextField();
		txtDonGia.setBounds(128, 371, 149, 32);
		txtDonGia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDonGia.setColumns(10);
		contentPane.add(txtDonGia);
		
		JLabel lblsoLuong = new JLabel("Số lượng");
		lblsoLuong.setBounds(10, 413, 102, 32);
		lblsoLuong.setForeground(new Color(234, 198, 150));
		lblsoLuong.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblsoLuong);
		txtsoLuong = new JTextField();
		txtsoLuong.setBounds(128, 413, 149, 32);
		txtsoLuong.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtsoLuong.setColumns(10);
		contentPane.add(txtsoLuong);
		
		
		
		JLabel lblTheLoai = new JLabel("Thể loại");
		lblTheLoai.setBounds(10, 456, 102, 32);
		lblTheLoai.setForeground(new Color(234, 198, 150));
		lblTheLoai.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblTheLoai);
		comboBox_TheLoai = new JComboBox();
		comboBox_TheLoai.setBounds(128, 456, 149, 32);
		comboBox_TheLoai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(comboBox_TheLoai);
		LoadComboBoxTheLoai();
		
		
		JLabel lblMaNXB = new JLabel("Mã NXB");
		lblMaNXB.setBounds(10, 498, 102, 32);
		lblMaNXB.setForeground(new Color(234, 198, 150));
		lblMaNXB.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblMaNXB);
		comboBox_NXB = new JComboBox();
		comboBox_NXB.setBounds(128, 498, 149, 32);
		 comboBox_NXB .setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(comboBox_NXB );
		LoadComboBoxNXB();
		
		
		
		
		
		JLabel lblTacGia = new JLabel("Mã tác giả");
		lblTacGia.setBounds(10, 546, 102, 32);
		lblTacGia.setForeground(new Color(234, 198, 150));
		lblTacGia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblTacGia);
		 comboBox_MaTG = new JComboBox();
		 comboBox_MaTG.setBounds(128, 547, 149, 32);
		//comboBox_MaTG.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4"}));
		comboBox_MaTG.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(comboBox_MaTG);
		LoadComboBoxTG();
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(287, 180, 849, 350);
		String[] headers = "Mã sách; Tên sách; Tên NCC; Đơn giá nhập; Số lượng; Thể loại; Tên NXB; Tên tác giả; Hình ảnh".split(";");
		 DataModel = new DefaultTableModel (headers,0);
		
		scrollPane.setViewportView(table_Sach = new JTable(DataModel));
		table_Sach.setRowHeight(25);
		table_Sach.setAutoCreateRowSorter(true);
		table_Sach.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		contentPane.add(scrollPane);
		
		table_Sach.addMouseListener(new MouseAdapter() {
//			private ImageIcon img1;
//			private ImageIcon img3;

			@Override
			public void mouseClicked(MouseEvent e) {
				Sach_DAO ds = new Sach_DAO();
				List<Sach> list = ds.getAllSach();
				Sach xm = new Sach();

				if (list.size() != 0) {

					xm = list.get(0);

				}
				int row = table_Sach.getSelectedRow();
				if (row != -1) {
					
					txtMaSach.setText(table_Sach.getValueAt(row, 0).toString());
					txtTenSach.setText(table_Sach.getValueAt(row, 1).toString());
					((JComboBox) comboBox_MaNCC).setSelectedItem(table_Sach.getValueAt(row, 2).toString());
					txtDonGia.setText(table_Sach.getValueAt(row, 3).toString());
					txtsoLuong.setText(table_Sach.getValueAt(row, 4).toString());
					
					((JComboBox) comboBox_TheLoai).setSelectedItem(table_Sach.getValueAt(row, 5).toString());
					((JComboBox) comboBox_NXB).setSelectedItem(table_Sach.getValueAt(row, 6).toString());
					
			
					
					((JComboBox) comboBox_MaTG).setSelectedItem(table_Sach.getValueAt(row, 7).toString());
					Object value = table_Sach.getValueAt(row, 8);
					if (value != null) {
						selectedImagePath = value.toString();
						ImageIcon icon = (new ImageIcon(new ImageIcon(table_Sach.getValueAt(row, 8).toString()).getImage()
								.getScaledInstance(lblIMGXM.getWidth(), lblIMGXM.getHeight(), Image.SCALE_SMOOTH)));
						lblIMGXM.setIcon(icon);
					} else {
					    System.out.println("Lỗi không hiển thị ảnh");
					}
					
				}
			}
		});
		
		
		
		
		
		
		
		 btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setBounds(164, 595, 133, 38);
		btnCapNhat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCapNhat.setBackground(new Color(17, 57, 70));
		btnCapNhat.setForeground(new Color(249, 224, 187));
		contentPane.add(btnCapNhat);
		

		
    	
		btnThem = new JButton("Thêm");
		btnThem.setBounds(10, 595, 133, 38);
		btnThem.setForeground(new Color(249, 224, 187));
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThem.setBackground(new Color(17, 57, 70));
		contentPane.add(btnThem);
		btnThem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    }
		});

		
		btnXoa = new JButton("Xóa");
		btnXoa.setForeground(new Color(249, 224, 187));
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoa.setBackground(new Color(17, 57, 70));
		btnXoa.setBounds(328, 595, 133, 38);
		contentPane.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ConnectDB a = new ConnectDB();
					Connection con = a.getConnection();
					PreparedStatement ps = con.prepareStatement("Delete Sach where maSach=?");
					ps.setString(1, table_Sach.getValueAt(table_Sach.getSelectedRow(), 0).toString());

					if (JOptionPane.showConfirmDialog(ActionListener, "Bạn Muốn Xóa Sách Này !", "Xác Nhận",
							JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
						ps.executeUpdate();
						DataModel.setRowCount(0);
						DoDuLieuSach();

					}

				} catch (Exception e2) {
					System.out.println(e2.toString());
				}
			}
		});
		
		 btnLammoi = new JButton("Làm mới");
		 btnLammoi.setForeground(new Color(249, 224, 187));
		 btnLammoi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		 btnLammoi.setBackground(new Color(17, 57, 70));
		 btnLammoi.setBounds(507, 595, 133, 38);
		contentPane.add(btnLammoi);
		btnLammoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object o = e.getSource();
				if (o.equals(btnLammoi)) {
					try {
						lamMoi();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		
		
		cboModelTK = new DefaultComboBoxModel<String>();
		comboBox = new JComboBox<String>(cboModelTK);
		comboBox.setForeground(new Color(249, 224, 187));
		comboBox.setBackground(new Color(17, 57, 70));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setBounds(718, 558, 135, 42);
		contentPane.add(comboBox);
		updateJComboBox();
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_4.setBounds(854, 558, 142, 42);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/search_img.png")));
		btnSearch.setBackground(new Color(17, 57, 70));
		btnSearch.setBounds(996, 558, 45, 42);
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
					

					if (o.equals(comboBox)) {
						textField_4.selectAll();
						textField_4.requestFocus();
					}

				}
			}
		});
		
		

		
		//HIển thị dữ liệu lên		
				DoDuLieuSach();
		
		
		
		
		
		
		JLabel lblNewLabel_4 = new JLabel("l");
		lblNewLabel_4.setBounds(0, 0, 1136, 663);
		lblNewLabel_4.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/gui_ncc.png")));
		contentPane.add(lblNewLabel_4);
		
		
		
		
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		//btnXoa.addActionListener(this);
	}
	
	
	//Hàm phương thức
	public void LoadComboBoxNCC() {
		NCC_DAO TL = new NCC_DAO();
		List<NhaCungCap> list = TL.getAllNCC();
		for (NhaCungCap lxm : list) {
			comboBox_MaNCC.addItem(lxm.getTenNCC());
		}
	}
	public void LoadComboBoxTheLoai() {
		TheLoai_DAO TL = new TheLoai_DAO();
		List<TheLoai> list = TL.getAllTL();
		for (TheLoai lxm : list) {
			comboBox_TheLoai.addItem(lxm.getTenTheLoai());
		}
	}
	public void LoadComboBoxNXB() {
		NXB_DAO TL = new NXB_DAO();
		List<NhaXuatBan> list = TL.getAllNXB();
		for (NhaXuatBan lxm : list) {
			comboBox_NXB.addItem(lxm.getTenNXB());
		}
	}
	
	public void LoadComboBoxTG() {
		TacGia_DAO TL = new TacGia_DAO();
		List<TacGia> list = TL.getAllTG();
		for (TacGia lxm : list) {
			comboBox_MaTG.addItem(lxm.getTenTacGia());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		
		if (o.equals(btnThem)) {
			if (checkEmpty()) {
				Sach sach = taoEntityXM();
				if (sa.addSach(sach)) {
					idSach += 1;
					if(idSach < 10) {
						txtMaSach.setText("MS00" + idSach);
					}else if (idSach >= 10 && idSach < 100) {
						txtMaSach.setText("MS0" + idSach);
					}else if(idSach >= 100) {
						txtMaSach.setText("MS" + idSach);
					}
					refreshPanelLienQuan();

				} else {
					JOptionPane.showMessageDialog(null, "Có lỗi");
				}
				
				JOptionPane.showMessageDialog(null, "Thêm Thành Công");
			}
				
		}
		
		else if(o.equals(btnCapNhat)) {
			int row = table_Sach.getSelectedRow();
	        if (row < 0) {
	            JOptionPane.showMessageDialog(btnCapNhat, "Vui lòng chọn một Sách để cập nhật!");
	            return;
	        }
			
	        Sach sach = taoEntityXM();
			if (sa.updateSach(sach)) {
				JOptionPane.showMessageDialog(null, "Đã Hoàn Thành Việc Cập Nhật!");
				refreshPanelLienQuan();
				
				
			}else {
		        JOptionPane.showMessageDialog(null, "Có lỗi");
		    }
			
			
		
			
			
		}
		
	}
	//Hàm update dữ liệu  table	sau 1 sự kiện gì đó
		private void updateTable() {
			table_Sach.clearSelection();
			String id;
			Sach_DAO sa = new Sach_DAO();
			List<Sach> listXM = sa.getAllSach();
	for(Sach n:sa.getAllSach()) {
				
				Object[] rowData= {n.getMaSach(),n.getTenSach(),n.getNhacungcap().getTenNCC(),n.getDonGiaNhap(),n.getSoLuong(),n.getTheloai().getTenTheLoai(),n.getNhaxuatban().getTenNXB(),n.getTacgia().getTenTacGia(),n.getHinhanh()};
				id=n.getHinhanh();
				DataModel.addRow(rowData);
			}
			table_Sach.setModel(DataModel);
		}
		private void selecttedRow(int row) {
			if(row!=-1) {
				table_Sach.setRowSelectionInterval(row, row);
				table_Sach.scrollRectToVisible(table_Sach.getCellRect(row, row, true));
			}
		}
	
	public Sach taoEntityXM() {
		String maXM = (String) txtMaSach.getText().trim().toString() + "";
		String tenXM = (String) txtTenSach.getText().trim().toString() + "";
		nhacungcap_dao=new NCC_DAO();
		String tenLX = comboBox_MaNCC.getSelectedItem().toString() + "";
		List<NhaCungCap> listncc = nhacungcap_dao.getncctheoTen(tenLX);
		NhaCungCap ncc = listncc.get(0);

		Double donGia = Double.parseDouble((String) txtDonGia.getText().trim().toString());
		int soLuongTon = Integer.parseInt((String) txtsoLuong.getText().trim().toString());

		
		theloai_dao=new TheLoai_DAO();
		String tenMS = comboBox_TheLoai.getSelectedItem().toString() + "";
		List<TheLoai> listTheloai = theloai_dao.gettltheoTen(tenMS);
		TheLoai theloai = listTheloai.get(0);

		
		nhaxuatban_dao=new NXB_DAO();
		String tenHSX = comboBox_NXB.getSelectedItem().toString() + "";
		List<NhaXuatBan> listNXB = nhaxuatban_dao.getNXBtheoTen(tenHSX);
		NhaXuatBan nhaxuatban = listNXB.get(0);

		tacgia_dao=new TacGia_DAO();
		String list = comboBox_MaTG.getSelectedItem().toString() + "";
		List<TacGia> listtg = tacgia_dao.getTGtheoTen(list);
		TacGia tg = listtg.get(0);

		

		String hinhAnh = selectedImagePath;
	    if (selectedImagePath == null || selectedImagePath.isEmpty()) {
	        // Nếu không có hình ảnh mới được chọn, giữ nguyên hình ảnh hiện tại từ dòng được chọn
	        hinhAnh = table_Sach.getValueAt(table_Sach.getSelectedRow(), 8).toString();
	    }
		return new Sach(maXM, tenXM , ncc, donGia, soLuongTon, theloai, nhaxuatban, tg, hinhAnh);
	}
	
	
	
	
	//hiển thị lên giao diện table	
		public void DoDuLieuSach() {
			String id;

			sa =new Sach_DAO();
			table_Sach.setRowHeight(25);
			for(Sach n:sa.getAllSach()) {
				
				Object[] rowData= {n.getMaSach(),n.getTenSach(),n.getNhacungcap().getTenNCC(),n.getDonGiaNhap(),n.getSoLuong(),n.getTheloai().getTenTheLoai(),n.getNhaxuatban().getTenNXB(),n.getTacgia().getTenTacGia(),n.getHinhanh()};
				id=n.getHinhanh();
				DataModel.addRow(rowData);
			}
		}

public void refreshPanelLienQuan() {
		
		DataModel.setRowCount(0);
		DoDuLieuSach();

		
		
	}
	
	

	
	private void lamMoi() throws SQLException {
		
		if(idSach < 10) {
			txtMaSach.setText("MS00" + idSach);
		}else if (idSach >= 10 && idSach < 100) {
			txtMaSach.setText("MS0" + idSach);
		}else if(idSach >= 100) {
			txtMaSach.setText("MS" + idSach);
		}
		
		txtTenSach.setText("");
		txtDonGia.setText("");
		txtsoLuong.setText("");
		
		

		// anh reset
		
		// Ten reset
		comboBox_MaNCC.setSelectedIndex(0);
		comboBox_TheLoai.setSelectedIndex(0);
		comboBox_NXB.setSelectedIndex(0);
		comboBox_MaTG.setSelectedIndex(0);
		

		xoaBang();
		updateTable();
	}
	
	private void xoaBang() {
		for (int j = 0; j < table_Sach.getRowCount(); j++) {
			DataModel.removeRow(j);
			j--;
		}
	}
	
	
	private void updateJComboBox() {
		cboModelTK.addElement("Tìm Mã Sách");
		cboModelTK.addElement("Tìm Tên Sách");
		
	}
	
	
	

	private void timTheoTen() throws SQLException {
		table_Sach.clearSelection();
		String ten = textField_4.getText();
		Sach_DAO daosach=new Sach_DAO();
		List<Sach> listkh = daosach.getKHTheoTen(ten);
		if (listkh.size() == 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy sách");

		} else {
			for(Sach n:listkh) {
				
				Object[] rowData= {n.getMaSach(),n.getTenSach(),n.getNhacungcap().getTenNCC(),n.getDonGiaNhap(),n.getSoLuong(),n.getTheloai().getTenTheLoai(),n.getNhaxuatban().getTenNXB(),n.getTacgia().getTenTacGia(),n.getHinhanh()};
				//id=n.getHinhanh();
				DataModel.addRow(rowData);
				JOptionPane.showMessageDialog(null, "Tìm thấy Sách Này  ");
			}

			table_Sach.setModel(DataModel);
		}
	}
	
	private void timTheoMa() {
		table_Sach.clearSelection();
		String ma = textField_4.getText();
		Sach_DAO daosach=new Sach_DAO();
		Sach sach=new Sach();
		
		sach = daosach.getKHTheoMa(ma);

		if (ma == null) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy Sách ");
		} else {
			
			Object[] rowData= {sach.getMaSach(),sach.getTenSach(),sach.getNhacungcap().getTenNCC(),sach.getDonGiaNhap(),sach.getSoLuong(),sach.getTheloai().getTenTheLoai(),sach.getNhaxuatban().getTenNXB(),sach.getTacgia().getTenTacGia(),sach.getHinhanh()};
			//id=n.getHinhanh();
			DataModel.addRow(rowData);
			
			JOptionPane.showMessageDialog(null, "Tìm thấy Sách ");
		}
		table_Sach.setModel(DataModel);
	}
	
	
	
	
	//Ràng buộc dữ liệu khi nhập
	private Boolean checkEmpty() {
		String ma = txtMaSach.getText();
		String tensach = txtTenSach.getText();
		String tg = txtDonGia.getText();
		String sl = txtsoLuong.getText();

		if (ma.trim().equals("")) {
			return showErrorTx(txtMaSach, "Mã Sách không được để trống");

		} else if (!(ma.length() > 0 && ma.matches("^(MS)[0-9]{3}$"))) {
			JOptionPane.showMessageDialog(null, "Không đúng định dạng" + "\nVui lòng nhập theo VD: MS001");
			txtMaSach.selectAll();
			txtMaSach.requestFocus();
			return false;
		}else if (sa.isMaSachTonTai(ma)) {
	        JOptionPane.showMessageDialog(this, "Mã Sách đã tồn tại. Vui lòng chọn mã khác.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
	        txtMaSach.selectAll();
	        txtMaSach.requestFocus();
	        return false;
	    
		} else if (tensach.trim().equals("")) {
			return showErrorTx(txtTenSach,"Tên Sách không được để trống");
		}else if (sa.istenSachTonTai(tensach)) {
	        JOptionPane.showMessageDialog(this, "Tên Sách đã tồn tại. Vui lòng nhập tên khác.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
	        txtTenSach.selectAll();
	        txtTenSach.requestFocus();
	        return false;
			
			
		} else if (tg.trim().equals("")) {
			return showErrorTx(txtDonGia, "Đơn Giá không được để trống");

		} else if (Double.parseDouble(txtDonGia.getText())<0) {
			JOptionPane.showMessageDialog(null, "Đơn Giá nhập vào Không Hợp Lệ" );
			txtDonGia.selectAll();
			txtDonGia.requestFocus();
			return false;
		} else if (sl.trim().equals("")) {
			return showErrorTx(txtsoLuong, "Số Lượng không được để trống");
		}else if (Integer.parseInt(txtsoLuong.getText())<0) {
			JOptionPane.showMessageDialog(null, "Số Lượng nhập vào Không Hợp Lệ" );
			txtsoLuong.selectAll();
			txtsoLuong.requestFocus();
			return false;
		}
		return true;
	}
	
	private Boolean showErrorTx(JTextField tx, String errorInfo) {
		JOptionPane.showMessageDialog(tx, errorInfo);
		tx.requestFocus();
		return false;
	}
	
	public int getIDSach() {
		dss = sa.getAllSach();
		if(dss.isEmpty()) {
			return 1;
		}else {
			int i = 1;
			for (Sach dsSachDaDuocTai : dss) {
				i++;
			}
			return i;
		}	
	}
}
