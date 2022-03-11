package com.tcdt.qlnvkhoachphi.table.catalog.denghicapvon;

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
@Table(name = "QLNV_KHVONPHI_DNGHI_CAPVON")
@Data
public class QlnvKhvonphiDnghiCapvon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DNGHI_CAPVON_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DNGHI_CAPVON_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DNGHI_CAPVON_SEQ")
	private Long id;

	String maDvi;
	@Transient
	String tenDvi;
	String maCanCu;
	String maHdong;
	String soCv;
	Date ngay;
	String trangThai;
	@Transient
	String tenTrangThai;

	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;
}


