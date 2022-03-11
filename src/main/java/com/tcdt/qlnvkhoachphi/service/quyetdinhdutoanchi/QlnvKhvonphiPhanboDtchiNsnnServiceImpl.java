package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnnCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiPhanboDtchiNsnnServiceImpl extends BaseController implements QlnvKhvonphiPhanboDtchiNsnnService{

	@Autowired
	private QlnvKhvonphiPhanboDtoanChiNsnnRepository qlnvKhvonphiPhanboDtoanChiNsnnRepository;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private QlnvKhvonphiPhanboDtoanChiNsnnCtietRepository qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository;

	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public Resp create(QlnvKhvonphiPhanboDtoanChiNsnnReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			// check role nhân viên mới được tạo
			UserInfo userInfo = getUserInfo(req);
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
				}
			}
			QlnvKhvonphiPhanboDtoanchiNsnn qlnvQdGiaoDtChiNsnn = new ModelMapper().map(objReq,
					QlnvKhvonphiPhanboDtoanchiNsnn.class);
			qlnvQdGiaoDtChiNsnn.setTrangThai(Constants.TT_BC_1);// Đang soạn
			qlnvQdGiaoDtChiNsnn.setNgayTao(new Date());
			qlnvQdGiaoDtChiNsnn.setNguoiTao(getUserName(req));

			qlnvQdGiaoDtChiNsnn = qlnvKhvonphiPhanboDtoanChiNsnnRepository.save(qlnvQdGiaoDtChiNsnn);

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

			ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> lstQdGiaoDtChiNsnnCtiets = new ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet>();
			for (QlnvKhvonphiPhanboDtoanchiNsnnCtiet qlnvChiNsnnCtiet : objReq.getLstCtiet()) {
				qlnvChiNsnnCtiet.setQlnvKhvonphiPhanboDtoanChiId(qlnvQdGiaoDtChiNsnn.getId());
				qlnvChiNsnnCtiet.setMaDviThien(String.valueOf(userInfo.getDvql()));
				lstQdGiaoDtChiNsnnCtiets.add(qlnvChiNsnnCtiet);
			}

			qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository.saveAll(lstQdGiaoDtChiNsnnCtiets);

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
	public Resp update(QlnvKhvonphiPhanboDtoanChiNsnnReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphiPhanboDtoanchiNsnn> optionalNsnn = qlnvKhvonphiPhanboDtoanChiNsnnRepository
					.findById(objReq.getId());
			if (!optionalNsnn.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + objReq.getId() + " của quyết định giao dự toán chi không tồn tại");
			}

			UserInfo userInfo = getUserInfo(req);
			QlnvKhvonphiPhanboDtoanchiNsnn qlnvPhanboDtoanchiNsnn = optionalNsnn.get();
			// check role and Trang thai
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!qlnvPhanboDtoanchiNsnn.getTrangThai().equals(Constants.TT_BC_1)
							&& !qlnvPhanboDtoanchiNsnn.getTrangThai().equals(Constants.TT_BC_3)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					if (!qlnvPhanboDtoanchiNsnn.getTrangThai().equals(Constants.TT_BC_5)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			}

			qlnvPhanboDtoanchiNsnn.setNgaySua(new Date());
			qlnvPhanboDtoanchiNsnn.setNguoiSua(userInfo.getUsername());

			qlnvPhanboDtoanchiNsnn = qlnvKhvonphiPhanboDtoanChiNsnnRepository.save(qlnvPhanboDtoanchiNsnn);

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

			ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> lstPhanboDtoanchiNsnnCtiets = new ArrayList<>();
			for (QlnvKhvonphiPhanboDtoanchiNsnnCtiet qlnvNsnnCtietUpdate : objReq.getLstCtiet()) {
				// update
				if (qlnvNsnnCtietUpdate.getId() != null) {
					Optional<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> optionalNsnnCtiet = qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository
							.findById(qlnvNsnnCtietUpdate.getId());
					if (!optionalNsnnCtiet.isPresent()) {
						// Them moi
						qlnvNsnnCtietUpdate.setQlnvKhvonphiPhanboDtoanChiId(qlnvPhanboDtoanchiNsnn.getId());
						lstPhanboDtoanchiNsnnCtiets.add(qlnvNsnnCtietUpdate);
					} else {
						// update
						QlnvKhvonphiPhanboDtoanchiNsnnCtiet nsnnCtiet = optionalNsnnCtiet.get();
						qlnvNsnnCtietUpdate.setQlnvKhvonphiPhanboDtoanChiId(qlnvPhanboDtoanchiNsnn.getId());
						updateObjectToObject(nsnnCtiet, qlnvNsnnCtietUpdate);
						lstPhanboDtoanchiNsnnCtiets.add(qlnvNsnnCtietUpdate);
					}
				} else {
					qlnvNsnnCtietUpdate.setQlnvKhvonphiPhanboDtoanChiId(qlnvPhanboDtoanchiNsnn.getId());
					lstPhanboDtoanchiNsnnCtiets.add(qlnvNsnnCtietUpdate);
				}
			}

			qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository.saveAll(lstPhanboDtoanchiNsnnCtiets);

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
	public Resp delete(String ids, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiPhanboDtoanchiNsnn> optionalNsnn = qlnvKhvonphiPhanboDtoanChiNsnnRepository
					.findById(Long.parseLong(ids));

			if (!optionalNsnn.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + ids + " của quyết định giao dự toán chi không tồn tại");
			}

			QlnvKhvonphiPhanboDtoanchiNsnn qlnvPhanboDtoanchiNsnn = optionalNsnn.get();
			UserInfo userInfo = getUserInfo(req);

			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnvPhanboDtoanchiNsnn.getMaDvi()));
			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!qlnvPhanboDtoanchiNsnn.getTrangThai().equals(Constants.TT_BC_1)) {
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
					.findFileDinhKemByQlnvId(qlnvPhanboDtoanchiNsnn.getId());

			if (!fileDinhKems.isEmpty()) {
				fileDinhKemRepository.deleteAll(fileDinhKems);
			}

			ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> lstNsnnCtiet = qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository
					.findByQlnvKhvonphiPhanboDtoanChiId(qlnvPhanboDtoanchiNsnn.getId());

			if(!lstNsnnCtiet.isEmpty()) {
				qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository.deleteAll(lstNsnnCtiet);
			}

			qlnvKhvonphiPhanboDtoanChiNsnnRepository.delete(qlnvPhanboDtoanchiNsnn);

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
	public Resp detail(String ids) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiPhanboDtoanchiNsnn> optional
			= qlnvKhvonphiPhanboDtoanChiNsnnRepository.findById(Long.parseLong(ids));

			if(!optional.isPresent()) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi phương án");
			}

			QlnvKhvonphiPhanboDtoanchiNsnn qlnvChiNsnn
				= optional.get();

			QlnvKhvonphiPhanboDtoanChiNsnnResp qlnvChiNsnnResp
				= new ModelMapper().map(qlnvChiNsnn, QlnvKhvonphiPhanboDtoanChiNsnnResp.class);

			ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> lstNsnnCtiet
				= qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository.findByQlnvKhvonphiPhanboDtoanChiId(qlnvChiNsnn.getId());

			qlnvChiNsnnResp.setLstCtiet(lstNsnnCtiet);

			resp.setData(qlnvChiNsnnResp);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		}catch(Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return resp;
	}

	@Override
	public Resp colection(QlnvKhvonphiPhanboDtoanChiNsnnSearchReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiPhanboDtoanchiNsnn> data = qlnvKhvonphiPhanboDtoanChiNsnnRepository
					.selectParams(objReq.getMaDvi(),objReq.getSoQd(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(),
							objReq.getTrangThai(), pageable);


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
	public Resp synthesis(String maDviThien, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {

			//TODO: check mã đơn vị thực hiện
			QlnvDmDonvi qlnvDmDonviThien = qlnvDmService.getDonViById(maDviThien);
			if (qlnvDmDonviThien == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> qlnvCtiet
				= qlnvKhvonphiPhanboDtoanChiNsnnCtietRepository.synthesis(maDviThien);

			resp.setData(qlnvCtiet);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		}catch(Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return resp;
	}

}
