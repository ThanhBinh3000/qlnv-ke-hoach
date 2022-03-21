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
import com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqg;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;

@Service
public class QlnvKhvonphiDchinhSauQtoanDtqgServiceImpl extends BaseController
		implements QlnvKhvonphiDchinhSauQtoanDtqgService {

	@Autowired
	private QlnvKhvonphiDchinhSauQtoanDtqgRepository qlnvKhvonphiDchinhSauQtoanDtqgRepository;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private QlnvKhvonphiDchinhSauQtoanDtqgCtietRepository qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository;

	@Autowired
	private QlnvDmService qlnvDmService;
	
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiDchinhSauQtoanDtqgResp create(QlnvKhvonphiDchinhSauQtoanDtqgReq objReq) throws Exception {
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

		QlnvKhvonphiDchinhSauQtoanDtqg qlnvQtoanDtqg = new ModelMapper().map(objReq,
				QlnvKhvonphiDchinhSauQtoanDtqg.class);

		qlnvQtoanDtqg.setTrangThai(Constants.TT_BC_1);// Đang soạn
		qlnvQtoanDtqg.setNgayTao(new Date());
		qlnvQtoanDtqg.setNguoiTao(userInfo.getUsername());

		qlnvQtoanDtqg = qlnvKhvonphiDchinhSauQtoanDtqgRepository.save(qlnvQtoanDtqg);

		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (!objReq.getFileDinhKems().isEmpty()) {
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KHVONPHI_DC_DU_TOAN_CHI);
				dinhKem.setQlnvId(qlnvQtoanDtqg.getId());
				fileDinhKems.add(dinhKem);
			}
			
			ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> lstQdGiaoDtChiNsnnCtiets = new ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet>();
			for (QlnvKhvonphiDchinhSauQtoanDtqgCtiet qlnvChiNsnnCtiet : objReq.getLstCtiet()) {
				qlnvChiNsnnCtiet.setQlnvKhvonphiDchinhId(qlnvQtoanDtqg.getId());;
				qlnvChiNsnnCtiet.setMaDviThien(String.valueOf(userInfo.getDvql()));
				lstQdGiaoDtChiNsnnCtiets.add(qlnvChiNsnnCtiet);
			}
			
			qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository.saveAll(lstQdGiaoDtChiNsnnCtiets);
			
			QlnvKhvonphiDchinhSauQtoanDtqgResp response = new ModelMapper().map(qlnvQtoanDtqg, QlnvKhvonphiDchinhSauQtoanDtqgResp.class);
			
			response.setLstCtiet(lstQdGiaoDtChiNsnnCtiets);
			
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> lstQdGiaoDtChiNsnnCtiets = new ArrayList<>();
		for (QlnvKhvonphiDchinhSauQtoanDtqgCtiet qlnvChiNsnnCtiet : objReq.getLstCtiet()) {
			qlnvChiNsnnCtiet.setQlnvKhvonphiDchinhId(qlnvQtoanDtqg.getId());
			qlnvChiNsnnCtiet.setMaDviThien(String.valueOf(userInfo.getDvql()));
			lstQdGiaoDtChiNsnnCtiets.add(qlnvChiNsnnCtiet);
		}

		qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository.saveAll(lstQdGiaoDtChiNsnnCtiets);
		
		QlnvKhvonphiDchinhSauQtoanDtqgResp response = new ModelMapper().map(qlnvQtoanDtqg, QlnvKhvonphiDchinhSauQtoanDtqgResp.class);
		
		response.setLstCtiet(lstQdGiaoDtChiNsnnCtiets);
		
		return response;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiDchinhSauQtoanDtqgResp update(QlnvKhvonphiDchinhSauQtoanDtqgReq objReq) throws Exception {

		if(objReq == null) {
			return null;
		}

		Optional<QlnvKhvonphiDchinhSauQtoanDtqg> optionalNsnn = qlnvKhvonphiDchinhSauQtoanDtqgRepository
				.findById(objReq.getId());
		if (!optionalNsnn.isPresent()) {
			throw new UnsupportedOperationException(
					"Mã ID: " + objReq.getId() + " của điều chỉnh sau quyết toán không tồn tại");
		}
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		QlnvKhvonphiDchinhSauQtoanDtqg qlnvDchinhSauQtoanDtqg = optionalNsnn.get();
		// check role and Trang thai
		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlnvDchinhSauQtoanDtqg.getTrangThai().equals(Constants.TT_BC_1)
						&& !qlnvDchinhSauQtoanDtqg.getTrangThai().equals(Constants.TT_BC_3)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				if (!qlnvDchinhSauQtoanDtqg.getTrangThai().equals(Constants.TT_BC_5)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
			}
		}

		qlnvDchinhSauQtoanDtqg.setNgaySua(new Date());
		qlnvDchinhSauQtoanDtqg.setNguoiSua(userInfo.getUsername());

		qlnvDchinhSauQtoanDtqg = qlnvKhvonphiDchinhSauQtoanDtqgRepository.save(qlnvDchinhSauQtoanDtqg);

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

		ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> lstPhanboDtoanchiNsnnCtiets = new ArrayList<>();
		for (QlnvKhvonphiDchinhSauQtoanDtqgCtiet qlnvNsnnCtietUpdate : objReq.getLstCtiet()) {
			// update
			if (qlnvNsnnCtietUpdate.getId() != null) {
				Optional<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> optionalNsnnCtiet = qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository
						.findById(qlnvNsnnCtietUpdate.getId());
				if (!optionalNsnnCtiet.isPresent()) {
					// Them moi
					qlnvNsnnCtietUpdate.setQlnvKhvonphiDchinhId(qlnvDchinhSauQtoanDtqg.getId());
					lstPhanboDtoanchiNsnnCtiets.add(qlnvNsnnCtietUpdate);
				} else {
					// update
					QlnvKhvonphiDchinhSauQtoanDtqgCtiet nsnnCtiet = optionalNsnnCtiet.get();
					qlnvNsnnCtietUpdate.setQlnvKhvonphiDchinhId(qlnvDchinhSauQtoanDtqg.getId());
					updateObjectToObject(nsnnCtiet, qlnvNsnnCtietUpdate);
					lstPhanboDtoanchiNsnnCtiets.add(qlnvNsnnCtietUpdate);
				}
			} else {
				qlnvNsnnCtietUpdate.setQlnvKhvonphiDchinhId(qlnvDchinhSauQtoanDtqg.getId());
				lstPhanboDtoanchiNsnnCtiets.add(qlnvNsnnCtietUpdate);
			}
		}

		qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository.saveAll(lstPhanboDtoanchiNsnnCtiets);
		QlnvKhvonphiDchinhSauQtoanDtqgResp response = new ModelMapper().map(qlnvDchinhSauQtoanDtqg, QlnvKhvonphiDchinhSauQtoanDtqgResp.class);
		response.setLstCtiet(lstPhanboDtoanchiNsnnCtiets);
		
		return response;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public void delete(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiDchinhSauQtoanDtqg> optionalNsnn = qlnvKhvonphiDchinhSauQtoanDtqgRepository
				.findById(Long.parseLong(ids));

		if (!optionalNsnn.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + ids + " của quyết định giao dự toán chi không tồn tại");
		}

		QlnvKhvonphiDchinhSauQtoanDtqg qlnvDchinhSauQtoanDtqg = optionalNsnn.get();

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvDchinhSauQtoanDtqg.getMaDvi()));
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlnvDchinhSauQtoanDtqg.getTrangThai().equals(Constants.TT_BC_1)) {
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
				.findFileDinhKemByQlnvId(qlnvDchinhSauQtoanDtqg.getId());

		if (!fileDinhKems.isEmpty()) {
			fileDinhKemRepository.deleteAll(fileDinhKems);
		}

		ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> lstNsnnCtiet = qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository
				.findByQlnvKhvonphiDchinhId(qlnvDchinhSauQtoanDtqg.getId());

		if (!lstNsnnCtiet.isEmpty()) {
			qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository.deleteAll(lstNsnnCtiet);
		}

		qlnvKhvonphiDchinhSauQtoanDtqgRepository.delete(qlnvDchinhSauQtoanDtqg);

	}

	@Override
	public QlnvKhvonphiDchinhSauQtoanDtqgResp detail(Long ids) {
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiDchinhSauQtoanDtqg> optional = qlnvKhvonphiDchinhSauQtoanDtqgRepository
				.findById(ids);

		if (!optional.isPresent()) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi phương án");
		}

		QlnvKhvonphiDchinhSauQtoanDtqg qlnvChiNsnn = optional.get();

		QlnvKhvonphiDchinhSauQtoanDtqgResp qlnvChiNsnnResp = new ModelMapper().map(qlnvChiNsnn,
				QlnvKhvonphiDchinhSauQtoanDtqgResp.class);

		ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> lstNsnnCtiet = qlnvKhvonphiDchinhSauQtoanDtqgCtietRepository
				.findByQlnvKhvonphiDchinhId(qlnvChiNsnn.getId());

		qlnvChiNsnnResp.setLstCtiet(lstNsnnCtiet);

		return qlnvChiNsnnResp;
	}

	@Override
	public Page<QlnvKhvonphiDchinhSauQtoanDtqg> colection(QlnvKhvonphiDchinhSauQtoanDtqgSearchReq objReq) {
		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiDchinhSauQtoanDtqg> data = qlnvKhvonphiDchinhSauQtoanDtqgRepository.selectParams(
				objReq.getMaDvi(), objReq.getSoQd(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(),
				objReq.getTrangThai(), pageable);

		return data;
	}

}
