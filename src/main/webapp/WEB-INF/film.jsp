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

	<h1>Film Details</h1>
	<p> You can change any details you want here </p>
	
	<!-- TODO: more of this for other fields -->
	<form action="changeFilm.do" method="get">
		<span>Title: </span>
		<input type="text" name="filmTitle" value="${filmId.title }">
		
		<br>
		<br>
		<button>Send updates</button>
	</form>
	
	<h2>${filmId}</h2>
	
	
</body>
</html>