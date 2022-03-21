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

	@Column(name = "K341_LUONG_TU_CHU")
	private Long k341LuongTuChu;

	@Column(name = "K341_TX_THEO_DMUC_TU_CHU")
	private Long k341TxTheoDmucTuChu;

	@Column(name = "K341_CHI_TX_KHONG_DMUC_TU_CHU")
	private Long k341ChiTxKhongDmucTuChu;
	
	@Column(name = "K341_LUONG_KHONG_TU_CHU")
	private Long k341LuongKhongTuChu;
	
	@Column(name = "K341_TX_THEO_DMUC_KHONG_TU_CHU")
	private Long k341TxTheoDmucKhongTuChu;
	
	@Column(name = "K341_CHI_TX_KHONG_DMUC_KHONG_TU_CHU")
	private Long k341ChiTxKhongDmucKhongTuChu;
	
	@Column(name = "K085_DAO_TAO")
	private Long k085DaoTao;
	
	@Column(name = "K102_NGHIEN_CUU_KHOA_HOC")
	private Long k102NghienCuuKhoaHoc;
	
	@Column(name = "K398_DAM_BAO_XA_HOI")
	private Long k398DamBaoXaHoi;
	
}


