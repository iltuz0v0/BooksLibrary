<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books Library</title>
    <style><%@include file="/WEB-INF/views/css/main.css"%></style>
</head>
<body>
<div class="header">
    <p class="nickname">${NICKNAME}</p>
    <p class="books_library">Books Library</p>
    <a href="main/add" class="book_add">Add Book</a>
    <a href="<c:url value="/j_spring_security_logout"/>" class="logout">Logout</a>
</div>
<div class="book_delete_div">
<p class="wrong">${DELETE_MESSAGE}</p>
<a href="/main?number=1" class="back_to_main_menu">back to main menu</a>
</div>
</body>
</html>
