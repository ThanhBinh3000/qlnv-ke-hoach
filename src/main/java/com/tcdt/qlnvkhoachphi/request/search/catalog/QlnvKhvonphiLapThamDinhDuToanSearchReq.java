package com.tcdt.qlnvkhoachphi.request.search.catalog;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiLapThamDinhDuToanSearchReq extends BaseRequest {
	String namBcao;
	String ngayTaoTu;
	String ngayTaoDen;
	String maLoaiBcao;
	String maDvi;
	String maBcao;
}
