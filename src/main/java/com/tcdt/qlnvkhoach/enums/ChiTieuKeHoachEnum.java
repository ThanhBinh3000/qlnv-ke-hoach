package com.tcdt.qlnvkhoach.enums;

public enum ChiTieuKeHoachEnum {
    QD("00"),
    QD_DC("01");
    private final String value;

    ChiTieuKeHoachEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
