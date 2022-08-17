package com.tcdt.qlnvkhoach.enums;

public enum PAGTrangThaiTHEnum {

    CHUA_TH("00", "Chưa tổng hơ", "Chưa tổng hợp"),

    DA_TH("01", "Đã tổng hợp", "Đã tổng hợp");


    private final String id;

    private final String ten;

    private final String trangThaiTh;


    PAGTrangThaiTHEnum(String id, String ten, String trangThaiTh) {
        this.id = id;
        this.ten = ten;
        this.trangThaiTh = trangThaiTh;
    }

    public String getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getTrangThaiTH() {
        return trangThaiTh;
    }

    public static String getTrangThaiDuyetById(String id){
        for (PAGTrangThaiTHEnum status : PAGTrangThaiTHEnum.values()) {
            if (status.getId().equals(id))
                return status.getTrangThaiTH();
        }

        return null;
    }
}
