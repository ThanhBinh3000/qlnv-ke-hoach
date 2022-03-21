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
@Table(name = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM")
@Data
public class QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM_SEQ")
	private Long id;

	@Column(name = "NXUAT_DTQG_HNAM_ID")
	private Long nxuatDtqgHnamId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_VTU_TBI")
	private String maVtuTbi;

	@Column(name = "SL")
	private Long sL;

}



