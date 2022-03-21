package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviResp;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp;

@Service
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportServiceImpl
		implements QlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportService {

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp importExcel(MultipartFile file) {
		QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp response = new QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp();
		try {
			InputStream excelFile = file.getInputStream();
			Workbook workbook = new XSSFWorkbook(excelFile);
			int numberOfSheet = workbook.getNumberOfSheets();
			if (numberOfSheet >= 1) {
				Sheet phuongAnGiaoSo = workbook.getSheetAt(0);
				if (phuongAnGiaoSo != null) {
					// TODO: danh mục noi dung
					// TODO: danh muc nhom

					ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp> lstPaGiaoSo = this
							.importPaGiaoSo(phuongAnGiaoSo);
					response.setListCtiet(lstPaGiaoSo);
				}
			}

			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp> importPaGiaoSo(Sheet sheet) {
		ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp> lstCtietResps = new ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp>();
		int count = 0;

		for (Row currentRow : sheet) {
			ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviResp> lstCtietDviResp = 
					new ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviResp>();
			
			QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp qlnvCtietResp
				= new QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp();
			
//			if (count < 5) {
//				count++;
//				continue;
//			}

			count++;
			int number = currentRow.getPhysicalNumberOfCells();

			// STT
			Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long stt = sttCell != null ? Long.valueOf(sttCell.getStringCellValue()) : null;
			
			// Noi dung
			String tenNoidung = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();
			qlnvCtietResp.setMaNoiDung(tenNoidung);

			// Nhom
			String tenNhom = currentRow.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();
			qlnvCtietResp.setMaNhom(tenNhom);
			
			//don vi
			for(int i = 3; i < number; i++) {
				QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviResp qlnvDviResp
					= new QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviResp();
				 String soTranChi = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
						.trim();
				qlnvDviResp.setSoTranChi(Long.parseLong(soTranChi));
				lstCtietDviResp.add(qlnvDviResp);
			}
			
			qlnvCtietResp.setLstPaGiaoSoKiemTraTcNsnnCtietDvi(lstCtietDviResp);
			
			lstCtietResps.add(qlnvCtietResp);
		}
		return lstCtietResps;
	}

}
