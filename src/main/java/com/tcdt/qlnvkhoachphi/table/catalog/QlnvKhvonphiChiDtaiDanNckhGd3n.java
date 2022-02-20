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
@Table(name = "QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N")
@Data
public class QlnvKhvonphiChiDtaiDanNckhGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_DTAI_DAN")
	private String tenDtaiDan;

	@Column(name = "MA_DVI")
	private Long maDvi;

	@Column(name = "TG_BDAU")
	private Long   tgBdau;

	@Column(name = "TG_KTHUC")
	private Long   tgKthuc;

	@Column(name = "KPHI_TONG_PHI_DUOC_DUYET")
	private Long   kphiTongPhiDuocDuyet;

	@Column(name = "KPHI_DA_DUOC_BTRI")
	private Long   kphiDaDuocBtri;

	@Column(name = "KPHI_DUOC_THIEN_DEN_THOI_DIEM_BCAO")
	private Long   kphiDuocThienDenThoiDiemBcao;

	@Column(name = "KPHI_DU_KIEN_BTRI_N1")
	private Long   kphiDuKienBtriN1;

	@Column(name = "KPHI_DU_KIEN_BTRI_N2")
	private Long   kphiDuKienBtriN2;

	@Column(name = "KPHI_DU_KIEN_BTRI_N3")
	private Long   kphiDuKienBtriN3;

	@Column(name = "KPHI_THUHOI")
	private Long   kphiThuhoi;

	@Column(name = "KPHI_TGIAN_THUHOI")
	private Long   kphiTgianThuhoi;
}


