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
<link rel="stylesheet" href="<%=cp%>/resource/css/board/article.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	function deleteSite(num) {
	<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
	    if(confirm("게시물을 삭제 하시겠습니까 ?")) {
	    	 var url="<%=cp%>/site/delete.do?num="+num+"&page=${page}";
	    	 location.href=url;
	    }	
	</c:if>
	
	<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
	    alert("게시물을 삭제할 수  없습니다.");
	</c:if>
	}
	
	function updateSite(num) {
	<c:if test="${sessionScope.member.userId==dto.userId}">
	    var url="<%=cp%>/site/update.do?num="+num+"&page=${page}";
	    location.href=url;
	</c:if>
	
	<c:if test="${sessionScope.member.userId!=dto.userId}">
	   alert("게시물을 수정할 수  없습니다.");
	</c:if>
	}
	
	$(function() {
		$(".tab-content").hide();
		$("ul.tabs li:first").addClass("active").show();
		$(".tab-content:first").show();
		
		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab-content").hide();
			
			var activeTab = $(this).find("a").attr("href");
			$(activeTab).fadeIn();			
			return false;
		});
	});
	
	
</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="board" style="width: 700px;">
        <div class="board-title">
            <h3><span>|</span> 관광지 </h3>
        </div>
        
        <div class="board-article">
        	<div class="row-line" style="text-align: center;">
        		 ${dto.subject}
        	</div>
        	
        	<div class="row-line" style="text-align: center;">
        		<div class="left-line">
        			이름 : ${dto.userName}
        		</div>
        		<div class="right-line">
        		 	${dto.created} |  조회  ${dto.hitCount}
        		</div>
        	</div>
        	
        	<div class="row-content">
        		<div class="left-content">
        			<img src="<%=cp%>/uploads/site/${dto.imageFilename}" style="max-width:40%; height:auto; resize:both;">
        		</div>
        		
        		<div class="right-content">        		
        			<ul class="tabs">
        				<li><a href="#tab-basic">기본정보</a></li>
        				<li><a href="#tab-detail">상세정보</a></li>
        				<li><a href="#tab-map">지도</a></li>
        			</ul>
        			
        			<div class="tab-container">
        				<div id="tab-basic" class="tab-content">        				
        					<h5>기본정보</h5> 
        				</div>
        				
        				<div id="tab-detail" class="tab-content">        				
        					<h5>상세정보</h5>        			
        				</div>
        				
        				<div id="tab-map" class="tab-content">        				
        					<h5>지도</h5>        			
        				</div>
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row-line">
        		<h3>개요</h3>
        		${dto.introduction}
        	</div>
        	
        	<div class="row-line">
        	
        	</div>
        	
			<div class="board-footer">
				<div class="left-footer">
					<c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn" onclick="updateSite('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="deleteSite('${dto.num}');">삭제</button>
			       </c:if>
				</div>
				
				<div class="right-footer">
					<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/site/list.do?page=${page}';">리스트</button>
				</div>
			</div>
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