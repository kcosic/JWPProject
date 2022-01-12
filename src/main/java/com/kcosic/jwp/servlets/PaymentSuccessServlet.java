package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.JspEnum;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PaymentSuccessServlet", value = "/paymentSuccess")
public class PaymentSuccessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspEnum.PAYMENT_SUCCESS.getJsp()).forward(request, response);
    }

}
