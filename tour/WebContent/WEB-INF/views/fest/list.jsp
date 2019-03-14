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
<title>festival</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/nav.css" type="text/css">
<style>
*{
	margin:0;
	padding:0;
	list-style-type:none;
}

body{
	font:13px "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}
.btn{
	border:0;
	outline:0;
	background:none;
	border-radius: 35px;
	padding: 5px 10px 6px;
	font-weight:500;
	font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
	cursor:pointer;
	text-align:center;
}
.btn:hover{
	background-color: #57667e;
	border-color: #adadad;
	color:white;
}

.active {
	background-color: #57667e;
	border-color: #adadad;
	color:white;
}

#wrap{
	width:940px;
	height:1000px;
	margin: 0px auto;
}
.contents{
	float: left;
	width:600px;
}
.area_tag{
	width:300px;
	float: right;
	background-color:#f7f7f7;
	padding: 35px 0 20px;
	font-size: 14px;
}
.tag_ul{
	margin:0px 30px;
	padding: 12px 0;
	border-top: 1px solid #e6e6e6;
	display: block; 
	clear: both;
}

.tag_ul > li{
	position: relative;
	margin:6px 5px 0px 0;
	float:left;
}

.inner{
	width:600px;
	height:40px;
	background-color:#666;
	color: white;
	padding : 12px 15px 10px;
	text-align:left;
	box-sizing:border-box;
	text-align: left;
}

.list > li{
	padding : 20px 0px;
	border-top: 1px solid #e6e6e6;
	border-bottom: 1px solid #e6e6e6;
}

.list > li .photo{
	position: absolute;
	width: 140px;
	height: 94px;
}



.list > li .area_txt{
	min-height: 94px;
	padding-left: 160px;
	padding-right: 20px;
}

dl, ul, ol, menu, li {
	list-style: none;
}

