<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
    <a href="main/add" class="book_add">Add book</a>
    <a href="<c:url value="/j_spring_security_logout"/>" class="logout">Logout</a>
</div>
<div class="title_book">Books</div>
<br/>
<security:authorize access= "hasRole('ROLE_ADMIN')" var= "isUSer"/>
<c:forEach var="book" items="${books}">
    <div class="book">
        <div class="book_id">${book.id}</div>
    <div class="book_name">${book.name}</div>
    <div class="book_author">${book.author}</div>
    <img src="/main/getimage/${book.length}/${book.id}" alt="Error" class="book_image">
    <div class="book_description">${book.description}</div>
    <div class="book_actions">
    <c:if test="${LOGIN_ID == book.id_user || isUSer}">
        <a href="/main/update/${book.id}" class="book_actions_element">Update</a>
        <a href="/main/delete/${book.id}" class="book_actions_element">Delete</a>
    </c:if>
    <a href="/main/download/${book.id}" class="book_actions_element">Download</a>
    </div>
    </div>
    <br/>
</c:forEach>
<div class="footer_references">
<c:if test="${not empty is_first}">
    <a href="/main?number=${is_first}" class="footer_reference">${is_first}</a>
</c:if>
<c:forEach var="page" items="${pages}">
    <a href="/main?number=${page}" class="footer_reference">${page}</a>
</c:forEach>
</div>
<div class="footer">
</div>
</body>
</html>
