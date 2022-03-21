package com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG")
@Data
public class QlnvKhvonphiDchinhSauQtoanDtqg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DCHINH_SAU_QTOAN_DTQG_SEQ")
	private Long id;
	String maDvi;
	@Transient
	String tenDvi;
	String soQd;
	String trangThai;
	@Transient
	String tenTrangThai;
	
	@Transient
	String ngayQuyetDinh;
	
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;
}

