package com.tcdt.qlnvkhoach.enums;

public enum LoaiHangHoaEnum {
    LUONG_THUC("00", "Điều chỉnh lương thực dự trữ nhà nước"),
    MUOI("01", "Điều chỉnh muối dự trữ nhà nước"),
    VAT_TU("02", "Điều chỉnh nhập vật tư, thiết bị");
    private final String value;
    private final String ten;

    LoaiHangHoaEnum(String value, String ten) {
        this.value = value;
        this.ten = ten;
    }

    public String getValue() {
        return value;
    }

    public String getTen() {
        return ten;
    }

    public static String getTenById(String id) {
        for (LoaiHangHoaEnum status : LoaiHangHoaEnum.values()) {
            if (status.getValue().equals(id))
                return status.getTen();
        }

        return null;
    }
}
