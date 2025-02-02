package com.tcdt.qlnvkhoach.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KH_CHI_TIEU_VAT_TU")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachVatTu implements Serializable {

	private static final long serialVersionUID = -4943732132397265447L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KE_HOACH_VAT_TU_SEQ")
	@SequenceGenerator(sequenceName = "KE_HOACH_VAT_TU_SEQ", allocationSize = 1, name = "KE_HOACH_VAT_TU_SEQ")
	private Long id;
	private Long ctkhnId;
	private Long donViId;
	private String maDvi;
	@Transient
	private String tenDonVi;
	private Long vatTuId;
	private String maVatTu;
	@Transient
	private String tenVatTu;
	private Long vatTuChaId;
	private String maVatTuCha;
	@Transient
	private String tenVatTuCha;
	private Double soLuongNhap;
	private String donViTinh;
	private String trangThai;
	private Integer sttDonVi;
	private Integer sttVatTu;

	public String groupByDonViIdAndVatTuId() {
		return String.format("%s_%s", donViId, vatTuId);
	}
}