package com.tcdt.qlnvkhoachphi.table.catalog;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET")
@Data
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
	
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	  @JoinColumn(name = "qlnvKhvonphiPaId", nullable = false)
//	  @OnDelete(action = OnDeleteAction.CASCADE)
//	  @JsonIgnore
//	  private QlnvKhvonphiPaGiaoSoKiemTraTcNsnn giaoSoKiemTraTcNsnn;
	
	@OneToMany
    @JoinTable(
        name="QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi",
        joinColumns = @JoinColumn( name="id"),
        inverseJoinColumns = @JoinColumn( name="qlnvKhvonphiPaCtietId")
    )
	private List<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi> listCtietDvi;

}
