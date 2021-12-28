package com.kcosic.jwp.shared.enums;

public enum AttributeEnum {
    LOGIN("login"),
    PASSWORD("password"),
    EMAIL("email"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    DATE_OF_BIRTH("dateOfBirth"),
    ADDRESS("address"),
    STREET_NUMBER("streetNumber"),
    APT_NUMBER("aptNumber"),
    FLOOR_NUMBER("floorNumber"),
    COUNTRY("country"),
    COUNTY("county"),
    CITY("city"),
    USER_DATA("user_data"),
    ERROR_MESSAGE("errorMessage"),
    ITEMS("items"),
    ITEM_ID("itemId"),
    TOTAL_PRICE("totalPrice"),
    SEARCH("search"),
    SORT_ASC("sortAsc"),
    SORT_DESC("sortDesc"),
    ITEM("item"),
    HAS_ERROR("hasError");


    private final String label;

    private AttributeEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
