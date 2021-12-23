package com.kcosic.jwp.shared.enums;

public enum LocalStorageEnum {
    USER("user"),
    CART("cart");


    private final String label;

    private LocalStorageEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
