package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBConn;

public class QnaDAO {
		private Connection conn=DBConn.getConnection();
	
		
	public int insertQna(QnaDTO dto, String mode) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int seq;
		String sql;
		
		try {
			sql="SELECT * FROM qna_seq.NEXTVAL FROM dual";
			
			pstmt=conn.prepareStatement(sql);
			
			seq=0;
			if(rs.next()) {
				seq=rs.getInt(1);
			}
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
		
		dto.setQnaNum(seq);
			if(mode.equals("created")){
				dto.setGroupNum(seq);
				dto.setDepth(0);
				dto.setOrderNo(0);
				dto.setParent(0);
			}else {
				if(mode.equals("reply")) {
					
				}
			}
			
		sql="INSERT INTO qna(qnaNum, userId, subject, content, groupNum, depth, orderNo, parent)";
		sql=" VALUES (?, ?, ?, ? , ?, ?,?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getQnaNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getDepth());
			pstmt.setInt(7, dto.getOrderNo());
			pstmt.setInt(8, dto.getParent());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) 
				try {
					pstmt.close();
				} catch (Exception e2) {

				}	
		}
		return result;
		
	}
	
	public int dataCount () {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL((*), O) FROM qna";
			
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
		if(rs.next()) {
			result=rs.getInt(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				if (pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
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
			if(searchKey.equals("created")) {
				searchValue=searchValue.replaceAll("-", "");
				sql="SELECT NVL((*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE TO_CHAR('created', 'YYYYMMDD') = ?";				
			}else if(searchKey.equals("userName")) {
				sql="SELECT NVL((*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR('userName', ?) = 1";
			}else {
				sql="SELECT NVL((*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR("+ searchKey + ", ?) >=1";
			}
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, searchValue);
			
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
	
	public List<QnaDTO> listQna(int start, int end){
		List<QnaDTO> list=new ArrayList<QnaDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("		SELECT ROWNUM rnum, tb*FROM( ");
			sb.append("			SELECT qnaNum, q.userId, userName, subject, ");
			sb.append("			groupNum, orderNo, depth, hitCount, ");
			sb.append(" 		TO_CHAR(created, 'YYYYMMDD')created");
			sb.append(" 		FROM qna q");
			sb.append("			JOIN member b ON q.userId=m.userId");
			sb.append(" 		ORDER BY groupNum DESC, orderNo ASC");
			sb.append("  	)tb WHERE ROWNUM <=? ");
			sb.append("	 ) WHERE rnum >=? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto=new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setHitCount(rs.getInt("hitCount"));
				
				list.add(dto);
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
	        
	        return list;
	    }

	public List<QnaDTO> listQna(int start, int end, String searchKey, String searchValue){
		List<QnaDTO> list=new ArrayList<QnaDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("		SELECT ROWNUM rum, *tb(  ");
			sb.append("		SELECT qnaNum,  q.userId, userName, subject, content, groupNum");
			sb.append("			, orderNo, depth, hitCount,");
			sb.append("			TO_CHAR(created, 'YYYYMMDD')created ");
			sb.append("			FROM qna q");
			sb.append("			JOIN member m ON q.userId=m.userId");
			sb.append(" )tb WHERE ROWNUM <= ?");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto=new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userNmae"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
						} catch (Exception e3) {
							// TODO: handle exception
						}
					}
				}

		return list;
		
	}
	}

