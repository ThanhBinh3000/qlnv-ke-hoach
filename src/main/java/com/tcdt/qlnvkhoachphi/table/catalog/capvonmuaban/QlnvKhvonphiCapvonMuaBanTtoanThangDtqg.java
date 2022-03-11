package com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG")
@Data
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_SEQ")
	private Long id;

	String maDviLap;
	String maDviCap;
	String maDviNhan;
	@Transient
	String tenDviLap;
	@Transient
	String tenDviCap;
	@Transient
	String tenDviNhan;

	String maLoaiGnhan;
	String soQd;

	@Transient
	String ngayQuyetDinh;

	String trangThai;
	@Transient
	String tenTrangThai;

	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qlnvCapvonMuaBan")
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> listCtiet = new ArrayList<>();

	public void setListCtiet(List<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> listCtiet) {
		this.listCtiet.clear();
		for (QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet child : listCtiet) {
			child.setQlnvCapvonMuaBan(this);
		}
		this.listCtiet.addAll(listCtiet);
	}

}
