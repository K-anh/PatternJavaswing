package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import entity.KhachHang;
import entity.NhaXuatBan;
import entity.TacGia;
import entity.TheLoai;

public class NXB_DAO {

	ArrayList<entity.NhaXuatBan> dsnxb;
	public NXB_DAO() {
		dsnxb = new ArrayList<NhaXuatBan>();
	}
public List<NhaXuatBan> getAllNXB(){
		
		List<NhaXuatBan> dsnxb=new ArrayList<NhaXuatBan>();
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		try {
			String sql="select * from NhaXuatBan";;
			Statement statement =con.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			
			while(rs.next()) {

				dsnxb.add(new NhaXuatBan(rs.getString("maNXB"),
						rs.getString("tenNXB"),
						
						
						rs.getString("diaChi")
						
						
				
						
						));
						
				
									
						
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnxb;
	}

public List<NhaXuatBan> getNXBtheoTen(String ten) {
	List<NhaXuatBan> dsNXB = new ArrayList<NhaXuatBan>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from NhaXuatBan AS tenNXB where tenNXB like N'%" + ten + "%'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
	
			String maNXB = rs.getString(1);
			String tenNXB = rs.getString(2);
			String diaChi = rs.getString(3);
			NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, diaChi);
			dsNXB.add(nxb);
			

		}

	} catch (SQLException e) {
		e.printStackTrace();
	
}
	return dsNXB;
}

public void addNXB(NhaXuatBan nxb) {
	Connection con = ConnectDB.getInstance().getConnection();
    PreparedStatement stmt = null;
   
    try {
        stmt = con.prepareStatement("insert into NhaXuatBan values(?,?,?)");
        stmt.setString(1, nxb.getMaNXB());
        stmt.setString(2, nxb.getTenNXB());
        stmt.setString(3, nxb.getDiaChi());
        
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
	}finally {
		
	}
}


public boolean xoaNXB(int maNXB) {
	Connection con = ConnectDB.getInstance().getConnection();
	PreparedStatement stmt = null;
	int n = 0;
	try {
		stmt = con.prepareStatement("DELETE FROM NhaXuatBan WHERE maNXB = ?");
		stmt.setInt(1, maNXB);
		n = stmt.executeUpdate();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return n > 0; 
}


public boolean isManxbTonTai(String manxb) {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        con = ConnectDB.getInstance().getConnection();
        stmt = con.prepareStatement("SELECT COUNT(*) FROM NhaXuatBan WHERE maNXB = ?");
        stmt.setString(1, manxb);

        rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Đóng kết nối và tài nguyên
        //close(rs);
        //close(stmt);
        //close(con);
    }

    return false;
}


//tìm TL theo tên
public List<NhaXuatBan> getNXBTheoTen(String ten) {
	List<NhaXuatBan> nxb = new ArrayList<NhaXuatBan>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from NhaXuatBan AS tenNXB where tenNXB like N'%" + ten + "%'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
	
			String manxb = rs.getString(1);
			String tennxb = rs.getString(2);
			String diaChi=rs.getString(3);
			NhaXuatBan nxbb=new NhaXuatBan(manxb, tennxb, diaChi);
			
			dsnxb.add(nxbb);
			
			
			
			

		}

	} catch (SQLException e) {
		e.printStackTrace();
	
}
	return dsnxb;
}

//Tìm TL theo mã 
public NhaXuatBan getNXBTheoMa(String ma) {
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from NhaXuatBan where maNXB = '" + ma + "'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			String manxb = rs.getString(1);
			String tennxb = rs.getString(2);
			String diaChi=rs.getString(3);
			NhaXuatBan nxbb=new NhaXuatBan(manxb, tennxb, diaChi);
			dsnxb.add(nxbb);
			
			return nxbb;
			
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return null;

}

}
