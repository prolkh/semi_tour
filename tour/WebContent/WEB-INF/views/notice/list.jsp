<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>어서옵쇼 관광지쇼</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/resource/css/layout.css" type="text/css">
<script type="text/javascript" src="<%=cp%>/resource/js/urtil.js"></script>
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.submit();
	}
</script>
</head>
<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container">
	<div class="body-container" style="width : 700px;">
		<div class="body-title">
			<h3><span style="font-family:wedings"></span>공지사항</h3>
	</div>
	
	<div>
		<table style="width 100%; margin: 20px auto 0px; border-spacing: 0px; ">
			<tr height="35">
				<td align="left" width="50">
					페이지
				</td>
				<td align="right">
					&nbsp;
				</td>
			</tr>
		</table >
		
		<table style="width 100%; margin: 20px auto 0px; border-spacing: 0px; ">
			<tr align="center" bgcolor="#eeeeee" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc; "> 
				<th width="60" style="color: #787878;">번호</th>
				<th style="color: #787878; width: 200" >제목</th>
				<th width="100" style="color: #787878;">작성자</th>		
				<th width="80" style="color: #787878;">작성일</th>
				<th width="60" style="color: #787878;">조회수</th>
				<th width="50" style="color: #787878;">첨부</th>
			</tr>
			<tr align="center" bgcolor="#ffffff" height="35" style="border-bottom:1px solid #cccccc; ">
				<td><span style="display: inline-block; width:28px; height: 18px; line-height:18px; background: #ED4C00; color: #FFFFFF; ">공지</span></td>
				<td align="center" style="padding-left:10px; width:400px;">제목넣으셈</td>
				<td>아이디</td>
				<td>작성일자</td>
				<td>조회수</td>
				<td>이미지파일</td>
				<td></td>
			</tr>
		</table>
		
		<table style="width:100%; margin: 0px auto; border-spacing: 0px;">
			<tr height="35">
				<td align="center">게시물</td>
			</tr>
		</table>
		
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			<tr height="40">
				<td align="left" width="100">
					<button type="button" class="btn">새로고침</button>
				</td>
				<td align="center">
					<form name="searchForm" action="center" method="post">
						<select name="searchKey" class="selectField">
							<option value="subject">제목</option>
							<option value="userName">작성자</option>
							<option value="content">내용</option>
							<option value="created">등록일</option>						
						</select>
						<input type="text" name="searchValue" class="boxTF">
						<button type="button" class="btn" onclick="">검색</button>
					</form>
				</td>
			</tr>
		</table>
	</div>
</div>
</div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </div>
</body>
</html>