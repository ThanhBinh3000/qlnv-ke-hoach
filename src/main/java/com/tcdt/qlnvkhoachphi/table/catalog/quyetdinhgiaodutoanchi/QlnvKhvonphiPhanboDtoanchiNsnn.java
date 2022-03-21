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
@Table(name = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN")
@Data
public class QlnvKhvonphiPhanboDtoanchiNsnn implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_SEQ")
	private Long id;
	
	Long maDvi;
	@Transient
	String tenDvi;
	
	String soQd;
	
//	SO_QD_BTC
	
//	ID_SO_QD_BTC
	String idSoQdBtc;
	
//	SO_QD_TCDT
	
//	ID_SO_QD_TC
	String idSoQdTc;
	
	String phanBoTong;
	
	String trangThai;
	@Transient
	String tenTrangThai;
	
	String lyDoTuChoi;
	
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	
}
