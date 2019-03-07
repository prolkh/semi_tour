package com.fest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.Util.DBConn;


public class FestDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertFest(FestDTO dto) {
		int result=0;
		
		PreparedStatement pstmt=null;
		String sql;
		
		try {			
			sql="INSERT INTO photo (num, userId, eventName, content, address, startDate, endDate, tel, homepage, host, price, createdDate, imageFilename) "
					+ " VALUES (event_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getEventName());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getAddress());
			pstmt.setString(5, dto.getStartDate());
			pstmt.setString(6, dto.getEndDate());
			pstmt.setString(7, dto.getTel());
			pstmt.setString(8, dto.getHomepage());
			pstmt.setString(9, dto.getHost());
			pstmt.setString(10, dto.getPrice());
			pstmt.setString(11, dto.getCreatedDate());
			pstmt.setString(12, dto.getImageFilename());
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
}
