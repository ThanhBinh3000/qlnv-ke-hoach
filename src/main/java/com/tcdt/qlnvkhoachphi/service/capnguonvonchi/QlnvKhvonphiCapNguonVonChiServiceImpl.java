package com.tcdt.qlnvkhoachphi.service.capnguonvonchi;

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

import com.tcdt.qlnvkhoachphi.repository.capnguonvonchi.QlnvKhvonphiDnghiCapvonCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.capnguonvonchi.QlnvKhvonphiDnghiCapvonRepository;
import com.tcdt.qlnvkhoachphi.repository.capnguonvonchi.QlnvKhvonphiThopCapvonCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.capnguonvonchi.QlnvKhvonphiThopCapvonRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.capnguonvonchi.QlnvKhvonphiDnghiCapvonResp;
import com.tcdt.qlnvkhoachphi.response.capnguonvonchi.QlnvKhvonphiThopCapvonResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvon;
import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiThopCapvon;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

@Service
public class QlnvKhvonphiCapNguonVonChiServiceImpl implements QlnvKhvonphiCapNguonVonChiService {
	@Autowired
	private QlnvKhvonphiDnghiCapvonRepository qlnvKhvonphiDnghiCapvonRepository;

	@Autowired
	private QlnvKhvonphiDnghiCapvonCtietRepository qlnvKhvonphiDnghiCapvonCtietRepository;

	@Autowired
	private QlnvKhvonphiThopCapvonRepository qlnvKhvonphiThopCapvonRepository;

	@Autowired
	private QlnvKhvonphiThopCapvonCtietRepository qlnvKhvonphiThopCapvonCtietRepository;

	@Autowired
	private QlnvDmService qlnvDmService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public QlnvKhvonphiDnghiCapvonResp detailDncv(Long id) throws Exception {
		if (StringUtils.isEmpty(id)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}
		Optional<QlnvKhvonphiDnghiCapvon> qOptional = qlnvKhvonphiDnghiCapvonRepository.findById(id);
		if (!qOptional.isPresent()) {
			throw new UnsupportedOperationException("Bản ghi không tồn tại");
		}
		QlnvKhvonphiDnghiCapvon qlnvKhvonphiDnghiCapvon = qOptional.get();

		// mapping response
		QlnvKhvonphiDnghiCapvonResp qlnvKhvonphiDnghiCapvonResp = modelMapper.map(qlnvKhvonphiDnghiCapvon,
				QlnvKhvonphiDnghiCapvonResp.class);
		// lấy danh sách file đính kèm
		ArrayList<FileDinhKem> lstFile = new ArrayList<>();
		ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository
				.findFileDinhKemByQlnvId(qlnvKhvonphiDnghiCapvon.getId());
		lstFile.addAll(fileDinhKem);
		qlnvKhvonphiDnghiCapvonResp.setLstFile(lstFile);
		return qlnvKhvonphiDnghiCapvonResp;
	}

