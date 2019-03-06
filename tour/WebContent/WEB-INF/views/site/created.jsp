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
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container">
	<div class="backForm" style="border:none;">
	<h1>글쓰기</h1>
	</div>	
	<div class="backForm">
		<ul class="writeForm">
			<li class="front">제&nbsp;&nbsp;목</li>
			<li class="line">
				<input type="text" class="cinput" name="">
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">개&nbsp;장&nbsp;기&nbsp;간</li>
			<li class="line">
				<input type="text" class="cinput" name="">
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">이&nbsp;용&nbsp;시&nbsp;간</li>
			<li class="line">
				<input type="text" class="cinput" name="">				
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">주&nbsp;소</li>
			<li class="line">
				<textarea class="" name="" style="height:20px;"></textarea>			
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">위&nbsp;도</li>
			<li class="line" style="width:210px;">
				<input type="text" class="cinput" name="" >				
			</li>
			<li class="front">경&nbsp;도</li>
			<li class="line" style="width:210px;">
				<input type="text" class="cinput" name="">				
			</li>
		</ul>
		<ul class="writeForm">
			<li class="front">전&nbsp;화&nbsp;번&nbsp;호</li>
			<li class="line">
				<input type="text" class="cinput" name="">				
			</li>
		</ul>
		<ul class="writeForm" style="height:100px;">
			<li class="front">개&nbsp;요</li>
			<li class="line">
				<textarea class="" name="" style="height:80px;"></textarea>			
			</li>
		</ul>
		<ul class="writeForm" style="height:150px;">
			<li class="front">상&nbsp;세&nbsp;정&nbsp;보</li>
			<li class="line">
				<textarea class="" name="" style="height:130px;"></textarea>	
			</li>
		</ul>
		<ul class="writeForm" style="border-bottom:none;">
			<li class="front">사&nbsp;진</li>
			<li class="line">
				<input type="file" name="upload" accept="image/*" class="photo">
			</li>
		</ul>
	</div>
	<div class="btn-div">
		<ul style="float:right;">
			<li class="li-btn"><button class="btn">올리기</button></li>
			<li class="li-btn"><button class="btn">다시쓰기</button></li>
			<li class="li-btn"><button class="btn">취소</button></li>
		</ul>
	</div>
</div>
<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>