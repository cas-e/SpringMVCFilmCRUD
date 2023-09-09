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

	
	<h1>Successfully Added Film</h1>

	<%-- <h2>Title: ${film.title}</h2>
	<h2>Id: ${film.id}</h2>
	 --%>
	<form action="changeFilm.do" method="get">
		<span>Title: </span>
		<input type="text" name="filmTitle" value="${filmId.title}">
		<span>Description: </span>
		<input type="text" name="filmTitle" value="${filmId.description}">
		<span>Release Year: </span>
		<input type="text" name="filmTitle" value="${filmId.releaseYear}">
		<span>Rental Duration: </span>
		<input type="text" name="filmTitle" value="${filmId.rentalDuration}">
		<span>Rental Rate: </span>
		<input type="text" name="filmTitle" value="${filmId.rentalRate}">
		<span>Film Length: </span>
		<input type="text" name="filmTitle" value="${filmId.length}">
		<span>Replacement Cost: </span>
		<input type="text" name="filmTitle" value="${filmId.replacementCost}">
		
		<br>
		<br>
		<button>Send updates</button>
	</form>
	
	
</body>
</html>