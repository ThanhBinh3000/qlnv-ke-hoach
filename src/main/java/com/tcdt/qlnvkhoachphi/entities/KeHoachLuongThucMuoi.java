package com.tcdt.qlnvkhoachphi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KE_HOACH_LUONG_THUC_MUOI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachLuongThucMuoi implements Serializable {
	private static final long serialVersionUID = 2406112053711573456L;
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
	private Integer stt;

	@Transient
	private List<KeHoachXuatLuongThucMuoi> khxltms = new ArrayList<>();

	public String groupByDonViIdAndVatTuId() {
		return String.format("%s_%s", donViId, vatTuId);
	}
}