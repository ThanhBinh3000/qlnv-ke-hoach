package com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_BCAO")
@Data
public class QlnvKhvonphiBcao implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_SEQ")
	Long id;
	String maBcao;
	Long namBcao;
	Long thangBcao;
	Long namHienHanh;
	String trangThai;
	@Transient
	String tenTrangThai;
	String maLoaiBcao;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String maDviTien;
	Long maDvi;
	Long maDviCha;
	@Transient
	String tenDvi;
	Long dotBcao;
	Long loaiBaoCao;
	String lyDoTuChoi;

}
