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

	
	<h1>Form for Editing Film</h1>

	
	<form action="changeFilm.do" method="get">
		<span>Title: </span>
		<input type="text" name="filmTitle" value="${filmObj.title}">
		<br>
		 
		<span>Description: </span>
		<input type="text" name="filmDesc" value="${filmObj.description}">
		<br>
		
		<span>Release Year: </span>
		<input type="text" name="filmYear" value="${filmObj.releaseYear}">
		<br>
		
		<span>Film Length: </span>
		<input type="text" name="filmLength" value="${filmObj.length}">
		<br>
		
		<span>Replacement Cost: </span>
		<input type="text" name="filmReplacementCost" value="${filmObj.replacementCost}">
		
		<!-- we need to save the the object info to make the edit -->
		<input name="filmId" value="${filmObj.id}" hidden="">
		
		<br>
		<br>
		<button>Send updates</button>
		

	</form>
	
	<br>
	<br>
	<a href="index.html">Back to Home</a>
</body>
</html>