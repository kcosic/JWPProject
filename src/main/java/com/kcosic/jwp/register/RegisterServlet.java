package com.kcosic.jwp.register;

import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), false);
            request.getRequestDispatcher(JspEnum.REGISTER.toString()).forward(request, response);
        } catch (ServletException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var email = (String)request.getParameter(AttributeEnum.EMAIL.toString());
        var firstName =request.getParameter(AttributeEnum.FIRST_NAME.toString());
        var lastName =request.getParameter(AttributeEnum.LAST_NAME.toString());
        var dateOfBirth = Date.valueOf(request.getParameter(AttributeEnum.DATE_OF_BIRTH.toString()));

        if(DbHelper.doesEmailExist(email)){
            attachAttributes(request, firstName, lastName, dateOfBirth, email);
            sendErrorResponse(request, response, "Email already exists");
            return;
        }

        String hashedPass = "";
        try {
            hashedPass = Helper.hash(request.getParameter(AttributeEnum.PASSWORD.toString()));
        } catch (NoSuchAlgorithmException e) {
            attachAttributes(request, firstName, lastName, dateOfBirth, email);
            sendErrorResponse(request,response,e.getMessage());
            return;
        }
        var newCustomer = new CustomerEntity();
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setDateOfBirth(dateOfBirth);
        newCustomer.setEmail(email);
        newCustomer.setPassword(hashedPass);
        newCustomer.setRoleId(1);
        newCustomer.setDefaultAddressId(null);
        newCustomer.setCurrentCartId(null);

        newCustomer = DbHelper.createCustomer(newCustomer);


        request.getRequestDispatcher(JspEnum.LOGIN.toString()).forward(request, response);

    }

    private void attachAttributes(HttpServletRequest request, String firstName, String lastName, Date dateOfBirth, String email) {
        request.setAttribute(AttributeEnum.FIRST_NAME.toString(), firstName);
        request.setAttribute(AttributeEnum.LAST_NAME.toString(), lastName);
        request.setAttribute(AttributeEnum.DATE_OF_BIRTH.toString(), dateOfBirth);
        request.setAttribute(AttributeEnum.EMAIL.toString(), email);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute(AttributeEnum.HAS_ERROR.toString(), true);
        request.setAttribute(AttributeEnum.ERROR_MESSAGE.toString(), message);
        request.getRequestDispatcher(JspEnum.REGISTER.toString()).forward(request, response);
    }
}
