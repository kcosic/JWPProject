package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import com.kcosic.jwp.shared.model.entities.ItemEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processAccountGetRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processAccountPostRequest(request, response);

    }

    private void processAccountGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var customer = Helper.getSessionData(request, AttributeEnum.USER_DATA, CustomerEntity.class);

        if (customer == null) {
            customer = Helper.createGuestCustomer();
            Helper.setSessionData(request, AttributeEnum.USER_DATA, customer);
        }

        if(customer.getRoleId() == 3){
            request.getRequestDispatcher(JspEnum.REGISTER.getJsp()).forward(request, response);
        }


        request.setAttribute(AttributeEnum.USER_DATA.toString(), customer.toString());
        request.getRequestDispatcher(JspEnum.PRODUCT.getJsp()).forward(request, response);
    }

    private void processAccountPostRequest(HttpServletRequest request, HttpServletResponse response) {
        
    }
}
