<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kcosic.jwp.shared.model.entities.CartEntity" %><%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 29/12/2021
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>My Account</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/account.css" rel="stylesheet" type="text/css">
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
                <c:if test="${userData.roleId == 1}">
                    <li class="nav-item w-100 d-grid gap-2">
                        <button id="btn-admin" class="btn" onclick="display('admin')">Admin</button>
                    </li>
                </c:if>
            </ul>
        </div>
        <%-- CONTENT --%>
        <c:if test="${userData.roleId == 1}">
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
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12" id="admin-history">
                            <c:choose>
                                <c:when test="${allCarts == null}">
                                    <span>There are no transactions to show.</span>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>User Email</th>
                                            <th>Date of purchase</th>
                                            <th>Total price</th>
                                            <th>Details</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="cart" items="${allCarts}" varStatus="loop">
                                            <tr>
                                                <td>${cart.customer.email}</td>
                                                <td>${cart.dateOfPurchase}</td>
                                                <td>${cart.totalPriceString}</td>
                                                <td>
                                                    <button type="button" class="btn btn-sm btn-primary"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#adminHistoryModal${loop.index}">
                                                        <span class="material-icons">edit</span>&nbsp;Edit
                                                    </button>
                                                    <div class="modal fade" id="adminHistoryModal${loop.index}"
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
                                                                        <c:forEach var="cartItem"
                                                                                   items="${cart.cartItems}">
                                                                            <tr>
                                                                                <td>${cartItem.item.name}</td>
                                                                                <td>${cartItem.quantity}</td>
                                                                                <td>${cartItem.price}kn</td>
                                                                                <td>${cartItem.price * cartItem.count}kn</td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                        </tbody>
                                                                        <tfoot>
                                                                        <span>Total cost: ${cart.totalPriceString}</span>
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
                                    </table>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-12" id="admin-items">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <h2>Items</h2>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-md-6">
                                        <button type="button" data-bs-target="#newItemModal" data-bs-toggle="modal">Add new item</button>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
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
                                                <c:forEach var="item" items="${items}" varStatus="loop">
                                                    <tr>
                                                        <td>${item.name}</td>
                                                        <td>${item.manufacturer}</td>
                                                        <td>${item.price}kn</td>
                                                        <td>${item.category.name}</td>
                                                        <td>
                                                            <span class="material-icons ${item.isActive ? "green" : "red"}">${item.isActive ? "check" : "remove"}</span>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-warning" type="button" data-bs-toggle="modal" data-bs-target="#editItemModal${loop.index}">
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
                                                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                                                    aria-label="Close"></button>
                                                                        </div>
                                                                        <form id="editItem${loop.index}" method="post"
                                                                              action="account?type=item">
                                                                            <input id="id${loop.index}" name="id"
                                                                                   value="${item.id}" hidden readonly required/>
                                                                            <div class="modal-body">
                                                                                <div class="row">
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="input-group">
                                                                                            <div class="form-floating w-100">
                                                                                                <input name="itemName" type="text"
                                                                                                       class="form-control"
                                                                                                       value="${item.name}"
                                                                                                       id="eItemName${loop.index}"
                                                                                                       required>
                                                                                                <label for="eItemName${loop.index}">Item name</label>
                                                                                                <div class="invalid-feedback">
                                                                                                    Item name is required
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="input-group">
                                                                                            <div class="form-floating w-100">
                                                                                                <input name="manufacturer" type="text"
                                                                                                       class="form-control"
                                                                                                       value="${item.manufacturer}"
                                                                                                       id="eManufacturer${loop.index}"
                                                                                                       >
                                                                                                <label for="eManufacturer${loop.index}">Manufacturer</label>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="input-group">
                                                                                            <div class="form-floating w-100">
                                                                                                <textarea name="description" rows="8" maxlength="999"
                                                                                                       class="form-control"
                                                                                                       value="${item.description}"
                                                                                                          id="eDescription${loop.index}">

                                                                                                </textarea>
                                                                                                <label for="eDescription${loop.index}">Description</label>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="input-group">
                                                                                            <div class="form-floating w-100">
                                                                                                <input name="floor" type="text"
                                                                                                       class="form-control"
                                                                                                       pattern="/^[+-]?([0-9]+\.?[0-9]*|\.[0-2]+)$/"
                                                                                                       value="${item.price}"
                                                                                                       id="ePrice${loop.index}" required>
                                                                                                <label for="ePrice${loop.index}">Price</label>
                                                                                                <div class="invalid-feedback">
                                                                                                    Price is required
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="input-group">
                                                                                            <div class="mb-3">
                                                                                                <label for="eImage${loop.index}">Image</label>                                                                                                <input name="image" type="file" accept=".jpg,.jpeg,.png"
                                                                                                       class="form-control"
                                                                                                       id="eImage${loop.index}" required>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="input-group">
                                                                                            <div class="form-floating w-100">

                                                                                                <select class="form-select" name="category" required id="eCategories${loop.index}">
                                                                                                    <c:forEach var="category" items="${categories}">
                                                                                                        <option value="${category.id}" ${category.id == item.category.id ? "selected" : ""}>${category.name}</option>
                                                                                                    </c:forEach>
                                                                                                </select>
                                                                                                <label for="eCategories${loop.index}">Category</label>                                                                                                <input name="image" type="file" accept=".jpg,.jpeg,.png"

                                                                                                <div class="invalid-feedback">
                                                                                                    Category is required
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 my-3 mx-3">
                                                                                        <div class="form-check">
                                                                                            <input class="" name="active" type="checkbox"
                                                                                                   checked="${item.isActive}"
                                                                                                   id="eActive${loop.index}" required>
                                                                                            <label class="form-check-label" for="eActive${loop.index}">
                                                                                                Active
                                                                                            </label>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </form>
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn btn-outline-danger"
                                                                                    data-bs-dismiss="modal">Close
                                                                            </button>
                                                                            <button type="submit" form="editItem${loop.index}"
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
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12" id="admin-categories">admin categories</div>
                        <div class="col-12" id="admin-customers">admin customers</div>
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
                                       type="text" value="${userData.firstName}" disabled="true"/>
                                <label for="firstName">First name</label>

                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6  my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="lastName" name="lastName" class="form-control" required minlength="2"
                                       maxlength="100" type="text" value="${userData.lastName}" disabled/>
                                <label for="lastName">Last name</label>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-6  my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="dateOfBirth" name="dateOfBirth" class="form-control" required maxlength="8"
                                       type="date" value="${userData.dateOfBirth}" disabled="true"/>
                                <label for="dateOfBirth">Date of birth</label>
                            </div>
                        </div>
                        <div id="emailWrapper" class="col-xs-12 col-md-6  my-3 mx-sm-1 mx-md-3">
                            <div class="details-value form-floating">
                                <input id="email" class="form-control" type="email" value="${userData.email}" disabled/>
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
                            <form action="account?type=default-address" method="post">
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
                <div class="col-12">
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
                                            <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                                                    data-bs-target="#historyModal${loop.index}">
                                                <span class="material-icons">edit</span>&nbsp;Edit
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
                                                                        <td>${cartItem.quantity}</td>
                                                                        <td>${cartItem.price}kn</td>
                                                                        <td>${cartItem.price * cartItem.count}kn</td>
                                                                    </tr>
                                                                </c:forEach>
                                                                </tbody>
                                                                <tfoot>
                                                                <span>Total cost: ${cart.totalPriceString}</span>
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
                            </table>
                        </c:otherwise>
                    </c:choose>
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
