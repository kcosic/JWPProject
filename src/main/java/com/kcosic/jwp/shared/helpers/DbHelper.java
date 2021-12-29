package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.dal.Dal;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.model.entities.*;
import jdk.jfr.Category;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DbHelper {
    private DbHelper() {
    }

    public static List<CustomerEntity> retrieveAllCustomers() {
        Dal dal = new Dal();
        return dal.retrieveAll(CustomerEntity.class).toList();
    }

    public static CustomerEntity retrieveCustomerByEmail(String email) throws EntityNotFoundException {
        Dal dal = new Dal();

        var data = dal.retrieveAll(CustomerEntity.class).filter((customer) -> customer.getEmail() != null);
        var filteredUser = data.filter((customer) -> customer.getEmail().equals(email)).findFirst();
        if (filteredUser.isEmpty()) {
            throw new EntityNotFoundException("Customer with given email doesn't exist.");
        }
        return filteredUser.get();
    }

    public static CustomerEntity retrieveCustomerById(int id) throws EntityNotFoundException {
        Dal dal = new Dal();
        var data = dal.retrieveById(CustomerEntity.class, id);
        if (data == null) {
            throw new EntityNotFoundException("Customer with given ID doesn't exist.");
        }
        return data;
    }

    public static CustomerEntity createCustomer(CustomerEntity customer) {
        var dal = new Dal();
        return dal.create(CustomerEntity.class, customer);
    }

    public static boolean doesEmailExist(String email) {
        Dal dal = new Dal();

        return dal
                .retrieveAll(CustomerEntity.class)
                .filter((customer)->customer.getEmail() != null)
                .anyMatch(
                        (customer) -> customer.getEmail().equals(email)
                );
    }


    public static List<ItemEntity> retrieveItems() {
        Dal dal = new Dal();
        var data = dal.retrieveAll(ItemEntity.class);
        return data.toList();
    }

    public static List<ItemEntity> retrieveItems(String query){
        Dal dal = new Dal();
        var data = dal.retrieveAll(ItemEntity.class)
                .filter(item-> item.getName().contains(query) || item.getManufacturer().contains(query));
        return data.toList();
    }

    public static ItemEntity retrieveItem(Integer id) {
        Dal dal = new Dal();

        var data = dal.retrieveById(ItemEntity.class, id);

        return data;
    }

    public static String addToCart(int customerId, ItemEntity item, boolean getTotalPrice) throws EntityNotFoundException {
        Dal dal = new Dal();

        var customer = retrieveCustomerById(customerId);

        if (customer.getCurrentCartId() == null) {
            var newCart = new CartEntity();
            newCart.setCustomerId(customerId);
            newCart = dal.create(CartEntity.class, newCart);
            customer.setCurrentCartId(newCart.getId());
            customer = dal.update(CustomerEntity.class, customer);
        }
        final var cartId = customer.getCurrentCartId();
        var optCartItem = dal
                .retrieveAll(CartItemEntity.class)
                .filter(cartItemEntity ->
                        cartItemEntity.getItemId().equals(item.getId()) &&
                                cartItemEntity.getCartId().equals(cartId))
                .findFirst();
        if (optCartItem.isPresent()) {
            var cartItem = optCartItem.get();
            cartItem.setCount(cartItem.getCount() + 1);
            dal.update(CartItemEntity.class, cartItem);
        } else {
            var newCartItem = new CartItemEntity();
            newCartItem.setCartId(customer.getCurrentCartId());
            newCartItem.setItemId(item.getId());
            newCartItem.setCount(1);
            dal.create(CartItemEntity.class, newCartItem);
        }

        if (getTotalPrice) {
            return calculateTotalPrice(retrieveCartItems(cartId));
        }
        return null;
    }


    public static List<CartItemEntity> retrieveCartItems(Integer cartId) {
        Dal dal = new Dal();
        return dal.retrieveAll(CartItemEntity.class)
                .filter(cartItemEntity -> cartItemEntity
                        .getCartId()
                        .equals(cartId)).toList();
    }

    public static String calculateTotalPrice(List<CartItemEntity> currentCartItems) {
        BigDecimal price = BigDecimal.valueOf(0);
        for (var cartItem : currentCartItems) {
            var item = retrieveItem(cartItem.getItemId());
            price = price.add(
                    BigDecimal.valueOf(cartItem.getCount())
                            .multiply(item.getPrice()));
        }

        return String.format("%,.2f", price.setScale(2, RoundingMode.DOWN));
    }

    public static CategoryEntity retrieveCategory(Integer categoryId) {
        Dal dal = new Dal();
        return dal.retrieveById(CategoryEntity.class, categoryId);
    }

    public static List<CategoryEntity> retrieveCategories() {
        Dal dal = new Dal();
        var data = dal.retrieveAll(CategoryEntity.class);
        return data.toList();
    }

    public static int cartQuantity(Integer cartId) {
        if(cartId == null){
            return 0;
        }
        return retrieveCartItems(cartId).size();
    }

    public static void updateCustomer(CustomerEntity customer) {
        Dal dal = new Dal();
        dal.update(CustomerEntity.class, customer);
    }
}
