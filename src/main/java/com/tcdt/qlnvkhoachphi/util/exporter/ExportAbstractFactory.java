package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.util.exporter.baocao.BCaoExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.capvonmuaban.CapvonMuaBanExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.dieuchinhdutoan.DchinhDuToanChiExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.LapThamDinhDuToanExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi.PhanboDtoanChiExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi.QdGiaoDtChiExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi.DchinhSauQtoanExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi.QtoanDtqgExportService;

public interface ExportAbstractFactory {
	ExportService getExportService(String type);

	CapvonMuaBanExportService getKHoachVonExportService(String type);

	DchinhDuToanChiExportService getDchinhDuToanChiExportService(String type);

	BCaoExportService getBCaoExportService(String type);

	LapThamDinhDuToanExportService getLapThamDinhDuToanExportService(String type);

	PhanboDtoanChiExportService getPhanboDtoanChiExportService(String type);

	QtoanDtqgExportService getQtoanDtqgExportService(String type);

	DchinhSauQtoanExportService getDchinhSauQtoanExportService(String type);

	QdGiaoDtChiExportService getQdGiaoDtChiExportService(String type);
}
