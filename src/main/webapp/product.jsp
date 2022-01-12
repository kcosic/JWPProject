<%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 28/12/2021
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="item" scope="request" type="com.kcosic.jwp.shared.model.entities.ItemEntity"/>
<html>
<head>
    <title>${item.manufacturer} - ${item.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/product.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/product.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/custom/header.js" type="text/javascript" ></script>

</head>
<body>
<jsp:include page="header.jsp"/>


<div class="container my-4">
    <div class="card w-100">
        <div class="card-header">
            <div class="row">
                <jsp:useBean id="category" scope="request" type="com.kcosic.jwp.shared.model.entities.CategoryEntity"/>
                <div class="col-12">${category.name}</div>
                <div class="col-12">${item.manufacturer} - ${item.name}</div>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-xs-12 col-md-6">
                    <div class="row">
                        <div class="col-12">
                            <img class="item-image" src="${contextPath}/assets/images/${item.image}" alt="no image"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-6">
                    <div class="p-4 h-100 d-flex flex-column justify-content-between">
                        <div class="description-wrapper">
                            <p class="item-description">${item.description}</p>
                        </div>
                        <div class="price-wrapper">
                            <h4 class="price">Price: ${item.price}kn</h4>
                            <div class="add-to-cart">
                                <form action="products" method="post" novalidate>
                                    <input id="itemId" name="itemId" hidden value="${item.id}" readonly/>
                                    <div class=" d-grid gap-2">
                                        <button class="btn btn-sm btn-secondary" onclick="addToCart(${item.id})" type="button">
                                            <span class="material-icons">add_shopping_cart</span>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
