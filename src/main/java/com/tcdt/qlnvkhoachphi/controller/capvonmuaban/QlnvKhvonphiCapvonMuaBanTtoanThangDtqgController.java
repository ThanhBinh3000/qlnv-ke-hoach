package com.tcdt.qlnvkhoachphi.controller.capvonmuaban;

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
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgServiceImpl;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = PathConstants.URL_CAP_VON_DTQG)
@Api(tags = "Cấp vốn mua, bán, thanh toán tiền hàng DTQG")
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgController extends BaseController {

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgServiceImpl qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl;

	@ApiOperation(value = "Lấy chi tiết thông tin cấp vốn", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		return ResponseEntity.ok(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.getDetail(ids));
	}

	@ApiOperation(value = "Lấy danh sách cấp vốn", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.getList(objReq));
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Thêm mới cấp vốn", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.create(objReq, getUserInfo(req)));
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Xóa cấp vốn", response = List.class)
	@GetMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.delete(ids, getUserInfo(req)));
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Sửa cấp vốn", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq qlnvBaoCaoReq,
			HttpServletRequest req) {
		return ResponseEntity
				.ok(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.update(qlnvBaoCaoReq, getUserInfo(req)));
	}

	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.function(objReq, getUserInfo(req)));
	}
}