package com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi;

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
@Table(name = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN")
@Data
@NamedEntityGraph(name = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN.listCtiet", attributeNodes = @NamedAttributeNode("listCtiet"))
public class QlnvKhvonphiBcaoKquaCtietThien implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_SEQ")
	private Long id;

	@Column(name = "QLNV_KHVONPHI_BCAO_ID")
	private Long qlnvKhvonphiBcaoId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_NDUNG_CHI")
	private String maNdungChi;

	@Column(name = "MA_NDUNG_CHI_PARENT")
	private String maNdungChiParent;

	@Column(name = "TRONG_DOT_TCONG")
	private Long trongDotTcong;

	@Column(name = "TRONG_DOT_THOC")
	private Long trongDotThoc;

	@Column(name = "TRONG_DOT_GAO")
	private Long trongDotGao;

	@Column(name = "LUY_KE_TCONG")
	private Long luyKeTcong;

	@Column(name = "LUY_KE_THOC")
	private Long luyKeThoc;

	@Column(name = "LUY_KE_GAO")
	private Long luyKeGao;

	@Column(name = "GHI_CHU")
	private String ghiChu;

	@Column(name = "LOAI_BAO_CAO")
	private Long loaiBaoCao;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bcaoKquaCtietThien", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<QlnvKhvonphiBcaoKquaCtietThienVtu> listCtiet = new ArrayList<>();

	public void setListCtiet(List<QlnvKhvonphiBcaoKquaCtietThienVtu> listCtiet) {
		this.listCtiet.clear();
		for (QlnvKhvonphiBcaoKquaCtietThienVtu child : listCtiet) {
			child.setBcaoKquaCtietThien(this);
		}
		this.listCtiet.addAll(listCtiet);
	}
	public void addChild(QlnvKhvonphiBcaoKquaCtietThienVtu child) {
		child.setBcaoKquaCtietThien(this);
		this.listCtiet.add(child);
	}

}


