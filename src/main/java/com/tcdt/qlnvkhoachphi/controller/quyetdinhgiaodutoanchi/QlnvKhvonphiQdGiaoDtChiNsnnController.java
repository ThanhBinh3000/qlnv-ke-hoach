package com.tcdt.qlnvkhoachphi.controller.quyetdinhgiaodutoanchi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnService;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_QD_GIAO_DU_TOAN_CHI)
@Slf4j
@Api(tags = "Quyết định giao dự toán chi")
public class QlnvKhvonphiQdGiaoDtChiNsnnController extends BaseController {

	@Autowired
	private QlnvKhvonphiQdGiaoDtChiNsnnService qlnvKhvonphiQdGiaoDtChiNsnnService;

	@ApiOperation(value = "Thêm mới quyết định giao dự toán chi", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiQdGiaoDtChiNsnnService.create(objReq, req));
	}

	@ApiOperation(value = "Sửa quyết định giao dự toán chi", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiQdGiaoDtChiNsnnService.update(objReq, req));
	}

	@ApiOperation(value = "Xóa quyết định giao dự toán chi", response = List.class)
	@GetMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiQdGiaoDtChiNsnnService.delete(ids, req));
	}

	@ApiOperation(value = "Lấy chi tiết thông tin quyết định giao dự toán chi", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		return ResponseEntity.ok(qlnvKhvonphiQdGiaoDtChiNsnnService.detail(ids));
	}

	@ApiOperation(value = "Tìm kiếm danh sách quyết đinh giao dự toán chi", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colectionPa(@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiQdGiaoDtChiNsnnService.colectionPa(objReq));
	}

}
