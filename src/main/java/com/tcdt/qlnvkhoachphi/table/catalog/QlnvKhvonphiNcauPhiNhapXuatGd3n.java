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
@Table(name = "QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N")
@Data
public class QlnvKhvonphiNcauPhiNhapXuatGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_VTU")
	private String maVtu;

	@Column(name = "MA_NHOM_CHI")
	private String maNhomChi;

	@Column(name = "MA_LOAI_CHI")
	private String maLoaiChi;

	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;

	@Column(name = "SL")
	private Long sL;

	@Column(name = "DMUC_PHI_TC")
	private Long dmucPhiTc;

	@Column(name = "THANH_TIEN")
	private Long thanhTien;

}


