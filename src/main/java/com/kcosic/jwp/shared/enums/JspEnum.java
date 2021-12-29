package com.kcosic.jwp.shared.enums;

/**
 * Contains all JSP-s
 */
public enum JspEnum {
    LOGIN("login.jsp", ""),
    REGISTER("register.jsp",""),
    PRODUCT("product.jsp",""),
    PRODUCTS("products.jsp","/products");


    private final String jsp;
    private final String url;

    private JspEnum(String jsp, String url) {
        this.jsp = jsp;
        this.url = url;
    }

    public String getJsp() {
        return jsp;
    }
    public String getUrl() {
        return url;
    }
}
