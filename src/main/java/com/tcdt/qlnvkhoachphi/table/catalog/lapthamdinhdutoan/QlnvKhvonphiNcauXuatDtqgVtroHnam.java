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
@Table(name = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM")
@Data
public class QlnvKhvonphiNcauXuatDtqgVtroHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "LUONG_XUAT_GAO_VTRO")
	private Long luongXuatGaoVtro;

	@Column(name = "LUONG_XUAT_THOC_VTRO")
	private Long luongXuatThocVtro;
 }
