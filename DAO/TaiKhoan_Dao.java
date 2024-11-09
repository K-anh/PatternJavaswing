package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import GUI.GUI_QuenMKOTP;
import entity.ChiTietHoaDon;
import entity.Mycombo_MaKH;
import entity.TaiKhoan;

public class TaiKhoan_Dao {
	public String otpCode;
	public String otpInMail;
	public String otpCodeInPut;
	
	
	public List<TaiKhoan> getAllTaiKhoan(){
		Connection con = ConnectDB.getInstance().getConnection();
		List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
		try {
			String sql = "Select * from TaiKhoan";
			Statement stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while(rs.next()) {
				dstk.add(new TaiKhoan(
						rs.getString("maNV"),
						rs.getString("hoTenNV"),
						rs.getString("matKhau"),
						rs.getString("chucVu"),
						rs.getString("email"),
						rs.getString("OTP"),
						rs.getBoolean("trangThai")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return dstk;
	}
	
	
//	Function dung de dang nhap Update Trang Thai = 1 (Dang Hoat Dong)
	public void dangNhap(String taiKhoan) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		 String sql = "UPDATE TaiKhoan SET trangThai = ? WHERE maNV = ?";
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setBoolean(1, true);
			stmt.setString(2, taiKhoan);
//			Thuc Hien Update
			stmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}finally {
			close(stmt);
		}
		
	}
	
//	Function dung de dang xuat Update Trang Thai = 0 (Khong Hoat Dong)
	public void dangXuat(String taiKhoan) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		
		 String sql = "UPDATE TaiKhoan SET trangThai = ? WHERE maNV = ?";
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setBoolean(1, false);
			stmt.setString(2, taiKhoan);
//			Thuc Hien Update
			stmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void doiMatKhau(String taiKhoan , String matKhauMoi) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		
		String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE maNV = ?";
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, matKhauMoi);
			stmt.setString(2, taiKhoan);
//			Thuc Hien Update
			stmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	
	public void quenMatKhau(String maNV , String otp) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		
		String sql = "UPDATE TaiKhoan SET OTP = ? WHERE maNV = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, otp);
			stmt.setString(2, maNV);
//			Thuc Hien Update
			stmt.executeUpdate();
			
			
			otpCode = otp;			
			
		} catch (SQLException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}finally {
			
			close(stmt);
		}
		
	}
	

	
	public void test() {
		System.out.println("OTP in mail : " + otpCode);
	}
	
	
	
	

	
	
	
	private void close(PreparedStatement stmt) {
		// TODO Auto-generated method stub
		if(stmt != null) {
			try {
				stmt.close();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
	}
	
	
}
