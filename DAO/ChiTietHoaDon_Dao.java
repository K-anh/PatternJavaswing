package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;

public class ChiTietHoaDon_Dao {
	
//	Function lấy chi tiết hóa đơn trong database
	public List<ChiTietHoaDon> getAllChiTietHoaDon(){
		List<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<ChiTietHoaDon>();
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			String sql = "Select * from CTHoaDon";
			Statement stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while(rs.next()) {
				dsChiTietHoaDon.add(new ChiTietHoaDon(
						rs.getString("maHD"),
						rs.getString("maSach"),
						rs.getString("tenSach"),
						rs.getInt("soLuong"),
						rs.getFloat("donGia"),
						rs.getDouble("thanhTien")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsChiTietHoaDon;
	}
	
	
//	Function add chi tiết hóa đơn trong database
	public void addChiTietHoaDon(ChiTietHoaDon cthd) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		
		try {
		stmt = con.prepareStatement("Insert CTHoaDon(maHD , maSach , tenSach , soLuong , donGia ,  thanhTien) values(?,?,?,?,?,?)");
			stmt.setString(1, cthd.getMaHD());
			stmt.setString(2, cthd.getMaSach());
			stmt.setString(3, cthd.getTenSach());
			stmt.setInt(4, cthd.getSoLuong());
			stmt.setFloat(5, cthd.getDonGia());
			stmt.setDouble(6, cthd.getThanhTien());
			stmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			close(stmt);
		}
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
