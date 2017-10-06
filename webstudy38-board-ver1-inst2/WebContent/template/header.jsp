<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
    <script type="text/javascript">
	function logout(){
		var f=confirm("로그아웃하시겠습니까?");
		if(f)
			location.href="${pageContext.request.contextPath}/DispatcherServlet?command=logout";
	}
</script>
<c:choose>
<c:when test="${sessionScope.mvo==null}">
<form method="post" action="${pageContext.request.contextPath}/DispatcherServlet">
<input type="hidden" name="command" value="login">
    <input type="text" name="id"   placeholder="아이디" size="12"> 
   <input type="password" name="password"  placeholder="비밀번호" size="12">
    <input type="submit" value="로그인">
</form>
</c:when>
<c:otherwise>
<a href="${pageContext.request.contextPath}/index.jsp">홈</a>&nbsp;&nbsp;
<a href="${pageContext.request.contextPath}/DispatcherServlet?command=writeView">글쓰기</a>&nbsp;&nbsp; 
 ${sessionScope.mvo.name}님 &nbsp;&nbsp; <a href="javascript:logout()">로그아웃</a>
</c:otherwise>
</c:choose>

