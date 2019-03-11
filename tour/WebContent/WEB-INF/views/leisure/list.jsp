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
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<style>
*{
	margin:0;
	padding:0;
	list-style-type:none;
} 

body{
	font-size: 13px;
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
	margin:100px auto;
}
.contents{
	float: left;
}
.area_tag{
	width:300px;
	float: right;
	background-color:#f7f7f7;
	padding: 35px 0 20px;
	font-size: 14px;
}
.photo{
	
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

.list > li .photo {
	position: absolute;
	width: 140px;
	height: 94px;
}

.list > li .photo img{	
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
</style>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

$(function() {
	var s = "${search}";
	if(s==""){
		s="전체";
	}
	
	$("#arealist.btn").each(function(){
		if($(this).attr("data-search")==s){
			$(this).addClass("active");
			return;
		}
	});
	
	$("#arealist .btn").click(function() {
		$("#arealist .btn").not($(this)).removeClass("active");
		if($(this).hasClass("active")){
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
			
			var search=$(this).attr("data-search");
			var url="<%=cp%>/leisure/list.do";
			if(search !="전체"){
				url+="?search="+encodeURIComponent(search);				
			}
			location.href=url;
		}
	});	
});

</script>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
    <div id="wrap">    	
	    <div class="contents">
        	<div class="inner">                    
            	<strong>총&nbsp;</strong>${dataCount}<strong>건</strong>
            </div>
            <div class="total_check">                
                <ul class="list">
                <c:forEach var="dto" items="${list}">
                    <li>
                        <div class="photo" onclick="javascript:location.href='<%=cp %>/leisure/article.do?num=${dto.num}&page=${page}';">
                        	<a href="${articleUrl}&num=${dto.num}">
                            <img src="<%=cp %>/uploads/leisure/${dto.imageFilename}" alt="${dto.imageFilename}">
                            </a>
                        </div>
                        <div class="area_txt">
                            <div class="title">
                                <a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
                            </div>
                          	<p>${dto.address}</p>
                           	<p>${dto.tel}</p>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="area_tag">
        		<ul class="tag_ul" id="edit" style="margin-bottom:10px; height:32px;">
        		
        			<li>
        				<button style="border:1px solid #57667e;" type="button" class="btn" onclick="location.href='<%=cp%>/leisure/created.do';">글 올리기</button>
        			</li>
        		
        			<li>
        				<button style="border:1px solid #57667e; padding-left:110px;" type="button" class="btn">검색</button>
        			</li>
        		</ul>

             	<ul class="tag_ul" id="arealist" style="height:180px;">		
                    <li class="All"><button type="button" class="btn" onclick=""><span>#전체</span></button></li>
                    <li id="1"><button type="button" class="btn" onclick=""><span>#서울</span></button></li>
                    <li id="6"><button type="button" class="btn" onclick=""><span>#부산</span></button></li>
                    <li id="4"><button type="button" class="btn" onclick="">#대구</button></li>
                    <li id="2"><button type="button" class="btn" onclick=""><span>#인천</span></button></li>
                    <li id="5"><button type="button" class="btn" onclick=""><span>#광주</span></button></li>
                    <li id="3"><button type="button" class="btn" onclick=""><span>#대전</span></button></li>
                    <li id="7"><button type="button" class="btn" onclick="">#울산</button></li>
                    <li id="8"><button type="button" class="btn" onclick=""><span>#세종</span></button></li>
                    <li id="31"><button type="button" class="btn" onclick=""><span>#경기</span></button></li>
                    <li id="32"><button type="button" class="btn" onclick=""><span>#강원</span></button></li>
                    <li id="33"><button type="button" class="btn" onclick=""><span>#충북</span></button></li>
                    <li id="34"><button type="button" class="btn" onclick=""><span>#충남</span></button></li>
                    <li id="35"><button type="button" class="btn" onclick=""><span>#경북</span></button></li>
                    <li id="36"><button type="button" class="btn" onclick=""><span>#경남</span></button></li>
                    <li id="37"><button type="button" class="btn" onclick=""><span>#전북</span></button></li>
                    <li id="38"><button type="button" class="btn" onclick=""><span>#전남</span></button></li>
                    <li id="39"><button type="button" class="btn" onclick=""><span>#제주</span></button></li>                    
                </ul>
        </div>
	</div>
    <div class="footer">
    	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>"
    </div>
</body>
</html>