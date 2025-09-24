package com.projectlos.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer status")
public enum CustomerStatus {
    PENDING("PENDING", "Pending"),
    ACTIVE("ACTIVE", "Active"),
    INACTIVE("INACTIVE", "Inactive"),
    SUSPENDED("SUSPENDED", "Suspended"),
    REJECTED("REJECTED", "Rejected");

    private final String code;
    private final String description;

    CustomerStatus(String code, String description) {
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
    public static CustomerStatus fromCode(String code) {
        for (CustomerStatus status : CustomerStatus.values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid customer status code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
