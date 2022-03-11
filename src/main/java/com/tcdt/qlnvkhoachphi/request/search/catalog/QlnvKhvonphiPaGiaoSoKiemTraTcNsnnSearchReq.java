package com.tcdt.qlnvkhoachphi.request.search.catalog;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq extends BaseRequest {
	String namPa;
	String ngayTaoTu;
	String ngayTaoDen;
	String maDviTao;
	String maPa;
	String trangThai;
	String soCv;
	String soQD;
}
