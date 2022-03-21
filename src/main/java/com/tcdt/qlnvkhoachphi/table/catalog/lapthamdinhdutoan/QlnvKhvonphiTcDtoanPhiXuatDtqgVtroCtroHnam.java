package com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM")
@Data
@NamedEntityGraph(name = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM.listCtiet", attributeNodes = @NamedAttributeNode("listCtiet"))
public class QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_ID")
	private Long qlnvKhvonphiId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_CUC_DTNN_KVUC")
	public String maCucDtnnKvuc;

	@Column(name = "LUONG_GAO")
	public Long luongGao;

	@Column(name = "CPHI_XUAT_CO_DMUC_GAO")
	public Long cphiXuatCoDmucGao;

	@Column(name = "CPHI_XUAT_CHUA_DMUC_GAO")
	public Long cphiXuatChuaDmucGao;

	@Column(name = "THANH_TIEN_CO_DMUC_GAO")
	public Long thanhTienCoDmucGao;

	@Column(name = "THANH_TIEN_KHONG_DMUC_GAO")
	public Long thanhTienKhongDmucGao;

	@Column(name = "THANH_TIEN_CONG_GAO")
	public Long thanhTienCongGao;

	@Column(name = "LUONG_THOC")
	public Long luongThoc;

	@Column(name = "CPHI_XUAT_CO_DMUC_THOC")
	public Long cphiXuatCoDmucThoc;

	@Column(name = "CPHI_XUAT_CHUA_DMUC_THOC")
	public Long cphiXuatChuaDmucThoc;

	@Column(name = "THANH_TIEN_CO_DMUC_THOC")
	public Long thanhTienCoDmucThoc;

	@Column(name = "THANH_TIEN_KHONG_DMUC_THOC")
	public Long thanhTienKhongDmucThoc;

	@Column(name = "THANH_TIEN_CONG_THOC")
	public Long thanhTienCongThoc;


	@OneToMany(fetch = FetchType.LAZY,mappedBy = "tcDtoanPhiXuatDtqgVtroCtroHnam", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroVtuHnam> listCtiet = new ArrayList<>();

	public void setListCtiet(List<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroVtuHnam> listCtiet) {
		this.listCtiet.clear();
		for (QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroVtuHnam child : listCtiet) {
			child.setTcDtoanPhiXuatDtqgVtroCtroHnam(this);
		}
		this.listCtiet.addAll(listCtiet);
	}

	public void addChild(QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroVtuHnam child) {
		child.setTcDtoanPhiXuatDtqgVtroCtroHnam(this);
		this.listCtiet.add(child);
	}

}


