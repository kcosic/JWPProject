package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.enums.PaymentEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;

@WebServlet(name = "PaymentServlet", value = "/payment")
public class PaymentServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processPaymentGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processPostPaymentRequest(request, response);
    }

    private void processPostPaymentRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var payment = request.getParameter(AttributeEnum.PAYMENT.toString());
        if(payment.equals("paypal")){
            handlePaypal(request, response);
        }
        else {
            handleCash(request, response);
        }
    }

    private void handlePaypal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);

        var cart = DbHelper.retrieveCurrentCart(customer.getId());
        cart.setPaidWith(PaymentEnum.PAYPAL);
        cart.setCurrent(false);
        cart.setDateOfPurchase(Date.valueOf(LocalDate.now()));

        DbHelper.updateCart(cart);

        response.setStatus(200);
        response.setHeader("Content-type", "Text/html");
        request.getRequestDispatcher(JspEnum.PAYMENT_SUCCESS.getJsp()).forward(request, response);
    }

    private void handleCash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);

        var cart = DbHelper.retrieveCurrentCart(customer.getId());
        cart.setPaidWith(PaymentEnum.CASH);
        cart.setCurrent(false);
        cart.setDateOfPurchase(Date.valueOf(LocalDate.now()));

        DbHelper.updateCart(cart);
        request.getRequestDispatcher(JspEnum.PAYMENT_SUCCESS.getJsp()).forward(request, response);
    }

    private void processPaymentGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);
        if(customer.getRole().getId() == 3){
            Helper.setSessionData(request, AttributeEnum.CUSTOMER_DATA, customer);
            Helper.addAttribute(request, AttributeEnum.HAS_ERROR, false);
            request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
            return;
        }
        var defaultAddress = DbHelper.retrieveDefaultAddress(customer);
        Helper.addAttribute(request, AttributeEnum.DEFAULT_ADDRESS, defaultAddress);

        var totalPrice = DbHelper.retrieveCurrentCart(customer.getId()).getTotalPriceString();
        Helper.addAttribute(request, AttributeEnum.TOTAL_PRICE, totalPrice);

        request.getRequestDispatcher(JspEnum.PAYMENT.getJsp()).forward(request, response);
    }

}
