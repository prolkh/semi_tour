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

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<style type="text/css">
*{ margin:0; padding: 0; }

body{
	background:#f5f6f7;
}
.lbl{
	position:absolute;
	margin: 14px 0px 0px 14px;
	color: #999999; font-size: 16pt;
}

.loginText{
	width:330px; height:35px;
	padding: 5px;
	padding-left:15px;
	border : 1px solid #999999;
	color: #333333;
	margin: 5px 0px;
	font-size:14px;
	
}

.btnConfirm{
	font-size: 17px;
	border: none;
	color:#ffffff;
	background: chocolate;
	width:352px;
	height: 50px;
	line-height: 50px;	
}

.btn:active, .btn:focus, .btn:hover {
	background-color:#e6e6e6;
	border-color: #adadad;
	color: #333333;
}

.login-container{
	background:white;
	margin: 10px auto;
	padding : 50px;
	border: 1px solid lightgrey;
	width: 365px; 
}

.login-container a{
	text-decoration: none;
}
</style>


<script type="text/javascript">
function labelHidden(ob, id){
	if(!ob.value){
		document.getElementById(id).style.display="";
	} else {
		document.getElementById(id).style.display="none";
	}
	
}

function sendOk() {
    var f = document.pwdForm;

    var str = f.userPwd.value;
    if(!str) {
        alert("\n패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/pwd_ok.do";
    f.submit();
}
</script>

</head>
<body>
<div style="height: 150px; background:grey;">&nbsp;</div>



<div class="container" style="margin-top:50px; margin-bottom:50px;">
	<div class="login-container">
		<div style="text-align: center;  margin-top: 5px; margin-bottom: 10px;">
			<span style="font-weight: bold; font-size: 25px;">비밀번호 확인</span>
		</div>
		
		
		<form name="pwdForm" method="post" action="">
			<table>
				<tr align="center" height="60">
					<td>
						<input type="text" name="userId" id="userId" class="loginText" maxlength="15"
								value="${sessionScope.member.userId}" readonly="readonly">&nbsp;
					</td>			
				</tr>
				<tr align="center" height="60">
					<td>
						<label for="userPwd" id="lblUserPwd" class="lbl">패스워드</label>
						<input type="password" name="userPwd" id="userPwd" class="loginText" maxlength="20"
								onfocus="document.getElementById('lblUserPwd').style.display='none';"
								onblur="labelHidden(this, 'lblUserPwd')">
								
					</td>
				</tr>
				<tr align="center" height="65">
					<td>
						<button type="button" onclick="sendOk();" class="btnConfirm">확인</button>
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