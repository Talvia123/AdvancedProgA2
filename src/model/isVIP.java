package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbConnection.DatabaseUtil;

public class isVIP {
	public boolean checkVIP(String username) {
		
		
		try {
			Connection connection = DatabaseUtil.getConnection();
	        String query = "SELECT * FROM user WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, username);

	        ResultSet resultSet = preparedStatement.executeQuery();
	        resultSet.next();
	        if(resultSet.getInt("isVIP")==1) {
	        	return true;
	        	
	        }else {
	        	return false;
	        }
	        
		}catch(Exception e) {
			return false;
		}
	}
}
