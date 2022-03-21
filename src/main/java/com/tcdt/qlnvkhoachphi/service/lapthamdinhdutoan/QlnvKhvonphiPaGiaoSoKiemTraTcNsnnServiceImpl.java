package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

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
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnCtietResp;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

@Service
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnServiceImpl extends BaseController
		implements QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService {

	@Autowired
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository;

	@Autowired
	private QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity;

	@Autowired
	private QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp detailPa(String ids) {
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findById(Long.parseLong(ids));

		if (!optional.isPresent()) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi phương án");
		}

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp response = new ModelMapper().map(optional.get(),
				QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp.class);
		return response;
	}

	@Override
	public Iterable<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> findAll() {
		Iterable<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findQlnvKhvonphiPaGiaoSoKiemTraTcNsnnByTrangThai();
		return optional;
	}

	@Override
	public QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp detailGiaoSo(String maGiao) throws Exception {
		if (StringUtils.isEmpty(maGiao)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		List<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> listDsachGiaoSoKiemTraTcNsnns = qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository
				.findByMaGiao(maGiao);

		QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp qlnvDsachGiaoSoKiemTraTcNsnnResp = new ModelMapper()
				.map(listDsachGiaoSoKiemTraTcNsnns.get(0), QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp.class);

		ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnCtietResp> lstCtietResps = new ArrayList<>();
		for (QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn qlnvKhDsachGiaoSo : listDsachGiaoSoKiemTraTcNsnns) {
			QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnCtietResp qlDsachGiaoSoKiemTraTcNsnnCtietResp = new ModelMapper()
					.map(qlnvKhDsachGiaoSo, QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnCtietResp.class);

			QlnvDmKhoachVonPhi qlnvDmNoiDung = qlnvDmService
					.getDmKhoachVonPhiById(Long.valueOf(qlnvKhDsachGiaoSo.getMaNoiDung()));
			if (qlnvDmNoiDung != null) {
				qlDsachGiaoSoKiemTraTcNsnnCtietResp.setTenNoiDung(qlnvDmNoiDung.getTenDm());
			}

			QlnvDmKhoachVonPhi qlnvDmNhom = qlnvDmService
					.getDmKhoachVonPhiById(Long.valueOf(qlnvKhDsachGiaoSo.getMaNhom()));

			if (qlnvDmNhom != null) {
				qlDsachGiaoSoKiemTraTcNsnnCtietResp.setTenNhom(qlnvDmNhom.getTenDm());
			}

			lstCtietResps.add(qlDsachGiaoSoKiemTraTcNsnnCtietResp);
		}

		qlnvDsachGiaoSoKiemTraTcNsnnResp.setLstCTiet(lstCtietResps);

		return qlnvDsachGiaoSoKiemTraTcNsnnResp;
	}

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp create(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq) throws Exception {
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

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn giaoSoKiemTraTcNsnn = new ModelMapper().map(objReq,
				QlnvKhvonphiPaGiaoSoKiemTraTcNsnn.class);

		giaoSoKiemTraTcNsnn.setNgayTao(new Date());
		giaoSoKiemTraTcNsnn.setNguoiTao(userInfo.getUsername());
		giaoSoKiemTraTcNsnn.setTrangThai(Constants.TT_BC_1);// Đang soạn

		// TODO: Chưa xử lý trường soQd và soCv

		giaoSoKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(giaoSoKiemTraTcNsnn);

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp response = new ModelMapper().map(giaoSoKiemTraTcNsnn,
				QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp.class);
		return response;
	}

	@Override
	public void delete(String ids) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findById(Long.parseLong(ids));

		if (!optional.isPresent()) {
			throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
		}

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlGiaoSoKiemTraTcNsnn = optional.get();

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(qlGiaoSoKiemTraTcNsnn.getMaDvi());

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
	}

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp update(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq) throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optionalPa = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findById(objReq.getId());

		if (!optionalPa.isPresent()) {
			throw new UnsupportedOperationException(
					"Mã ID: " + objReq.getId() + " của phương án giao sổ kiểm tra không tồn tại");
		}
		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlSoKiemTraTcNsnn = optionalPa.get();

		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(qlSoKiemTraTcNsnn.getMaDvi());
		if (qlnvDmDonvi == null) {
			throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
		}

		if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		for (Role role : userInfo.getRoles()) {
			if (role.getId().equals(Constants.NHAN_VIEN)) {
				if (!qlSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_1)
						&& !qlSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_3)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
				if (!qlSoKiemTraTcNsnn.getTrangThai().equals(Constants.TT_BC_5)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			} else if (role.getId().equals(Constants.LANH_DAO)) {
				throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
			}
		}

		if (!StringUtils.isEmpty(objReq.getListIdDeletes())) {
			List<Long> items = Arrays.asList(objReq.getListIdDeletes().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			for (Long i : items) {
				qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.deleteById(i);
			}
		}

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvNsnn = new ModelMapper().map(objReq,
				QlnvKhvonphiPaGiaoSoKiemTraTcNsnn.class);

		qlnvNsnn.setNguoiSua(userInfo.getUsername());
		qlnvNsnn.setNgaySua(new Date());

		qlnvNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlnvNsnn);

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp response = new ModelMapper().map(qlnvNsnn,
				QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp.class);
		return response;
	}

	@Override
	public Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> colectionPa(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq)
			throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> data = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.selectParams(
				objReq.getNamPa(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getMaDviTao(),
				objReq.getSoQD(), objReq.getSoCv(), objReq.getMaPa(), objReq.getTrangThai(), pageable);

		return data;
	}

	@Override
	public ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> giaoSo(LstQlnvKhvonphiDsachGiaoSoReq objReq)
			throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		// TODO check quyền được giao (chưa biết có role "giao" hay ko?)
		// check xem bản ghi phương án có số QD thì mới được giao
		String maPa = objReq.getLstQlnvKhvonphiDsachGiaoSo().get(0).getMaPa();

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvPaGiaoSoOpt = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findByMaPa(maPa);
		if (qlnvPaGiaoSoOpt == null) {
			throw new UnsupportedOperationException("Phương án chưa có số quyết định");
		}

		if (qlnvPaGiaoSoOpt.getSoCv().isEmpty() || qlnvPaGiaoSoOpt.getSoQd().isEmpty()) {
			throw new UnsupportedOperationException("Phương án chưa có số quyết định hoặc công văn");
		}

		ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> lstQlnvKhvonphiDsachGiaoSo = new ArrayList<>();
		for (QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnReq item : objReq.getLstQlnvKhvonphiDsachGiaoSo()) {
			QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn qlnvDsachGiaoSo = new ModelMapper().map(item,
					QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn.class);

			qlnvDsachGiaoSo.setNgayTao(new Date());
			qlnvDsachGiaoSo.setNguoiTao(userInfo.getUsername());
			// check nếu đã có > 1 bản ghi giao cho đơn vị trong năm 1, thì trạng thái là
			// "giao lại"
//				ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> lstqlnvDsachGiaoSo = qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository
//						.checkBanTinDaGiao(qlnvDsachGiaoSo.getMaPa(), qlnvDsachGiaoSo.getMaDviNhan(),
//								qlnvDsachGiaoSo.getNamGiao(), null, null, null, null,null);
//				if (lstqlnvDsachGiaoSo.isEmpty()) {
//					qlnvDsachGiaoSo.setTrangThai(Constants.TT_DA_GIAO);// Đã giao
//				}
			qlnvDsachGiaoSo.setTrangThai(Constants.TT_DA_GIAO);

			lstQlnvKhvonphiDsachGiaoSo.add(qlnvDsachGiaoSo);
		}

		qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository.saveAll(lstQlnvKhvonphiDsachGiaoSo);

		return lstQlnvKhvonphiDsachGiaoSo;

	}

	@Override
	public Page<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> colection(QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq)
			throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}
		int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
		int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
		Page<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> data = qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository.selectParams(
				objReq.getNamGiao(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getMaDviTao(),
				objReq.getMaDviNhan(), objReq.getMaGiao(), objReq.getMaPa(), objReq.getTrangThai(), pageable);

		for (QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn qlnvKhDsachGiaoSo : data) {
			QlnvDmDonvi qlnvDmDonviTao = qlnvDmService.getDonViById(Long.valueOf(qlnvKhDsachGiaoSo.getMaDviTao()));
			if (!ObjectUtils.isEmpty(qlnvDmDonviTao)) {
				qlnvKhDsachGiaoSo.setTenDviTao(qlnvDmDonviTao.getTenDvi());
			}

			QlnvDmDonvi qlnvDmDonviNhan = qlnvDmService.getDonViById(Long.valueOf(qlnvKhDsachGiaoSo.getMaDviNhan()));
			if (!ObjectUtils.isEmpty(qlnvDmDonviNhan)) {
				qlnvKhDsachGiaoSo.setTenDviNhan(qlnvDmDonviNhan.getTenDvi());
			}

			QlnvDmKhoachVonPhi qlnvDmNoiDung = qlnvDmService
					.getDmKhoachVonPhiById(Long.valueOf(qlnvKhDsachGiaoSo.getMaNoiDung()));
			if (qlnvDmNoiDung != null) {
				qlnvKhDsachGiaoSo.setTenNoiDung(qlnvDmNoiDung.getTenDm());
			}

			QlnvDmKhoachVonPhi qlnvDmNhom = qlnvDmService
					.getDmKhoachVonPhiById(Long.valueOf(qlnvKhDsachGiaoSo.getMaNhom()));
			if (qlnvDmNhom != null) {
				qlnvKhDsachGiaoSo.setTenNhom(qlnvDmNhom.getTenDm());
			}

			if (qlnvKhDsachGiaoSo.getTrangThai().equals(Constants.TT_DA_GIAO)) {
				qlnvKhDsachGiaoSo.setTenTrangThai("Đã giao");
			}

			QlnvDmKhoachVonPhi qlnvDmDviTien = qlnvDmService
					.getDmKhoachVonPhiById(Long.valueOf(qlnvKhDsachGiaoSo.getMaDviTien()));
			if (!ObjectUtils.isEmpty(qlnvDmDviTien)) {
				qlnvKhDsachGiaoSo.setTenDviTien(qlnvDmDviTien.getTenDm());
			}
		}

		return data;
	}

	@Override
	public String genMaGiaoSo() {
		int index = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.getMaGiaoSo();
		String maGiaoSo = "GS".concat(String.valueOf(index));
		return maGiaoSo;
	}

	@Override
	public String genMaPa() {
		int index = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.getMaPa();
		String maPa = "PA".concat(String.valueOf(index));
		return maPa;
	}

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnn function(NhomNutChucNangReq objReq) throws Exception {
		if (objReq == null) {
			return null;
		}

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) {
			throw new Exception("Bad request.");
		}

		Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findById(objReq.getId());
		if (!optional.isPresent()) {
			throw new UnsupportedOperationException("ID Sổ giao kiểm tra không tồn tại!");
		}
		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnKiemTraTcNsnn = optional.get();
		QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(Long.valueOf(qlnKiemTraTcNsnn.getMaDvi()));

		qlnKiemTraTcNsnn.setTrangThai(
				Utils.function(qlnKiemTraTcNsnn.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));

		qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlnKiemTraTcNsnn);

		return qlnKiemTraTcNsnn;
	}

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnn addQdAndCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq)
			throws Exception {
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

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findByMaPa(objReq.getMaPa());

		qlnvKiemTraTcNsnn.setSoCv(objReq.getSoCv());
		qlnvKiemTraTcNsnn.setSoQd(objReq.getSoQd());

		qlnvKiemTraTcNsnn.setNguoiSua(userInfo.getUsername());
		qlnvKiemTraTcNsnn.setNgaySua(new Date());

		if (!StringUtils.isEmpty(objReq.getListIdFiles())) {
			List<Long> items = Arrays.asList(objReq.getListIdFiles().split(",")).stream()
					.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
			fileDinhKemRepository.deleteWithIds(items);
		}
		ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
		if (objReq.getFileDinhKems() != null) {
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KHVONPHI_PA_GIAO_SO_KT);
				dinhKem.setQlnvId(qlnvKiemTraTcNsnn.getId());
				fileDinhKems.add(dinhKem);
			}
			fileDinhKemRepository.saveAll(fileDinhKems);
		}

		qlnvKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlnvKiemTraTcNsnn);

		return qlnvKiemTraTcNsnn;
	}

	@Override
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnn deleteQdCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq)
			throws Exception {
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

		QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findByMaPa(objReq.getMaPa());

		if (qlnvKiemTraTcNsnn.getSoCv() == null || qlnvKiemTraTcNsnn.getSoQd() == null) {
			throw new UnsupportedOperationException("Mã phương án chưa nhập quyết định và công văn!");
		}

		qlnvKiemTraTcNsnn.setSoCv(null);
		qlnvKiemTraTcNsnn.setSoQd(null);

		qlnvKiemTraTcNsnn.setNguoiSua(userInfo.getUsername());
		qlnvKiemTraTcNsnn.setNgaySua(new Date());

		qlnvKiemTraTcNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlnvKiemTraTcNsnn);

		return qlnvKiemTraTcNsnn;
	}

}
