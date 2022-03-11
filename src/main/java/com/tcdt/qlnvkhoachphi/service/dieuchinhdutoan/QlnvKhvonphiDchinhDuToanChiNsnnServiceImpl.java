package com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDchinhDuToanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnCtiet;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.Formater;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiDchinhDuToanChiNsnnServiceImpl implements IQlnvKhvonphiDchinhDuToanChiNsnnService {

	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnRepository qlnvKhvonphiDchinhDuToanChiNsnnRepository;

	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnCtietRepository qlnvKhvonphiDchinhDuToanChiNsnnCtietRepository;

	@Autowired
	private QlnvDmService qlnvDmService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Resp getDetail(String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiDchinhDuToanChiNsnn> qOptional = qlnvKhvonphiDchinhDuToanChiNsnnRepository
					.findById(Long.parseLong(ids));
			if (!qOptional.isPresent()) {
				throw new UnsupportedOperationException("Bản ghi không tồn tại");
			}
			QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn = qOptional.get();

			// mapping response
			QlnvKhvonphiDchinhDuToanChiNsnnResp qlnvKhvonphiDchinhDuToanChiNsnnResp = modelMapper
					.map(qlnvKhvonphiDchinhDuToanChiNsnn, QlnvKhvonphiDchinhDuToanChiNsnnResp.class);
			// lấy danh sách file đính kèm
			ArrayList<FileDinhKem> lstFile = new ArrayList<>();
			ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository
					.findFileDinhKemByQlnvId(qlnvKhvonphiDchinhDuToanChiNsnn.getId());
			lstFile.addAll(fileDinhKem);
			qlnvKhvonphiDchinhDuToanChiNsnnResp.setLstFile(lstFile);

			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnResp);
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
	public Resp getList(QlnvKhvonphiDchinhDuToanChiNsnnSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiDchinhDuToanChiNsnn> data = qlnvKhvonphiDchinhDuToanChiNsnnRepository.selectParams(
					objReq.getNamHienHanh(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getMaDvi(),
					objReq.getTrangThai(), objReq.getSoQd(), pageable);

			for (QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn : data) {
				QlnvDmDonvi qlnvDmDonvi = qlnvDmService
						.getDonViById(String.valueOf(qlnvKhvonphiDchinhDuToanChiNsnn.getMaDvi()));
				if (!ObjectUtils.isEmpty(qlnvDmDonvi)) {
					qlnvKhvonphiDchinhDuToanChiNsnn.setTenDvi(qlnvDmDonvi.getTenDvi());
				}
				qlnvKhvonphiDchinhDuToanChiNsnn
						.setTenTrangThai(Utils.getTenTrangThai(qlnvKhvonphiDchinhDuToanChiNsnn.getTrangThai()));
			}
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
	public Resp add(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			// check role nhân viên mới được tạo
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
				}
			}
			QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn = modelMapper.map(objReq,
					QlnvKhvonphiDchinhDuToanChiNsnn.class);
			qlnvKhvonphiDchinhDuToanChiNsnn.setTrangThai(Constants.TT_BC_1);// Đang soạn
			qlnvKhvonphiDchinhDuToanChiNsnn.setNgayTao(new Date());
			qlnvKhvonphiDchinhDuToanChiNsnn.setNguoiTao(userInfo.getUsername());
			qlnvKhvonphiDchinhDuToanChiNsnn.setNgayQuyetDinh(Formater.str2date(objReq.getNgayqd()));
			QlnvKhvonphiDchinhDuToanChiNsnn chiNsnn = qlnvKhvonphiDchinhDuToanChiNsnnRepository
					.save(qlnvKhvonphiDchinhDuToanChiNsnn);

			// Thêm mới file đính kèm:
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			if (objReq.getFileDinhKems() != null) {
				for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_DC_DU_TOAN_CHI);
					dinhKem.setQlnvId(chiNsnn.getId());
					fileDinhKems.add(dinhKem);
				}
				fileDinhKemRepository.saveAll(fileDinhKems);
			}
			objReq.setId(chiNsnn.getId());
			resp.setData(objReq);
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
	public Resp synthetic(TongHopDchinhDuToanChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			String maDvi = objReq.getMaDvi();
			String namHienTai = objReq.getNamHienTai();
			ArrayList<QlnvKhvonphiDchinhDuToanChiNsnnCtiet> lstCTietBcao = qlnvKhvonphiDchinhDuToanChiNsnnCtietRepository
					.synthesis(maDvi,namHienTai);
			resp.setData(lstCTietBcao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (

		Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return resp;
	}

	@Override
	public Resp remove(String ids, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resp update(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphiDchinhDuToanChiNsnn> optionalBC = qlnvKhvonphiDchinhDuToanChiNsnnRepository
					.findById(objReq.getId());
			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("Mã ID: " + objReq.getId() + " của báo cáo không tồn tại");
			}
			QlnvKhvonphiDchinhDuToanChiNsnn nsnn = optionalBC.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(nsnn.getMaDvi()));
			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			// check khác đơn vị
			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			// check với trạng thái hiện tại không được sửa
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!nsnn.getTrangThai().equals(Constants.TT_BC_1)
							&& !nsnn.getTrangThai().equals(Constants.TT_BC_3)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					if (!nsnn.getTrangThai().equals(Constants.TT_BC_5)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			}

			// Cập nhật file theo chỉnh sửa
			if (!StringUtils.isEmpty(objReq.getListIdFiles())) {
				List<Long> items = Arrays.asList(objReq.getListIdFiles().split(",")).stream()
						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				fileDinhKemRepository.deleteWithIds(items);
			}
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			if (objReq.getFileDinhKems() != null) {
				for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_DC_DU_TOAN_CHI);
					dinhKem.setQlnvId(nsnn.getId());
					fileDinhKems.add(dinhKem);
				}
				fileDinhKemRepository.saveAll(fileDinhKems);
			}

			if (!StringUtils.isEmpty(objReq.getListIdDeletes())) {
				List<Long> items = Arrays.asList(objReq.getListIdDeletes().split(",")).stream()
						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				qlnvKhvonphiDchinhDuToanChiNsnnCtietRepository.deleteWithIds(items);
			}

			QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn = modelMapper.map(objReq,
					QlnvKhvonphiDchinhDuToanChiNsnn.class);

			qlnvKhvonphiDchinhDuToanChiNsnn.setNguoiSua(userInfo.getUsername());
			qlnvKhvonphiDchinhDuToanChiNsnn.setNgaySua(new Date());

			QlnvKhvonphiDchinhDuToanChiNsnn chiNsnn = qlnvKhvonphiDchinhDuToanChiNsnnRepository
					.save(qlnvKhvonphiDchinhDuToanChiNsnn);

			resp.setData(chiNsnn);
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
	public Resp validate(QlnvKhvonphiDchinhDuToanChiNsnnReq qlnvBaoCaoReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resp function(NhomNutChucNangReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphiDchinhDuToanChiNsnn> optionalBC = qlnvKhvonphiDchinhDuToanChiNsnnRepository
					.findById(objReq.getId());
			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("ID Báo cáo không tồn tại!");
			}
			QlnvKhvonphiDchinhDuToanChiNsnn qlnvKhvonphiDchinhDuToanChiNsnn = optionalBC.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService
					.getDonViById(String.valueOf(qlnvKhvonphiDchinhDuToanChiNsnn.getMaDvi()));

			//kiểm tra quyền thao tác
			qlnvKhvonphiDchinhDuToanChiNsnn.setTrangThai(Utils.function(qlnvKhvonphiDchinhDuToanChiNsnn.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));

			// update trạng thái báo cáo
			qlnvKhvonphiDchinhDuToanChiNsnnRepository.save(qlnvKhvonphiDchinhDuToanChiNsnn);
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnn);
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
