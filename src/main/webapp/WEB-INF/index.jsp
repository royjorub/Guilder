
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ page isErrorPage="true" %>   
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to Guilder</title>
    <link rel="stylesheet" type="text/css" href="../css/index.css">
</head>
<body>
    <div class="wrapper">
    	<div class="forms">
	    <div class="formbox">
	    	<fieldset>
	    	<legend>Register</legend>
		    <form:form method="POST" action="/" modelAttribute="user">
		        <p>
		            <form:label path="name">Name:</form:label>
		            <form:input type="name" path="name"/>
		        </p>
		        <p>
		            <form:label path="email">Email:</form:label>
		            <form:input type="email" path="email"/>
		        </p>
		        <p>
		            <form:label path="password">Password:</form:label>
		            <form:password path="password"/>
		        </p>
		        <p>
		            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
		            <form:password path="passwordConfirmation"/>
		        </p>
		        <input type="submit" value="Register"/>
		    </form:form>    
		    </fieldset>
		</div>
		<br>
	    <div class="formbox">
		    <fieldset>
		    <legend>Login</legend>
	    	<form method="post" action="/login">
		        <p>
		            <label for="email">Email</label>
		            <input type="text" id="email" name="email"/>
		        </p>
		        <p>
		            <label for="password">Password</label>
		            <input type="password" id="password" name="password"/>
		        </p>
		        <input type="submit" value="Login"/>
	    </form>    
	    </fieldset>
	    </div>
	    </div>
    	<p><form:errors path="user.*"/> ${error}</p>
    </div>
</body>
</html>