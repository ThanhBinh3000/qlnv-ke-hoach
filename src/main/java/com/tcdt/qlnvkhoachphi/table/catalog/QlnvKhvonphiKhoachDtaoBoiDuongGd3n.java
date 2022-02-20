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
@Table(name = "QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N")
@Data
public class QlnvKhvonphiKhoachDtaoBoiDuongGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_LOAI")
	private String maLoai;

	@Column(name = "MA_KHOAN")
	private String maKhoan;

	@Column(name = "MA_CHI_MUC")
	private String maChiMuc;

	@Column(name = "SO_LUOT_NGUOI_N1")
	private Long soLuotNguoiN1;

	@Column(name = "THANH_TIEN_N1")
	private Long thanhTienN1;

	@Column(name = "SO_LUOT_NGUOI_N2")
	private Long soLuotNguoiN2;

	@Column(name = "THANH_TIEN_N2")
	private Long thanhTienN2;

	@Column(name = "SO_LUOT_NGUOI_N3")
	private Long soLuotNguoiN3;

	@Column(name = "THANH_TIEN_N3")
	private Long thanhTienN3;
}


