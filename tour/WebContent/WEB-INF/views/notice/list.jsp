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
<link rel="stylesheet" href="<%=cp%>/resource/css/nav.css" type="text/css">
<style type="text/css">
*{
	margin:0;
	padding:0;
	list-style-type:none;
}

</style>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.submit();
	}
</script>
</head>
<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
</div>

<div class="container">
	<div class="body-container" style="width : 700px; margin-top: 100px auto 100px 100px;">
		<div class="body-title" style="text-align: center; font-size: 35px; font-weight: bold; margin-top: 50px; ">
			<span style="border-top:3px solid #E1E1E1; border-bottom: 3px solid #E1E1E1; color: gray; text-transform: uppercase;     font-family: 'Roboto', Arial,sans-serif,'Nanum Gothic';">
	
			&nbsp;&nbsp;&nbsp;notice&nbsp;&nbsp;&nbsp;</span>
	</div>
	
	<div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
	     <td align="right" width="100%">
					${dataCount}개(${page}/${total_page}페이지)

			</tr>
		</table >
		
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="center" bgcolor="#666" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
				<th width="100" style="color: white;">번호</th>
				<th style="color: white; width: 200" >제목</th>
				<th width="100" style="color: white;">작성자</th>		
				<th width="110" style="color: white;">작성일</th>
				<th width="60" style="color: white;">조회수</th>
				<th width="50" style="color: white;">첨부</th>
			</tr>

			<c:forEach var="dto" items="${listNotice}">
			<tr align="center" bgcolor="#ffffff" height="35" style="border-bottom:1px solid #cccccc; ">
				<td>
				<span>
				<img src="<%=cp %>/resource/images/notice3.png">
				</span></td>
				<td align="center" style="padding-left:10px; width:400px;">
				<img src="<%=cp %>/resource/images/hot.gif">
				<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
				<c:if test="${dto.gap<=1}"><img src="<%=cp %>/resource/images/hot.gif"></c:if>
				</td>
				<td>${dto.userName }</td>
				<td>${dto.created }</td>
				<td>${dto.hitCount }</td>
				<td>
					<c:if test="${not empty dto.saveFilename}">
						<a href="<%=cp %>s/notice/download.do?num=${dto.num}"><img src="<%=cp%>/resource/images/disk.gif" border="0" style="margin-top: 1px;"></a>
					</c:if>
				
				</td>
			</tr>	
		</c:forEach>
		
		<c:forEach var="dto" items="${list}">
			<tr align="center" bgcolor="#ffffff" height="35" style="border-bottom:1px solid #cccccc; ">
				<td>${dto.listNum }</td>
				<td align="left" style="padding-left:10px;">
				<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
				</td>
				<td>${dto.userName }</td>
				<td>${dto.created }</td>
				<td>${dto.hitCount }</td>
				<td>
					<c:if test="${not empty dto.saveFilename}">
						<a href="<%=cp %>/notice/download.do?num=${dto.num}"><img src="<%=cp%>/resource/images/disk.gif" border="0" style="margin-top: 1px;"></a>
					</c:if>				
				</td>
			</tr>	
		</c:forEach>
		</table>
		
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			        <c:if test="${dataCount==0 }">
			                등록된 게시물이 없습니다.
			         </c:if>
			        <c:if test="${dataCount!=0 }">
			               ${paging}
			         </c:if>
				</td>
			   </tr>
		</table>	
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			<tr height="40">
				<td align="left" width="100">
					<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/list.do'">새로고침</button>
				</td>
	
				<td align="center">
					<form name="searchForm" action="<%=cp %>/notice/list.do" method="post">
						<select name="searchKey" class="selectField">
							<option value="subject">제목</option>
							<option value="userName">작성자</option>
							<option value="content">내용</option>
							<option value="created">등록일</option>						
						</select>
						<input type="text" name="searchValue" class="boxTF">
						<button type="button" class="btn" onclick="searchList()">검색</button>	
					</form>
				<td align="right" width="100">
			          <c:if test="${sessionScope.member.userId=='admin'}">
			              <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/created.do';">글올리기</button>
			          </c:if>			
				</td>		
			</tr>
		</table>

	</div>
</div>
</div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>    
</body>
</html>