package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.SinhVien;

public class SinhVienService extends OracleService {
	public int xoaSinhVien(SinhVien sv) {
		try {
			String sql="delete from sinhvien where ma=?";
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			preparedStatement.setString(1, sv.getMa());
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public ArrayList<SinhVien> layToanBoSinhVien (String malop){
		ArrayList<SinhVien> ds = new ArrayList<SinhVien>();
		try {
			String sql="select * from sinhvien where malop=?";
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			preparedStatement.setString(1, malop);
			ResultSet  result=preparedStatement.executeQuery();
			while (result.next()) {
				SinhVien sv=new SinhVien();
				sv.setMa(result.getString(1));
				sv.setTen(result.getString(2));
				sv.setTuoi(result.getString(3));
				sv.setMalop(malop);
				ds.add(sv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}
}
