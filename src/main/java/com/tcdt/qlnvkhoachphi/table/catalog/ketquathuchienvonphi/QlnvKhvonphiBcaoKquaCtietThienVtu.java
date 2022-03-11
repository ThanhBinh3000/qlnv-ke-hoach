package com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi;

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
@Table(name = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_VTU")
@Data
public class QlnvKhvonphiBcaoKquaCtietThienVtu implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_VTU_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_VTU_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_VTU_SEQ")
	private Long id;

//	@Column(name = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_ID")
//	private Long qlnvKhvonphiBcaoKquaCtietThienId;

	@Column(name = "MA_VTU")
	private Long maVtu;

	@Column(name = "SL")
	private Long sL;

	@Column(name = "LOAI_MAT_HANG")
	private String loaiMatHang;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qlnvKhvonphiBcaoKquaCtietThienId")
	@JsonBackReference
	private QlnvKhvonphiBcaoKquaCtietThien bcaoKquaCtietThien;

}
