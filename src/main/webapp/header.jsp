<jsp:useBean id="totalPrice" scope="request" class="java.lang.String"/>
<jsp:useBean id="cartItems" scope="request" class="java.lang.Integer"/>
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
    .header-price{
        color:#FDFDF9FF;
    }
</style>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">JWP</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="products">Home</a>
                </li>
                <%--                <li class="nav-item dropdown">--%>
                <%--                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">--%>
                <%--                        Dropdown--%>
                <%--                    </a>--%>
                <%--                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
                <%--                        <li><a class="dropdown-item" href="#">Action</a></li>--%>
                <%--                        <li><a class="dropdown-item" href="#">Another action</a></li>--%>
                <%--                        <li><hr class="dropdown-divider"></li>--%>
                <%--                        <li><a class="dropdown-item" href="#">Something else here</a></li>--%>
                <%--                    </ul>--%>
                <%--                </li>--%>
            </ul>
            <div class="d-flex flex-row-reverse align-content-center mx-3" href="cart">
                <a class="position-relative nav-url" href="cart">
                    <span class="material-icons">shopping_cart</span>
                    <% if(cartItems != null && cartItems > 0) { %>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        ${cartItems}
                        <span class="visually-hidden">unread messages</span>
                    </span>
                    <% } %>
                </a>
                <span id="price" class="header-price">${totalPrice != null ? totalPrice : "-" }kn</span>
            </div>
            <a class="d-flex nav-url mx-3" href="account">
                <span class="material-icons">
                    account_circle
                </span>
            </a>
        </div>
    </div>
</nav>