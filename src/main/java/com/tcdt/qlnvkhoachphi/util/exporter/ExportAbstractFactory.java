package com.tcdt.qlnvkhoachphi.util.exporter;

public interface ExportAbstractFactory {
	ExportService getExportService(String type);
}
