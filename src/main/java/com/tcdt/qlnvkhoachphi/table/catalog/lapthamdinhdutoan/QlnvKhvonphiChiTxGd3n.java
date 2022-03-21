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
@Table(name = "QLNV_KHVONPHI_CHI_TX_GD3N")
@Data
public class QlnvKhvonphiChiTxGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_CHI_TX_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_CHI_TX_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_CHI_TX_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_NOI_DUNG")
	private String maNoiDung;

	@Column(name = "MA_NHOM_CHI")
	private String maNhomChi;

	@Column(name = "MA_LOAI_CHI")
	private String maLoaiChi;

	@Column(name = "NAM_HHANH_N")
	private Long namHhanhN;

	@Column(name = "TRAN_CHI_DUOC_TB_N1")
	private Long tranChiDuocTbN1;

	@Column(name = "NCAU_CHI_CUA_DVI_N1")
	private Long ncauChiCuaDviN1;

	@Column(name = "CLECH_TRAN_CHI_VS_NCAU_N1")
	private Long clechTranChiVsNcauN1;

	@Column(name = "TRAN_CHI_DUOC_TB_N2")
	private Long tranChiDuocTbN2;

	@Column(name = "NCAU_CHI_CUA_DVI_N2")
	private Long ncauChiCuaDviN2;

	@Column(name = "CLECH_TRAN_CHI_VS_NCAU_N2")
	private Long clechTranChiVsNcauN2;

	@Column(name = "TRAN_CHI_DUOC_TB_N3")
	private Long tranChiDuocTbN3;

	@Column(name = "NCAU_CHI_CUA_DVI_N3")
	private Long ncauChiCuaDviN3;

	@Column(name = "CLECH_TRAN_CHI_VS_NCAU_N3")
	private Long clechTranChiVsNcauN3;

}
