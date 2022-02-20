package com.tcdt.qlnvkhoachphi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmKHoachVonRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmDonViService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKHoachVon;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
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
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository;

	@Autowired
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity;

	@Autowired
	private QlnvDmDonViService qlnvDmDonViService;

	@Autowired
	private QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository;

	@Autowired
	private QlnvDmKHoachVonRepository qlnvDmKHoachVonRepository;

//	@ApiOperation(value = "Thêm mới phương án giao sổ kiểm tra", response = List.class)
//	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq, HttpServletRequest req){
//		Resp resp = new Resp();
//		try {
//			UserInfo userInfo = getUserInfo(req);
//			for (Role role : userInfo.getRoles()) {
//				if (!role.getId().equals(Constants.NHAN_VIEN)) {
//					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
//				}
//			}
//
//			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn giaoSoKiemTraTcNsnn = new ModelMapper()
//					.map(objReq, QlnvKhvonphiPaGiaoSoKiemTraTcNsnn.class);
//
//			giaoSoKiemTraTcNsnn.setNgayTao(new Date());
//			giaoSoKiemTraTcNsnn.setNguoiTao(userInfo.getUsername());
//			giaoSoKiemTraTcNsnn.setTrangThai(Constants.TT_BC_1);// Đang soạn
//
//			 // TODO: Chưa xử lý trường soQd và soCv
//
//			// QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet
//			ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> dataMapCtiet = new ArrayList<>();
//			ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi> dataMapCtietDvi = new ArrayList<>();
//			for(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReq object : objReq.getLstPaGiaoSoKiemTraTcNsnnCtiet()) {
//				QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet giaoSoKTracTiet =
//						new ModelMapper().map(object, QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet.class);
//				giaoSoKTracTiet.setQlnvKhvonphiPaId(giaoSoKiemTraTcNsnn.getId());
//
//				// QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi
//				for(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDviReq qlCtietDvi: object.getLstPaGiaoSoKiemTraTcNsnnCtietDvi()) {
//					QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi qlTcNsnnCtietDvi
//						= new ModelMapper().map(qlCtietDvi, QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietDvi.class);
//					qlTcNsnnCtietDvi.setQlnvKhvonphiPaCtietId(giaoSoKTracTiet.getId());
//					dataMapCtietDvi.add(qlTcNsnnCtietDvi);
//				}
//				giaoSoKTracTiet.setListCtietDvi(dataMapCtietDvi);
//				dataMapCtiet.add(giaoSoKTracTiet);
//
//			}
//			giaoSoKiemTraTcNsnn.setListCtiet(dataMapCtiet);
//
//			giaoSoKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(giaoSoKiemTraTcNsnn);
//
//			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
//			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
//		}catch (Exception e) {
//			// TODO: handle exception
//			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
//			resp.setMsg(e.getMessage());
//			log.error(e.getMessage());
//		}
//		return ResponseEntity.ok(resp);
//	}

	@ApiOperation(value = "Xóa phương án giao sổ kiểm tra", response = List.class)
	@GetMapping(value = "/xoa/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findById(Long.parseLong(ids));

			if (!optional.isPresent()) {
				throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
			}

			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlGiaoSoKiemTraTcNsnn = optional.get();

			UserInfo userInfo = getUserInfo(req);

			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(qlGiaoSoKiemTraTcNsnn.getMaDvi());

			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!qlGiaoSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_1)) {
						throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			}

			qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.delete(qlGiaoSoKiemTraTcNsnn);

			ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> arrayList = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity
					.findByQlnvKhvonphiPaGiaoSoKiemTraTcNsnnId(qlGiaoSoKiemTraTcNsnn.getId());
			if (!arrayList.isEmpty()) {
				qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.deleteAll(arrayList);
			}

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

