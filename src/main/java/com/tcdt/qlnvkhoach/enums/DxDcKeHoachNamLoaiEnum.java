package com.tcdt.qlnvkhoach.enums;

public enum DxDcKeHoachNamLoaiEnum {
    LUONG_THUC("00"),
    MUOI("01"),
    VAT_TU("02");
    private final String value;

    DxDcKeHoachNamLoaiEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
