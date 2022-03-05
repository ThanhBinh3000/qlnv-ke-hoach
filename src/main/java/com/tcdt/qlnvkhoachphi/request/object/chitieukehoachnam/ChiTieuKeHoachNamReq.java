package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChiTieuKeHoachNamReq {
	@ApiModelProperty(notes = "Bắt buộc nhập đối với update")
	private Long id;
	@Size(max = 20, message = "Số quyết định không được vượt quá 20 ký tự.")
	private String soQuyetDinh;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKy;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayHieuLuc;
	private Integer namKeHoach;
	@Size(max = 500, message = "Trích yếu không được vượt quá 500 ký tự.")
	private String trichYeu;
	private List<KeHoachLuongThucDuTruReq> khLuongThuc = new ArrayList<>();
	private List<KeHoachMuoiDuTruReq> khMuoi = new ArrayList<>();
	private List<KeHoachNhapVatTuThietBiReq> khVatTu = new ArrayList<>();


}
