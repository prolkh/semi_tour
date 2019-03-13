package com.qna;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Util.MyUtil;
import com.member.SessionInfo;

@WebServlet("/qna/*")
public class QnaServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {

		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		
		HttpSession session=req.getSession();
		SessionInfo  info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "WEB-INF/views/member/login.jsp");
			return;
		}
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);			
		}
		
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) {
		MyUtil util=new MyUtil();
		QnaDAO dao=new QnaDAO();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt("page");
		
		String searchKey=req.getParameter("searchKey");
		String searchValue=req.getParameter("searchValue");
		
		if(searchKey==null) {
			searchKey="subject";
			searchValue="";
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue = URLDecoder.decode("utf-8");
		}
		
		int dataCount;
		if(searchValue.length()==0){
			dataCount=dao.dataCount();
		}else {
			dataCount=dao.dataCount(searchKey, searchValue);
		}
		
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int start=(current_page-1)*rows-1;
		int end=current_page*rows;
		
		List<QnaDTO> list;
		if(searchValue.length()!=0) 
			list=dao.listQna(start, end);
		else
			list=dao.listQna(start, end, searchKey, searchValue);
		
		int listNum, n=0;
		Iterator<QnaDTO> it=list.iterator();
		while(it.hasNext()) {
			QnaDTO dto=it.next();
			listNum=dataCount-(start+n-1);
			dto.setSetListNum(listNum);
			n++;
		}
		
		String query="";
		if(searchValue.length()!=0) {
			query="searchKey"+searchKey+"&searchValue"+searchValue;
		}
		}
		
	}
	
}
