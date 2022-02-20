package com.tcdt.qlnvkhoachphi.table.catalog;

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
@Table(name = "QLNV_KHVONPHI")
@Data
public class QlnvKhvonphi implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_SEQ")
	private Long id;
	String maBcao;
	Long maDvi;

	@Transient
	String tenDvi;

	String maDviTien;
	String maLoaiBcao;
	Long namBcao;
	Long namHienHanh;
	String trangThai;

	@Transient
	String tenTrangThai;

	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
}
