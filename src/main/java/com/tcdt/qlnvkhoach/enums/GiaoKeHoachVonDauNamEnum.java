package com.tcdt.qlnvkhoach.enums;

public enum GiaoKeHoachVonDauNamEnum {

    DU_THAO("00", "Dự Thảo", "Dự Thảo"),

    BAN_HANH("29", "Ban Hành", "Ban Hành"),
    ;

    private final String id;

    private final String ten;

    private final String trangThaiDuyet;


    GiaoKeHoachVonDauNamEnum(String id, String ten, String trangThaiDuyet) {
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

    public static String getTentById(String id){
        for (GiaoKeHoachVonDauNamEnum status : GiaoKeHoachVonDauNamEnum.values()) {
            if (status.getId().equals(id))
                return status.getTen();
        }

        return null;
    }

    public static String getTrangThaiDuyetById(String id){
        for (GiaoKeHoachVonDauNamEnum status : GiaoKeHoachVonDauNamEnum.values()) {
            if (status.getId().equals(id))
                return status.getTrangThaiDuyet();
        }

        return null;
    }
}
