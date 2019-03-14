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
<title>관광지 둘러보기</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/nav.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/board/article.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=077360eee8a1e1c82e55602b5c5bcf32"></script>

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
	
	$(function() {
		$("#sendReply").click(function() {
			var content = $("#replyContent").val().trim();
			
			if(! content) {
				$("#replyContent").focus();
				return;
			}
			
			content = encodeURIComponent(content);
			
			var url = "<%=cp%>/site/insertReply.do";
			var query = "num=${dto.num}&content="+content;
			
			$.ajax({
				type:"post"
				,url:url
				,data:query
				,dataType:"json"
				,success:function(data){
					$("#replyContent").val("");
					listPage(1);
				}
			,beforeSend:function(jqXHR){
				jqXHR.setRequestHeader("AJAX", true);
			}
			,error:function(e){ // 403에러
				if(e.status == 403){
					location.href = "<%=cp%>/member/login.do";
					return;
				}
				console.log(e.responseText);
			}
			
			});
			
		});
	});
	
	$(function() {
		listPage(1);
	});
	
	function listPage(page) {
		var url = "<%=cp%>/site/listReply.do";
		var query = "num=${dto.num}&pageNo="+page;
		
		$.ajax({
			type:"get"
			,url:url
			,data:query
			,success:function(data){
				$("#listReply").html(data);
			}
		,beforeSend:function(jqXHR){
			jqXHR.setRequestHeader("AJAX", true);
		}
		,error:function(e){ // 403에러
			if(e.status == 403){
				location.href = "<%=cp%>/member/login.do";
				return;
			}
			console.log(e.responseText);
		}		
		});
	}
	
	$(function(){
		$("body").on("click", ".deleteReply", function(){
			if(! confirm("댓글을 삭제하시겠습니까 ? "))
			    return;
			
			var url="<%=cp%>/site/deleteReply.do";
			var replyNum=$(this).attr("data-replyNum");
			var page=$(this).attr("data-pageNo");
			
			$.ajax({
				type:"post"
				,url:url
				,data:{replyNum:replyNum}
				,dataType:"json"
				,success:function(data) {
					listPage(page);
				}
			    ,beforeSend :function(jqXHR) {
			    	jqXHR.setRequestHeader("AJAX", true);
			    }
			    ,error:function(jqXHR) {
			    	if(jqXHR.status==403) {
			    		location.href = "<%=cp%>/member/login.do";
			    		return;
			    	}
			    	console.log(jqXHR.responseText);
			    }
			});
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
            <h3><span>|</span> 관광지 </h3>
        </div>
        
        <div class="board-article">
        	<div class="row-line" style="text-align: center;">
        		 <h2>${dto.subject}</h2>
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
        			<img src="<%=cp%>/uploads/site/${dto.imageFilename}" style="max-width:100%; height:auto; resize:both;">
        		</div>
        		
        		<div class="right-content">        		
        			<ul class="tabs">
        				<li><a href="#tab-basic">기본정보</a></li>
        				<li><a href="#tab-detail">상세정보</a></li>
        				<li><a href="#tab-map">지도</a></li>
        			</ul>
        			
        			<div class="tab-container">
        				<div id="tab-basic" class="tab-content">
        				   <h4>기본정보</h4><br>
        				       주소 :
        				   ${dto.address}<br>
        				     우편번호 :
        				   ${dto.zip}
        				</div>
        				
        				<div id="tab-detail" class="tab-content">
        				   <h4>상세정보</h4><br>
        				   ${dto.content}<br>
        				   ${dto.inquiry}
        				
        				</div>
        				
        				<div id="tab-map" class="tab-content">
        				    <h4>지도</h4>
        				    <script type="text/javascript">    				    
	        				    var mapContainer = document.getElementById('tab-map'), // 지도를 표시할 div 
	        				    mapOption = { 
	        				        center: new daum.maps.LatLng("${dto.latitude}", "${dto.longitude}"), // 지도의 중심좌표
	        				        level: 3 // 지도의 확대 레벨
	        				    };
	
	        					var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	        					
	        					// 마커가 표시될 위치입니다 
	        					var markerPosition  = new daum.maps.LatLng("${dto.latitude}", "${dto.longitude}"); 
	        					
	        					// 마커를 생성합니다
	        					var marker = new daum.maps.Marker({
	        					    position: markerPosition
	        					});
	        					
	        					// 마커가 지도 위에 표시되도록 설정합니다
	        					marker.setMap(map);
        				    </script>
        				</div>
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div style="padding: 10px 0px 0px 5px; ">        	
        		<h3>개요</h3>
        	</div>
        	<div class="row-content">
				${dto.introduction}
        	</div>
        	
        	
            
            <div>
	            <table style='width: 100%; margin: 15px auto 0px; border-spacing: 0px;'>
	            <tr height='30'> 
		            <td align='left'>
		            	<span style='font-weight: bold;' >댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span>
		            </td>
	            </tr>
	            <tr>
	               <td style='padding:5px 5px 0px;'>
	                    <textarea id='replyContent' class='boxTA' style='width:99%; height: 70px;'></textarea>
	                </td>
	            </tr>
	            <tr>
	               <td align='right'>
	                    <button type='button' class='btn' style='padding:10px 20px;' id="sendReply">댓글 등록</button>
	                </td>
	            </tr>
	            </table>
	            
	            <div id="listReply"></div>
 	       </div>
        	
        	
			<div class="board-footer">
				<div class="left-footer">
					<c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn" onclick="updateSite('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userRoll > 3}">				    
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

</body>
</html>