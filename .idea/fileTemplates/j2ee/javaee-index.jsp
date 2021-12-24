<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>ServletName</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/scripts/popper/popper.min.js" rel="script">
    <link href="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" rel="script">
    <link href="${contextPath}/assets/styles/custom/servletName.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/servletName.js" type="text/javascript" ></script>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>