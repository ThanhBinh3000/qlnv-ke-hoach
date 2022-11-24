package com.tcdt.qlnvkhoach.enums;

public enum DxDcKeHoachNamStatusTongCucEnum {
    CHUA_XU_LY("00", "Chưa xử lý"),
    DA_XU_LY("01", "Đã xử lý"),
    TU_CHOI("02", "Từ Chối");

    private final String id;
    private final String ten; // trang thai cuc
    DxDcKeHoachNamStatusTongCucEnum(String id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public String getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public static String getTenById(String id) {
        for (DxDcKeHoachNamStatusTongCucEnum status : DxDcKeHoachNamStatusTongCucEnum.values()) {
            if (status.getId().equals(id))
                return status.getTen();
        }

        return null;
    }
}
