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
.btn{
	border:0;
	outline:0;
	border-radius:8px;
	margin:3px 3px 3px auto;
	float:left;
	background:none;
	
}
.btn:hover, .btn:focus{
	background-color: darkblue;
	border-color: none;
	color: white;
	border-radius:15px;
}
#wrap{
	width:940px;
	height:1480px;
	margin:0px auto;
}
.tag_ul{
	width:240px; 
	float:right; 
	background-color:#f7f7f7;
	border-radius:8px;
	margin:10px auto;
}
</style>
</head>
<body>	
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
    <div id="wrap">
	    <div class="contents" style="float:left;">	    
        	<ul style="weight:650px; margin:70px auto;">
        		<li style="float:right;">
        		<button name="write" onclick="location.href='<%=cp%>/leisure/created.do'">글쓰기</button>
        		</li>
        	</ul>                
        	<div class="inner"style="margin:100px auto 0px auto; text-align:center; box-sizing:border-box;">                    
            	<ul class=""style="width:650px; height:30px; background-color:darkblue; float:left;">
                  	<li class="" style="color:white; float:left; margin:4px;"><strong>총&nbsp;</strong>0000<strong>건</strong></li>                 		
                </ul>                    
                <ul class="">
                    <li class="" style="width:650px; height:136px; margin:22px auto 0px auto; float:left; border-bottom:1px solid darkblue;">
                        <div class="lei_name" style="width:140px; height:95px; float:left;">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="" style="width:420px; float:left; margin:22px auto; text-align:left;">
                            <div class="">
                                <a href="" onclick="">이름</a>
                            </div>
                          	<br>
                          	<p>주소</p>
                           	<p>전화번호</p>
                        </div>
                    </li>
                </ul>                    
                <ul class="">
                    <li class="" style="width:650px; height:136px; margin:22px auto 0px auto; float:left; border-bottom:1px solid darkblue;">
                        <div class="lei_name" style="width:140px; height:95px; float:left;">
                            <a href="" onclick=""><img src="" alt="사진"></a>
                        </div>
                        <div class="" style="width:420px; float:left; margin:22px auto; text-align:left;">
                            <div class="">
                                <a href="" onclick="">이름</a>
                            </div>
                            <br>
                            <p>주소</p>
                            <p>전화번호</p>
                        </div>
                    </li>                        
                </ul>
            </div>
        </div>
        <div class="area_tag" style="padding-right:30px; padding-top:100px;">
            <div class="tag">
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
	</div>
    <div class="footer">
    	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>"
    </div>
</body>
</html>