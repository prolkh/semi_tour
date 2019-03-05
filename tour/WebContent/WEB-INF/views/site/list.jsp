<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
*{
	margin:0 auto;
	padding:0;
	list-style-type:none;
}

.btn, .btn_all_active{
	border:0;
	outline:0;
	border-radius:4px;
	margin:3px 3px 3px auto;
}
.btn:hover, .btn:action, .btn:focus{
	background-color: #848457;
	border-color: #adadad;
	color: #333333;	
}
#wrap{
	width:940px;
	height:1480px;
	
}
</style>
</head>
<body>
	<body>
        <div id="wrap" style="width:940px; height:1480px;">                     
            <div class="contents">
                <div class="inner"style="margin:100px auto 0px auto; text-align:center; box-sizing:border-box;">
                    <div class="" style="width:600px; height:30px; background-color:blue; text-color:white; float:left;">
                    	
                    </div>
                    <ul class="">                    
                        <li class="" style="width:600px; height:136px; margin:22px auto 0px auto; float:left; border-bottom:1px solid #654684;">
                            <div class="lei_name" style="width:140px; height:95px; float:left;">
                                <a href="" onclick=""><img src="" alt="사진"></a>
                            </div>
                            <div class="" style="width:420px; float:left; margin:22px auto; text-align:left;">
                                <div class="">
                                    <a href="" onclick="">이름</a>
                                </div>
                                <p>주소</p>
                                <p>전화번호</p>
                            </div>
                        </li>
                    </ul>
                    <ul class="">                    
                        <li class="" style="width:600px; height:136px; margin:22px auto 0px auto; float:left; border-bottom:1px solid #654684;">
                            <div class="lei_name" style="width:140px; height:95px; float:left;">
                                <a href="" onclick=""><img src="" alt="사진"></a>
                            </div>
                            <div class="" style="width:420px; float:left; margin:22px auto; text-align:left;">
                                <div class="">
                                    <a href="" onclick="">이름</a>
                                </div>
                                <p>주소</p>
                                <p>전화번호</p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="area_tag" style="margin:0 auto;">
                <div class="tag">
                	<ul class="" id="" style="width:240px; float:right;">
                		
                        <li id="All"><button type="button" class="btn_all_active" style="float:left;" onclick=""><span>#전체</span></button></li>
                        <li id="1"><button type="button" class="btn" style="float:left;" onclick=""><span>#서울</span></button></li>
                        <li id="6"><button type="button" class="btn" style="float:left;" onclick=""><span>#부산</span></button></li>
                        <li id="4"><button type="button" class="btn" onclick=""><span>#대구</span></button></li>
                        <li id="2"><button type="button" class="btn" style="float:left;" onclick=""><span>#인천</span></button></li>
                        <li id="5"><button type="button" class="btn" style="float:left;" onclick=""><span>#광주</span></button></li>
                        <li id="3"><button type="button" class="btn" style="float:left;" onclick=""><span>#대전</span></button></li>
                        <li id="7"><button type="button" class="btn" onclick=""><span>#울산</span></button></li>
                        <li id="8"><button type="button" class="btn" style="float:left;" onclick=""><span>#세종</span></button></li>
                        <li id="31"><button type="button" class="btn" style="float:left;" onclick=""><span>#경기</span></button></li>
                        <li id="32"><button type="button" class="btn" style="float:left;" onclick=""><span>#강원</span></button></li>
                        <li id="33"><button type="button" class="btn" onclick=""><span>#충북</span></button></li>
                        <li id="34"><button type="button" class="btn" style="float:left;" onclick=""><span>#충남</span></button></li>
                        <li id="35"><button type="button" class="btn" style="float:left;" onclick=""><span>#경북</span></button></li>
                        <li id="36"><button type="button" class="btn" style="float:left;" onclick=""><span>#경남</span></button></li>
                        <li id="37"><button type="button" class="btn" onclick=""><span>#전북</span></button></li>
                        <li id="38"><button type="button" class="btn" style="float:left;" onclick=""><span>#전남</span></button></li>
                        <li id="39"><button type="button" class="btn" style="float:left;" onclick=""><span>#제주</span></button></li>
                    
                    </ul>
                </div>        
        	</div>
    	</div>
	</body>
</html>