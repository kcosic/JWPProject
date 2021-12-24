package com.kcosic.jwp.login;

import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        try {
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), false);
            request.getRequestDispatcher(JspEnum.LOGIN.toString()).forward(request, response);
        } catch (ServletException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(AttributeEnum.LOGIN.toString());
        String password = request.getParameter(AttributeEnum.PASSWORD.toString());

        try {
            var customer = DbHelper.retrieveCustomerByEmail(username);
            var hashedPassword = Helper.hash(password);
            if(hashedPassword.equals(customer.getPassword())){
                request.getRequestDispatcher(null).forward(request, response);
            }
        } catch (EntityNotFoundException e) {
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), true);
            request.getRequestDispatcher(JspEnum.LOGIN.toString()).forward(request, response);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
