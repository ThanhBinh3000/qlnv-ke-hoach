package com.tcdt.qlnvkhoachphi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDchinhDuToanChiNsnnCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDchinhDuToanChiNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiDchinhDuToanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmDonViService;
import com.tcdt.qlnvkhoachphi.service.QlnvKhvonphiDchinhDuToanChiNsnnService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDchinhDuToanChiNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDchinhDuToanChiNsnnCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_DIEU_CHINH_DU_TOAN_CHI)
@Slf4j
@Api(tags = "Báo cáo điều chỉnh dự toán chi")
public class QlnvKhvonphiDchinhDuToanChiNsnnController extends BaseController{
	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnService khvonphiDchinhDuToanChiNsnnService;

	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnRepository qlnvKhvonphiDchinhDuToanChiNsnnRepository;

	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnCtietRepository qlnvKhvonphiDchinhDuToanChiNsnnCtietRepository;

	@Autowired
	private QlnvDmDonViService qlnvDmDonViService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;


	@ApiOperation(value = "Lấy chi tiết thông tin báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn = khvonphiDchinhDuToanChiNsnnService.getBaoCaoById(Long.parseLong(ids));

			// mapping response
			QlnvKhvonphiDchinhDuToanChiNsnnResp qlnvKhvonphiDchinhDuToanChiNsnnResp = new ModelMapper()
					.map(qlnvKhvonphiDchinhDuToanChiNsnn, QlnvKhvonphiDchinhDuToanChiNsnnResp.class);



				// lấy danh sách file đính kèm
//				List<Long> lstDataId = Arrays.asList(qlnvKhvonphiDchinhDuToanChiNsnn.getVanBan().split(",")).stream()
//						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
//				ArrayList<FileDinhKem> lstFile = new ArrayList<>();
//				for (Long dataId : lstDataId) {
//					FileDinhKem fileDinhKem = fileDinhKemRepository.findByDataId(dataId);
//					lstFile.add(fileDinhKem);
//				}
//				qlnvKhvonphiDchinhDuToanChiNsnnResp.setLstFile(lstFile);


			if (ObjectUtils.isEmpty(qlnvKhvonphiDchinhDuToanChiNsnnResp)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnResp);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}


//	@ApiOperation(value = "Lấy danh sách bao cáo", response = List.class)
//	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnSearchReq objReq, HttpServletRequest req) {
//		Resp resp = new Resp();
//		try {
//			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
//			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
//			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
//			Page<QlnvKhvonphiDchinhDuToanChiNsnn> data = qlnvKhvonphiDchinhDuToanChiNsnnRepository.selectParams(objReq.getNamHienHanh(), objReq.getNgayTaoTu(),
//					objReq.getNgayTaoDen(), objReq.getMaDvi(), objReq.getTrangThai(), pageable);
//
//			for (QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn : data) {
//				QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(qlnvKhvonphiDchinhDuToanChiNsnn.getId());
//				if (!ObjectUtils.isEmpty(qlnvDmDonvi)) {
//					qlnvKhvonphiDchinhDuToanChiNsnn.setTenDvi(qlnvDmDonvi.getTenDvi());
//				}
//				qlnvKhvonphiDchinhDuToanChiNsnn.setTenTrangThai(Utils.getTenTrangThai(qlnvKhvonphiDchinhDuToanChiNsnn.getTrangThai()));
//			}
//
//			resp.setData(data);
//			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
//			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
//		} catch (Exception e) {
//			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
//			resp.setMsg(e.getMessage());
//			log.error(e.getMessage());
//		}
//		return ResponseEntity.ok(resp);
//	}


	@ApiOperation(value = "Thêm mới báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnReq objReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			// check role nhân viên mới được tạo
			UserInfo userInfo = getUserInfo(req);
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
				}
			}
			QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn = new ModelMapper().map(objReq,
					QlnvKhvonphiDchinhDuToanChiNsnn.class);
			qlnvKhvonphiDchinhDuToanChiNsnn.setTrangThai(Constants.TT_BC_1);// Đang soạn
			qlnvKhvonphiDchinhDuToanChiNsnn.setNgayTao(new Date());
			qlnvKhvonphiDchinhDuToanChiNsnn.setNguoiTao(getUserName(req));

//			QlnvKhvonphiDchinhDuToanChiNsnn createCheck = qlnvKhvonphiDchinhDuToanChiNsnnRepository
//					.save(qlnvKhvonphiDchinhDuToanChiNsnn);
			// quản lý file đính kèm:


			List<QlnvKhvonphiDchinhDuToanChiNsnnCtiet> dataMap = new ArrayList<>();
			for (Object item : objReq.getChiNsnnCtietReqs()) {
				QlnvKhvonphiDchinhDuToanChiNsnnCtiet duToanChiNsnnCtiet = new ModelMapper().map(item,
						QlnvKhvonphiDchinhDuToanChiNsnnCtiet.class);
				dataMap.add(duToanChiNsnnCtiet);
			}
			qlnvKhvonphiDchinhDuToanChiNsnn.setListCtiet(dataMap);
			qlnvKhvonphiDchinhDuToanChiNsnnRepository.save(qlnvKhvonphiDchinhDuToanChiNsnn);
//			qlnvKhvonphiDchinhDuToanChiNsnnCtietRepository.saveAll(dataMap);

//			for (QlnvKhvonphiDchinhDuToanChiNsnnCtiet qlnvKhvonphiDchinhDuToanChiNsnnCtiet : dataMap) {
//
//				dataMap.add(qlnvKhvonphiDchinhDuToanChiNsnnCtiet);
//			}


			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnn);
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
