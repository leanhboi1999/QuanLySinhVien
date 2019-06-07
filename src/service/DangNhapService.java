package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.DangNhap;

public class DangNhapService extends OracleService {
	public DangNhap login(String user, String pass) {
		DangNhap account=null;
		try {
			String sql="select * from dangnhap where username=? and password =?";
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			ResultSet result=preparedStatement.executeQuery();
			if(result.next()) {
				account=new DangNhap();
				account.setUsername(result.getString(1));
				account.setPassword(result.getString(2));
			}
			
		} catch (Exception e) {
			
		}
		
		return account;
	}
}
