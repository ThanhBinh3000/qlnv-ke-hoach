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
@Table(name = "QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N")
@Data
public class QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_CTRINH")
	private String tenCtrinh;

	@Column(name = "NGUON_VON")
	private String nguonVon;

	@Column(name = "LOAI_CT")
	private String loaiCt;
	
	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;

	@Column(name = "TGIAN_KC")
	private Long tgianKc;

	@Column(name = "TGIAN_HT")
	private Long tgianHt;

	@Column(name = "DTOAN_DUOC_DUYET_CQUAN_QD")
	private String dtoanDuocDuyetCquanQd;

	@Column(name = "DTOAN_DUOC_DUYET_TONG_GTRI")
	private Long dtoanDuocDuyetTongGtri;

	@Column(name = "THIEN_KLUONG_N_DTOAN_KPHI_DEN_3006N")
	private Long thienKluongNDtoanKphiDen3006n;

	@Column(name = "THIEN_KLUONG_N_UOC_THIEN_CA_NAM_N")
	private Long thienKluongNUocThienCaNamN;

	@Column(name = "TTOAN_KLUONG_N_DA_TTOAN_DEN_3006N")
	private Long ttoanKluongNDaTtoanDen3006n;

	@Column(name = "TTOAN_KLUONG_N_UOC_THIEN_CA_NAM_N")
	private Long ttoanKluongNUocThienCaNamN;

	@Column(name = "TONG_SO_N1")
	private Long tongSoN1;

	@Column(name = "TTOAN_NAM_TRUOC_CHUYEN_SANG_N1")
	private Long ttoanNamTruocChuyenSangN1;

	@Column(name = "PSINH_MOI_N1")
	private Long psinhMoiN1;

	@Column(name = "TONG_SO_N2")
	private Long tongSoN2;

	@Column(name = "TTOAN_NAM_TRUOC_CHUYEN_SANG_N2")
	private Long ttoanNamTruocChuyenSangN2;

	@Column(name = "PSINH_MOI_N2")
	private Long psinhMoiN2;

	@Column(name = "TONG_SO_N3")
	private Long tongSoN3;

	@Column(name = "TTOAN_NAM_TRUOC_CHUYEN_SANG_N3")
	private Long ttoanNamTruocChuyenSangN3;

	@Column(name = "PSINH_MOI_N3")
	private Long psinhMoiN3;

}



