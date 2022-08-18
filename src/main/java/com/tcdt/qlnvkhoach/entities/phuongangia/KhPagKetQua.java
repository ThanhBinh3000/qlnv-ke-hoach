package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = KhPagKetQua.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhPagKetQua implements Serializable {
	private static final long serialVersionUID = -2281361977218600320L;
	public static final String TABLE_NAME = "KH_PAG_KET_QUA";

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PAG_KET_QUA_SEQ")
	@SequenceGenerator(sequenceName = "KH_LT_PAG_KET_QUA_SEQ", allocationSize = 1, name = "KH_LT_PAG_KET_QUA_SEQ")
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "TEN_DVI_BAO_GIA")
	private String tenDviBaoGia;

	@Column(name = "DON_GIA")
	private BigDecimal donGia;

	@Column(name = "DON_GIA_VAT")
	private BigDecimal donGiaVat;

	@Column(name = "CLOAI_VTHH")
	private String cloaiVthh;

	@Column(name = "THOI_HAN_BAO_GIA")
	private String thoiHanBaoGia;

	@Column(name = "GHI_CHU")
	private String ghiChu;

	@Transient
	private FileDinhKemChung fileDinhKem;

	/**
	 * {@link com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum}
	 */
	@Column(name = "TYPE")
	private String type;

	/**
	 * {@link KhPhuongAnGia}
	 */
	@Column(name = "PAG_ID")
	private Long phuongAnGiaId;

	static final public String getFileDinhKemDataType(String type) {
		return String.format("%s_%s", KhPagKetQua.TABLE_NAME, type);
	}
}
