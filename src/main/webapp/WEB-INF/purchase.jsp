<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Purchase Order</title>
<link rel="stylesheet" type="text/css" href="../css/purchase.css">
</head>
<body>
	<div class="wrapper">
		<a href="/treasurechest">return</a>
		<div class="gold">$<c:out value="${goldValue}"/>.00</div>
		<div class="coin">$<c:out value="${goldCoinValue}"/>.00</div>
		<div class="purchase">
			<form action="/purchase" method="POST">
		    <label for="amount"></label>
		    <input type="number" min="1" name="amount" value="1">
		    <br>
		    <button type="submit">Purchase</button>
		</form>
		</div>
	</div>
</body>
</html>