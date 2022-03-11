package com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1")
@Data
public class QlnvKhvonphiBcaoThinhSdungDtoanPl1 implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_BCAO_ID")
	private Long qlnvKhvonphiBcaoId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_NDUNG")
	private String maNdung;

	@Column(name = "MA_NDUNG_PARENT")
	private String maNdungParent;

	@Column(name = "MA_NDUNG_CHILD")
	private String maNdungChild;

	@Column(name = "KPHI_SDUNG_TCONG")
	private Long kphiSdungTcong;

	@Column(name = "KPHI_SDUNG_DTOAN")
	private Long kphiSdungDtoan;

	@Column(name = "KPHI_SDUNG_NGUON_KHAC")
	private Long kphiSdungNguonKhac;

	@Column(name = "KPHI_SDUNG_NGUON_QUY")
	private Long kphiSdungNguonQuy;

	@Column(name = "KPHI_SDUNG_NSTT")
	private Long kphiSdungNstt;

	@Column(name = "KPHI_SDUNG_CK")
	private Long kphiSdungCk;

	@Column(name = "KPHI_CHUYEN_SANG_TCONG")
	private Long kphiChuyenSangTcong;

	@Column(name = "KPHI_CHUYEN_SANG_DTOAN")
	private Long kphiChuyenSangDtoan;

	@Column(name = "KPHI_CHUYEN_SANG_NGUON_KHAC")
	private Long kphiChuyenSangNguonKhac;

	@Column(name = "KPHI_CHUYEN_SANG_NGUON_QUY")
	private Long kphiChuyenSangNguonQuy;

	@Column(name = "KPHI_CHUYEN_SANG_NSTT")
	private Long kphiChuyenSangNstt;

	@Column(name = "KPHI_CHUYEN_SANG_CK")
	private Long kphiChuyenSangCk;

	@Column(name = "DTOAN_GIAO_TCONG")
	private Long dtoanGiaoTcong;

	@Column(name = "DTOAN_GIAO_DTOAN")
	private Long dtoanGiaoDtoan;

	@Column(name = "DTOAN_GIAO_NGUON_KHAC")
	private Long dtoanGiaoNguonKhac;

	@Column(name = "DTOAN_GIAO_NGUON_QUY")
	private Long dtoanGiaoNguonQuy;

	@Column(name = "DTOAN_GIAO_NSTT")
	private Long dtoanGiaoNstt;

	@Column(name = "DTOAN_GIAO_CK")
	private Long dtoanGiaoCk;

	@Column(name = "GIAI_NGAN_THANG_BCAO_TCONG")
	private Long giaiNganThangBcaoTcong;

	@Column(name = "GIAI_NGAN_THANG_BCAO_TCONG_TLE")
	private Long giaiNganThangBcaoTcongTle;

	@Column(name = "GIAI_NGAN_THANG_BCAO_DTOAN")
	private Long giaiNganThangBcaoDtoan;

	@Column(name = "GIAI_NGAN_THANG_BCAO_DTOAN_TLE")
	private Long giaiNganThangBcaoDtoanTle;

	@Column(name = "GIAI_NGAN_THANG_BCAO_NGUON_KHAC")
	private Long giaiNganThangBcaoNguonKhac;

	@Column(name = "GIAI_NGAN_THANG_BCAO_NGUON_KHAC_TLE")
	private Long giaiNganThangBcaoNguonKhacTle;

	@Column(name = "GIAI_NGAN_THANG_BCAO_NGUON_QUY")
	private Long giaiNganThangBcaoNguonQuy;

	@Column(name = "GIAI_NGAN_THANG_BCAO_NGUON_QUY_TLE")
	private Long giaiNganThangBcaoNguonQuyTle;

	@Column(name = "GIAI_NGAN_THANG_BCAO_NSTT")
	private Long giaiNganThangBcaoNstt;

	@Column(name = "GIAI_NGAN_THANG_BCAO_NSTT_TLE")
	private Long giaiNganThangBcaoNsttTle;

	@Column(name = "GIAI_NGAN_THANG_BCAO_CK")
	private Long giaiNganThangBcaoCk;

	@Column(name = "GIAI_NGAN_THANG_BCAO_CK_TLE")
	private Long giaiNganThangBcaoCkTle;

	@Column(name = "LUY_KE_GIAI_NGAN_TCONG")
	private Long luyKeGiaiNganTcong;

	@Column(name = "LUY_KE_GIAI_NGAN_TCONG_TLE")
	private Long luyKeGiaiNganTcongTle;

	@Column(name = "LUY_KE_GIAI_NGAN_DTOAN")
	private Long luyKeGiaiNganDtoan;

	@Column(name = "LUY_KE_GIAI_NGAN_DTOAN_TLE")
	private Long luyKeGiaiNganDtoanTle;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_KHAC")
	private Long luyKeGiaiNganNguonKhac;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_KHAC_TLE")
	private Long luyKeGiaiNganNguonKhacTle;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_QUY")
	private Long luyKeGiaiNganNguonQuy;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_QUY_TLE")
	private Long luyKeGiaiNganNguonQuyTle;

	@Column(name = "LUY_KE_GIAI_NGAN_NSTT")
	private Long luyKeGiaiNganNstt;

	@Column(name = "LUY_KE_GIAI_NGAN_NSTT_TLE")
	private Long luyKeGiaiNganNsttTle;

	@Column(name = "LUY_KE_GIAI_NGAN_CK")
	private Long luyKeGiaiNganCk;

	@Column(name = "LUY_KE_GIAI_NGAN_CK_TLE")
	private Long luyKeGiaiNganCkTle;


}
