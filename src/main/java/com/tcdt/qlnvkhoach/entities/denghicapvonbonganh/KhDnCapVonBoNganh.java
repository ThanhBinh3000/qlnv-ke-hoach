package com.tcdt.qlnvkhoach.entities.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = KhDnCapVonBoNganh.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhDnCapVonBoNganh extends TrangThaiBaseEntity implements Serializable {

	public static final String TABLE_NAME = "KH_DN_CAP_VON_BO_NGANH";
	private static final long serialVersionUID = -4074438189820672619L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_CAP_VON_BO_NGANH_SEQ")
	@SequenceGenerator(sequenceName = "KH_DN_CAP_VON_BO_NGANH_SEQ", allocationSize = 1, name = "KH_DN_CAP_VON_BO_NGANH_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAM")
	private Integer nam;

	@Column(name = "SO_DE_NGHI")
	private String soDeNghi;

	@Column(name = "NGAY_DE_NGHI")
	private LocalDate ngayDeNghi;

	@Column(name = "MA_BO_NGANH")
	private String maBoNganh;

	@Column(name = "GHI_CHU")
	private String ghiChu;

	@Column(name = "MA_DVI")
	private String maDvi;

	@Column(name = "CAP_DVI")
	private String capDvi;

	@Column(name = "KH_DN_TH_ID")
	private Long khDnThId;

	@Transient
	private List<KhDnCapVonBoNganhCt> chiTietList;

	@Transient
	private List<FileDinhKemChung> fileDinhKems;
}
