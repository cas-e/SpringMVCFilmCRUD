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

	
	<h1>Form for Adding Film</h1>

	
	<form action="createFilm.do" method="get">
		<span>Title: </span>
		<input type="text" name="filmTitle">
		<br>
		<span>Description: </span>
		<input type="text" name="filmDesc">
		<br>
		
		<span>Release Year: </span>
		<input type="text" name="filmYear">
		<br>
		
		<span>Rental Duration: </span>
		<input type="text" name="rentalDuration" >
		<br>
		
		<span>Film Length: </span>
		<input type="text" name="filmLength">
		<br>
		
		<br>
		<br>
		<button>Send updates</button>
		
	</form>
	
	<br>
	<br>
	<a href="index.html">Back to Home</a>
	
	
</body>
</html>