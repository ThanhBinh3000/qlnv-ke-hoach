package com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi;

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
@Table(name = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG")
@Data
public class QlnvKhvonphiBcaoKquaThienXuatHang implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_BCAO_ID")
	private Long qlnvKhvonphiBcaoId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_VTU")
	private String maVtu;

	@Column(name = "MA_VTU_PARENT")
	private String maVtuParent;

	@Column(name = "MA_VTU_CHILD")
	private String maVtuChild;

	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;

	@Column(name = "SO_LUONG_KHOACH")
	private Long soLuongKhoach;

	@Column(name = "SO_LUONG_TTE")
	private Long soLuongTte;

	@Column(name = "DG_GIA_KHOACH")
	private Long dgGiaKhoach;

	@Column(name = "DG_GIA_BAN_TTHIEU")
	private Long dgGiaBanTthieu;

	@Column(name = "DG_GIA_BAN_TTE")
	private Long dgGiaBanTte;

	@Column(name = "TT_GIA_HTOAN")
	private Long ttGiaHtoan;

	@Column(name = "TT_GIA_BAN_TTE")
	private Long ttGiaBanTte;

	@Column(name = "TT_CLECH_GIA_TTE_VA_GIA_HTOAN")
	private Long ttClechGiaTteVaGiaHtoan;

	@Column(name = "GHI_CHU")
	private String ghiChu;

}
