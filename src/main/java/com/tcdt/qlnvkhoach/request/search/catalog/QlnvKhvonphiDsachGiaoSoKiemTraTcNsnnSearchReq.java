package com.tcdt.qlnvkhoach.request.search.catalog;

import com.tcdt.qlnvkhoach.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq extends BaseRequest {
	String namGiao;
	String ngayTaoTu;
	String ngayTaoDen;
	String maDviTao;
	String maDviNhan;
	String maGiao;
	String maPa;
}
