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
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
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

.list > li .area_txt{
	min-height: 94px;
	padding-left: 160px;
	padding-right: 20px;
}

dl, ul, ol, menu, li {
	list-style: none;
}
</style>

<script type="text/javascript">
$(function() {
	var btn = "area_tag .btn";
	$("area_tag .btn").click(function() {
		if($("area_tag .btn").hasClass("active")){
			$("area_tag .btn").removeClass("active");
		} 
		if($("area_tag .btn").is(".active")==false){
			$("area_tag .btn").addClass("active");
		}
	});	
})


</script>

</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	
	
    <div id="wrap">
    	<div class="header">
    	
    	</div>
	    <div class="contents">
        	<div class="inner">                    
            	<strong>총&nbsp;</strong>0000<strong>건</strong>
            </div>
            <div class="total_check">                
                <ul class="list">
                    <li>
                        <div class="photo">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="area_txt">
                            <div class="title">
                                <a href="" onclick="">이름</a>
                            </div>
                          	<p>주소</p>
                           	<p>전화번호</p>
                        </div>
                    </li>
                </ul>
                
                <ul class="list">
                    <li>
                        <div class="photo">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="area_txt">
                            <div class="title">
                                <a href="" onclick="">이름</a>
                            </div>
                          	<p>주소</p>
                           	<p>전화번호</p>
                        </div>
                    </li>
                </ul>
                
                <ul class="list">
                    <li>
                        <div class="photo">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="area_txt">
                            <div class="title">
                                <a href="" onclick="">이름</a>
                            </div>
                          	<p>주소</p>
                           	<p>전화번호</p>
                        </div>
                    </li>
                </ul>
                <ul class="list">
                    <li>
                        <div class="photo">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="area_txt">
                            <div class="title">
                                <a href="" onclick="">이름</a>
                            </div>
                          	<p>주소</p>
                           	<p>전화번호</p>
                        </div>
                    </li>
                </ul>
                <ul class="list">
                    <li>
                        <div class="photo">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="area_txt">
                            <div class="title">
                                <a href="" onclick="">이름</a>
                            </div>
                          	<p>주소</p>
                           	<p>전화번호</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="area_tag">
            	<ul class="tag_ul" id="monthlist">
                    <li id="All"><button type="button" class="btn" onclick=""><span>#전체</span></button></li>
            		<c:forEach var="i" begin="1" end="12" step="1">
            			<li id="${i<=9?'0':''}${i}"><button type="button" class="btn" onclick=""><span>#${i}월</span></button></li>
            		</c:forEach>
            	</ul>
            
            
             	<ul class="tag_ul" id="">		
                    <li id="All"><button type="button" class="btn" onclick=""><span>#전체</span></button></li>
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