//	@ApiOperation(value = "Sửa phương án giao sổ kiểm tra chi tiết", response = List.class)
//	@PutMapping(value = "/cap-nhat", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq qlnvReq, HttpServletRequest req) {
//		Resp resp = new Resp();
//		try {
//			Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optionalPa
//				= qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.findById(qlnvReq.getId());
//
//			if(!optionalPa.isPresent()) {
//				throw new UnsupportedOperationException(
//						"Mã ID: " + qlnvReq.getId() + " của báo cáo không tồn tại");
//			}
//			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlSoKiemTraTcNsnn
//				= optionalPa.get();
//
//			UserInfo userInfo = getUserInfo(req);
//			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(qlSoKiemTraTcNsnn.getId());
//			if (qlnvDmDonvi == null) {
//				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
//			}
//
//			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
//				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
//			}
//
//			for (Role role : userInfo.getRoles()) {
//				if (role.getId().equals(Constants.NHAN_VIEN)) {
//					if (!qlSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_1)
//							&& !qlSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_3)) {
//						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
//					}
//				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
//					if (!qlSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_5)) {
//						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
//					}
//				} else if (role.getId().equals(Constants.LANH_DAO)) {
//					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
//				}
//			}
//
//			qlSoKiemTraTcNsnn.setNguoiSua(userInfo.getUsername());
//			qlSoKiemTraTcNsnn.setNgaySua(new Date());
//
//			if(!StringUtils.isEmpty(qlnvReq.getListIdDeletes())) {
//				List<Long> items = Arrays.asList(qlnvReq.getListIdDeletes().split(",")).stream()
//						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
//				qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.deleteWithIds(items);
//			}
//
//			ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> arrayList = new ArrayList<>();
//
//			for(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReq qlNsnnCtietReq : qlnvReq.getLstPaGiaoSoKiemTraTcNsnnCtiet()) {
//				if(qlNsnnCtietReq.getId() != null) {
//					Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> optional
//						= qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.findById(qlNsnnCtietReq.getId());
//
//					QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet qlTcNsnnCtiet
//						= optional.get();
//					qlTcNsnnCtiet.setQlnvKhvonphiPaId(qlSoKiemTraTcNsnn.getId());
//
//					updateObjectToObject(qlTcNsnnCtiet, qlNsnnCtietReq);
//
//					arrayList.add(qlTcNsnnCtiet);
//				}else {
//					QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet qlNsnnCtiet
//						= new ModelMapper().map(qlNsnnCtietReq, QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet.class);
//
//					qlNsnnCtiet.setQlnvKhvonphiPaId(qlSoKiemTraTcNsnn.getId());
//					arrayList.add(qlNsnnCtiet);
//				}
//				qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.saveAll(arrayList);
//			}
//			qlSoKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlSoKiemTraTcNsnn);
//
//			resp.setData(qlSoKiemTraTcNsnn);
//			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
//			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
//		}catch(Exception e) {
//			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
//			resp.setMsg(e.getMessage());
//			log.error(e.getMessage());
//		}
//		return ResponseEntity.ok(resp);
//	}
//
	@ApiOperation(value = "Lấy danh sách phương án", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colectionPa(@Valid @RequestBody QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq,
			HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> data = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.selectParams(pageable);

//			for (QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvKhvonphiPaGiaoSo : data) {
//
//				for (QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet qlnvPaGiaoSoCtiet : qlnvKhvonphiPaGiaoSo.getListCtiet()) {
//
//					qlnvPaGiaoSoCtiet.setId(id);
//
//					qlnvPaGiaoSoCtiet.setListCtietDvi(listCtietDvi);
//
//				}
//			}

//			for (QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvKhvonphiPaGiaoSo : data) {
//				//get tên các mã
//				QlnvDmDonvi qlnvDmDonviTao = qlnvDmDonViService.getDonViById(Long.valueOf(qlnvKhvonphiPaGiaoSo.getMaDviTao()));
//				if (!ObjectUtils.isEmpty(qlnvDmDonviTao)) {
//					qlnvKhDsachGiaoSo.setTenDviTao(qlnvDmDonviTao.getTenDvi());
//				}
//			}

			resp.setData(data);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Giao số kiểm tra NSNN", response = List.class)
	@PostMapping(value = PathConstants.URL_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> giaoSo(@Valid @RequestBody LstQlnvKhvonphiDsachGiaoSoReq objReq,
			HttpServletRequest req) {
		Resp resp = new Resp();
		try {

			// TODO check quyền được giao (chưa biết có role "giao" hay ko?)
			// check xem bản ghi phương án có số QD thì mới được giao
			String maPa = objReq.getLstQlnvKhvonphiDsachGiaoSo().get(0).getMaPa();
			// TODO get QlnvKhvonphiPaGiaoSoKiemTraTcNsnn theo mã phương án
			Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> qlnvPaGiaoSoOpt = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findById(null);
			if (!qlnvPaGiaoSoOpt.isPresent()) {
				throw new UnsupportedOperationException("Phương án chưa có số quyết định");
			}

			UserInfo userInfo = getUserInfo(req);
//			for (Role role : userInfo.getRoles()) {
//				if (!role.getId().equals(Constants.NHAN_VIEN)) {
//					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
//				}
//			}

			ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> lstQlnvKhvonphiDsachGiaoSo = new ArrayList<>();
			for (QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnReq item : objReq.getLstQlnvKhvonphiDsachGiaoSo()) {
				QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn qlnvDsachGiaoSo = new ModelMapper().map(item,
						QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn.class);

				qlnvDsachGiaoSo.setNgayTao(new Date());
				qlnvDsachGiaoSo.setNguoiTao(userInfo.getUsername());
				// check nếu đã có > 1 bản ghi giao cho đơn vị trong năm 1, thì trạng thái là
				// "giao lại"
				ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> lstqlnvDsachGiaoSo = qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository
						.checkBanTinDaGiao(qlnvDsachGiaoSo.getMaPa(), qlnvDsachGiaoSo.getMaDviNhan(),
								qlnvDsachGiaoSo.getNamGiao());
				if (lstqlnvDsachGiaoSo.isEmpty()) {
					qlnvDsachGiaoSo.setTrangThai(Constants.TT_DA_GIAO);// Đã giao
				} else {
					qlnvDsachGiaoSo.setTrangThai(Constants.TT_GIAO_LAI);// Giao lại
				}

				lstQlnvKhvonphiDsachGiaoSo.add(qlnvDsachGiaoSo);
			}

			qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository.saveAll(lstQlnvKhvonphiDsachGiaoSo);

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy danh sách giao số", response = List.class)
	@PostMapping(value = PathConstants.URL_DSACH_GIAO_SO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq,
			HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> data = qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository
					.selectParams(objReq.getNamGiao(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(),
							objReq.getMaDviTao(), objReq.getMaDviNhan(), objReq.getMaGiao(), objReq.getMaPa(),
							objReq.getTrangThaiGiao(), pageable);

			for (QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn qlnvKhDsachGiaoSo : data) {
				// get tên các mã
				QlnvDmDonvi qlnvDmDonviTao = qlnvDmDonViService.getDonViById(qlnvKhDsachGiaoSo.getMaDviTao());
				if (!ObjectUtils.isEmpty(qlnvDmDonviTao)) {
					qlnvKhDsachGiaoSo.setTenDviTao(qlnvDmDonviTao.getTenDvi());
				}

				QlnvDmDonvi qlnvDmDonviNhan = qlnvDmDonViService.getDonViById(qlnvKhDsachGiaoSo.getMaDviNhan());
				if (!ObjectUtils.isEmpty(qlnvDmDonviNhan)) {
					qlnvKhDsachGiaoSo.setTenDviNhan(qlnvDmDonviNhan.getTenDvi());
				}

				Optional<QlnvDmKHoachVon> qlnvDmNoiDung = qlnvDmKHoachVonRepository
						.findById(Long.valueOf(qlnvKhDsachGiaoSo.getMaNoiDung()));
				if (!qlnvDmNoiDung.isPresent()) {
					qlnvKhDsachGiaoSo.setTenNoiDung(qlnvDmNoiDung.get().getTenDm());
				}

				Optional<QlnvDmKHoachVon> qlnvDmNhom = qlnvDmKHoachVonRepository
						.findById(Long.valueOf(qlnvKhDsachGiaoSo.getMaNhom()));
				if (!qlnvDmNhom.isPresent()) {
					qlnvKhDsachGiaoSo.setTenNhom(qlnvDmNhom.get().getTenDm());
				}

				if (qlnvKhDsachGiaoSo.getTrangThai().equals(Constants.TT_DA_GIAO)) {
					qlnvKhDsachGiaoSo.setTenTrangThai("Đã giao");
				} else {
					qlnvKhDsachGiaoSo.setTenTrangThai("Giao lại");
				}

				Optional<QlnvDmKHoachVon> qlnvDmDviTien = qlnvDmKHoachVonRepository
						.findById(Long.valueOf(qlnvKhDsachGiaoSo.getMaDviTien()));
				if (!ObjectUtils.isEmpty(qlnvDmDviTien)) {
					qlnvKhDsachGiaoSo.setTenDviTien(qlnvDmDviTien.get().getTenDm());
				}
			}

			resp.setData(data);
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
