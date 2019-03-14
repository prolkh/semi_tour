<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String cp = request.getContextPath();
%>


<div class="header-top">
	<div class="header-left"></div>
	<div class="header-right">
		<div>
			<div style="padding-top: 20px;  float: right;">
				<c:if test="${empty sessionScope.member}">
	                <a href="<%=cp%>/member/login.do" style="color:white">로그인</a>
	                    <span style="color:white">&nbsp;|&nbsp;</span>
	                <a href="<%=cp%>/member/member.do" style="color:white">회원가입</a>
                </c:if>
                <c:if test="${not empty sessionScope.member }">
                	<span> ${sessionScope.member.userName}</span>님
                	&nbsp;|&nbsp;
                    <a href="<%=cp%>/member/logout.do">로그아웃</a>
                    &nbsp;|&nbsp;
                    <a href="<%=cp%>/member/pwd.do?mode=update">정보수정</a>
                </c:if>
			</div>
		</div>
	</div>
</div>

<div class="header-main">

</div>
