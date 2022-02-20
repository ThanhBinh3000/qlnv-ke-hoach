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
@Table(name = "QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N")
@Data
public class QlnvKhvonphiNcauChiNsnnGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_NDUNG")
	private String maNdung;
	
	@Column(name = "TEN_DAN")
	private String tenDan;

	@Column(name = "MA_LOAI_CHI")
	private String maLoaiChi;

	@Column(name = "MA_KHOAN_CHI")
	private String maKhoanChi;

	@Column(name = "MA_MUC_CHI")
	private String maMucChi;

	@Column(name = "MA_LOAI_CHI_TX")
	private String maLoaiChiTx;

	@Column(name = "DTOAN_N")
	private Long dtoanN;

	@Column(name = "UOC_THIEN_N")
	private Long uocThienN;

	@Column(name = "TRAN_CHI_N1")
	private Long tranChiN1;

	@Column(name = "NCAU_CHI_N1")
	private Long ncauChiN1;

	@Column(name = "CLECH_TRAN_CHI_VS_NCAU_CHI_N1")
	private Long clechTranChiVsNcauChiN1;

	@Column(name = "SSANH_NCAU_N_VOI_N_1")
	private Long ssanhNcauNVoiN1;

	@Column(name = "TRAN_CHI_N2")
	private Long tranChiN2;

	@Column(name = "NCAU_CHI_N2")
	private Long ncauChiN2;

	@Column(name = "CLECH_TRAN_CHI_VS_NCAU_CHI_N2")
	private Long clechTranChiVsNcauChiN2;

	@Column(name = "TRAN_CHI_N3")
	private Long tranChiN3;

	@Column(name = "NCAU_CHI_N3")
	private Long ncauChiN3;

	@Column(name = "CLECH_TRAN_CHI_VS_NCAU_CHI_N3")
	private Long clechTranChiVsNcauChiN3;

}

