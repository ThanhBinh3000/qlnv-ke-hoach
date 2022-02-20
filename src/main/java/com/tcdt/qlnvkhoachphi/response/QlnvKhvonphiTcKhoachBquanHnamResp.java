package com.tcdt.qlnvkhoachphi.response;

import java.util.ArrayList;

import lombok.Data;
@Data
public class QlnvKhvonphiTcKhoachBquanHnamResp {
	Long id;
	Long qlnvKhvonphiId;

	String maCucDtnnKvuc;
	Long kphiBquanCoDmucThocTx;
	Long kphiBquanCoDmucThocLd;
	Long kphiBquanCoDmucGaoTx;
	Long kphiBquanCoDmucGaoLd;
	Long kphiBquanCoDmucTong;
	Long kphiBquanChuaDmucTong;
    Long tongCong;

	ArrayList<Object>  lstTcKhoachBquanTbiVtuHnams;
}
