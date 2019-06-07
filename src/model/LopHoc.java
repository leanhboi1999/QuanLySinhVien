package model;

public class LopHoc {
	private String MaLop;
	private String TenLop;

	public String getMaLop() {
		return MaLop;
	}

	public void setMaLop(String maLop) {
		MaLop = maLop;
	}

	public String getTenLop() {
		return TenLop;
	}

	public void setTenLop(String tenLop) {
		TenLop = tenLop;
	}

	@Override
	public String toString() {
		return this.TenLop;
	}

	

}
