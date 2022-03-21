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
@Table(name = "QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N")
@Data
public class QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_LVUC")
	private String maLvuc;

	@Column(name = "MA_MUC_CHI")
	private String maMucChi;

	@Column(name = "MTIEU_NVU")
	private Long mtieuNvu;

	@Column(name = "CS_PHAP_LY_THIEN")
	private Long csPhapLyThien;

	@Column(name = "HDONG_CHU_YEU")
	private Long hdongChuYeu;

	@Column(name = "NGUON_KPHI")
	private Long nguonKphi;

	@Column(name = "TONG_SO")
	private Long tongSo;

	@Column(name = "CHI_CS")
	private Long chiCs;

	@Column(name = "CHI_MOI")
	private Long chiMoi;

	@Column(name = "DTU_PTRIEN")
	private Long dtuPtrien;

	@Column(name = "DTU_PTRIEN_CHI_CS")
	private Long dtuPtrienChiCs;

	@Column(name = "DTU_PTRIEN_CHI_MOI")
	private Long dtuPtrienChiMoi;

	@Column(name = "CHI_TX")
	private Long chiTx;

	@Column(name = "CHI_TX_CHI_CS")
	private Long chiTxChiCs;

	@Column(name = "CHI_TX_CHI_MOI")
	private Long chiTxChiMoi;
}


