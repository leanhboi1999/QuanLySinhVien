package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import model.LopHoc;

public class LopHocService extends OracleService {
	public ArrayList<LopHoc>layToanBoLop () {
		ArrayList<LopHoc> ds= new ArrayList<LopHoc>();
		
		try {
			String sql="select * from lophoc";
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			ResultSet result=preparedStatement.executeQuery();
			while(result.next()) {
				LopHoc lop=new LopHoc();
				lop.setMaLop(result.getString(1));
				lop.setTenLop(result.getString(2));
				ds.add(lop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ds;
	}
	
	public int LuuLop(LopHoc lop) {
		try {
			String sql="insert into lophoc values(?,?)";
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			preparedStatement.setString(1, lop.getMaLop());
			preparedStatement.setString(2, lop.getTenLop());
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
