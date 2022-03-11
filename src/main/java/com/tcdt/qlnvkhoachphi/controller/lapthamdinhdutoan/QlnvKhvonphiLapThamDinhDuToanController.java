package com.tcdt.qlnvkhoachphi.controller.lapthamdinhdutoan;

import java.util.List;
import java.util.Optional;

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
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiLapThamDinhDuToanReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.TongHopReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiLapThamDinhDuToanSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan.QlnvKhvonphiLapThamDinhDuToanServiceImpl;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiLapThamDinhDuToan;
import com.tcdt.qlnvkhoachphi.util.PathConstants;
import com.tcdt.qlnvkhoachphi.util.Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_LAP_THAM_DINH_DU_TOAN)
@Slf4j
@Api(tags = "Lập thẩm định dự toán")
public class QlnvKhvonphiLapThamDinhDuToanController extends BaseController {
	@Autowired
	private QlnvKhvonphiRepository qlnvBaoCaoRepository;

	@Autowired
	private QlnvDmService qlnvDmService;
	
	@Autowired
	private QlnvKhvonphiLapThamDinhDuToanServiceImpl qlnvKhvonphiLapThamDinhDuToanServiceImpl;

	@ApiOperation(value = "Lấy chi tiết thông tin báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		return ResponseEntity.ok(qlnvKhvonphiLapThamDinhDuToanServiceImpl.getDetail(ids));
	}

	@ApiOperation(value = "Lấy danh sách bao cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiLapThamDinhDuToanSearchReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiLapThamDinhDuToanServiceImpl.getList(objReq));
	}

	@Transactional(rollbackFor = {Exception.class, Throwable.class})
	@ApiOperation(value = "Thêm mới báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiLapThamDinhDuToanReq objReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiLapThamDinhDuToanServiceImpl.create(objReq, getUserInfo(req)));
	}

	@ApiOperation(value = "Tổng hợp báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> synthesis(@Valid @RequestBody TongHopReq objReq, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiLapThamDinhDuToanServiceImpl.synthetic(objReq));
	}

	@Transactional(rollbackFor = {Exception.class, Throwable.class})
	@ApiOperation(value = "Xóa báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiLapThamDinhDuToanServiceImpl.delete(ids, getUserInfo(req)));
	}

	@Transactional(rollbackFor = {Exception.class, Throwable.class})
	@ApiOperation(value = "Sửa báo cáo chi tiết", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiLapThamDinhDuToanReq qlnvBaoCaoReq,
			HttpServletRequest req) {
		return ResponseEntity.ok(qlnvKhvonphiLapThamDinhDuToanServiceImpl.update(qlnvBaoCaoReq, getUserInfo(req)));
	}

	@ApiOperation(value = "Sinh mã báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_BCAO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaBcao() {
		Resp resp = new Resp();
		try {
			int index = qlnvBaoCaoRepository.getMaBaoCao();
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
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			UserInfo userInfo = getUserInfo(req);

			Optional<QlnvKhvonphiLapThamDinhDuToan> optionalBC = qlnvBaoCaoRepository.findById(objReq.getId());
			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("ID Báo cáo không tồn tại!");
			}
			QlnvKhvonphiLapThamDinhDuToan qlnvBaoCao = optionalBC.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnvBaoCao.getMaDvi()));

			// kiểm tra quyền thao tác
			qlnvBaoCao.setTrangThai(
					Utils.function(qlnvBaoCao.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));
			// update trạng thái báo cáo
			qlnvBaoCaoRepository.save(qlnvBaoCao);

			resp.setData(qlnvBaoCao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}
}