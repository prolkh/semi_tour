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
		
		//uri�� ���� �۾� ����
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
		//�� ����Ʈ
		String cp=req.getContextPath();
		FestDAO dao=new FestDAO();
		MyUtil util=new MyUtil();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		
		String areacode = req.getParameter("areacode");
		
		if(areacode==null) {
			areacode="All";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			areacode = URLDecoder.decode(areacode, "utf-8");
		}
						
		int rows=5;
		int dataCount;
		int total_page;
		
		//��ü ������ ����		
		if(areacode.length() != 0)
			dataCount=dao.dataCount(areacode);	//search
		else
			dataCount = dao.dataCount();
		
		
		
		//��ü ������ ��
		total_page=util.pageCount(rows,  dataCount);
		if(current_page>total_page)
			current_page=total_page;
		
		// �Խù� ������ ���۰� ����ġ
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		
		//�Խù� ��������
		List<FestDTO> list;
		if(areacode.length() != 0)
			list = dao.listFest(start, end, areacode);
		else 
			list =dao.listFest(start, end);
		
		//����¡ ó��
		String query="";
		String listUrl=cp+"/fest/list.do";
		String articleUrl;
		if(areacode.length()==0) {
			listUrl = cp+"/fest/list.do";
			articleUrl = cp + "/fest/article.do?page=" + current_page;
		} else {
			query += "&search=" + URLEncoder.encode(areacode, "utf-8");
			
			listUrl = cp + "/fest/list.do?"+query;
			articleUrl = cp + "/fest/article.do?page="+current_page+"&"+query;
		}
		
		String paging=util.paging(current_page, total_page, listUrl);
		
		//�������� list.jsp�� �ѱ� ��
		req.setAttribute("list",list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("areacode", areacode);
		
		forward(req, resp, "/WEB-INF/views/fest/list.jsp");
	}
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �۾��� ��
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
		// �Խù� ����
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//�̹����� ������ ���
		String root=session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"fest";
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		FestDAO dao=new FestDAO();
		
		String encType= "UTF-8";
		int maxSize=10*1024*1024;
											// request, ������ ������ ���, �ִ�ũ��, �Ķ����Ÿ��, �������ϸ� ��ȣ
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
			
			// ������ ����� ���ϸ�
			String saveFilename=mreq.getFilesystemName("upload");
			
			//���ϸ� ����
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
