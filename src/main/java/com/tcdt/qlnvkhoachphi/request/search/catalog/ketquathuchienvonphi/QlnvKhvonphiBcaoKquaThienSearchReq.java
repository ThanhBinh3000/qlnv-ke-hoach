package com.tcdt.qlnvkhoachphi.request.search.catalog.ketquathuchienvonphi;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class QlnvKhvonphiBcaoKquaThienSearchReq extends BaseRequest {
	String maDvi;
	String ngayTaoTu;
	String ngayTaoDen;
	String trangThai;
	Long namBcao;
	Long dotBcao;
	String maLoaiBcao;
	String maBcao;
}