	@Override
	public Page<QlnvKhvonphiDnghiCapvon> searchDncv(QlnvKhvonphiDnghiCapvonSearchReq objReq) throws Exception {
		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiDnghiCapvon> data = qlnvKhvonphiDnghiCapvonRepository.selectParams(objReq.getNgayTaoTu(),
				objReq.getNgayTaoDen(), objReq.getMaDvi(), objReq.getTrangThai(), pageable);

		for (QlnvKhvonphiDnghiCapvon qlnvKhvonphiDnghiCapvon : data) {
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvKhvonphiDnghiCapvon.getMaDvi()));
			if (!ObjectUtils.isEmpty(qlnvDmDonvi)) {
				qlnvKhvonphiDnghiCapvon.setTenDvi(qlnvDmDonvi.getTenDvi());
			}
			qlnvKhvonphiDnghiCapvon.setTenTrangThai(Utils.getTenTrangThai(qlnvKhvonphiDnghiCapvon.getTrangThai()));
		}

		return data;
	}

	@Override
	public QlnvKhvonphiDnghiCapvonReq createDncv(QlnvKhvonphiDnghiCapvonReq objReq) throws Exception {
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
		QlnvKhvonphiDnghiCapvon qlnvKhvonphiDnghiCapvon = modelMapper.map(objReq, QlnvKhvonphiDnghiCapvon.class);
		qlnvKhvonphiDnghiCapvon.setTrangThai(Constants.TT_BC_1);// Đang soạn
		qlnvKhvonphiDnghiCapvon.setNgayTao(new Date());
		qlnvKhvonphiDnghiCapvon.setNguoiTao(userInfo.getUsername());
		QlnvKhvonphiDnghiCapvon chiNsnn = qlnvKhvonphiDnghiCapvonRepository.save(qlnvKhvonphiDnghiCapvon);

		// Thêm mới file đính kèm:
		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (objReq.getFileDinhKems() != null) {
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				if (dinhKemReq instanceof FileDinhKemReq) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_CAP_NGUON_VON_CHI);
					dinhKem.setQlnvId(chiNsnn.getId());
					fileDinhKems.add(dinhKem);
				} else {
					throw new UnsupportedOperationException(" Sai định dạng file!");
				}
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}
		objReq.setId(chiNsnn.getId());
		return objReq;
	}

	@Override
	public Resp syntheticDncv(TongHopDchinhDuToanChiNsnnReq objReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QlnvKhvonphiDnghiCapvon deleteDncv(String ids) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiDnghiCapvon> optionalBC = qlnvKhvonphiDnghiCapvonRepository.findById(Long.parseLong(ids));

		if (!optionalBC.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
		}
		QlnvKhvonphiDnghiCapvon dnghiCapvon = optionalBC.get();

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.valueOf(dnghiCapvon.getMaDvi()));
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		// check khác đơn vị
		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		// check với trạng thái hiện tại không được xóa
		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!dnghiCapvon.getTrangThai().equals(Constants.TT_BC_1)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
			}
		}
		dnghiCapvon.setTrangThai(Constants.TT_BC_0);// // Đã xóa

		return dnghiCapvon;
	}

	@Override
	public QlnvKhvonphiDnghiCapvonReq updateDncv(QlnvKhvonphiDnghiCapvonReq objReq) throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		Optional<QlnvKhvonphiDnghiCapvon> optionalBC = qlnvKhvonphiDnghiCapvonRepository.findById(objReq.getId());
		if (!optionalBC.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + objReq.getId() + " của báo cáo không tồn tại");
		}
		QlnvKhvonphiDnghiCapvon nsnn = optionalBC.get();
		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.valueOf(nsnn.getMaDvi()));
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
				if (!nsnn.getTrangThai().equals(Constants.TT_BC_1) && !nsnn.getTrangThai().equals(Constants.TT_BC_3)) {
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
				if (dinhKemReq instanceof FileDinhKemReq) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_CAP_NGUON_VON_CHI);
					dinhKem.setQlnvId(nsnn.getId());
					fileDinhKems.add(dinhKem);
				} else {
					throw new UnsupportedOperationException(" Sai định dạng file!");
				}
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		if (!StringUtils.isEmpty(objReq.getListIdDeletes())) {
			List<Long> items = Arrays.asList(objReq.getListIdDeletes().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			qlnvKhvonphiDnghiCapvonCtietRepository.deleteWithIds(items);
		}

		QlnvKhvonphiDnghiCapvon qlnvKhvonphiDnghiCapvon = modelMapper.map(objReq, QlnvKhvonphiDnghiCapvon.class);

		qlnvKhvonphiDnghiCapvon.setNguoiSua(userInfo.getUsername());
		qlnvKhvonphiDnghiCapvon.setNgaySua(new Date());
		qlnvKhvonphiDnghiCapvonRepository.save(qlnvKhvonphiDnghiCapvon);

		return objReq;
	}

	@Override
	public Resp validate(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resp function(NhomNutChucNangReq objReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QlnvKhvonphiThopCapvonResp detailThcv(Long id) throws Exception {
		if (StringUtils.isEmpty(id)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}
		Optional<QlnvKhvonphiThopCapvon> qOptional = qlnvKhvonphiThopCapvonRepository.findById(id);
		if (!qOptional.isPresent()) {
			throw new UnsupportedOperationException("Bản ghi không tồn tại");
		}
		QlnvKhvonphiThopCapvon qlnvKhvonphiThopCapvon = qOptional.get();

		// mapping response
		QlnvKhvonphiThopCapvonResp qlnvKhvonphiThopResp = modelMapper.map(qlnvKhvonphiThopCapvon,
				QlnvKhvonphiThopCapvonResp.class);
		// lấy danh sách file đính kèm
		ArrayList<FileDinhKem> lstFile = new ArrayList<>();
		ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository
				.findFileDinhKemByQlnvId(qlnvKhvonphiThopCapvon.getId());
		lstFile.addAll(fileDinhKem);
		qlnvKhvonphiThopResp.setLstFile(lstFile);

		return qlnvKhvonphiThopResp;
	}

	@Override
	public Resp syntheticThcv(TongHopDchinhDuToanChiNsnnReq objReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QlnvKhvonphiThopCapvon deleteThcv(String ids) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiThopCapvon> optionalBC = qlnvKhvonphiThopCapvonRepository.findById(Long.parseLong(ids));

		if (!optionalBC.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
		}
		QlnvKhvonphiThopCapvon thopCapvon = optionalBC.get();

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.valueOf(thopCapvon.getMaDvi()));
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		// check khác đơn vị
		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		// check với trạng thái hiện tại không được xóa
		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!thopCapvon.getTrangThai().equals(Constants.TT_BC_1)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
			}
		}
		thopCapvon.setTrangThai(Constants.TT_BC_0);// // Đã xóa

		return thopCapvon;
	}

	@Override
	public Page<QlnvKhvonphiThopCapvon> searchThcv(QlnvKhvonphiThopCapvonSearchReq objReq) throws Exception {

		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiThopCapvon> data = qlnvKhvonphiThopCapvonRepository.selectParams(objReq.getNgayTaoTu(),
				objReq.getNgayTaoDen(), objReq.getMaDvi(), objReq.getTrangThai(), pageable);

		for (QlnvKhvonphiThopCapvon qlnvKhvonphiDnghiCapvon : data) {
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(qlnvKhvonphiDnghiCapvon.getMaDvi()));
			if (!ObjectUtils.isEmpty(qlnvDmDonvi)) {
				qlnvKhvonphiDnghiCapvon.setTenDvi(qlnvDmDonvi.getTenDvi());
			}
			qlnvKhvonphiDnghiCapvon.setTenTrangThai(Utils.getTenTrangThai(qlnvKhvonphiDnghiCapvon.getTrangThai()));
		}

		return data;
	}

	@Override
	public QlnvKhvonphiThopCapvonReq createThcv(QlnvKhvonphiThopCapvonReq objReq) throws Exception {
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
		QlnvKhvonphiThopCapvon qlnvKhvonphiThopCapvon = modelMapper.map(objReq, QlnvKhvonphiThopCapvon.class);
		qlnvKhvonphiThopCapvon.setTrangThai(Constants.TT_BC_1);// Đang soạn
		qlnvKhvonphiThopCapvon.setNgayTao(new Date());
		qlnvKhvonphiThopCapvon.setNguoiTao(userInfo.getUsername());
		QlnvKhvonphiThopCapvon chiNsnn = qlnvKhvonphiThopCapvonRepository.save(qlnvKhvonphiThopCapvon);

		// Thêm mới file đính kèm:
		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (objReq.getFileDinhKems() != null) {
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				if (dinhKemReq instanceof FileDinhKemReq) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_CAP_NGUON_VON_CHI);
					dinhKem.setQlnvId(chiNsnn.getId());
					fileDinhKems.add(dinhKem);
				} else {
					throw new UnsupportedOperationException(" Sai định dạng file!");
				}
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}
		objReq.setId(chiNsnn.getId());

		return objReq;
	}

	@Override
	public QlnvKhvonphiThopCapvonReq updateThcv(QlnvKhvonphiThopCapvonReq objReq) throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		Optional<QlnvKhvonphiThopCapvon> optionalBC = qlnvKhvonphiThopCapvonRepository.findById(objReq.getId());
		if (!optionalBC.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + objReq.getId() + " của báo cáo không tồn tại");
		}
		QlnvKhvonphiThopCapvon nsnn = optionalBC.get();
		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.parseLong(nsnn.getMaDvi()));
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
				if (!nsnn.getTrangThai().equals(Constants.TT_BC_1) && !nsnn.getTrangThai().equals(Constants.TT_BC_3)) {
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
				if (dinhKemReq instanceof FileDinhKemReq) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_CAP_NGUON_VON_CHI);
					dinhKem.setQlnvId(nsnn.getId());
					fileDinhKems.add(dinhKem);
				} else {
					throw new UnsupportedOperationException(" Sai định dạng file!");
				}
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		if (!StringUtils.isEmpty(objReq.getListIdDeletes())) {
			List<Long> items = Arrays.asList(objReq.getListIdDeletes().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			qlnvKhvonphiThopCapvonCtietRepository.deleteWithIds(items);
		}

		QlnvKhvonphiThopCapvon qlnvKhvonphiThopCapvon = modelMapper.map(objReq, QlnvKhvonphiThopCapvon.class);

		qlnvKhvonphiThopCapvon.setNguoiSua(userInfo.getUsername());
		qlnvKhvonphiThopCapvon.setNgaySua(new Date());

		qlnvKhvonphiThopCapvonRepository.save(qlnvKhvonphiThopCapvon);

		return objReq;
	}

}
