package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.CartEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.management.Attribute;
import java.io.IOException;

@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        processCartGetRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        processCartPostRequest(request, response);
    }

    private void processCartGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);
        var cart = DbHelper.retrieveCurrentCart(customer.getId());
        Helper.addAttribute(request, AttributeEnum.CART, cart);
        request.getRequestDispatcher(JspEnum.CART.getJsp()).forward(request, response);
    }

    private void processCartPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);
        var cart = DbHelper.retrieveCurrentCart(customer.getId());

        var clearCart = request.getParameter(AttributeEnum.CLEAR_CART.toString());

        if(!Helper.isNullOrEmpty(clearCart)){
            cart.clearCart();
        }

        var rawItemIdForRemoval = request.getParameter(AttributeEnum.REMOVE_ITEM.toString());
        if(!Helper.isNullOrEmpty(rawItemIdForRemoval)){
            cart.removeCartItem(Integer.parseInt(rawItemIdForRemoval));
        }

        var rawCount = request.getParameter(AttributeEnum.COUNT.toString());
        var rawCartItemId = request.getParameter(AttributeEnum.CART_ITEM_ID.toString());

        if(!Helper.isNullOrEmpty(rawCount) && !Helper.isNullOrEmpty(rawCartItemId)){
            var count = Integer.parseInt(rawCount);
            var cartItemId = Integer.parseInt(rawCartItemId);

            if(count > 0){

                var optCartItem = cart.getCartItems().stream().filter(cartItemEntity -> cartItemEntity.getId() == cartItemId).findFirst();
                if(optCartItem.isPresent()){
                    optCartItem.get().setCount(count);
                    cart.updateCartItem(optCartItem.get());
                }
            }
            else {
                cart.removeCartItem(cartItemId);
            }
        }

        Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE,(
                cart != null ? cart.getTotalPriceString() : "0"));
        var cartItems = DbHelper.cartQuantity(customer.getId());
        Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);


        Helper.addAttribute(request, AttributeEnum.CART, cart);
        request.getRequestDispatcher(JspEnum.CART.getJsp()).forward(request, response);
    }

    private void removeCartItem(CartEntity cart, String rawId) {
        var cartItemId = Integer.parseInt(rawId);
        cart.getCartItems().removeIf(cartItemEntity -> cartItemEntity.getId() == cartItemId);
        DbHelper.updateCart(cart);
    }
}
