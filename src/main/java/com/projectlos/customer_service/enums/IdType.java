package com.projectlos.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of identification document")
public enum IdType {
    KTP("KTP", "Kartu Tanda Penduduk"),
    PASSPORT("PASSPORT", "Passport"),
    SIM("SIM", "Surat Izin Mengemudi"),
    KITAS("KITAS", "Kartu Izin Tinggal Terbatas"),
    KITAP("KITAP", "Kartu Izin Tinggal Tetap"),
    OTHER("OTHER", "Other");

    private final String code;
    private final String description;

    IdType(String code, String description) {
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
    public static IdType fromCode(String code) {
        for (IdType idType : IdType.values()) {
            if (idType.code.equalsIgnoreCase(code)) {
                return idType;
            }
        }
        throw new IllegalArgumentException("Invalid ID type code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
