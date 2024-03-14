<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
	<body>
		<table>
		<tbody>
			<tr><td><a href="/main.lims"><img class="logo" src="<c:url value='/assets/image/404_error.png' />"></a></td></tr>
<%-- 			<tr><td style="width:100px;">에러아이디</td><td>${error.errorlogId}</td></tr> --%>
<%-- 			<tr><td>타입</td><td>${error.exceptionname}</td></tr> --%>
<%-- 			<tr><td>메시지</td><td>${error.message}</td></tr> --%>
<%-- 			<tr><td>StackTrace</td><td>${error.stackTrace}</td></tr> --%>
		</tbody>
		</table>
		
	</body>
</html>
