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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET")
@Data
@NamedEntityGraph(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET.lstPaGiaoSoKiemTraTcNsnnCtietDvi", attributeNodes = @NamedAttributeNode("lstPaGiaoSoKiemTraTcNsnnCtietDvi"))
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET_SEQ")
	private Long id;

//	@Column(name = "QLNV_KHVONPHI_PA_ID")
//	private Long qlnvKhvonphiPaId;

	@Column(name = "MA_NOI_DUNG")
	private String maNoiDung;

	@Column(name = "MA_NHOM")
	private String maNhom;

	@Column(name = "TONG_SO")
	private Long tongSo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qlnvNsnnCtiet")
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi> lstPaGiaoSoKiemTraTcNsnnCtietDvi = new ArrayList<>();

//	PA_GIAO_SO_KIEM_TRA_TC_NSNN_ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paGiaoSoKiemTraTcNsnnId", nullable = false)
	@JsonBackReference
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvNsnn;

	public void setListCtietDvi(List<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi> listCtiet) {
		this.lstPaGiaoSoKiemTraTcNsnnCtietDvi.clear();
		for (QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi child : listCtiet) {
			child.setQlnvNsnnCtiet(this);
		}
		this.lstPaGiaoSoKiemTraTcNsnnCtietDvi.addAll(listCtiet);
	}

}
