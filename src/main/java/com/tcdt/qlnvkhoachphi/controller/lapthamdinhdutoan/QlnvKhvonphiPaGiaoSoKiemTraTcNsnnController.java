package com.tcdt.qlnvkhoachphi.controller.lapthamdinhdutoan;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportService;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_PA_GIAO_SO_KT)
@Api(tags = "Phương án giao số kiểm tra")
@Slf4j
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnController extends BaseController {

	@Autowired
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService;
	
	@Autowired
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportService qlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportService;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Lấy chi tiết thông tin phương án giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailPa(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.detailPa(ids));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Lấy danh sách thông tin phương án giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> findAll() {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.findAll());
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Lấy chi tiết thông tin giao số kiểm tra cho các đơn vị", response = List.class)
	@GetMapping(value = PathConstants.URL_CTIET_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailGiaoSo(@PathVariable("maGiao") String maGiao) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.detailGiaoSo(maGiao));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Thêm mới phương án giao sổ kiểm tra", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.create(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Xóa phương án giao sổ kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.delete(ids);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Sửa phương án giao sổ kiểm tra chi tiết", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq qlnvReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.update(qlnvReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Tìm kiếm danh sách phương án", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colectionPa(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.colectionPa(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Giao số kiểm tra NSNN", response = List.class)
	@PostMapping(value = PathConstants.URL_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> giaoSo(@Valid @RequestBody LstQlnvKhvonphiDsachGiaoSoReq objReq) {
		Resp resp = new Resp();
		try {
			qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.giaoSo(objReq);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Tìm kiếm danh sách giao số", response = List.class)
	@PostMapping(value = PathConstants.URL_DSACH_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.colection(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Sinh mã giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaGiaoSo() {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.genMaGiaoSo());
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Sinh mã phương án giao số kiểm tra", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_PA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaPa() {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.genMaPa());
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.function(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Nhập quyết định, công văn", response = List.class)
	@PutMapping(value = PathConstants.URL_ADD_QD_CV, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> addQdAndCv(@Valid @RequestBody QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.addQdAndCv(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Xóa quyết định, công văn", response = List.class)
	@PutMapping(value = PathConstants.URL_DELETE_QD_CV, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> deleteQdCv(@Valid @RequestBody QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnService.deleteQdCv(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	
	@ApiOperation(value = "Import excel phương án giao số kiểm tra", response = List.class)
	@PostMapping(value = PathConstants.URL_IMPORT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> importExcel(@RequestPart("file") MultipartFile file) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportService.importExcel(file));
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
