package entity;

import java.sql.Date;

public class NhanVien {
	private String maNV;
	private String hoTenNV;
	private Date ngaySinh;
	private String diaChi;
	private String sdt;
	private String chucVu;
	private boolean gioiTinh;
	private String cccd;
	private boolean tinhTrang;
	private String email;
	private String lyDoNghiViec;
	
	
	public NhanVien() {
	
	}


	public NhanVien(String maNV, String hoTenNV, Date ngaySinh, String diaChi, String sdt, String chucVu,
			boolean gioiTinh, String cccd, boolean tinhTrang, String email, String lyDoNghiViec) {
		super();
		this.maNV = maNV;
		this.hoTenNV = hoTenNV;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.chucVu = chucVu;
		this.gioiTinh = gioiTinh;
		this.cccd = cccd;
		this.tinhTrang = tinhTrang;
		this.email = email;
		this.lyDoNghiViec = lyDoNghiViec;
	}


	public String getMaNV() {
		return maNV;
	}


	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}


	public String getHoTenNV() {
		return hoTenNV;
	}


	public void setHoTenNV(String hoTenNV) {
		this.hoTenNV = hoTenNV;
	}


	public Date getNgaySinh() {
		return ngaySinh;
	}


	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}


	public String getDiaChi() {
		return diaChi;
	}


	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}


	public String getSdt() {
		return sdt;
	}


	public void setSdt(String sdt) {
		this.sdt = sdt;
	}


	public String getChucVu() {
		return chucVu;
	}


	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}


	public boolean isGioiTinh() {
		return gioiTinh;
	}


	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}


	public String getCccd() {
		return cccd;
	}


	public void setCccd(String cccd) {
		this.cccd = cccd;
	}


	public boolean isTinhTrang() {
		return tinhTrang;
	}


	public void setTinhTrang(boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLyDoNghiViec() {
		return lyDoNghiViec;
	}


	public void setLyDoNghiViec(String lyDoNghiViec) {
		this.lyDoNghiViec = lyDoNghiViec;
	}


	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", hoTenNV=" + hoTenNV + ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi
				+ ", sdt=" + sdt + ", chucVu=" + chucVu + ", gioiTinh=" + gioiTinh + ", cccd=" + cccd + ", tinhTrang="
				+ tinhTrang + ", email=" + email + ", lyDoNghiViec=" + lyDoNghiViec + "]";
	}
	
	

	
	
	
	
}
