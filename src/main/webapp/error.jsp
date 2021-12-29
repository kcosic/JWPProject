<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 29/12/2021
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>${item.manufacturer} - ${item.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/error.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/error.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/custom/header.js" type="text/javascript" ></script>

</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid">
    <div class="row align-content-center justify-content-center">
        <div class="col-12 ">
            <div class="card">
                <div class="card-header">
                    <h2 class="error-title">Error</h2>
                </div>
                <div class="card-body">
                    <p>Error has happened</p>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
