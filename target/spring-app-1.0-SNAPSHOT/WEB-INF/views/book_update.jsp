<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<div class="book_update_div">
<form:form action="/main/update/${book.id}" modelAttribute="book" method="post">
    <form:label path="bookname" cssClass="login_div_label">Bookname</form:label>
    <br/>
    <form:input path="bookname" cssClass="text_input"/>
    <br/>
    <form:errors path="bookname" cssClass="wrong"/>
    <br/>
    <form:label path="author" cssClass="login_div_label">Author</form:label>
    <br/>
    <form:input path="author" cssClass="text_input"/>
        <br/>
    <form:errors path="author" cssClass="wrong"/>
    <br/>
    <form:label path="description" cssClass="login_div_label">Description</form:label>
    <br/>
    <form:input path="description" cssClass="text_input"/>
    <br/>
    <form:errors path="description" cssClass="wrong"/>
    <br/>
    <button type="submit" class="accept">Submit</button>
    <br/>
</form:form>
<p class="wrong">${UPDATE_MESSAGE}</p>
<a href="/main?number=1" class="back_to_main_menu">back to main menu</a>
</div>
</body>
</html>
