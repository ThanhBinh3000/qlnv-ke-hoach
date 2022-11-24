package com.tcdt.qlnvkhoach.request.object.catalog;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class NhomNutChucNangReq {
	@NotNull(message = "Không được để trống")
	@Pattern(regexp = "^\\d{1}+$", message = "Sai định dạng")
	String maChucNang;

	@NotNull(message = "Không được để trống")
	Long id;
}
