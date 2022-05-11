package com.tcdt.qlnvkhoach.util.exporter;

public interface ExportAbstractFactory {
	ExportService getExportService(String type);

}
