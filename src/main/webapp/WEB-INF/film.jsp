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
	<span>Title: </span>
	${filmId.title}
	<br>
	<span>Description: </span>
	${filmId.description}
	<br>
	<span>Release Year: </span>
	${filmId.releaseYear}
	<br>
	<span>Rental Duration: </span>
	${filmId.rentalDuration}
	<br>
	<span>Rental Rate: </span>
	${filmId.rentalRate}
	<br>
	<span>Film Length: </span>
	${filmId.length}
	<br>
	<span>Replacement Cost: </span>
	${filmId.replacementCost}
	<br>
	<br>
	<a href="index.html">Back to Home</a>
	
	
	
	
	
	
</body>
</html>