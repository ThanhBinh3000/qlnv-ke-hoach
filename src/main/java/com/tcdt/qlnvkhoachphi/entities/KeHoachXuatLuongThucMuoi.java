package com.tcdt.qlnvkhoachphi.entities;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KE_HOACH_XUAT_LUONG_THUC_MUOI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachXuatLuongThucMuoi extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KE_HOACH_XUAT_LUONG_THUC_MUOI_SEQ")
	@SequenceGenerator(sequenceName = "KE_HOACH_XUAT_LUONG_THUC_MUOI_SEQ", allocationSize = 1, name = "KE_HOACH_XUAT_LUONG_THUC_MUOI_SEQ")
	private Long id;
	private Long keHoachId;
	private Long soLuongXuat;
	private Long namKeHoach;
}