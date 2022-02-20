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
@Table(name = "QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N")
@Data
public class QlnvKhvonphiTcNcauKhoachDtxdGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_DAN")
	private String tenDan;

	@Column(name = "MA_LOAI_KHOACH")
	private String maLoaiKhoach;

	@Column(name = "MA_KHOI_DAN")
	private String maKhoiDan;

	@Column(name = "MA_DDIEM_XDUNG")
	private String maDdiemXdung;

	@Column(name = "DDIEM_MO_TK")
	private String ddiemMoTk;

	@Column(name = "MA_NGANH_KTE")
	private String maNganhKte;

	@Column(name = "NLUC_TKE")
	private String nlucTke;

	@Column(name = "NAM_KC_TTE")
	private Long namKcTte;

	@Column(name = "NAM_HT_TTE")
	private Long namHtTte;

	@Column(name = "QD_DUYET_DAN_DTU_SONGAYTHANG")
	private String qdDuyetDanDtuSongaythang;

	@Column(name = "QD_DUYET_DAN_DTU_TONG_VON")
	private Long qdDuyetDanDtuTongVon;

	@Column(name = "QD_DCHINH_DAN_DTU_SONGAYTHANG")
	private String qdDchinhDanDtuSongaythang;

	@Column(name = "QD_DCHINH_DAN_DTU_TONG_VON")
	private Long qdDchinhDanDtuTongVon;

	@Column(name = "QD_DUYET_TK_DTOAN_SONGAYTHANG")
	private String qdDuyetTkDtoanSongaythang;

	@Column(name = "QD_DUYET_TK_DTOAN_TONG")
	private Long qdDuyetTkDtoanTong;

	@Column(name = "QD_DUYET_TK_DTOAN_XL")
	private Long qdDuyetTkDtoanXl;

	@Column(name = "QD_DUYET_TK_DTOAN_TB")
	private Long qdDuyetTkDtoanTb;

	@Column(name = "QD_DUYET_TK_DTOAN_CX")
	private Long qdDuyetTkDtoanCx;

	@Column(name = "KLTH_CAP_DEN_3006_SONGAYTHANG")
	private String klthCapDen3006Songaythang;

	@Column(name = "KLTH_CAP_DEN_3006_NSTT")
	private Long klthCapDen3006Nstt;

	@Column(name = "KLTH_CAP_DEN_3006_DTOAN_CHI_TX")
	private Long klthCapDen3006DtoanChiTx;

	@Column(name = "KLTH_CAP_DEN_3006_QUYKHAC")
	private Long klthCapDen3006Quykhac;

	@Column(name = "KLTH_CAP_DEN_3112_SONGAYTHANG")
	private String klthCapDen3112Songaythang;

	@Column(name = "KLTH_CAP_DEN_3112_NSTT")
	private Long klthCapDen3112Nstt;

	@Column(name = "KLTH_CAP_DEN_3112_DTOAN_CHI_TX")
	private Long klthCapDen3112DtoanChiTx;

	@Column(name = "KLTH_CAP_DEN_3112_QUYKHAC")
	private Long klthCapDen3112Quykhac;

	@Column(name = "NCAU_VON_N1")
	private Long ncauVonN1;

	@Column(name = "NCAU_VON_N2")
	private Long ncauVonN2;

	@Column(name = "NCAU_VON_N3")
	private Long ncauVonN3;

	@Column(name = "GHICHU")
	private String ghiChu;

}


