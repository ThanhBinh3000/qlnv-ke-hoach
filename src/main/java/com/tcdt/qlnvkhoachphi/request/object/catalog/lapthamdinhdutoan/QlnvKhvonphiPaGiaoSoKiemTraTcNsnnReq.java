package com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq {
	
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	Long id;
	
	@NotNull(message = "Không được để trống maPa")
	@Size(max = 20, message = "Mã phương án không được vượt quá 20 ký tự")
	@Pattern(regexp = "^(PA.)+\\d+$", message = "Sai định dạng")
	String maPa;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã đơn vị không được vượt quá 20 ký tự")
	String maDvi;
	
	@NotNull(message = "Không được để trống namPa")
	Long namPa;
	
	@NotNull(message = "Không được để trống namHienHanh")
	Long namHienHanh;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
	String trangThai;
	
	@NotNull(message = "Không được để trống maDviTien")
	@Size(max = 20, message = "Mã báo cáo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Sai định dạng")
	String maDviTien;
	
	String ListIdDeletes;
	
	ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReq> lstPaGiaoSoKiemTraTcNsnnCtiet;
}
