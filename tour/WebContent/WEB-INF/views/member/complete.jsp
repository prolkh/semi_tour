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
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">

	<div>
	    <div style="margin: 70px auto 60px; width:420px; min-height: 350px;">
		
	    	<div style="text-align: center;">
	        	<span style="font-weight: bold; font-size:27px; color: #424951;">${title}</span>
	        </div>
	        
	        <div class="messageBox">
	            <div style="line-height: 150%; padding-top: 35px;">${message}</div>
	            <div style="margin-top: 20px;">
                      <button type="button" onclick="javascript:location.href='<%=cp%>/';" class="btnConfirm">메인화면으로 이동</button>
                 </div>
	        </div>
	     </div>   
    </div>
    	       
</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>


</body>
</html>