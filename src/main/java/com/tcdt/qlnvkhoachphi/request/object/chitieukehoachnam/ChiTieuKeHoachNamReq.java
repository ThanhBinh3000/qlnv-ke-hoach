package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChiTieuKeHoachNamReq {
	private Long id;
	private String soQuyetDinh;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKy;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayHieuLuc;
	private Integer namKeHoach;
	private String trichYeu;
	private List<KeHoachLuongThucDuTruReq> khLuongThuc = new ArrayList<>();
	private List<KeHoachMuoiDuTruReq> khMuoi = new ArrayList<>();
	private List<KeHoachNhapVatTuThietBiReq> khVatTu = new ArrayList<>();
}
