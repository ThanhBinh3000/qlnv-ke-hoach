package com.tcdt.qlnvkhoachphi.service.capvonmuaban;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqg;
import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

@Service
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
	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp getDetail(Long id) {
		if (StringUtils.isEmpty(id)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> optional = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
				.findById(id);

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
		ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository.findFileDinhKemByQlnvId(qlnvCapvonMuaBan.getId());
		lstFile.addAll(fileDinhKem);
		qlnvCapvonMuaBanResp.setLstFile(lstFile);

		return qlnvCapvonMuaBanResp;
	}

	@Override
	public Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> getList(
			QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq objReq) {
		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> data = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
				.selectParams(objReq.getMaDviCap(), objReq.getMaDviCap(), objReq.getMaLoaiGnhan(),
						objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getTrangThai(), objReq.getSoQd(),
						pageable);
//		Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> data = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
//				.selectParams(objReq.getMaDviCap(), objReq.getMaDviCap(), objReq.getMaLoaiGnhan(),
//						objReq.getNgayQuyetDinh(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getTrangThai(),
//						objReq.getSoQd(), pageable);
		return data;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp create(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq)
			throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

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
		// add
		qlnvCapvonMuaBan = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

		ArrayList<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> lstCapvonMuaBanCtiets = new ArrayList<>();
		for (QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet qlnvCapvonMuaBanCtiet : objReq.getLstCTiet()) {
			qlnvCapvonMuaBanCtiet.setQlnvKhvonphiCapvonId(qlnvCapvonMuaBan.getId());
			lstCapvonMuaBanCtiets.add(qlnvCapvonMuaBanCtiet);
		}

		// add detail
		qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository.saveAll(lstCapvonMuaBanCtiets);

		// Thêm mới file đính kèm:
		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (!objReq.getLstFile().isEmpty()) {
			try {
				for (FileDinhKemReq dinhKemReq : objReq.getLstFile()) {
					FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG);
					dinhKem.setQlnvId(qlnvCapvonMuaBan.getId());
					fileDinhKems.add(dinhKem);
				}
			} catch (Exception e) {
				throw new UnsupportedOperationException("lstFile sai định dạng");
			}

			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp response = new ModelMapper().map(qlnvCapvonMuaBan,
				QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp.class);
		response.setLstCtiet(lstCapvonMuaBanCtiets);
		response.setLstFile(fileDinhKems);

		return response;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public void delete(String ids) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Bắt buộc nhập Id");
		}

		Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> capvonMuaBanOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
				.findById(Long.parseLong(ids));

		if (!capvonMuaBanOpt.isPresent()) {
			throw new UnsupportedOperationException("ID: " + ids + " không tồn tại");
		}

		QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = capvonMuaBanOpt.get();
		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvCapvonMuaBan.getMaDviNhan()));
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
		ArrayList<FileDinhKem> fileDinhKems = fileDinhKemRepository.findFileDinhKemByQlnvId(qlnvCapvonMuaBan.getId());

		if (!fileDinhKems.isEmpty()) {
			fileDinhKemRepository.deleteAll(fileDinhKems);
		}

//			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.delete(qlnvCapvonMuaBan);
		qlnvCapvonMuaBan.setTrangThai(Constants.TT_BC_0);// trạng thái xóa
		qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp update(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq)
			throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> capvonMuaBanOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
				.findById(objReq.getId());
		if (!capvonMuaBanOpt.isPresent()) {
			throw new UnsupportedOperationException("ID: " + objReq.getId() + " không tồn tại");
		}

		QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = capvonMuaBanOpt.get();

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvCapvonMuaBan.getMaDviLap()));
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		// check khác đơn vị
		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

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
		qlnvCapvonMuaBan = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

		// update detail
		ArrayList<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> lstCapvonMuaBanCtiets = new ArrayList<>();
		for (QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet capvonMuaBanCtiets : objReq.getLstCTiet()) {
			// update
			if (capvonMuaBanCtiets.getId() != null) {
				Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> capvonMuaBanCtietOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository
						.findById(capvonMuaBanCtiets.getId());
				if (!capvonMuaBanCtietOpt.isPresent()) {
					// Them moi
					capvonMuaBanCtiets.setQlnvKhvonphiCapvonId(qlnvCapvonMuaBan.getId());
					lstCapvonMuaBanCtiets.add(capvonMuaBanCtiets);
				} else {
					// update
					QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet nsnnCtiet = capvonMuaBanCtietOpt.get();
					capvonMuaBanCtiets.setQlnvKhvonphiCapvonId(qlnvCapvonMuaBan.getId());
					updateObjectToObject(nsnnCtiet, capvonMuaBanCtiets);
					lstCapvonMuaBanCtiets.add(capvonMuaBanCtiets);
				}
			} else {
				capvonMuaBanCtiets.setQlnvKhvonphiCapvonId(qlnvCapvonMuaBan.getId());
				lstCapvonMuaBanCtiets.add(capvonMuaBanCtiets);
			}
		}
		qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository.saveAll(lstCapvonMuaBanCtiets);

		// xoa chi tiet
		if (!StringUtils.isEmpty(objReq.getListIdDeletes())) {
			List<Long> items = Arrays.asList(objReq.getListIdDeletes().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository.deleteWithIds(items);
		}

		// Cập nhật file theo chỉnh sửa
		if (!StringUtils.isEmpty(objReq.getListIdFiles())) {
			List<Long> items = Arrays.asList(objReq.getListIdFiles().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			fileDinhKemRepository.deleteWithIds(items);
		}
		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (!objReq.getLstFile().isEmpty()) {
			for (FileDinhKemReq dinhKemReq : objReq.getLstFile()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KHVONPHI_PHANBO_DTOAN_CHI);
				dinhKem.setQlnvId(objReq.getId());
				fileDinhKems.add(dinhKem);
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp response = new ModelMapper().map(qlnvCapvonMuaBan,
				QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp.class);
		response.setLstCtiet(lstCapvonMuaBanCtiets);
		response.setLstFile(fileDinhKems);

		return response;
	}

	@Override
	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqg function(NhomNutChucNangReq objReq) throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		Optional<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> capvonMuaBanOpt = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
				.findById(objReq.getId());
		if (!capvonMuaBanOpt.isPresent()) {
			throw new UnsupportedOperationException("ID không tồn tại!");
		}
		QlnvKhvonphiCapvonMuaBanTtoanThangDtqg qlnvCapvonMuaBan = capvonMuaBanOpt.get();
		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvCapvonMuaBan.getMaDviNhan()));

		// kiểm tra quyền thao tác
		qlnvCapvonMuaBan.setTrangThai(
				Utils.function(qlnvCapvonMuaBan.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));
		// update trạng thái báo cáo
		qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository.save(qlnvCapvonMuaBan);

		return qlnvCapvonMuaBan;
	}
}
