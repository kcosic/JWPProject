<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 23/12/2021
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
                <div class="row my-3">
                    <div class="col mx-3">
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
                    <div class="col mx-3">
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
                </div>
                <div class="row my-3">
                    <div class="col mx-3">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="dateOfBirth" type="date" class="form-control" value="${dateOfBirth}"
                                       id="dateOfBirth" required>
                                <label for="dateOfBirth">Date of birth</label>
                                <div class="invalid-feedback">
                                    Date of birth is required
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col mx-3">
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
                </div>
                <div class="row my-3">
                    <div class="col mx-3">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                       title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                                       class="form-control" value="Pa$$w0rd"
                                       id="password" required>
                                <label for="password">Password</label>
                                <div class="invalid-feedback">
                                    Password is required
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col mx-3">
                        <div class="input-group has-validation mb-3">
                            <div class="form-floating w-100">
                                <input name="repeatPassword" type="password" class="form-control"
                                       value="Pa$$w0rd"
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
