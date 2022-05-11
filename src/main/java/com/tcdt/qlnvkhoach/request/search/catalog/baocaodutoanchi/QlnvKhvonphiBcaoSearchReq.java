package com.tcdt.qlnvkhoach.request.search.catalog.baocaodutoanchi;

import com.tcdt.qlnvkhoach.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class QlnvKhvonphiBcaoSearchReq extends BaseRequest {
	String maDvi;
	String ngayTaoTu;
	String ngayTaoDen;
	String trangThai;
	Long namBcao;
	Long thangBcao;
	String maLoaiBcao;
	String maBcao;
	Long dotBcao;
	Long loaiBaoCao;
}
