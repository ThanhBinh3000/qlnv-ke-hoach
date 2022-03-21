package com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_CTIET")
@Data
public class QlnvKhvonphiPhanboDtoanchiNsnnCtiet {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_CTIET_SEQ")
	private Long id;
	
	@Column(name = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI_ID")
	private Long qlnvKhvonphiPhanboDtoanChiId;
	
	@Column(name = "MA_DVI_THIEN")
	private String maDviThien;
	
	@Column(name = "NAM_THIEN")
	private Long namThien;
	
	@Column(name = "DTOAN_GIAO")
	private Long DtoanGiao;
	
	@Column(name = "MA_NDUNG")
	private String maNdung;
	
	@Column(name = "TONG_TIEN")
	private Long tongTien;
	
	@Column(name = "MA_KHOAN_MUC")
	private String maKhoanMuc;
	
	@Column(name = "MA_LOAI_CHI")
	private String maLoaiChi;
	
	@Column(name = "SO_TIEN")
	private Long soTien;
	
	@Column(name = "GHICHU")
	private String Ghichu;
	
	@Column(name = "CAN_CU_DTOAN")
	private String canCuDtoan;
	
	@Column(name = "PHAN_BO")
	private String phanBo;
}
