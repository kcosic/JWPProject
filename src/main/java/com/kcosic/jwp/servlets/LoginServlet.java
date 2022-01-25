package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        processLoginGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        processLoginPostRequest(request, response);
    }

    private void processLoginPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(AttributeEnum.LOGIN.toString());
        String password = request.getParameter(AttributeEnum.PASSWORD.toString());
        var customer = getOrCreateCustomer(request);
        try {
            var loggedInCustomer = DbHelper.retrieveCustomerByEmail(username);
            var hashedPassword = Helper.hash(password);
            if (hashedPassword.equals(loggedInCustomer.getPassword())) {
                var currentCart = DbHelper.retrieveCurrentCart(loggedInCustomer.getId());
                Helper.setSessionData(request, AttributeEnum.CUSTOMER_DATA, loggedInCustomer);
                Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE,(
                        currentCart != null ? currentCart.getTotalPriceString() : "0"));
                var cartItems = DbHelper.cartQuantity(loggedInCustomer.getId());
                Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);
                Helper.addAttribute(request,AttributeEnum.HAS_ERROR, false);

                DbHelper.deleteCustomer(customer);
                Helper.log(request, loggedInCustomer.getEmail(), "USER LOGGED IN");
            }
            else {
                Helper.addAttribute(request,AttributeEnum.HAS_ERROR, true);
            }
        } catch (EntityNotFoundException e) {
            Helper.addAttribute(request,AttributeEnum.HAS_ERROR, true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
    }

    private void processLoginGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Helper.addAttribute(request,AttributeEnum.HAS_ERROR, false);
        request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
    }
}
