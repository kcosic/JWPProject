<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 25/12/2021
  Time: 08:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %>

<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Products</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/scripts/popper/popper.min.js" rel="script">
    <link href="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" rel="script">
    <link href="${contextPath}/assets/styles/custom/products.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/products.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid">
    <div class="row mt-3">
        <div class="col-xs-12 col-6">
            <span class="category-name">All products</span>
        </div>
        <div class="col-xs-12 col-6 d-flex justify-content-end">
            <form action="products" method="get" novalidate class="no-margin">
                <div class="input-group">
                    <div class="form-floating">
                        <input name="search" type="text" class="form-control"
                               id="search">
                        <label for="search">Product search</label>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <span class="material-icons">search</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <% if (request.getAttribute(AttributeEnum.ITEMS.toString()) != null) { %>

        <jsp:useBean id="items" scope="request" type="java.util.List"/>
        <c:forEach var="item" items="${items}">
            <div class="col-xs-12 col-sm-6 col-md-3 col-xl-2">
                <div class="card">
                    <div class="card-header">
                        <h5>${item.manufacturer} - ${item.name}</h5>
                    </div>
                    <div class="card-image">
                        <img class="item-preview" src="${contextPath}/assets/images/${item.image}" alt="No image"/>
                    </div>
                    <div class="card-body">
                        <p class="item-description">${item.description}</p>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col">Price: ${item.price}kn</div>
                            <div class="col">
                                <form action="products" method="post" novalidate>
                                    <input id="itemId" name="itemId" hidden value="${item.id}" readonly/>
                                    <div class=" d-grid gap-2">
                                        <button class="btn btn-sm btn-secondary" type="submit">
                                            <span class="material-icons">add_shopping_cart</span>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <div class="row">
                            <div class="col d-grid gap-2">
                                <a class="btn btn-primary" href="products/${item.id}">Visit</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <% } else { %>
        <div class="no-items">There are no products to show</div>
        <% } %>

    </div>

</div>
</body>
</html>
