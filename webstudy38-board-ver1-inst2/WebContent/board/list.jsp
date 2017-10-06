<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class="table table-bordered  table-hover boardlist">
		<thead>
		<tr class="success">
			<th>번호</th>
			<th class="title">제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회</th>
			</tr>
		</thead>
		<tbody>						
				<c:forEach var="bvo" items="${requestScope.vo.list}">				
			<tr>
			    <td>${bvo.no }</td>				
				<td>
				<c:choose>
				<c:when test="${sessionScope.mvo!=null}">
				<a href="${pageContext.request.contextPath}/DispatcherServlet?command=showContent&no=${bvo.no }">
				${bvo.title }</a>
				</c:when>
				<c:otherwise>
				${bvo.title }
				</c:otherwise>
				</c:choose>
				</td>
				<td>${bvo.memberVO.name }</td>
				<td>${bvo.timePosted }</td>
				<td>${bvo.hits }</td>
			</tr>	
			</c:forEach>	
			
		</tbody>		
	</table>
	
	<c:set var="pb" value="${requestScope.vo.pagingBean }"/>
	<div align="center"><ul class="pagination">
	<c:if test="${pb.previousPageGroup==true }">
	<li class="page-item"><a href="${pageContext.request.contextPath}/DispatcherServlet
	?command=list&pageNum=${pb.startPageOfPageGroup-1 }">Previous </a></li>
	</c:if>
		<c:forEach var="page" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
			<c:choose>
			<c:when test="${pb.nowPage==page}">
			<li class="page-item"><a href="#">${page}</a></li>
			</c:when>
			<c:otherwise>
			<li class="page-item"><a href="${pageContext.request.contextPath}/DispatcherServlet?command=list&pageNum=${page }">${page}</a></li>
			</c:otherwise>
			</c:choose>
			</c:forEach>				
	<c:if test="${pb.nextPageGroup==true }">
	<li class="page-item">	<a href="${pageContext.request.contextPath}/DispatcherServlet
	?command=list&pageNum=${pb.endPageOfPageGroup+1 }"> Next </a></li>
	</c:if>
		</ul></div>


 