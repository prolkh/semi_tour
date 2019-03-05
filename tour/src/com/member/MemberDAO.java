package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertMember(MemberDTO dto) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO member(userId, userPwd, userName, tel, email) VALUES (?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserName());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getEmail());
			result=pstmt.executeUpdate();
		
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
	
	public MemberDTO readMember(String userId) {
		MemberDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT userId, userName, userPwd, tel, created_date, modify_date, userRoll"
					+ "FROM member WHERE userId = ?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userId"));
				dto.setTel(rs.getString("userId"));
				dto.setCreated_date(rs.getString("userId"));
				dto.setModify_date(rs.getString("userId"));
				dto.setUserRoll(rs.getInt("userId"));				
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
	public int updateMember() {
		int result =0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "";
			pstmt=conn.prepareStatement(sql);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int deleteMember() {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "";
			pstmt=conn.prepareStatement(sql);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
}
