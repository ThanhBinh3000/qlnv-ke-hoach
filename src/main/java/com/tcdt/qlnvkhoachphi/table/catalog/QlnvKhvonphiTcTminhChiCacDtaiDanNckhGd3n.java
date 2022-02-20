package com.tcdt.qlnvkhoachphi.table.catalog;

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
@Table(name = "QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N")
@Data
public class QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_DTAI_DAN_NCKH")
	private String tenDtaiDanNckh;

	@Column(name = "MA_DVI")
	private String maDvi;

	@Column(name = "TGIAN_BDAU")
	private Long tgianBdau;

	@Column(name = "TGIAN_KTHUC")
	private Long tgianKthuc;

	@Column(name = "KPHI_TONG_PHI_DUOC_DUYET")
	private Long kphiTongPhiDuocDuyet;

	@Column(name = "KPHI_DA_DUOC_THIEN_DEN_TDIEM_BCAO")
	private Long kphiDaDuocThienDenTdiemBcao;

	@Column(name = "KPHI_DKIEN_BTRI_N1")
	private Long kphiDkienBtriN1;

	@Column(name = "KPHI_DKIEN_BTRI_N2")
	private Long kphiDkienBtriN2;

	@Column(name = "KPHI_DKIEN_BTRI_N3")
	private Long kphiDkienBtriN3;

	@Column(name = "KPHI_THU_HOI")
	private Long kphiThuHoi;

	@Column(name = "TGIAN_THU_HOI")
	private Long tgianThuHoi;

}


