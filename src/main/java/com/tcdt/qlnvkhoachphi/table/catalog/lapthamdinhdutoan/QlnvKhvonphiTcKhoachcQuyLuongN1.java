package com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan;

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
@Table(name = "QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1")
@Data
public class QlnvKhvonphiTcKhoachcQuyLuongN1 implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;


	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_CUC_DTNN_KVUC")
	private String maCucDtnnKvuc;

	@Column(name = "BCHE_GIA0_N1")
	private Long bcheGia0N1;

	@Column(name = "DKIEN_CCVC_CO_MAT_0101N1")
	private Long dkienCcvcCoMat0101n1;

	@Column(name = "DKIEN_HDONG_CO_MAT_0101N1")
	private Long dkienHdongCoMat0101n1;

	@Column(name = "BCHE_CHUA_SDUNG")
	private Long bcheChuaSdung;

	@Column(name = "TONG_QUY_LUONG_PCAP_CKDG_THEO_LUONG_CCVC_HDLD")
	private Long tongQuyLuongPcapCkdgTheoLuongCcvcHdld;

	@Column(name = "TONG_SO")
	private Long tongSo;

	@Column(name = "CCVC_0101N1_CONG")
	private Long ccvc0101n1Cong;

	@Column(name = "CCVC_0101N1_LUONG")
	private Long ccvc0101n1Luong;

	@Column(name = "CCVC_0101N1_PCAP")
	private Long ccvc0101n1Pcap;

	@Column(name = "CCVC_0101N1_CKDG")
	private Long ccvc0101n1Ckdg;

	@Column(name = "QUY_LUONG_TANG_THEM_DO_NANG_BAC_LUONG_CCVC_0101N1")
	private Long quyLuongTangThemDoNangBacLuongCcvc0101n1;

	@Column(name = "BCHE_CHUA_SDUNG_CONG")
	private Long bcheChuaSdungCong;

	@Column(name = "BCHE_CHUA_SDUNG_LUONG_HE_SO234")
	private Long bcheChuaSdungLuongHeSo234;

	@Column(name = "BCHE_CHUA_SDUNG_CKDG")
	private Long bcheChuaSdungCkdg;

	@Column(name = "QUY_LUONG_PCAP_CKDG_THEO_LUONG_HDLD")
	private Long quyLuongPcapCkdgTheoLuongHdld;

}


