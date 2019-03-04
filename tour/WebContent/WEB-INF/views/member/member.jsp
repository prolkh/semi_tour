<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
        <title>로그인</title>
        <style>
        #wrap.member.inner{overflow:hidden;position:relative;width:100px;margin:0 auto;}
   

        </style>

    </head>
    <body>
        <div id="wrap">
        <div class="header">헤더</div>
            <h><a href="#"></a></h>
        <div>
            <form>
                <span>관광 홈페이지 제목</span>
                <div class="member">
                    <div class="inner">
                        <div>
                            <div style= "margin:0 auto; text-align: center; box-sizing: border-box; font-size: 30px;"><a href="#">어서옵쇼 관광지쇼 </a> </div>
                        <div style= "margin:20px auto 20px auto;text-align: center; box-sizing: border-box;">
                            <h2><label>아이디</label></h2>
                            <span>
                            <input type="text"; maxlength="40"; style="font-size: 25px; padding: 10px 60px 10px 60px;">
                            <br><span>아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야됨</span></br>
                            </span>
                        </div>

                        <div style= "margin:20px auto 20px auto;text-align: center; box-sizing: border-box;">
                            <h3><label>비밀번호</label></h3>
                            <span>
                                <input type="password"; maxlength="40"; style="font-size: 25px; padding: 10px 60px 10px 60px;">
                                <br><span>비밀번호는 10자 이내로~(이건 알아서설정)</span></br>
                            </span>
                        </div>

                        <div style= "margin:20px auto 20px auto;text-align: center; box-sizing: border-box;">
                            <h3><label>비밀번호 확인</label></h3>
                            <span>
                                <input type="password"; maxlength="40"; style="font-size: 25px; padding: 10px 60px 10px 60px;">
                                 <br><span>비밀번호한번더 입력점</span></br>
                            </span>
                        </div>

                        <div style= "margin:20px auto 20px auto;text-align: center; box-sizing: border-box;">
                            <h3><label>전화번호</label></h3>
                            <span>
                            <select type="text"; style="height: 50px; font-size: 20px; padding: 10px 20px 10px 20px;">
                                <option value="010">010</option>
                                <option value="011">011</option>
                                <option value="016">016</option>
                                <option value="017">017</option>
                                <option value="018">018</option>
                                <option value="019">019</option>
                            </select>
                            -
                            <input type="text" name="" value="" class="boxTF" maxlength="4" style="height: 40px" >
                            -
                            <input type="text" name="" value="" class="boxTF" maxlength="4" style="height: 40px">
                            </span>
                        </div>
                        
                        <div style= "margin:20px auto 20px auto;text-align: center; box-sizing: border-box;">
                            <h3><label>이메일</label></h3>
                            <span>
                                <input type="text"; maxlength="40"; style="font-size: 25px; padding: 10px 60px 10px 60px;">
                            </span>
                        </div>
                        <div style= "margin:20px auto 20px auto;text-align: center; box-sizing: border-box;">
                            <span>
                                <button type="text"; maxlength="40"; style="font-size: 25px; padding: 10px 60px 10px 60px;">가입하기</button>
                            </span>
                        </div>
                </div>
            </div>
            </div>
        </form>
        </div>
    </div>
    </body>
</html>