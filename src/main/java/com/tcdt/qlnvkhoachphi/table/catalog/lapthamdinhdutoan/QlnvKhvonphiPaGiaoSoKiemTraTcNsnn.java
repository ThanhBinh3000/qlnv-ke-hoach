package com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN")
@Data
@NamedEntityGraph(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN.listCtiet", attributeNodes = @NamedAttributeNode("listCtiet"))
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
	Long maDvi;
	Long namPa;
	Long namHienHanh;
	String trangThai;
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String maDviTien;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qlnvNsnn")
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> listCtiet = new ArrayList<>();

	public void setListCtiet(List<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> listCtiet) {
		this.listCtiet.clear();
		for (QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet child : listCtiet) {
			child.setQlnvNsnn(this);
		}
		this.listCtiet.addAll(listCtiet);
	}

}
