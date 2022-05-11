package com.tcdt.qlnvkhoach.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StatusReq {
	@NotNull(message = "Không được để trống")
	Long id;
	@NotNull(message = "Không được để trống")
	String trangThai;
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String lyDoTuChoi;
}
