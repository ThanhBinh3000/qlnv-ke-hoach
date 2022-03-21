package com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnReq {
	
	@NotNull(message = "Không được để trống id")
	Long id;
	
	@NotNull(message = "Không được để trống maGiao")
	@Size(max = 20, message = "Mã giao không được vượt quá 20 ký tự")
	@Pattern(regexp = "^(M)+\\d+$", message = "Sai định dạng")
	String maGiao;
	
	@NotNull(message = "Không được để trống maPa")
	@Size(max = 20, message = "Mã phương án không được vượt quá 20 ký tự")
	@Pattern(regexp = "^(PA.)+\\d+$", message = "Sai định dạng")
	String maPa;
	
	@NotNull(message = "Không được để trống maDviTao")
	@Size(max = 20, message = "Mã đơn vị tạo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	String maDviTao;
	
	@NotNull(message = "Không được để trống maDviNhan")
	@Size(max = 20, message = "Mã đơn vị nhận không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Yêu cầu nhập số")
	String maDviNhan;
	
	@NotNull(message = "Không được để trống namGiao")
	@Size(max = 4, message = "Năm giao không được vượt quá 4 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Sai định dạng")
	Long namGiao;
	
	@NotNull(message = "Không được để trống maNoiDung")
	@Size(max = 20, message = "Mã nội dung không được vượt quá 20 ký tự")
	@Pattern(regexp = "^d+$", message = "Sai định dạng")
	String maNoiDung;
	
	@NotNull(message = "Không được để trống maNhom")
	@Size(max = 20, message = "Mã nhóm không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Sai định dạng")
	String maNhom;
	
	@NotNull(message = "Không được để trống soDuocGiao")
	@Pattern(regexp = "^\\d+$", message = "Sai định dạng")
	Long soDuocGiao;
	
	@NotNull(message = "Không được để trống maDviTien")
	@Size(max = 20, message = "Mã báo cáo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^\\d+$", message = "Sai định dạng")
	String maDviTien;
}
