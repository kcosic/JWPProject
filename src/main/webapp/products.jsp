<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %><%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 25/12/2021
  Time: 08:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="items" value="<%= request.getAttribute(AttributeEnum.ITEMS.toString())%>"/>
<c:set var="selectedCategory" value="<%= request.getAttribute(AttributeEnum.SELECTED_CATEGORY.toString())%>"/>
<c:set var="search" value="<%= request.getAttribute(AttributeEnum.SEARCH.toString())%>"/>
<c:set var="categories" value="<%= request.getAttribute(AttributeEnum.CATEGORIES.toString())%>"/>
<c:set var="categoryId" value="<%= request.getAttribute(AttributeEnum.CATEGORY_ID.toString())%>"/>
<html>
<head>
    <title>Products</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/products.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/products.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/custom/header.js" type="text/javascript"></script>

</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid my-4">
    <div class="row mt-3">
        <div class="col-xs-12 col-sm-6">
            <h1 class="category-name">${selectedCategory != null ? selectedCategory : "All products"}</h1>
        </div>
        <div class="col-xs-12 col-sm-6 d-flex">
            <form name="searchForm" action="products" method="get" novalidate class="w-100">

                <div class="input-group d-flex justify-content-end">
                    <div class="form-floating">
                        <select class="form-select" id="categoryFilter" name="categoryId">
                            <option value="">All</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}" ${category.id.equals(categoryId) ? "selected" : ""}>${category.name}</option>
                            </c:forEach>
                        </select>
                        <label for="categoryFilter">Filter category</label>
                    </div>
                    <div class="form-floating">
                        <input name="search" type="text" class="form-control" value="${search}"
                               id="search">
                        <label for="search">Product search</label>
                    </div>
                    <button onclick="toggleOverlay(true)" type="submit" id="searchSubmit" class="btn btn-outline-primary">
                        <span class="material-icons">search</span>
                    </button>
                    <button onclick="toggleOverlay(true)" form="clearFilter" type="submit" id="btnClearFilter" class="btn btn-outline-danger">
                        <span class="material-icons">clear</span>
                    </button>
                </div>
            </form>
            <form id="clearFilter" action="products" method="get" novalidate>
                <input hidden required readonly value="" name="categoryId"/>
                <input hidden required readonly value="" name="search"/>
            </form>
        </div>
    </div>

    <div class="products-wrapper mt-3">
        <c:choose>
            <c:when test="${items != null}">
                <c:forEach var="item" items="${items}">
                    <div class="product-wrapper mx-3 my-3 ">
                        <div class="card">
                            <div class="card-header fixed-height">
                                <h5>${item.manufacturer} - ${item.name}</h5>
                            </div>
                            <div class="card-image text-center">
                                <img class="item-preview" src="${contextPath}/assets/images/${item.image}"
                                     alt="No image"/>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">Price: ${item.price}kn</div>
                                    <div class="col">
                                        <form action="products" method="post" novalidate>
                                            <input id="itemId" name="itemId" hidden value="${item.id}" readonly/>
                                            <div class=" d-grid gap-2">
                                                <button class="btn btn-sm btn-secondary" onclick="addToCart(${item.id})"
                                                        type="button">
                                                    <span class="material-icons">add_shopping_cart</span>
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="row">
                                    <form action="product" method="get">
                                        <div class="col d-grid gap-2">
                                            <input name="id" hidden value="${item.id}" readonly/>
                                            <button onclick="toggleOverlay(true)" class="btn btn-primary" type="submit">
                                                Visit
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-items">There are no products to show</div>
            </c:otherwise>
        </c:choose>
    </div>

</div>
</body>
</html>
