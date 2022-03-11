package com.tcdt.qlnvkhoachphi.controller.lapthamdinhdutoan;

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
import com.tcdt.qlnvkhoachphi.request.object.catalog.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_PA_GIAO_SO_KT)
@Slf4j
@Api(tags = "Phương án giao số kiểm tra")
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnController extends BaseController {

	@Autowired
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService;

	@ApiOperation(value = "Lấy chi tiết thông tin phương án giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailPa(@PathVariable("ids") String ids) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.detailPa(ids));
	}

	@ApiOperation(value = "Lấy danh sách thông tin phương án giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> findAll() {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.findAll());
	}

	@ApiOperation(value = "Lấy chi tiết thông tin giao số kiểm tra cho các đơn vị", response = List.class)
	@GetMapping(value = PathConstants.URL_CTIET_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailGiaoSo(@PathVariable("maGiao") String maGiao) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.detailGiaoSo(maGiao));
	}

	@ApiOperation(value = "Thêm mới phương án giao sổ kiểm tra", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.create(objReq, req));
	}

	@ApiOperation(value = "Xóa phương án giao sổ kiểm tra", response = List.class)
	@GetMapping(value = "/xoa/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.delete(ids, req));
	}

	@ApiOperation(value = "Sửa phương án giao sổ kiểm tra chi tiết", response = List.class)
	@PutMapping(value = "/cap-nhat", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq qlnvReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.update(qlnvReq, req));
	}

	@ApiOperation(value = "Tìm kiếm danh sách phương án", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colectionPa(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.colectionPa(objReq, req));
	}

	@ApiOperation(value = "Giao số kiểm tra NSNN", response = List.class)
	@PostMapping(value = PathConstants.URL_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> giaoSo(@Valid @RequestBody LstQlnvKhvonphiDsachGiaoSoReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.giaoSo(objReq, req));
	}

	@ApiOperation(value = "Tìm kiếm danh sách giao số", response = List.class)
	@PostMapping(value = PathConstants.URL_DSACH_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.colection(objReq, req));
	}

	@ApiOperation(value = "Sinh mã giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaGiaoSo() {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.genMaGiaoSo());

	}

	@ApiOperation(value = "Sinh mã phương án giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_PA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaPa() {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.genMaPa());

	}

	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.function(objReq, req));
	}

	@ApiOperation(value = "Nhập quyết định, công văn", response = List.class)
	@PutMapping(value = PathConstants.URL_ADD_QD_CV, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> addQdAndCv(@Valid @RequestBody QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.addQdAndCv(objReq, req));
	}

	@ApiOperation(value = "Xóa quyết định, công văn", response = List.class)
	@PutMapping(value = PathConstants.URL_DELETE_QD_CV, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> deleteQdCv(@Valid @RequestBody QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.deleteQdCv(objReq, req));
	}
}
