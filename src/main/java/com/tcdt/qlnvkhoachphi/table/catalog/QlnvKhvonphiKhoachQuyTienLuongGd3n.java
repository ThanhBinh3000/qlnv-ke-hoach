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
@Table(name = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N")
@Data
public class QlnvKhvonphiKhoachQuyTienLuongGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_DVI")
	private String maDvi;

	@Column(name = "TONG_CBO_N")
	private String tongCboN;

	@Column(name = "TONG_BCHE_DUOC_PD_N")
	private String tongBcheDuocPdN;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_N")
	private String tongQuyLuongCoTchatLuongN;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N")
	private String tongQuyLuongCoTchatLuongTheoBcheN;

	@Column(name = "LUONG_CBAN_N")
	private String tuongCbanN;

	@Column(name = "PHU_CAP_N")
	private String phuCapN;

	@Column(name = "CAC_KHOAN_DGOP_N")
	private String cacKhoanDgopN;

	@Column(name = "TONG_CBO_THIEN_N")
	private Long tongCboThienN;

	@Column(name = "TONG_BCHE_DUOC_PD_THIEN_N")
	private Long tongBcheDuocPdThienN;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_THIEN_N")
	private Long tongQuyLuongCoTchatLuongThienN;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_THIEN_N")
	private Long tongQuyLuongCoTchatLuongTheoBcheThienN;

	@Column(name = "LUONG_CBAN_THIEN_N")
	private Long luongCbanThienN;

	@Column(name = "PHU_CAP_THIEN_N")
	private Long phuCapThienN;

	@Column(name = "CAC_KHOAN_DGOP_THIEN_N")
	private Long cacKhoanDgopThienN;

	@Column(name = "TONG_CBO_N1")
	private Long tongCboN1;

	@Column(name = "TONG_BCHE_DUOC_PD_N1")
	private Long tongBcheDuocPdN1;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_N1")
	private Long tongQuyLuongCoTchatLuongN1;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N1")
	private Long tongQuyLuongCoTchatLuongTheoBcheN1;

	@Column(name = "LUONG_CBAN_N1")
	private Long luongCbanN1;

	@Column(name = "PHU_CAP_N1")
	private Long phuCapN1;

	@Column(name = "CAC_KHOAN_DGOP_N1")
	private Long cacKhoanDgopN1;

	@Column(name = "TONG_CBO_N2")
	private Long tongCboN2;

	@Column(name = "TONG_BCHE_DUOC_PD_N2")
	private Long tongBcheDuocPdN2;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_N2")
	private Long tongQuyLuongCoTchatLuongN2;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N2")
	private Long tongQuyLuongCoTchatLuongTheoBcheN2;

	@Column(name = "LUONG_CBAN_N2")
	private Long luongCbanN2;

	@Column(name = "PHU_CAP_N2")
	private Long phuCapN2;

	@Column(name = "CAC_KHOAN_DGOP_N2")
	private Long cacKhoanDgopN2;

	@Column(name = "TONG_CBO_N3")
	private Long tongCboN3;

	@Column(name = "TONG_BCHE_DUOC_PD_N3")
	private Long tongBcheDuocPdN3;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_N3")
	private Long tongQuyLuongCoTchatLuongN3;

	@Column(name = "TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N3")
	private Long tongQuyLuongCoTchatLuongTheoBcheN3;

	@Column(name = "LUONG_CBAN_N3")
	private Long luongCbanN3;

	@Column(name = "PHU_CAP_N3")
	private Long phuCapN3;

	@Column(name = "CAC_KHOAN_DGOP_N3")
	private Long cacKhoanDgopN3;


}


