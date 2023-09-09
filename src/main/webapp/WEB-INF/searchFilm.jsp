<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Details</title>
</head>
<body>

	<h1> Search Results </h1>
	
	
	
		<ul>
		<c:forEach var="f" items="${searchResults }">
			
			<li>${f}</li>
			<%-- <li><a href="presidentInfo?termNumber=${p.termNumber}">${p.firstName} ${p.lastName}</a> </li>
			 --%>
		</c:forEach>
	</ul>
</body>
</html>