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
@Table(name = "QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N")
@Data
public class QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_TBI")
	private String maTbi;

	@Column(name = "N1")
	private Long n1;

	@Column(name = "N2")
	private Long n2;

	@Column(name = "N3")
	private Long n3;

}



