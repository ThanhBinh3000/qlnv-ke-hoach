package com.tcdt.qlnvkhoach.entities.denghicapvonbonganh;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = KhDnCapVonBoNganhCt.TABLE_NAME)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KhDnCapVonBoNganhCt implements Serializable {
	public static final String TABLE_NAME = "KH_DN_CAP_VON_BO_NGANH_CT";
	private static final long serialVersionUID = 6242326510650973156L;


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_CAP_VON_BO_NGANH_CT_SEQ")
	@SequenceGenerator(sequenceName = "KH_DN_CAP_VON_BO_NGANH_CT_SEQ", allocationSize = 1, name = "KH_DN_CAP_VON_BO_NGANH_CT_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "MA_VAT_TU_CHA")
	private String maVatTuCha;

	@Column(name = "MA_VAT_TU")
	private String maVatTu;

	@Column(name = "SO_LUONG")
	private BigDecimal soLuong;

	@Column(name = "DON_VI_TINH")
	private String donViTinh;

	@Column(name = "THANH_TIEN")
	private BigDecimal thanhTien;

	@Column(name = "KINH_PHI_DA_CAP")
	private BigDecimal kinhPhiDaCap;

	@Column(name = "YC_CAP_THEM")
	private BigDecimal ycCapThem;

	@Column(name = "KH_DN_CAP_VON_BO_NGANH_ID")
	private Long deNghiCapVonBoNganhId;
}
