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
<title>tour</title>

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

.inner-row > span {
   display: inline-block;
   padding:15px 10px;
   margin-left:15px;
   border:1px solid black;
   width: 400px; height: 250px;
   box-sizing:border-box;
}
.inner-row > span:first-child {
   margin-left:0;
}

.inner-row > span > img {
   display: inline-block;
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
				<span><a href="<%=cp%>/site/list.do"></a></span>
				<span></span> 
				<span></span> 
			</div>
			<div class="inner-row">
				<span></span> 
				<span></span> 
				<span></span> 
			</div>       
		</div>  
	</div>


	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</div>

</body>
</html>