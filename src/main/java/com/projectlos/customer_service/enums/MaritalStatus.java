package com.projectlos.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer marital status")
public enum MaritalStatus {
    SINGLE("SINGLE", "Single"),
    MARRIED("MARRIED", "Married"),
    DIVORCED("DIVORCED", "Divorced"),
    WIDOWED("WIDOWED", "Widowed"),
    SEPARATED("SEPARATED", "Separated");

    private final String code;
    private final String description;

    MaritalStatus(String code, String description) {
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
    public static MaritalStatus fromCode(String code) {
        for (MaritalStatus status : MaritalStatus.values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid marital status code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
