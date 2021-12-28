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

@WebServlet(name = "ProductServlet", value = "/product/*")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processProductGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processProductPostRequest(request,response);
    }

    private void processProductPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var customer = Helper.getSessionData(request, AttributeEnum.USER_DATA, CustomerEntity.class);
        var item = (ItemEntity)request.getAttribute(AttributeEnum.ITEM.toString());
        var data = DbHelper.retrieveItems();
        if (customer == null) {
            customer = Helper.createGuestCustomer();
            Helper.setSessionData(request, AttributeEnum.USER_DATA, customer);
        }
        try {

            var price = DbHelper.addToCart(
                    customer.getId(),
                    item,
                    true);
            request.setAttribute(AttributeEnum.TOTAL_PRICE.toString(), price);

        } catch (EntityNotFoundException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        }
        request.setAttribute(AttributeEnum.USER_DATA.toString(), customer.toString());
        request.setAttribute(AttributeEnum.ITEMS.toString(), data);
        request.removeAttribute(AttributeEnum.HAS_ERROR.toString());
        request.getRequestDispatcher(JspEnum.PRODUCT.toString()).forward(request, response);
    }


    private void processProductGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var itemIdRaw = request.getRequestURI().replace("/product/","");
        Integer itemId = null;
        try{
            itemId = Integer.parseInt(itemIdRaw);
            if (itemId == null) {
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            request.getRequestDispatcher(JspEnum.PRODUCTS.toString()).forward(request, response);
        }
        var item = DbHelper.retrieveItem(itemId);
        request.setAttribute(AttributeEnum.ITEM.toString(), item);
        request.removeAttribute(AttributeEnum.ITEMS.toString());
        request.removeAttribute(AttributeEnum.HAS_ERROR.toString());
        request.getRequestDispatcher(JspEnum.PRODUCT.toString()).forward(request, response);

    }
}
