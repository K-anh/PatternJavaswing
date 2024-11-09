package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.GUI_TrangChu;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class LoadVaoHieuSach extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel lblphamtramload;
	private static JLabel lblHienThiTrangThai;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public LoadVaoHieuSach() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoadVaoHieuSach.class.getResource("/imgs/qls.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 322);
		setLocation(new java.awt.Point(450, 150));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 255));
		panel.setBounds(0, 0, 566, 297);
		contentPane.add(panel);
		panel.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(0, 285, 564, 14);
		progressBar.setForeground(new Color(204, 102, 0));
		panel.add(progressBar);
		
		lblphamtramload = new JLabel("%");
		lblphamtramload.setBackground(new Color(210, 105, 30));
		lblphamtramload.setBounds(223, 64, 246, 165);
		lblphamtramload.setForeground(new Color(210, 105, 30));
		lblphamtramload.setFont(new Font("Tahoma", Font.BOLD, 70));
		panel.add(lblphamtramload);
		
		lblHienThiTrangThai = new JLabel("");
		lblHienThiTrangThai.setBounds(0, 226, 607, 49);
		lblHienThiTrangThai.setForeground(Color.WHITE);
		lblHienThiTrangThai.setBackground(Color.RED);
		lblHienThiTrangThai.setFont(new Font("Tahoma", 1, 12));
		panel.add(lblHienThiTrangThai);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(0, 276, 607, 11);
		progressBar_1.setEnabled(false);
		progressBar_1.setForeground(new Color(255, 255, 255));
		progressBar_1.setIndeterminate(true);
		progressBar_1.setBackground(new Color(210, 105, 30));
		panel.add(progressBar_1);
		
		lblNewLabel = new JLabel("Hiệu Sách HTND");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel.setBounds(155, 0, 267, 44);
		panel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\PTUD_Project\\test\\src\\imgs\\nenload.jpg"));
		lblNewLabel_1.setBounds(0, 0, 564, 275);
		panel.add(lblNewLabel_1);
	}
	
	
	public static void main(String[] args) {
		LoadVaoHieuSach loadShopTT = new LoadVaoHieuSach();
		loadShopTT.setVisible(true);
		try {
			
			for (int i = 0; i <= 100; i++) {
				int value = loadShopTT.progressBar.getValue();
                if (value < loadShopTT.progressBar.getMaximum()) {
                	loadShopTT.progressBar.setValue(value + 1);
                    
                    if (i == 50) {
                    	lblHienThiTrangThai.setText("Đang tải tài nguyên...");
                    } else if (i == 80) {
                    	lblHienThiTrangThai.setText("Tải thành công...");
                    }
                } 
				Thread.sleep(40);
				loadShopTT.progressBar.setValue(i);
				loadShopTT.lblphamtramload.setText(Integer.toString(i)+"%");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		new GUI_DangNhap().setVisible(true);
		loadShopTT.dispose();
	}
}
