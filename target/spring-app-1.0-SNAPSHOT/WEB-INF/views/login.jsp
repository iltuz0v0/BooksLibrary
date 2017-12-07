<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books Library</title>
    <style><%@include file="/WEB-INF/views/css/main.css"%></style>
</head>
<script>
    function Formdata(data){
        if (data.login === null || data.login.value.length === 0
        || data.password === null || data.password.value.length === 0)
        {data.login.value = 'Empty login'; data.password.value = 'Empty password';return true;}}

</script>
<body>
<div class="login_div">
<form action="/j_spring_security_check" method="post">
    <label name="login" class="login_div_label">Login</label><br/>
    <input class="text_input" name="login" />
    <br/>
    <label name="password" class="login_div_label">Password</label>
    <br/>
    <input class="text_input" name="password" type="password"/>
    <c:if test="${not empty param.error}">
        <p class="wrong">Wrong login or password</p>
    </c:if>
    <button type="submit" class="accept" onClick="return Formdata(this.form)">Sign in</button>
</form>
<a href="/registration" class="registration_reference">registration</a>
</div>
</body>
</html>
