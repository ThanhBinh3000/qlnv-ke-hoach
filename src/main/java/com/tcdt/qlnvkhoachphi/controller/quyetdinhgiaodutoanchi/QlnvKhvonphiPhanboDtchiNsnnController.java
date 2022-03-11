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
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtchiNsnnService;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_PHANBO_DTOAN_CHI)
@Slf4j
@Api(tags = "Phân bổ dự toán chi")
public class QlnvKhvonphiPhanboDtchiNsnnController extends BaseController{
	
	@Autowired
	private QlnvKhvonphiPhanboDtchiNsnnService qlnvKhvonphiPhanboDtchiNsnnService;
	
	@ApiOperation(value = "Thêm mới phân bổ dự toán chi", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiPhanboDtoanChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPhanboDtchiNsnnService.create(objReq, req));
	}
	
	@ApiOperation(value = "Sửa phân bổ dự toán chi", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiPhanboDtoanChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPhanboDtchiNsnnService.update(objReq, req));
	}
	
	@ApiOperation(value = "Xóa phân bổ dự toán chi", response = List.class)
	@GetMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPhanboDtchiNsnnService.delete(ids, req));
	}
	
	@ApiOperation(value = "Lấy chi tiết phân bổ dự toán chi", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		return ResponseEntity.ok(qlnvKhvonphiPhanboDtchiNsnnService.detail(ids));
	}
	
	@ApiOperation(value = "Tìm kiếm danh sách phân bổ dự toán dự toán chi", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiPhanboDtoanChiNsnnSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPhanboDtchiNsnnService.colection(objReq, req));
	}
	
	
	@ApiOperation(value = "Tổng hợp kế hoạch phân bổ, giao dự toán theo đơn vị thực hiện", response = List.class)
	@PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> synthesis(@Valid @RequestBody String maDviThien, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPhanboDtchiNsnnService.synthesis(maDviThien, req));
	}
	
}
