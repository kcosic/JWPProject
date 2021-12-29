package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import com.kcosic.jwp.shared.model.entities.ItemEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.*;
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
        if (customer == null) {
            customer = Helper.createGuestCustomer();
            Helper.setSessionData(request, AttributeEnum.USER_DATA, customer);
        }
        try {

            var optionalItem = data.stream().filter((item)->item.getId().equals(itemId)).findFirst();
            if(optionalItem.isEmpty()) {
                throw new EntityNotFoundException("Item not found with that ID");
            }
            var price = DbHelper.addToCart(
                    customer.getId(),
                    optionalItem.get(),
                    true);

            var cartItems = DbHelper.cartQuantity(customer.getCurrentCartId());
            Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE, price);
            Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);
        } catch (EntityNotFoundException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        }
        Helper.addAttribute(request,AttributeEnum.ITEMS, data);
        request.getRequestDispatcher(JspEnum.PRODUCTS.getJsp()).forward(request, response);
    }

    private void processProductsGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        var search = request.getParameter(AttributeEnum.SEARCH.toString());
        List<ItemEntity> data;
        if(search == null){
            data = DbHelper.retrieveItems();
        }else {
            data = DbHelper.retrieveItems(search);
        }
        var customer = Helper.getSessionData(request, AttributeEnum.USER_DATA, CustomerEntity.class);
        if (customer == null) {
            customer = Helper.createGuestCustomer();
            Helper.setSessionData(request, AttributeEnum.USER_DATA, customer);
        }
        Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE,(
                customer.getCurrentCartId() != null ?
                        DbHelper.calculateTotalPrice(DbHelper.retrieveCartItems(customer.getCurrentCartId())) :
                        "0"));
        var cartItems = DbHelper.cartQuantity(customer.getCurrentCartId());
        Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);
        Helper.addAttribute(request,AttributeEnum.ITEMS, data);
        Helper.addAttribute(request,AttributeEnum.SEARCH, search);
        request.getRequestDispatcher(JspEnum.PRODUCTS.getJsp()).forward(request, response);

    }

}
