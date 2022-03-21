package com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
}
