package com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi;

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
@Table(name = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3")
@Data
public class QlnvKhvonphiBcaoThinhSdungDtoanPl3 implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_BCAO_ID")
	private Long qlnvKhvonphiBcaoId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_DAN")
	private String maDan;

	@Column(name = "DDIEM_XDUNG")
	private String ddiemXdung;

	@Column(name = "QDDT_SO_QDINH")
	private String qddtSoQdinh;

	@Column(name = "QDDT_TMDT_TSO")
	private Long qddtTmdtTso;

	@Column(name = "QDDT_TMDT_NSNN")
	private Long qddtTmdtNsnn;

	@Column(name = "LUY_KE_VON_TSO")
	private Long luyKeVonTso;

	@Column(name = "LUY_KE_VON_NSNN")
	private Long luyKeVonNsnn;

	@Column(name = "LUY_KE_GIAI_NGAN_HET_NAM_TSO")
	private Long luyKeGiaiNganHetNamTso;

	@Column(name = "LUY_KE_GIAI_NGAN_HET_NAM_NSNN_TSO")
	private Long luyKeGiaiNganHetNamNsnnTso;

	@Column(name = "LUY_KE_GIAI_NGAN_HET_NAM_NSNN_KH_NAM_TRUOC")
	private Long luyKeGiaiNganHetNamNsnnKhNamTruoc;

	@Column(name = "KHOACH_VON_DTU")
	private Long khoachVonDtu;

	@Column(name = "KHOACH_TSO")
	private Long khoachTso;

	@Column(name = "KHOACH_NSNN")
	private Long khoachNsnn;

	@Column(name = "KLUONG_THIEN")
	private Long kluongThien;

	@Column(name = "GIAI_NGAN_TSO")
	private Long giaiNganTso;

	@Column(name = "GIAI_NGAN_TSO_TLE")
	private Long giaiNganTsoTle;

	@Column(name = "GIAI_NGAN_NSNN")
	private Long giaiNganNsnn;

	@Column(name = "GIAI_NGAN_NSNN_TLE")
	private Long giaiNganNsnnTle;

	@Column(name = "LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_TSO")
	private Long luyKeGiaiNganDauNamNsnnTso;

	@Column(name = "LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_TSO_TLE")
	private Long luyKeGiaiNganDauNamNsnnTsoTle;

	@Column(name = "LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_NSNN")
	private Long luyKeGiaiNganDauNamNsnnNsnn;

	@Column(name = "LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_NSNN_TLE")
	private Long luyKeGiaiNganDauNamNsnnNsnnTle;

	@Column(name = "NDUNG_CVIEC_HTHANH_CUOI_THANG")
	private String ndungCviecHthanhCuoiThang;

	@Column(name = "NDUNG_CVIEC_DANG_THIEN")
	private String ndungCviecDangThien;

	@Column(name = "KHOACH_THIEN_NDUNG_CVIEC_THANG_CON_LAI_NAM")
	private String khoachThienNdungCviecThangConLaiNam;

	@Column(name = "GHI_CHU")
	private String ghiChu;
}
