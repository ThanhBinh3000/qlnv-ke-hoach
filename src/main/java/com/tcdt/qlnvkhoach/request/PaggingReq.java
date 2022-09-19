package com.tcdt.qlnvkhoach.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaggingReq {
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "20")
	Integer limit;
	@NotNull(message = "Không được để trống")
	@PositiveOrZero(message = "Trang tìm kiếm phải >= 0")
	@ApiModelProperty(example = "0")
	Integer page;

	String orderBy;

	String orderType;

	public PaggingReq(Integer limit, Integer page) {
		this.limit = limit;
		this.page = page;
	}
}