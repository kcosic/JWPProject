package com.kcosic.jwp.shared.enums;

/**
 * Contains all attributes and parameters used in the application
 */
public enum AttributeEnum {
    LOGIN("login"),
    PASSWORD("password"),
    EMAIL("email"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    ITEM_NAME("itemName"),
    DATE_OF_BIRTH("dateOfBirth"),
    ADDRESS("address"),
    STREET_NAME("streetName"),
    STREET_NUMBER("streetNumber"),
    APT_NUMBER("aptNumber"),
    FLOOR_NUMBER("floorNumber"),
    ZIP_CODE("zipCode"),
    COUNTRY("country"),
    COUNTY("county"),
    CITY("city"),
    CUSTOMER_DATA("customerData"),
    ERROR_MESSAGE("errorMessage"),
    ITEMS("items"),
    ITEM_ID("itemId"),
    MANUFACTURER("manufacturer"),
    ITEM_PRICE("itemPrice"),
    DESCRIPTION("description"),
    IMAGE("image"),
    ACTIVE("active"),
    TOTAL_PRICE("totalPrice"),
    SEARCH("search"),
    SORT_ASC("sortAsc"),
    SORT_DESC("sortDesc"),
    ITEM("item"),
    ID("id"),
    CATEGORY("category"),
    CART_ITEMS("cartItems"),
    LOGOUT("logout"),
    TYPE("type"),
    HAS_ERROR("hasError"),
    DEFAULT_ADDRESS("defaultAddress"),
    ADDRESSES("addresses"),
    CUSTOMERS("customers"),
    CATEGORIES("categories"),
    CARTS("carts"),
    ALL_CARTS("allCarts"),
    PASSED_VIEW("passedView"),
    PASSED_ADMIN_VIEW("passedAdminView"),
    CATEGORY_NAME("categoryName"),
    ROLE_ID("roleId"),
    ROLES("roles"),
    CART("cart"),
    REMOVE_ITEM("removeItem"),
    COUNT("count"),
    CART_ITEM_ID("cartItemId"),
    CLEAR_CART("clearCart"),
    PAYMENT("payment"),
    LOGS("logs"),
    LOG_SEARCH_QUERY("logSearchQuery"),
    CATEGORY_SEARCH_QUERY("categorySearchQuery"),
    ITEM_SEARCH_QUERY("itemSearchQuery"),
    CUSTOMER_SEARCH_QUERY("customerSearchQuery"),
    HISTORY_SEARCH_QUERY("historySearchQuery"),
    ACTION("action"),
    HISTORY_PAGINATION("historyPagination"),
    ITEM_PAGINATION("itemPagination"),
    CUSTOMER_PAGINATION("customerPagination"),
    CATEGORY_PAGINATION("categoryPagination"),
    LOG_PAGINATION("logPagination"), ALL_CATEGORIES("allCategories"), SELECTED_CATEGORY("selectedCategory");

    private final String label;

    AttributeEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
