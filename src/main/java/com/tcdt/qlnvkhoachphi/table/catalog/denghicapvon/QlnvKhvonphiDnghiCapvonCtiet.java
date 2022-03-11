package com.tcdt.qlnvkhoachphi.table.catalog.denghicapvon;

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
@Table(name = "QLNV_KHVONPHI_DNGHI_CAPVON_CTIET")
@Data
public class QlnvKhvonphiDnghiCapvonCtiet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DNGHI_CAPVON_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DNGHI_CAPVON_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DNGHI_CAPVON_CTIET_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_DNGHI_CAPVON_ID")
	private Long qlnvKhvonphiDnghiCapvonId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_LOAI_GIA")
	private String maLoaiGia;

	@Column(name = "MA_MAT_HANG")
	private String maMatHang;

	@Column(name = "MA_CLOAI")
	private String maCloai;

	@Column(name = "DON_GIA")
	private Long donGia;

	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;

	@Column(name = "MA_DVI_TIEN")
	private String maDviTien;

	@Column(name = "CHI_PHI_LQUAN")
	private Long chiPhiLquan;

	@Column(name = "SO_LUONG")
	private Long soLuong;

	@Column(name = "MA_KHO")
	private String maKho;

	@Column(name = "MA_NGUON_HANG")
	private String maNguonHang;

	@Column(name = "GIA_TTRUONG")
	private Long giaTtruong;

	@Column(name = "GIA_TDA_TTHIEU")
	private Long giaTdaTthieu;

	@Column(name = "THANH_TIEN")
	private Long thanhTien;

	@Column(name = "MA_DVI_DGNHI")
	private String maDviDgnhi;

	@Column(name = "MA_LOAI_VON")
	private String maLoaiVon;

}
