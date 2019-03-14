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
			sql = "SELECT userId, userName, userPwd, tel, created_date, modify_date, userRoll "
					+ "FROM member WHERE userId = ?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			 
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setTel(rs.getString("tel"));
				dto.setCreated_date(rs.getString("created_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setUserRoll(rs.getInt("userRoll"));				
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
	
	public int updateMember(MemberDTO dto) {
		int result =0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("UPDATE member SET userPwd=?, modify_date=SYSDATE, tel=?, email=? WHERE userId=?");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getTel());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getUserId());
			
			result=pstmt.executeUpdate();
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
