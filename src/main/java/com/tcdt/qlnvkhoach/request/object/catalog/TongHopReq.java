package com.tcdt.qlnvkhoach.request.object.catalog;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TongHopReq {
	@NotNull(message = "Không được để trống")
	String maLoaiBcao;

	@NotNull(message = "Không được để trống")
	String maDvi;
	
	@NotNull(message = "Không được để trống")
	String namHienTai;
}
