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
	
	public int dataCount(String area, String month) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			if(month.equals("")) {
				sql="SELECT NVL(COUNT(*), 0) FROM event WHERE INSTR(address, ?) >= 1";
			} else if(area.equals("")) {
				sql="SELECT NVL(COUNT(*), 0) FROM event WHERE TO_CHAR(startdate, 'MM') = ?";
			} else {
				sql="SELECT NVL(COUNT(*), 0) FROM event WHERE INSTR(address, ?) >= 1 AND TO_CHAR(startdate, 'MM') = ?";
			}
			
			pstmt=conn.prepareStatement(sql);
			
			if(month.equals("")) {
				pstmt.setString(1, area);
			} else if(area.equals("")) {
				pstmt.setString(1, month);
			} else {
				pstmt.setString(1, area);
				pstmt.setString(2, month);
			}
//			sql="SELECT NVL(COUNT(*), 0) FROM event WHERE INSTR(address, ?) >= 1 AND (TO_CHAR(startdate, 'MM') = ?";
//			pstmt.setString(1, area);
//			pstmt.setString(2, month);
//			pstmt.setString(3, month);

			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);

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
	
	// 검색 있는 리스트
	public List<FestDTO> listFest(int start, int end, String area, String month){
		List<FestDTO> list = new ArrayList<FestDTO>();
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("	    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("	        SELECT num, userId, eventName, address, startDate, endDate, tel, homepage, host, price, created, imageFilename, content");
			sb.append("	        FROM event");
			if(month.equals("")) {
				sb.append("		WHERE INSTR(address, ?) >= 1");
			} else if(area.equals("")) {
				sb.append("		WHERE TO_CHAR(startdate, 'MM') = ?");
			} else {
				sb.append("		WHERE INSTR(address, ?) >= 1 AND TO_CHAR(startdate, 'MM') = ?");
			}
			sb.append("	        ORDER BY num DESC");
			sb.append("	    )tb WHERE ROWNUM  <= ?");
			sb.append(") WHERE rnum>=?");
			
			pstmt = conn.prepareStatement(sb.toString());

			if(month.equals("")) {
				pstmt.setString(1, area);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			} else if(area.equals("")) {
				pstmt.setString(1, month);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			} else {
				pstmt.setString(1, area);
				pstmt.setString(2, month);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			}

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
	
	public FestDTO readFest(int num) {
		FestDTO dto=null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT num, e.userId userId, eventname, address, startdate, enddate, e.tel tel, homepage, host, price,"
					+ " created, imagefilename, content FROM event e JOIN member m ON e.userId = m.userId WHERE num= ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new FestDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setEventName(rs.getString("eventname"));
				dto.setAddress(rs.getString("address"));
				dto.setStartDate(rs.getDate("startdate").toString());
				dto.setEndDate(rs.getDate("enddate").toString());
				dto.setTel(rs.getString("tel"));
				dto.setHomepage(rs.getString("homepage"));
				dto.setHost(rs.getString("host"));
				dto.setPrice(rs.getString("price"));
				dto.setCreatedDate(rs.getString("created"));
				dto.setImageFilename(rs.getString("imagefilename"));
				dto.setContent(rs.getString("content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public int updateFest(FestDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql= "UPDATE event SET eventname= ?, address= ?, startdate= ?, enddate= ?, tel= ?, "
					+ " homepage= ?, host= ?, price= ?, imagefilename= ?, content=? WHERE num = ?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getEventName());
			pstmt.setString(2, dto.getAddress());
			pstmt.setString(3, dto.getStartDate());
			pstmt.setString(4, dto.getEndDate());
			pstmt.setString(5, dto.getTel());
			pstmt.setString(6, dto.getHomepage());
			pstmt.setString(7, dto.getHost());
			pstmt.setString(8, dto.getPrice());
			pstmt.setString(9, dto.getImageFilename());
			pstmt.setString(10, dto.getContent());
			pstmt.setInt(11, dto.getNum());
			
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return result;
	}
	
	public int deleteFest(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM event WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
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
	
	
}