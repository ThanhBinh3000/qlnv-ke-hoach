package com.tcdt.qlnvkhoachphi.entities.catalog;

import lombok.Data;

@Data
public class QlnvKhvonphiKhoachBquanHnamMatHangEntity {

	Long id;
	Long qlnvKhvonphiId;

	Long KphiBquanThocTx;
	Long KphiBquanThocLd;
	Long KphiBquanGaoTx;
	Long KphiBquanGaoLd;

	Object lstCTiet;
}
