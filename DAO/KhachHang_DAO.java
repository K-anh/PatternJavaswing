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
import entity.Mycombo_MaKH;

import java.util.Date;


public class KhachHang_DAO {
	
	ArrayList<entity.KhachHang> dskh;
	public KhachHang_DAO() {
		dskh = new ArrayList<KhachHang>();

	}	
public List<KhachHang> getAllKH(){
		
		List<KhachHang> dsKH=new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		try {
			String sql="select * from KhachHang";;
			Statement statement =con.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			
			while(rs.next()) {

				dsKH.add(new KhachHang(rs.getString("maKH"),
						rs.getString("tenKH"),
						rs.getDate("ngaySinh"),
						
						rs.getString("diaChi"),
						rs.getString("sdt")
						
						
						
						));
						
				
									
						
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsKH;
	}


public void addKH(KhachHang khach) {
	Connection con = ConnectDB.getInstance().getConnection();
    PreparedStatement stmt = null;
   
    try {
        stmt = con.prepareStatement("insert into KhachHang values(?,?,?,?,?)");
        stmt.setString(1, khach.getMaKH());
        stmt.setString(2, khach.getTenKH());
        // Chuyển đổi ngày tháng từ java.util.Date thành java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(khach.getNgaySinh().getTime());

        stmt.setDate(3, sqlDate); // Sử dụng java.sql.Date đã chuyển đổi
        stmt.setString(4, khach.getDiaChi());
        stmt.setString(5, khach.getSdt());
        
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
	}finally {
		Close(stmt);
	}
}



public void updateKH(KhachHang khach) {
	Connection con=ConnectDB.getInstance().getConnection();
		PreparedStatement stmt=null;
	String sql = "update KhachHang "
            + "SET tenKH = ?, "
            + "ngaySinh = ?, "
            + "diaChi = ?, "
            + "sdt = ? "
            + "WHERE maKH = ?";
	try {
		stmt =con.prepareStatement(sql);
		
        stmt.setString(1, khach.getTenKH());
        // Chuyển đổi ngày tháng từ java.util.Date thành java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(khach.getNgaySinh().getTime());

        stmt.setDate(2, sqlDate); // Sử dụng java.sql.Date đã chuyển đổi
        stmt.setString(3, khach.getDiaChi());
        stmt.setString(4, khach.getSdt());
        
        stmt.setString(5, khach.getMaKH());
        stmt.executeUpdate();
		
	} catch (SQLException ex) {
		ex.printStackTrace();
	}finally {
		Close(stmt);
	}
}

private void Close(PreparedStatement stmt) {
	if(stmt !=null)
		try {
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	
}


			
			// tìm KH theo tên
			public List<KhachHang> getKHTheoTen(String ten) {
				List<KhachHang> dskh = new ArrayList<KhachHang>();
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "select * from KhachHang AS tenKH where tenKH like N'%" + ten + "%'";
				try {
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
				
						String makh = rs.getString(1);
						String tenkh = rs.getString(2);
						java.sql.Date ngaySinh=rs.getDate(3);
						String diaChi = rs.getString(4);
						String Sdt = rs.getString(5);
						KhachHang kh=new KhachHang(makh, tenkh, ngaySinh, diaChi, Sdt);
						
						dskh.add(kh);
						
						//return ncc;
						
						

					}

				} catch (SQLException e) {
					e.printStackTrace();
				
			}
				return dskh;
			}
			
			// Tìm khách hàng theo mã 
			public KhachHang getKHTheoMa(String ma) {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "select * from KhachHang where maKH = '" + ma + "'";
				try {
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
						String makh = rs.getString(1);
						String tenkh = rs.getString(2);
						java.sql.Date ngaySinh=rs.getDate(3);
						String diaChi = rs.getString(4);
						String Sdt = rs.getString(5);
						KhachHang kh=new KhachHang(makh, tenkh, ngaySinh, diaChi, Sdt);
						
						dskh.add(kh);
						return kh;
						
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return null;

			}
	
			
			public List<Mycombo_MaKH> getmaKH(){
				
				Connection con = ConnectDB.getInstance().getConnection();
				List<Mycombo_MaKH> dsMaKH = new ArrayList<Mycombo_MaKH>();
				String sql = "Select * from KhachHang";
				Statement stament;
				try {
					stament = con.createStatement();
					ResultSet rs = stament.executeQuery(sql);
					while (rs.next()) {
						dsMaKH.add(new Mycombo_MaKH(rs.getString("maKH")));
					}
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();

				}
				return dsMaKH;
			}
			
			
			
			
			public boolean isMaKHTonTai(String maKH) {
			    Connection con = null;
			    PreparedStatement stmt = null;
			    ResultSet rs = null;

			    try {
			        con = ConnectDB.getInstance().getConnection();
			        stmt = con.prepareStatement("SELECT COUNT(*) FROM KhachHang WHERE maKH = ?");
			        stmt.setString(1, maKH);

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
			
			public boolean isSDTTonTai(String SDT) {
			    Connection con = null;
			    PreparedStatement stmt = null;
			    ResultSet rs = null;

			    try {
			        con = ConnectDB.getInstance().getConnection();
			        stmt = con.prepareStatement("SELECT COUNT(*) FROM KhachHang WHERE sdt = ?");
			        stmt.setString(1, SDT);

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
	

}
