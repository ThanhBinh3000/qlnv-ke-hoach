package com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;


@Entity
@Table(name = "QLNV_KHVONPHI_THOP_CAPVON_CTIET")
@Data
public class QlnvKhvonphiThopCapvonCtiet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHVONPHI_THOP_CAPVON_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHVONPHI_THOP_CAPVON_CTIET_SEQ", allocationSize = 1, name = "QLNV_KHVONPHI_THOP_CAPVON_CTIET_SEQ")
	private Long id;

//	@Column(name = "QLNV_KHVONPHI_THOP_CAPVON_ID")
//	private Long qlnvKhvonphiThopCapvonId;

	@Column(name = "STT")
	private Long stt;

	@Column(name = "MA_DVI")
	private String maLoaiGia;

	@Column(name = "SO_THAM_CHIEU")
	private String soThamChieu;

	@Column(name = "LTHUC_MUOI")
	private String lthucMuoi;

	@Column(name = "VTU_TBI")
	private String vtuTbi;

	@Column(name = "MA_DVI_TIEN")
	private String maDviTien;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qlnvKhvonphiThopCapvonId")
	@JsonBackReference
	private QlnvKhvonphiThopCapvon thopCapvon;

}


