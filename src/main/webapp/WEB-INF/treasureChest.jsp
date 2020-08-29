<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${user.name}"/>'s Treasure Chest</title>
<link rel="stylesheet" type="text/css" href="../css/treasurechest.css">
</head>
<body>
	<div class="wrapper">
		<div class="logout"><a href="/logout">Logout</a></div>
		<div class="gold">
		<div class="goldSpot">Gold Spot Value</div>
		<div class="goldVal">$<c:out value="${goldValue}"/>.00</div>
		</div>
		<div class="purchase">
		<a href="/purchase">Purchase</a>
		<br>
		<div class="order"><a href="/purchase">Order</a></div>
		</div>
		<div class="send"><a href="/sendgold">Send Gold</a></div>
		<div class="welcome"> Welcome,
		<div class="user"><c:out value="${user.name}"/></div>
		</div>
		<div class="bottom">
		<div class="auction"><a href="/auctionhouse">Auction House</a></div>
		<div class="userGold">Coins Owned <c:out value="${user.goldCoins}"/></div>
		</div>
	</div>
</body>
</html>