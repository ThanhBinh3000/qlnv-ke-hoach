package com.tcdt.qlnvkhoach.enums;

public enum PAGTrangThaiEnum {

    DU_THAO("00", "Dự Thảo", "Dự Thảo"),

    CHO_DUYET_TP("01", "Chờ duyệt – TP", "Chờ duyệt – TP"),

    CHO_DUYET_LĐC("02", "Chờ duyệt – LĐC", "Chờ duyệt – LĐC"),

    DA_DUYET("03", "Đã duyệt", "Đã duyệt"),

    TU_CHOI_TP("04", "Từ chối - TP", "Từ chối - TP"),

    TU_CHOI_LĐC("05", "Từ chối - LĐC", "Từ chối - LĐC"),
    ;

    private final String id;

    private final String ten;

    private final String trangThaiDuyet;


    PAGTrangThaiEnum(String id, String ten, String trangThaiDuyet) {
        this.id = id;
        this.ten = ten;
        this.trangThaiDuyet = trangThaiDuyet;
    }

    public String getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getTrangThaiDuyet() {
        return trangThaiDuyet;
    }

    public static String getTrangThaiDuyetById(String id){
        for (PAGTrangThaiEnum status : PAGTrangThaiEnum.values()) {
            if (status.getId().equals(id))
                return status.getTrangThaiDuyet();
        }

        return null;
    }
}
