package com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReq {
	Long id;
	String maNoiDung;
	String maNhom;
	Long tongSo;
	
	ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviReq> lstPaGiaoSoKiemTraTcNsnnCtietDvi;
}
