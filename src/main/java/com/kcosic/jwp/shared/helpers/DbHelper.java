package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.dal.Dal;
import com.kcosic.jwp.shared.enums.PaymentEnum;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.model.PaginationResponse;
import com.kcosic.jwp.shared.model.entities.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DbHelper {
    private DbHelper() {
    }

    public static List<CustomerEntity> retrieveCustomers(boolean ignoreGuests) {
        var dal = new Dal();
        var data = dal.retrieveAll(CustomerEntity.class);
        if (ignoreGuests) {
            data = data.filter(customerEntity -> customerEntity.getRole().getId() != 3);
        }
        return data.toList();
    }

    public static List<CustomerEntity> retrieveCustomers(String searchQuery, boolean ignoreGuests) {
        return retrieveCustomers(ignoreGuests).stream().filter(
                customerEntity ->
                        customerEntity.getEmail() != null &&
                                customerEntity
                                        .getEmail()
                                        .toLowerCase(Locale.ROOT)
                                        .contains(searchQuery.toLowerCase(Locale.ROOT))
        ).toList();
    }

    public static CustomerEntity retrieveCustomerByEmail(String email) throws EntityNotFoundException {
        var dal = new Dal();

        var data = dal.retrieveAll(CustomerEntity.class).filter((customer) -> customer.getEmail() != null);
        var filteredUser = data.filter((customer) -> customer.getEmail().equals(email)).findFirst();
        if (filteredUser.isEmpty()) {
            throw new EntityNotFoundException("Customer with given email doesn't exist.");
        }
        return filteredUser.get();
    }

    public static CustomerEntity retrieveCustomerById(int id) {
        var dal = new Dal();

        return dal.retrieveById(CustomerEntity.class, id);
    }

    public static AddressEntity retrieveAddressById(int id) {
        var dal = new Dal();

        return dal.retrieveById(AddressEntity.class, id);
    }

    public static CustomerEntity createCustomer(CustomerEntity customer) {
        var dal = new Dal();
        return dal.create(CustomerEntity.class, customer);
    }

    public static boolean doesEmailExist(String email) {
        var dal = new Dal();

        return dal
                .retrieveAll(CustomerEntity.class)
                .filter((customer) -> customer.getEmail() != null)
                .anyMatch(
                        (customer) -> customer.getEmail().equals(email)
                );
    }


    public static List<ItemEntity> retrieveItems(boolean activeOnly) {
        var dal = new Dal();
        var data = dal.retrieveAll(ItemEntity.class);
        if (activeOnly) {
            data = data.filter(ItemEntity::getIsActive);
        }
        return data.toList();
    }

    public static List<ItemEntity> retrieveItems(String query, boolean activeOnly) {
        var data = retrieveItems(activeOnly)
                .stream()
                .filter(item ->
                        item.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) ||
                                item.getManufacturer().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) ||
                                item.getCategory().getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
                );
        return data.toList();
    }

    public static ItemEntity retrieveItem(Integer id) {
        var dal = new Dal();

        return dal.retrieveById(ItemEntity.class, id);
    }

    public static String addToCart(int customerId, ItemEntity item, boolean getTotalPrice) {
        var dal = new Dal();
        var customer = retrieveCustomerById(customerId);
        var currentCart = retrieveCurrentCart(customerId);
        if (currentCart == null) {
            var newCart = new CartEntity();
            newCart.setCustomer(customer);
            newCart.setDateCreated(Date.valueOf(LocalDate.now()));
            newCart.setTotalPrice(BigDecimal.valueOf(0));
            newCart.setCurrent(true);
            newCart.setPaidWith(PaymentEnum.UNPAID);
            currentCart = dal.create(CartEntity.class, newCart);
        }
        /*var currentCartId = currentCart.getId();
        var optCartItem = dal
                .retrieveAll(CartItemEntity.class)
                .filter(cartItemEntity ->
                        cartItemEntity.getItem().equals(item) &&
                                cartItemEntity.getCart().getId().equals(currentCartId))
                .findFirst();*/
        var optCartItem = currentCart.getCartItems().stream()
                .filter(cartItemEntity ->
                        cartItemEntity.getItem().equals(item))
                .findFirst();
        if (optCartItem.isPresent()) {
            var cartItem = optCartItem.get();
            cartItem.setCount(cartItem.getCount() + 1);
            dal.update(CartItemEntity.class, cartItem);
        } else {
            var newCartItem = new CartItemEntity();
            newCartItem.setCart(currentCart);
            newCartItem.setItem(item);
            newCartItem.setCount(1);
            newCartItem.setPrice(item.getPrice());
            newCartItem.getCart().addCartItem(newCartItem);
        }
        currentCart.setTotalPrice(calculateTotalPrice(currentCart.getCartItems()));
        dal.update(CartEntity.class, currentCart);

        if (getTotalPrice) {
            return currentCart.getTotalPriceString();
        }

        return null;
    }


    public static List<CartItemEntity> retrieveCartItems(Integer cartId) {
        var dal = new Dal();
        return dal.retrieveAll(CartItemEntity.class)
                .filter(cartItemEntity -> cartItemEntity
                        .getCart().getId()
                        .equals(cartId)).toList();
    }

    public static BigDecimal calculateTotalPrice(Collection<CartItemEntity> currentCartItems) {
        var price = BigDecimal.valueOf(0);
        for (var cartItem : currentCartItems) {
            price = price.add(
                    BigDecimal.valueOf(cartItem.getCount())
                            .multiply(cartItem.getItem().getPrice()));
        }

        return price.setScale(2, RoundingMode.DOWN);
    }

    public static CategoryEntity retrieveCategory(Integer categoryId) {
        var dal = new Dal();
        return dal.retrieveById(CategoryEntity.class, categoryId);
    }

    public static List<CategoryEntity> retrieveCategories() {
        var dal = new Dal();
        var data = dal.retrieveAll(CategoryEntity.class);
        return data.toList();
    }

    public static int cartQuantity(int customerId) {
        var cart = retrieveCurrentCart(customerId);
        if (cart == null) {
            return 0;
        }
        var quantity = 0;

        for (var cartItem : cart.getCartItems()) {
            quantity += cartItem.getCount();
        }

        return quantity;
    }

    public static void updateCustomer(CustomerEntity customer) {
        var dal = new Dal();
        dal.update(CustomerEntity.class, customer);
    }

    public static List<AddressEntity> retrieveAddresses(int customerId) {
        var dal = new Dal();
        return dal
                .retrieveAll(AddressEntity.class)
                .filter((addressEntity -> addressEntity.getCustomer().getId() == customerId))
                .toList();
    }

    public static void createAddress(AddressEntity newAddress) {
        var dal = new Dal();
        dal.create(AddressEntity.class, newAddress);
    }

    public static void updateAddress(AddressEntity address) {
        var dal = new Dal();
        dal.update(AddressEntity.class, address);
    }

    public static List<CartEntity> retrieveAllCarts(boolean onlyPurchased) {
        var dal = new Dal();

        var carts = dal.retrieveAll(CartEntity.class);

        if (onlyPurchased) {
            carts = carts.filter((cart) -> cart.getDateOfPurchase() != null);
        }

        return carts.toList();

    }

    public static <T extends BaseEntity> PaginationResponse<T> paginateData(List<T> rawData, int wantedPage, int perPage) {
        var total = rawData.size();
        var pages = (total / perPage) + 1;
        var toSkip = (wantedPage - 1) * perPage;
        var filteredData = rawData.stream().skip(toSkip).limit(perPage).toList();

        return new PaginationResponse<>(
                filteredData,
                pages,
                perPage,
                total,
                pages > wantedPage,
                wantedPage > 1,
                wantedPage);
    }

    public static List<CartEntity> retrieveAllCarts(String searchQuery, boolean onlyPurchased) {
        var dal = new Dal();

        var carts = dal.retrieveAll(CartEntity.class).filter(
                cartEntity ->
                        cartEntity.getCustomer().getRole().getId() != 3
        );

        carts = carts.filter(cartEntity -> cartEntity.getCustomer().toString().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT)) ||
                cartEntity.getCustomer().getEmail().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT)) ||
                (cartEntity.getDateOfPurchase() != null && cartEntity.getDateOfPurchase().toString().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT))));

        if (onlyPurchased) {
            carts = carts.filter((cart) -> cart.getDateOfPurchase() != null);
        }

        return carts.toList();

    }

    public static List<CartEntity> retrieveCarts(Integer customerId, boolean onlyPurchased) {
        var dal = new Dal();

        var carts = dal.retrieveAll(CartEntity.class).filter((cartEntity -> Objects.equals(cartEntity.getCustomerId(), customerId)));

        if (onlyPurchased) {
            carts = carts.filter((cart) -> cart.getDateOfPurchase() != null);
        }

        return carts.toList();
    }

    public static List<CartEntity> retrieveCustomerCarts(int customerId) {
        var dal = new Dal();
        return dal.retrieveAll(CartEntity.class).filter(cartEntity -> cartEntity.getCustomer().getId() == customerId).toList();
    }

    public static CartEntity retrieveCurrentCart(int customerId) {
        var data = retrieveCustomerCarts(customerId).stream().filter(CartEntity::getCurrent).findFirst();
        return data.orElse(null);
    }


    public static void updateCart(CartEntity cartEntity) {
        var dal = new Dal();
        dal.update(CartEntity.class, cartEntity);
    }

    public static void deleteCustomer(CustomerEntity customer) {
        var dal = new Dal();
        deleteCustomerAddresses(customer);
        deleteCustomerCarts(customer);
        dal.delete(CustomerEntity.class, customer);
    }

    private static void deleteCustomerAddresses(CustomerEntity customer) {
        var addresses = retrieveAddresses(customer.getId());
        if (addresses != null) {
            var dal = new Dal();
            dal.bulkDelete(AddressEntity.class, addresses);
        }
    }

    private static void deleteCustomerCarts(CustomerEntity customer) {
        var carts = retrieveCustomerCarts(customer.getId());
        if (carts != null) {
            for (var cart :
                    carts) {
                deleteCartsCartItems(cart);
            }
            var dal = new Dal();
            dal.bulkDelete(CartEntity.class, carts);
        }
    }

    private static void deleteCartsCartItems(CartEntity cart) {
        var cartItems = retrieveCartItems(cart.getId());
        if (cartItems != null) {
            var dal = new Dal();
            dal.bulkDelete(CartItemEntity.class, cartItems);
        }
    }

    public static void updateItem(ItemEntity item) {
        var dal = new Dal();
        dal.update(ItemEntity.class, item);
    }

    public static void createItem(ItemEntity item) {
        var dal = new Dal();
        dal.create(ItemEntity.class, item);
    }

    public static void updateCategory(CategoryEntity category) {
        var dal = new Dal();
        dal.update(CategoryEntity.class, category);
    }

    public static void createCategory(CategoryEntity category) {
        var dal = new Dal();
        dal.create(CategoryEntity.class, category);
    }

    public static RoleEntity retrieveRole(int roleId) {
        var dal = new Dal();
        return dal.retrieveById(RoleEntity.class, roleId);
    }


    public static List<RoleEntity> retrieveRoles() {
        var dal = new Dal();
        return dal
                .retrieveAll(RoleEntity.class)
                .filter((roleEntity -> roleEntity.getId() != 3))
                .toList();
    }

    public static void deleteCartItem(int itemId) {
        var dal = new Dal();
        dal.delete(CartItemEntity.class, dal.retrieveById(CartItemEntity.class, itemId));
    }

    public static AddressEntity retrieveDefaultAddress(CustomerEntity customer) {
        var dal = new Dal();
        var address = dal.retrieveAll(AddressEntity.class).filter(addressEntity -> Objects.equals(addressEntity.getCustomer().getId(), customer.getId()) && addressEntity.getDefault()).findFirst();
        return address.orElse(null);

    }

    public static void addLog(LogEntity log) {
        var dal = new Dal();
        dal.create(LogEntity.class, log);
    }

    public static List<LogEntity> retrieveLogs() {
        var dal = new Dal();
        return dal.retrieveAll(LogEntity.class).toList();
    }

    public static List<LogEntity> retrieveLogs(String query) {
        var dal = new Dal();
        return dal.retrieveAll(LogEntity.class).filter(logEntity ->
                logEntity.getCustomer().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) ||
                        logEntity.getActionName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) ||
                        logEntity.getActionTime().toString().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) ||
                        logEntity.getIpAddress().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
        ).toList();
    }

    public static List<CategoryEntity> retrieveCategories(String categoryQuery) {
        var dal = new Dal();
        return dal.retrieveAll(CategoryEntity.class).filter(
                categoryEntity ->
                        categoryEntity.getName().toLowerCase(Locale.ROOT).contains(categoryQuery.toLowerCase(Locale.ROOT))).toList();
    }

    public static List<ItemEntity> retrieveCategoryItems(int categoryId) {
        var dal = new Dal();
        return dal
                .retrieveById(CategoryEntity.class, categoryId)
                .getItems()
                .stream()
                .filter(ItemEntity::getIsActive)
                .toList();
    }
}
