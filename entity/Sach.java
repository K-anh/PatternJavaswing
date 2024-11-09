package entity;

public class Sach {
	private String maSach,tenSach ;
	private NhaCungCap nhacungcap;
	private double donGiaNhap;
	private int soLuong;
	private TheLoai theloai;
	private NhaXuatBan nhaxuatban;
	private TacGia tacgia;
	private String hinhanh;
	public Sach() {
		super();
	}
	public Sach(String maSach, String tenSach, NhaCungCap nhacungcap, double donGiaNhap, int soLuong, TheLoai theloai,
			NhaXuatBan nhaxuatban, TacGia tacgia, String hinhanh) {
		super();
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.nhacungcap = nhacungcap;
		this.donGiaNhap = donGiaNhap;
		this.soLuong = soLuong;
		this.theloai = theloai;
		this.nhaxuatban = nhaxuatban;
		this.tacgia = tacgia;
		this.hinhanh = hinhanh;
		
	}
	public String getMaSach() {
		return maSach;
	}
	public void setMaSach(String maSach) {
		this.maSach = maSach;
	}
	public String getTenSach() {
		return tenSach;
	}
	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}
	public NhaCungCap getNhacungcap() {
		return nhacungcap;
	}
	public void setNhacungcap(NhaCungCap nhacungcap) {
		this.nhacungcap = nhacungcap;
	}
	public double getDonGiaNhap() {
		return donGiaNhap;
	}
	public void setDonGiaNhap(double donGiaNhap) {
		this.donGiaNhap = donGiaNhap;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public TheLoai getTheloai() {
		return theloai;
	}
	public void setTheloai(TheLoai theloai) {
		this.theloai = theloai;
	}
	public NhaXuatBan getNhaxuatban() {
		return nhaxuatban;
	}
	public void setNhaxuatban(NhaXuatBan nhaxuatban) {
		this.nhaxuatban = nhaxuatban;
	}
	public TacGia getTacgia() {
		return tacgia;
	}
	public void setTacgia(TacGia tacgia) {
		this.tacgia = tacgia;
	}
	public String getHinhanh() {
		return hinhanh;
	}
	public void setHinhanh(String hinhanh) {
		this.hinhanh = hinhanh;
	}
	@Override
	public String toString() {
		return "Sach [maSach=" + maSach + ", tenSach=" + tenSach + ", nhacungcap=" + nhacungcap + ", donGiaNhap="
				+ donGiaNhap + ", soLuong=" + soLuong + ", theloai=" + theloai + ", nhaxuatban=" + nhaxuatban
				+ ", tacgia=" + tacgia + ", hinhanh=" + hinhanh + "]";
	}
	
	
	
}
