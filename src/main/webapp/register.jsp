<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %><%--suppress HtmlUnknownTarget --%>
<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 23/12/2021
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="firstName" value="<%= request.getAttribute(AttributeEnum.FIRST_NAME.toString())%>"/>
<c:set var="lastName" value="<%= request.getAttribute(AttributeEnum.LAST_NAME.toString())%>"/>
<c:set var="email" value="<%= request.getAttribute(AttributeEnum.EMAIL.toString())%>"/>
<c:set var="dateOfBirth" value="<%= request.getAttribute(AttributeEnum.DATE_OF_BIRTH.toString())%>"/>
<c:set var="errorMessage" value="<%= request.getAttribute(AttributeEnum.ERROR_MESSAGE.toString())%>"/>
<html>
<head>
    <title>Register</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/register.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/register.js" type="text/javascript"></script>
</head>
<body>
<div class="container h-100 d-flex justify-content-center align-items-center">
    <div class="card">
        <div class="card-header">
            Register
        </div>
        <div class="card-body">
            <form action="register" class="needs-validation" method="post" novalidate>
                <div class="row">
                    <div class="col-xs-12 col-md-6">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="firstName" type="text" class="form-control" value="${firstName}"
                                       id="firstName" required>
                                <label for="firstName">First name</label>
                                <div class="invalid-feedback">
                                    First name is required
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="lastName" type="text" class="form-control" value="${lastName}"
                                       id="lastName" required>
                                <label for="lastName">Last name</label>
                                <div class="invalid-feedback">
                                    Last name is required
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="dateOfBirth" type="date" class="form-control" value="${dateOfBirth}"
                                       maxlength="8"
                                       id="dateOfBirth" required>
                                <label for="dateOfBirth">Date of birth</label>
                                <div class="invalid-feedback">
                                    Date of birth is required
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="email" type="email" class="form-control" value="${email}"
                                       id="email" required>
                                <label for="email">Email</label>
                                <div class="invalid-feedback">
                                    Email is required
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                       title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                                       class="form-control"
                                       id="password" required>
                                <label for="password">Password</label>
                                <div class="invalid-feedback">
                                    Password is required. Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="repeatPassword" type="password" class="form-control"
                                       id="repeatPassword" required>
                                <label for="repeatPassword">Repeat password</label>
                                <div class="invalid-feedback">
                                    Passwords must match.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="col">
                        <div class="col d-grid gap-2">
                            <a class="btn btn-block btn-primary" href="login">Back</a>
                        </div>
                    </div>
                    <div class="col">
                        <div class="col d-grid gap-2">
                            <button class="btn btn-block btn-success" type="submit">
                                Register
                            </button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        ${errorMessage}
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
