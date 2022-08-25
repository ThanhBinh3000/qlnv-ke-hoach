package com.tcdt.qlnvkhoach.enums;

public enum PAGTongHopTrangThaiEnum {

    DU_THAO("00", "Dự Thảo", "Dự Thảo"),

    CHO_DUYET_TP("01", "Chờ duyệt – TP", "Chờ duyệt – TP"),

    CHO_DUYET_LĐC("02", "Từ chối – TP", "Từ chối – TP"),

    DA_DUYET("03", "Chờ duyệt - LĐC", "Đã duyệt"),

    TU_CHOI_TP("04", "Từ chối - LĐ Cục", "Từ chối - LĐ Cục"),

    TU_CHOI_LĐC("05", "Đã duyệt - LĐ Cục", "Đã duyệt - LĐ Cục"),
    ;

    private final String id;

    private final String ten;

    private final String trangThaiDuyet;


    PAGTongHopTrangThaiEnum(String id, String ten, String trangThaiDuyet) {
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
        for (PAGTongHopTrangThaiEnum status : PAGTongHopTrangThaiEnum.values()) {
            if (status.getId().equals(id))
                return status.getTrangThaiDuyet();
        }

        return null;
    }
}
