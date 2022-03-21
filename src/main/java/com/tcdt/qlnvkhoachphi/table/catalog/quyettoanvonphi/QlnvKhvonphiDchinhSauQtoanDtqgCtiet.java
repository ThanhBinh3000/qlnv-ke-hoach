package com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi;

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
@Table(name = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_CTIET")
@Data
public class QlnvKhvonphiDchinhSauQtoanDtqgCtiet implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_CTIET_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_DCHINH_ID")
	private Long qlnvKhvonphiDchinhId;

	@Column(name = "MA_DVI_THIEN")
	private String maDviThien;

	@Column(name = "NAM")
	private Long nam;

	@Column(name = "TONG_GIA_QTOAN")
	private Long tongGiaQtoan;

	@Column(name = "TONG_TIEN_DCHINH")
	private Long tongTienDchinh;

	@Column(name = "GHICHU")
	private String ghiChu;

}
