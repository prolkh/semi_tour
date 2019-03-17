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
<title>festival</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/nav.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function sendOk() {
    var f = document.festForm;

	var str = f.eventName.value;
    if(!str) {
        alert("축제 이름을 입력하세요. ");
        f.eventName.focus();
        return;
    }

	str = f.content.value;
    if(!str) {
        alert("설명을 입력하세요. ");
        f.content.focus();
        return;
    }
    
	str = f.startDate.value;
    if(!str) {
        alert("시작일을 입력하세요. ");
        f.startDate.focus();
        return;
    }
    
	str = f.endDate.value;
    if(!str) {
        alert("종료일을 입력하세요. ");
        f.endDate.focus();
        return;
    }
    
	str = f.tel.value;
	var format = /^(01[016789])-[0-9]{3,4}-[0-9]{4}$/g;
    if(! format.test(str)) {
        alert("전화번호를 다시 입력해주세요.");
        f.tel.focus();
        return;
    }
    
    str = f.homepage.value;
    if(!str) {
        alert("홈페이지 주소를 입력하세요. ");
        f.homepage.focus();
        return;
    }

    
    str = f.host.value;
    if(!str) {
        alert("주최/주관란을 입력하세요. ");
        f.host.focus();
        return;
    }
    
    str = f.address.value;
    if(!str) {
        alert("주소를 입력하세요. ");
        f.address.focus();
        return;
    }
    
	str = f.price.value;
	if(!str) {
		alert("이용요금을 입력하세요. ");
		f.price.focus();
		return;
	}

	var mode="${mode}";	
	if(mode=="created"||mode=="update" && f.upload.value!="") {
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload.value)) {
			alert('이미지 파일을 등록은 필수입니다.');
			f.upload.focus();
			return;
		}
	}
	
	f.action="<%=cp%>/fest/${mode}_ok.do";

	f.submit();
}

$(function() {
	// jQuery ui를 사용한 날짜 선택창 디자인
	$("#startDate").datepicker({
		showMonthAfterYear:true
		,showOn:"button"
		,buttonImage:"<%=cp%>/resource/images/calendar.gif"
		,buttonImageOnly:true
	});
	
	$("#endDate").datepicker({
		showMonthAfterYear:true
		,showOn:"button"
		,buttonImage:"<%=cp%>/resource/images/calendar.gif"
		,buttonImageOnly:true
	});
	
	$(".ui-datepicker-trigger").css({position:"relative", top:"3px", left:"3px"});
});

// 종료날짜가 시작날짜보다 뒤에 있는지 검사하는 함수
$(function(){
	$("form #endDate").change(function(){
		var startDate = $('form #startDate').val();
		var endDate = $(this).val();
		
		var startArray = startDate.split('-');
		var endArray = endDate.split('-');
		
		var start_date = new Date(startArray[0], startArray[1], startArray[2]);
		var end_date = new Date(endArray[0], endArray[1], endArray[2]);
		
		if(start_date.getTime() > end_date.getTime()){
			alert("종료날짜보다 시작날짜가 작아야합니다.");
			$(this).val('');
			return;
		}
	})
});

</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="body-container" style="width: 700px; margin-top:20px;">
        <div class="body-title">
            <h3><span style="font-family: Webdings">2</span> 축제 일정 등록 </h3>
        </div>
        
        <div>
 			<form name="festForm" method="post" enctype="multipart/form-data" autocomplete="off">
			  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">축제 이름</td>
			      <td style="padding-left:10px;" colspan="3"> 
			        <input type="text" name="eventName" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.eventName}">
			      </td>
			  </tr>
			
			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">작성자</td>
			      <td style="padding-left:10px;" colspan="3"> 
			            ${sessionScope.member.userName}
			      </td>
			  </tr>
			  
			
			  <tr align="left" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center; padding-top:5px;" valign="top">설&nbsp;&nbsp;&nbsp;&nbsp;명</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;" colspan="3"> 
			        <textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">시작일</td>
			      <td style="padding-left:10px;"> 
			        <input type="text" name="startDate" id="startDate" maxlength="" class="boxTF" style="width: 45%;" value="${dto.startDate}" readonly="readonly">
			      </td>
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">종료일</td>
			      <td style="padding-left:10px;">
			        <input type="text" name="endDate" id="endDate" maxlength="" class="boxTF" style="width: 45%;" value="${dto.endDate}" readonly="readonly">
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">전화번호</td>
			      <td style="padding-left:10px;" colspan="3"> 
			        <input type="text" name="tel" maxlength="14" class="boxTF" style="width: 55%;" value="${dto.tel}">
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">홈페이지</td>
			      <td style="padding-left:10px;" colspan="3"> 
			        <input type="text" name="homepage" maxlength="40" class="boxTF" style="width: 55%;" value="${dto.homepage}">
			      </td>
			  </tr>
			   
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">주최/주관</td>
			      <td style="padding-left:10px;" colspan="3"> 
			        <input type="text" name="host" maxlength="30" class="boxTF" style="width: 55%;" value="${dto.host}">
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">주소</td>
			      <td style="padding-left:10px;" colspan="3"> 
			        <input type="text" name="address" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.address}">
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">이용요금</td>
			      <td style="padding-left:10px;" colspan="3"> 
			        <input type="text" name="price" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.price}">
			      </td>
			  </tr>
			  
			  
			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">이미지</td>
			      <td style="padding-left:10px;" colspan="3"> 
			           <input type="file" name="upload" accept="image/*" 
			                      class="boxTF" size="53" style="height: 25px;">
			       </td>
			  </tr> 

			  </table>
			
			  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			      
			      <c:if test="${mode=='update'}">
			      	<input type="hidden" name="num" value="${dto.num}">
			      	<input type="hidden" name="page" value="${page}">
			      	<input type="hidden" name="imageFilename" value="${dto.imageFilename}">
			      	<input type="hidden" name="userId" value="${dto.userId}">
			      </c:if>
			      
			      
			        <button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/fest/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
			      </td>
			    </tr>
			  </table>
			</form>
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