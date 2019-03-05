package com.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		
		//uri에 따른 작업 구분
		if(uri.indexOf("login.do")!=-1)	{
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req,resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req,resp);
		} else if(uri.indexOf("member.do")!=-1) {
			memberForm(req,resp);
		} else if(uri.indexOf("member_ok.do")!=-1) {
			memberSubmit(req,resp);
		}
	}
	
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리
		// 세션 객체. 세션 정보는 서버에 저장(로그인 정보, 권한등을 저장)
		HttpSession session=req.getSession();
		
		MemberDAO dao=new MemberDAO();
		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		
		MemberDTO dto=dao.readMember(userId);
		if(dto!=null) {
			if(userPwd.equals(dto.getUserPwd())){
				// 로그인 성공 : 로그인 정보를 서버에 저장
				// 세션의 유지시간을 30분으로 설정(기본 30분)
				session.setMaxInactiveInterval(30*60);
				
				//세션에 저장할 내용
				SessionInfo info=new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				info.setUserRoll(dto.getUserRoll());
				//세션에 member라는  이름으로 저장
				session.setAttribute("member", info);
				
				//메인화면으로 리다이렉트
				resp.sendRedirect(cp);
				return;
			}
		}
		
		//로그인 실패인 경우(다시 로그인 폼으로)
		String msg="아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);
		
		forward(req, resp, "/WEB-INF/views/member/login.jsp");	
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입폼
		req.setAttribute("title", "회원가입");
		req.setAttribute("mode", "created");
		
		String path="/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);
	}
	
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리
		MemberDAO dao=new MemberDAO();
		MemberDTO dto=new MemberDTO();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		dto.setTel(req.getParameter("tel"));
		dto.setEmail(req.getParameter("email"));
		
		int result=dao.insertMember(dto);
		if(result == 0) {
			String message="회원가입이 실패했습니다.";
			
			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			forward(req,resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("<b>"+ dto.getUserName() + "</b>님 회원가입이 되었습니다.<br>");
		sb.append("메인 화면으로 이동하여 로그인 하시기 바랍니다.<br>");
		
		req.setAttribute("title", "회원 가입");
		req.setAttribute("message", sb.toString());
		
		forward(req, resp, "/WEB-INF/views/member/complete.jsp");
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session=req.getSession();
		String cp = req.getContextPath();
		
		// 세션에 저장된 정보를 지운다.
		session.removeAttribute("member");
		
		// 세션에 저장된 정보를 모두 지우고 초기화한다.
		session.invalidate();
		
		// 루트로 리다이렉트
		resp.sendRedirect(cp);
	}
	
}
