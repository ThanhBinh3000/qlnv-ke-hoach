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
@Table(name = "QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM")
@Data
public class QlnvKhvonphiTcKhoachBquanHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_CUC_DTNN_KVUC")
	private String maCucDtnnKvuc;

	@Column(name = "KPHI_BQUAN_CO_DMUC_THOC_TX")
	private Long kphiBquanCoDmucThocTx;

	@Column(name = "KPHI_BQUAN_CO_DMUC_THOC_LD")
	private Long kphiBquanCoDmucThocLd;

	@Column(name = "KPHI_BQUAN_CO_DMUC_GAO_TX")
	private Long kphiBquanCoDmucGaoTx;

	@Column(name = "KPHI_BQUAN_CO_DMUC_GAO_LD")
	private Long kphiBquanCoDmucGaoLd;

	@Column(name = "KPHI_BQUAN_CO_DMUC_TONG")
	private Long kphiBquanCoDmucTong;

	@Column(name = "KPHI_BQUAN_CHUA_DMUC_TONG")
	private Long kphiBquanChuaDmucTong;

	@Column(name = "TONG_CONG")
	private Long tongCong;

}


