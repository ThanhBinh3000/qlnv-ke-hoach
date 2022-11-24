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

@Entity
@Table(name = "KT_TRANGTHAI_HIENTHOI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KtTrangthaiHienthoi {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_TRANGTHAI_HIENTHOI_SEQ")
	@SequenceGenerator(sequenceName = "KT_TRANGTHAI_HIENTHOI_SEQ", allocationSize = 1, name = "KT_TRANGTHAI_HIENTHOI_SEQ")
  private Long id;
  private String maDonVi;
  private String tenDonVi;
  private String maVthh;
  private String tenVthh;
  private String loaiVthh;
  private String tenLoaiVthh;
  private String cloaiVthh;
  private String tenCloaiVthh;
  private Double slHienThoi;
  private Long duDau;
  private Long tongNhap;
  private Long tongXuat;
  private String nam;
  private int donViTinhId;
  private String tenDonViTinh;
}
