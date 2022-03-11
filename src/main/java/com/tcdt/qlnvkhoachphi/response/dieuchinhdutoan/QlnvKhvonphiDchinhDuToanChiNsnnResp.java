package com.tcdt.qlnvkhoachphi.response.dieuchinhdutoan;

import com.tcdt.qlnvkhoachphi.table.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnn;

import lombok.Data;
@Data
public class QlnvKhvonphiDchinhDuToanChiNsnnResp {
	Long id;
	QlnvKhvonphiDchinhDuToanChiNsnn dchinhDuToanChiNsnn;
	Object lstFile;
}
