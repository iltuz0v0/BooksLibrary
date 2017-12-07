<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Books Library</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style><%@include file="/WEB-INF/views/css/main.css"%></style>
</head>
<body>
<div class="header">
    <p class="nickname">${NICKNAME}</p>
    <p class="books_library">Books Library</p>
    <a href="main/add" class="book_add">Add Book</a>
    <a href="<c:url value="/j_spring_security_logout"/>" class="logout">Logout</a>
</div>
<div class="book_add_div">
<form:form action="/main/add" enctype="multipart/form-data" modelAttribute="book" method="post">
    <label class="login_div_label">File</label>
    <br/>
    <input name="file" class="file" type="file" size="50"/>
    <br/>
    <form:errors path="file" cssClass="wrong"/>
    <br/>
    <label class="login_div_label">Image</label>
    <br/>
    <input name="image" class="image" type="file"/>
    <br/>
    <form:errors path="image" cssClass="wrong"/>
    <br/>
    <form:label path="bookname" cssClass="login_div_label">Bookname</form:label>
    <br/>
    <form:input path="bookname" cssClass="text_input" />
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
    <form:input path="description" cssClass="description_input"/>
    <br/>
    <form:errors path="description" cssClass="wrong"/>
    <br/>
    <button type="submit" class="accept">Submit</button>
</form:form>
<p class="wrong">${ADDITION_MESSAGE}</p>
<a href="/main?number=1" class="back_to_main_menu">back to main menu</a>
</div>
</body>
</html>
