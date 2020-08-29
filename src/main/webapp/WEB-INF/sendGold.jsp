<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Send Gold</title>
<link rel="stylesheet" type="text/css" href="../css/send.css">
</head>
<body>
	<div class="wrapper">
		<a href="/treasurechest">return</a> 
		<div class="towhom">
			<form action="/sendgold" method="POST">
		    <input type="number" min="1" max="${user.goldCoins}" name="amount" value="1">
		    <div class="sendto">
		    <select id="receiver" name="receiver">
		        <c:forEach items="${userList}" var="receiver">
		        	<c:choose>
		            <c:when test="${receiver != user}">
						<option value="${receiver.id}"><c:out value="${receiver.name}"/></option>
			        </c:when>
			        </c:choose>
				</c:forEach>
		    </select>
		    </div>
		    <button type="submit">Send</button>
			</form>
		</div>
		<p>*<c:out value="${user.name}"/> Current Amount Gold Coins: <c:out value="${user.goldCoins}"/></p>
	</div>
</body>
</html>