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
@Table(name = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD_CTIET")
@Data
public class QlnvKhvonphiQdGiaoDtChiNsnnCtiet {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD_CTIET_SEQ")
	private Long id;
	
	@Column(name = "QLNV_KHVONPHI_DS_QD_ID")
	private Long qlnvKhvonphiDsQdId;
	
	@Column(name = "MA_LOAI")
	private String maLoai;
	
	@Column(name = "MA_NHOM")
	private String maNhom;
	
	@Column(name = "MA_MAT_HANG")
	private String maMatHang;
	
	@Column(name = "SO_LUONG")
	private Long soLuong;
	
	@Column(name = "GIA_TRI")
	private Long giaTri;
	
	@Column(name = "MA_DVI_TINH")
	private String maDviTinh;
	
	@Column(name = "NAM_THIEN")
	private Long namThien;
	
	@Column(name = "DU_TOAN")
	private Long duToan;
	
	@Column(name = "MA_DVI_TIEN")
	private String maDviTien;
}
