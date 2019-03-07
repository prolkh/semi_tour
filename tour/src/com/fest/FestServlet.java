package com.fest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Util.FileManager;
import com.Util.MyServlet;
import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@WebServlet("/fest/*")
public class FestServlet extends MyServlet{
	private static final long serialVersionUID = 1L;
	
	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//글 리스트
		forward(req, resp, "/WEB-INF/views/fest/list.jsp");
	}
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		req.setAttribute("mode", "created");

		forward(req, resp, "/WEB-INF/views/fest/created.jsp");
	}
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 저장
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		FestDAO dao=new FestDAO();
		
		String encType= "UTF-8";
		int maxSize=10*1024*1024;
											// request, 서버에 저장할 경로, 최대크기, 파라미터타입, 동일파일명 보호
		MultipartRequest mreq=new MultipartRequest(req, pathname, maxSize, 
					encType, new DefaultFileRenamePolicy());
		
		FestDTO dto=new FestDTO();
		if(mreq.getFile("upload")!=null) {
			dto.setUserId(info.getUserId());
			dto.setEventName(mreq.getParameter("eventName"));
			dto.setContent(mreq.getParameter("content"));
			dto.setAddress(mreq.getParameter("address"));
			dto.setStartDate(mreq.getParameter("startDate"));
			dto.setEndDate(mreq.getParameter("endDate"));
			dto.setTel(mreq.getParameter("tel"));
			dto.setHomepage(mreq.getParameter("homepage"));
			dto.setHost(mreq.getParameter("host"));
			dto.setPrice(mreq.getParameter("price"));
			dto.setCreatedDate(mreq.getParameter("createdDate"));
			dto.setImageFilename(mreq.getParameter("imageFilename"));
			
			// 서버에 저장된 파일명
			String saveFilename=mreq.getFilesystemName("upload");
			
			//파일명 변경
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			dto.setImageFilename(saveFilename);
			
			System.out.println(dao.insertFest(dto));
		}
		
		resp.sendRedirect(cp+"/fest/list.do");
		
	}	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
