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
<div class="container">
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	<div>
		<form name="noticeForm" method="post" enctype="application/x-www-form-urlencoded">
		<table style="width: 50%; margin:80px auto; border-spacing:3px; border-collapse:collapse; border:2px solid gray;">
		<tr align="left" height="40" style="border-top: 1px solid #ccccccc; border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align:center; ">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			<td style="padding-left:10px;">
			<input type="checkbox" name="notice" value="1">공지
			</td>
		</tr>
		
		<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align: center;">작성자</td>
			<td style="padding-left: 10px;">
				유저이름
			</td>
		</tr>
		
		<tr align="left" style="border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			<td valign="top" style="padding:5px 0px 5px 10px;">
			<textarea name="content" rows="12" class="boxTA" style="width: 95%;">내용넣기</textarea>
			</td>
			
		</tr>
		
		<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align: center;">첨&nbsp;&nbsp;&nbsp;&nbsp;부</td>
			<td style="padding-left:10px;"> 
			<input type="file" name="upload" class="boxTF" size="53" style="height: 25px;">
			</td>
		</tr>
		
		<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align: center;">첨부된파일</td>
			<td style="padding-left:10px;"><a href="#"></a> </td>
		</tr>		
		</table>
		
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		<tr height="45">
			<td align="center">
			<button type="button" class="btn" onclick="">등록할랭 수정할랭</button>
			<button type="reset" class="btn">다시입력</button>
			<button type="button" class="btn" onclick="">등록취소할랭 수정취소할랭</button>
				<input type="hidden" name="num" value="">
				<input type="hidden" name="page" value="">
				<input type="hidden" name="filesize" value="">
				<input type="hidden" name="saveFilename" value="">
				<input type="hidden" name="origianlFilename" value="">
			</td>
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