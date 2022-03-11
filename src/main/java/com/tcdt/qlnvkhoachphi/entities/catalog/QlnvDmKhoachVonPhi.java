package com.tcdt.qlnvkhoachphi.entities.catalog;

import java.io.Serializable;

import lombok.Data;

@Data
public class QlnvDmKhoachVonPhi implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	String loaiDm;
	String tenDm;
	String thongTin;
	String levelDm;
	String trangThai;
	String ngayTao;
	String nguoiTao;
	String ngaySua;
	String nguoiSua;

	private QlnvDmKhoachVonPhi parent;

}
