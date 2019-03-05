package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.Util.DBConn;

public class NoticeDAO   {
	private Connection conn=DBConn.getConnection();
	
	public int insertNotice(NoticeDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO notice(");
			sb.append("num, notice, userId, subject, content, saveFilename, originalFilename, filesize");
			sb.append(") VALUES (notice_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getSaveFilename());
			pstmt.setString(6, dto.getOriginalFilename());
			pstmt.setInt(7, dto.getFilesize());
			
			pstmt.executeUpdate();
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

}
