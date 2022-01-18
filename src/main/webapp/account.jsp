<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %><%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 29/12/2021
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="customerData" scope="session"
       value="<%= request.getSession().getAttribute(AttributeEnum.CUSTOMER_DATA.toString())%>"/>
<c:set var="roles" scope="request" value="<%= request.getAttribute(AttributeEnum.ROLES.toString())%>"/>
<c:set var="passedView" scope="request" value="<%= request.getAttribute(AttributeEnum.PASSED_VIEW.toString())%>"/>
<c:set var="passedAdminView" scope="request"
       value="<%= request.getAttribute(AttributeEnum.PASSED_ADMIN_VIEW.toString())%>"/>
<c:set var="allCarts" scope="request" value="<%= request.getAttribute(AttributeEnum.ALL_CARTS.toString())%>"/>
<c:set var="historySearchQuery" scope="request"
       value="<%= request.getAttribute(AttributeEnum.HISTORY_SEARCH_QUERY.toString())%>"/>
<c:set var="items" scope="request" value="<%= request.getAttribute(AttributeEnum.ITEMS.toString())%>"/>
<c:set var="itemSearchQuery" scope="request"
       value="<%= request.getAttribute(AttributeEnum.ITEM_SEARCH_QUERY.toString())%>"/>
<c:set var="categories" scope="request" value="<%= request.getAttribute(AttributeEnum.CATEGORIES.toString())%>"/>
<c:set var="categorySearchQuery" scope="request"
       value="<%= request.getAttribute(AttributeEnum.CATEGORY_SEARCH_QUERY.toString())%>"/>
<c:set var="allCategories" scope="request" value="<%= request.getAttribute(AttributeEnum.ALL_CATEGORIES.toString())%>"/>
<c:set var="customers" scope="request" value="<%= request.getAttribute(AttributeEnum.CUSTOMERS.toString())%>"/>
<c:set var="customerSearchQuery" scope="request"
       value="<%= request.getAttribute(AttributeEnum.CUSTOMER_SEARCH_QUERY.toString())%>"/>
<c:set var="logs" scope="request" value="<%= request.getAttribute(AttributeEnum.LOGS.toString())%>"/>
<c:set var="logSearchQuery" scope="request"
       value="<%= request.getAttribute(AttributeEnum.LOG_SEARCH_QUERY.toString())%>"/>
<c:set var="addresses" scope="request" value="<%= request.getAttribute(AttributeEnum.ADDRESSES.toString())%>"/>
<c:set var="defaultAddress" scope="request"
       value="<%= request.getAttribute(AttributeEnum.DEFAULT_ADDRESS.toString())%>"/>
