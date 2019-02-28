<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String cp = request.getContextPath();
%>


<div class="header-top">
	<div class="header-left">
	
	</div>

	<div class="header-right">
		<div>
			<div style="padding-top: 20px;  float: right;">
                <a href="<%=cp%>/member/login.do">로그인</a>
                    &nbsp;|&nbsp;
                <a href="<%=cp%>/member/member.do">회원가입</a>
			</div>
		</div>
	</div>
</div>

<div class="menu">
	<ul class="nav">
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>		
	</ul>

</div>