.list > li .photo img{
	width: 140px;
	height: 94px;
}
.list > li .area_txt a{
	text-decoration:none;
	font-size:20px;
	font-weight:bold;
	color:black;
	width:100%;
	display:inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.list > li .area_txt a:hover, a:active{
	text-decoration:underline;
	color: chocolate;
}

</style>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
/* $(function() {
	$("#monthlist .btn").click(function() {
		if($(this).parent().hasClass("All")){
			if($(this).hasClass("active")){
				$("#monthlist .btn").removeClass("active");
			} else {
				$("#monthlist .btn").addClass("active");
			}
		} else {
			$("#monthlist .btn").find(".All").removeClass("active");
			// $(this).parent().parent().find(".All").children().removeClass("active");
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			} else {
				$(this).addClass("active");
			}
		}
		
		
	});	
}); */
 
$(function() {
	if("${monthcode}"==""){
		$("#monthlist .All .btn").addClass("active");		
	} else {
		$("#monthlist .btn[data-month=${monthcode}]").addClass("active");
	}
	
	$("#monthlist .btn").click(function() {
		$("#monthlist .btn").not($(this)).removeClass("active");
		var month = $(this).attr("data-month");
		var area = "${areacode}";
		var url = "<%=cp%>/fest/list.do";
		if(area !="" && month !="전체")
			url+="?areacode="+encodeURIComponent(area)+"&monthcode="+month;
		else if(month !="전체")
			url+="?monthcode="+month;
		else if(area !="")
			url+="?areacode="+encodeURIComponent(area);
		location.href=url;
	});	
});


$(function() {
	if("${areacode}"==""){
		$("#arealist .All .btn").addClass("active");		
	} else {
		$("#arealist .btn[data-area=${areacode}]").addClass("active");
	}
	
	$("#arealist .btn").click(function() {
		$("#arealist .btn").not($(this)).removeClass("active");
		var area = $(this).attr("data-area");
		var month = "${monthcode}";
		
		var url = "<%=cp%>/fest/list.do";
		if(area !="전체" && month !="")
			url+="?areacode="+encodeURIComponent(area)+"&monthcode="+month;
		else if(month !="")
			url+="?monthcode="+month;
		else if(area !="전체")
			url+="?areacode="+encodeURIComponent(area);
		location.href=url;
	});	
});

var didScroll;
$(window).scroll(function(event){
	didScroll = true;
});

setInterval(function(){
	if(didScroll){
		hasScrolled();
		didScroll = false;
	}
}, 250);

var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('header').outerHeight();
function hasScrolled() {
	var st = $(this).scrollTop();
	
	// Make sure they scroll more than delta
	if(Math.abs(lastScrollTop - st) <= delta)
		return;
	
	if(st > lastScrollTop && st > navbarHeight) {
		$("nav").removeClass("nav-down").addClass("nav-up");
	} else {
		if(st + $(window).height() < $(document).height()){
			$("nav").removeClass("nav-up").addClass("nav-down");
		}
	}
	lastScrollTop = st;
}

</script>

<style>
.nav-up{
	top: -64px;
}
</style>

</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
	</div>
	
    <div id="wrap">
	    <div class="title" style="padding: 15px;">
    		<span style=" font-size: 36px; font-weight:800;">#축제</span>
    	</div>
	    <div class="contents">
	    
        	<div class="inner">                    
            	<strong>총&nbsp;</strong>${dataCount}<strong>건</strong>
            </div>
            <div class="total_check"> 
            
			<ul class="list">
<c:forEach var="dto" items="${list}">               
				<li>
					<div class="photo">
						<a href="${articleUrl}&num=${dto.num}">
							<img src="<%=cp%>/uploads/fest/${dto.imageFilename}" alt="${dto.eventName}">
						</a>
                    </div>
					<div class="area_txt">
						<div class="title">
							<a href="${articleUrl}&num=${dto.num}">${dto.eventName}</a>
						</div>
						<br>
						<p>[ ${dto.startDate}~${dto.endDate} ]</p>
						<br>
						<p>${dto.address}</p>
					</div>
				</li>
</c:forEach>
			</ul>
			
			<table style="width: 100%; border-spacing: 0">
				<tr height="50">
					<td align="center">
						${dataCount==0?"등록된 게시물이 없습니다.":paging}
					</td>
				</tr>
			</table> 
            </div>
        </div>
        <div class="area_tag">
        		<ul class="tag_ul" id="edit" style="margin-bottom:10px; height:32px;">
        			<li>
        				<button style="border:1px solid #57667e;" type="button" class="btn" onclick="location.href='<%=cp%>/fest/created.do';">글 올리기</button>
        			</li>
        			<li>
        				<button style="border:1px solid #57667e; padding-left:110px;" type="button" class="btn">검색</button>
        			</li>
        		</ul>
        
            	<ul class="tag_ul" id="monthlist" style="height:144px;">
                    <li class="All"><button type="button" class="btn" data-month="전체"><span>#전체</span></button></li>
            		<c:forEach var="i" begin="1" end="12" step="1">
            			<li id="${i<=9?'0':''}${i}"><button type="button" class="btn" data-month="${i<=9?'0':''}${i}"><span>#${i}월</span></button></li>
            		</c:forEach>
            	</ul>
            
            
             	<ul class="tag_ul" id="arealist" style="height:180px;">		
                    <li class="All"><button type="button" class="btn" data-area="전체">#전체</button></li>
                    <li id="1"><button type="button" class="btn" data-area="서울">#서울</button></li>
                    <li id="6"><button type="button" class="btn" data-area="부산">#부산</button></li>
                    <li id="4"><button type="button" class="btn" data-area="대구">#대구</button></li>
                    <li id="2"><button type="button" class="btn" data-area="인천">#인천</button></li>
                    <li id="5"><button type="button" class="btn" data-area="광주">#광주</button></li>
                    <li id="3"><button type="button" class="btn" data-area="대전">#대전</button></li>
                    <li id="7"><button type="button" class="btn" data-area="울산">#울산</button></li>
                    <li id="8"><button type="button" class="btn" data-area="세종">#세종</button></li>
                    <li id="31"><button type="button" class="btn" data-area="경기">#경기</button></li>
                    <li id="32"><button type="button" class="btn" data-area="강원">#강원</button></li>
                    <li id="33"><button type="button" class="btn" data-area="충청북도">#충북</button></li>
                    <li id="34"><button type="button" class="btn" data-area="충청남도">#충남</button></li>
                    <li id="35"><button type="button" class="btn" data-area="경상북도">#경북</button></li>
                    <li id="36"><button type="button" class="btn" data-area="경상남도">#경남</button></li>
                    <li id="37"><button type="button" class="btn" data-area="전라북도">#전북</button></li>
                    <li id="38"><button type="button" class="btn" data-area="전라남도">#전남</button></li>
                    <li id="39"><button type="button" class="btn" data-area="제주">#제주</button></li>                    
                </ul>
        </div>
	</div>
    <div class="footer">
    	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>"
    </div>
</body>
</html>