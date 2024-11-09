package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ConnectDB.ConnectDB;

import entity.NhaCungCap;



public class NCC_DAO {
	ArrayList<entity.NhaCungCap> dsncc;
	public NCC_DAO() {
		dsncc = new ArrayList<NhaCungCap>();

	}	
	
public List<NhaCungCap> getAllNCC(){
		
		List<NhaCungCap> dsNCC=new ArrayList<NhaCungCap>();
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		try {
			String sql="select * from NhaCungCap";;
			Statement statement =con.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()) {

				
				dsNCC.add(new NhaCungCap(rs.getString("maNCC"),
						rs.getString("tenNCC"),
						rs.getString("sdt"),
						rs.getString("diaChi"),
						rs.getBoolean("tinhTrang")
						
				));
									
						
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsNCC;
	}
	
	public void addNCC(NhaCungCap nhacc) {
		Connection con=ConnectDB.getInstance().getConnection();
		PreparedStatement stmt=null;
		try {
			stmt =con.prepareStatement("insert into NhaCungCap values(?,?,?,?,?)");
			stmt.setString(1, nhacc.getMaNCC());
			stmt.setString(2, nhacc.getTenNCC());
			stmt.setString(3, nhacc.getSdt());
			stmt.setString(4, nhacc.getDiaChi());
			
			stmt.setBoolean(5, nhacc.isTinhTrang());
			
			stmt.executeUpdate();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			
		}
	}
	
	public void updateNCC(NhaCungCap nhacc) {
		Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    String sql = "UPDATE NhaCungCap "
	            + "SET tenNCC = ?, "
	            + "sdt = ?, "
	            + "diaChi = ?, "
	            + "tinhTrang = ? "
	            + "WHERE maNCC = ?";  // Đảm bảo có một khoảng trắng giữa ? và WHERE
	    try {
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, nhacc.getTenNCC());
	        stmt.setString(2, nhacc.getSdt());
	        stmt.setString(3, nhacc.getDiaChi());
	        stmt.setBoolean(4, nhacc.isTinhTrang());
	        stmt.setString(5, nhacc.getMaNCC());

	        stmt.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } finally {
	        // Đóng kết nối và tài nguyên
	        //close(stmt);
	        //close(con);
	    }
	}
	
	

			
			// tìm Nhà cung cấp theo tên
			public List<NhaCungCap> getNCCTheoTen(String ten) {
				List<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "select * from NhaCungCap AS tenNCC where tenNCC like N'%" + ten + "%'";
				try {
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
				
						String maNCC = rs.getString(1);
						String tenNCC = rs.getString(2);
						String Sdt = rs.getString(3);
						String diaChi = rs.getString(4);
						Boolean tinhTrang = rs.getBoolean(5);
						NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, Sdt, diaChi, tinhTrang);
						dsncc.add(ncc);
						
						

					}

				} catch (SQLException e) {
					e.printStackTrace();
				
			}
				return dsncc;
			}
			
			// Tìm nhà cung cấp theo mã 
			public NhaCungCap getNCCTheoMa(String ma) {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "select * from NhaCungCap where maNCC = '" + ma + "'";
				try {
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
						String maNCC = rs.getString(1);
						String tenNCC = rs.getString(2);
						String Sdt = rs.getString(3);
						String diaChi = rs.getString(4);
						
						Boolean tinhTrang = rs.getBoolean(5);
						NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, Sdt, diaChi, tinhTrang);
						dsncc.add(ncc);
						return ncc;
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return null;

			}
	
			
			//load combobox danh sách tên ncc lên gui Sach
			public List<NhaCungCap> getncctheoTen(String ten) {
				List<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "select * from NhaCungCap AS tenNCC where tenNCC like N'%" + ten+ "%'";
				try {
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
				
						String maNCC = rs.getString(1);
						String tenNCC = rs.getString(2);
						String diaChi = rs.getString(3);
						String Sdt = rs.getString(4);
						Boolean tinhTrang = rs.getBoolean(5);
						NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, Sdt, diaChi, tinhTrang);
						dsncc.add(ncc);
						

					}

				} catch (SQLException e) {
					e.printStackTrace();
				
			}
				return dsncc;
			}	
			
			
			//Kiểm tra ràng buộc mã đã tồn tại
			public boolean isMaNCCTonTai(String maNCC) {
			    Connection con = null;
			    PreparedStatement stmt = null;
			    ResultSet rs = null;

			    try {
			        con = ConnectDB.getInstance().getConnection();
			        stmt = con.prepareStatement("SELECT COUNT(*) FROM NhaCungCap WHERE maNCC = ?");
			        stmt.setString(1, maNCC);

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
			//Kiểm tra ràng buộc SDT đã tồn tại
			public boolean isSDTTonTai(String SDT) {
			    Connection con = null;
			    PreparedStatement stmt = null;
			    ResultSet rs = null;

			    try {
			        con = ConnectDB.getInstance().getConnection();
			        stmt = con.prepareStatement("SELECT COUNT(*) FROM NhaCungCap WHERE sdt = ?");
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
