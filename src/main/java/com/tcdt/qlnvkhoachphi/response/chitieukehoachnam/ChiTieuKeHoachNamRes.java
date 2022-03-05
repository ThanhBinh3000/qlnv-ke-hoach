package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChiTieuKeHoachNamRes {
	private Long id;
	private String soQuyetDinh;
	private LocalDate ngayKy;
	private LocalDate ngayHieuLuc;
	private Integer namKeHoach;
	private String trichYeu;
	private String trangThai;
	private String tenTrangThai;
	private Long donViId;
	private LocalDate ngayGuiDuyet;
	private Long nguoiGuiDuyetId;
	private LocalDate ngayPheDuyet;
	private Long nguoiPheDuyetId;
	private String lyDoTuChoi;

	private List<KeHoachLuongThucDuTruRes> khLuongThuc = new ArrayList<>();
	private List<KeHoachMuoiDuTruRes> khMuoiDuTru = new ArrayList<>();
	private List<KeHoachVatTuRes> khVatTu = new ArrayList<>();
}
