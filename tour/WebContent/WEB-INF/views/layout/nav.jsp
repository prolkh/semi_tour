<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String cp=request.getContextPath();
%>

<nav id="gnb">
	<div class="menu-top">
		<a href="javascript:history.back();" class="btn-prev">뒤로</a>
		<a href="javascript:location.href='<%=cp%>/main.do'" class="btn-home">홈</a>
		<div id="btn-menu" class="btn-menu">메뉴</div>
		<ul class="box-menu">
			<li><a href="<%=cp%>/site/list.do">관광지</a></li>
			<li><a href="<%=cp%>/leisure/list.do">레포츠</a></li>
			<li><a href="<%=cp%>/fest/list.do">축제일정</a></li>
			<li><a href="<%=cp%>/notice/list.do">공지</a></li>
			<li><a href="<%=cp%>/qna/list.do">질문게시판</a></li>
		</ul>
		<button type="button" id="btn-my" class="btn-my">MyPage</button>
		<div class="box-my">
			<ul>
				<c:if test="${empty sessionScope.member}">
					<li><a href="<%=cp%>/member/login.do">로그인</a></li>
	                <li><a href="<%=cp%>/member/member.do">회원가입</a></li>
                </c:if>
                <c:if test="${not empty sessionScope.member }">
					<li style="border-bottom:1px solid white; text-align:center;"><a> ${sessionScope.member.userName}</a></li>
                    <li><a href="<%=cp%>/member/logout.do">로그아웃</a></li>
                    <li><a href="<%=cp%>/member/pwd.do?mode=update">정보수정</a><li>
                </c:if>				
			</ul>
		</div>
	</div>
</nav>

<script>
$(function () {
	// 메뉴 보이기
	$("#btn-menu").hover(function(){
		$(this).parent().find(".box-my").slideUp("fast");
		$(this).parent().find(".box-menu").slideDown("fast").show();
		$(this).parent().hover(function(){}, function(){
			$(this).parent().find(".box-menu").slideUp("fast");
		});
	});
	
	// 마이페이지 보이기
	$("#btn-my").hover(function(){
		$(this).parent().find(".box-menu").slideUp("fast");
		$(this).parent().find(".box-my").slideDown("fast").show();
		$(this).parent().hover(function(){}, function(){
			$(this).parent().find(".box-my").slideUp("fast");
		});
	});	
	
	$(".btn-home").hover(function(){
		$(this).parent().children(".box-menu").hide();
		$(this).parent().children(".box-my").hide();
	});
	
	$(".btn-prev").hover(function(){
		$(this).parent().children(".box-menu").hide();
		$(this).parent().children(".box-my").hide();
	});
});



</script>