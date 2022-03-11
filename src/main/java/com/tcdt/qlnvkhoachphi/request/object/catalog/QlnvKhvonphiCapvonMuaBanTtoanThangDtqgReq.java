package com.tcdt.qlnvkhoachphi.request.object.catalog;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	Long id;
	
	@NotNull(message = "Không được để trống")
	String maDviLap;
	
	@NotNull(message = "Không được để trống")
	String maDviCap;
	
	@NotNull(message = "Không được để trống")
	String maLoaiGnhan;
	
	@NotNull(message = "Không được để trống")
	String soQd;

	String lyDoTuChoi;

	String listIdFiles;
	
	ArrayList<FileDinhKemReq> fileDinhKems;

	ArrayList<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> lstCTiet;

}
