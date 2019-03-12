package com.site;

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

@WebServlet("/site/*")
public class SiteServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		// 세션 정보 
		HttpSession session = req.getSession();		
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		/*  
		// AJAX에서 로그인이 안된 경우 403이라는 에러 코드를 던짐
		String header = req.getHeader("AJAX");
		if(header != null && header.equals("true") && info == null) {
			resp.sendError(403);
			return;
		}
		
		// AJAX가 아닌 경우에 로그인이 안된경우
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}	
		*/	

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
		} else if (uri.indexOf("insertReply.do") != -1) { // AJAX
			insertReply(req, resp);
		} else if (uri.indexOf("listReply.do") != -1) { // AJAX
			listReply(req, resp);
		} else if (uri.indexOf("deleteReply.do") != -1) { // AJAX
			deleteReply(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		String cp = req.getContextPath();
		SiteDAO dao = new SiteDAO();
		MyUtil util = new MyUtil();

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null)
			current_page = Integer.parseInt(page);

		String search = req.getParameter("search");

		if (search == null) {
			search = "";
		}
		if (req.getMethod().equalsIgnoreCase("GET")) {
			search = URLDecoder.decode(search, "utf-8");
		}

		int rows = 5;
		int dataCount, total_page;

		// 전체 데이터 개수
		if (search.length() != 0)
			dataCount = dao.dataCount(search); // search
		else
			dataCount = dao.dataCount();
		total_page = util.pageCount(rows, dataCount);

		if (current_page > total_page)
			current_page = total_page;

		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;

		List<SiteDTO> list;
		if (search.length() != 0)
			list = dao.listSite(start, end, search);
		else
			list = dao.listSite(start, end);

		String query = "";
		String listUrl;
		String articleUrl;

		if (search.length() == 0) {
			listUrl = cp + "/site/list.do";
			articleUrl = cp + "/site/article.do?page=" + current_page;
		} else {
			query += "&search=" + URLEncoder.encode(search, "utf-8");

			listUrl = cp + "/site/list.do?" + query;
			articleUrl = cp + "/site/article.do?page=" + current_page + "&" + query;
		}

		String paging = util.paging(current_page, total_page, listUrl);

		// 포워딩 jsp에 넘길 데이터
		req.setAttribute("list", list);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("search", search);

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

		SiteDAO dao = new SiteDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");

		String search = req.getParameter("search");

		if (search == null) {
			search = "";
		}

		// 조회수
		dao.updateHitCount(num);

		SiteDTO dto = dao.readSite(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/site/list.do?page=" + page);
			return;
		}

		MyUtil util = new MyUtil();
		dto.setContent(util.htmlSymbols(dto.getContent()));
		dto.setIntroduction(util.htmlSymbols(dto.getIntroduction()));

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/site/article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		SiteDAO dao = new SiteDAO();
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));

		SiteDTO dto = dao.readSite(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/site/list.do?page=" + page);
			return;
		}

		// 글을 등록한 사람만 수정가능
		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/site/list.do?page=" + page);
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/site/created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		String cp = req.getContextPath();
		SiteDAO dao = new SiteDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String encType = "UTF-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());

		String page = mreq.getParameter("page");
		if (!mreq.getParameter("uesrId").equals(info.getUserId())) {
			resp.sendRedirect(cp + "/site/list.do?page=" + page);
		}

		int num = Integer.parseInt(mreq.getParameter("num"));

		SiteDTO dto = new SiteDTO();

		dto.setNum(num);
		dto.setSubject(mreq.getParameter("subject"));
		dto.setUseTime(mreq.getParameter("useTime"));
		dto.setZip(Integer.parseInt(mreq.getParameter("zip")));
		dto.setAddress(mreq.getParameter("address"));
		dto.setLongitude(Float.parseFloat(mreq.getParameter("longitude")));
		dto.setLatitude(Float.parseFloat(mreq.getParameter("latitude")));
		dto.setContent(mreq.getParameter("content"));
		dto.setIntroduction(mreq.getParameter("introduction"));
		dto.setImageFilename(mreq.getParameter("imageFilename"));

		if (mreq.getFile("upload") != null) {
			// 기존 파일 지우기
			FileManager.doFiledelete(pathname, dto.getImageFilename());

			// 서버에 저장된 새로운 파일명
			String saveFilename = mreq.getFilesystemName("upload");

			// 이름 변경
			saveFilename = FileManager.doFilerename(pathname, saveFilename);

			dto.setImageFilename(saveFilename);
		}

		dao.updateSite(dto);

		resp.sendRedirect(cp + "/site/article.do?num=" + dto.getNum() + "&page=" + page);
	}

	protected void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		SiteDAO dao = new SiteDAO();
		ReplyDTO dto = new ReplyDTO();
		
		dto.setUserId(info.getUserId());
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setContent(req.getParameter("content"));
		
		dao.insertReply(dto);
		
		String state = "true";
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());		
	}

	protected void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 리스트
		SiteDAO dao = new SiteDAO();
		MyUtil util = new MyUtil();
		
		int num = Integer.parseInt(req.getParameter("num"));
		int current_page = 1;
		String pageNo = req.getParameter("pageNo");
		if(pageNo != null)
			current_page = Integer.parseInt(pageNo);
		
		int rows = 5;
		int total_page = 0;
		int replyCount = 0;
		
		replyCount = dao.dataCountReply(num);
		total_page = util.pageCount(rows, replyCount);
		if(current_page > total_page)
			current_page = total_page;
		
		int start = (current_page-1)*rows+1;
		int end = current_page*rows;
		
		List<ReplyDTO> list = dao.listReply(num, start, end);
		
		for(ReplyDTO dto : list) {
			dto.setContent(util.htmlSymbols(dto.getContent()));
		}
		
		String paging = util.pagingMethod(current_page, total_page, "listPage");
		
		req.setAttribute("list", list);
		req.setAttribute("pageNo", current_page);
		req.setAttribute("replyCount", replyCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		
		forward(req, resp, "/WEB-INF/views/site/listReply.jsp");
	}
	
	protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		int replyNum = Integer.parseInt(req.getParameter("replyNum"));
		
		SiteDAO dao = new SiteDAO();		
		
		String state="false";
		int result=dao.deleteReply(replyNum, info.getUserId());
		if(result==1)
			state="true";
		
		JSONObject job=new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제 완료
		// 수정 완료
		String cp = req.getContextPath();
		SiteDAO dao = new SiteDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");

		// DB에서 가져오기
		SiteDTO dto = dao.readSite(num);
		if (dto == null || (!dto.getUserId().equals(info.getUserId()) && info.getUserRoll() < 3)) {
			resp.sendRedirect(cp + "/site/list.do?page=" + page);
			return;
		}

		FileManager.doFiledelete(pathname, dto.getImageFilename());

		dao.deleteSite(num);
		resp.sendRedirect(cp + "/site/list.do?page=" + page);
	}

}
