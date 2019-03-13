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
<title></title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/board/article.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/nav.css" type="text/css">
<style>
.tab-container li{
	display:list-item;
	overflow:hidden;
}

.tab-container strong{
	float:left;
	width:25%;
}

.tab-container span{
	float:left;
	width:75%;
}

</style>


<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=077360eee8a1e1c82e55602b5c5bcf32"></script>

<script type="text/javascript">
function deleteFest(num) {
	<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
		if(confirm("게시물을 삭제 하시겠습니까 ?")) {
			var url="<%=cp%>/fest/delete.do?num="+num+"&page=${page}";
			location.href=url;
		}	
	</c:if>
	
	<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
	    alert("게시물을 삭제할 수  없습니다.");
	</c:if>
}
	
function updateFest(num) {
	<c:if test="${sessionScope.member.userId==dto.userId}">
		var url="<%=cp%>/fest/update.do?num="+num+"&page=${page}";
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
    <jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
</div>
	
<div class="container">
	<div class="board" style="width: 700px;">
		<div class="board-title">
			<h3><span>|</span> 축제 정보 </h3>
		</div>
        
		<div class="board-article">
			<div class="row-line" style="text-align: center;">
        		 <h2>${dto.eventName}</h2>
        	</div>
        	
        	<div class="row-line" style="text-align: center;">
        		<div class="left-line">
        			${dto.address}
        		</div>
        		<div class="right-line">
        		 	${dto.createdDate}  
        		</div>
        	</div>
        	
        	<div class="row-content">
        		<div class="left-content">
        			<img src="<%=cp%>/uploads/fest/${dto.imageFilename}" style="max-width:100%; height:auto; resize:both;">
        		</div>
        		
        		<div class="right-content">        		
        			<ul class="tabs">
        				<li><a href="#tab-basic">기본정보</a></li>
        				<li><a href="#tab-detail">상세정보</a></li>
        			</ul>
        			
        			<div class="tab-container">
        				<div id="tab-basic" class="tab-content">
							<ul>
								<li><strong>시작일</strong><span>${dto.startDate}</span></li>
								<li><strong>종료일</strong><span>${dto.endDate}</span></li>
								<li>&nbsp;</li>
								<li><strong>주소</strong><span>${dto.address}</span></li>
							</ul>
        				</div>
        				
        				<div id="tab-detail" class="tab-content">
							<ul>
								<li><strong>전화번호</strong><span>${dto.tel}</span></li>
								<li><strong>주최</strong><span>${dto.host}</span></li>
								<li>&nbsp;</li>
								<li><strong>이용요금</strong><span>${dto.price}</span></li>
							</ul>
        				</div>
        			</div>
        		</div>
        	</div>
        	     	
       		<div class="row-content" style="padding: 10px 0px 0px 5px;">
				${dto.content}
       		</div>

			<div class="board-footer">
				<div class="left-footer">
					<c:if test="${sessionScope.member.userId==dto.userId}">				    
						<button type="button" class="btn" onclick="updateFest('${dto.num}');">수정</button>
					</c:if>
					<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userRoll > 3}">				    
						<button type="button" class="btn" onclick="deleteFest('${dto.num}');">삭제</button>
					</c:if>
				</div>
				
				<div class="right-footer">
					<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/fest/list.do?page=${page}';">리스트</button>
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