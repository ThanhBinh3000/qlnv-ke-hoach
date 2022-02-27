package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.nhaptrongnam.NhapTrongNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.tondaunam.TonDauNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.tonkhocuoinam.TonKhoCuoiNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.xuattrongnam.XuatTrongNamRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeHoachLuongThucDuTruRes {
	private String cucDTNNKhuVuc;
	private TonDauNamRes tonDauNam;
	private NhapTrongNamRes nhapTrongNam;
	private XuatTrongNamRes xuatTrongNam;
	private TonKhoCuoiNamRes tonKhoCuoiNam;
}
