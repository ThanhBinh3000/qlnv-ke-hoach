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
@Table(name = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO")
@Data
public class QlnvKhvonphiNxuatDtqgHnamThocGao implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "LUONG_THOC_XUAT")
	private Long luongThocXuat;

	@Column(name = "LUONG_THOC_NHAP")
	private Long luongThocNhap;

	@Column(name = "LUONG_GAO_XUAT")
	private Long luongGaoXuat;

	@Column(name = "LUONG_GAO_NHAP")
	private Long luongGaoNhap;


}
