package com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class TongHopDchinhDuToanChiNsnnReq {
	@NotNull(message = "Không được để trống")
	String maDvi;

	@NotNull(message = "Không được để trống")
	String namHienTai;
}
