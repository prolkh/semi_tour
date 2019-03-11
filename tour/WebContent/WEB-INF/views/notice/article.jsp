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
				${dto.subject }
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td width="50%" align="left" style="padding-left: 5px;">
				이름 : ${dto.userName }
			</td>	
			<td width="50%" align="right" style="padding-right: 5px;">
				${dto.created }| 조회수 ${dto.hitCount }
			</td>
		</tr>
		
		<tr style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
				${dto.content }
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding-left: 5px;">
			   첨&nbsp;&nbsp;부 :
			   <c:if test="${not empty dto.saveFilename }">
			   <a href="<%=cp%>/notice/download.do?num=${dto.num}">${dto.originalFilename}</a>
				(<fmt:formatNumber value="${dto.fileSize/1024}" pattern="0.00"/> Kbyte)
				</c:if>
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding-left: 5px;">
				이전글 :
				<c:if test="${not empty preReadDto }"> 
				<a href="<%=cp%>/notice/article.do?${query}&num=${preReadDto.num}">${preReadDto.subject }</a>
				</c:if>
			</td>
		</tr>
		
		<tr height="35" style="border-bottom: 1px solid #cccccc;">
			<td colspan="2" align="left" style="padding-left: 5px;">
			다음글 : 
			<c:if test="${not empty nextReadDto}">
			<a href="<%=cp%>/notice/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.subject}</a>
			</c:if>
			</td>
		</tr>
		</table>
		
		<table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
		<tr>
			<td width="300" align="left">
			<c:if test="${sessionScope.member.userId=dto.userId }">
				<button type="button" class="btn" onclick="">수정</button>
			</c:if>	
			<c:if test="${sessionScope.member.userId=dto.userId || sessionScope.member.userId=='admin'}">
				<button type="button" class="btn" onclick="">삭제</button>	
			</c:if>			
			</td>
			<td align="right">
				<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/list.do?${query}';">리스트</button>
			</td>
		</tr>
		</table>	
	</div>
	</div>
</div>
</body>
</html>