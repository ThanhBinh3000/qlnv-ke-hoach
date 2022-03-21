package com.tcdt.qlnvkhoachphi.service.quyettoanvonphi;

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
import com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.quyettoanvonphi.QlnvKhvonphiQtoanDtqgResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqg;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;

@Service
public class QlnvKhvonphiQtoanDtqgServiceImpl extends BaseController implements QlnvKhvonphiQtoanDtqgService {

	@Autowired
	private QlnvKhvonphiQtoanDtqgRepository qlnvKhvonphiQtoanDtqgRepository;

	@Autowired
	private QlnvKhvonphiQtoanDtqgCtietRepository qlnvKhvonphiQtoanDtqgCtietRepository;

	@Autowired
	private QlnvDmService qlnvDmService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiQtoanDtqgResp create(QlnvKhvonphiQtoanDtqgReq objReq) throws Exception {

		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		for (Role role : userInfo.getRoles()) {
			if (!role.getId().equals(Constants.NHAN_VIEN)) {
				throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
			}
		}

		QlnvKhvonphiQtoanDtqg qlnvQtoanDtqg = new ModelMapper().map(objReq, QlnvKhvonphiQtoanDtqg.class);

		qlnvQtoanDtqg.setTrangThai(Constants.TT_BC_1);// Đang soạn
		qlnvQtoanDtqg.setNgayTao(new Date());
		qlnvQtoanDtqg.setNguoiTao(userInfo.getUsername());

		qlnvQtoanDtqg = qlnvKhvonphiQtoanDtqgRepository.save(qlnvQtoanDtqg);

		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (!objReq.getFileDinhKems().isEmpty()) {
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KHVONPHI_DC_DU_TOAN_CHI);
				dinhKem.setQlnvId(qlnvQtoanDtqg.getId());
				fileDinhKems.add(dinhKem);
			}
		}

		ArrayList<QlnvKhvonphiQtoanDtqgCtiet> lstQdGiaoDtChiNsnnCtiets = new ArrayList<QlnvKhvonphiQtoanDtqgCtiet>();
		for (QlnvKhvonphiQtoanDtqgCtiet qlnvChiNsnnCtiet : objReq.getLstCtiet()) {
			qlnvChiNsnnCtiet.setQlnvKhvonphiQtoanId(qlnvQtoanDtqg.getId());
			;
			qlnvChiNsnnCtiet.setMaDviThien(String.valueOf(userInfo.getDvql()));
			lstQdGiaoDtChiNsnnCtiets.add(qlnvChiNsnnCtiet);
		}

		qlnvKhvonphiQtoanDtqgCtietRepository.saveAll(lstQdGiaoDtChiNsnnCtiets);

		QlnvKhvonphiQtoanDtqgResp response = new ModelMapper().map(qlnvQtoanDtqg, QlnvKhvonphiQtoanDtqgResp.class);

		response.setLstCtiet(lstQdGiaoDtChiNsnnCtiets);

		return response;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiQtoanDtqgResp update(QlnvKhvonphiQtoanDtqgReq objReq) throws Exception {

		if (objReq == null) {
			return null;
		}

		Optional<QlnvKhvonphiQtoanDtqg> optionalNsnn = qlnvKhvonphiQtoanDtqgRepository.findById(objReq.getId());
		if (!optionalNsnn.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + objReq.getId() + " của quyết toán không tồn tại");
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		QlnvKhvonphiQtoanDtqg qlnvQtoanDtqg = optionalNsnn.get();
		// check role and Trang thai
		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlnvQtoanDtqg.getTrangThai().equals(Constants.TT_BC_1)
						&& !qlnvQtoanDtqg.getTrangThai().equals(Constants.TT_BC_3)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				if (!qlnvQtoanDtqg.getTrangThai().equals(Constants.TT_BC_5)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
			}
		}

		qlnvQtoanDtqg.setNgaySua(new Date());
		qlnvQtoanDtqg.setNguoiSua(userInfo.getUsername());

		qlnvQtoanDtqg = qlnvKhvonphiQtoanDtqgRepository.save(qlnvQtoanDtqg);

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

		ArrayList<QlnvKhvonphiQtoanDtqgCtiet> lstQtoanDtqgCtiets = new ArrayList<>();
		for (QlnvKhvonphiQtoanDtqgCtiet qlnvNsnnCtietUpdate : objReq.getLstCtiet()) {
			// update
			if (qlnvNsnnCtietUpdate.getId() != null) {
				Optional<QlnvKhvonphiQtoanDtqgCtiet> optionalNsnnCtiet = qlnvKhvonphiQtoanDtqgCtietRepository
						.findById(qlnvNsnnCtietUpdate.getId());
				if (!optionalNsnnCtiet.isPresent()) {
					// Them moi
					qlnvNsnnCtietUpdate.setQlnvKhvonphiQtoanId(qlnvQtoanDtqg.getId());
					lstQtoanDtqgCtiets.add(qlnvNsnnCtietUpdate);
				} else {
					// update
					QlnvKhvonphiQtoanDtqgCtiet nsnnCtiet = optionalNsnnCtiet.get();
					qlnvNsnnCtietUpdate.setQlnvKhvonphiQtoanId(qlnvQtoanDtqg.getId());
					updateObjectToObject(nsnnCtiet, qlnvNsnnCtietUpdate);
					lstQtoanDtqgCtiets.add(qlnvNsnnCtietUpdate);
				}
			} else {
				qlnvNsnnCtietUpdate.setQlnvKhvonphiQtoanId(qlnvQtoanDtqg.getId());
				lstQtoanDtqgCtiets.add(qlnvNsnnCtietUpdate);
			}
		}

		qlnvKhvonphiQtoanDtqgCtietRepository.saveAll(lstQtoanDtqgCtiets);

		QlnvKhvonphiQtoanDtqgResp response = new ModelMapper().map(qlnvQtoanDtqg, QlnvKhvonphiQtoanDtqgResp.class);

		response.setLstCtiet(lstQtoanDtqgCtiets);

		return response;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public void delete(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiQtoanDtqg> optionalNsnn = qlnvKhvonphiQtoanDtqgRepository.findById(Long.parseLong(ids));

		if (!optionalNsnn.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + ids + " của quyết định giao dự toán chi không tồn tại");
		}

		QlnvKhvonphiQtoanDtqg qlnvQtoanDtqg = optionalNsnn.get();

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvQtoanDtqg.getMaDvi()));
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlnvQtoanDtqg.getTrangThai().equals(Constants.TT_BC_1)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
			}
		}

		// Xóa file dinh kem
		ArrayList<FileDinhKem> fileDinhKems = fileDinhKemRepository.findFileDinhKemByQlnvId(qlnvQtoanDtqg.getId());

		if (!fileDinhKems.isEmpty()) {
			fileDinhKemRepository.deleteAll(fileDinhKems);
		}

		ArrayList<QlnvKhvonphiQtoanDtqgCtiet> lstNsnnCtiet = qlnvKhvonphiQtoanDtqgCtietRepository
				.findByQlnvKhvonphiQtoanId(qlnvQtoanDtqg.getId());

		if (!lstNsnnCtiet.isEmpty()) {
			qlnvKhvonphiQtoanDtqgCtietRepository.deleteAll(lstNsnnCtiet);
		}

		qlnvKhvonphiQtoanDtqgRepository.delete(qlnvQtoanDtqg);
	}

	@Override
	public QlnvKhvonphiQtoanDtqgResp detail(String ids) {
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiQtoanDtqg> optional = qlnvKhvonphiQtoanDtqgRepository.findById(Long.parseLong(ids));

		if (!optional.isPresent()) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi phương án");
		}

		QlnvKhvonphiQtoanDtqg qlnvChiNsnn = optional.get();

		QlnvKhvonphiQtoanDtqgResp qlnvChiNsnnResp = new ModelMapper().map(qlnvChiNsnn, QlnvKhvonphiQtoanDtqgResp.class);

		ArrayList<QlnvKhvonphiQtoanDtqgCtiet> lstNsnnCtiet = qlnvKhvonphiQtoanDtqgCtietRepository
				.findByQlnvKhvonphiQtoanId(qlnvChiNsnn.getId());

		qlnvChiNsnnResp.setLstCtiet(lstNsnnCtiet);

		return qlnvChiNsnnResp;
	}

	@Override
	public Page<QlnvKhvonphiQtoanDtqg> colection(QlnvKhvonphiQtoanDtqgSearchReq objReq) {
		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiQtoanDtqg> data = qlnvKhvonphiQtoanDtqgRepository.selectParams(objReq.getMaDvi(),
				objReq.getSoQd(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getTrangThai(), pageable);
		return data;
	}

}
