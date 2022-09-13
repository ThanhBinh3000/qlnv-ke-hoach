package com.tcdt.qlnvkhoach.enums;

public enum ChiTieuKeHoachNamStatusEnum {
	DU_THAO("00", "Dự Thảo", "Dự Thảo"),
	CHO_DUYET_LDV("18", "Chờ duyệt - LDV", "Chờ duyệt - LDV"),
	TU_CHOI_LDV("19", "Từ chối - LDV", "Từ chối - LDV"),
	DA_DUYET_LDV("20", "Đã duyệt - LDV", "Đã duyệt - LDV"),
	CHO_DUYET_TP("01", "Chờ duyệt - TP", "Chờ duyệt - TP"),
	TU_CHOI_TP("02", "Từ chối - TP", "Từ chối - TP"),
	CHO_DUYET_LDC("03", "Chờ duyệt - LDC", "Chờ duyệt - LDC"),
	TU_CHOI_LDC("04", "Từ chối - LDC", "Từ chối - LDC"),
	DA_DUYET_LDC("05", "Đã duyệt - LDC", "Đã duyệt - LDC"),
	BAN_HANH("29", "Ban Hành", "Ban Hành");
	private final String id;
	private final String ten;
	private final String trangThaiDuyet;

	ChiTieuKeHoachNamStatusEnum(String id, String ten, String trangThaiDuyet) {
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

	public static String getTenById(String id) {
		for (ChiTieuKeHoachNamStatusEnum status : ChiTieuKeHoachNamStatusEnum.values()) {
			if (status.getId().equals(id))
				return status.getTen();
		}

		return null;
	}

	public static String getTrangThaiDuyetById(String id) {
		for (ChiTieuKeHoachNamStatusEnum status : ChiTieuKeHoachNamStatusEnum.values()) {
			if (status.getId().equals(id))
				return status.getTrangThaiDuyet();
		}

		return null;
	}
}
