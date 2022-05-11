package com.tcdt.qlnvkhoach.request.search.catalog.quyetdinhdutoanchi;

import com.tcdt.qlnvkhoach.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiQdGiaoDtChiNsnnSearchReq extends BaseRequest{
	
	String soQd;
	String ngayTaoTu;
	String ngayTaoDen;
	String noiQd;
}
