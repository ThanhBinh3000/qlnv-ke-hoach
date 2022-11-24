package com.tcdt.qlnvkhoach.enums;

public enum DxDcKeHoachNamChiTieuEnum {

    XUAT_TRONG_NAM("00"),
    NHAP_TRONG_NAM("01");
    private final String value;

    DxDcKeHoachNamChiTieuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
