package com.tcdt.qlnvkhoachphi.controller.dieuchinhdutoan;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDchinhDuToanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan.IQlnvKhvonphiDchinhDuToanChiNsnnService;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = PathConstants.URL_DIEU_CHINH_DU_TOAN_CHI)
@Api(tags = "Điều chỉnh dự toán chi")
public class QlnvKhvonphiDchinhDuToanChiNsnnController extends BaseController {

	@Autowired
	private IQlnvKhvonphiDchinhDuToanChiNsnnService qlnvKhvonphiDchinhDuToanChiNsnnService;


	@ApiOperation(value = "Lấy chi tiết thông tin báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		return ResponseEntity.ok(qlnvKhvonphiDchinhDuToanChiNsnnService.getDetail(ids));
	}

	@ApiOperation(value = "Lấy danh sách báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiDchinhDuToanChiNsnnService.getList(objReq));
	}

	@ApiOperation(value = "Tổng hợp báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> synthesis(@Valid @RequestBody TongHopDchinhDuToanChiNsnnReq objReq, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiDchinhDuToanChiNsnnService.synthetic(objReq));
	}

	@Transactional(rollbackFor = {Exception.class, Throwable.class})
	@ApiOperation(value = "Thêm mới báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiDchinhDuToanChiNsnnService.add(objReq, getUserInfo(req)));
	}

	@Transactional(rollbackFor = {Exception.class, Throwable.class})
	@ApiOperation(value = "Sửa báo cáo chi tiết", response = List.class)
	@PutMapping(value = "/cap-nhat", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnReq qlnvBaoCaoReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiDchinhDuToanChiNsnnService.update(qlnvBaoCaoReq, getUserInfo(req)));
	}

	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiDchinhDuToanChiNsnnService.function(objReq, getUserInfo(req)));
	}

}
