package com.tcdt.qlnvkhoach.request.object.catalog;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tcdt.qlnvkhoach.util.Constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvDmDonviReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã đơn vị không được vượt quá 20 ký tự")
	String maDvi;

	@Size(max = 20, message = "Mã đơn vị cha không được vượt quá 20 ký tự")
	String maDviCha;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên đơn vị không được vượt quá 50 ký tự")
	String tenDvi;

	@Size(max = 5, message = "Mã hành chính không được được vượt quá 5 ký tự")
	String maHchinh;

	@Size(max = 5, message = "Mã tỉnh/thành phố không được vượt quá 5 ký tự")
	String maTinh;

	@Size(max = 5, message = "Mã quận/huyện không được vượt quá 5 ký tự")
	String maQuan;

	@Size(max = 5, message = "Mã phường/xã không được vượt quá 5 ký tự")
	String maPhuong;

	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;

	@Size(max = 20, message = "Cấp đơn vị không được vượt quá 250 ký tự")
	String capDvi;

	@Size(max = 20, message = "Kiểu đơn vị không được vượt quá 250 ký tự")
	String kieuDvi;

	@Size(max = 20, message = "Loại đơn vị không được vượt quá 250 ký tự")
	String loaiDvi;

	@Size(max = 250, message = "Ghi chú không được vượt quá 250 ký tự")
	String ghiChu;

	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
	@ApiModelProperty(example = Constants.HOAT_DONG)
	String trangThai;
}
