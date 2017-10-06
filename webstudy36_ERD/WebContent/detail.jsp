<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
</head>
<link rel="stylesheet" type="text/css" href="home.css"> 
<body>
<a href="index.jsp">홈</a><hr>
아이디: ${ requestScope.vo.id }<br>
이름: ${ requestScope.vo.name }
<br><br><br>
<table>
<tbody>
<c:forEach items="${requestScope.vo.hobby}" var="hobby">
<tr>
	<td>${hobby.name }</td>
</tr>
</c:forEach>
</tbody>
</table>

</body>
</html>