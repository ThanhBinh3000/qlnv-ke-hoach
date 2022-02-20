package com.tcdt.qlnvkhoachphi.request.object.catalog;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKhvonphiChiTxGd3nReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã báo cáo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^(BC)+\\d+$", message = "Sai định dạng")
	String maBcao;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	String stt;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã nội dung không được vượt quá 20 ký tự")
	String noi_dung;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã nhóm chi không được vượt quá 20 ký tự")
	String nhom_Chi;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã loại chi không được vượt quá 20 ký tự")
	String loai_Chi;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Năm hiện hành không được vượt quá 20 ký tự")
	String nhh_N;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Trần chi được thông báo năm 1 không được vượt quá 20 ký tự")
	String tcdtb_N1;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Nhu cầu chi của đơn vị năm 1 không được vượt quá 20 ký tự")
	String ncccdv_N1;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Chênh lệch trần chi nhu cầu năm 1 không được vượt quá 20 ký tự")
	String cltcnc_N1;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Trần chi được thông báo năm 2 không được vượt quá 20 ký tự")
	String tcdtb_N2;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Nhu cầu chi của đơn vị năm 2 không được vượt quá 20 ký tự")
	String ncccdv_N2;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Chênh lệch trần chi nhu cầu năm 2 không được vượt quá 20 ký tự")
	String cltcnc_N2;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Trần chi được thông báo năm 3 không được vượt quá 20 ký tự")
	String tcdtb_N3;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Nhu cầu chi của đơn vị năm 3 không được vượt quá 20 ký tự")
	String ncccdv_N3;

	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	@Size(max = 20, message = "Chênh lệch trần chi nhu cầu năm 3 không được vượt quá 20 ký tự")
	String cltcnc_N3;
}
