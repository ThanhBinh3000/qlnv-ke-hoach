package com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU")
@Data
public class QlnvKhvonphiNxuatDtqgHnamVattu implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_TBI")
	private Long maTbi;
	@Transient
	private String tenMaTbi;

	@Column(name = "SL_NHAP")
	private Long slNhap;

}
