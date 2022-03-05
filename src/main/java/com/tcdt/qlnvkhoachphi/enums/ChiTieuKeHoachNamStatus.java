package com.tcdt.qlnvkhoachphi.enums;

public enum ChiTieuKeHoachNamStatus {
	MOI_TAO("00", "Mới tạo"),
	CHO_DUYET("01", "Chờ duyệt"),
	DA_DUYET("02", "Đã duyệt"),
	TU_CHOI("03", "Từ chối");

	private final String id;
	private final String ten;

	ChiTieuKeHoachNamStatus(String id, String ten) {
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
		for (ChiTieuKeHoachNamStatus status : ChiTieuKeHoachNamStatus.values()) {
			if (status.getId().equals(id))
				return status.getTen();
		}

		return null;
	}
}
