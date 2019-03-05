<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container">
	<div class="body-container" style="width:700px;">
	<div class="body-title">
		<div class="body-title"><span>공지사항입니다요</span></div>
	</div>
	
	<div>
		<table style="width:80%; margin: 50px auto 0px; border-spacing: 0px; border-collapse: collapse;">
		<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="center">
				제목넣으시옹
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td width="50%" align="left" style="padding-left: 5px;">
				이름넣으시옹
			</td>	
			<td width="50%" align="right" style="padding-right: 5px;">
				올린시간넣으세용 | 조회수 조회숫자넣으세용
			</td>
		</tr>
		
		<tr style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
				내용넣으세용
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding-left: 5px;">
			   첨&nbsp;&nbsp;부 :
			   <a href="#"></a>
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding-left: 5px;">
				이전글 : 
				<a href="#"></a>
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding-left: 5px;">
			다음글 : 
			<a href="#"></a>
			</td>
		</tr>
		</table>
		
		<table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
		<tr>
			<td width="300" align="left">
				<button type="button" class="btn" onclick="">수정</button>
				<button type="button" class="btn" onclick="">삭제</button>			
			</td>
			<td align="right">
				<button type="button" class="btn" onclick="">리스트</button>
			</td>
		</tr>
		</table>	
	</div>
	</div>
</div>
</body>
</html>