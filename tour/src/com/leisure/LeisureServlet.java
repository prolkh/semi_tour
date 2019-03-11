package com.leisure;

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

@WebServlet("/leisure/*")
public class LeisureServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	private String pathname;
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String uri = req.getRequestURI();
				
		//���� ����
		HttpSession session = req.getSession();
				
		//�̹����� ������ ���
		String root = session.getServletContext().getRealPath("/");
		pathname = root+"uploads"+File.separator+"leisure";
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		//uri�� ���� ���� ����
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
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�Խù� ����Ʈ
		
		String cp = req.getContextPath();
		LeisureDAO dao = new LeisureDAO();
		MyUtil util = new MyUtil();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		//�˻�
		String search=req.getParameter("search");
		if(search==null) {
			search="";
		}
		
		//GET ����� ��� ���ڵ�
		if(req.getMethod().equalsIgnoreCase("GET")) {
			search=URLDecoder.decode(search, "UTF-8");
		}
		
		//��ü ������ ����
		int rows=5;
		int dataCount;
		int total_page;
		if(search.length()!=0) {
			dataCount=dao.dataCount(search);
		}else {
			dataCount=dao.dataCount();
		}
		
		
		//��ü ������ ��
		total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		//�Խù� ������ ���۰� �� ��ġ
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		
		//�Խù� ��������
		List<LeisureDTO> list;
		if(search.length()!=0) {
			list=dao.listLeisure(start,end,search);
		}else {
			list=dao.listLeisure(start, end);
		}
		
		//����¡ ó��
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
		//�������� list.jsp�� �ѱ� ��
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		
		forward(req,resp, "/WEB-INF/views/leisure/list.jsp");		
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		//���� ����
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
		//�Խù� ����
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		LeisureDAO dao = new LeisureDAO();
		
		String encType= "UTF-8";
		int maxSize=8*1024*1024;
		
		//request, ������ ����� ���, �ִ�ũ��, �Ķ����Ÿ��, ���� ���ϸ� ��ȣ
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
			
			//����� �����̸�
			String saveFilename = mreq.getFilesystemName("upload");
			
			//���ϸ� ����
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			dto.setImageFilename(saveFilename);
			
			dao.insertLeisure(dto);			
		}
		resp.sendRedirect(cp+"/leisure/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�Խù� ����
	
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
		//��ȸ�� ����
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
		
		//DB���� �Խù� ��������
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
		
		if(!mreq.getParameter("userId").equals(info.getUserId())) {
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
			//���� ���� �����
			FileManager.doFiledelete(pathname, dto.getImageFilename());
			//������ ����Ǵ� ���ο� ����
			String saveFilename=mreq.getFilesystemName("upload");
			//�̸�����
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			
			dto.setImageFilename(saveFilename);			
		}
		dao.updateLeisure(dto);
		
		resp.sendRedirect(cp+"/leisure/article.do?num="+dto.getNum()+"&page="+page);
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
