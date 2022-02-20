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
@Table(name = "QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM")
@Data
public class QlnvKhvonphiTcThopDtoanChiTxHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_DVI")
	private String maDvi;

	@Column(name = "TONG_CONG")
	private Long tongCong;

	@Column(name = "K331_TCONG")
	private Long k331Tcong;

	@Column(name = "K331_KHONG_TCHU_CO_DMUC_CONG")
	private Long k331KhongTchuCoDmucCong;

	@Column(name = "K331_KHONG_TCHU_CO_DMUC_NX")
	private Long k331KhongTchuCoDmucNx;

	@Column(name = "K331_KHONG_TCHU_CO_DMUC_VTCT")
	private Long k331KhongTchuCoDmucVtct;

	@Column(name = "K331_KHONG_TCHU_CO_DMUC_BQUAN")
	private Long k331KhongTchuCoDmucBquan;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_CONG")
	private Long k331KhongTchuChuaDmucCong;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_CNTT")
	private Long k331KhongTchuChuaDmucCntt;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_THUE_KHO")
	private Long k331KhongTchuChuaDmucThueKho;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_MSAM_TSAN")
	private Long k331KhongTchuChuaDmucMsamTsan;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_BHIEM_HHOA")
	private Long k331KhongTchuChuaDmucBhiemHhoa;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_PHONG_CHONG_MOI_KPLB")
	private Long k331KhongTchuChuaDmucPhongChongMoiKplb;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_VCHUYEN_BQUAN_TSAN_QHIEM")
	private Long k331KhongTchuChuaDmucVchuyenBquanTsanQhiem;

	@Column(name = "K331_KHONG_TCHU_CHUA_DMUC_SCHUA_KHO_TANG")
	private Long k331KhongTchuChuaDmucSchuaKhoTang;

	@Column(name = "K341_TCONG")
	private Long k341Tcong;

	@Column(name = "K341_LUONG")
	private Long k341Luong;

	@Column(name = "K341_TX_THEO_DMUC")
	private Long k341TxTheoDmuc;

	@Column(name = "K341_CHI_TX_KHONG_DMUC")
	private Long k341ChiTxKhongDmuc;

}


