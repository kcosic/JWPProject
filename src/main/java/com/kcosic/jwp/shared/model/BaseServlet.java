package com.kcosic.jwp.shared.model;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BaseServlet extends HttpServlet {
    public BaseServlet() {
        super();
    }

    protected CustomerEntity getOrCreateCustomer(HttpServletRequest request){
        var customer = Helper.getSessionData(request, AttributeEnum.CUSTOMER_DATA, CustomerEntity.class);
        if (customer == null) {
            customer = Helper.createGuestCustomer();
        }
        Helper.setSessionData(request, AttributeEnum.CUSTOMER_DATA, customer);
        return customer;
    }

    protected void logoutCustomer(HttpServletRequest request){
        Helper.removeSessionData(request, AttributeEnum.CUSTOMER_DATA);
        Helper.removeSessionData(request, AttributeEnum.TOTAL_PRICE);
        Helper.removeSessionData(request, AttributeEnum.CART_ITEMS);
        Helper.removeAttribute(request, AttributeEnum.LOGOUT);
    }

    protected void handleLogoutRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutCustomer(request);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
    }
}
