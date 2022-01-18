package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        processRegistrationGetRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        processRegistrationPostRequest(request, response);
    }

    private void processRegistrationGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        var customer = Helper.getSessionData(request, AttributeEnum.CUSTOMER_DATA, CustomerEntity.class);

        if(customer != null && customer.getRole().getId() != 3){
            request.getRequestDispatcher(JspEnum.PRODUCTS.getJsp()).forward(request, response);
        }

        Helper.addAttribute(request, AttributeEnum.HAS_ERROR, false);
        request.getRequestDispatcher(JspEnum.REGISTER.getJsp()).forward(request, response);

    }

    private void processRegistrationPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var email = (String)request.getParameter(AttributeEnum.EMAIL.toString());
        var firstName =request.getParameter(AttributeEnum.FIRST_NAME.toString());
        var lastName =request.getParameter(AttributeEnum.LAST_NAME.toString());
        var dateOfBirth = Date.valueOf(request.getParameter(AttributeEnum.DATE_OF_BIRTH.toString()));

        if(DbHelper.doesEmailExist(email)){
            attachAttributes(request, firstName, lastName, dateOfBirth, email);
            sendErrorResponse(request, response, "Email already exists");
            return;
        }

        String hashedPass;
        try {
            hashedPass = Helper.hash(request.getParameter(AttributeEnum.PASSWORD.toString()));
        } catch (NoSuchAlgorithmException e) {
            attachAttributes(request, firstName, lastName, dateOfBirth, email);
            sendErrorResponse(request,response,e.getMessage());
            return;
        }
        var guestCustomer = Helper.getSessionData(request, AttributeEnum.CUSTOMER_DATA, CustomerEntity.class);
        if(guestCustomer != null){
            guestCustomer.setFirstName(firstName);
            guestCustomer.setLastName(lastName);
            guestCustomer.setDateOfBirth(dateOfBirth);
            guestCustomer.setEmail(email);
            guestCustomer.setPassword(hashedPass);
            guestCustomer.setRole(DbHelper.retrieveRole(2));
            DbHelper.updateCustomer(guestCustomer);
        }
        else {
            var newCustomer = new CustomerEntity();
            newCustomer.setFirstName(firstName);
            newCustomer.setLastName(lastName);
            newCustomer.setDateOfBirth(dateOfBirth);
            newCustomer.setEmail(email);
            newCustomer.setPassword(hashedPass);
            newCustomer.setRole(DbHelper.retrieveRole(2));
            DbHelper.createCustomer(newCustomer);
        }

        Helper.goToPage(request,response, JspEnum.LOGIN, false);
    }

    private void attachAttributes(HttpServletRequest request, String firstName, String lastName, Date dateOfBirth, String email) {
       Helper.addAttribute(request,AttributeEnum.FIRST_NAME, firstName);
       Helper.addAttribute(request,AttributeEnum.LAST_NAME, lastName);
       Helper.addAttribute(request,AttributeEnum.DATE_OF_BIRTH, dateOfBirth);
       Helper.addAttribute(request,AttributeEnum.EMAIL, email);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        Helper.addAttribute(request,AttributeEnum.HAS_ERROR, true);
        Helper.addAttribute(request,AttributeEnum.ERROR_MESSAGE, message);
        request.getRequestDispatcher(JspEnum.REGISTER.getJsp()).forward(request, response);
    }
}
