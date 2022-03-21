package com.tcdt.qlnvkhoachphi.request.object.catalog;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class FileDinhKemReq {
	Long id;

	@NotNull(message = "Không được để trống")
	String fileName;

	@NotNull(message = "Không được để trống")
	String fileSize;

	@NotNull(message = "Không được để trống")
	String fileUrl;
}
