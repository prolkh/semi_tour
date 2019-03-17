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
	// 메뉴에 커서를 올리면 하위에 있는 메뉴들이 표시되도록 하는 함수
	$("#btn-menu").hover(function(){
		$(this).parent().find(".box-my").slideUp("fast");
		$(this).parent().find(".box-menu").slideDown("fast").show();
		$(this).parent().hover(function(){}, function(){
			$(this).parent().find(".box-menu").slideUp("fast");
		});
	});
	
	// 마이페이지에 커서를 올리면 아래 메뉴들이 표시되도록 하는 함수
	$("#btn-my").hover(function(){
		$(this).parent().find(".box-menu").slideUp("fast");
		$(this).parent().find(".box-my").slideDown("fast").show();
		$(this).parent().hover(function(){}, function(){
			$(this).parent().find(".box-my").slideUp("fast");
		});
	});	
	
	// 홈에 커서를 올리면 표시되었던 다른 메뉴들에게 숨기기 적용
	$(".btn-home").hover(function(){
		$(this).parent().children(".box-menu").hide();
		$(this).parent().children(".box-my").hide();
	});
	
	// 이전 페이지에 커서를 올리면 다른 메뉴들에게 숨기기 적용
	$(".btn-prev").hover(function(){
		$(this).parent().children(".box-menu").hide();
		$(this).parent().children(".box-my").hide();
	});
});

// 스크롤을 내리면 position:fixed가 적용되어 있는 nav를 숨기는 함수
var didScroll;
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('nav').outerHeight();

// 스크롤이 되었는지 확인하는 함수
$(window).scroll(function(event){
	didScroll = true;
});

// didScroll함수 검사하는 시간을 250(단위 : ms)으로 설정
setInterval(function() {
	if(didScroll){
		hasScrolled();
		didScroll = false;
	}
}, 250);

// 스크롤이 발생된 경우 실행하는 함수 
// 원래는 스크롤이 올라가면 다시 보이게 설정하려 했으나 잘 되지 않아서 주석으로 처리...
function hasScrolled() {
	var st= $(this).scrollTop();
	
/* 	if(Math.abs(lastScrollTop - st) <=delta)
		return;
	 */
	if(st > lastScrollTop ){
		$("nav").css("top", "-70px");
	} else {
		$("nav").css("top", "60px");
	/* 	if(st + $(window).height() < $(document).height()) {
			$("nav").css("top", "60px");
		} */
	}
}

</script>