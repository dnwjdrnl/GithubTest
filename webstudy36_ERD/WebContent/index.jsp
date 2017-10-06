<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>home</title>
</head>
<link rel="stylesheet" type="text/css" href="home.css"> 
<body>
<%-- index.jsp --DispatcherSErvlet --HandlerMapping--Controller--MemberDAO
							|					RegisterViewController
							|
							register_member.jsp
 --%>
<a href="DispatcherServlet?command=selectHobby">회원가입</a><br>
<form action="DispatcherServlet">
<input type="hidden" name="command" value="findById">
아이디<input type="text" name="id" required="required">
<input type="submit" value="검색">
</form>
</body>
</html>