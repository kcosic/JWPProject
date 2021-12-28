package com.kcosic.jwp.shared.enums;

public enum JspEnum {
    LOGIN("login.jsp"),
    REGISTER("register.jsp"),
    PRODUCT("product.jsp"),
    PRODUCTS("products.jsp");


    private final String label;

    private JspEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
