package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.ItemEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processProductGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processProductPostRequest(request,response);
    }

    private void processProductPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        var isLoggedOut = request.getParameter(AttributeEnum.LOGOUT.toString()) != null;
        if(isLoggedOut){
            handleLogoutRequest(request, response);
        }
        else{
            handleAddToCartRequest(request, response);
        }
    }

    private void handleAddToCartRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);

        var item = (ItemEntity)request.getAttribute(AttributeEnum.ITEM.toString());

        String price = DbHelper.addToCart( customer.getId(), item, true);

        var cartItems = DbHelper.cartQuantity(customer.getId());
        Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE, price);
        Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);
        Helper.setSessionData(request, AttributeEnum.CUSTOMER_DATA, customer.toString());
        request.getRequestDispatcher(JspEnum.PRODUCT.getJsp()).forward(request, response);
    }

    @Override
    protected void handleLogoutRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.handleLogoutRequest(request, response);
        Helper.removeSessionData(request,AttributeEnum.CART_ITEMS);
        Helper.removeSessionData(request,AttributeEnum.TOTAL_PRICE);
        processProductGetRequest(request, response);
    }

    private void processProductGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var itemIdRaw = request.getParameter(AttributeEnum.ID.toString());
        Integer itemId = null;
        try{
            itemId = Integer.parseInt(itemIdRaw);
        }catch (NumberFormatException e){
            request.getRequestDispatcher(JspEnum.PRODUCTS.getJsp()).forward(request, response);
        }
        var item = DbHelper.retrieveItem(itemId);
        var category = DbHelper.retrieveCategory(item.getCategoryId());
        Helper.addAttribute(request,AttributeEnum.ITEM, item);
        Helper.addAttribute(request,AttributeEnum.CATEGORY, category);
        Helper.setSessionIfNotExists(request,AttributeEnum.CART_ITEMS, 0);
        Helper.setSessionIfNotExists(request,AttributeEnum.TOTAL_PRICE, "0");

        request.getRequestDispatcher(JspEnum.PRODUCT.getJsp()).forward(request, response);
    }
}
