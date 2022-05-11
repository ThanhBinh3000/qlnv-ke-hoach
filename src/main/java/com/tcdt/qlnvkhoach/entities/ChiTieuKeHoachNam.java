package com.tcdt.qlnvkhoach.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ChiTieuKeHoachNam.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTieuKeHoachNam extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6093365068005372524L;
	public static final String TABLE_NAME = "CHI_TIEU_KE_HOACH_NAM";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHI_TIEU_KE_HOACH_NAM_SEQ")
	@SequenceGenerator(sequenceName = "CHI_TIEU_KE_HOACH_NAM_SEQ", allocationSize = 1, name = "CHI_TIEU_KE_HOACH_NAM_SEQ")
	private Long id;
	private String soQuyetDinh;
	private LocalDate ngayKy;
	private LocalDate ngayHieuLuc;
	private Integer namKeHoach;
	private String trichYeu;
	private String trangThai;
	private Long donViId;
	private LocalDate ngayGuiDuyet;
	private Long nguoiGuiDuyetId;
	private LocalDate ngayPheDuyet;
	private Long nguoiPheDuyetId;
	private String lyDoTuChoi;
	private String loaiQuyetDinh;
	private boolean lastest;
	private Long qdGocId;
	private String capDvi;
	private String maDvi;
	private String canCu;

	@Lob
	private String ghiChu;
	@Transient
	private List<KeHoachLuongThucMuoi> khLuongThucList = new ArrayList<>();

	@Transient
	private List<KeHoachLuongThucMuoi> khMuoiList = new ArrayList<>();

	@Transient
	private List<KeHoachVatTu> khVatTuList = new ArrayList<>();

	@Transient
	private List<FileDinhKemChung> fileDinhKems = new ArrayList<>();
}