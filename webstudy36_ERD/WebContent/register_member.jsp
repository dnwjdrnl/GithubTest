<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>register_member</title>
</head>
<body>
<a href="index.jsp">홈</a><hr>
<form action="DispatcherServlet">
<input type="hidden" name="command" value="register">
아이디<input type="text" name="id" required="required"><br>
패스워드<input type="password" name="password" required="required"><br>
이름<input type="text" name="name" required="required"><br>
취미<br>
<c:forEach items="${requestScope.hobbyList}" var="hobby">
<input type="checkbox" name="hobby" value="${hobby.hid }" >${hobby.name }&nbsp;&nbsp;<br>
</c:forEach>

<input type="submit" value="등록">
</form>
</body>
</html>