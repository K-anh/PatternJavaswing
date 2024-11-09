package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import entity.Mycombo_MaKH;
import entity.Mycombo_MaNV;
import entity.NhaCungCap;
import entity.NhanVien;

public class NhanVien_Dao {
	ArrayList<entity.NhanVien> dsnv;
	public NhanVien_Dao() {
		dsnv = new ArrayList<NhanVien>();

	}	
	
	public List<NhanVien> getAllNhanVien(){
		List<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			String sql = "Select * from NhanVien";
			Statement stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while(rs.next()) {
				dsNhanVien.add( new NhanVien(
						rs.getString("maNV"),
						rs.getString("hoTenNV"),
						rs.getDate("ngaySinh"),
						rs.getString("diaChi"),
						rs.getString("sdt"),
						rs.getString("chucVu"),
						rs.getBoolean("gioiTinh"),
						rs.getString("CCCD"),
						rs.getBoolean("tinhTrang"),
						rs.getString("email"),
						rs.getString("lyDoNghiViec")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsNhanVien;
	}
	
	public void addNhanVien(NhanVien nv) {
		Connection con  = ConnectDB.getInstance().getConnection();
		
		PreparedStatement stmt = null;
		try {
			
			stmt = con.prepareStatement("insert into NhanVien (maNV , hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh , CCCD , tinhTrang , email) values(?,?,?,?,?,?,?,?,? ,?)");
//			maNV , hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh , CCCD , tinhTrang
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getHoTenNV());
			stmt.setDate(3, nv.getNgaySinh());
			stmt.setString(4, nv.getDiaChi());
			stmt.setString(5, nv.getSdt());
			stmt.setString(6, nv.getChucVu());
			stmt.setBoolean(7, nv.isGioiTinh());
			stmt.setString(8, nv.getCccd());
			stmt.setBoolean(9, nv.isTinhTrang());
			stmt.setString(10, nv.getEmail());
			stmt.executeUpdate();
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void updateNhanVien(String maNhanVien , String diaChiNew , String sdtNew , String chucVuNew ,String emailNew) {
		Connection con  = ConnectDB.getInstance().getConnection();
		
		PreparedStatement stmt = null;
		 String sql = "UPDATE NhanVien SET diaChi= ?, sdt = ? , chucVu=?  , email = ?   WHERE maNV = ?";
		
		try {
			
			stmt = con.prepareStatement(sql);
//			maNV , hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh , CCCD , tinhTrang
			
			

			
			
			stmt.setString(1, diaChiNew);
			stmt.setString(2, sdtNew);
			stmt.setString(3, chucVuNew);
			stmt.setString(4, emailNew);
			stmt.setString(5, maNhanVien);
			
			stmt.executeUpdate();
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}finally {
			close(stmt);
		}
		
		
	}
	
	
	
	
	
	public List<NhanVien> getNhanVienDangHoatDong(){
		List<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		List<NhanVien> dsNhanVienDangHoatDong = new ArrayList<NhanVien>();
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			String sql = "Select * from NhanVien";
			Statement stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while(rs.next()) {
				dsNhanVien.add( new NhanVien(
						rs.getString("maNV"),
						rs.getString("hoTenNV"),
						rs.getDate("ngaySinh"),
						rs.getString("diaChi"),
						rs.getString("sdt"),
						rs.getString("chucVu"),
						rs.getBoolean("gioiTinh"),
						rs.getString("CCCD"),
						rs.getBoolean("tinhTrang"),
						rs.getString("email"),
						rs.getString("lyDoNghiViec")));
			}
			
			for (NhanVien nhanVien : dsNhanVien) {
				if(nhanVien.isTinhTrang() == true && nhanVien.getLyDoNghiViec().trim().equals("") ) {
					dsNhanVienDangHoatDong.add(nhanVien);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsNhanVienDangHoatDong;
	}
	
	
	public List<NhanVien> getNhanVienKhongHoatDong(){
		List<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		List<NhanVien> dsNhanVienKhongHoatDong = new ArrayList<NhanVien>();
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			String sql = "Select * from NhanVien";
			Statement stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while(rs.next()) {
				dsNhanVien.add( new NhanVien(
						rs.getString("maNV"),
						rs.getString("hoTenNV"),
						rs.getDate("ngaySinh"),
						rs.getString("diaChi"),
						rs.getString("sdt"),
						rs.getString("chucVu"),
						rs.getBoolean("gioiTinh"),
						rs.getString("CCCD"),
						rs.getBoolean("tinhTrang"),
						rs.getString("email"),
						rs.getString("lyDoNghiViec")));
			}
			
			for (NhanVien nhanVien : dsNhanVien) {
				if(nhanVien.isTinhTrang() == false && !nhanVien.getLyDoNghiViec().trim().equals("") ) {
					dsNhanVienKhongHoatDong.add(nhanVien);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsNhanVienKhongHoatDong;
	}
	
	
	
//	FC này sẽ không xóa nhân viên nó chỉ update lại tình trạng và lý do nghỉ việc
	public void xoaNhanVien(String maNV , String lyDoNghiViec) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		 String sql = "UPDATE NhanVien SET tinhTrang = ?, lyDoNghiViec = ? WHERE maNV = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, false);
			stmt.setString(2, lyDoNghiViec);
			stmt.setString(3, maNV);
			stmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}				
	}
	
	
//	Giup Khoi Phuc Nhan Vien Bi Xoa
	public void khoiPhucNhanVienBiXoa(String maNV) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		 String sql = "UPDATE NhanVien SET tinhTrang = ?, lyDoNghiViec = ? WHERE maNV = ?";
		 try {
				stmt = con.prepareStatement(sql);
				stmt.setBoolean(1, true);
				stmt.setString(2, "");
				stmt.setString(3, maNV);
				stmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				close(stmt);
			}				
		 
	}
	
	public List<Mycombo_MaNV> getmaNV(){
		
		Connection con = ConnectDB.getInstance().getConnection();
		List<Mycombo_MaNV> dsMaNV = new ArrayList<Mycombo_MaNV>();
		String sql = "Select * from NhanVien";
		Statement stament;
		try {
			stament = con.createStatement();
			ResultSet rs = stament.executeQuery(sql);
			while (rs.next()) {
				dsMaNV.add(new Mycombo_MaNV(rs.getString("maNV")));
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();

		}
		return dsMaNV;
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
	
	
	
	
	//Tìm kiếm Nhân VIên
	// tìm NV theo SDT
	
				public NhanVien getNVTheoSDT(String SDT) throws SQLException {
					ConnectDB.getInstance();
					Connection con = ConnectDB.getConnection();
					String sql = "select * from NhanVien where sdt = '" + SDT + "'";
					try {
						Statement statement = con.createStatement();
						ResultSet rs = statement.executeQuery(sql);
						while (rs.next()) {
							String manv=rs.getString(1);
							String tennv=rs.getString(2);
							java.sql.Date ngaySinh=rs.getDate(3);
							String diaChi=		rs.getString(4);
							String sdt=		rs.getString(5);
							String chucvu=	rs.getString(6);
							Boolean gioiTinh=rs.getBoolean(7);
							String cccd=rs.getString(8);
							Boolean tinhTrang=rs.getBoolean(9);
							String email = rs.getString(10);
							String lydo=rs.getString(11);
							
							NhanVien nv=new NhanVien(manv, tennv, ngaySinh, diaChi, sdt, chucvu, gioiTinh, cccd, tinhTrang, email , lydo);
							dsnv.add(nv);
							return nv;
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return null;
				}
				
				// tìm NV theo tên
				public List<NhanVien> getNVTheoTen(String ten) {
					List<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
					ConnectDB.getInstance();
					Connection con = ConnectDB.getConnection();
					String sql = "select * from NhanVien AS hoTenNV where hoTenNV like N'%" + ten + "%'";
					try {
						Statement statement = con.createStatement();
						ResultSet rs = statement.executeQuery(sql);
						while (rs.next()) {
					
							String manv=rs.getString(1);
							String tennv=rs.getString(2);
							java.sql.Date ngaySinh=rs.getDate(3);
							String diaChi=		rs.getString(4);
							String sdt=		rs.getString(5);
							String chucvu=	rs.getString(6);
							Boolean gioiTinh=rs.getBoolean(7);
							String cccd=rs.getString(8);
							Boolean tinhTrang=rs.getBoolean(9);
							String email = rs.getString(10);
							String lydo=rs.getString(11);
							
							NhanVien nv = new NhanVien(manv, tennv, ngaySinh, diaChi, sdt, chucvu, gioiTinh, cccd, tinhTrang, email , lydo);
							dsnv.add(nv);
							
							
							

						}

					} catch (SQLException e) {
						e.printStackTrace();
					
				}
					return dsnv;
				}
				
				// Tìm NV theo mã 
				public NhanVien getNVTheoMa(String ma) {
					ConnectDB.getInstance();
					Connection con = ConnectDB.getConnection();
					String sql = "select * from NhanVien where maNV = '" + ma + "'";
					try {
						Statement statement = con.createStatement();
						ResultSet rs = statement.executeQuery(sql);
						while (rs.next()) {
							
							String manv=rs.getString(1);
							String tennv=rs.getString(2);
							java.sql.Date ngaySinh=rs.getDate(3);
							String diaChi=		rs.getString(4);
							String sdt=		rs.getString(5);
							String chucvu=	rs.getString(6);
							Boolean gioiTinh=rs.getBoolean(7);
							String cccd=rs.getString(8);
							Boolean tinhTrang=rs.getBoolean(9);
							String email = rs.getString(10);
							String lydo=rs.getString(11);
							
							NhanVien nv=new NhanVien(manv, tennv, ngaySinh, diaChi, sdt, chucvu, gioiTinh, cccd, tinhTrang, email, lydo);
							dsnv.add(nv);
							return nv;
							
							
									
							
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

					return null;

				}
				
				
		public void thangChuc(String maNhanVien) {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = null;
			 String sql = "UPDATE NhanVien SET  chucVu = ? WHERE maNV = ?";
			 try {
					stmt = con.prepareStatement(sql);
					stmt.setString(1, "Quản Lý");
					stmt.setString(2, maNhanVien);
					stmt.executeUpdate();
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					close(stmt);
				}				
		}
		
		
		
		public boolean isSDTTonTai(String SDT) {
		    Connection con = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        con = ConnectDB.getInstance().getConnection();
		        stmt = con.prepareStatement("SELECT COUNT(*) FROM NhanVien WHERE sdt = ?");
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
		
		public boolean isCCTonTai(String cccd) {
		    Connection con = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        con = ConnectDB.getInstance().getConnection();
		        stmt = con.prepareStatement("SELECT COUNT(*) FROM NhanVien WHERE cccd = ?");
		        stmt.setString(1, cccd);

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
		public boolean isEmailTonTai(String email) {
		    Connection con = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        con = ConnectDB.getInstance().getConnection();
		        stmt = con.prepareStatement("SELECT COUNT(*) FROM NhanVien WHERE email = ?");
		        stmt.setString(1, email);

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


