package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChiTieuKeHoachNamReq {
	private Long id;
	private String soQuyetDinh;
	private LocalDate ngayKy;
	private LocalDate ngayHieuLuc;
	private Long namKeHoach;
	private String trichYeu;
	private List<KeHoachLuongThucDuTruReq> keHoachLuongThucDuTru = new ArrayList<>();
	private List<KeHoachMuoiDuTruReq> keHoachMuoiDuTru = new ArrayList<>();
	private List<KeHoachNhapVatTuThietBiReq> keHoachNhapVatTuThietBi = new ArrayList<>();
}
