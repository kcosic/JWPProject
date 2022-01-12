<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 11/01/2022
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Payment Error</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/paymentError.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/paymentError.js" type="text/javascript"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-header">
                    <h2>ERROR</h2>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-12">There was an error with your payment option. Please try again.</div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="btn-group" role="group">
                        <button type="submit" form="products" class="btn btn-primary">Go back</button>
                    </div>
                    <form action="products" method="get" id="products"></form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
