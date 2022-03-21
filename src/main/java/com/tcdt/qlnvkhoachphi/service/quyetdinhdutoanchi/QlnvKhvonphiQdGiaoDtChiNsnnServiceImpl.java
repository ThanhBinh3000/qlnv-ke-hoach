package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

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
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiQdGiaoDtChiNsnnServiceImpl extends BaseController
		implements QlnvKhvonphiQdGiaoDtChiNsnnService {

	@Autowired
	private QlnvKhvonphiQdGiaoDtChiNsnnRepository qlnvKhvonphiQdGiaoDtChiNsnnRepository;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private QlnvKhvonphiQdGiaoDtChiNsnnCtietRepository qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository;

	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public Resp detail(String ids) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiQdGiaoDtChiNsnn> optional = qlnvKhvonphiQdGiaoDtChiNsnnRepository
					.findById(Long.parseLong(ids));

			if (!optional.isPresent()) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi phương án");
			}

			QlnvKhvonphiQdGiaoDtChiNsnn qlnvChiNsnn = optional.get();

			QlnvKhvonphiQdGiaoDtChiNsnnResp qlnvChiNsnnResp = new ModelMapper().map(qlnvChiNsnn,
					QlnvKhvonphiQdGiaoDtChiNsnnResp.class);

			ArrayList<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> lstNsnnCtiet = qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository
					.findByQlnvKhvonphiDsQdId(qlnvChiNsnn.getId());

			qlnvChiNsnnResp.setLstCtiet(lstNsnnCtiet);

			resp.setData(qlnvChiNsnnResp);
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
	public Page<QlnvKhvonphiQdGiaoDtChiNsnn> colection(QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq) {
		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiQdGiaoDtChiNsnn> data = qlnvKhvonphiQdGiaoDtChiNsnnRepository.selectParams(objReq.getNoiQd(),
				objReq.getSoQd(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), pageable);

		return data;
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@Override
	public QlnvKhvonphiQdGiaoDtChiNsnnResp create(QlnvKhvonphiQdGiaoDtChiNsnnReq objReq) throws Exception {
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
		QlnvKhvonphiQdGiaoDtChiNsnn qlnvQdGiaoDtChiNsnn = new ModelMapper().map(objReq,
				QlnvKhvonphiQdGiaoDtChiNsnn.class);
		qlnvQdGiaoDtChiNsnn.setTrangThai(Constants.TT_BC_1);// Đang soạn
		qlnvQdGiaoDtChiNsnn.setNgayTao(new Date());
		qlnvQdGiaoDtChiNsnn.setNguoiTao(userInfo.getUsername());
		qlnvQdGiaoDtChiNsnn.setNgayQd(new Date());

		qlnvQdGiaoDtChiNsnn = qlnvKhvonphiQdGiaoDtChiNsnnRepository.save(qlnvQdGiaoDtChiNsnn);

		// Thêm mới file đính kèm:
		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (!objReq.getFileDinhKems().isEmpty()) {
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KHVONPHI_DC_DU_TOAN_CHI);
				dinhKem.setQlnvId(qlnvQdGiaoDtChiNsnn.getId());
				fileDinhKems.add(dinhKem);
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		ArrayList<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> lstQdGiaoDtChiNsnnCtiets = new ArrayList<>();
		for (QlnvKhvonphiQdGiaoDtChiNsnnCtiet qlnvChiNsnnCtiet : objReq.getLstCtiet()) {
			qlnvChiNsnnCtiet.setQlnvKhvonphiDsQdId(qlnvQdGiaoDtChiNsnn.getId());
			lstQdGiaoDtChiNsnnCtiets.add(qlnvChiNsnnCtiet);
		}

		qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository.saveAll(lstQdGiaoDtChiNsnnCtiets);
	
		QlnvKhvonphiQdGiaoDtChiNsnnResp response = new ModelMapper().map(qlnvQdGiaoDtChiNsnn, QlnvKhvonphiQdGiaoDtChiNsnnResp.class);
		
		response.setLstCtiet(lstQdGiaoDtChiNsnnCtiets);
		
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
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiQdGiaoDtChiNsnn> optionalNsnn = qlnvKhvonphiQdGiaoDtChiNsnnRepository
				.findById(Long.parseLong(ids));

		if (!optionalNsnn.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + ids + " của quyết định giao dự toán chi không tồn tại");
		}

		QlnvKhvonphiQdGiaoDtChiNsnn qlnvQdGiaoDtChiNsnn = optionalNsnn.get();

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.valueOf(qlnvQdGiaoDtChiNsnn.getMaDvi()));
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlnvQdGiaoDtChiNsnn.getTrangThai().equals(Constants.TT_BC_1)) {
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
				.findFileDinhKemByQlnvId(qlnvQdGiaoDtChiNsnn.getId());

		if (!fileDinhKems.isEmpty()) {
			fileDinhKemRepository.deleteAll(fileDinhKems);
		}

		ArrayList<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> lstNsnnCtiet = qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository
				.findByQlnvKhvonphiDsQdId(qlnvQdGiaoDtChiNsnn.getId());

		if (!lstNsnnCtiet.isEmpty()) {
			qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository.deleteAll(lstNsnnCtiet);
		}

		qlnvKhvonphiQdGiaoDtChiNsnnRepository.delete(qlnvQdGiaoDtChiNsnn);

	}

	@Override
	public QlnvKhvonphiQdGiaoDtChiNsnnResp update(QlnvKhvonphiQdGiaoDtChiNsnnReq objReq) throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		Optional<QlnvKhvonphiQdGiaoDtChiNsnn> optionalNsnn = qlnvKhvonphiQdGiaoDtChiNsnnRepository
				.findById(objReq.getId());
		if (!optionalNsnn.isPresent()) {
			throw new UnsupportedOperationException(
					"Mã ID: " + objReq.getId() + " của quyết định giao dự toán chi không tồn tại");
		}

		QlnvKhvonphiQdGiaoDtChiNsnn qlnvQdGiaoDtChiNsnn = optionalNsnn.get();
		// check role and Trang thai
		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlnvQdGiaoDtChiNsnn.getTrangThai().equals(Constants.TT_BC_1)
						&& !qlnvQdGiaoDtChiNsnn.getTrangThai().equals(Constants.TT_BC_3)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				if (!qlnvQdGiaoDtChiNsnn.getTrangThai().equals(Constants.TT_BC_5)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
			}
		}

		qlnvQdGiaoDtChiNsnn.setNgaySua(new Date());
		qlnvQdGiaoDtChiNsnn.setNguoiSua(userInfo.getUsername());

		qlnvQdGiaoDtChiNsnn = qlnvKhvonphiQdGiaoDtChiNsnnRepository.save(qlnvQdGiaoDtChiNsnn);

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
				dinhKem.setDataType(Constants.QLNV_KHVONPHI_QD_GIAO_DTOAN_CHI);
				dinhKem.setQlnvId(objReq.getId());
				fileDinhKems.add(dinhKem);
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		ArrayList<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> lstQdGiaoDtChiNsnnCtiet = new ArrayList<>();
		for (QlnvKhvonphiQdGiaoDtChiNsnnCtiet qlnvNsnnCtietUpdate : objReq.getLstCtiet()) {
			// update
			if (qlnvNsnnCtietUpdate.getId() != null) {
				Optional<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> optionalNsnnCtiet = qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository
						.findById(qlnvNsnnCtietUpdate.getId());
				if (!optionalNsnnCtiet.isPresent()) {
					// Them moi
					qlnvNsnnCtietUpdate.setQlnvKhvonphiDsQdId(qlnvQdGiaoDtChiNsnn.getId());
					lstQdGiaoDtChiNsnnCtiet.add(qlnvNsnnCtietUpdate);
				} else {
					// update
					QlnvKhvonphiQdGiaoDtChiNsnnCtiet nsnnCtiet = optionalNsnnCtiet.get();
					nsnnCtiet.setQlnvKhvonphiDsQdId(qlnvQdGiaoDtChiNsnn.getId());
					updateObjectToObject(nsnnCtiet, qlnvNsnnCtietUpdate);
					lstQdGiaoDtChiNsnnCtiet.add(nsnnCtiet);
				}
			} else {
				qlnvNsnnCtietUpdate.setQlnvKhvonphiDsQdId(qlnvQdGiaoDtChiNsnn.getId());
				lstQdGiaoDtChiNsnnCtiet.add(qlnvNsnnCtietUpdate);
			}
		}

		qlnvKhvonphiQdGiaoDtChiNsnnCtietRepository.saveAll(lstQdGiaoDtChiNsnnCtiet);

		QlnvKhvonphiQdGiaoDtChiNsnnResp response = new ModelMapper().map(qlnvQdGiaoDtChiNsnn, QlnvKhvonphiQdGiaoDtChiNsnnResp.class);
		
		response.setLstCtiet(lstQdGiaoDtChiNsnnCtiet);
		
		return response;
	}

}
