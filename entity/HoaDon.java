package entity;

import java.sql.Date;

public class HoaDon {
	private String maHD;
	private String maNV;
	private String maKH;
	private float VAT;
	private String maQuay;
	private float uuDai;
	private double tongTien;
	private Date ngayLap;
	
	public HoaDon() {
		
	}
	public HoaDon(String maHD, String maNV, String maKH, Date ngayLap  ,float vAT, String maQuay, float uuDai, double tongTien) {
		super();
		this.maHD = maHD;
		this.maNV = maNV;
		this.maKH = maKH;
		this.ngayLap = ngayLap;
		this.VAT = vAT;
		this.maQuay = maQuay;
		this.uuDai = uuDai;
		this.tongTien = tongTien;
	}
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public float getVAT() {
		return VAT;
	}
	public void setVAT(float vAT) {
		VAT = vAT;
	}
	public String getMaQuay() {
		return maQuay;
	}
	public void setMaQuay(String maQuay) {
		this.maQuay = maQuay;
	}
	public float getUuDai() {
		return uuDai;
	}
	public void setUuDai(float uuDai) {
		this.uuDai = uuDai;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	
	
	public Date getNgayLap() {
		return ngayLap;
	}
	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}
	
	
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", maNV=" + maNV + ", maKH=" + maKH + ", VAT=" + VAT + ", maQuay=" + maQuay
				+ ", uuDai=" + uuDai + ", tongTien=" + tongTien + "]";
	}
	
	
	
	
	
}
