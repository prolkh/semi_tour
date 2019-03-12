<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
// 기본적으로 인코딩디지 않는것 : 영문자, 숫자
var s="seoul?자바&123+4";
var out;
out=escape(s);	//+등의 일부 특수문자는 인코딩 안됨
console.log(out);	//seoul%3F%uC790%uBC14%26123+4

out=encodeURI(s);	// ?, &, +등의 일부 특수문자는 인코딩 안됨
console.log(out);	//seoul?%EC%9E%90%EB%B0%94&123+4

out=encodeURIComponent(s);	// 모두 인코딩
console.log(out);	//seoul%3F%EC%9E%90%EB%B0%94%26123%2B4
</script>

</head>
<body>

</body>
</html>