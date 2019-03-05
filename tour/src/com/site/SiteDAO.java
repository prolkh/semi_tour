package com.site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.DBConn;

public class SiteDAO {
	private Connection conn = DBConn.getConnection();
	
	// 글 삭제
	public int deleteSite(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM site WHERE num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	
	public int dataCount() {
		// 데이터 갯수
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM site";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
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
	
	public SiteDTO readSite(int num) {
		SiteDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT num, s.userId, userName,");
			sb.append("       subject, useTime, zip, address,");
			sb.append("       longitude, latitude, ");
			sb.append("       content, introduction, inquiry, ");
			sb.append("       imageFilename, hitCount, created");
			sb.append("       FROM site s");
			sb.append("       JOIN member m ON s.userId=m.userId");
			sb.append("       WHERE num=?;");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1,  num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new SiteDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setZip(rs.getInt("zip"));
				dto.setAddress(rs.getString("address"));
				dto.setLongitude(rs.getFloat("longitude"));
				dto.setLatitude(rs.getFloat("latitude"));
				dto.setContent(rs.getString("content"));
				dto.setIntroduction(rs.getString("introduction"));
				dto.setInquiry(rs.getString("inquiry"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getDate("created").toString());
			}
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
		
		return dto;
	}

}
