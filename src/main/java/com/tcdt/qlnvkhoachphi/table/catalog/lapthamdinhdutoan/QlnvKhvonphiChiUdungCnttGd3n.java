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
@Table(name = "QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N")
@Data
public class QlnvKhvonphiChiUdungCnttGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "NDUNG")
	private String nDung;

	@Column(name = "LOAI_KHOACH")
	private String loaiKhoach;

	@Column(name = "LOAI_DAN")
	private String loaiDan;

	@Column(name = "TONG_DTOAN_SL")
	private Long tongDtoanSl;

	@Column(name = "TONG_DTOAN_GTRI")
	private Long tongDtoanGtri;

	@Column(name = "THIEN_NAM_TRUOC")
	private Long thienNamTruoc;

	@Column(name = "CB_DTU_N")
	private Long cbDtuN;

	@Column(name = "TH_DTU_N")
	private Long thDtuN;

	@Column(name = "CB_DTU_N1")
	private Long cbDtuN1;

	@Column(name = "TH_DTU_N1")
	private Long thDtuN1;

	@Column(name = "CB_DTU_N2")
	private Long cbDtuN2;

	@Column(name = "TH_DTU_N2")
	private Long thDtuN2;

	@Column(name = "CB_DTU_N3")
	private Long cbDtuN3;

	@Column(name = "TH_DTU_N3")
	private Long thDtuN3;

	@Column(name = "GHI_CHU")
	private String ghiChu;

}


