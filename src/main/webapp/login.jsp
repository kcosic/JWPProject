<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %><%--suppress HtmlUnknownTarget --%>
<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 17/12/2021
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="customerData" scope="session" value="<%= request.getSession().getAttribute(AttributeEnum.CUSTOMER_DATA.toString())%>"/>
<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/login.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/login.js" type="text/javascript" ></script>
</head>
<body>
<c:if test="${customerData !=  null && customerData.role.id != 3}">
    <c:redirect url="/products"/>
</c:if>

<div class="container h-100 d-flex justify-content-center align-items-center">
    <form action="login" method="post"  class="needs-validation" novalidate>
        <jsp:useBean id="hasError" scope="request" type="java.lang.Boolean"/>
        <c:if test="${hasError}">
            <div class="row mb-4">
                <div class="col text-center red-border">
                    Invalid login data.
                </div>
            </div>
        </c:if>
        <div class="card">
            <div class="card-header">
                <div class="row-cols-1 text-center text-uppercase">
                    Login
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col ">
                        <div class="input-group has-validation mb-3">
                            <span class="input-group-text material-icons" id="inputGroupPrepend">face</span>
                            <div class="form-floating ">
                                <input name="login" type="email" class="form-control" aria-describedby="inputGroupPrepend" id="login" required>
                                <label for="login">Email address</label>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="input-group has-validation mb-3">
                            <span class="input-group-text material-icons">password</span>
                            <div class="form-floating">
                                <input name="password" type="password" class="form-control" id="password" required>
                                <label for="password">Password</label>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="card-footer">
                <div class="row mt-3">
                    <div class="col d-grid gap-2">
                        <button class="btn btn-block btn-success d-flex flex-row justify-content-center" type="submit">
                            <span class="material-icons">login</span>&nbsp;
                            Log In
                        </button>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col d-grid gap-2">
                        <a class="btn btn-block btn-primary d-flex flex-row justify-content-center" href="register">
                            <span class="material-icons">contact_page</span>
                            Register
                        </a>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col d-grid gap-2">
                        <a class="btn btn-warning d-flex flex-row justify-content-center" href="products">
                            <span class="material-icons">arrow_back</span>&nbsp;To Products
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