<c:set var="carts" scope="request" value="<%= request.getAttribute(AttributeEnum.CARTS.toString())%>"/>
<html>
<head>
    <title>My Account</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/account.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        let passedView = '${passedView}';
        let passedAdminView = '${passedAdminView}';
    </script>
    <script src="${contextPath}/assets/scripts/custom/account.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/custom/header.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid mx-xs-0 mx-md-4 my-4">

    <div class="row">
        <%-- NAVIGATION--%>
        <div class="col-xs-12 col-md-2">
            <ul class="nav flex-column">
                <li class="nav-item w-100 d-grid gap-2">
                    <button id="btn-details" class="btn" onclick="display('details')">Details</button>
                </li>
                <li class="nav-item w-100 d-grid gap-2">
                    <button id="btn-address" class="btn" onclick="display('address')">Addresses</button>
                </li>
                <li class="nav-item w-100 d-grid gap-2">
                    <button id="btn-history" class="btn" onclick="display('history')">History</button>
                </li>
                <c:if test="${customerData.role.id == 1}">
                    <li class="nav-item w-100 d-grid gap-2">
                        <button id="btn-admin" class="btn" onclick="display('admin')">Admin</button>
                    </li>
                </c:if>
            </ul>
        </div>
        <%-- CONTENT --%>
        <c:if test="${customerData.role.id == 1}">
            <div id="admin" class="col">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <ul class="nav nav-tabs">
                                <li class="nav-item">
                                    <button id="btn-admin-history" class="nav-link"
                                            onclick="adminDisplay('admin-history')">History
                                    </button>
                                </li>
                                <li class="nav-item">
                                    <button id="btn-admin-items" class="nav-link" onclick="adminDisplay('admin-items')">
                                        Items
                                    </button>
                                </li>
                                <li class="nav-item">
                                    <button id="btn-admin-categories" class="nav-link"
                                            onclick="adminDisplay('admin-categories')">Categories
                                    </button>
                                </li>
                                <li class="nav-item">
                                    <button id="btn-admin-customers" class="nav-link"
                                            onclick="adminDisplay('admin-customers')">Customers
                                    </button>
                                </li>
                                <li class="nav-item">
                                    <button id="btn-admin-logs" class="nav-link"
                                            onclick="adminDisplay('admin-logs')">Logs
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12" id="admin-history">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <h2>History</h2>
                                    </div>
                                    <c:if test="${allCarts.data != null}">
                                        <div class="col-xs-12 col-md-6">
                                            <form action="account" class="search-wrapper"
                                                  method="get">
                                                <input name="historySearchQuery" value="${historySearchQuery}"
                                                       class="form-control w-50"/>
                                                <input name="type" value="admin-history" readonly required hidden/>
                                                <button type="submit" class="btn btn-sm btn-primary">
                                                    <span class="material-icons">search</span>
                                                </button>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row my-3">
                                    <div class="col-12 table-responsive">
                                        <c:choose>
                                            <c:when test="${allCarts.data == null}">
                                                <span>There are no transactions to show.</span>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="table table-sm table-striped">
                                                    <thead>
                                                    <tr>
                                                        <th>User Email</th>
                                                        <th>Date of purchase</th>
                                                        <th>Total price</th>
                                                        <th>Details</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="cart" items="${allCarts.data}" varStatus="loop">
                                                        <tr>
                                                            <td>${cart.customer.email}</td>
                                                            <td>${cart.dateOfPurchase}</td>
                                                            <td>${cart.totalPriceString}</td>
                                                            <td>
                                                                <button type="button" class="btn btn-sm btn-primary"
                                                                        data-bs-toggle="modal"
                                                                        data-bs-target="#adminHistoryModal${loop.index}">
                                                                    <span class="material-icons">preview</span>&nbsp;View
                                                                </button>
                                                                <div class="modal fade"
                                                                     id="adminHistoryModal${loop.index}"
                                                                     data-bs-backdrop="static"
                                                                     data-bs-keyboard="false" tabindex="-1"
                                                                     aria-labelledby="staticBackdropLabel"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title">Purchase
                                                                                    details</h5>
                                                                                <button type="button" class="btn-close"
                                                                                        data-bs-dismiss="modal"
                                                                                        aria-label="Close"></button>
                                                                            </div>
                                                                            <div class="modal-body table-responsive">
                                                                                <table class="table table-striped">
                                                                                    <thead>
                                                                                    <tr>
                                                                                        <th>Item</th>
                                                                                        <th>Quantity</th>
                                                                                        <th>Price per item</th>
                                                                                        <th>Total price</th>
                                                                                    </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                    <c:forEach var="cartItem"
                                                                                               items="${cart.cartItems}">
                                                                                        <tr>
                                                                                            <td>${cartItem.item.name}</td>
                                                                                            <td>${cartItem.count}</td>
                                                                                            <td>${cartItem.price}kn</td>
                                                                                            <td>${cartItem.price * cartItem.count}kn</td>
                                                                                        </tr>
                                                                                    </c:forEach>
                                                                                    </tbody>
                                                                                    <tfoot>
                                                                                    Total cost: ${cart.totalPriceString}
                                                                                    </tfoot>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                    <caption>
                                                        <nav aria-label="Pagination">
                                                            <ul class="pagination">
                                                                <li class="page-item ${!allCarts.canGoPrev ? 'disabled':''}">
                                                                    <a class="page-link"
                                                                       href="account?type=admin-history&historyPagination=${allCarts.currentPage - 1}${historySearchQuery != null ? "&action=search&historySearchQuery=".concat(historySearchQuery) : ""}">Previous</a>
                                                                </li>

                                                                <c:forEach begin="1" end="${allCarts.pages}" var="i">
                                                                    <li class="page-item ${allCarts.currentPage == i ? 'active': ""}">
                                                                        <a class="page-link"
                                                                           href="account?type=admin-history&historyPagination=${i}${historySearchQuery != null ? "&action=search&historySearchQuery=".concat(historySearchQuery) : ""}">${i}</a>
                                                                    </li>
                                                                </c:forEach>
                                                                <li class="page-item ${!allCarts.canGoNext ? 'disabled':''}">
                                                                    <a class="page-link"
                                                                       href="account?type=admin-history&historyPagination=${allCarts.currentPage + 1}${historySearchQuery != null ? "&action=search&historySearchQuery=".concat(historySearchQuery) : ""}">Next</a>
                                                                </li>
                                                            </ul>
                                                        </nav>
                                                    </caption>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="col-12" id="admin-items">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <h2>Items</h2>
                                    </div>
                                    <c:if test="${items.data != null}">
                                        <div class="col-xs-12 col-md-6">
                                            <form action="account" class="search-wrapper"
                                                  method="get">
                                                <input name="type" value="admin-items" required hidden readonly/>
                                                <input name="action" value="search" required hidden readonly/>
                                                <input name="itemSearchQuery" value="${itemSearchQuery}"
                                                       class="form-control w-50"/>
                                                <button type="submit" class="btn btn-sm btn-primary">
                                                    <span class="material-icons">search</span>
                                                </button>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row my-3">
                                    <div class="col-xs-12 col-md-6">
                                        <button type="button" class="btn btn-primary" data-bs-target="#newItemModal"
                                                data-bs-toggle="modal">Add
                                            new item
                                        </button>
                                        <div class="modal fade" id="newItemModal"
                                             data-bs-backdrop="static"
                                             data-bs-keyboard="false" tabindex="-1"
                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Edit Item</h5>
                                                        <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal"
                                                                aria-label="Close"></button>
                                                    </div>
                                                    <form id="addItem" method="post" enctype="multipart/form-data"
                                                          action="account?type=admin-items">
                                                        <input name="action" value="data" required hidden readonly/>

                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="itemName"
                                                                                   type="text"
                                                                                   class="form-control"
                                                                                   id="cItemName"
                                                                                   required>
                                                                            <label for="cItemName">Item
                                                                                name</label>
                                                                            <div class="invalid-feedback">
                                                                                Item name is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="manufacturer"
                                                                                   type="text"
                                                                                   class="form-control"
                                                                                   id="cManufacturer"
                                                                            >
                                                                            <label for="cManufacturer">Manufacturer</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                                                <textarea
                                                                                                        name="description"
                                                                                                        rows="8"
                                                                                                        maxlength="3999"
                                                                                                        class="form-control"
                                                                                                        id="cDescription"></textarea>
                                                                            <label for="cDescription">Description</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="itemPrice"
                                                                                   type="text"
                                                                                   class="form-control"
                                                                                   pattern="^[+-]?([0-9]+\.?[0-9]*|\.[0-2]+)$"
                                                                                   id="cPrice"
                                                                                   required>
                                                                            <label for="cPrice">Price</label>
                                                                            <div class="invalid-feedback">
                                                                                Price is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="mb-3">
                                                                            <label for="cImage">Image</label>
                                                                            <input name="image"
                                                                                   type="file"
                                                                                   accept=".jpg,.jpeg,.png"
                                                                                   class="form-control"
                                                                                   id="cImage" required
                                                                            />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">

                                                                            <select class="form-select"
                                                                                    name="category"
                                                                                    required
                                                                                    id="cCategories">
                                                                                <option>Select category</option>
                                                                                <c:forEach
                                                                                        var="category"
                                                                                        items="${allCategories}">
                                                                                    <option value="${category.id}">${category.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                            <label for="cCategories">Category</label>
                                                                            <div class="invalid-feedback">
                                                                                Category is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12 my-3">
                                                                    <div class="form-check">
                                                                        <input class="" name="active"
                                                                               type="checkbox"
                                                                               value="true"
                                                                               id="cActive"/>
                                                                        <label class="form-check-label"
                                                                               for="cActive">
                                                                            Active
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                    <div class="modal-footer">
                                                        <button type="button"
                                                                class="btn btn-outline-danger"
                                                                data-bs-dismiss="modal">Close
                                                        </button>
                                                        <button type="submit"
                                                                form="addItem"
                                                                class="btn btn-success">Save
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12 table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Manufacturer</th>
                                                <th>Price</th>
                                                <th>Category</th>
                                                <th>Active</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="item" items="${items.data}" varStatus="loop">
                                                <tr>
                                                    <td>${item.name}</td>
                                                    <td>${item.manufacturer}</td>
                                                    <td>${item.price}kn</td>
                                                    <td>${item.category.name}</td>
                                                    <td>
                                                        <span class="material-icons ${item.isActive ? "green" : "red"}">${item.isActive ? "check" : "remove"}</span>
                                                    </td>
                                                    <td>
                                                        <button class="btn btn-warning" type="button"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#editItemModal${loop.index}">
                                                            <span class="material-icons">edit</span>
                                                        </button>
                                                        <div class="modal fade" id="editItemModal${loop.index}"
                                                             data-bs-backdrop="static"
                                                             data-bs-keyboard="false" tabindex="-1"
                                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">Edit Item</h5>
                                                                        <button type="button" class="btn-close"
                                                                                data-bs-dismiss="modal"
                                                                                aria-label="Close"></button>
                                                                    </div>
                                                                    <form id="editItem${loop.index}" method="post"
                                                                          enctype="multipart/form-data"
                                                                          action="account?type=admin-items">
                                                                        <input name="action" value="data" required
                                                                               hidden readonly/>

                                                                        <input id="id${loop.index}" name="id"
                                                                               value="${item.id}" hidden readonly
                                                                               required/>
                                                                        <div class="modal-body">
                                                                            <div class="row">
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">
                                                                                            <input name="itemName"
                                                                                                   type="text"
                                                                                                   class="form-control"
                                                                                                   value="${item.name}"
                                                                                                   id="eItemName${loop.index}"
                                                                                                   required>
                                                                                            <label for="eItemName${loop.index}">Item
                                                                                                name</label>
                                                                                            <div class="invalid-feedback">
                                                                                                Item name is required
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">
                                                                                            <input name="manufacturer"
                                                                                                   type="text"
                                                                                                   class="form-control"
                                                                                                   value="${item.manufacturer}"
                                                                                                   id="eManufacturer${loop.index}"
                                                                                            >
                                                                                            <label for="eManufacturer${loop.index}">Manufacturer</label>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">
                                                                                                <textarea
                                                                                                        name="description"
                                                                                                        rows="8"
                                                                                                        maxlength="3999"
                                                                                                        class="form-control"
                                                                                                        id="eDescription${loop.index}">${item.description}</textarea>
                                                                                            <label for="eDescription${loop.index}">Description</label>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">
                                                                                            <input name="itemPrice"
                                                                                                   type="text"
                                                                                                   class="form-control"
                                                                                                   pattern="^[+-]?([0-9]+\.?[0-9]*|\.[0-2]+)$"
                                                                                                   value="${item.price}"
                                                                                                   id="ePrice${loop.index}"
                                                                                                   required>
                                                                                            <label for="ePrice${loop.index}">Price</label>
                                                                                            <div class="invalid-feedback">
                                                                                                Price is required
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="mb-3">
                                                                                            <label for="eImage${loop.index}">Image</label>
                                                                                            <input name="image"
                                                                                                   type="file"
                                                                                                   accept=".jpg,.jpeg,.png"
                                                                                                   class="form-control"
                                                                                                   id="eImage${loop.index}"
                                                                                            >
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">

                                                                                            <select class="form-select"
                                                                                                    name="category"
                                                                                                    required
                                                                                                    id="eCategories${loop.index}">
                                                                                                <c:forEach
                                                                                                        var="category"
                                                                                                        items="${allCategories}">
                                                                                                    <option value="${category.id}" ${category.id == item.category.id ? "selected" : ""}>${category.name}</option>
                                                                                                </c:forEach>
                                                                                            </select>
                                                                                            <label for="eCategories${loop.index}">Category</label>
                                                                                            <div class="invalid-feedback">
                                                                                                Category is required
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 my-3">
                                                                                    <div class="form-check">
                                                                                        <input class="" name="active"
                                                                                               type="checkbox"
                                                                                               value="true"
                                                                                               checked="${item.isActive}"
                                                                                               id="eActive${loop.index}"
                                                                                        >
                                                                                        <label class="form-check-label"
                                                                                               for="eActive${loop.index}">
                                                                                            Active
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-danger"
                                                                                data-bs-dismiss="modal">Close
                                                                        </button>
                                                                        <button type="submit"
                                                                                form="editItem${loop.index}"
                                                                                class="btn btn-success">Save
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                            <caption>
                                                <nav aria-label="Pagination">
                                                    <ul class="pagination">
                                                        <li class="page-item ${!items.canGoPrev ? 'disabled':''}"><a
                                                                class="page-link"
                                                                href="account?type=admin-items&itemPagination=${items.currentPage - 1}${itemSearchQuery != "" ? "&action=search&itemSearchQuery=".concat(itemSearchQuery) : ""}">Previous</a>
                                                        </li>
                                                        <c:forEach begin="1" end="${items.pages}" var="i">
                                                            <li class="page-item ${items.currentPage == i ? 'active': ""}">
                                                                <a class="page-link"
                                                                   href="account?type=admin-items&itemPagination=${i}${itemSearchQuery != "" ? "&action=search&itemSearchQuery=".concat(itemSearchQuery) : ""}">${i}</a>
                                                            </li>
                                                        </c:forEach>
                                                        <li class="page-item ${!items.canGoNext ? 'disabled':''}"><a
                                                                class="page-link"
                                                                href="account?type=admin-items&itemPagination=${items.currentPage + 1}${itemSearchQuery != "" ? "&action=search&itemSearchQuery=".concat(itemSearchQuery) : ""}">Next</a>
                                                        </li>
                                                    </ul>
                                                </nav>
                                            </caption>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12" id="admin-categories">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <h2>Categories</h2>
                                    </div>
                                    <c:if test="${categories.data != null}">
                                        <div class="col-xs-12 col-md-6">
                                            <form action="account" class="search-wrapper"
                                                  method="get">
                                                <input name="categorySearchQuery" value="${categorySearchQuery}"
                                                       class="form-control w-50"/>
                                                <input name="action" value="search" required hidden readonly/>
                                                <input name="type" value="admin-categories" required hidden readonly/>
                                                <button type="submit" class="btn btn-sm btn-primary">
                                                    <span class="material-icons">search</span>
                                                </button>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row  my-3">
                                    <div class="col-xs-12 col-md-6">
                                        <button type="button" class="btn btn-primary" data-bs-target="#newCategoryModal"
                                                data-bs-toggle="modal">Add
                                            new category
                                        </button>
                                        <div class="modal fade" id="newCategoryModal"
                                             data-bs-backdrop="static"
                                             data-bs-keyboard="false" tabindex="-1"
                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Add Category</h5>
                                                        <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal"
                                                                aria-label="Close"></button>
                                                    </div>
                                                    <form id="addCategory" method="post"
                                                          action="account?type=admin-categories">
                                                        <input name="action" value="data" required hidden readonly/>

                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-12 my-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="categoryName"
                                                                                   type="text"
                                                                                   class="form-control"
                                                                                   id="cCategoryName"
                                                                                   required>
                                                                            <label for="cCategoryName">Category
                                                                                name</label>
                                                                            <div class="invalid-feedback">
                                                                                Name is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                    <div class="modal-footer">
                                                        <button type="button"
                                                                class="btn btn-outline-danger"
                                                                data-bs-dismiss="modal">Close
                                                        </button>
                                                        <button type="submit"
                                                                form="addCategory"
                                                                class="btn btn-success">Save
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12 table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="category" items="${categories.data}" varStatus="loop">
                                                <tr>
                                                    <td>${category.name}</td>
                                                    <td>
                                                        <button class="btn btn-warning" type="button"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#editCategoryModal${loop.index}">
                                                            <span class="material-icons">edit</span>
                                                        </button>
                                                        <div class="modal fade" id="editCategoryModal${loop.index}"
                                                             data-bs-backdrop="static"
                                                             data-bs-keyboard="false" tabindex="-1"
                                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">Edit Category</h5>
                                                                        <button type="button" class="btn-close"
                                                                                data-bs-dismiss="modal"
                                                                                aria-label="Close"></button>
                                                                    </div>
                                                                    <form id="editCategory${loop.index}" method="post"
                                                                          action="account?type=admin-categories">
                                                                        <input name="action" value="data" required
                                                                               hidden readonly/>

                                                                        <input id="id${loop.index}" name="id"
                                                                               value="${category.id}" hidden readonly
                                                                               required/>
                                                                        <div class="modal-body">
                                                                            <div class="row">
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">
                                                                                            <input name="categoryName"
                                                                                                   type="text"
                                                                                                   class="form-control"
                                                                                                   value="${category.name}"
                                                                                                   id="eCategoryName${loop.index}"
                                                                                                   required>
                                                                                            <label for="eCategoryName${loop.index}">Category
                                                                                                name</label>
                                                                                            <div class="invalid-feedback">
                                                                                                Name is required
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-danger"
                                                                                data-bs-dismiss="modal">Close
                                                                        </button>
                                                                        <button type="submit"
                                                                                form="editCategory${loop.index}"
                                                                                class="btn btn-success">Save
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>

                                            <caption>
                                                <nav aria-label="Pagination">
                                                    <ul class="pagination">
                                                        <li class="page-item ${!categories.canGoPrev ? 'disabled':''}">
                                                            <a
                                                                    class="page-link"
                                                                    href="account?type=admin-categories&categoryPagination=${categories.currentPage - 1}${categorySearchQuery != null ? "&action=search&categorySearchQuery=".concat(categorySearchQuery) : ""}">Previous</a>
                                                        </li>
                                                        <c:forEach begin="1" end="${categories.pages}" var="i">
                                                            <li class="page-item ${categories.currentPage == i ? 'active': ""}">
                                                                <a class="page-link"
                                                                   href="account?type=admin-categories&categoryPagination=${i}${categorySearchQuery != null ? "&action=search&categorySearchQuery=".concat(categorySearchQuery) : ""}">${i}</a>
                                                            </li>
                                                        </c:forEach>
                                                        <li class="page-item ${!categories.canGoNext ? 'disabled':''}">
                                                            <a
                                                                    class="page-link"
                                                                    href="account?type=admin-categories&categoryPagination=${categories.currentPage + 1}${categorySearchQuery != null ? "&action=search&categorySearchQuery=".concat(categorySearchQuery) : ""}">Next</a>
                                                        </li>
                                                    </ul>
                                                </nav>
                                            </caption>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12" id="admin-customers">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <h2>Customers</h2>
                                    </div>
                                    <c:if test="${customers.data != null}">
                                        <div class="col-xs-12 col-md-6">
                                            <form action="account" class="search-wrapper"
                                                  method="get">
                                                <input name="type" value="admin-customers" required hidden readonly/>
                                                <input name="action" value="search" required hidden readonly/>
                                                <input name="customerSearchQuery" value="${customerSearchQuery}"
                                                       class="form-control w-50"/>
                                                <button type="submit" class="btn btn-sm btn-primary">
                                                    <span class="material-icons">search</span>
                                                </button>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row  my-3">
                                    <div class="col-12 table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th>First name</th>
                                                <th>Last name</th>
                                                <th>Email</th>
                                                <th>Role</th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="customer" items="${customers.data}" varStatus="loop">
                                                <tr>
                                                    <td>${customer.firstName}</td>
                                                    <td>${customer.lastName}</td>
                                                    <td>${customer.email}</td>
                                                    <td>${customer.role.name}</td>
                                                    <td>
                                                        <button class="btn btn-warning" type="button"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#editCustomerModal${loop.index}">
                                                            <span class="material-icons">edit</span>
                                                        </button>
                                                        <div class="modal fade" id="editCustomerModal${loop.index}"
                                                             data-bs-backdrop="static"
                                                             data-bs-keyboard="false" tabindex="-1"
                                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">Change customer
                                                                            role</h5>
                                                                        <button type="button" class="btn-close"
                                                                                data-bs-dismiss="modal"
                                                                                aria-label="Close"></button>
                                                                    </div>
                                                                    <form id="editCustomer${loop.index}" method="post"
                                                                          action="account?type=admin-customers">
                                                                        <input name="action" value="data" required
                                                                               hidden readonly/>

                                                                        <input id="id${loop.index}" name="id"
                                                                               value="${customer.id}" hidden readonly
                                                                               required/>
                                                                        <div class="modal-body">
                                                                            <div class="row">
                                                                                <div class="col-12 my-3">
                                                                                    <div class="input-group">
                                                                                        <div class="form-floating w-100">
                                                                                            <select class="form-select"
                                                                                                    name="role"
                                                                                                    required
                                                                                                    id="eRoles">
                                                                                                <c:forEach
                                                                                                        var="role"
                                                                                                        items="${roles}">
                                                                                                    <option value="${role.id}">${role.name}</option>
                                                                                                </c:forEach>
                                                                                            </select>
                                                                                            <label for="eRoles">Roles</label>
                                                                                            <div class="invalid-feedback">
                                                                                                Role is required
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-danger"
                                                                                data-bs-dismiss="modal">Close
                                                                        </button>
                                                                        <button type="submit"
                                                                                form="editCustomer${loop.index}"
                                                                                class="btn btn-success">Save
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                            <caption>
                                                <nav aria-label="Pagination">
                                                    <ul class="pagination">
                                                        <li class="page-item ${!customers.canGoPrev ? 'disabled':''}"><a
                                                                class="page-link"
                                                                href="account?action=admin-customers&customerPagination=${customers.currentPage - 1}${customerSearchQuery != null ? "&action=search&customerSearchQuery=".concat(customerSearchQuery) : ""}">Previous</a>
                                                        </li>
                                                        <c:forEach begin="1" end="${customers.pages}" var="i">
                                                            <li class="page-item ${customers.currentPage == i ? 'active': ""}">
                                                                <a class="page-link"
                                                                   href="account?action=admin-customers&customerPagination=${i}${customerSearchQuery != null ? "&action=search&customerSearchQuery=".concat(customerSearchQuery) : ""}">${i}</a>
                                                            </li>
                                                        </c:forEach>
                                                        <li class="page-item ${!customers.canGoNext ? 'disabled':''}"><a
                                                                class="page-link"
                                                                href="account?action=admin-customers&customerPagination=${customers.currentPage + 1}${customerSearchQuery != null ? "&action=search&customerSearchQuery=".concat(customerSearchQuery): ""}">Next</a>
                                                        </li>
                                                    </ul>
                                                </nav>
                                            </caption>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12" id="admin-logs">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <h2>Logs</h2>
                                    </div>
                                    <c:if test="${logs.data == null}">
                                        <div class="col-xs-12 col-md-6">
                                            <form action="account" class="search-wrapper" method="get">
                                                <input name="logSearch" value="${logSearchQuery}"
                                                       class="form-control w-50"/>
                                                <input name="action" value="search" required hidden readonly/>
                                                <input name="type" value="admin-logs" required hidden readonly/>
                                                <button type="submit" class="btn btn-sm btn-primary">
                                                    <span class="material-icons">search</span>
                                                </button>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row  my-3">
                                    <div class="col-xs-12 col-md-6  table-responsive">
                                        <c:choose>
                                            <c:when test="${logs.data == null}">
                                                <span>There are no logs to show.</span>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="table table-striped">
                                                    <thead>
                                                    <tr>
                                                        <th>Customer</th>
                                                        <th>IP Address</th>
                                                        <th>Action Name</th>
                                                        <th>Action Time</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="log" items="${logs.data}" varStatus="loop">
                                                        <tr>
                                                            <td>${log.customer}</td>
                                                            <td>${log.ipAddress}</td>
                                                            <td>${log.actionName}</td>
                                                            <td>${log.actionTime}</td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                    <caption>
                                                        <nav aria-label="Pagination">
                                                            <ul class="pagination">
                                                                <li class="page-item ${!logs.canGoPrev ? 'disabled':''}">
                                                                    <a
                                                                            class="page-link"
                                                                            href="account?type=admin-logs&logPagination=${logs.currentPage - 1}">Previous</a>
                                                                </li>
                                                                <c:forEach begin="1" end="${logs.pages}" var="i">
                                                                    <li class="page-item ${logs.currentPage == i ? 'active': ""}">
                                                                        <a class="page-link"
                                                                           href="account?type=admin-logs&logPagination=${i}">${i}</a>
                                                                    </li>
                                                                </c:forEach>
                                                                <li class="page-item ${!logs.canGoNext ? 'disabled':''}">
                                                                    <a
                                                                            class="page-link"
                                                                            href="account?type=admin-logs&logPagination=${logs.currentPage + 1}">Next</a>
                                                                </li>
                                                            </ul>
                                                        </nav>
                                                    </caption>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <div id="details" class="col">
            <form method="post" action="account?type=details" novalidate>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12 d-flex flex-row justify-content-between align-items-center">
                            <h2>Customer Details</h2>
                            <span class="material-icons" onclick="toggleDetailsFields()">edit</span>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="firstName" name="firstName" maxlength="100" required class="form-control"
                                       type="text" value="${customerData.firstName}" disabled/>
                                <label for="firstName">First name</label>

                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6  my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="lastName" name="lastName" class="form-control" required minlength="2"
                                       maxlength="100" type="text" value="${customerData.lastName}" disabled/>
                                <label for="lastName">Last name</label>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6  my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="dateOfBirth" name="dateOfBirth" class="form-control" required maxlength="8"
                                       type="date" value="${customerData.dateOfBirth}" disabled/>
                                <label for="dateOfBirth">Date of birth</label>
                            </div>
                        </div>
                        <div id="emailWrapper" class="col-xs-12 col-md-6  my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="email" class="form-control" type="email" value="${customerData.email}"
                                       disabled/>
                                <label for="email">Email</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <button id="btnSaveDetails" type="submit" class="btn btn-success">Save</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div id="address" class="col">
            <div class="row">
                <div class="col-12"><h2>Address</h2></div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-md-6">
                    <c:choose>
                        <c:when test="${addresses == null}">
                            <span>You have no addresses</span>
                        </c:when>
                        <c:otherwise>
                            <form action="account?type=default-address" class="d-flex flex-row" method="post">
                                <select class="form-select" name="address" aria-label="Default address">
                                    <c:if test="${defaultAddress == null}">
                                        <option selected>Select Default Address</option>
                                    </c:if>
                                    <c:forEach var="address" items="${addresses}">
                                        <option value="${address.id}" ${address.id == defaultAddress.id ? "selected" : ""}>${address}</option>
                                    </c:forEach>
                                </select>
                                <button class="btn btn-success" type="submit">Save</button>
                            </form>

                        </c:otherwise>
                    </c:choose>

                </div>
                <div class="col-xs-12 col-md-6">
                    <button type="button" class="btn btn-primary" data-bs-target="#newAddressModal"
                            data-bs-toggle="modal">Add New Address
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-12  table-responsive">
                    <c:if test="${addresses != null}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Street</th>
                                <th>Street No.</th>
                                <th>Apt No.</th>
                                <th>Floor No.</th>
                                <th>Zip Code</th>
                                <th>City</th>
                                <th>County</th>
                                <th>Country</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="address" items="${addresses}" varStatus="loop">
                                <tr>
                                    <td>${address.street}</td>
                                    <td>${address.streetNumber}</td>
                                    <td>${address.apartmentNumber}</td>
                                    <td>${address.floorNumber}</td>
                                    <td>${address.zipCode}</td>
                                    <td>${address.city}</td>
                                    <td>${address.county}</td>
                                    <td>${address.country}</td>
                                    <td>
                                        <button type="button" class="btn btn-sm btn-warning" data-bs-toggle="modal"
                                                data-bs-target="#editAddressModal${loop.index}">
                                            <span class="material-icons">edit</span>&nbsp;Edit
                                        </button>
                                        <div class="modal fade" id="editAddressModal${loop.index}"
                                             data-bs-backdrop="static"
                                             data-bs-keyboard="false" tabindex="-1"
                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Add New Address</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                                aria-label="Close"></button>
                                                    </div>
                                                    <form id="editAddress${loop.index}" method="post"
                                                          action="account?type=address">
                                                        <input id="id${loop.index}" name="id"
                                                               value="${address.id}" hidden readonly required/>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="streetName" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.street}"
                                                                                   id="eStreetName${loop.index}"
                                                                                   required>
                                                                            <label for="eStreetName${loop.index}">Street
                                                                                Name</label>
                                                                            <div class="invalid-feedback">
                                                                                Street is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="streetNumber" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.streetNumber}"
                                                                                   id="eStreetNumber${loop.index}"
                                                                                   required>
                                                                            <label for="eStreetNumber${loop.index}">Street
                                                                                Number</label>
                                                                            <div class="invalid-feedback">
                                                                                Street number is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="aptNumber" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.apartmentNumber}"
                                                                                   id="eApartment${loop.index}">
                                                                            <label for="eApartment${loop.index}">Apartment
                                                                                Number</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="floor" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.floorNumber}"
                                                                                   id="eFloor${loop.index}">
                                                                            <label for="eFloor${loop.index}">Floor
                                                                                Number</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="zipCode" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.zipCode}"
                                                                                   id="eZipCode${loop.index}" required>
                                                                            <label for="eZipCode${loop.index}">ZIP
                                                                                Code</label>
                                                                            <div class="invalid-feedback">
                                                                                Zip code is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="city" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.city}"
                                                                                   id="eCity${loop.index}" required>
                                                                            <label for="eCity${loop.index}">City</label>
                                                                            <div class="invalid-feedback">
                                                                                City is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="county" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.county}"
                                                                                   id="eCounty${loop.index}" required>
                                                                            <label for="eCounty${loop.index}">County</label>
                                                                            <div class="invalid-feedback">
                                                                                County is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                                                                    <div class="input-group">
                                                                        <div class="form-floating w-100">
                                                                            <input name="country" type="text"
                                                                                   class="form-control"
                                                                                   value="${address.country}"
                                                                                   id="eCountry${loop.index}" required>
                                                                            <label for="eCountry${loop.index}">Country</label>
                                                                            <div class="invalid-feedback">
                                                                                Country is required
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>

                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-outline-danger"
                                                                data-bs-dismiss="modal">Close
                                                        </button>
                                                        <button type="submit" form="editAddress${loop.index}"
                                                                class="btn btn-primary">Save
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>

                        </table>
                    </c:if>


                </div>
            </div>
        </div>
        <div id="history" class="col">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12 table-responsive">
                        <c:choose>
                            <c:when test="${carts == null}">
                                <span>You have no purchases to show.</span>
                            </c:when>
                            <c:otherwise>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Date of purchase</th>
                                        <th>Total price</th>
                                        <th>Details</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="cart" items="${carts}" varStatus="loop">
                                        <tr>
                                            <td>${cart.dateOfPurchase}</td>
                                            <td>${cart.totalPriceString}</td>
                                            <td>
                                                <button type="button" class="btn btn-sm btn-primary"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#historyModal${loop.index}">
                                                    <span class="material-icons">preview</span>&nbsp;View
                                                </button>
                                                <div class="modal fade" id="historyModal${loop.index}"
                                                     data-bs-backdrop="static"
                                                     data-bs-keyboard="false" tabindex="-1"
                                                     aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">Purchase details</h5>
                                                                <button type="button" class="btn-close"
                                                                        data-bs-dismiss="modal"
                                                                        aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <table class="table table-striped">
                                                                    <thead>
                                                                    <tr>
                                                                        <th>Item</th>
                                                                        <th>Quantity</th>
                                                                        <th>Price per item</th>
                                                                        <th>Total price</th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:forEach var="cartItem" items="${cart.cartItems}">
                                                                        <tr>
                                                                            <td>${cartItem.item.name}</td>
                                                                            <td>${cartItem.count}</td>
                                                                            <td>${cartItem.price}kn</td>
                                                                            <td>${cartItem.price * cartItem.count}kn</td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                    </tbody>
                                                                    <caption>
                                                                    <span>Total cost: ${cart.totalPriceString}</span>
                                                                    </caption>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="newAddressModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add New Address</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="newAddress" method="post" action="account?type=address">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="streetName" type="text" class="form-control"
                                           id="cStreetName" required>
                                    <label for="cStreetName">Street Name</label>
                                    <div class="invalid-feedback">
                                        Street is required
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="streetNumber" type="text" class="form-control"
                                           id="cStreetNumber" required>
                                    <label for="cStreetNumber">Street Number</label>
                                    <div class="invalid-feedback">
                                        Street number is required
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="aptNumber" type="text" class="form-control"
                                           id="cApartment">
                                    <label for="cApartment">Apartment Number</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="floor" type="text" class="form-control"
                                           id="cFloor">
                                    <label for="cFloor">Floor Number</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="zipCode" type="text" class="form-control"
                                           id="cZipCode" required>
                                    <label for="cZipCode">ZIP Code</label>
                                    <div class="invalid-feedback">
                                        Zip code is required
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="city" type="text" class="form-control"
                                           id="cCity" required>
                                    <label for="cCity">City</label>
                                    <div class="invalid-feedback">
                                        City is required
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="county" type="text" class="form-control"
                                           id="cCounty" required>
                                    <label for="cCounty">County</label>
                                    <div class="invalid-feedback">
                                        County is required
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6 my-3 mx-xs-0 mx-md-3">
                            <div class="input-group">
                                <div class="form-floating w-100">
                                    <input name="country" type="text" class="form-control"
                                           id="cCountry" required>
                                    <label for="cCountry">Country</label>
                                    <div class="invalid-feedback">
                                        Country is required
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <div class="modal-footer">
                <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Close</button>
                <button type="submit" form="newAddress" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
