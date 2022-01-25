<%@ page import="com.kcosic.jwp.shared.enums.AttributeEnum" %><%--
  Created by IntelliJ IDEA.
  User: Kresimir
  Date: 11/01/2022
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="totalPrice" scope="request" value="<%= request.getAttribute(AttributeEnum.TOTAL_PRICE.toString())%>"/>
<c:set var="customerData" scope="session" value="<%= request.getSession().getAttribute(AttributeEnum.CUSTOMER_DATA.toString())%>"/>
<c:set var="defaultAddress" scope="request" value="<%= request.getAttribute(AttributeEnum.DEFAULT_ADDRESS.toString())%>"/>
<html>
<head>
    <title>Payment</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${contextPath}/assets/scripts/jQuery/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="${contextPath}/assets/scripts/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${contextPath}/assets/styles/custom/payment.css" rel="stylesheet" type="text/css">
    <script>
        let totalPrice = '${totalPrice}';
        let customerName = '${customerData}';
        let address = '${defaultAddress}';
    </script>
    <script src="${contextPath}/assets/scripts/custom/payment.js" type="text/javascript"></script>
    <script src="https://www.paypal.com/sdk/js?client-id=AarCvYNF5RL2c37rDeEKejUjEii_IBFcH9p7q_hNsZ_XXnfvqbbg4SStnQQ21k8Mr7UgMQgMo2gwJJxt&components=buttons"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-header">
                    <h2>Payment</h2>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-xs-12 col-md-6">Customer</div>
                        <div class="col-xs-12 col-md-6">
                            <c:choose>
                                <c:when test="${customerData == null}">
                                   Error-customer is empty
                                </c:when>
                                <c:otherwise>
                                    ${customerData}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-md-6">Default address</div>
                        <div class="col-xs-12 col-md-6">
                            <c:choose>
                                <c:when test="${defaultAddress == null}">
                                    You have no assigned default address. Go to your profile and assign it.
                                </c:when>
                                <c:otherwise>
                                    ${defaultAddress}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-md-6">Final price</div>
                        <div class="col-xs-12 col-md-6">
                            ${totalPrice}kn
                        </div>
                    </div>
                </div>
                <div class="card-footer">

                    <c:choose>
                        <c:when test="${defaultAddress != null && customerData != null}">
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-3 d-grid gap-2">
                                    <button type="submit" form="cash" class="btn btn-success" >Cash on delivery</button>
                                </div>
                            </div>


                        </c:when>
                        <c:otherwise>
                            Set default address
                        </c:otherwise>
                    </c:choose>
                    <div ${defaultAddress != null && customerData != null ? "" : "hidden"} class="row">
                        <div class="col-xs-12 col-sm-6 col-md-3">
                            <div class="" id="paypal-button-container"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-sm-6 col-md-3 d-grid gap-2">
                            <button type="submit" form="cart" class="btn btn-warning">Go to cart</button>
                        </div>
                    </div>
                    <form action="cart" method="get" id="cart"></form>
                    <form action="payment" method="post" id="cash">
                        <input hidden readonly required name="payment" value="cash"/>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
