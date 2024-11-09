package entity;

public class TheLoai {
	private String maTheLoai;
	private String tenTheLoai;
	public TheLoai() {
		super();
	}
	
	public TheLoai(String tenTheLoai) {
		super();
		this.tenTheLoai = tenTheLoai;
	}

	public TheLoai(String maTheLoai, String tenTheLoai) {
		super();
		this.maTheLoai = maTheLoai;
		this.tenTheLoai = tenTheLoai;
	}
	public String getMaTheLoai() {
		return maTheLoai;
	}
	@Override
	public String toString() {
		return "TheLoai [maTheLoai=" + maTheLoai + ", tenTheLoai=" + tenTheLoai + "]";
	}
	public void setMaTheLoai(String maTheLoai) {
		this.maTheLoai = maTheLoai;
	}
	public String getTenTheLoai() {
		return tenTheLoai;
	}
	public void setTenTheLoai(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}
	
	

}
