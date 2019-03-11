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
		StringBuffer sb = new StringBuffer();
		int result=0;
		
		try {
			sb.append("insert into leisure(NUM, USERID, SUBJECT, OPENING, USETIME, ADDRESS,");
			sb.append(" LATITUDE, LONGITUDE, TEL, CONTENT, INTRODUCTION, IMAGEFILENAME)");
			sb.append(" values(LEISURE_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getOpening());
			pstmt.setString(4, dto.getUseTime());
			pstmt.setString(5, dto.getAddress());
			pstmt.setFloat(6, dto.getLatitude());
			pstmt.setFloat(7, dto.getLongitude());
			pstmt.setString(8, dto.getTel());
			pstmt.setString(9, dto.getContent());
			pstmt.setString(10, dto.getIntroduction());
			pstmt.setString(11, dto.getImageFilename());
			
			result=pstmt.executeUpdate();
			
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
			sql="select num, userId, subject, opening, useTime, address, ";
			sql+="longitude, latitude, tel, content, introduction, hitCount, created, imageFilename";
			sql+=" from leisure where num=?";
			
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
				dto.setLongitude(rs.getFloat("longitude"));
				dto.setLatitude(rs.getFloat("latitude"));
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
			sql+="longitude=?, latitude=?, tel=?, content=?, introduction=?, imageFilename=?";
			sql+="from leisure where num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getOpening());
			pstmt.setString(3, dto.getUseTime());
			pstmt.setString(4, dto.getAddress());
			pstmt.setFloat(5, dto.getLongitude());
			pstmt.setFloat(6, dto.getLatitude());
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
			sb.append("SELECT * FROM (");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM(");
			sb.append("	SELECT num, l.userId, subject, opening, useTime, l.tel, address, longitude,");
			sb.append("	latitude, created, introduction, content, hitCount, imageFilename");
			sb.append("	FROM leisure l JOIN member m ON l.userId=m.userId ");
			sb.append(" ORDER BY num DESC) tb WHERE ROWNUM <=?");
			sb.append("	)WHERE rnum >= ?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				LeisureDTO dto = new LeisureDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setOpening(rs.getString("opening"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setAddress(rs.getString("address"));
				dto.setLongitude(rs.getInt("longitude"));
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
			e.printStackTrace();
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
	
	public List<LeisureDTO> listLeisure(int start, int end, String search){
		List<LeisureDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("select * from (");
			sb.append("	select ROWNUM rnum, tb.*from(");
			sb.append("	select num, subject, opening, useTime, tel, address, longitude,");
			sb.append("	latitude, created, introduction, content, hitCount");
			sb.append("	from leisure where INSTR(address,?)>=1 order by num DESC) tb where ROWNUM <=?");
			sb.append("	)where rnum >= ?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, search);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				LeisureDTO dto = new LeisureDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setOpening(rs.getString("opening"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setAddress(rs.getString("address"));
				dto.setLongitude(rs.getInt("longitude"));
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
			e.printStackTrace();
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
	public int dataCount() {
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
			e.printStackTrace();
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
	
	public int dataCount(String search) {
		int result=0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql="select NVL(count(*),0) from leisure where instr(address,?)>=1";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, search);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(Exception e2) {					
				}
			}
		}		
		return result;
	}
	
	public int updateHitCount(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="update leisure set hitCount=hitCount+1 where num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e.toString());
		}finally {
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
