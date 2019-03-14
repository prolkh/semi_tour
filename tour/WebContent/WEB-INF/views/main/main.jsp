<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String cp = request.getContextPath();
%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>tour is my love</title>
	
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<style type="text/css">

.main-title{
	color: #ff792a;
	line-height: 1.2em;
	font-size: 1.438rem;
	padding-top: 25px;
	padding-bottom: 10px;
}

.inner {
	clear: both;	
}

.inner-row {
   clear: both;
   margin-bottom: 10px;
}



div.imgTopic {
	position:relative;
	width: 400px; height: 250px;
	font-family:'Malgun Gothic', "맑은 고딕",sansserif;
	border: 1px solid #eee;
	margin-left:15px;
	float: left;
}

div.imgTopic img{
	width: 400px; height: 250px;
}

.inner-row > div:first-child {
   margin-left:0;
}

div .imgTopic a {
	text-decoration: none
}
h1.title {
	position:absolute;
	z-index:1;
	left:15px;
	bottom:10px;
	display:block;
}
h1.title a:hover, h1.title a:focus {
	text-decoration:underline;
}
h1.title a{
	font-size:18px;
	font-weight:bold;
	color:#fff;
}


span.info {
	position:absolute;
	left:0;
	width:400px;
	bottom:0px;
	height:50px;
	padding:0px 0px 0px;
	background: rgb(0, 0, 0, .5);
	color: white;
}



</style>
</head>
<body>

<div class="container">

	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>

	<div class="body-container">
	    <h2 class="main-title">한눈에 모아보기</h2>
	    
		<div class="inner">
			<div class="inner-row">
				<div class="imgTopic" style="margin-bottom: 30px;">
					<h1 class="title"><a href="#">관광지</a></h1>
					<p class="content"><a href="<%=cp%>/site/list.do"><img src="/tour/resource/img/site.jpg" alt="관광지" />
						<span class="info">&nbsp;</span>
					</a></p>
				</div>
				
				<div class="imgTopic">
					<h1 class="title"><a href="#">레포츠</a></h1>
					<p class="content"><a href="<%=cp%>/leisure/list.do"><img src="/tour/resource/img/leisure.jpg" alt="레포츠" />
						<span class="info">&nbsp;</span>
					</a></p>
				</div>
				
				<div class="imgTopic">
					<h1 class="title"><a href="#">축제</a></h1>
					<p class="content"><a href="<%=cp%>/fest/list.do"><img src="/tour/resource/img/fest2.jpg" alt="축제" />
						<span class="info">&nbsp;</span>
					</a></p>
				</div>
	
			</div>
			<div class="inner-row" style="clear:both;">
				<div class="imgTopic">
					<h1 class="title"><a href="#">공지사항</a></h1>
					<p class="content"><a href="<%=cp%>/notice/list.do"><img src="/tour/resource/img/notice.jpg" alt="공지사항" />
						<span class="info">&nbsp;</span>
					</a></p>
				</div>
				
				<div class="imgTopic">
					<h1 class="title"><a href="#">질문과 답변</a></h1>
					<p class="content"><a href="<%=cp%>/qna/list.do"><img src="/tour/resource/img/QnA.jpg" alt="질문과답변" />
						<span class="info">&nbsp;</span>
					</a></p>
				</div>
				
				<div class="imgTopic">
					<h1 class="title"><a href="#">돋보기</a></h1>
					<p class="content"><a href="#"><img src="/tour/resource/img/more2.png" alt="더보기" />
						<span class="info">&nbsp;</span>
					</a></p>
				</div>
			</div>       
		</div>  
	</div>


	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</div>

</body>
</html>