package com.tcdt.qlnvkhoachphi.request.object.catalog.ketquathuchienvonphi;

import java.util.ArrayList;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;

import lombok.Data;
@Data
public class QlnvKhvonphiBcaoKquaThienReq {
	Long id;
	String maDvi;
	String maBcao;
	String maLoaiBcao;
	Long namBcao;
	Long dotBcao;
	String trangThai;

	String listIdDeletes;

	String listIdFiles;

	ArrayList<FileDinhKemReq> fileDinhKems;

	ArrayList<Object> lstCTietBCao;
}
