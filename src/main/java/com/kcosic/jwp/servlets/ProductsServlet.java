package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.ItemEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "ProductsServlet", value = "/products")
public class ProductsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        processProductsGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        var isLoggedOut = request.getParameter(AttributeEnum.LOGOUT.toString()) != null;
        if(isLoggedOut){
            handleLogoutRequest(request, response);
            processProductsGetRequest(request, response);
            return;
        }
        processProductsPostRequest(request, response);
    }

    private void processProductsPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var customer = getOrCreateCustomer(request);
        var itemId = Integer.parseInt(request.getParameter(AttributeEnum.ITEM_ID.toString()));
        var search = request.getParameter(AttributeEnum.SEARCH.toString());
        var categoryIdRaw = request.getParameter(AttributeEnum.CATEGORY_ID.toString());
        var categories = DbHelper.retrieveCategories();

        Collection<ItemEntity> data;
        if(!Helper.isNullOrEmpty(categoryIdRaw)){
            var categoryId = Integer.parseInt(categoryIdRaw);
            var optSelectedCategory = categories.stream().filter(categoryEntity -> categoryEntity.getId() == categoryId).findFirst();
            //noinspection OptionalGetWithoutIsPresent
            data = search != null ? filterCategoryItems(search,optSelectedCategory.get().getItems()) : optSelectedCategory.get().getItems();
            Helper.addAttribute(request,AttributeEnum.ITEMS, data);
            Helper.addAttribute(request, AttributeEnum.CATEGORY_NAME, optSelectedCategory.get().getName());
            Helper.addAttribute(request, AttributeEnum.CATEGORY_ID, optSelectedCategory.get().getId());
        }
        else {
            data = search != null ? DbHelper.retrieveItems(search, true) : DbHelper.retrieveItems(true);
            Helper.addAttribute(request,AttributeEnum.ITEMS, data);
            Helper.addAttribute(request, AttributeEnum.CATEGORY_ID, null);
        }

        try {
            var optItem = data.stream().filter((item)->item.getId().equals(itemId)).findFirst();
            if(optItem.isEmpty()) {
                throw new EntityNotFoundException("Item not found with that ID");
            }
            var totalPrice = DbHelper.addToCart(
                    customer.getId(),
                    optItem.get(),
                    true);

            var cartItems = DbHelper.cartQuantity(customer.getId());
            Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE, totalPrice);
            Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);
        } catch (EntityNotFoundException e) {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        }
        request.getRequestDispatcher(JspEnum.PRODUCTS.getJsp()).forward(request, response);
    }

    private void processProductsGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var search = request.getParameter(AttributeEnum.SEARCH.toString());
        var categoryIdRaw = request.getParameter(AttributeEnum.CATEGORY_ID.toString());
        var categories = DbHelper.retrieveCategories();

        var customer = getOrCreateCustomer(request);
        var currentCart = DbHelper.retrieveCurrentCart(customer.getId());
        var cartItems = DbHelper.cartQuantity(customer.getId());

        if(!Helper.isNullOrEmpty(categoryIdRaw)){
            var categoryId = Integer.parseInt(categoryIdRaw);
            var optSelectedCategory = categories.stream().filter(categoryEntity -> categoryEntity.getId() == categoryId).findFirst();
            //noinspection OptionalGetWithoutIsPresent
            var data = search != null ? filterCategoryItems(search,optSelectedCategory.get().getItems()) : optSelectedCategory.get().getItems();
            Helper.addAttribute(request,AttributeEnum.ITEMS, data);
            Helper.addAttribute(request, AttributeEnum.CATEGORY_NAME, optSelectedCategory.get().getName());
            Helper.addAttribute(request, AttributeEnum.CATEGORY_ID, optSelectedCategory.get().getId());

        }
        else {
            var data = search != null ? DbHelper.retrieveItems(search, true) : DbHelper.retrieveItems(true);
            Helper.addAttribute(request,AttributeEnum.ITEMS, data);
            Helper.addAttribute(request, AttributeEnum.CATEGORY_ID, null);

        }

        Helper.setSessionData(request, AttributeEnum.CUSTOMER_DATA, customer);
        Helper.setSessionData(request,AttributeEnum.TOTAL_PRICE,(currentCart != null ? currentCart.getTotalPriceString() : "0"));
        Helper.setSessionData(request,AttributeEnum.CART_ITEMS, cartItems);
        Helper.addAttribute(request,AttributeEnum.SEARCH, search);
        Helper.addAttribute(request,AttributeEnum.CATEGORIES, categories);
        request.getRequestDispatcher(JspEnum.PRODUCTS.getJsp()).forward(request, response);

    }

    private List<ItemEntity> filterCategoryItems(String search, Collection<ItemEntity> items) {
        return items
                .stream()
                .filter(itemEntity ->
                    itemEntity.getName().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)) ||
                    itemEntity.getManufacturer().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)))
                .toList();
    }

}
