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
@Table(name = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM")
@Data
public class QlnvKhvonphiKhoachQuyTienLuongHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_DVI")
	private String maDvi;

	@Column(name = "BCHE_GIAO_N1")
	private Long bcheGiao_N1;

	@Column(name = "DU_KIEN_SO_CCVC_CO_MAT_N1")
	private Long duKienSoCcvcCoMat_N1;

	@Column(name = "DU_KIEN_SO_HDONG_CO_MAT_N1")
	private Long duKienSoHdongCoMat_N1;

	@Column(name = "BCHE_CHUA_SDUNG")
	private Long bcheChuaSdung;

	@Column(name = "TONGQUY_LUONG_PCAP_THEO_LUONG_CCVC_HDLD")
	private Long tongquyLuongPcapTheoLuongCcvcHdld;

	@Column(name = "TONG_SO")
	private Long tongSo;

	@Column(name = "CCVC_DU_KIEN_CO_MAT_N1_CONG")
	private Long ccvcDuKienCoMat_N1_Cong;

	@Column(name = "CCVC_DU_KIEN_CO_MAT_N1_LUONG_THEO_BAC")
	private Long ccvcDuKienCoMat_N1_LuongTheoBac;

	@Column(name = "CCVC_DU_KIEN_CO_MAT_N1_PCAP")
	private Long ccvcDuKienCoMat_N1_Pcap;

	@Column(name = "CCVC_DU_KIEN_CO_MAT_N1_CKDG")
	private Long ccvcDuKienCoMat_N1_Ckdg;

	@Column(name = "QUY_LUONG_TANG_NANG_BAC_LUONG_N1")
	private Long quyLuongTangNangBacLuong_N1;

	@Column(name = "BCHE_CHUA_SDUNG_CONG")
	private Long bcheChuaSdungCong;

	@Column(name = "BCHE_CHUA_SDUNG_LUONG")
	private Long bcheChuaSdungLuong;

	@Column(name = "BCHE_CHUA_SDUNG_CKDG")
	private Long bcheChuaSdungCkdg;

	@Column(name = "QUY_LUONG_PCAP_THEO_HDLD")
	private Long quyLuongPcapTheoHdld;



}



