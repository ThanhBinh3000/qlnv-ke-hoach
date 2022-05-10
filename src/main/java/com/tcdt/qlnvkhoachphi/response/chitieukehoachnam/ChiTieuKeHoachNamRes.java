package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoachphi.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	private Long qdGocId;
	private String soQdGoc;
	private String ghiChu;
	private String canCu;

	private List<KeHoachLuongThucDuTruRes> khLuongThuc = new ArrayList<>();
	private List<KeHoachMuoiDuTruRes> khMuoiDuTru = new ArrayList<>();
	private List<KeHoachVatTuRes> khVatTu = new ArrayList<>();

	private List<FileDinhKemChung> fileDinhKems = new ArrayList<>();
}
