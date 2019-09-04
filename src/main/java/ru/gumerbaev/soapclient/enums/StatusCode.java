package ru.gumerbaev.soapclient.enums;

public enum StatusCode {
    OK("00.Result.OK"),
    NOT_FOUND("01.Result.NotFound"),
    ERROR("02.Result.Error");

    private String description;

    StatusCode(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
