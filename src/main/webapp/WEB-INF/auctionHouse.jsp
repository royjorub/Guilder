<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../css/auction.css">
<link rel="stylesheet" type="text/css" media="screen" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" />
<title>Auction House</title>
</head>
<body>
	<div class="wrapper">
		<a href="/treasurechest">return</a>
		<p>${error}</p>
		<div class="auctions">
		<form:form action="/auctionhouse" method="post" modelAttribute="auction">
			<input type="hidden" name="maker" value="${user.id}">
			<p>I wish to:</p>
				<select id="action" name="action">
				<option value="sell">Sell</option>
		        <option value="buy">Buy</option>
		   		</select>
			<p>
		        <form:label path="coinAmount">Amount of Gold Coins: </form:label>
		        <form:input type="number" min="1" max="${user.goldCoins}" value="1" required="required" path="coinAmount"/>
		    </p> 
			<p>
		        <form:label path="pricePerCoin">At Price Per Coin Of: $</form:label>
		        <form:input type="number" min="1" value="1" required="required" path="pricePerCoin"/>
		    </p> 
		    <input type="submit" value="Create Auction"/>
		</form:form> 
		
		<h2>Guilders Selling Gold</h2>
		<div class="scroller">
		<table class="table table-striped">
		        <thead>
		            <tr>
		                <th>Name Of Seller</th>
		                <th>Amount Of Coins</th>
		                <th>Price Per Coin</th>
		                <th></th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach items="${auctions}" var="auction">
		                <c:choose>
		                <c:when test="${auction.action == sell}">
			                <tr>
			                    <td>
			                    	<c:out value="${auction.maker.name}"/>
			                    </td>
			                    <td>
			                    	<c:out value="${auction.coinAmount}"/>
			                    </td>
			                    <td>
			                    	$<c:out value="${auction.pricePerCoin}"/>
			                    </td>
			                    <td>
				                     <c:choose>
			               			 <c:when test="${auction.maker != user}">
				                    	<a href="/takeauction/${auction.id}">Accept Offer</a>
				                    </c:when>
				        			</c:choose>
			                    </td>
			                </tr>
			        	</c:when>
			        	</c:choose>	
		            </c:forEach>
		        </tbody>
		    </table>
		    </div>
		    
		    
		    <h2>Guilders Buying Gold</h2>
		    <div class="scroller">
		<table class="table table-striped">
		        <thead>
		            <tr>
		                <th>Name Of Buyer</th>
		                <th>Amount Of Coins</th>
		                <th>Price Per Coin</th>
		                <th></th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach items="${auctions}" var="auction">
		                <c:choose>
		                <c:when test="${auction.action == buy}">
			                <tr>
			                    <td>
			                    	<c:out value="${auction.maker.name}"/>
			                    </td>
			                    <td>
			                    	<c:out value="${auction.coinAmount}"/>
			                    </td>
			                    <td>
			                    	$<c:out value="${auction.pricePerCoin}"/>
			                    </td>
			                    <td>
			                    	<c:choose>
			               			 <c:when test="${auction.maker != user}">
				                    	<a href="/takeauction/${auction.id}">Accept Offer</a>
				                    </c:when>
				        			</c:choose>
			                    </td>
			                </tr>
			        	</c:when>
			        	</c:choose>	
		            </c:forEach>
		        </tbody>
		    </table>
		 </div>
	</div>
	</div>
</body>
</html>