package com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_DNGHI_CAPVON")
@Data
@NamedEntityGraph(name = "QLNV_KHVONPHI_DNGHI_CAPVON.listCtiet", attributeNodes = @NamedAttributeNode("listCtiet"))
public class QlnvKhvonphiDnghiCapvon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_DNGHI_CAPVON_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_DNGHI_CAPVON_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_DNGHI_CAPVON_SEQ")
	private Long id;

	String maDvi;
	@Transient
	String tenDvi;
	String maCanCu;
	String maHdong;
	String soCv;
	Date ngay;
	String trangThai;
	@Transient
	String tenTrangThai;

	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dnghiCapvon", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<QlnvKhvonphiDnghiCapvonCtiet> listCtiet = new ArrayList<>();

	public void setListCtiet(List<QlnvKhvonphiDnghiCapvonCtiet> listCtiet) {
		this.listCtiet.clear();
		for (QlnvKhvonphiDnghiCapvonCtiet child : listCtiet) {
			child.setDnghiCapvon(this);
		}
		this.listCtiet.addAll(listCtiet);
	}

	public void addChild(QlnvKhvonphiDnghiCapvonCtiet child) {
		child.setDnghiCapvon(this);
		this.listCtiet.add(child);
	}



}


