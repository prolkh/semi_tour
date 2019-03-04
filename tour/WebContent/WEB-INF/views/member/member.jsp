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
<title>로그인</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<style>
	.body-container .member {font-size: 15px; }
	.body-container .member .inner{margin-top: 5px; margin-bottom: 15px; text-align: center;}
	.body-container .member .inner > p {line-height: 150%;}
	.body-container .member .inner .memberTF {width: 50%; padding-top:10px; padding-bottom:10px;}
	.body-container .member .inner > .block-title {font-weight: 700; font-size: 22px;}
	.body-container .member .inner > .block-msg {color: #333333;}
</style>
</head>

<body>
    
<div class="container">
	<div class="header membership" style= "margin:0 auto; text-align: center; font-size: 30px; text-decoration: none;">
		<h1><a href="#" class="logo"><span class="blind">어서옵쇼 관광지쇼</span></a></h1>
	</div>
          
	<div class="body-container">
		<form>
			<div class="member">
				<div class="inner">
					<p class="block-title">아이디</p>
					<p><input type="text" class="boxTF memberTF" name="userId"></p>
					<p class="block-msg">아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야됨</p>
				</div>
				
				<div class="inner">
					<p class="block-title">이름</p>
					<p><input type="text" class="boxTF memberTF" name="userName"></p>
				</div>
				
				
				<div class="inner">
					<p class="block-title">패스워드</p>
					<p><input type="text" class="boxTF memberTF" name="userPwd"></p>
					<p class="block-msg">비밀번호는 10자 이내로~</p>
				</div>
				
				<div class="inner">
					<p class="block-title">패스워드 확인</p>
					<p><input type="text" class="boxTF memberTF" name="userPwd2"></p>
					<p class="block-msg">비밀번호 한번 더 입력</p>
				</div>
				

				<div class="inner">
					<p class="block-title">전화번호</p>
					<p>
					   <input type="text" name="tel1" class="boxTF" maxlength="3" style="width: 14%; padding-top:10px; padding-bottom:10px; "> -
					   <input type="text" name="tel2" class="boxTF" maxlength="4" style="width: 14%; padding-top:10px; padding-bottom:10px; "> -
					   <input type="text" name="tel3" class="boxTF" maxlength="4" style="width: 14%; padding-top:10px; padding-bottom:10px; ">
					</p>
				</div>

				<div class="inner">
					<p class="block-title">이메일</p>
					<p>
					   <input type="text" name="email" class="boxTF memberTF">
					</p>
				</div>

				<div class="inner">
					<p>
					   <button type="button" class="btn">가입취소</button>
					   <button type="reset" class="btn">다시입력</button>
					   <button type="button" class="btn">가입취소</button>
					</p>
				</div>
				
			</div>
		    
		</form>
        
	</div>
    
    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </div>
    
</div>
 
</body>
</html>