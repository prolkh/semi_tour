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
<script type="text/javascript">
function sendNotice() {
	var f=document.noticeForm;
	
	var str=f.subject.value;
	if(!str){
		alert("제목을 입력하세요")
		f.subject.focus();
		return;
	}
	str=f.content.value;
	if(!str){
		alert("내용을 입력하세요.")
		f.content.focus();
		return;
	}

	f.action="<%=cp%>/notice/${mode}_ok.do";

	f.submit();
}

</script>


</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
<div class="container">
	 <div class="body-container" style="width: 700px;">
	<div>
		<form name="noticeForm" method="post" enctype="multipart/form-data" >
		<table style="width: 50%; margin:80px auto; border-spacing:3px; border-collapse:collapse; border:2px solid gray;">
		<tr align="left" height="40" style="border-top: 1px solid #ccccccc; border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align:center; ">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			<td style="padding-left:10px;">
			     <input type="text" name="subject" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.subject}">
			</td>
		</tr>
		
		<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			<td width="100" bgcolor="#eeeeee" style="text-align: center;">공지여부</td>
			<td style="padding-left:10px;"> 
			    <input type="checkbox" name="notice" value="1" ${dto.notice==1 ? "checked='checked' ":"" } > 공지
			 </td>
			  </tr>
		
		<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align: center;">작성자</td>
			<td style="padding-left: 10px;">
				${sessionScope.member.userName}
			</td>
		</tr>
		
		<tr align="left" style="border-bottom: 1px solid #cccccc;">
			<td width="100" bgcolor="#eeeeee" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			<td valign="top" style="padding:5px 0px 5px 10px;">
			<textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content }</textarea>
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
			 <td style="padding-left:10px;"> 
				              <a href="#">삭제</a>   
			 </td>
		 </tr> 
		
			
		</table>
		
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		<tr height="45">
			<td align="center">
			<button type="button" class="btn" onclick="sendNotice()">${mode=='update'? '수정완료':'등록하기' }</button>
			<button type="reset" class="btn">다시입력</button>
			<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/list.do';">${mode=='update'?'수정취소':'등록취소' }</button>
			</td>
		</tr>		
		</table>
</form>
</div>
</div>
</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>