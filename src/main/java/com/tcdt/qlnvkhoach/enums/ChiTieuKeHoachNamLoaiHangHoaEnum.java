package com.tcdt.qlnvkhoach.enums;

public enum ChiTieuKeHoachNamLoaiHangHoaEnum {
    LUONG_THUC("00"),
    MUOI("01"),
    VAT_TU("02");
    private final String value;

    ChiTieuKeHoachNamLoaiHangHoaEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
