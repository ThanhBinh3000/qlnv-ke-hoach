package com.tcdt.qlnvkhoachphi.table.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "QLNV_DM_KHOACHVON")
@Data
public class QlnvDmKhoachVonPhi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DM_KHOACHVON_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DM_KHOACHVON_SEQ", allocationSize = 1, name = "QLNV_DM_KHOACHVON_SEQ")
	private Long id;
	String loaiDm;
	String tenDm;
	Integer levelDm;
//	String idCha;
	String thongTin;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "idCha", referencedColumnName = "id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private QlnvDmKhoachVonPhi idCha;
}