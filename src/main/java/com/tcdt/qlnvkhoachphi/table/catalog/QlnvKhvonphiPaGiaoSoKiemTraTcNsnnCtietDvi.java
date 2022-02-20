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
@Table(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_DVI")
@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_DVI_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_DVI_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_DVI_SEQ")
	private Long id;

//	@Column(name = "QLNV_KHVONPHI_PA_CTIET_ID")
//	private Long qlnvKhvonphiPaCtietId;

	@Column(name = "KHUVUC_ID")
	private String khuvucId;

	@Column(name = "SO_TRANCHI")
	private Long soTranchi;
}


