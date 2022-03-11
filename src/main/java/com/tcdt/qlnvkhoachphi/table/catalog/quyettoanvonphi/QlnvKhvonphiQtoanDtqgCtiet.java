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
@Table(name = "QLNV_KHVONPHI_QTOAN_DTQG_CTIET")
@Data
public class QlnvKhvonphiQtoanDtqgCtiet implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_QTOAN_DTQG_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_QTOAN_DTQG_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_QTOAN_DTQG_CTIET_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_QTOAN_ID")
	private Long qlnvKhvonphiQtoanId;

	@Column(name = "MA_DVI_THIEN")
	private String maDviThien;

	@Column(name = "NAM")
	private Long nam;

	@Column(name = "DU_TOAN")
	private Long duToan;

	@Column(name = "VON_CAP")
	private String vonCap;

	@Column(name = "VON_UNG")
	private String vonUng;

	@Column(name = "CTMT_VON_CAP")
	private Long ctmtVonCap;

	@Column(name = "CTMT_VON_UNG")
	private Long ctmtVonUng;

	@Column(name = "CTMT_TONG")
	private Long ctmtTong;

	@Column(name = "CTMG_VON_CAP")
	private Long ctmgVonCap;

	@Column(name = "CTMG_VON_UNG")
	private Long ctmgVonUng;

	@Column(name = "CTMG_TONG")
	private Long ctmgTong;

	@Column(name = "TCVMLT_VON_CAP")
	private Long tcvmltVonCap;

	@Column(name = "TCVMLT_VON_UNG")
	private Long tcvmltVonUng;

	@Column(name = "TCVMLT_TONG")
	private Long tcvmltTong;

	@Column(name = "GHICHU")
	private String ghiChu;

}
