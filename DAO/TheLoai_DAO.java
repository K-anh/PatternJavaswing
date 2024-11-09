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
import entity.TheLoai;


public class TheLoai_DAO {
	
	ArrayList<entity.TheLoai> dstl;
	public TheLoai_DAO() {
		dstl = new ArrayList<TheLoai>();
}
public List<TheLoai> getAllTL(){
		
		List<TheLoai> dsTL=new ArrayList<TheLoai>();
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		try {
			String sql="select * from TheLoai";;
			Statement statement =con.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			
			while(rs.next()) {

				dsTL.add(new TheLoai(rs.getString("maTheLoai"),
						rs.getString("tenTheLoai")
						
						
						
			
						
						
						
						));
						
				
									
						
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsTL;
	}

public List<TheLoai> gettltheoTen(String ten) {
	List<TheLoai> dsTheLoai = new ArrayList<TheLoai>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from TheLoai AS tenTheLoai where tenTheLoai like N'%" + ten + "%'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
	
			String maTheLoai = rs.getString(1);
			String tenTheLoai = rs.getString(2);

			TheLoai TL = new TheLoai(maTheLoai, tenTheLoai);
			dsTheLoai.add(TL);
			

		}

	} catch (SQLException e) {
		e.printStackTrace();
	
}
	return dsTheLoai;
}

public void addTL(TheLoai tl) {
	Connection con = ConnectDB.getInstance().getConnection();
    PreparedStatement stmt = null;
   
    try {
        stmt = con.prepareStatement("insert into TheLoai values(?,?)");
        stmt.setString(1, tl.getMaTheLoai());
        stmt.setString(2, tl.getTenTheLoai());
       
        
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
	}finally {
		
	}
}





public boolean isMaTLTonTai(String maTL) {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        con = ConnectDB.getInstance().getConnection();
        stmt = con.prepareStatement("SELECT COUNT(*) FROM TheLoai WHERE maTheLoai = ?");
        stmt.setString(1, maTL);

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
public boolean isTenTLTonTai(String tenTL) {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        con = ConnectDB.getInstance().getConnection();
        stmt = con.prepareStatement("SELECT COUNT(*) FROM TheLoai WHERE tenTheLoai = ?");
        stmt.setString(1, tenTL);

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


//Tìm kiếm


// tìm TL theo tên
public List<TheLoai> getTLTheoTen(String ten) {
	List<TheLoai> dstl = new ArrayList<TheLoai>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from TheLoai AS tenTheLoai where tenTheLoai like N'%" + ten + "%'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
	
			String maTL = rs.getString(1);
			String tenTL = rs.getString(2);
			
			TheLoai TL=new TheLoai(maTL, tenTL);
			dstl.add(TL);
			
			//return ncc;
			
			

		}

	} catch (SQLException e) {
		e.printStackTrace();
	
}
	return dstl;
}

// Tìm TL theo mã 
public TheLoai getTLTheoMa(String ma) {
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from TheLoai where maTheLoai = '" + ma + "'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			String maTL = rs.getString(1);
			String tenTL = rs.getString(2);
			TheLoai TL=new TheLoai(maTL, tenTL);
			dstl.add(TL);
			
			return TL;
			//return ncc;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return null;

}

}
