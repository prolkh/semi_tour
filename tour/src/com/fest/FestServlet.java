package com.fest;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Util.FileManager;
import com.Util.MyServlet;
import com.Util.MyUtil;
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

		String uri=req.getRequestURI();
		
		//이미지를 저장할 경로
		HttpSession session=req.getSession();
		String root=session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"fest";
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		//uri에 따른 작업 구분
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
		String cp=req.getContextPath();
		FestDAO dao=new FestDAO();
		MyUtil util=new MyUtil();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		// 달 검색
		String monthcode = req.getParameter("monthcode");
		
		if(monthcode==null) {
			monthcode="";
		}
		
		// 지역검색
		String areacode = req.getParameter("areacode");
		
		if(areacode==null) {
			areacode="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			areacode = URLDecoder.decode(areacode, "utf-8");
		}
						
		int rows=5;
		int dataCount;
		int total_page;
		
		//전체 데이터 개수		
		if(areacode.length() != 0 || monthcode.length() !=0)
			dataCount=dao.dataCount(areacode, monthcode);
		else
			dataCount = dao.dataCount();
		
		
		
		//전체 페이지 수
		total_page=util.pageCount(rows,  dataCount);
		if(current_page>total_page)
			current_page=total_page;
		
		// 게시물 가져올 시작과 끝위치
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		
		//게시물 가져오기
		List<FestDTO> list;
		if(areacode.length() != 0 || monthcode.length() != 0)
			list = dao.listFest(start, end, areacode, monthcode);
		else 
			list =dao.listFest(start, end);
		
		//페이징 처리
		String query="";
		String listUrl=cp+"/fest/list.do";
		String articleUrl;
		if(areacode.length() == 0 && monthcode.length() == 0) {
			listUrl = cp+"/fest/list.do";
			articleUrl = cp + "/fest/article.do?page=" + current_page;
		} else {
			query += "&areacode=" + URLEncoder.encode(areacode, "utf-8");
			query += "&monthcode=" + monthcode;
			
			listUrl = cp + "/fest/list.do?"+query;
			articleUrl = cp + "/fest/article.do?page="+current_page+"&"+query;
		}
		
		String paging=util.paging(current_page, total_page, listUrl);
		
		//포워딩할 list.jsp에 넘길 값
		req.setAttribute("list",list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("areacode", areacode);
		req.setAttribute("monthcode", monthcode);
		
		forward(req, resp, "/WEB-INF/views/fest/list.jsp");
	}
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		String cp=req.getContextPath();

		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
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
		// 게시물 보기
		String cp=req.getContextPath();
		
		FestDAO dao=new FestDAO();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		FestDTO dto=dao.readFest(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/fest/list.do?page="+page);
			return;
		}
		
		dto.setContent(dto.getContent().replaceAll("&", "&amp;")
										.replaceAll("\"", "&quot;")
										.replaceAll(">", "&gt;")
										.replaceAll("<", "&lt;")
										.replaceAll("/n", "<br>")
										.replaceAll("\\s", "&nbsp;"));
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req,resp, "/WEB-INF/views/fest/article.jsp");
	}
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		String cp=req.getContextPath();
		FestDAO dao=new FestDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		//DB에서 해당 게시물 가져오기
		FestDTO dto=dao.readFest(num);
		if(dto==null || !dto.getUserId().contentEquals(info.getUserId())) {
			resp.sendRedirect(cp+"/fest/list.do?page="+page);
			return;
		}
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/fest/created.jsp");
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		String cp=req.getContextPath();
		FestDAO dao=new FestDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(req,pathname, maxSize,
				encType, new DefaultFileRenamePolicy());
		
		String page=mreq.getParameter("page");
		
		if(! mreq.getParameter("userId").contentEquals(info.getUserId())) {
			resp.sendRedirect(cp+"/fest/list.do?page="+page); }
		 
		
		FestDTO dto=new FestDTO();
		
		dto.setNum(Integer.parseInt(mreq.getParameter("num")));
		dto.setEventName(mreq.getParameter("eventName"));
		dto.setAddress(mreq.getParameter("address"));
		dto.setStartDate(mreq.getParameter("startDate"));
		dto.setEndDate(mreq.getParameter("endDate"));
		dto.setTel(mreq.getParameter("tel"));
		dto.setHomepage(mreq.getParameter("homepage"));
		dto.setHost(mreq.getParameter("host"));
		dto.setPrice(mreq.getParameter("price"));
		dto.setImageFilename(mreq.getParameter("imageFilename"));
		dto.setContent(mreq.getParameter("content"));
				
		if(mreq.getFile("upload")!=null) {
			// 기존 파일 지우기
			FileManager.doFiledelete(pathname, dto.getImageFilename());
			
			// 서버에 저장된 새로운 파일명
			String saveFilename=mreq.getFilesystemName("upload");
			
			// 이름 변경
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			
			dto.setImageFilename(saveFilename);
		}
		
		System.out.println(dao.updateFest(dto)+"개 행 업데이트");
		
		resp.sendRedirect(cp+"/fest/article.do?num="+dto.getNum()+"&page="+page);
	}
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제 완료
		String cp=req.getContextPath();
		FestDAO dao=new FestDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		//DB에서 해당 게시물 가져오기
		FestDTO dto=dao.readFest(num);
		if(dto==null || (!dto.getUserId().contentEquals(info.getUserId()) && !info.getUserId().equals("admin"))) {
			resp.sendRedirect(cp+"/fest/list.do?page="+page);
			return;
		}
		FileManager.doFiledelete(pathname, dto.getImageFilename());
		
		dao.deleteFest(num);
		resp.sendRedirect(cp+"/fest/list.do?page="+page);
	}
}
