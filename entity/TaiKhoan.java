package entity;

public class TaiKhoan {
	private String maNV;
	private String hoTenNhanVien;
	private String mk;
	private String chucVu;
	private String email;
	private String OTP;
	private boolean trangThai;
	
	public TaiKhoan() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public TaiKhoan(String maNV, String hoTenNhanVien, String mk, String chucVu, String email, String oTP,
			boolean trangThai) {
		super();
		this.maNV = maNV;
		this.hoTenNhanVien = hoTenNhanVien;
		this.mk = mk;
		this.chucVu = chucVu;
		this.email = email;
		OTP = oTP;
		this.trangThai = trangThai;
	}



	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTenNhanVien() {
		return hoTenNhanVien;
	}

	public void setHoTenNhanVien(String hoTenNhanVien) {
		this.hoTenNhanVien = hoTenNhanVien;
	}

	public String getMk() {
		return mk;
	}

	public void setMk(String mk) {
		this.mk = mk;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "TaiKhoan [maNV=" + maNV + ", hoTenNhanVien=" + hoTenNhanVien + ", mk=" + mk + ", chucVu=" + chucVu
				+ ", email=" + email + ", OTP=" + OTP + ", trangThai=" + trangThai + "]";
	}

	

	
}
