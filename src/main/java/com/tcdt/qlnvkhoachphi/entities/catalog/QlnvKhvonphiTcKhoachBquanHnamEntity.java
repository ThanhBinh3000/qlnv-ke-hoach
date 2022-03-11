package com.tcdt.qlnvkhoachphi.entities.catalog;

import lombok.Data;

@Data
public class QlnvKhvonphiTcKhoachBquanHnamEntity {
	Long id;
	Long qlnvKhvonphiId;
	Long stt;
	String maCucDtnnKvuc;
	Long kphiBquanCoDmucThocTx;
	Long kphiBquanCoDmucThocLd;
	Long kphiBquanCoDmucGaoTx;
	Long kphiBquanCoDmucGaoLd;
	Long kphiBquanCoDmucTong;
	Long kphiBquanChuaDmucTong;
	Long tongCong;

	Object lstTcKhoachBquanTbiVtuHnams;
}
