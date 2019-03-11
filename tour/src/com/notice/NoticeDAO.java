package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBConn;

public class NoticeDAO   {
	private Connection conn=DBConn.getConnection();
	
	public int insertNotice(NoticeDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO notice(");
			sb.append(" num, notice, userId, subject, content, saveFilename, originalFilename, filesize ");
			sb.append(" ) VALUES (notice_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getSaveFilename());
			pstmt.setString(6, dto.getOriginalFilename());
			pstmt.setLong(7, dto.getFilesize());
			
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;		
	}
	//검색 안할시 list
	public List<NoticeDTO> listNotice(int start, int end){
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM(");
			sb.append("	SELECT ROWNUM rnum, tb.*FROM (");
			sb.append(" 	SELECT num, n.userId, userName");
			sb.append(" 	, subject, saveFilename, hitCount, created ");
			sb.append("  FROM notice n JOIN member m ON n.userId=m.userId");
			sb.append("  ORDER BY num DESC");
			sb.append(") tb WHERE ROWNUM <=?");
			sb.append(") WHERE rnum >=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				
				//rs에서 돌린 해당애들을 get으로 가져와서 객체를 생성한 DTO에 넣어줌 
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
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
	
	//검색할시 list
	public List<NoticeDTO> listNotice(int start, int end, String searchKey, String searchValue){
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM(");
			sb.append("		SELECT num, n.userId, userName, subject, saveFilename, hitCount, created");
			sb.append("			FROM notice n JOIN member m ON n.userId=m.userId");
			
			if(searchKey.equalsIgnoreCase("created")) {
				searchValue=searchValue.replace("-", "");
				sb.append("	WHERE TO_CHAR(created, 'YYYY-MM-DD'=?");
			}else {
				sb.append(" WHERE INSTR(" +searchKey+", ?)>=1");
			}
			sb.append("	ORDER BY num DESC");
			sb.append("		)tb WHERE ROWNUM <=?");
			sb.append(") WHERE rnum>=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
		
	}
	
	//공지글 list
	public List<NoticeDTO> listNotice(){
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT num, n.userId, userName");
			sb.append("	,subject, saveFilename, hitCount, TO_CHAR(created, 'YYYY-MM-DD')created ");
			sb.append("		FROM notice n JOIN member m ON n.userId=m.userId");
			sb.append("	WHERE notice=1");
			sb.append(" ORDER BY num DESC");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
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
	 
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM notice";
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
	
	public int dataCount(String searchKey, String searchValue) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			if(searchKey.equalsIgnoreCase("created")) {
				searchValue=searchValue.replaceAll("-", "");
				sql="SELECT NVL(COUNT(*), 0) FROM notice n JOIN member m ON n.userId=m.userId WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
			}else {   
        		sql="SELECT NVL(COUNT(*), 0) FROM notice n JOIN member m ON n.userId=m.userId WHERE  INSTR(" + searchKey + ", ?) >= 1 "; 
			}
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, searchValue);
			
			rs=pstmt.executeQuery();
			
			if(rs.next())
				result=rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO: handle exception
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
	
	public NoticeDTO readNotice(int num) {
		NoticeDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		sql = "SELECT num, notice, n.userId, userName, subject, content, saveFilename,originalFilename, filesize, hitCount, created ";
		sql+= "  FROM notice n JOIN member m ON n.userId=m.userId WHERE num = ?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NoticeDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setNotice(rs.getInt("notice"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				dto.setFilesize(rs.getLong("filesize"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));                                                      
			}
		} catch (Exception e) {
		   	System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					
				} 
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
		}
			
		return dto;
	}
	
	public NoticeDTO preReadNotice(int num, String searchKey, String searchValue ) {
		NoticeDTO dto=null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue.length()!=0) {
			      sb.append("SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("     SELECT num, subject FROM notice n JOIN member m ON n.userId=m.userId ");
	                if(searchKey.equalsIgnoreCase("created")) {
	                	searchValue=searchValue.replaceAll("-", "");
	                	sb.append("     WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  "); //?:검색한 값
	                } else {
	                	sb.append("     WHERE (INSTR(" + searchKey + ", ?) >= 1)  ");
	                }
	                sb.append("         AND (num > ? ) ");//넘어온 게시물번호
	                sb.append("         ORDER BY num ASC ");
	                sb.append("      ) tb WHERE ROWNUM=1 ");

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setString(1, searchValue);
	                pstmt.setInt(2, num);
				} else {
	                sb.append("SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("     SELECT num, subject FROM notice n JOIN member m ON n.userId=m.userId ");                
	                sb.append("     WHERE num > ? ");
	                sb.append("         ORDER BY num ASC ");
	                sb.append("      ) tb WHERE ROWNUM=1 ");

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setInt(1, num);
				}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
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
	
	public NoticeDTO nextReadNotice(int num, String searchKey, String searchValue) {
		NoticeDTO dto=new NoticeDTO();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			   if(searchValue.length() != 0) {
	                sb.append("SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("     SELECT num, subject FROM notice n JOIN member m ON n.userId=m.userId ");
	                if(searchKey.equalsIgnoreCase("created")) {
	                	searchValue=searchValue.replaceAll("-", "");
	                	sb.append("     WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
	                } else {
	                	sb.append("     WHERE (INSTR(" + searchKey + ", ?) >= 1)  ");
	                }
	                sb.append("         AND (num < ? ) ");
	                sb.append("         ORDER BY num DESC ");
	                sb.append("      ) tb WHERE ROWNUM=1 ");

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setString(1, searchValue);
	                pstmt.setInt(2, num);
				} else {
	                sb.append("SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("     SELECT num, subject FROM notice n JOIN member m ON n.userId=m.userId ");
	                sb.append("     WHERE num < ? ");
	                sb.append("         ORDER BY num DESC ");
	                sb.append("      ) tb WHERE ROWNUM=1 ");

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setInt(1, num);
				}

	            rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					
				}
			}
			if(pstmt!=null) {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						
					}
				}
			}
		}
		
		return dto;
		
	}
	//article부분
	public int updateHitCount(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE notice SET hitCount=hitCount+1 WHERE num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
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
