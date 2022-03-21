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
@Table(name = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG")
@Data
public class QlnvKhvonphiBcaoKquaThienNhapMuaHang implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_BCAO_ID")
	private Long qlnvKhvonphiBcaoId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_VTU")
	private String maVtu;

	@Column(name = "MA_VTU_PARENT")
	private String maVtuParent;

	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;

	@Column(name = "SO_QD")
	private String soQd;

	@Column(name = "KH_SO_LUONG")
	private Long khSoLuong;

	@Column(name = "KH_GIA_MUA_TD")
	private Long khGiaMuaTd;

	@Column(name = "KH_TTIEN")
	private Long khTtien;

	@Column(name = "TH_SO_LUONG")
	private Long thSoLuong;

	@Column(name = "TH_GIA_MUA_TD")
	private Long thGiaMuaTd;

	@Column(name = "TH_TTIEN")
	private Long thTtien;

	@Column(name = "GHI_CHU")
	private String ghiChu;

}




