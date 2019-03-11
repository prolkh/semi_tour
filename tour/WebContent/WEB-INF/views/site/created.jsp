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
<title>관광지 올리기</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/board/created.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
	function sendSite() {
		var f = document.siteForm;
		
		// 제목입력
		var str = f.subject.value;
		if(!str) {
			f.subject.focus();
			return;
		}
		
		// 주소 입력
		str = f.address.value;
		if(!str) {
			f.address.focus();
			return;
		}
		
		// 위도
		str = f.latitude.value;
		if(!str) {
			f.latitude.focus();
			return;
		}
		
		// 경도
		str = f.longitude.value;
		if(!str) {
			f.longitude.focus();
			return;
		}
		
		// 개요
		str = f.introduction.value;
		if(!str) {
			f.introduction.focus();
			return;
		}		
		
		// 상세정보
		str = f.content.value;
		if(!str) {
			f.content.focus();
			return;
		}
		
		var mode = "${mode}";
		
		if(mode == "created")
			f.action="<%=cp%>/site/created_ok.do";
		else if(mode == "update")
			f.action="<%=cp%>/site/update_ok.do";
			
		f.submit();	
		
	}
	
<c:if test="${mode=='update'}">
	function deleteFile(num) {
		var url = "<%=cp%>/site/deleteFile.do?num="+num+"&page=${page}";
		location.href=url;
	}
</c:if>

</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container">
	<div class="backForm" style="border:none;">
	<h1>글쓰기</h1>
	</div>	
	<form name="siteForm" method="post" enctype="multipart/form-data" action="<%=cp%>/site/created_ok.do">
		<div class="backForm">
			<ul class="writeForm">
				<li class="front">제&nbsp;&nbsp;목</li>
				<li class="line">
					<input type="text" class="cinput" name="subject" value="${dto.subject}">
				</li>
			</ul>
			<ul class="writeForm">
				<li class="front">이&nbsp;용&nbsp;시&nbsp;간</li>
				<li class="line">
					<input type="text" class="cinput" name="useTime" value="${dto.useTime}">				
				</li>
			</ul>
			<ul class="writeForm">
				<li class="front">주&nbsp;소</li>
				<li class="line">
					<textarea name="address" style="height:20px;">${dto.address}</textarea>			
				</li>
			</ul>
			<ul class="writeForm">
				<li class="front">우&nbsp;편&nbsp;번&nbsp;호</li>
				<li class="line">
					<input type="text" class="cinput" name="zip" value="${dto.zip}">
				</li>
			</ul>
			<ul class="writeForm">
				<li class="front">위&nbsp;도</li>
				<li class="line" style="width:210px;">
					<input type="text" class="cinput" name="latitude" value="${dto.latitude}" >				
				</li>
				<li class="front">경&nbsp;도</li>
				<li class="line" style="width:210px;">
					<input type="text" class="cinput" name="longitude" value="${dto.longitude}">				
				</li>
			</ul>
			
			<ul class="writeForm" style="height:100px;">
				<li class="front">개&nbsp;요</li>
				<li class="line">
					<textarea name="introduction" style="height:80px;">${dto.introduction}</textarea>			
				</li>
			</ul>
			<ul class="writeForm" style="height:150px;">
				<li class="front">상&nbsp;세&nbsp;정&nbsp;보</li>
				<li class="line">
					<textarea name="content" style="height:130px;">${dto.content}</textarea>	
				</li>
			</ul>
			<ul class="writeForm" style="border-bottom:none;">
				<li class="front">사&nbsp;진</li>
				<li class="line">
					<input type="file" name="upload" accept="image/*" class="photo" value="${dto.imageFilename}">
				</li>
			</ul>
		</div>
		<div class="btn-div">
			<ul style="float:right;">
				<c:if test="${mode=='update'}">
					<input type="hidden" name="num" value="${dto.num}">
					<input type="hidden" name="imageFilename" value="${dto.imageFilename}">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="uesrId" value="${dto.userId}">
					
				</c:if>			
				<li class="li-btn"><button type="button" class="btn" onclick="sendSite();">${mode=='update'?'수정완료':'등록하기'}</button></li>
				<li class="li-btn"><button type="reset" class="btn">다시쓰기</button></li>
				<li class="li-btn"><button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/site/list.do';">${mode=='update'?'수정취소':'등록취소'}</button></li>
			</ul>
		</div>
	</form>
</div>
<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>

</body>
</html>