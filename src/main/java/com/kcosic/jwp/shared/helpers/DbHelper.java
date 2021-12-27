package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.dal.Dal;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.model.entities.CartEntity;
import com.kcosic.jwp.shared.model.entities.CartItemEntity;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import com.kcosic.jwp.shared.model.entities.ItemEntity;

import java.math.BigDecimal;
import java.util.List;

public class DbHelper {
    private DbHelper(){
    }

    public static List<CustomerEntity> retrieveAllCustomers(){
        Dal dal = new Dal();
        return dal.retrieveAll(CustomerEntity.class).toList();
    }

    public static CustomerEntity retrieveCustomerByEmail(String email) throws EntityNotFoundException {
        Dal dal = new Dal();

        var data = dal.retrieveAll(CustomerEntity.class);
        var filteredUser = data.filter((customer)-> customer.getEmail().equals(email)).findFirst();
        if(filteredUser.isEmpty()){
            throw new EntityNotFoundException("Customer with given email doesn't exist.");
        }
        return filteredUser.get();
    }

    public static CustomerEntity retrieveCustomerById(int id) throws EntityNotFoundException {
        Dal dal = new Dal();
        var data = dal.retrieveById(CustomerEntity.class, id);
        if(data == null){
            throw new EntityNotFoundException("Customer with given ID doesn't exist.");
        }
        return data;
    }

    public static CustomerEntity createCustomer(CustomerEntity customer){
        var dal = new Dal();
        return dal.create(CustomerEntity.class, customer);
    }

    public static boolean doesEmailExist(String email) {
        Dal dal = new Dal();

        return dal
                .retrieveAll(CustomerEntity.class)
                .anyMatch(
                        (customer)->customer.getEmail().equals(email)
                );
    }


    public static List<ItemEntity> retrieveItems() {
        Dal dal = new Dal();

        var data = dal.retrieveAll(ItemEntity.class);

        return data.toList();
    }

    public static BigDecimal addToCart(int customerId, ItemEntity item, boolean getTotalPrice) throws EntityNotFoundException {
        Dal dal = new Dal();

        var customer = retrieveCustomerById(customerId);

        if(customer.getCurrentCartId() == null){
            var newCart = new CartEntity();
            newCart.setCustomerId(customerId);
            newCart = dal.create(CartEntity.class,newCart);
            customer.setCurrentCartId(newCart.getId());
        }

        var optionalCartItem = customer.getCurrentCart()
                .getCartItems()
                .stream()
                .filter(
                        cartItemEntity ->
                                cartItemEntity.getItemId().equals(item.getId())
                ).findFirst();

        if(optionalCartItem.isPresent()){
            var cartItem = optionalCartItem.get();
            cartItem.setCount(cartItem.getCount() + 1);
            dal.update(CartItemEntity.class, cartItem);
        }
        else{
            var newCartItem = new CartItemEntity();
            newCartItem.setCartId(customer.getCurrentCartId());
            newCartItem.setItemId(item.getId());
            newCartItem.setCount(1);
            dal.create(CartItemEntity.class, newCartItem);
        }

        if(getTotalPrice){
            return calculateTotalPrice(customer.getCurrentCart());
        }
        return null;
    }

    private static BigDecimal calculateTotalPrice(CartEntity currentCart) {
        BigDecimal price = BigDecimal.valueOf(0);
        for (var cartItem : currentCart.getCartItems()) {
            price = price.add(
                    BigDecimal.valueOf(cartItem.getCount())
                            .multiply(cartItem.getItem().getPrice()));
        }

        return price;
    }
}
