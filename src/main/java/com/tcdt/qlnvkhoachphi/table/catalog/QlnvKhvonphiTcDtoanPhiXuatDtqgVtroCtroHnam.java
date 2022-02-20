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
@Table(name = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM")
@Data
public class QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_CUC_DTNN_KVUC")
	private String maCucDtnnKvuc;

	@Column(name = "LUONG")
	private Long luong;

	@Column(name = "CPHI_XUAT_CO_DMUC")
	private Long cphiXuatCoDmuc;

	@Column(name = "CPHI_XUAT_CHUA_DMUC")
	private Long cphiXuatChuaDmuc;

	@Column(name = "THANH_TIEN_CO_DMUC")
	private Long thanhTienCoDmuc;

	@Column(name = "THANH_TIEN_KHONG_DMUC")
	private Long thanhTienKhongDmuc;

	@Column(name = "THANH_TIEN_CONG")
	private Long thanhTienCong;

}


