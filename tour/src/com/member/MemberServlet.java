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
		
		//uri�� ���� �۾� ����
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
		// �α��� ��
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ó��
		// ���� ��ü. ���� ������ ������ ����(�α��� ����, ���ѵ��� ����)
		HttpSession session=req.getSession();
		
		MemberDAO dao=new MemberDAO();
		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		
		MemberDTO dto=dao.readMember(userId);
		if(dto!=null) {
			if(userPwd.equals(dto.getUserPwd())){
				// �α��� ���� : �α��� ������ ������ ����
				// ������ �����ð��� 30������ ����(�⺻ 30��)
				session.setMaxInactiveInterval(30*60);
				
				//���ǿ� ������ ����
				SessionInfo info=new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				info.setUserRoll(dto.getUserRoll());
				//���ǿ� member���  �̸����� ����
				session.setAttribute("member", info);
				
				//����ȭ������ �����̷�Ʈ
				resp.sendRedirect(cp);
				return;
			}
		}
		
		//�α��� ������ ���(�ٽ� �α��� ������)
		String msg="���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.";
		req.setAttribute("message", msg);
		
		forward(req, resp, "/WEB-INF/views/member/login.jsp");	
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ��������
		req.setAttribute("title", "ȸ������");
		req.setAttribute("mode", "created");
		
		String path="/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);
	}
	
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ������ ó��
		MemberDAO dao=new MemberDAO();
		MemberDTO dto=new MemberDTO();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		dto.setTel(req.getParameter("tel"));
		dto.setEmail(req.getParameter("email"));
		
		int result=dao.insertMember(dto);
		if(result == 0) {
			String message="ȸ�������� �����߽��ϴ�.";
			
			req.setAttribute("title", "ȸ�� ����");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			forward(req,resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("<b>"+ dto.getUserName() + "</b>�� ȸ�������� �Ǿ����ϴ�.<br>");
		sb.append("���� ȭ������ �̵��Ͽ� �α��� �Ͻñ� �ٶ��ϴ�.<br>");
		
		req.setAttribute("title", "ȸ�� ����");
		req.setAttribute("message", sb.toString());
		
		forward(req, resp, "/WEB-INF/views/member/complete.jsp");
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α׾ƿ�
		HttpSession session=req.getSession();
		String cp = req.getContextPath();
		
		// ���ǿ� ����� ������ �����.
		session.removeAttribute("member");
		
		// ���ǿ� ����� ������ ��� ����� �ʱ�ȭ�Ѵ�.
		session.invalidate();
		
		// ��Ʈ�� �����̷�Ʈ
		resp.sendRedirect(cp);
	}
	
}
