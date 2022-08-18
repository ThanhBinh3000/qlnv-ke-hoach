package com.tcdt.qlnvkhoach.enums;

public enum KhPagLtQuyetDinhBtcEnum {
  DU_THAO("00", "Dự Thảo"),
  BAN_HANH("01", "Ban Hành");

  private final String id;
  private final String ten;

  KhPagLtQuyetDinhBtcEnum(String id, String ten) {
    this.id = id;
    this.ten = ten;

  }

  public static String getLabelById(String id) {
    for (KhPagLtQuyetDinhBtcEnum e : values()) {
      if (e.id.equals(id)) {
        return e.ten;
      }
    }
    return null;
  }

  public String getId() {
    return id;
  }

  public String getTen() {
    return ten;
  }

}
