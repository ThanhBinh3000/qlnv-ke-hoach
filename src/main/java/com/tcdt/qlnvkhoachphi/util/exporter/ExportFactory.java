package com.tcdt.qlnvkhoachphi.util.exporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.BCaoExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.QlnvKhvonphiBCaoKquaThienNhapMuaHangExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.QlnvKhvonphiBcaoKquaCtietThienExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.QlnvKhvonphiBcaoKquaThienXuatHangExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.QlnvKhvonphiBcaoThinhSdungDtoanPl1Exporter;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.QlnvKhvonphiBcaoThinhSdungDtoanPl2Exporter;
import com.tcdt.qlnvkhoachphi.util.exporter.baocao.QlnvKhvonphiBcaoThinhSdungDtoanPl3Exporter;
import com.tcdt.qlnvkhoachphi.util.exporter.capvonmuaban.CapvonMuaBanExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.dieuchinhdutoan.DchinhDuToanChiExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.LapThamDinhDuToanExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiDmVondtXdcbgd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiKhoachCtaoSchuaGd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiKhoachDtaoBoiDuongGd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiNcauPhiNhapXuatGd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiNxuatDtqgHnamVattuExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiTcDtoanChiDtqgGd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiTcKhoachBquanThocGaoHnamExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiTcKhoachcQuyLuongN1Exporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiTcNcauKhoachDtxdGd3nExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan.QlnvKhvonphiTcThopDtoanChiTxHnamExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi.PhanboDtoanChiExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi.QdGiaoDtChiExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtchiNsnnExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi.DchinhSauQtoanExportService;
import com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi.QlnvKhvonphiQtoanDtqgExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi.QtoanDtqgExportService;

import lombok.extern.slf4j.Slf4j;

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

	@Override
	public CapvonMuaBanExportService getKHoachVonExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
			case Constants.ExportDataType.CAP_VON_MUA_BAN:
				return context.getBean(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public DchinhDuToanChiExportService getDchinhDuToanChiExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
			case Constants.ExportDataType.QLNV_KHVONPHI_DC_DU_TOAN_CHI:
				return context.getBean(QlnvKhvonphiDchinhDuToanChiNsnnExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public BCaoExportService getBCaoExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
		    case Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1:
			    return context.getBean(QlnvKhvonphiBcaoThinhSdungDtoanPl1Exporter.class);
		    case Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2:
			    return context.getBean(QlnvKhvonphiBcaoThinhSdungDtoanPl2Exporter.class);
		    case Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3:
			    return context.getBean(QlnvKhvonphiBcaoThinhSdungDtoanPl3Exporter.class);
			case Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG:
				return context.getBean(QlnvKhvonphiBCaoKquaThienNhapMuaHangExporter.class);
			case Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG:
				return context.getBean(QlnvKhvonphiBcaoKquaThienXuatHangExporter.class);
			case Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_NHAP_MUA:
				return context.getBean(QlnvKhvonphiBcaoKquaCtietThienExporter.class);
			case Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_XUAT_HANG:
				return context.getBean(QlnvKhvonphiBcaoKquaCtietThienExporter.class);
			case Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_BQUAN_LAN_DAU:
				return context.getBean(QlnvKhvonphiBcaoKquaCtietThienExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public LapThamDinhDuToanExportService getLapThamDinhDuToanExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
		case Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N:// 01
			return context.getBean(QlnvKhvonphiDmVondtXdcbgd3nExporter.class);
		case Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU:// 02
			return context.getBean(QlnvKhvonphiNxuatDtqgHnamVattuExporter.class);
		case Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG:// 03
		case Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM:// 04
		case Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N:// 05
		case Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM:// 06
		case Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N:// 07
		case Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N:// 08
		case Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N:// 09
		case Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N:// 10
		case Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N:// 11
		case Constants.QLNV_KHVONPHI_CHI_TX_GD3N:// 12
		case Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N:// 13
			return context.getBean(QlnvKhvonphiNcauPhiNhapXuatGd3nExporter.class);
		case Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N:// 14
			return context.getBean(QlnvKhvonphiKhoachCtaoSchuaGd3nExporter.class);
		case Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N:// 15
			return context.getBean(QlnvKhvonphiKhoachDtaoBoiDuongGd3nExporter.class);
		// Loai bao cao Tổng cục
		case Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N:// 16
			return context.getBean(QlnvKhvonphiTcNcauKhoachDtxdGd3nExporter.class);
		case Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM:// 17
			return context.getBean(QlnvKhvonphiTcThopDtoanChiTxHnamExporter.class);
		case Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM:// 18
		case Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM:// 19
			return context.getBean(QlnvKhvonphiTcKhoachBquanThocGaoHnamExporter.class);
		case Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM:// 20
		case Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N:// 21
			return context.getBean(QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nExporter.class);
		case Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1:// 22
			return context.getBean(QlnvKhvonphiTcKhoachcQuyLuongN1Exporter.class);
		case Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N:// 23
			return context.getBean(QlnvKhvonphiTcDtoanChiDtqgGd3nExporter.class);
		case Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N:// 24
		case Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N:// 25
		case Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N:// 26
		case Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N:// 27
		case Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N:// 28
		case Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N:// 29
		case Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N:// 30
		case Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N:// 31
		case Constants.QLNV_KHVONPHI_TC_KHOACH_DTAO_BOI_DUONG_GD3N:// 32
		default:
			throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public PhanboDtoanChiExportService getPhanboDtoanChiExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
			case Constants.ExportDataType.PHANBO_GIAO_DTOAN_CHI:
				return context.getBean(QlnvKhvonphiPhanboDtchiNsnnExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public QtoanDtqgExportService getQtoanDtqgExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
			case Constants.ExportDataType.QTOAN_DTQG:
				return context.getBean(QlnvKhvonphiQtoanDtqgExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public DchinhSauQtoanExportService getDchinhSauQtoanExportService(String type) {
		log.info("Get exporter type =  {}", type);
		switch (type) {
			case Constants.ExportDataType.DCHINH_SAU_QTOAN:
				return context.getBean(QlnvKhvonphiDchinhSauQtoanDtqgExporter.class);
			default:
				throw new IllegalArgumentException("Không hỗ trợ export type");
		}
	}

	@Override
	public QdGiaoDtChiExportService getQdGiaoDtChiExportService(String type) {
		// TODO Auto-generated method stub
		return null;
	}






}
