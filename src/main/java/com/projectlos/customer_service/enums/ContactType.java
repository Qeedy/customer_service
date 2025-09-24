package com.projectlos.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contact type")
public enum ContactType {
    PHONE("PHONE", "Phone"),
    EMAIL("EMAIL", "Email");

    private final String code;
    private final String description;

    ContactType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static ContactType fromCode(String code) {
        for (ContactType type : ContactType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid contact type code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
