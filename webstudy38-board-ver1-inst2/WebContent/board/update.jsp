<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
 <form action="${pageContext.request.contextPath}/DispatcherServlet" method="post" >
 <input type="hidden" name="command" value="updatePosting"></input>	
 <input type="hidden" name="no" value="${bvo.no}"></input>	
   <table class="table" >
    <tr>
    <td>제목 &nbsp;&nbsp;
     <input type="text" name="title" value="${bvo.title}" required="required">
    </td>
    </tr>   
    <tr>
     <td>     
     <textarea cols="90" rows="15" name="content" required="required" >${bvo.content }</textarea>
     </td>
    </tr> 
     </table>    
     <div class="btnArea">
     <button type="submit" class="btn" >수정</button>  
     <button type="reset" class="btn" >취소</button>   
    </div>  
  </form>














