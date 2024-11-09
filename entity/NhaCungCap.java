package entity;

import java.util.Objects;

public class NhaCungCap {
	private String maNCC;
	private String tenNCC;
	private String sdt;
	private String diaChi;
	private boolean tinhTrang;
	//private String tinhTrang;
	
	
	
	public NhaCungCap(String tenNCC) {
		super();
		this.tenNCC=tenNCC;
	}
	public NhaCungCap() {
		super();
	}
	public NhaCungCap(String maNCC, String tenNCC, String sdt, String diaChi, boolean tinhTrang) {
		super();
		this.maNCC = maNCC;
		this.tenNCC = tenNCC;
		this.sdt = sdt;
		this.diaChi = diaChi;
		this.tinhTrang = tinhTrang;
	}
	public String getMaNCC() {
		return maNCC;
	}
	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}
	public String getTenNCC() {
		return tenNCC;
	}
	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public boolean isTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	@Override
	public String toString() {
		return "NhaCungCap [maNCC=" + maNCC + ", tenNCC=" + tenNCC + ", sdt=" + sdt + ", diaChi=" + diaChi
				+ ", tinhTrang=" + tinhTrang + "]";
	}
	
	
	
	
	
}