<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="totalPrice" scope="session" class="java.lang.String"/>
<jsp:useBean id="cartItems" scope="session" class="java.lang.Integer"/>
<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 26/12/2021
  Time: 07:54
  To change this template use File | Settings | File Templates.
--%>
<style>
    .nav-url {
        text-decoration: none;
        color: #FDFDF9FF;
    }

    .header-price {
        color: #FDFDF9FF;
    }

    .overlay {
        visibility: hidden;
        position: fixed;
        width: 100%;
        height: 100%;
        z-index: 1000;
        opacity: 0.5;
        background-color: lightslategray;
        display: flex;
        justify-content: center;
        align-items: center;
    }

</style>
<div id="overlay" class="overlay">
    <div class="spinner-border text-warning" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">JWP</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navContent">
            <ul class="navbar-nav me-auto align-items-end">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="products">Home</a>
                </li>
            </ul>
            <div class="d-flex flex-row-reverse align-items-center m-sm-3  m-md-2" href="cart">
                <form class="m-0" action="cart" method="get" novalidate>
                    <button type="submit" class="btn btn-dark d-flex justify-content-center align-content-center position-relative nav-url" href="cart">
                        <span id="price" class="header-price">${totalPrice != null ? totalPrice : "-" }kn</span>

                        <span class="material-icons">shopping_cart</span>
                        <c:if test="${cartItems != null && cartItems > 0}">
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                            ${cartItems}
                            <span class="visually-hidden">unread messages</span>
                        </span>
                        </c:if>
                    </button>
                </form>
            </div>
            <jsp:useBean id="customerData" class="com.kcosic.jwp.shared.model.entities.CustomerEntity" scope="session"/>
            <div class="btn-group d-flex justify-content-end  m-sm-3 m-md-2">
                <button class="btn btn-sm btn-dark dropdown-toggle" type="button" id="settingsDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                    <span class="material-icons">
                        account_circle
                    </span>
                </button>
                <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end" aria-labelledby="settingsDropdown">
                    <c:choose>
                        <c:when test="${customerData != null && customerData.role.id != 3}">
                            <li>
                                <button form="account" type="submit" class="dropdown-item d-flex justify-content-between align-content-center">
                                    Settings
                                    <span class="material-icons">
                                    settings
                                </span>
                                </button>
                                <form class="d-none" id="account" action="account" method="get" novalidate></form>
                            </li>
                            <li>
                                <button form="logout" type="submit" class="dropdown-item d-flex justify-content-between align-content-center">
                                    Logout
                                    <span class="material-icons">
                                    logout
                                </span>
                                </button>
                                <form class="d-none" id="logout" action="products" method="post" novalidate>
                                    <input hidden value="true" name="logout">
                                </form>
                            </li>

                        </c:when>
                        <c:otherwise>
                            <li>

                                <button type="submit" form="login" class="dropdown-item d-flex justify-content-between align-content-center">
                                    Login&nbsp;
                                    <span class="material-icons">
                                    login
                                </span>
                                </button>
                                <form class="d-none" id="login" action="login" method="get" novalidate></form>
                            </li>
                            <li>

                                <button type="submit" form="register" class="dropdown-item d-flex justify-content-between align-content-center">
                                    Register&nbsp;
                                    <span class="material-icons">
                                    how_to_reg
                                </span>
                                </button>
                                <form class="d-none" id="register" action="register" method="get" novalidate></form>
                            </li>

                        </c:otherwise>
                    </c:choose>
                </ul>

            </div>

        </div>
    </div>
</nav>