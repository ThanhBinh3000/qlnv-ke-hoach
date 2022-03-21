package com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan;

import java.util.ArrayList;

import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviReq;

import lombok.Data;

@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp {
	Long id;
	String maNoiDung;
	String maNhom;
	Long tongSo;
	
	ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviResp> lstPaGiaoSoKiemTraTcNsnnCtietDvi;
}
