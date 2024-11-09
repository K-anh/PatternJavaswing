package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import entity.HoaDon;

import entity.NhanVien;

public class HoaDon_Dao {
	
	public List<HoaDon> getAllHoaDon(){
		List<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			String sql = "Select * from HoaDon";
			Statement stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while(rs.next()) {
				dsHoaDon.add(new HoaDon(
						rs.getString("maHD"),
						rs.getString("maNV"),
						rs.getString("maKH"),
						rs.getDate("ngayLap"),
						rs.getFloat("VAT"),
						rs.getString("maQuay"),
						rs.getFloat("uuDai"),
						rs.getFloat("tongTien")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHoaDon;
	}
	
	
//	Function add hoa don vao database
	public void addHoaDon(String maHD, String maNV, String maKH  , float VAT , String maQuay , float uuDai , double tongTien) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("Insert HoaDon(maHD, maNV, maKH  , VAT , maQuay , uuDai , tongTien) values(?,?,?,?,?,?,?)");
			stmt.setString(1, maHD);
			stmt.setString(2, maNV);
			stmt.setString(3, maKH);
			stmt.setFloat(4, VAT);
			stmt.setString(5, maQuay);
			stmt.setFloat(6, uuDai);
			stmt.setDouble(7, tongTien);
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
