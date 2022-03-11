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
@Table(name = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2")
@Data
public class QlnvKhvonphiBcaoThinhSdungDtoanPl2 implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_BCAO_ID")
	private Long qlnvKhvonphiBcaoId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_NDUNG")
	private String maNdung;

	@Column(name = "DTOAN_SDUNG_NAM_TCONG")
	private Long dtoanSdungNamTcong;

	@Column(name = "DTOAN_SDUNG_NAM_NGUON_NSNN")
	private Long dtoanSdungNamNguonNsnn;

	@Column(name = "DTOAN_SDUNG_NAM_NGUON_SN")
	private Long dtoanSdungNamNguonSn;

	@Column(name = "DTOAN_SDUNG_NAM_NGUON_QUY")
	private Long dtoanSdungNamNguonQuy;

	@Column(name = "GIAI_NGAN_THANG_TCONG")
	private Long giaiNganThangTcong;

	@Column(name = "GIAI_NGAN_THANG_TCONG_TLE")
	private Long giaiNganThangTcongTle;

	@Column(name = "GIAI_NGAN_THANG_NGUON_NSNN")
	private Long giaiNganThangNguonNsnn;

	@Column(name = "GIAI_NGAN_THANG_NGUON_NSNN_TLE")
	private Long giaiNganThangNguonNsnnTle;

	@Column(name = "GIAI_NGAN_THANG_NGUON_SN")
	private Long giaiNganThangNguonSn;

	@Column(name = "GIAI_NGAN_THANG_NGUON_SN_TLE")
	private Long giaiNganThangNguonSnTle;

	@Column(name = "GIAI_NGAN_THANG_NGUON_QUY")
	private Long giaiNganThangNguonQuy;

	@Column(name = "GIAI_NGAN_THANG_NGUON_QUY_TLE")
	private Long giaiNganThangNguonQuyTle;

	@Column(name = "LUY_KE_GIAI_NGAN_TCONG")
	private Long luyKeGiaiNganTcong;

	@Column(name = "LUY_KE_GIAI_NGAN_TCONG_TLE")
	private Long luyKeGiaiNganTcongTle;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_NSNN")
	private Long luyKeGiaiNganNguonNsnn;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_NSNN_TLE")
	private Long luyKeGiaiNganNguonNsnnTle;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_SN")
	private Long luyKeGiaiNganNguonSn;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_SN_TLE")
	private Long luyKeGiaiNganNguonSnTle;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_QUY")
	private Long luyKeGiaiNganNguonQuy;

	@Column(name = "LUY_KE_GIAI_NGAN_NGUON_QUY_TLE")
	private Long luyKeGiaiNganNguonQuyTle;

}
