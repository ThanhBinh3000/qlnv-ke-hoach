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
@Table(name = "QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N")
@Data
public class QlnvKhvonphiKhoachCtaoSchuaGd3n implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_CTRINH")
	private String tenCtrinh;

	@Column(name = "MA_NGUON_VON")
	private String maNguonVon;

	@Column(name = "MA_LOAI_CT")
	private String maLoaiCt;

	@Column(name = "TGIAN_KC")
	private Long tgianKc;

	@Column(name = "TGIAN_HT")
	private Long tgianHt;

	@Column(name = "MA_CQUAN_QD")
	private String maCquanQd;

	@Column(name = "TONG_GTRI")
	private Long tongGtri;

	@Column(name = "DTOAN_KPHI_3006N")
	private Long dtoanKphi3006n;

	@Column(name = "UOC_THIEN_N")
	private Long uocThienN;

	@Column(name = "DA_TTOAN_3006N")
	private Long daTtoan3006n;

	@Column(name = "UOC_TTOAN_N")
	private Long uocTtoanN;

	@Column(name = "TSO_N1")
	private Long tsoN1;

	@Column(name = "TTOAN_N1")
	private Long ttoanN1;

	@Column(name = "PHAT_SINH_N1")
	private Long phatSinhN1;

	@Column(name = "TSO_N2")
	private Long tsoN2;

	@Column(name = "TTOAN_N2")
	private Long ttoanN2;

	@Column(name = "PHAT_SINH_N2")
	private Long phatSinhN2;

	@Column(name = "TSO_N3")
	private Long tsoN3;

	@Column(name = "TTOAN_N3")
	private Long ttoanN3;

	@Column(name = "PHAT_SINH_N3")
	private Long phatSinhN3;

}


