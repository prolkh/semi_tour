package com.site;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

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

@WebServlet("/site/*")
public class SiteServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do"); // 포워딩이나 리다이렉팅
			return;
		}

		// 이미지를 저장할 경로
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "site";
		File f = new File(pathname);

		if (!f.exists()) {
			f.mkdirs();
		}

		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("reply.do") != -1) {
			replyForm(req, resp);
		} else if (uri.indexOf("reply_ok.do") != -1) {
			replySubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		// String cp = req.getContextPath();
		SiteDAO dao = new SiteDAO();
		MyUtil util = new MyUtil();

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null)
			current_page = Integer.parseInt(page);
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if(searchKey==null) {
			searchKey="subject";
			searchValue="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue = URLDecoder.decode(searchValue, "utf-8");
		}
		
		int rows = 10;
		int dataCount, total_page;
		
		// 전체 데이터 개수
		if(searchValue.length() != 0 )
			dataCount = dao.dataCount(); //searchKey, searchValue
		else
			dataCount = dao.dataCount();
		total_page = util.pageCount(rows, dataCount);
		
		if(current_page>total_page)
			current_page=total_page;
		
		// int start = (current_page-1)*rows+1;
		// int end = current_page*rows;
		
		
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/site/list.jsp");

	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 쓰기 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		if (info.getUserRoll() < 3) {
			resp.sendRedirect(cp + "/site/list.do");
			return;
		}

		req.setAttribute("mode", "created");

		forward(req, resp, "/WEB-INF/views/site/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 게시물 저장
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		SiteDAO dao = new SiteDAO();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		if (info.getUserRoll() < 3) {
			resp.sendRedirect(cp + "/site/list.do");
			return;
		}

		String encType = "UTF-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());

		SiteDTO dto = new SiteDTO();
		if (mreq.getFile("upload") != null) {
			dto.setUserId(info.getUserId());
			dto.setUserName(info.getUserName());
			dto.setSubject(mreq.getParameter("subject"));
			dto.setUseTime(mreq.getParameter("useTime"));
			dto.setZip(Integer.parseInt(mreq.getParameter("zip")));
			dto.setAddress(mreq.getParameter("address"));
			dto.setLongitude(Float.parseFloat(mreq.getParameter("longitude")));
			dto.setLatitude(Float.parseFloat(mreq.getParameter("latitude")));
			dto.setContent(mreq.getParameter("content"));
			dto.setIntroduction(mreq.getParameter("introduction"));

			String saveFilename = mreq.getFilesystemName("upload");

			saveFilename = FileManager.doFilerename(pathname, saveFilename);
			dto.setImageFilename(saveFilename);

			dao.insertSite(dto);
		}
		resp.sendRedirect(cp + "/site/list.do");

	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 보기
		String cp = req.getContextPath();

		SiteDAO dao = new SiteDAO();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");

		SiteDTO dto = dao.readSite(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/site/list.do?page=" + page);
			return;
		}

		MyUtil util = new MyUtil();
		dto.setContent(util.htmlSymbols(dto.getContent()));

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/site/article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
