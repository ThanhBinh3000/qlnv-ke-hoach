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

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KE_HOACH_LUONG_THUC_MUOI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachLuongThucMuoi extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KE_HOACH_LUONG_THUC_MUOI_SEQ")
	@SequenceGenerator(sequenceName = "KE_HOACH_LUONG_THUC_MUOI_SEQ", allocationSize = 1, name = "KE_HOACH_LUONG_THUC_MUOI_SEQ")
	private Long id;
	private Long ctkhnId;
	private Long donViId;
	private String trichYeu;
	private Long vatTuId;
	private Double soLuongNhap;
	private String donViTinh;
	private String trangThai;
}