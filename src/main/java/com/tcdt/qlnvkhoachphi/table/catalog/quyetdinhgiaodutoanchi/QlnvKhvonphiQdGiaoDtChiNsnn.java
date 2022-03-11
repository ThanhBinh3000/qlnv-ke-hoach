package com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi;

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
@Table(name = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD")
@Data
public class QlnvKhvonphiQdGiaoDtChiNsnn  implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DCHINH_DU_TOAN_CHI_NSNN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD_SEQ")
	private Long id;
	
	Long maDvi;
	@Transient
	String tenDvi;
	
	String noiQd;
	String soQd;
	
	Date ngayQd;
	
	String trangThai;
	@Transient
	String tenTrangThai;
	
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	
}
