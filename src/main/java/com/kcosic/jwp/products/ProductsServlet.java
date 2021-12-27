package com.kcosic.jwp.products;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProductsServlet", value = "/products")
public class ProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        var data = DbHelper.retrieveItems();
        try {
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), false);
            request.setAttribute(AttributeEnum.ITEMS.toString(), data);
            request.getRequestDispatcher(JspEnum.PRODUCTS.toString()).forward(request, response);
        } catch (ServletException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
            var a  = "shit";
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
