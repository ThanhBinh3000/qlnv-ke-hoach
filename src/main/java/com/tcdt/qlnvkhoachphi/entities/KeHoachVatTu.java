package com.tcdt.qlnvkhoachphi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "KE_HOACH_VAT_TU")
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
	private Long vatTuId;
	private String maVatTu;
	private Long vatTuChaId;
	private String maVatTuCha;
	private Double soLuongNhap;
	private String donViTinh;
	private String trangThai;
	private Integer sttDonVi;
	private Integer sttVatTu;

	public String groupByDonViIdAndVatTuId() {
		return String.format("%s_%s", donViId, vatTuId);
	}
}