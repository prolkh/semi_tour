package com.fest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBConn;


public class FestDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertFest(FestDTO dto) {
		int result=0;
		
		PreparedStatement pstmt=null;
		String sql;
		
		try {			
			sql="INSERT INTO event(num, userId, eventName, content, address, startDate, endDate, tel, homepage, host, price, imageFilename) "
					+ " VALUES (event_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			pstmt.setString(11, dto.getImageFilename());
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
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
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM event";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public List<FestDTO> listFest(int start, int end){
		List<FestDTO> list = new ArrayList<FestDTO>();
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("	    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("	        SELECT num, userId, eventName, address, startDate, endDate, tel, homepage, host, price, created, imageFilename, content");
			sb.append("	        FROM event");
			sb.append("	        ORDER BY num DESC");
			sb.append("	    )tb WHERE ROWNUM  <= ?");
			sb.append(") WHERE rnum>=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				FestDTO dto=new FestDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setEventName(rs.getString("eventName"));
				dto.setContent(rs.getString("content"));
				dto.setAddress(rs.getString("address"));
				dto.setStartDate(rs.getDate("startDate").toString());
				dto.setEndDate(rs.getDate("endDate").toString());
				dto.setTel(rs.getString("tel"));
				dto.setHomepage(rs.getString("homepage"));
				dto.setHost(rs.getString("host"));
				dto.setPrice(rs.getString("price"));
				dto.setCreatedDate(rs.getDate("created").toString());
				dto.setImageFilename(rs.getString("imageFilename"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}
	
}
