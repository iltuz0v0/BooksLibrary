<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books Library</title>
    <style><%@include file="/WEB-INF/views/css/main.css"%></style>
</head>
<body>
<div class="login_div">
<form:form action="/registration" modelAttribute="login" method="post">
    <form:label path="login" cssClass="login_div_label">Login</form:label>
    <br/>
    <form:input path="login" cssClass="text_input"/>
    <br/>
    <form:errors path="login" cssClass="wrong"/>
    <br/>
    <form:label path="password" cssClass="login_div_label">Password</form:label>
    <br/>
    <form:password path="password" cssClass="text_input" />
    <br/>
    <form:errors path="password" cssClass="wrong"/>
    <br/>
    <button type="submit" class="accept">Sign up</button>
</form:form>
    <p class="wrong">${REGISTRATION_MESSAGE}</p>
<a href="/" class="back_to_main_menu">Back to main menu</a>
</div>
</body>
</html>
