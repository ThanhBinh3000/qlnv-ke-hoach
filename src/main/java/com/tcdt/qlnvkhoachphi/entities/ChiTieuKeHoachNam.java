package com.tcdt.qlnvkhoachphi.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CHI_TIEU_KE_HOACH_NAM")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTieuKeHoachNam extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHI_TIEU_KE_HOACH_NAM_SEQ")
	@SequenceGenerator(sequenceName = "CHI_TIEU_KE_HOACH_NAM_SEQ", allocationSize = 1, name = "CHI_TIEU_KE_HOACH_NAM_SEQ")
	private Long id;
	private String soQuyetDinh;
	private LocalDate ngayKy;
	private LocalDate ngayHieuLuc;
	private Long namKeHoach;
	private String trichYeu;
	private String trangThai;
	private Long donViId;
	private LocalDate ngayGuiDuyet;
	private Long nguoiGuiDuyetId;
	private LocalDate ngayPheDuyet;
	private Long nguoiPheDuyetId;
	private String lyDoTuChoi;
}