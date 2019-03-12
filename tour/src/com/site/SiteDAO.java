package com.site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBConn;

public class SiteDAO {
	private Connection conn = DBConn.getConnection();
	
	// 글 작성
	public int insertSite(SiteDTO dto) {
		int result=0;
		
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT INTO site(num, userId, subject, useTime, zip, address,");
			sb.append("					latitude, longitude, content, introduction, imageFilename)");
			sb.append("VALUES(site_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getUseTime());
			pstmt.setInt(4, dto.getZip());
			pstmt.setString(5, dto.getAddress());
			pstmt.setFloat(6, dto.getLatitude());
			pstmt.setFloat(7, dto.getLongitude());
			pstmt.setString(8, dto.getContent());
			pstmt.setString(9, dto.getIntroduction());
			pstmt.setString(10, dto.getImageFilename());
			
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
	
	// 글 수정
	public int updateSite(SiteDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE site SET subject=?, useTime=?, zip=?, address=?,");
			sb.append("latitude=?, longitude=?, content=?, introduction=?,");
			sb.append("inquiry=?, imageFilename=? ");
			sb.append("WHERE num = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getUseTime());
			pstmt.setInt(3, dto.getZip());
			pstmt.setString(4, dto.getAddress());
			pstmt.setFloat(5, dto.getLatitude());
			pstmt.setFloat(6, dto.getLongitude());
			pstmt.setString(7, dto.getContent());
			pstmt.setString(8, dto.getIntroduction());
			pstmt.setString(9, dto.getImageFilename());
			pstmt.setString(10, dto.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
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
	
	// 검색이 없는 경우
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
	public int dataCount(String search) {
		// 데이터 갯수
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM site WHERE INSTR(address, ?) >= 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search);
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
	// 검색 없는 리스트
	public List<SiteDTO> listSite(int start, int end){
		List<SiteDTO> list = new ArrayList<SiteDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("        SELECT num, n.userId, userName");
			sb.append("            ,subject, useTime,  zip");
			sb.append("            ,address, latitude, longitude");
			sb.append("            ,content, introduction, inquiry");
			sb.append("            ,imageFilename, hitCount, created");
			sb.append("         FROM site  n JOIN member m ON n.userId=m.userId");
			sb.append("	       ORDER BY num DESC");
			sb.append("    ) tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SiteDTO dto = new SiteDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setZip(rs.getInt("zip"));
				dto.setAddress(rs.getString("address"));
				dto.setLatitude(rs.getFloat("latitude"));
				dto.setLongitude(rs.getFloat("longitude"));
				dto.setContent(rs.getString("content"));
				dto.setIntroduction(rs.getString("introduction"));
				dto.setInquiry(rs.getString("inquiry"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
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
	
	
	// 검색 있는 리스트
	public List<SiteDTO> listSite(int start, int end, String search){
		List<SiteDTO> list = new ArrayList<SiteDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("        SELECT num, n.userId, userName");
			sb.append("            ,subject, useTime,  zip");
			sb.append("            ,address, latitude, longitude");
			sb.append("            ,content, introduction, inquiry");
			sb.append("            ,imageFilename, hitCount, created");
			sb.append("         FROM site  n JOIN member m ON n.userId=m.userId");
			sb.append("			WHERE INSTR(address,?) >=1");
			sb.append("	       ORDER BY num DESC");
			sb.append("    ) tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, search);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SiteDTO dto = new SiteDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setUseTime(rs.getString("useTime"));
				dto.setZip(rs.getInt("zip"));
				dto.setAddress(rs.getString("address"));
				dto.setLatitude(rs.getFloat("latitude"));
				dto.setLongitude(rs.getFloat("longitude"));
				dto.setContent(rs.getString("content"));
				dto.setIntroduction(rs.getString("introduction"));
				dto.setInquiry(rs.getString("inquiry"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
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
			sb.append("       WHERE num=?");
			
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
	
	// 조회수 증가
	public int updateHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE site SET hitCount= hitCount+1 WHERE num = ?";
			
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
	
	public int insertReply(ReplyDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT INTO siteReply(replyNum, num, userId, content)");
			sb.append(" VALUES(siteReply_seq.NEXTVAL, ?, ?, ?) ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getContent());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}	
		
		return result;
	}
	
	public int dataCountReply(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM siteReply WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public List<ReplyDTO> listReply(int num, int start, int end) {
		List<ReplyDTO> list = new ArrayList<ReplyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.*  FROM (");
			sb.append("	      SELECT r.replyNum, r.userId, userName, num, content, r.created");
			sb.append("		  FROM bbsReply r");
			sb.append("		  JOIN  member m ON r.userId = m.userId");
			sb.append("		  WHERE num = ?");
			sb.append("		  ORDER BY  r.replyNum DESC");
			sb.append("	) tb  WHERE  ROWNUM  <= ?");
			sb.append(")  WHERE rnum >= ?");	
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}	
		
		return list;
	}
	
	/*
	 * public ReplyDTO readReply(int replyNum) {
	 * 
	 * }
	 */

}
