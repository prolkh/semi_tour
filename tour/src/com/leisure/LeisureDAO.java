package com.leisure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBConn;


public class LeisureDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertLeisure(LeisureDTO dto) {
		PreparedStatement pstmt=null;
		String sql;
		int result=0;
		
		try {
			sql="insert into leisure set(num, userId, subject, opening, useTime, address,";
			sql="longitude, latitude, tel, content, introduction, imageFilename)";
			sql="values(lei_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getOpening());
			pstmt.setString(4, dto.getUseTime());
			pstmt.setString(5, dto.getAddress());
			pstmt.setInt(6, dto.getLongitude());
			pstmt.setInt(7, dto.getLatitude());
			pstmt.setString(8, dto.getTel());
			pstmt.setString(9, dto.getContent());
			pstmt.setString(10, dto.getIntroduction());
			pstmt.setString(11, dto.getImageFilename());
			
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println(e.toString());
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
			}catch(Exception e2) {
			}
		}		
		return result;		
	}
	
	public int deleteLeisure(int num) {
		PreparedStatement pstmt=null;
		String sql;
		int result=0;
		
		try {
			sql="delete from leisure where num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {

					pstmt.close();

				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public LeisureDTO readLeisure(int num) {
		LeisureDTO dto=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql="select userId, subject, opening, useTime, address, ";
			sql="longitude, latitude, tel, content, introduction, hitCount, created, imageFilename";
			sql=" from leisure where num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto= new LeisureDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setOpening(rs.getString("opening"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setAddress(rs.getString("address"));
				dto.setLongitude(rs.getInt("longtitude"));
				dto.setLatitude(rs.getInt("latitude"));
				dto.setTel(rs.getString("tel"));
				dto.setContent(rs.getString("content"));
				dto.setIntroduction(rs.getString("introduction"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getDate("created").toString());
				dto.setImageFilename(rs.getString("imageFilename"));
							
			}			
		}catch(Exception e){
			System.out.println(e.toString());
		}finally {
			if (rs != null){
				try {
					pstmt.close();
				}catch(Exception e2){
				}
			}
			if (pstmt != null){
				try {
					pstmt.close();
				}catch(Exception e2){
				}
			}
		}		
		return dto;
	}
	
	public int updateLeisure(LeisureDTO dto) {
		PreparedStatement pstmt= null;
		String sql;
		int result=0;
		
		try {
			sql="update set subject=?, opening=?, useTime=?, address=?,";
			sql="longitude=?, latitude=?, tel=?, content=?, introduction=?, imageFilename=?";
			sql="from leisure where num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getOpening());
			pstmt.setString(3, dto.getUseTime());
			pstmt.setString(4, dto.getAddress());
			pstmt.setInt(5, dto.getLongitude());
			pstmt.setInt(6, dto.getLatitude());
			pstmt.setString(7, dto.getTel());
			pstmt.setString(8, dto.getContent());
			pstmt.setString(9, dto.getIntroduction());
			pstmt.setString(10, dto.getImageFilename());
			pstmt.setInt(11, dto.getNum());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e.toString());
		}finally {
			if (pstmt != null){
				try {
					pstmt.close();
				}catch(Exception e2){
				}
			}
		}
		return result;
	}
	
	public List<LeisureDTO> listLeisure(int start, int end){
		List<LeisureDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("select * from (");
			sb.append("	select ROWNUM rnum, tb.*from(");
			sb.append("	select subject, opening, useTime, tel, address, longitude,");
			sb.append("	latitude, created, introduction, content, hitCount");
			sb.append("	from leisure order by num DESC) tb where ROWNUM <=?");
			sb.append("	)where rnum >= ?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				LeisureDTO dto = new LeisureDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setOpening(rs.getString("opening"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setAddress(rs.getString("address"));
				dto.setLongitude(rs.getInt("longtitude"));
				dto.setLatitude(rs.getInt("latitude"));
				dto.setTel(rs.getString("tel"));
				dto.setContent(rs.getString("content"));
				dto.setIntroduction(rs.getString("introduction"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getDate("created").toString());
				dto.setImageFilename(rs.getString("imageFilename"));
				
				list.add(dto);
			}			
		}catch(Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				}catch(Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(Exception e2) {
				}
			}
		}
		return list;
	}
	
	public int datacount() {
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs = null;
		int result=0;
		
		try {
			sql="select NVL(count(*),0) from leisure";
			
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				}catch(Exception e2) {					
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(Exception e2) {					
				}
			}
		}		
		return result;
	}
}
