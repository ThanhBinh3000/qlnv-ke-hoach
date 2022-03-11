package com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;

import lombok.Data;
@Data
public class QlnvKhvonphiBcaoReq {
	Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã báo cáo không được vượt quá 20 ký tự")
	@Pattern(regexp = "^(BC)+\\d+$", message = "Sai định dạng")
	String maBcao;
	Long namBcao;
	Long thangBcao;
	Long namHienHanh;
	String trangThai;
	String maLoaiBcao;
	String maDviTien;
	Long maDvi;
	Long maDviCha;
	Long dotBcao;
	Long loaiBaoCao;

	String listIdDeletes;

	String listIdFiles;

	ArrayList<FileDinhKemReq> fileDinhKems;

	ArrayList<Object> lstCTietBCao;
}
