package com.kcosic.jwp.shared.enums;

public enum ParameterEnum {
    LOGIN("login"),
    PASSWORD("password"),
    HAS_ERROR("hasError");


    private final String label;

    private ParameterEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
