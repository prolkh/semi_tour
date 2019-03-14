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
		String sql;
		int seq;
		
		try { //번호가 두번씩 중복으로 들어가야되는곳이 있는데 시퀀스를 쓰게되면 전이나 뒷번호까지 같이 들어가므로 따로 담아서 필요할때만 쓰려고 처리
			sql="SELECT qna_seq.NEXTVAL FROM dual";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			seq=0;
			if(rs.next())
				seq=rs.getInt(1);
			rs.close();
			pstmt.close();
			rs=null;
			pstmt=null;
			
			dto.setQnaNum(seq);
			if(mode.equals("created")) {
				// 글쓰기일때
				dto.setGroupNum(seq);
				dto.setOrderNo(0);
				dto.setDepth(0);
				dto.setParent(0);
			} else if(mode.equals("reply")) {
				// 답변일때
				updateOrderNo(dto.getGroupNum(), dto.getOrderNo());
				
				dto.setDepth(dto.getDepth() + 1);
				dto.setOrderNo(dto.getOrderNo() + 1);
			}
			
			sql = "INSERT INTO qna(qnaNum, userId, subject, content, ";
			sql += "  groupNum, depth, orderNo, parent) ";
			sql += "  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
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
	
	// 답변일 경우 orderNo 변경
	public int updateOrderNo(int groupNum, int orderNo) {
		int result = 0;
		PreparedStatement pstmt=null;
		String sql;
		
		sql = "UPDATE qna SET orderNo=orderNo+1 WHERE groupNum = ? AND orderNo > ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, orderNo);
			result = pstmt.executeUpdate();
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
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM qna";
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
	
	// 검색 에서 전체의 개수
    public int dataCount(String searchKey, String searchValue) {
        int result=0;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;

        try {
        	if(searchKey.equals("created")) {
        		searchValue=searchValue.replaceAll("-", "");
        		sql="SELECT NVL(COUNT(*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
        	} else if(searchKey.equals("userName")) {
        		sql="SELECT NVL(COUNT(*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR(userName, ?) = 1 ";
        	} else {
        		sql="SELECT NVL(COUNT(*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR(" + searchKey + ", ?) >= 1 ";
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
	
	public List<QnaDTO> listQna(int start, int end) {
		List<QnaDTO> list=new ArrayList<QnaDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("         SELECT qnaNum, q.userId, userName,");
			sb.append("               subject, groupNum, orderNo, depth, hitCount,");
			sb.append("               TO_CHAR(created, 'YYYY-MM-DD') created");
			sb.append("               FROM qna q");
			sb.append("               JOIN member m ON q.userId=m.userId");
			sb.append("               ORDER BY groupNum DESC, orderNo ASC ");
			sb.append("    ) tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto=new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
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
	
	// 검색에서 리스트
    public List<QnaDTO> listQna(int start, int end, String searchKey, String searchValue) {
        List<QnaDTO> list=new ArrayList<QnaDTO>();

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("         SELECT qnaNum, q.userId, userName,");
			sb.append("               subject, groupNum, orderNo, depth, hitCount,");
			sb.append("               TO_CHAR(created, 'YYYY-MM-DD') created");
			sb.append("               FROM qna q");
			sb.append("               JOIN member m ON q.userId=m.userId");
			if(searchKey.equals("created")) {
				searchValue=searchValue.replaceAll("-", "");
				sb.append("           WHERE TO_CHAR(created, 'YYYYMMDD') = ? ");
			} else if(searchKey.equals("userName")) {
				sb.append("           WHERE INSTR(userName, ?) = 1 ");
			} else {
				sb.append("           WHERE INSTR(" + searchKey + ", ?) >= 1 ");
			}
			
			sb.append("               ORDER BY groupNum DESC, orderNo ASC ");
			sb.append("    ) tb WHERE ROWNUM <= ?");
			sb.append(") WHERE rnum >= ?");
            
			pstmt=conn.prepareStatement(sb.toString());
            
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
            
            rs=pstmt.executeQuery();
            
            while(rs.next()) {
                QnaDTO dto=new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
            
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
	
	public QnaDTO readQna(int qnaNum) {
		QnaDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT qnaNum, q.userId, userName, subject, ");
			sb.append("    content, created, hitCount, groupNum, depth, orderNo, parent ");
			sb.append("    FROM qna q");
			sb.append("    JOIN member m ON q.userId=m.userId");
			sb.append("    WHERE qnaNum=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setParent(rs.getInt("parent"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
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
	
    // 이전글
    public QnaDTO preRead(int groupNum, int orderNo, String searchKey, String searchValue) {
    	QnaDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(searchValue!=null && searchValue.length() != 0) {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("  SELECT qnaNum, subject  ");
    			sb.append("               FROM qna q");
    			sb.append("               JOIN member m ON q.userId=m.userId");
    			if(searchKey.equals("created")) {
    				searchValue=searchValue.replaceAll("-", "");
    				sb.append("           WHERE (TO_CHAR(created, 'YYYYMMDD') = ? ) AND ");
    			} else if(searchKey.equals("userName")) {
    				sb.append("           WHERE (INSTR(userName, ?) = 1 ) AND ");
    			} else {
    				sb.append("           WHERE (INSTR(" + searchKey + ", ?) >= 1 ) AND ");
            	}
            
                sb.append("     (( groupNum = ? AND orderNo < ?) ");
                sb.append("         OR (groupNum > ? )) ");
                sb.append("         ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                
                pstmt.setString(1, searchValue);
                pstmt.setInt(2, groupNum);
                pstmt.setInt(3, orderNo);
                pstmt.setInt(4, groupNum);
			} else {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("     SELECT qnaNum, subject FROM qna q JOIN member m ON q.userId=m.userId ");                
                sb.append("  WHERE (groupNum = ? AND orderNo < ?) ");
                sb.append("         OR (groupNum > ? ) ");
                sb.append("         ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setInt(3, groupNum);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new QnaDTO();
                dto.setQnaNum(rs.getInt("qnaNum"));
                dto.setSubject(rs.getString("subject"));
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

    // 다음글
    public QnaDTO nextReadQna(int groupNum, int orderNo, String searchKey, String searchValue) {
    	QnaDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(searchValue!=null && searchValue.length() != 0) {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("  SELECT qnaNum, subject ");
    			sb.append("               FROM qna q");
    			sb.append("               JOIN member m ON q.userId=m.userId");
    			if(searchKey.equals("created")) {
    				searchValue=searchValue.replaceAll("-", "");
    				sb.append("           WHERE (TO_CHAR(created, 'YYYYMMDD') = ? ) AND ");
    			} else if(searchKey.equals("userName")) {
    				sb.append("           WHERE (INSTR(userName, ?) = 1) AND ");
    			} else {
    				sb.append("           WHERE (INSTR(" + searchKey + ", ?) >= 1) AND ");
    			}
    			
                sb.append("     (( groupNum = ? AND orderNo > ?) ");
                sb.append("         OR (groupNum < ? )) ");
                sb.append("         ORDER BY groupNum DESC, orderNo ASC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, searchValue);
                pstmt.setInt(2, groupNum);
                pstmt.setInt(3, orderNo);
                pstmt.setInt(4, groupNum);

			} else {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("     SELECT qnaNum, subject FROM qna q JOIN member m ON q.userId=m.userId ");
                sb.append("  WHERE (groupNum = ? AND orderNo > ?) ");
                sb.append("         OR (groupNum < ? ) ");
                sb.append("         ORDER BY groupNum DESC, orderNo ASC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setInt(3, groupNum);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new QnaDTO();
                dto.setQnaNum(rs.getInt("qnaNum"));
                dto.setSubject(rs.getString("subject"));
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

	public int updateHitCount(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql = "UPDATE qna SET hitCount=hitCount+1 WHERE qnaNum=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);
			result = pstmt.executeUpdate();
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
	
	public int updateQna(QnaDTO dto, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="UPDATE qna SET subject=?, content=? ";
		sql+= " WHERE qnaNum=? AND userId=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getQnaNum());
			pstmt.setString(4, userId);
			result = pstmt.executeUpdate();
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
	
	public int deleteQna(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM qna WHERE qnaNum IN (SELECT qnaNum FROM qna START WITH  qnaNum = ? CONNECT BY PRIOR qnaNum = parent)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);
			result = pstmt.executeUpdate();
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
}
