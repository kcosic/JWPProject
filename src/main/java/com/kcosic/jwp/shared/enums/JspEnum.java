package com.kcosic.jwp.shared.enums;

/**
 * Contains all JSP-s
 */
public enum JspEnum {
    LOGIN("login.jsp", "/login"),
    REGISTER("register.jsp","/register"),
    PRODUCT("product.jsp","/product"),
    ACCOUNT("account.jsp","/account"),
    CART("cart.jsp","/cart"),
    PRODUCTS("products.jsp","/products"),
    PAYMENT("payment.jsp", "/payment"), PAYMENT_SUCCESS("paymentSuccess.jsp", "/paymentSuccess"), PAYMENT_ERROR("paymentError.jsp", "/paymentError");


    private final String jsp;
    private final String url;

    JspEnum(String jsp, String url) {
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
