package com.kcosic.jwp.login;

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
        processLoginGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       processLoginPostRequest(request, response);
    }

    private void processLoginPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(AttributeEnum.LOGIN.toString());
        String password = request.getParameter(AttributeEnum.PASSWORD.toString());

        try {
            var customer = DbHelper.retrieveCustomerByEmail(username);
            var hashedPassword = Helper.hash(password);
            if (hashedPassword.equals(customer.getPassword())) {
                Helper.setSessionData(request, AttributeEnum.USER_DATA, customer);
                request.getRequestDispatcher(JspEnum.PRODUCTS.toString()).forward(request, response);
            }
        } catch (EntityNotFoundException e) {
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), true);
            request.getRequestDispatcher(JspEnum.LOGIN.toString()).forward(request, response);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private void processLoginGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/html");

            if (Helper.isUserAuthenticated(request.getParameter(AttributeEnum.USER_DATA.toString()))) {
                request.setAttribute(AttributeEnum.HAS_ERROR.toString(), false);
                request.getRequestDispatcher(JspEnum.PRODUCTS.toString()).forward(request, response);
            }

            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), false);
            request.getRequestDispatcher(JspEnum.LOGIN.toString()).forward(request, response);

        } catch (ServletException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        }
    }
}
