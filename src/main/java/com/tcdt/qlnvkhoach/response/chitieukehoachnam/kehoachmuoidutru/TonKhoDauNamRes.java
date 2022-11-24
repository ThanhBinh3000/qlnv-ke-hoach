package com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TonKhoDauNamRes {
	private Long donViId;
	private String tenDonVi;
	private String maDonVi;
	private String maVatTu;
	// Ton kho dau nam
	private Double tong;
	private List<VatTuNhapRes> tonKho = new ArrayList<>();
}
