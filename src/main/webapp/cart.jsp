<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %><%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 11/01/2022
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="cart" scope="request" value="<%= request.getAttribute(AttributeEnum.CART.toString())%>"/>
<html>
<head>
    <title>Cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/cart.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/assets/scripts/custom/cart.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/custom/header.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container my-3">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header"><h2>Cart</h2></div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${cart.cartItems != null && cart.cartItems.size() > 0}">
                            <div class="row">
                                <div class="col overflow-x-auto">
                                    <table class="table table-sm table-striped table-responsive table-min-width">
                                        <thead>
                                        <tr>
                                            <th>Item name</th>
                                            <th>Quantity</th>
                                            <th>Price per unit</th>
                                            <th>Total price</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${cart.cartItems}" var="cartItem" varStatus="loop">
                                            <tr>
                                                <td>${cartItem.item.name}</td>
                                                <td>
                                                    <button class="btn btn-sm btn-secondary"  onclick="toggleOverlay(true)"
                                                            form="itemIncrease${loop.index}" type="submit"><span
                                                            class="material-icons">arrow_upward</span></button>
                                                        ${cartItem.count}
                                                    <button class="btn btn-sm btn-secondary"  onclick="toggleOverlay(true)"
                                                            form="itemDecrease${loop.index}" type="submit"><span
                                                            class="material-icons">arrow_downward</span></button>
                                                    <form id="itemIncrease${loop.index}" action="cart" method="post">
                                                        <input name="cartItemId" readonly required hidden
                                                               value="${cartItem.id}"/>
                                                        <input name="count" readonly required hidden
                                                               value="${cartItem.count + 1}"/>
                                                    </form>
                                                    <form id="itemDecrease${loop.index}" action="cart" method="post">
                                                        <input name="cartItemId" readonly required hidden
                                                               value="${cartItem.id}"/>
                                                        <input name="count" readonly required hidden
                                                               value="${cartItem.count - 1}"/>
                                                    </form>
                                                </td>
                                                <td>${cartItem.price}kn</td>
                                                <td>${cartItem.price * cartItem.count}kn</td>
                                                <td>
                                                    <form method="post" action="cart">
                                                        <input name="removeItem" value="${cartItem.id}" readonly
                                                               required hidden/>
                                                        <button type="submit" class="btn btn-outline-danger">
                                                            <span class="material-icons">delete_forever</span>
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td>Total cart price</td>
                                            <td>${cart.totalPrice}kn</td>
                                            <td></td>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row text-center">
                                Your cart is empty
                            </div>
                        </c:otherwise>
                    </c:choose>


                </div>

                <div class="card-footer">
                    <c:choose>
                        <c:when test="${cart.cartItems != null && cart.cartItems.size() > 0}">
                            <div class="row justify-content-end">
                                <div class="col-xs-12 col-sm-6 col-md-2">
                                    <form action="cart" method="post" id="clearCart" novalidate>
                                        <input name="clearCart" value="true" readonly required hidden/>
                                    </form>
                                    <button type="submit"  onclick="toggleOverlay(true)" form="clearCart" class="btn btn-outline-danger">
                                        <span class="material-icons">remove_shopping_cart</span>&nbsp;Clear
                                    </button>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-2">
                                    <form action="payment" method="get" id="submitCart" novalidate></form>
                                    <button type="submit"  onclick="toggleOverlay(true)" form="submitCart" class="btn btn-success">
                                        <span class="material-icons">shopping_cart_checkout</span>&nbsp;Checkout
                                    </button>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row justify-content-end">
                                <div class="col-xs-12 col-sm-6 col-md-2">
                                    <form action="products" method="get" id="products" novalidate></form>
                                    <button type="submit"  onclick="toggleOverlay(true)" form="products" class="btn btn-warning">
                                        <span class="material-icons">arrow_left</span>&nbsp;To Products
                                    </button>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>


            </div>
        </div>
    </div>
</div>
</body>
</html>
