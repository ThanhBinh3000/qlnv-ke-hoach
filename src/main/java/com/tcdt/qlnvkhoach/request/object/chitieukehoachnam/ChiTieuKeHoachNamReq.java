package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChiTieuKeHoachNamReq {
	@ApiModelProperty(notes = "Bắt buộc nhập đối với update")
	private Long id;
	@NotBlank(message = "Số quyết định Không được để trống")
	@Size(max = 20, message = "Số quyết định không được vượt quá 20 ký tự.")
	private String soQuyetDinh;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKy;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayHieuLuc;

	@NotNull(message = "Năm kế hoạch không được để trống")
	private Integer namKeHoach;

	@NotBlank(message = "Trích yếu Không được để trống")
	@Size(max = 500, message = "Trích yếu không được vượt quá 500 ký tự.")
	private String trichYeu;

	private String ghiChu;

	private List<KeHoachLuongThucDuTruReq> khLuongThuc = new ArrayList<>();
	private List<KeHoachMuoiDuTruReq> khMuoi = new ArrayList<>();
	private List<KeHoachNhapVatTuThietBiReq> khVatTu = new ArrayList<>();
	private List<FileDinhKemReq> fileDinhKemReqs = new ArrayList<>();

	// Chi tieu ke hoach nam
	private List<FileDinhKemReq> canCus = new ArrayList<>();
}
