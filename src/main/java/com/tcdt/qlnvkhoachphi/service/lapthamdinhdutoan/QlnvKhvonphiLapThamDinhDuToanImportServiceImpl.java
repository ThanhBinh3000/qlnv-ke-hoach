package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiKhoachBquanHnamMatHangEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiNcauXuatDtqgVtroHnamEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiNxuatDtqgHnamVattuEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiTcKhoachBquanHnamEntity;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmKhoachVonPhiRepository;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiDmVondtXdcbgd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachBquanHnamMatHang;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachQuyTienLuongGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNcauXuatDtqgVtroVtuHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNxuatDtqgHnamVattu;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcKhoachBquanHnam;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiLapThamDinhDuToanImportServiceImpl implements QlnvKhvonphiLapThamDinhDuToanImportService {

	@Autowired
	private QlnvDmKhoachVonPhiRepository qlnvDmKhoachVonPhiRepository;

	@Autowired
	private QlnvDmDonviRepository qlnvDmDonviRepository;

	@Override
	public QlnvKhvonphiResp importExcel(MultipartFile file, String maLoaiBcao) {
		QlnvKhvonphiResp response = new QlnvKhvonphiResp();

		try {
			InputStream excelFile = file.getInputStream();
			Workbook workbook = new XSSFWorkbook(excelFile);
			int numberOfSheet = workbook.getNumberOfSheets();
			if (numberOfSheet >= 1) {
				Sheet lapThamDinh = workbook.getSheetAt(0);
				if (lapThamDinh != null) {

					// danh mục thiết bị vật tư
					Map<String, QlnvDmKhoachVonPhi> mapDmTBiVTu = qlnvDmKhoachVonPhiRepository
							.findByLoaiDm(Constants.DM_TBI_VTU).stream()
							.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

					// danh mục đơn vị tính
					Map<String, QlnvDmKhoachVonPhi> mapDmDViTinh = qlnvDmKhoachVonPhiRepository
							.findByLoaiDm(Constants.DM_DVI_TINH).stream()
							.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

					// danh mục đơn vị
					Map<String, QlnvDmDonvi> mapDmDVi = Lists.newArrayList(qlnvDmDonviRepository.findAll()).stream()
							.collect(Collectors.toMap(QlnvDmDonvi::getTenDvi, Function.identity()));

					if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
						// TODO TuanVA bổ xung cache
						// danh mục loại kế hoạch
						Map<String, QlnvDmKhoachVonPhi> mapDmLoaiKeHoach = qlnvDmKhoachVonPhiRepository
								.findByLoaiDm(Constants.DM_LOAI_KE_HOACH).stream()
								.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

						// danh mục loại khối dự án
						Map<String, QlnvDmKhoachVonPhi> mapDmLoaiKhoiDan = qlnvDmKhoachVonPhiRepository
								.findByLoaiDm(Constants.DM_KHOI_DAN).stream()
								.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

						// danh mục địa điểm xây dựng
						Map<String, QlnvDmKhoachVonPhi> mapDmLoaiMaDdiemXd = qlnvDmKhoachVonPhiRepository
								.findByLoaiDm(Constants.DM_DIA_DIEM_XD).stream()
								.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

						// danh mục loại mã ngành kinh tế
						Map<String, QlnvDmKhoachVonPhi> mapDmLoaiMaNganhKte = qlnvDmKhoachVonPhiRepository
								.findByLoaiDm(Constants.DM_MA_NGANH_KT).stream()
								.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

						List<QlnvKhvonphiDmVondtXdcbgd3n> cTietBaoCao = this.importBcao01(lapThamDinh, mapDmLoaiKeHoach,
								mapDmLoaiKhoiDan, mapDmLoaiMaDdiemXd, mapDmLoaiMaNganhKte);
						response.setLstCTietBCao(cTietBaoCao);

					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
						List<QlnvKhvonphiNxuatDtqgHnamVattu> lstCTietBaoCao = this.importBcao02(lapThamDinh,
								mapDmTBiVTu);
						QlnvKhvonphiNxuatDtqgHnamVattuEntity cTietBaoCao = new QlnvKhvonphiNxuatDtqgHnamVattuEntity();
						cTietBaoCao.setLstCTiet(lstCTietBaoCao);
						response.setLstCTietBCao(cTietBaoCao);
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
						// danh mục nhóm
						Map<String, QlnvDmKhoachVonPhi> mapDmNhom = qlnvDmKhoachVonPhiRepository
								.findByLoaiDm(Constants.DM_NHOM).stream()
								.collect(Collectors.toMap(QlnvDmKhoachVonPhi::getTenDm, Function.identity()));

						List<QlnvKhvonphiKhoachBquanHnamMatHang> lstCTietBaoCao = this.importBcao03(lapThamDinh,
								mapDmTBiVTu, mapDmNhom);
						QlnvKhvonphiNxuatDtqgHnamVattuEntity cTietBaoCao = new QlnvKhvonphiNxuatDtqgHnamVattuEntity();
						cTietBaoCao.setLstCTiet(lstCTietBaoCao);
						response.setLstCTietBCao(cTietBaoCao);
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
						List<QlnvKhvonphiNcauXuatDtqgVtroVtuHnam> lstCTietBaoCao = this.importBcao04(lapThamDinh,
								mapDmTBiVTu, mapDmDViTinh);
						QlnvKhvonphiNcauXuatDtqgVtroHnamEntity cTietBaoCao = new QlnvKhvonphiNcauXuatDtqgVtroHnamEntity();
						cTietBaoCao.setLstCTiet(lstCTietBaoCao);
						response.setLstCTietBCao(cTietBaoCao);
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
						List<QlnvKhvonphiKhoachQuyTienLuongGd3n> lstCTietBaoCao = this.importBcao05(lapThamDinh,
								mapDmDVi);
						response.setLstCTietBCao(lstCTietBaoCao);
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
					}
					// TC
					else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
					} else if (maLoaiBcao
							.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
						
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
					} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 32
					}
				}
			}

