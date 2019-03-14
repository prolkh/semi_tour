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
<title>질문과 답변</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>



<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.userId=='admin'}">
<script type="text/javascript">
function deleteQna(num) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/qna/delete.do?qnaNum="+num+"&${query}";
		location.href=url;
	}
}
</script>
</c:if>



</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="body-container" style="width: 700px;">
        <div class="body-title" style="margin-top: 20px;">
            <h3><span ><img src="<%=cp%>/resource/img/qna.png"></span> 질문과 답변 </h3>
        </div>
        
        <div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				   <c:if test="${dto.depth!=0 }">[Re] </c:if>
				   ${dto.subject}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       이름 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.created } | 조회 ${dto.hitCount}
			    </td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			      ${dto.content}
			   </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			       이전글 :
                  <c:if test="${not empty preReadDto}">
                         <a href="<%=cp%>/qna/article.do?qnaNum=${preReadDto.qnaNum}&${query}">${preReadDto.subject}</a>
                  </c:if>
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			       다음글 :
                  <c:if test="${not empty nextReadDto}">
                         <a href="<%=cp%>/qna/article.do?qnaNum=${nextReadDto.qnaNum}&${query}">${nextReadDto.subject}</a>
                  </c:if>
			    </td>
			</tr>
			</table>
			
			<table style="width: 100%; margin: 20px auto 20px; border-spacing: 0px;">
			<tr height="45">
			    <td width="300" align="left">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/qna/reply.do?qnaNum=${dto.qnaNum}&page=${page}';">답변</button>
			          <c:if test="${sessionScope.member.userId==dto.userId}">
			          	<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/qna/update.do?qnaNum=${dto.qnaNum}&${query}';">수정</button>
			          </c:if>
			          <c:if test="${sessionScope.member.userId!=dto.userId}">
			          	<button type="button" class="btn" disabled="disabled">수정</button>
			          </c:if>
			          <c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.userId=='admin'}">
			          <button type="button" class="btn" onclick="deleteQna('${dto.qnaNum}');">삭제</button>
					  </c:if>	  
					  <c:if test="${sessionScope.member.userId!=dto.userId&&sessionScope.member.userId!='admin'}">
			          <button type="button" class="btn"  disabled="disabled">삭제</button>
					  </c:if>	
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/qna/list.do?${query}';">리스트</button>
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