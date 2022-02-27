package com.tcdt.qlnvkhoachphi.entities;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KE_HOACH_VAT_TU")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachVatTu extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KE_HOACH_VAT_TU_SEQ")
	@SequenceGenerator(sequenceName = "KE_HOACH_VAT_TU_SEQ", allocationSize = 1, name = "KE_HOACH_VAT_TU_SEQ")
	private Long id;
	private Long keHoachId;
	private Long donViId;
	private Long vatTuId;
	private Long vatTuChaId;
	private Long soLuongNhap;
	private String donViTinh;
	private String trangThai;
}