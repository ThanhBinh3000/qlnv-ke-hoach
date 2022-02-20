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
@Table(name = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM")
@Data
public class QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_CUC_DTNN_KVUC")
	private String maCucDtnnKvuc;

	@Column(name = "NXUAT_THOC_LUONG_XUAT")
	private Long nxuatThocLuongXuat;

	@Column(name = "NXUAT_THOC_LUONG_NHAP")
	private Long nxuatThocLuongNhap;

	@Column(name = "NXUAT_THOC_LUONG_DMUC_PHI_XUAT")
	private Long nxuatThocLuongDmucPhiXuat;

	@Column(name = "NXUAT_THOC_LUONG_DMUC_PHI_NHAP")
	private Long nxuatThocLuongDmucPhiNhap;

	@Column(name = "NXUAT_THOC_LUONG_TTIEN")
	private Long nxuatThocLuongTtien;

	@Column(name = "NXUAT_GAO_LUONG_XUAT")
	private Long nxuatGaoLuongXuat;

	@Column(name = "NXUAT_GAO_LUONG_NHAP")
	private Long nxuatGaoLuongNhap;

	@Column(name = "NXUAT_GAO_LUONG_DMUC_PHI_XUAT")
	private Long nxuatGaoLuongDmucPhiXuat;

	@Column(name = "NXUAT_GAO_LUONG_DMUC_PHI_NHAP")
	private Long nxuatGaoLuongDmucPhiNhap;

	@Column(name = "NXUAT_GAO_LUONG_TTIEN")
	private Long nxuatGaoLuongTtien;

}


