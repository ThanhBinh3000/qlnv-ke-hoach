package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExportFactory implements ExportAbstractFactory {
	@Autowired
	ApplicationContext context;

	@Override
	public ExportService getExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
			case Constants.ExportDataType.CHI_TIEU_LUONG_THUC:
				return context.getBean(CtkhnKeHoachLuongThucExporter.class);
			case Constants.ExportDataType.CHI_TIEU_MUOI:
				return context.getBean(CtkhnKeHoachMuoiExporter.class);
			case Constants.ExportDataType.CHI_TIEU_VAT_TU:
				return context.getBean(CtkhnKeHoachVatTuExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}
}
