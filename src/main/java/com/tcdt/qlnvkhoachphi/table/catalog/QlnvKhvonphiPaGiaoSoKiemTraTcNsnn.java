package com.tcdt.qlnvkhoachphi.table.catalog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN")
@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnn implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_SEQ")
	@Column(name = "ID")
	private Long id;

	String maPa;
	String soQd;
	String soCv;
	String maDvi;
	Long namPa;
	Long namHienHanh;
	String trangThai;
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String maDviTien;
	
	@OneToMany
    @JoinTable(
        name="QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet",
        joinColumns = @JoinColumn( name="id"),
        inverseJoinColumns = @JoinColumn( name="qlnvKhvonphiPaId")
    )
	private List<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> listCtiet;
}
