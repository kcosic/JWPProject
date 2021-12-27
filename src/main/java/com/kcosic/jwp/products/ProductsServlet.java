package com.kcosic.jwp.products;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet(name = "ProductsServlet", value = "/products")
public class ProductsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       processProductsGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processProductsPostRequest(request, response);
    }

    private void processProductsPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var customer = Helper.getSessionData(request, AttributeEnum.USER_DATA, CustomerEntity.class);
        var itemId = Integer.parseInt(request.getParameter(AttributeEnum.ITEM_ID.toString()));
        var data = DbHelper.retrieveItems();
        try {
            if (customer == null) {
                customer = Helper.createGuestCustomer();
            }
            var optionalItem = data.stream().filter((item)->item.getId().equals(itemId)).findFirst();
            if(optionalItem.isEmpty()) {
                throw new EntityNotFoundException("Item not found with that ID");
            }
            var price = DbHelper.addToCart(
                    customer.getId(),
                    optionalItem.get(),
                    false);
            request.setAttribute(AttributeEnum.TOTAL_PRICE.toString(), price.toString());

        } catch (EntityNotFoundException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        }

        request.setAttribute(AttributeEnum.ITEMS.toString(), data);
        request.setAttribute(AttributeEnum.HAS_ERROR.toString(), true);
        request.getRequestDispatcher(JspEnum.PRODUCTS.toString()).forward(request, response);
    }

    private void processProductsGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        var data = DbHelper.retrieveItems();
        try {
            request.setAttribute(AttributeEnum.TOTAL_PRICE.toString(), BigDecimal.ZERO.toString());
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), false);
            request.setAttribute(AttributeEnum.ITEMS.toString(), data);
            request.getRequestDispatcher(JspEnum.PRODUCTS.toString()).forward(request, response);
        } catch (ServletException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        }
    }

}
