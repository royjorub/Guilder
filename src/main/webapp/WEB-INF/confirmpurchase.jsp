<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Confirm Purchase</title>
<link rel="stylesheet" type="text/css" href="../css/purchaseconfirm.css">
</head>
<body>
	<div class="wrapper">
		<a href="/purchase">back</a>
		<div class="summary">confirming the purchase of <c:out value="${goldPurchaseAmount}"/> gold coins, at the rate of 
		<br>
		$<c:out value="${goldCoinValue}"/>.00 
		<br>
		<br>
		for a total of 
		<br>
		$<c:out value="${goldPurchasePrice}"/>.00</div>
		<div class="pay"><a href="/processpurchase">pay now with credit card or paypal</a></div>
	</div>
</body>
</html>