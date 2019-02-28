<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container">
	<div>
		<div>
			<span>회원 로그인</span>
		</div>
		
		<form name="loginForm" method="post" action="">
			<table>
				<tr align="center" height="60">
					<td>
						<label>아이디</label>
						<input type="text" name="userId" id="userId" class="" maxlength="15">
					</td>			
				</tr>
				<tr align="center" height="60">
					<td>
						<label>패스워드</label>
						<input type="password" name="userPwd" id="userPwd" class="" maxlength="20">
					</td>
				</tr>
				<tr align="center" height="65">
					<td>
						<button type="button" onclick="sendLogin();" class="">로그인</button>
					</td>
				</tr>
				
				<tr align="center" height="45">
					<td>
						<a href="<%=cp%>/">아이디 찾기</a>
						<a href="<%=cp%>/">패스워드 찾기</a>
						<a href="<%=cp%>/member/member.do">회원가입</a>
					</td>
				</tr>
				<tr align="center" height="40">
					 <td><span style="color:blue;">${message}</span></td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>


</body>
</html>