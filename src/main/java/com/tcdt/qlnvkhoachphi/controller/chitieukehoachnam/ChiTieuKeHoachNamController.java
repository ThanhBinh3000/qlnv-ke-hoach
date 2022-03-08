package com.tcdt.qlnvkhoachphi.controller.chitieukehoachnam;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamExportService;
import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamImportService;
import com.tcdt.qlnvkhoachphi.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/chi-tieu-ke-hoach-nam")
@Slf4j
@Api(tags = "Quản lý chỉ tiêu kế hoạch năm")
public class ChiTieuKeHoachNamController extends BaseController {
	@Autowired
	private ChiTieuKeHoachNamService chiTieuKeHoachNamService;

	@Autowired
	private ChiTieuKeHoachNamImportService importSv;

	@Autowired
	private ChiTieuKeHoachNamExportService chiTieuKeHoachNamExportSv;

	@ApiOperation(value = "Tạo mới Chỉ tiêu kế hoạch năm", response = List.class)
	@PostMapping
	public final ResponseEntity<Resp> create(@RequestBody ChiTieuKeHoachNamReq req) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.createQd(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Chi tiết chỉ tiêu kế hoạch năm", response = List.class)
	@GetMapping("/{id}")
	public final ResponseEntity<Resp> getDetailQd(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.detailQd(id));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
			log.error("error", e);
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa chỉ tiêu kế hoạch năm", response = List.class)
	@PutMapping()
	public final ResponseEntity<Resp> updateQd(@RequestBody ChiTieuKeHoachNamReq req) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.updateQd(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Import file kế hoạch lương thực, muối, vật tư", response = List.class)
	@PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> importKh(@RequestPart("file") MultipartFile file) {
		Resp resp = new Resp();
		try {
			resp.setData(importSv.importKeHoach(file));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Gửi duyệt/Phê duyệt/Từ chối chỉ tiêu kế hoạch năm", response = List.class)
	@PutMapping("/status")
	public final ResponseEntity<Resp> updateStatus(@RequestBody StatusReq req) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.updateStatus(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tạo mới quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
	@PostMapping("/quyet-dinh-dieu-chinh")
	public final ResponseEntity<Resp> createQdDc(@RequestBody QdDcChiTieuKeHoachNamReq req) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.createQdDc(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
	@PutMapping("/quyet-dinh-dieu-chinh")
	public final ResponseEntity<Resp> updateQdDc(@RequestBody QdDcChiTieuKeHoachNamReq req) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.updateQdDc(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Import file điều chỉnh kế hoạch lương thực, muối, vật tư", response = List.class)
	@PostMapping(value = "/quyet-dinh-dieu-chinh/import", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> importQdDc(@RequestPart("file") MultipartFile file) {
		Resp resp = new Resp();
		try {
			resp.setData(importSv.importKeHoach(file));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Chi tiết quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
	@GetMapping("/quyet-dinh-dieu-chinh/{id}")
	public final ResponseEntity<Resp> getDetailQdDc(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.detailQdDc(id));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
			log.error("error", e);
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu chỉ tiêu kế hoạch năm", response = List.class)
	@ResponseStatus(HttpStatus.OK)
	@GetMapping()
	public final ResponseEntity<Resp> searchQd(SearchChiTieuKeHoachNamReq req, Pageable pageable) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamExportSv.searchQd(req, pageable));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/quyet-dinh-dieu-chinh")
	public final ResponseEntity<Resp> searchQdDc(SearchChiTieuKeHoachNamReq req, Pageable pageable) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamExportSv.searchQdDc(req, pageable));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
		}
		return ResponseEntity.ok(resp);
	}
}
