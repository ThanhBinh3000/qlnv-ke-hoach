package com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_CTIET")
@Data
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_CTIET_SEQ")
	private Long id;

//	@Column(name = "QLNV_KHVONPHI_CAPVON_ID")
//	private Long qlnvKhvonphiCapvonId;

	@Column(name = "MA_DVI_THIEN")
	private String maDviThien;

	@Column(name = "MA_LOAI_VON")
	private String maLoaiVon;

	@Column(name = "MA_MAT_HANG")
	private String maMatHang;

	@Column(name = "SO_LUONG")
	private Long soLuong;

	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;

	@Column(name = "SO_TIEN")
	private Long soTien;

	@Column(name = "MA_DVI_TIEN")
	private String maDviTien;

	@Column(name = "NGAY_GNHAN")
	private Date ngayGnhan;

	@Column(name = "DON_GIA")
	private Long donGia;

	@Column(name = "THANH_TIEN")
	private Long thanhTien;

	@Column(name = "MA_HDONG")
	private String maHdong;

	@Column(name = "GHI_CHU")
	private String ghiChu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qlnvKhvonphiCapvonId", nullable = false)
	@JsonBackReference
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan;

}


