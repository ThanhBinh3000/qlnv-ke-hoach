package com.tcdt.qlnvkhoachphi.service.capvonmuaban;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqg;
import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgServiceImpl extends BaseController
		implements QlnvKhvonphiCapvonMuaBanTtoanThangDtqgService {
	@Autowired
	private QlnvDmService qlnvDmService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository;

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository;

	@Override
	public Resp getDetail(String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> optional = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
					.findById(Long.parseLong(ids));

			if (!optional.isPresent()) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = optional.get();

			QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp qlnvCapvonMuaBanResp = new ModelMapper().map(qlnvCapvonMuaBan,
					QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp.class);

			ArrayList<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> lstCapvonMuaBanCtiet = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository
					.findQlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietByQlnvKhvonphiCapVonId(qlnvCapvonMuaBan.getId());

			qlnvCapvonMuaBanResp.setLstCtiet(lstCapvonMuaBanCtiet);

			// lấy danh sách file đính kèm
			ArrayList<FileDinhKem> lstFile = new ArrayList<>();
			ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository
					.findFileDinhKemByQlnvId(qlnvCapvonMuaBan.getId());
			lstFile.addAll(fileDinhKem);
			qlnvCapvonMuaBanResp.setLstFile(lstFile);
			if (ObjectUtils.isEmpty(qlnvCapvonMuaBanResp)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			resp.setData(qlnvCapvonMuaBanResp);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return resp;
	}

	@Override
	public Resp getList(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> data = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
					.selectParams(objReq.getMaDviCap(), objReq.getMaDviCap(), objReq.getMaLoaiGnhan(),
							objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getTrangThai(), objReq.getSoQd(),
							pageable);
//			Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> data = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
//					.selectParams(objReq.getMaDviCap(), objReq.getMaDviCap(), objReq.getMaLoaiGnhan(),
//							objReq.getNgayQuyetDinh(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(),
//							objReq.getTrangThai(), objReq.getSoQd(), pageable);

			resp.setData(data);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return resp;
	}

	@Override
	public Resp create(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			// check role nhân viên mới được tạo
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
				}
			}
			QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = new ModelMapper().map(objReq,
					QlnvKhvonphiCapvonMuaBanTtoanThangDtqg.class);
			qlnvCapvonMuaBan.setTrangThai(Constants.TT_BC_1);// Đang soạn
			qlnvCapvonMuaBan.setNgayTao(new Date());
			qlnvCapvonMuaBan.setNguoiTao(userInfo.getUsername());

			qlnvCapvonMuaBan = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

			// Thêm mới file đính kèm:
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			if (!objReq.getFileDinhKems().isEmpty()) {
				for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
					FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG);
					dinhKem.setQlnvId(qlnvCapvonMuaBan.getId());
					fileDinhKems.add(dinhKem);
				}
				fileDinhKemRepository.saveAll(fileDinhKems);
			}

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return resp;
	}

	@Override
	public Resp delete(String ids, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> capvonMuaBanOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
					.findById(Long.parseLong(ids));

			if (!capvonMuaBanOpt.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + ids + " của quyết định giao dự toán chi không tồn tại");
			}

			QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = capvonMuaBanOpt.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnvCapvonMuaBan.getMaDviNhan()));
			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!qlnvCapvonMuaBan.getTrangThai().equals(Constants.TT_BC_1)) {
						throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			}

			// Xóa file dinh kem
			ArrayList<FileDinhKem> fileDinhKems = fileDinhKemRepository
					.findFileDinhKemByQlnvId(qlnvCapvonMuaBan.getId());

			if (!fileDinhKems.isEmpty()) {
				fileDinhKemRepository.deleteAll(fileDinhKems);
			}

//			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.delete(qlnvCapvonMuaBan);
			qlnvCapvonMuaBan.setTrangThai(Constants.TT_BC_0);// trạng thái xóa
			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return resp;
	}

	@Override
	public Resp update(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> capvonMuaBanOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
					.findById(objReq.getId());
			if (!capvonMuaBanOpt.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + objReq.getId() + " của quyết định giao dự toán chi không tồn tại");
			}

			QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = capvonMuaBanOpt.get();
			// check role and Trang thai
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!qlnvCapvonMuaBan.getTrangThai().equals(Constants.TT_BC_1)
							&& !qlnvCapvonMuaBan.getTrangThai().equals(Constants.TT_BC_3)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					if (!qlnvCapvonMuaBan.getTrangThai().equals(Constants.TT_BC_5)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			}

			qlnvCapvonMuaBan.setNgaySua(new Date());
			qlnvCapvonMuaBan.setNguoiSua(userInfo.getUsername());

			// update
			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

			// Cập nhật file theo chỉnh sửa
			if (!StringUtils.isEmpty(objReq.getListIdFiles())) {
				List<Long> items = Arrays.asList(objReq.getListIdFiles().split(",")).stream()
						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				fileDinhKemRepository.deleteWithIds(items);
			}
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			if (!objReq.getFileDinhKems().isEmpty()) {
				for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
					FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_PHANBO_DTOAN_CHI);
					dinhKem.setQlnvId(objReq.getId());
					fileDinhKems.add(dinhKem);
				}
				fileDinhKemRepository.saveAll(fileDinhKems);
			}

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());

		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return resp;
	}

	@Override
	public Resp function(NhomNutChucNangReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {

			Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> capvonMuaBanOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
					.findById(objReq.getId());
			if (!capvonMuaBanOpt.isPresent()) {
				throw new UnsupportedOperationException("ID không tồn tại!");
			}
			QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = capvonMuaBanOpt.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnvCapvonMuaBan.getMaDviLap()));

			// kiểm tra quyền thao tác
			qlnvCapvonMuaBan.setTrangThai(
					Utils.function(qlnvCapvonMuaBan.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));
			// update trạng thái báo cáo
			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

			resp.setData(qlnvCapvonMuaBan);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return resp;
	}
}
