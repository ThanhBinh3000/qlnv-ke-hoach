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
@Table(name = "QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N")
@Data
public class QlnvKhvonphiTcDtoanChiDtqgGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_NDUNG_CHI")
	public String maNdungChi;

	@Column(name = "CHITIET")
	public String chiTiet;

	@Column(name = "KHOACH_CHI_NSNN_N1")
	public Long khoachChiNsnnN1;

	@Column(name = "KHOACH_CHI_NSNN_N2")
	public Long khoachChiNsnnN2;

	@Column(name = "KHOACH_CHI_NSNN_N3")
	public Long khoachChiNsnnN3;

}


