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
@Table(name = "QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N")
@Data
public class QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_VBAN")
	private String tenVban;

	@Column(name = "MA_HTHUC_VBAN")
	private String maHthucVban;

	@Column(name = "TGIAN_DKIEN")
	private String tgianDkien;

	@Column(name = "MA_DVI_CHU_TRI")
	private String maDviChuTri;

	@Column(name = "DVI_PHOP")
	private String dviPhop;

	@Column(name = "DTOAN_KPHI")
	private Long dtoanKphi;

	@Column(name = "CCU_LAP_DTOAN")
	private String ccuLapDtoan;


}


