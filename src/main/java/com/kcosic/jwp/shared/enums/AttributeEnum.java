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
    USER_DATA("userData"),
    ERROR_MESSAGE("errorMessage"),
    ITEMS("items"),
    ITEM_ID("itemId"),
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
    USERS("users"),
    CATEGORIES("categories"),
    CARTS("carts"),
    ALL_CARTS("allCarts");


    private final String label;

    private AttributeEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
