package com.projectlos.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Employment type")
public enum EmploymentType {
    PERMANENT("PERMANENT", "Permanent"),
    CONTRACT("CONTRACT", "Contract"),
    FREELANCE("FREELANCE", "Freelance"),
    PART_TIME("PART_TIME", "Part Time"),
    INTERNSHIP("INTERNSHIP", "Internship"),
    SELF_EMPLOYED("SELF_EMPLOYED", "Self Employed"),
    UNEMPLOYED("UNEMPLOYED", "Unemployed"),
    RETIRED("RETIRED", "Retired");

    private final String code;
    private final String description;

    EmploymentType(String code, String description) {
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
    public static EmploymentType fromCode(String code) {
        for (EmploymentType type : EmploymentType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid employment type code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
