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
		<input type="text" name="filmTitle" value="${filmId.title}">
		<br>
		<span>Description: </span>
		<input type="text" name="filmTitle" value="${filmId.description}">
		<br>
		<span>Release Year: </span>
		<input type="text" name="filmTitle" value="${filmId.releaseYear}">
		<br>
		<span>Rental Duration: </span>
		<input type="text" name="filmTitle" value="${filmId.rentalDuration}">
		<br>
		<span>Rental Rate: </span>
		<input type="text" name="filmTitle" value="${filmId.rentalRate}">
		<br>
		<span>Film Length: </span>
		<input type="text" name="filmTitle" value="${filmId.length}">
		<br>
		<span>Replacement Cost: </span>
		<input type="text" name="filmTitle" value="${filmId.replacementCost}">
		
		<br>
		<br>
		<button>Send updates</button>
		

	</form>
	
	<br>
	<br>
	<a href="index.html">Back to Home</a>
</body>
</html>