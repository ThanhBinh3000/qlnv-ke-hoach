package com.tcdt.qlnvkhoachphi.enums;

public enum ChiTieuKeHoachNamStatus {
	DU_THAO("00", "Dự Thảo"),
	LANH_DAO_DUYET("01", "Lãnh Đạo Duyệt"),
	BAN_HANH("02", "Ban Hành"),
	TU_CHOI("03", "Từ Chối");

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
