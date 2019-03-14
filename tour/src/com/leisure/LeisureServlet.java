package com.leisure;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import net.sf.json.JSONObject;

@WebServlet("/leisure/*")
public class LeisureServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	private String pathname;
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String uri = req.getRequestURI();
				
		//세션 정보
		HttpSession session = req.getSession();
				
		//이미지를 저장할 경로
		String root = session.getServletContext().getRealPath("/");
		pathname = root+"uploads"+File.separator+"leisure";
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		//uri에 따른 직업 구분
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		}else if(uri.indexOf("created.do")!=-1){
			createdForm(req, resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		}else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		}else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		}else if(uri.indexOf("update_ok.do")!=-1){
			updateSubmit(req, resp);
		}else if(uri.indexOf("delete.do")!=-1) {
			delete(req,resp);
		}else if(uri.indexOf("insertReply.do")!=-1) {
			insertReply(req, resp);
		}else if(uri.indexOf("listReply.do")!=-1) {
			listReply(req,resp);
		}else if(uri.indexOf("deleteReply.do")!=-1) {
			deleteReply(req,resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//게시물 리스트
		
		String cp = req.getContextPath();
		LeisureDAO dao = new LeisureDAO();
		MyUtil util = new MyUtil();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		//검색
		String search=req.getParameter("search");
		if(search==null) {
			search="";
		}
		
		//GET 방식인 경우 디코딩
		if(req.getMethod().equalsIgnoreCase("GET")) {
			search=URLDecoder.decode(search, "UTF-8");
		}
		
		//전체 데이터 개수
		int rows=5;
		int dataCount;
		int total_page;
		if(search.length()!=0) {
			dataCount=dao.dataCount(search);
		}else {
			dataCount=dao.dataCount();
		}
				
		//전체 페이지 수
		total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		//게시물 가져올 시작과 끝 위치
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		
		//게시물 가져오기
		List<LeisureDTO> list;
		if(search.length()!=0) {
			list=dao.listLeisure(start,end,search);
		}else {
			list=dao.listLeisure(start, end);
		}
		
		//페이징 처리
		String query="";
		String listUrl;
		String articleUrl;
		
		if(search.length()==0) {
			listUrl=cp+"/leisure/list.do";
			articleUrl=cp+"/leisure/article.do?page="+current_page;
		}else {
			query +="&search="+URLEncoder.encode(search,"UTF-8");
			
			listUrl=cp+"/leisure/list.do?"+query;
			articleUrl=cp+"/leisure/article.do?page="+current_page+"&"+query;
		}
		
		String paging=util.paging(current_page, total_page, listUrl);
		//포워딩할 list.jsp에 넘길 값
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("search", search);
		
		forward(req,resp, "/WEB-INF/views/leisure/list.jsp");		
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		//세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.jsp");
			return;
		}
		
		req.setAttribute("mode","created");
				
		forward(req, resp, "/WEB-INF/views/leisure/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//게시물 저장
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		LeisureDAO dao = new LeisureDAO();
		
		String encType= "UTF-8";
		int maxSize=8*1024*1024;
		
		//request, 서버에 저장될 경로, 최대크기, 파라미터타입, 동일 파일명 보호
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());	
		
		LeisureDTO dto = new LeisureDTO();
		if(mreq.getFile("upload")!=null) {
			dto.setUserId(info.getUserId());
			dto.setSubject(mreq.getParameter("subject"));
			dto.setOpening(mreq.getParameter("opening"));
			dto.setUseTime(mreq.getParameter("useTime"));
			dto.setAddress(mreq.getParameter("address"));
			dto.setLongitude(Float.parseFloat(mreq.getParameter("longitude")));
			dto.setLatitude(Float.parseFloat(mreq.getParameter("latitude")));
			dto.setTel(mreq.getParameter("tel"));			
			dto.setContent(mreq.getParameter("content"));
			dto.setIntroduction(mreq.getParameter("introduction"));
			
			//저장된 파일이름
			String saveFilename = mreq.getFilesystemName("upload");
			
			//파일명 변경
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			dto.setImageFilename(saveFilename);
			
			dao.insertLeisure(dto);			
		}
		resp.sendRedirect(cp+"/leisure/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//게시물 보기
	
		String cp=req.getContextPath();		
		LeisureDAO dao = new LeisureDAO();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		String search=req.getParameter("search");
		if(search==null) {
			search="";					
		}	
		
		LeisureDTO dto=dao.readLeisure(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/leisure/list.do?page="+page);
			return;
		}
		//조회수 증가
		dao.updateHitCount(num);		
		
		MyUtil util=new MyUtil();
		dto.setContent(util.htmlSymbols(dto.getContent()));
				
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req,resp,"/WEB-INF/views/leisure/article.jsp");
		
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cp=req.getContextPath();
		
		LeisureDAO dao=new LeisureDAO();
		
		HttpSession session= req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		//DB에서 게시물 가져오기
		LeisureDTO dto = dao.readLeisure(num);
		if(dto==null || ! dto.getUserId().equals(info.getUserId()))	{
			resp.sendRedirect(cp+"/leisure/list.do?page="+page);
			return;
		}
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req,resp,"/WEB-INF/views/leisure/created.jsp");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		LeisureDAO dao = new LeisureDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String encType="UTF-8";
		int maxSize=8*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());
		
		String page=mreq.getParameter("page");
		
		if(! mreq.getParameter("userId").equals(info.getUserId())) {
			resp.sendRedirect(cp+"/leisure/list.do?page="+page);
		}
				
		LeisureDTO dto=new LeisureDTO();
		
		dto.setNum(Integer.parseInt(mreq.getParameter("num")));
		dto.setSubject(mreq.getParameter("subject"));
		dto.setOpening(mreq.getParameter("opening"));
		dto.setUseTime(mreq.getParameter("useTime"));
		dto.setTel(mreq.getParameter("tel"));			
		dto.setAddress(mreq.getParameter("address"));
		dto.setLongitude(Float.parseFloat(mreq.getParameter("longitude")));
		dto.setLatitude(Float.parseFloat(mreq.getParameter("latitude")));
		dto.setImageFilename(mreq.getParameter("imageFilename"));
		dto.setContent(mreq.getParameter("content"));
		dto.setIntroduction(mreq.getParameter("introduction"));
		
		if(mreq.getFile("upload")!=null) {
			//기존 파일 지우기
			FileManager.doFiledelete(pathname, dto.getImageFilename());
			//서버에 저장되는 새로운 파일
			String saveFilename=mreq.getFilesystemName("upload");
			//이름변경
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			
			dto.setImageFilename(saveFilename);			
		}
		dao.updateLeisure(dto);
		
		resp.sendRedirect(cp+"/leisure/article.do?num="+dto.getNum()+"&page="+page);
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		LeisureDAO dao = new LeisureDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		//DB에서 해당 게시물 가져오기
		LeisureDTO dto = dao.readLeisure(num);
		if(dto==null || (! dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin"))) {
			resp.sendRedirect(cp+"/leisure/list.do?page="+page);
			return;
		}
		FileManager.doFiledelete(pathname, dto.getImageFilename());
		
		dao.deleteLeisure(num);
		resp.sendRedirect(cp+"/leisure/list.do?page="+page);		
	}
	
	protected void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		LeisureDAO dao = new LeisureDAO();
		ReplyDTO dto = new ReplyDTO();
		
		dto.setUserId(info.getUserId());
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setContent(req.getParameter("content"));
		dto.setCreated(req.getParameter("created"));
		
		dao.insertReply(dto);
		
		String state="true";
		JSONObject job =new JSONObject();
		job.put("state",state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}
	
	protected void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LeisureDAO dao= new LeisureDAO();
		MyUtil util = new MyUtil();
		
		int num=Integer.parseInt(req.getParameter("num"));
		int current_page=1;
		String pageNo=req.getParameter("pageNo");
		
		if(pageNo != null) {
			current_page=Integer.parseInt(pageNo);
		}
		
		int rows=5;
		int total_page=0;
		int replyCount=0;
		
		replyCount=dao.dataCountReply(num);
		
		total_page=util.pageCount(rows, replyCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		
		List<ReplyDTO> list=dao.listReply(num, start, end);
		
		for(ReplyDTO dto:list) {
			dto.setContent(util.htmlSymbols(dto.getContent()));
		}
		
		String paging=util.pagingMethod(current_page, total_page, "listPage");
		
		req.setAttribute("list", list);
		req.setAttribute("pageNo", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("replyCount", replyCount);
		req.setAttribute("paging", paging);		
		
		forward(req,resp,"/WEB-INF/views/leisure/listReply.jsp");
				
	}
	
	protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		int replyNum=Integer.parseInt(req.getParameter("replyNum"));
		
		LeisureDAO dao = new LeisureDAO();
		
		String state="false";
		
		int result=dao.deleteReply(replyNum,info.getUserId());
		if(result==1) {
			state="true";
		}
		JSONObject job=new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}	
}
