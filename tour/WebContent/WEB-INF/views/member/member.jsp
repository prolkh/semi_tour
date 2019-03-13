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
	.body-container .member {font-size: 14px; width:430px; margin:20px auto; padding:10px; border:1px solid; border-color:gray; border-radius:16px;}
	.body-container .member .inner{width:430px; margin-top: 5px; margin-bottom: 15px; text-align: center;}
	.body-container .member .inner > p {width:430px; line-height: 120%;}
	.body-container .member .inner .memberTF {width: 390px; padding-top:5px; padding-bottom:5px;}
	.body-container .member .inner > .block-title {width:430px; font-weight: 700; font-size: 18px;}
	.body-container .member .inner > .block-msg {width:430px; color: #333333;}	
</style>

<script type="text/javascript">
function memberOk() {
	var f=document.memberForm;
	var str;
	
	str = f.userId.value;
	str = str.trim();
	if(!str){
		alert("아이디를 입력하세요!");
		f.userId.focus();
		return;
	}
	
	if(! /^[a-z][a-z0-9_]{4,9}$/i.test(str)){
		alert("아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야됨");
		f.userId.focus();
		return;
	}
	f.userId.value = str;
	
	str = f.userName.value;
	str = str.trim();
	if(!str){
		alert("이름을 입력하세요.");
		f.userName.focus();
		return;
	}
	f.userName.value = str;
	
	
	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("패스워드를 입력하세요.");
		f.userPwd.focus();
		return;
	}
	if(! /^(?=.*[a-z])(?=.*[!@#$%^&*+=-]|.*[0-9]).{5,10}$/i.test(str)) {
		alert("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;
	
	if(str!=f.userPwd2.value){
		alert("패스워드가 일치하지 않습니다.");
		f.userPwd2.focus();
		return;
	}
	
	str = f.tel1.value;
	str = str.trim();
	if(!str) {
		alert("전화번호를 입력하세요.");
		f.tel1.focus();
		return;
	}
	if(!/^(\d+)$/.test(str)){
		alert("숫자만 가능합니다.");
		f.tel1.focus();
		return;
	}
	
	str = f.tel2.value;
	str = str.trim();
	if(!str){
		alert("전화번호를 입력하세요");
		f.tel2.focus();
		return;
	}
	if(!/^(\d+)$/.test(str)){
		alert("숫자만 가능합니다.");
		f.tel2.focus();
		return;
	}
	
	str = f.tel3.value;
	str = str.trim();
	if(!str){
		alert("전화번호를 입력하세요");
		f.tel3.focus();
		return;
	}
	if(!/^(\d+)$/.test(str)){
		alert("숫자만 가능합니다.");
		f.tel3.focus();
		return;
	}
	
	
	
	str = f.email.value;
	str = str.trim();
	if(!str){
		alert("이메일을 입력하세요.");
		f.email.focus();
		return;
	}
	if(! isValidEmail(str)){
		alert("올바른 이메일을 입력해주세요");
		f.email.focus();
		return;
	}
	
	var mode="${mode}";
    if(mode=="created") {
    	f.action = "<%=cp%>/member/member_ok.do";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/member/update_ok.do";
    }
		
	f.submit();	
}

function isValidEmail(data){
    var format = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    return format.test(data); // true : 올바른 포맷 형식
}
</script>

</head>
<body>
    
<div class="container">
	<div class="header membership" style= "margin:0 auto; text-align: center; font-size: 30px; text-decoration: none;">
		<h1><a href="<%=cp%>/" class="logo"><span class="blind">어서오세요</span></a></h1>
	</div>
    <div style="">
	<div class="body-container">
		<form name="memberForm" method="post" action="" onsubmit="">
			<div class="member">
				<div class="inner">
					<p class="block-title">아이디</p>
					<p><input type="text" class="boxTF memberTF" name="userId"></p>
					<p class="block-msg">아이디는 5~10자이며, 첫글자는 영문자로 시작해야합니다</p>
				</div>
				
				<div class="inner">
					<p class="block-title">이름</p>
					<p><input type="text" class="boxTF memberTF" name="userName"></p>
				</div>
				
				
				<div class="inner">
					<p class="block-title">비밀번호</p>
					<p><input type="password" class="boxTF memberTF" name="userPwd"></p>
					<p class="block-msg">비밀번호는 5~10자이며 숫자나 특수문자가 포함되어야 합니다</p>
				</div>
				
				<div class="inner">
					<p class="block-title">비밀번호 확인</p>
					<p><input type="password" class="boxTF memberTF" name="userPwd2"></p>
					<p class="block-msg">비밀번호 한번 더 입력</p>
				</div>
				

				<div class="inner">
					<p class="block-title">전화번호</p>
					<p>
					   <input type="text" name="tel1" class="boxTF" maxlength="3" style="width: 112px; padding-top:5px; padding-bottom:5px;"> -
					   <input type="text" name="tel2" class="boxTF" maxlength="4" style="width: 112px; padding-top:5px; padding-bottom:5px;"> -
					   <input type="text" name="tel3" class="boxTF" maxlength="4" style="width: 112px; padding-top:5px; padding-bottom:5px;">
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
					   <button type="button" name="sendButton" class="btn" onclick="memberOk();">회원가입</button>
					   <button type="reset" class="btn">다시입력</button>
					   <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/';">가입취소</button>
					</p>
				</div>
				
			</div>
		    
		</form>
        
	</div>
    </div>
    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </div>
    
</div>
 
</body>
</html>