<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/nav.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
<style>
*{
margin:0;
padding:0;
list-style-type:none;
}
</style>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
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
		<div class="body-title" style="text-align: center; font-size: 45px; font-weight: bold; margin-top: 50px; ">
			<span style="border-top:3px solid #E1E1E1; border-bottom: 3px solid #E1E1E1; color: gray; text-transform: uppercase;     font-family: 'Roboto', Arial,sans-serif,'Nanum Gothic';">&nbsp;&nbsp;Q&nbsp;&amp;&nbsp;A&nbsp;&nbsp;</span>
	</div>
        
        <div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="right" width="100%">
			          ${dataCount}개(${page}/${total_page} 페이지)
			      </td>

			   </tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="center" bgcolor="#666" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <th width="60" style="color: white;">번호</th>
			      <th style="color: white;">제목</th>
			      <th width="100" style="color: white;">작성자</th>
			      <th width="80" style="color: white;">작성일</th>
			      <th width="60" style="color: white;">조회수</th>
			  </tr>
			 
<c:forEach var="dto" items="${list}">
			  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td>${dto.listNum}</td>
			      <td align="left" style="padding-left: 10px;">
			           <c:forEach var="n" begin="1" end="${dto.depth }">&nbsp;&nbsp;</c:forEach>
			           <c:if test="${dto.depth!=0}">└&nbsp;</c:if>
			           <a href="${articleUrl}&qnaNum=${dto.qnaNum}">${dto.subject}</a>
			      </td>
			      <td>${dto.userName}</td>
			      <td>${dto.created}</td>
			      <td>${dto.hitCount}</td>
			  </tr>
</c:forEach>

			</table>
			 
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			        <c:if test="${dataCount==0}">등록된 게시물이 없습니다.</c:if>
			        <c:if test="${dataCount!=0}">${paging}</c:if>
				</td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="40">
			      <td align="left" width="100">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/qna/list.do';">새로고침</button>
			      </td>
			      <td align="center">
			          <form name="searchForm" action="<%=cp%>/qna/list.do" method="post">
			             	<select name="searchKey" class="selectField">
			                  <option value="subject">제목</option>
			                  <option value="userName">작성자</option>
			                  <option value="content">내용</option>
			                  <option value="created">등록일</option>
			            </select>
			    	<input type="text" name="searchValue" class="boxTF">
			            <button type="button" class="btn" onclick="searchList()">검색</button>
			        </form>
			      </td>
			      <td align="right" width="100">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/qna/created.do';">글올리기</button>
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