package com.projectlos.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Family member relationship")
public enum Relationship {
    SPOUSE("SPOUSE", "Spouse"),
    CHILD("CHILD", "Child"),
    PARENT("PARENT", "Parent"),
    SIBLING("SIBLING", "Sibling"),
    OTHER("OTHER", "Other");

    private final String code;
    private final String description;

    Relationship(String code, String description) {
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
    public static Relationship fromCode(String code) {
        for (Relationship relationship : Relationship.values()) {
            if (relationship.code.equalsIgnoreCase(code)) {
                return relationship;
            }
        }
        throw new IllegalArgumentException("Invalid relationship code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
