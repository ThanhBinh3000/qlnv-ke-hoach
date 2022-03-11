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
@Table(name = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_VTU_HNAM")
@Data
public class QlnvKhvonphiNcauXuatDtqgVtroVtuHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_VTU_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_VTU_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_VTU_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_VTU_TBI")
	private Long maVtuTbi;

	@Column(name = "SL")
	private Long sL;

	@Column(name = "MA_DVI_VTU_TBI")
	private Long maDviVtuTbi;
}