//            if (numberOfSheet >= 2) {
//                Sheet khMuoi = workbook.getSheetAt(1);
//                if (khMuoi != null) {
//                    List<KeHoachMuoiDuTruRes> keHoachMuoiDuTruRes = this.importKeHoachMuoi(khMuoi, mapDonVi);
//                    response.setKhMuoi(keHoachMuoiDuTruRes);
//                }
//            }
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private List<QlnvKhvonphiDmVondtXdcbgd3n> importBcao01(Sheet sheet, Map<String, QlnvDmKhoachVonPhi> dmLoaiKeHoach,
			Map<String, QlnvDmKhoachVonPhi> dmLoaiKhoiDan, Map<String, QlnvDmKhoachVonPhi> dmLoaisMaDdiemXd,
			Map<String, QlnvDmKhoachVonPhi> dmLoaiMaNganhKte) throws Exception {
		List<QlnvKhvonphiDmVondtXdcbgd3n> responses = new ArrayList<>();

		int count = 0;
		for (Row currentRow : sheet) {
			// data row
			if (count < 5) {
				count++;
				continue;
			}
			count++;

			// STT
			Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long stt = sttCell != null ? Long.valueOf(sttCell.getStringCellValue()) : null;

			// Tên dự án
			String tenDan = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Loại kế hoạch
			String tenMaKhoach = currentRow.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Khối dự án
			String tenKhoiDan = currentRow.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Địa điểm xây dựng
			String tenMaDdiemXd = currentRow.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Địa điểm mở tài khoản
			String ddiemMoTaikhoan = currentRow.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();

			// Mã số dự án
			String masoDan = currentRow.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Mã ngành kinh tế (Loại khoản)
			String tenMaNganhKte = currentRow.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();

			// Năng lực thiết kế
			String nlucTke = currentRow.getCell(8, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Năm KC - HT thực tế
			// KC
			Cell namKcTteCell = currentRow.getCell(9, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long namKcTte = namKcTteCell != null ? Long.valueOf(namKcTteCell.getStringCellValue()) : null;
			// HT
			Cell namHtTteCell = currentRow.getCell(10, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long namHtTte = namHtTteCell != null ? Long.valueOf(namHtTteCell.getStringCellValue()) : null;

			// QD duyệt dự án đầu tư
			// CQ duyệt, số, ngày tháng
			String qdDuyetDanDtuSongaythang = currentRow.getCell(11, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();
			// Tổng vốn đầu tư
			Long qdDuyetDanDtuTongVon = Long.valueOf(
					currentRow.getCell(12, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// QD điều chỉnh DA (Lần cuối)
			// CQ duyệt, số, ngày tháng
			String qdDchinhDanDtuSongaythang = currentRow.getCell(13, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();
			// Tổng vốn đầu tư
			Long qdDchinhDanDtuTongVon = Long.valueOf(
					currentRow.getCell(14, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// Quyết định duyệt thiết kế dự án
			// CQ duyệt, số, ngày tháng
			String qdDuyetTkDtoanSongaythang = currentRow.getCell(15, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();
			// Tổng
			Long qdDuyetTkDtoanTong = Long.valueOf(
					currentRow.getCell(16, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// Trong đó
			// XL
			Long qdDuyetTkDtoanXl = Long.valueOf(
					currentRow.getCell(17, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// TB
			Long qdDuyetTkDtoanTb = Long.valueOf(
					currentRow.getCell(18, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// CK
			Long qdDuyetTkDtoanCk = Long.valueOf(
					currentRow.getCell(19, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// KL TH, cấp phát đến 30/06 năm thực hiện
			// CQ duyệt, số, ngày tháng
			String klthCapDen3006Songaythang = currentRow.getCell(20, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();
			// Trong đó
			// NSTT
			Long klthCapDen3006Nstt = Long.valueOf(
					currentRow.getCell(21, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// Khác
			// DT chi TX
			Long klthCapDen3006DtoanChiTx = Long.valueOf(
					currentRow.getCell(22, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// Quỹ khác
			Long klthCapDen3006Quykhac = Long.valueOf(
					currentRow.getCell(23, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// KL TH, cấp phát đến 31/12 năm thực hiện
			// CQ duyệt, số, ngày tháng
			String klthCapDen3112Songaythang = currentRow.getCell(24, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();
			// Trong đó
			// NSTT
			Long klthCapDen3112Nstt = Long.valueOf(
					currentRow.getCell(25, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// Khác
			// DT chi TX
			Long klthCapDen3112DtoanChiTx = Long.valueOf(
					currentRow.getCell(26, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());
			// Quỹ khác
			Long klthCapDen3112Quykhac = Long.valueOf(
					currentRow.getCell(27, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// Nhu cầu vốn năm N+1
			Long ncauVonN1 = Long.valueOf(
					currentRow.getCell(28, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// Nhu cầu vốn năm N+2
			Long ncauVonN2 = Long.valueOf(
					currentRow.getCell(29, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// Nhu cầu vốn năm N+3
			Long ncauVonN3 = Long.valueOf(
					currentRow.getCell(30, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim());

			// Ghi chú: Ghi rõ công trình thuộc loại hoàn thành hay dự kiến hoàn thành
			String ghiChu = currentRow.getCell(31, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			QlnvKhvonphiDmVondtXdcbgd3n response = new QlnvKhvonphiDmVondtXdcbgd3n();

			response.setStt(stt);
			response.setTenDan(tenDan);

			// loại kế hoạch
			QlnvDmKhoachVonPhi loaiKeHoach = dmLoaiKeHoach.get(tenMaKhoach);
			if (loaiKeHoach == null)
				throw new Exception("Mã loại kế hoạch không tồn tại");
			response.setMaKhoach(loaiKeHoach.getId());

			// loại khối dự ans
			QlnvDmKhoachVonPhi loaiKhoiDan = dmLoaiKhoiDan.get(tenKhoiDan);
			if (loaiKhoiDan == null)
				throw new Exception("Mã khối dự án không tồn tại");
			response.setMaKhoiDan(loaiKhoiDan.getId());

			// loại địa điểm xây dựng
			QlnvDmKhoachVonPhi loaisMaDdiemXd = dmLoaisMaDdiemXd.get(tenMaDdiemXd);
			if (loaisMaDdiemXd == null)
				throw new Exception("Mã khối dự án không tồn tại");
			response.setMaDdiemXd(loaisMaDdiemXd.getId());

			response.setDdiemMoTaikhoan(ddiemMoTaikhoan);
			response.setMasoDan(masoDan);

			// loại mã ngành kinh tế
			QlnvDmKhoachVonPhi loaiMaNganhKte = dmLoaiMaNganhKte.get(tenMaNganhKte);
			if (loaiMaNganhKte == null)
				throw new Exception("Mã khối dự án không tồn tại");
			response.setMaNganhKte(loaiMaNganhKte.getId());

			response.setNlucTke(nlucTke);
			response.setNamKcTte(namKcTte);
			response.setNamHtTte(namHtTte);

			response.setQdDuyetDanDtuSongaythang(qdDuyetDanDtuSongaythang);
			response.setQdDuyetDanDtuTongVon(qdDuyetDanDtuTongVon);
			response.setQdDchinhDanDtuSongaythang(qdDchinhDanDtuSongaythang);
			response.setQdDchinhDanDtuTongVon(qdDchinhDanDtuTongVon);

			response.setQdDuyetTkDtoanSongaythang(qdDuyetTkDtoanSongaythang);
			response.setQdDuyetTkDtoanTong(qdDuyetTkDtoanTong);
			response.setQdDuyetTkDtoanXl(qdDuyetTkDtoanXl);
			response.setQdDuyetTkDtoanTb(qdDuyetTkDtoanTb);
			response.setQdDuyetTkDtoanCk(qdDuyetTkDtoanCk);

			response.setKlthCapDen3006Songaythang(klthCapDen3006Songaythang);
			response.setKlthCapDen3006Nstt(klthCapDen3006Nstt);
			response.setKlthCapDen3006DtoanChiTx(klthCapDen3006DtoanChiTx);
			response.setKlthCapDen3006Quykhac(klthCapDen3006Quykhac);

			response.setKlthCapDen3112Songaythang(klthCapDen3112Songaythang);
			response.setKlthCapDen3112Nstt(klthCapDen3112Nstt);
			response.setKlthCapDen3112DtoanChiTx(klthCapDen3112DtoanChiTx);
			response.setKlthCapDen3112Quykhac(klthCapDen3112Quykhac);

			response.setNcauVonN1(ncauVonN1);
			response.setNcauVonN2(ncauVonN2);
			response.setNcauVonN3(ncauVonN3);
			response.setGhiChu(ghiChu);

			responses.add(response);
		}

		return responses;
	}

	private List<QlnvKhvonphiNxuatDtqgHnamVattu> importBcao02(Sheet sheet, Map<String, QlnvDmKhoachVonPhi> mapDmTBiVTu)
			throws Exception {
		List<QlnvKhvonphiNxuatDtqgHnamVattu> responses = new ArrayList<>();

		int count = 0;
		for (Row currentRow : sheet) {
			// data row
			if (count < 1) {
				count++;
				continue;
			}
			count++;

			// STT
			Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long stt = sttCell != null ? Long.valueOf(sttCell.getStringCellValue()) : null;

			// Tên thiết bị
			String tenMaTbi = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Số lượng nhập
			Cell slNhapCell = currentRow.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long slNhap = slNhapCell != null ? Long.valueOf(slNhapCell.getStringCellValue()) : null;

			QlnvKhvonphiNxuatDtqgHnamVattu response = new QlnvKhvonphiNxuatDtqgHnamVattu();

			response.setStt(stt);
			response.setTenMaTbi(tenMaTbi);

			// loại thiết bị vật tư
			QlnvDmKhoachVonPhi dmTBiVTu = mapDmTBiVTu.get(tenMaTbi);
			if (dmTBiVTu == null)
				throw new Exception("Mã loại thiết bị vật tư");
			response.setMaTbi(dmTBiVTu.getId());
			response.setSlNhap(slNhap);
			responses.add(response);
		}

		return responses;
	}

	private List<QlnvKhvonphiKhoachBquanHnamMatHang> importBcao03(Sheet sheet,
			Map<String, QlnvDmKhoachVonPhi> mapDmTBiVTu, Map<String, QlnvDmKhoachVonPhi> mapDmNhom) throws Exception {
		List<QlnvKhvonphiKhoachBquanHnamMatHang> responses = new ArrayList<>();

		int count = 0;
		for (Row currentRow : sheet) {
			// data row
			if (count < 1) {
				count++;
				continue;
			}
			count++;

			// STT
			Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long stt = sttCell != null ? Long.valueOf(sttCell.getStringCellValue()) : null;

			// Loại mặt hàng
			String tenMaMatHang = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();
			// Nhóm
			String tenMaNhom = currentRow.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Kinh phí bảo quản
			Cell kPhiCell = currentRow.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long kPhi = kPhiCell != null ? Long.valueOf(kPhiCell.getStringCellValue()) : null;

			QlnvKhvonphiKhoachBquanHnamMatHang response = new QlnvKhvonphiKhoachBquanHnamMatHang();

			response.setStt(stt);

			// loại thiết bị vật tư
			QlnvDmKhoachVonPhi dmTBiVTu = mapDmTBiVTu.get(tenMaMatHang);
			if (dmTBiVTu == null)
				throw new Exception("Mã loại thiết bị vật tư");
			response.setMaMatHang(dmTBiVTu.getId());

			// loại thiết bị vật tư
			QlnvDmKhoachVonPhi dmNhom = mapDmNhom.get(tenMaNhom);
			if (dmNhom == null)
				throw new Exception("Mã danh mục nhóm");
			response.setMaNhom(dmNhom.getId());

			response.setKPhi(kPhi);
			responses.add(response);
		}

		return responses;
	}

	private List<QlnvKhvonphiNcauXuatDtqgVtroVtuHnam> importBcao04(Sheet sheet,
			Map<String, QlnvDmKhoachVonPhi> mapDmTBiVTu, Map<String, QlnvDmKhoachVonPhi> mapDmDViTinh)
			throws Exception {
		List<QlnvKhvonphiNcauXuatDtqgVtroVtuHnam> responses = new ArrayList<>();

		int count = 0;
		for (Row currentRow : sheet) {
			// data row
			if (count < 1) {
				count++;
				continue;
			}
			count++;

			// STT
			Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long stt = sttCell != null ? Long.valueOf(sttCell.getStringCellValue()) : null;

			// Chỉ tiêu
			String tenMaVtuTbi = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			// Kinh phí bảo quản
			Cell sLCell = currentRow.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long sL = sLCell != null ? Long.valueOf(sLCell.getStringCellValue()) : null;

			// Nhóm
			String tenMaDviVtuTbi = currentRow.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
					.getStringCellValue().trim();

			QlnvKhvonphiNcauXuatDtqgVtroVtuHnam response = new QlnvKhvonphiNcauXuatDtqgVtroVtuHnam();

			response.setStt(stt);

			// loại thiết bị vật tư
			QlnvDmKhoachVonPhi dmTBiVTu = mapDmTBiVTu.get(tenMaVtuTbi);
			if (dmTBiVTu == null)
				throw new Exception("Mã loại thiết bị vật tư");
			response.setMaVtuTbi(dmTBiVTu.getId());

			// danh mục đơn vị tính
			QlnvDmKhoachVonPhi dmDViTinh = mapDmDViTinh.get(tenMaDviVtuTbi);
			if (dmDViTinh == null)
				throw new Exception("Mã danh mục đơn vị tính");
			response.setMaDviVtuTbi(dmDViTinh.getId());

			response.setSL(sL);
			responses.add(response);
		}

		return responses;
	}

	private List<QlnvKhvonphiKhoachQuyTienLuongGd3n> importBcao05(Sheet sheet, Map<String, QlnvDmDonvi> mapDmDVi)
			throws Exception {
		List<QlnvKhvonphiKhoachQuyTienLuongGd3n> responses = new ArrayList<>();

		int count = 0;
		for (Row currentRow : sheet) {
			// data row
			if (count < 5) {
				count++;
				continue;
			}
			count++;

			// STT
			Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long stt = sttCell != null ? Long.valueOf(sttCell.getStringCellValue()) : null;

			// Tên đơn vị
			String tenMaDvi = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()
					.trim();

			Cell tongCboNCell = currentRow.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongCboN = tongCboNCell != null ? Long.valueOf(tongCboNCell.getStringCellValue()) : null;

			Cell tongBcheDuocPdNCell = currentRow.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongBcheDuocPdN = tongBcheDuocPdNCell != null ? Long.valueOf(tongBcheDuocPdNCell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongNCell = currentRow.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongN = tongQuyLuongCoTchatLuongNCell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongNCell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongTheoBcheNCell = currentRow.getCell(5,
					Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongTheoBcheN = tongQuyLuongCoTchatLuongTheoBcheNCell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongTheoBcheNCell.getStringCellValue())
					: null;

			Cell tuongCbanNCell = currentRow.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tuongCbanN = tuongCbanNCell != null ? Long.valueOf(tuongCbanNCell.getStringCellValue()) : null;

			Cell phuCapNCell = currentRow.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long phuCapN = phuCapNCell != null ? Long.valueOf(phuCapNCell.getStringCellValue()) : null;

			Cell cacKhoanDgopNCell = currentRow.getCell(8, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long cacKhoanDgopN = cacKhoanDgopNCell != null ? Long.valueOf(cacKhoanDgopNCell.getStringCellValue())
					: null;

			Cell tongCboThienNCell = currentRow.getCell(9, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongCboThienN = tongCboThienNCell != null ? Long.valueOf(tongCboThienNCell.getStringCellValue())
					: null;

			Cell tongBcheDuocPdThienNCell = currentRow.getCell(10, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongBcheDuocPdThienN = tongBcheDuocPdThienNCell != null
					? Long.valueOf(tongBcheDuocPdThienNCell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongThienNCell = currentRow.getCell(11,
					Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongThienN = tongQuyLuongCoTchatLuongThienNCell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongThienNCell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongTheoBcheThienNCell = currentRow.getCell(12,
					Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongTheoBcheThienN = tongQuyLuongCoTchatLuongTheoBcheThienNCell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongTheoBcheThienNCell.getStringCellValue())
					: null;

			Cell luongCbanThienNCell = currentRow.getCell(13, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long luongCbanThienN = luongCbanThienNCell != null ? Long.valueOf(luongCbanThienNCell.getStringCellValue())
					: null;

			Cell phuCapThienNCell = currentRow.getCell(14, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long phuCapThienN = phuCapThienNCell != null ? Long.valueOf(phuCapThienNCell.getStringCellValue()) : null;

			Cell cacKhoanDgopThienNCell = currentRow.getCell(15, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long cacKhoanDgopThienN = cacKhoanDgopThienNCell != null
					? Long.valueOf(cacKhoanDgopThienNCell.getStringCellValue())
					: null;

			Cell tongCboN1Cell = currentRow.getCell(16, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongCboN1 = tongCboN1Cell != null ? Long.valueOf(tongCboN1Cell.getStringCellValue()) : null;

			Cell tongBcheDuocPdN1Cell = currentRow.getCell(17, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongBcheDuocPdN1 = tongBcheDuocPdN1Cell != null
					? Long.valueOf(tongBcheDuocPdN1Cell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongN1Cell = currentRow.getCell(18, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongN1 = tongQuyLuongCoTchatLuongN1Cell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongN1Cell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongTheoBcheN1Cell = currentRow.getCell(19,
					Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongTheoBcheN1 = tongQuyLuongCoTchatLuongTheoBcheN1Cell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongTheoBcheN1Cell.getStringCellValue())
					: null;

			Cell luongCbanN1Cell = currentRow.getCell(20, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long luongCbanN1 = luongCbanN1Cell != null ? Long.valueOf(luongCbanN1Cell.getStringCellValue()) : null;

			Cell phuCapN1Cell = currentRow.getCell(21, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long phuCapN1 = phuCapN1Cell != null ? Long.valueOf(phuCapN1Cell.getStringCellValue()) : null;

			Cell cacKhoanDgopN1Cell = currentRow.getCell(22, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long cacKhoanDgopN1 = cacKhoanDgopN1Cell != null ? Long.valueOf(cacKhoanDgopN1Cell.getStringCellValue())
					: null;

			Cell tongCboN2Cell = currentRow.getCell(23, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongCboN2 = tongCboN2Cell != null ? Long.valueOf(tongCboN2Cell.getStringCellValue()) : null;

			Cell tongBcheDuocPdN2Cell = currentRow.getCell(24, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongBcheDuocPdN2 = tongBcheDuocPdN2Cell != null
					? Long.valueOf(tongBcheDuocPdN2Cell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongN2Cell = currentRow.getCell(25, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongN2 = tongQuyLuongCoTchatLuongN2Cell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongN2Cell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongTheoBcheN2Cell = currentRow.getCell(26,
					Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongTheoBcheN2 = tongQuyLuongCoTchatLuongTheoBcheN2Cell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongTheoBcheN2Cell.getStringCellValue())
					: null;

			Cell luongCbanN2Cell = currentRow.getCell(27, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long luongCbanN2 = luongCbanN2Cell != null ? Long.valueOf(luongCbanN2Cell.getStringCellValue()) : null;

			Cell phuCapN2Cell = currentRow.getCell(28, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long phuCapN2 = phuCapN2Cell != null ? Long.valueOf(phuCapN2Cell.getStringCellValue()) : null;

			Cell cacKhoanDgopN2Cell = currentRow.getCell(29, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long cacKhoanDgopN2 = cacKhoanDgopN2Cell != null ? Long.valueOf(cacKhoanDgopN2Cell.getStringCellValue())
					: null;

			Cell tongCboN3Cell = currentRow.getCell(30, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongCboN3 = tongCboN3Cell != null ? Long.valueOf(tongCboN3Cell.getStringCellValue()) : null;

			Cell tongBcheDuocPdN3Cell = currentRow.getCell(31, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongBcheDuocPdN3 = tongBcheDuocPdN3Cell != null
					? Long.valueOf(tongBcheDuocPdN3Cell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongN3Cell = currentRow.getCell(32, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongN3 = tongQuyLuongCoTchatLuongN3Cell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongN3Cell.getStringCellValue())
					: null;

			Cell tongQuyLuongCoTchatLuongTheoBcheN3Cell = currentRow.getCell(33,
					Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long tongQuyLuongCoTchatLuongTheoBcheN3 = tongQuyLuongCoTchatLuongTheoBcheN3Cell != null
					? Long.valueOf(tongQuyLuongCoTchatLuongTheoBcheN3Cell.getStringCellValue())
					: null;

			Cell luongCbanN3Cell = currentRow.getCell(34, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long luongCbanN3 = luongCbanN3Cell != null ? Long.valueOf(luongCbanN3Cell.getStringCellValue()) : null;

			Cell phuCapN3Cell = currentRow.getCell(35, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long phuCapN3 = phuCapN3Cell != null ? Long.valueOf(phuCapN3Cell.getStringCellValue()) : null;

			Cell cacKhoanDgopN3Cell = currentRow.getCell(36, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Long cacKhoanDgopN3 = cacKhoanDgopN3Cell != null ? Long.valueOf(cacKhoanDgopN3Cell.getStringCellValue())
					: null;

			QlnvKhvonphiKhoachQuyTienLuongGd3n response = new QlnvKhvonphiKhoachQuyTienLuongGd3n();

			response.setStt(stt);
			
			// danh mục đơn vị
			QlnvDmDonvi dmDVi = mapDmDVi.get(tenMaDvi);
			if (dmDVi == null)
				throw new Exception("Mã danh mục đơn vị tính");
			response.setMaDvi(dmDVi.getId());

			response.setTongCboN(tongCboN);
			response.setTongBcheDuocPdN(tongBcheDuocPdN);
			response.setTongQuyLuongCoTchatLuongN(tongQuyLuongCoTchatLuongN);
			response.setTongQuyLuongCoTchatLuongTheoBcheN(tongQuyLuongCoTchatLuongTheoBcheN);
			response.setTuongCbanN(tuongCbanN);
			response.setPhuCapN(phuCapN);
			response.setCacKhoanDgopN(cacKhoanDgopN);
			response.setTongCboThienN(tongCboThienN);
			response.setTongBcheDuocPdThienN(tongBcheDuocPdThienN);
			response.setTongQuyLuongCoTchatLuongThienN(tongQuyLuongCoTchatLuongThienN);
			response.setTongQuyLuongCoTchatLuongTheoBcheThienN(tongQuyLuongCoTchatLuongTheoBcheThienN);
			response.setLuongCbanThienN(luongCbanThienN);
			response.setPhuCapThienN(phuCapThienN);
			response.setCacKhoanDgopThienN(cacKhoanDgopThienN);
			response.setTongCboN1(tongCboN1);
			response.setTongBcheDuocPdN1(tongBcheDuocPdN1);
			response.setTongQuyLuongCoTchatLuongN1(tongQuyLuongCoTchatLuongN1);
			response.setTongQuyLuongCoTchatLuongTheoBcheN1(tongQuyLuongCoTchatLuongTheoBcheN1);
			response.setLuongCbanN1(luongCbanN1);
			response.setPhuCapN1(phuCapN1);
			response.setCacKhoanDgopN1(cacKhoanDgopN1);
			response.setTongCboN2(tongCboN2);
			response.setTongBcheDuocPdN2(tongBcheDuocPdN2);
			response.setTongQuyLuongCoTchatLuongN2(tongQuyLuongCoTchatLuongN2);
			response.setTongQuyLuongCoTchatLuongTheoBcheN2(tongQuyLuongCoTchatLuongTheoBcheN2);
			response.setLuongCbanN2(luongCbanN2);
			response.setPhuCapN2(phuCapN2);
			response.setCacKhoanDgopN2(cacKhoanDgopN2);
			response.setTongCboN3(tongCboN3);
			response.setTongBcheDuocPdN3(tongBcheDuocPdN3);
			response.setTongQuyLuongCoTchatLuongN3(tongQuyLuongCoTchatLuongN3);
			response.setTongQuyLuongCoTchatLuongTheoBcheN3(tongQuyLuongCoTchatLuongTheoBcheN3);
			response.setLuongCbanN3(luongCbanN3);
			response.setPhuCapN3(phuCapN3);
			response.setCacKhoanDgopN3(cacKhoanDgopN3);
			
			responses.add(response);
		}

		return responses;
	}
}
