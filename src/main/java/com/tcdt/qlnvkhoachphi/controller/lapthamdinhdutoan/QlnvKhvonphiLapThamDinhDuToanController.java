package com.tcdt.qlnvkhoachphi.controller.lapthamdinhdutoan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiLapThamDinhDuToanRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiLapThamDinhDuToanReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.TongHopReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiLapThamDinhDuToanSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiLapThamDinhDuToanExportService;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiLapThamDinhDuToanImportService;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiLapThamDinhDuToanServiceImpl;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_LAP_THAM_DINH_DU_TOAN)
@Slf4j
@Api(tags = "Lập thẩm định dự toán")
public class QlnvKhvonphiLapThamDinhDuToanController extends BaseController {
	@Autowired
	private QlnvKhvonphiLapThamDinhDuToanRepository qlnvKhvonphiLapThamDinhDuToanRepository;

	@Autowired
	private QlnvKhvonphiLapThamDinhDuToanServiceImpl qlnvKhvonphiLapThamDinhDuToanServiceImpl;

	@Autowired
	private QlnvKhvonphiLapThamDinhDuToanExportService qlnvKhvonphiLapThamDinhDuToanExportService;

	@Autowired
	private QlnvKhvonphiLapThamDinhDuToanImportService qlnvKhvonphiLapThamDinhDuToanImportService;

	@ApiOperation(value = "Lấy chi tiết thông tin lập thẩm định dự toán", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@RequestParam Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanServiceImpl.getDetail(id));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy danh sách lập thẩm định dự toán", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiLapThamDinhDuToanSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanServiceImpl.getList(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Thêm mới lập thẩm định dự toán", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiLapThamDinhDuToanReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanServiceImpl.create(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tổng hợp lập thẩm định dự toán", response = List.class)
	@PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> synthesis(@Valid @RequestBody TongHopReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanServiceImpl.synthetic(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xóa lập thẩm định dự toán", response = List.class)
	@DeleteMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			qlnvKhvonphiLapThamDinhDuToanServiceImpl.delete(ids);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}

	@ApiOperation(value = "Sửa lập thẩm định dự toán chi tiết", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiLapThamDinhDuToanReq qlnvBaoCaoReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanServiceImpl.update(qlnvBaoCaoReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sinh mã báo cáo lập thẩm định dự toán", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_BCAO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaBcao() {
		Resp resp = new Resp();
		try {
			int index = qlnvKhvonphiLapThamDinhDuToanRepository.getMaBaoCao();
			String maBaoCao = "BC".concat(String.valueOf(index));
			resp.setData(maBaoCao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}

	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanServiceImpl.function(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Export excel lập thẩm định dự toán", response = List.class)
	@PostMapping(value = PathConstants.URL_EXPORT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void exportToExcel(HttpServletResponse response, @RequestParam(required = false) List<String> type,
			@RequestParam Long id) {
		try {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "";

			for (String item : type) {
				if (item.equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
					headerValue = "attachment; filename=ke-hoach-danh-muc-von-dau-tu-xdcb-03-nam_"
							+ currentDateTime + ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
					headerValue = "attachment; filename=nhu-cau-nhap-xuat-hang-dtqg-hang-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
					headerValue = "attachment; filename=ke-hoach-bao-quan-hang-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
					headerValue = "attachment; filename=nhu-cau_xuat-hang-dtqg-vien-tro-cuu-tro-hang-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
					headerValue = "attachment; filename=ke-hoach-quy-tien-luong-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
					//TODO chưa làm
					headerValue = "attachment; filename=ke-hoach-quy-tien-luong-hang-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
					//TODO chưa làm
					headerValue = "attachment; filename=thuyet-minh-cac-de-tai-du-an-nghien-cuu-khoa-hoc-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
					//TODO chưa làm
					headerValue = "attachment; filename=xay-dung-van-ban-quy-pham-luat-du-tru-quoc-gia-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
					//TODO chưa làm
					headerValue = "attachment; filename=du-toan-chi-ung-dung-cntt-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
					//TODO chưa làm
					headerValue = "attachment; filename=du-toan-chi-mua-sam-may-moc-thiet-bi-chuyen-dung-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
					//TODO chưa làm
					headerValue = "attachment; filename=nhu-cau-chi-ngan-sach-nha-nuoc-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
					//TODO chưa làm
					headerValue = "attachment; filename=chi-thuong-xuyen-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
					//TODO chưa làm
					headerValue = "attachment; filename=nhu-cau-phi-nhap-xuat-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
					//TODO chưa làm
					headerValue = "attachment; filename=ke-hoach-cai-tao-sua-chua-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
					//TODO chưa làm
					headerValue = "attachment; filename=ke-hoach-dao-tao-boi-duong-03-nam_" + currentDateTime
							+ ".xlsx";
				}
				// TC
				else if (item.equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
					headerValue = "attachment; filename=nhu-cau-ke-hoach-DTXD-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
					headerValue = "attachment; filename=tong-hop-du-toan-chi-thuong-xuyen-hang-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19
					headerValue = "attachment; filename=ke-hoach-bao-quan-hang-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
				} else if (item
						.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
					headerValue = "attachment; filename=ke-hoach-du-toan-cai-tao-sua-chua-he-thong-kho-tang-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
					headerValue = "attachment; filename=ke-hoach-quy-tien-luong-nam-N+1_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
					headerValue = "attachment; filename=du-toan-chi-du-tru-quoc-gia-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
					headerValue = "attachment; filename=thuyet-minh-chi-cac-de-tai-du-an-nckh-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
					headerValue = "attachment; filename=xay-dung-van-ban-quy-pham-phap-luat-dtqg-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
					headerValue = "attachment; filename=du-toan-chi-ung-dung-cntt-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
					headerValue = "attachment; filename=du-toan-chi-mua-sam-may-moc-thiet-bi-chuyen-dung-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
					headerValue = "attachment; filename=tong-hop-nhu-cau-chi-nsnn-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
					headerValue = "attachment; filename=tong-hop-nhu-cau-chi-thuong-xuyen-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
					headerValue = "attachment; filename=chi-tiet-nhu-cau-chi-thuong-xuyen-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
					headerValue = "attachment; filename=tong-hop-muc-tieu-nhiem-vu-chu-yeu-va-nhu-cau-chi-tiet-moi-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				} else if (item.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 32
					headerValue = "attachment; filename=khoach-dtao-boi-duong-giai-doan-03-nam_" + currentDateTime
							+ ".xlsx";
				}
			}

			response.setHeader(headerKey, headerValue);
			qlnvKhvonphiLapThamDinhDuToanExportService.exportToExcel(response, type, id);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}
	}

	@ApiOperation(value = "Import excel lập thẩm định dự toán", response = List.class)
	@PostMapping(value = PathConstants.URL_IMPORT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> importExcel(@RequestPart("file") MultipartFile file, @RequestParam(required = true) String maLoaiBCao) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiLapThamDinhDuToanImportService.importExcel(file, maLoaiBCao));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
}