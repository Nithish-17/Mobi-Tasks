package model;

public enum ApiStatus {
    SUCCESS,
    TOKEN_EXPIRED,
    SCHEMA_CHANGED,
    RATE_LIMIT_EXCEEDED,
    PARTIAL_DATA;
}
