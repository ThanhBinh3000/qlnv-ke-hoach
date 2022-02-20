package com.tcdt.qlnvkhoachphi.request.object.catalog;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKhvonphiReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã báo cáo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^(BC)+\\d+$", message = "Sai định dạng")
	String maBcao;

	@NotNull(message = "Không được để trống")
//	@Size(max = 20, message = "Mã đơn vị không được vượt quá 20 ký tự")
//	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	Long maDvi;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã đơn vị tiền không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	String maDviTien;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã loại báo cáo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	String maLoaiBcao;

	@NotNull(message = "Không được để trống")
	@Size(max = 4, min = 4, message = "Năm báo cáo không được vượt quá hoặc nhỏ hơn 4 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	String namBcao;

	@NotNull(message = "Không được để trống")
//	@Size(max = 4, min = 4, message = "Năm hiện hành không được vượt quá hoặc nhỏ hơn 4 ký tự")
//	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	Long namHienHanh;

	String listIdDeletes;

	String listIdFiles;

	ArrayList<FileDinhKemReq> fileDinhKems;

	ArrayList<Object> lstCTietBCao;

}
