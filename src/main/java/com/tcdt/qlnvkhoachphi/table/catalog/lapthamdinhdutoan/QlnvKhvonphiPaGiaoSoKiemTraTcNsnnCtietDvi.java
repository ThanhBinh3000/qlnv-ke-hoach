package com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@Column(name = "MA_KHU_VUC")
	private String maKhuVuc;

	@Column(name = "SO_TRAN_CHI")
	private Long soTranChi;
	
	@Column(name = "TRANG_THAI")
	private String trangThai;

	@ManyToOne(fetch = FetchType.LAZY)
//	PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_ID
	@JoinColumn(name = "paGiaoSoKiemTraTcNsnnCtietId", nullable = false)
	@JsonBackReference
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet qlnvNsnnCtiet;
}
