package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import entity.NhaXuatBan;
import entity.TacGia;
import entity.TheLoai;


public class TacGia_DAO {
	
	ArrayList<entity.TacGia> dstg;
	public TacGia_DAO() {
		dstg = new ArrayList<TacGia>();
	}
public List<TacGia> getAllTG(){
		
		List<TacGia> dsTG=new ArrayList<TacGia>();
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		try {
			String sql="select * from TacGia";;
			Statement statement =con.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			
			while(rs.next()) {

				dsTG.add(new TacGia(rs.getString("maTacGia"),
						rs.getString("tenTacGia")
						
						
						
						
						
						
						
						));
						
				
									
						
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsTG;
	}

public List<TacGia> getTGtheoTen(String ten) {
	List<TacGia> dstg = new ArrayList<TacGia>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from TacGia AS tenTacGia where tenTacGia like N'%" + ten + "%'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
	
			String maTacGia = rs.getString(1);
			String tenTacGia = rs.getString(2);
			
			TacGia tg = new TacGia(maTacGia, tenTacGia);
			dstg.add(tg);
			

		}

	} catch (SQLException e) {
		e.printStackTrace();
	
}
	return dstg;
}


public void addTG(TacGia TG) {
	Connection con = ConnectDB.getInstance().getConnection();
    PreparedStatement stmt = null;
   
    try {
        stmt = con.prepareStatement("insert into TacGia values(?,?)");
        stmt.setString(1, TG.getMaTacGia());
        stmt.setString(2, TG.getTenTacGia());
       
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
	}finally {
		
	}
}


public boolean xoaTG(int maTG) {
	Connection con = ConnectDB.getInstance().getConnection();
	PreparedStatement stmt = null;
	int n = 0;
	try {
		stmt = con.prepareStatement("DELETE FROM TacGia WHERE maTacGia = ?");
		stmt.setInt(1, maTG);
		n = stmt.executeUpdate();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return n > 0; 
}



public boolean isMaTGTonTai(String maTG) {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        con = ConnectDB.getInstance().getConnection();
        stmt = con.prepareStatement("SELECT COUNT(*) FROM TacGia WHERE maTacGia = ?");
        stmt.setString(1, maTG);

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
public List<TacGia> getTGTheoTen(String ten) {
	List<TacGia> dstg = new ArrayList<TacGia>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from TacGia AS tenTheLoai where tenTacGia like N'%" + ten + "%'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
	
			String maTG = rs.getString(1);
			String tenTG = rs.getString(2);
			
			TacGia tg=new TacGia(maTG, tenTG);
			dstg.add(tg);
			
			
			
			

		}

	} catch (SQLException e) {
		e.printStackTrace();
	
}
	return dstg;
}

//Tìm TL theo mã 
public TacGia getTGTheoMa(String ma) {
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	String sql = "select * from TacGia where maTacGia = '" + ma + "'";
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			String maTG = rs.getString(1);
			String tenTG = rs.getString(2);

			TacGia tg=new TacGia(maTG, tenTG);
			dstg.add(tg);
			
			return tg;
			
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return null;

}

}
