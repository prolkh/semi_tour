<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행지 올리기</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<style>
*{
	margin:0;
	padding:0;
	list-style-type:none;
}
.container{
	width:940px; 
	height:1040px;
}
.backForm{
	width:680px;
	margin:10px auto;
	border:1px solid darkblue;
}
.front{
	width:100px; 
	text-align:center; 
	margin:5px; 
	height:25px; 
	float:left; 
	padding-top:5px;
}
.writeForm{
	width:660px; 
	height:40px; 
	margin:0px auto; 
	border-bottom:1px solid darkblue;
}
.line{
	width:550px; 
	height:30px; 
	margin:5px auto; 
	float:left;
}
.photo{
	margin:3px;
}
.line_input{
	width:95%; 
	height:20px; 
	margin:3px;
}

.line_text{
	width:95%;
	margin:3px;
}
.under{
	float:left; 
	margin:10px 5px;
}
</style>
<script type="text/javascript">
	function sendOk(){
		var f = document.board;
		
		f.action="<%=cp%>/leisure/${mode}_ok.do";
		
		f.submit();
	}

</script>
</head>
<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="container" style="width:940px; height:1040px;">
	<div class="backForm" style="border:none;">
	<h1>글쓰기</h1>
	</div>	
	<form name="board" method="post" enctype="multipart/form-data">	
	<div class="backForm">
		<ul class="writeForm">
			<li class="front">제&nbsp;&nbsp;목</li>
			<li class="line">
				<input type="text" class="line_input" name="subject" value="${dto.subject}">
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">개&nbsp;장&nbsp;기&nbsp;간</li>
			<li class="line">
				<input type="text" class="line_input" name="opening" value="${dto.opening}">
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">이&nbsp;용&nbsp;시&nbsp;간</li>
			<li class="line">
				<input type="text" class="line_input" name="useTime" value="${dto.useTime}">				
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">주&nbsp;소</li>
			<li class="line">
				<textarea class="line_text" name="address" style="height:20px;">${dto.address}</textarea>			
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">위&nbsp;도</li>
			<li class="line" style="width:210px;">
				<input type="text" class="line_input" name="latitude" value="${dto.latitude}">			
			</li>
			<li class="front">경&nbsp;도</li>
			<li class="line" style="width:210px;">
				<input type="text" class="line_input" name="longitude" value="${dto.longitude}">				
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">전&nbsp;화&nbsp;번&nbsp;호</li>
			<li class="line">
				<input type="text" class="line_input" name="tel" value="${dto.tel }" >				
			</li>
		</ul>
		<ul class="writeForm" style="height:100px;">
			<li class="front">개&nbsp;요</li>
			<li class="line">
				<textarea class="line_text" name="introduction" style="height:80px;">${dto.introduction}</textarea>			
			</li>
		</ul>
		<ul class="writeForm" style="height:150px;">
			<li class="front">상&nbsp;세&nbsp;정&nbsp;보</li>
			<li class="line">
				<textarea class="line_text" name="content" style="height:130px;">${dto.content}</textarea>	
			</li>
		</ul>
		<ul class="writeForm" style="border-bottom:none;">
			<li class="front">사&nbsp;진</li>
			<li class="line">
				<input type="file" name="upload" accept="image/*" class="photo">
			</li>
		</ul>
	</div>
	<div class="backForm" style="height:40px; border:none;">
		<ul style="float:right;">
		<c:if test="${mode=='update'}">
			<input type="hidden" name="num" value="${dto.num }">
			<input type="hidden" name="imageFilename" value="${dto.imageFilename}">
			<input type="hidden" name="page" value="${page }">
			<input type="hidden" name="userId" value="${dto.userId}">
			
		</c:if>
			<li class="under"><button class="btn" type="button" onclick="sendOk()">${mode=='update'?'수정완료':'등록하기'}</button></li>
			<li class="under"><button class="btn" type="reset">다시쓰기</button></li>
			<li class="under"><button class="btn" type="button" onclick="javascript:location.href='<%=cp%>/WEB-INF/views/leisure/list.do';">취소</button></li>			
		
		</ul>
	</div>
	</form>
	
</div>
<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>