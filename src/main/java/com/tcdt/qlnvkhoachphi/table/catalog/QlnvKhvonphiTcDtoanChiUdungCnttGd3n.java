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
@Table(name = "QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N")
@Data
public class QlnvKhvonphiTcDtoanChiUdungCnttGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "NDUNG")
	private String nDung;

	@Column(name = "MA_LOAI_KHOACH")
	private String maLoaiKhoach;

	@Column(name = "MA_LOAI_DAN")
	private String maLoaiDan;

	@Column(name = "TONG_DTOAN_SL")
	private Long tongDtoanSl;

	@Column(name = "TONG_DTOAN_GTRI")
	private Long tongDtoanGtri;

	@Column(name = "THIEN_NAM_TRUOC")
	private Long thienNamTruoc;

	@Column(name = "DTOAN_THIEN_N_CB")
	private Long dtoanThienNCb;

	@Column(name = "DTOAN_THIEN_N_TH")
	private Long dtoanThienNTh;

	@Column(name = "DTOAN_THIEN_N1_CB")
	private Long dtoanThienN1Cb;

	@Column(name = "DTOAN_THIEN_N1_TH")
	private Long dtoanThienN1Th;

	@Column(name = "DTOAN_THIEN_N2_CB")
	private Long dtoanThienN2Cb;

	@Column(name = "DTOAN_THIEN_N2_TH")
	private Long dtoanThienN2Th;

	@Column(name = "DTOAN_THIEN_N3_CB")
	private Long dtoanThienN3Cb;

	@Column(name = "DTOAN_THIEN_N3_TH")
	private Long dtoanThienN3Th;
	
	@Column(name = "GHI_CHU")
	private Long ghiChu;

}


