package com.tcdt.qlnvkhoach.entities.catalog;

import java.io.Serializable;

import lombok.Data;

@Data
public class QlnvDmDonvi implements Serializable {
	private static final long serialVersionUID = 1L;

	Long id;
	String maDvi;
	String tenDvi;
	String capDvi;
	String trangThai;
	QlnvDmDonvi parent;
}
