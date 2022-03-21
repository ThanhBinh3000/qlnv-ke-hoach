package com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan;

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
@Table(name = "QLNV_KHVONPHI_DSACH_GIAO_SO_KIEM_TRA_TC_NSNN")
@Data
public class QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DSACH_GIAO_SO_KIEM_TRA_TC_NSNN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DSACH_GIAO_SO_KIEM_TRA_TC_NSNN_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DSACH_GIAO_SO_KIEM_TRA_TC_NSNN_SEQ")
	private Long id;

	String maGiao;
	String maPa;
	
	String maDviTao;
	@Transient
	String tenDviTao;
	
	String maDviNhan;
	@Transient
	String tenDviNhan;
	
	Long namGiao;
	
	String maNoiDung;
	@Transient
	String tenNoiDung;
	
	String maNhom;
	@Transient
	String tenNhom;
	
	Long soDuocGiao;
	
	String trangThai;
	@Transient
	String tenTrangThai;
	
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	
	String maDviTien;
	@Transient
	String tenDviTien;

}



