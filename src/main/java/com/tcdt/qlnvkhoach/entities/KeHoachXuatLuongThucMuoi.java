package com.tcdt.qlnvkhoach.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "KE_HOACH_XUAT_LUONG_THUC_MUOI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachXuatLuongThucMuoi implements Serializable {
	private static final long serialVersionUID = -6212686510947229243L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KHX_LUONGTHUCMUOI_SEQ")
	@SequenceGenerator(sequenceName = "KHX_LUONGTHUCMUOI_SEQ", allocationSize = 1, name = "KHX_LUONGTHUCMUOI_SEQ")
	private Long id;
	private Long keHoachId;
	private Double soLuongXuat;
	private Integer namKeHoach;
}