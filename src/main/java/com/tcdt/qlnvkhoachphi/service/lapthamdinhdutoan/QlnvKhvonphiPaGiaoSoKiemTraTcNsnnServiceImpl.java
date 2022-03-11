package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
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
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnCtietResp;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnServiceImpl extends BaseController implements QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService{
	
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
	public Resp detailPa(String ids) {
		// TODO Auto-generated method stub
	Resp resp = new Resp();
	try {
		if (StringUtils.isEmpty(ids)) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}

		Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
				.findById(Long.parseLong(ids));

		if (!optional.isPresent()) {
			throw new UnsupportedOperationException("Không tồn tại bản ghi phương án");
		}

		resp.setData(optional.get());
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
	public Resp findAll() {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {

			Iterable<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findQlnvKhvonphiPaGiaoSoKiemTraTcNsnnByTrangThai();

			resp.setData(optional);
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
	public Resp detailGiaoSo(String maGiao) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
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
						.getDmKhoachVonPhiById(qlnvKhDsachGiaoSo.getMaNoiDung());
				if (qlnvDmNoiDung != null) {
					qlDsachGiaoSoKiemTraTcNsnnCtietResp.setTenNoiDung(qlnvDmNoiDung.getTenDm());
				}

				QlnvDmKhoachVonPhi qlnvDmNhom = qlnvDmService.getDmKhoachVonPhiById(qlnvKhDsachGiaoSo.getMaNhom());

				if (qlnvDmNhom != null) {
					qlDsachGiaoSoKiemTraTcNsnnCtietResp.setTenNhom(qlnvDmNhom.getTenDm());
				}

				lstCtietResps.add(qlDsachGiaoSoKiemTraTcNsnnCtietResp);
			}

			qlnvDsachGiaoSoKiemTraTcNsnnResp.setLstCTiet(lstCtietResps);

			resp.setData(qlnvDsachGiaoSoKiemTraTcNsnnResp);
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
	public Resp create(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			UserInfo userInfo = getUserInfo(req);
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

			resp.setData(giaoSoKiemTraTcNsnn);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
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

			Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findById(Long.parseLong(ids));

			if (!optional.isPresent()) {
				throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
			}

			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlGiaoSoKiemTraTcNsnn = optional.get();

			UserInfo userInfo = getUserInfo(req);

//			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlGiaoSoKiemTraTcNsnn.getMaDvi()));
//
//			if (qlnvDmDonvi == null) {
//				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
//			}
//
//			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
//				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
//			}

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

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return resp;
	}

	@Override
	public Resp update(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq qlnvReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optionalPa = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findById(qlnvReq.getId());

			if (!optionalPa.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + qlnvReq.getId() + " của phương án giao sổ kiểm tra không tồn tại");
			}
			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlSoKiemTraTcNsnn = optionalPa.get();

			UserInfo userInfo = getUserInfo(req);
//			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlSoKiemTraTcNsnn.getMaDvi()));
//			if (qlnvDmDonvi == null) {
//				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
//			}
//
//			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
//				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
//			}

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

			if (!StringUtils.isEmpty(qlnvReq.getListIdDeletes())) {
				List<Long> items = Arrays.asList(qlnvReq.getListIdDeletes().split(",")).stream()
						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				for (Long i : items) {
					qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.deleteById(i);
				}
//				qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity.deleteWithIds(items);
			}

			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvNsnn = new ModelMapper().map(qlnvReq,
					QlnvKhvonphiPaGiaoSoKiemTraTcNsnn.class);

			qlnvNsnn.setNguoiSua(userInfo.getUsername());
			qlnvNsnn.setNgaySua(new Date());

			qlnvNsnn = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlnvNsnn);

			resp.setData(qlnvNsnn);
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
	public Resp colectionPa(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> data = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.selectParams(
					objReq.getNamPa(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getMaDviTao(),
					objReq.getSoQD(), objReq.getSoCv(), objReq.getMaPa(), objReq.getTrangThai(), pageable);

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
//				QlnvDmDonvi qlnvDmDonviTao = qlnvDmService.getDonViById(Long.valueOf(qlnvKhvonphiPaGiaoSo.getMaDviTao()));
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
		return resp;
	}

	@Override
	public Resp giaoSo(LstQlnvKhvonphiDsachGiaoSoReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {

			// TODO check quyền được giao (chưa biết có role "giao" hay ko?)
			// check xem bản ghi phương án có số QD thì mới được giao
			String maPa = objReq.getLstQlnvKhvonphiDsachGiaoSo().get(0).getMaPa();
			// TODO get QlnvKhvonphiPaGiaoSoKiemTraTcNsnn theo mã phương án
			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnvPaGiaoSoOpt = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findByMaPa(maPa);
			if (qlnvPaGiaoSoOpt == null) {
				throw new UnsupportedOperationException("Phương án chưa có số quyết định");
			}

			// TODO Kiểm tra quyết định và công văn
			if (qlnvPaGiaoSoOpt.getSoCv().isEmpty() || qlnvPaGiaoSoOpt.getSoQd().isEmpty()) {
				throw new UnsupportedOperationException("Phương án chưa có số quyết định hoặc công văn");
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
	public Resp colection(QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> data = qlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository
					.selectParams(objReq.getNamGiao(), objReq.getNgayTaoTu(), objReq.getNgayTaoDen(),
							objReq.getMaDviTao(), objReq.getMaDviNhan(), objReq.getMaGiao(), objReq.getMaPa(),
							objReq.getTrangThai(), pageable);

			for (QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn qlnvKhDsachGiaoSo : data) {
				// get tên các mã
				QlnvDmDonvi qlnvDmDonviTao = qlnvDmService.getDonViById(qlnvKhDsachGiaoSo.getMaDviTao());
				if (!ObjectUtils.isEmpty(qlnvDmDonviTao)) {
					qlnvKhDsachGiaoSo.setTenDviTao(qlnvDmDonviTao.getTenDvi());
				}

				QlnvDmDonvi qlnvDmDonviNhan = qlnvDmService.getDonViById(qlnvKhDsachGiaoSo.getMaDviNhan());
				if (!ObjectUtils.isEmpty(qlnvDmDonviNhan)) {
					qlnvKhDsachGiaoSo.setTenDviNhan(qlnvDmDonviNhan.getTenDvi());
				}

				QlnvDmKhoachVonPhi qlnvDmNoiDung = qlnvDmService
						.getDmKhoachVonPhiById(qlnvKhDsachGiaoSo.getMaNoiDung());
				if (qlnvDmNoiDung != null) {
					qlnvKhDsachGiaoSo.setTenNoiDung(qlnvDmNoiDung.getTenDm());
				}

				QlnvDmKhoachVonPhi qlnvDmNhom = qlnvDmService.getDmKhoachVonPhiById(qlnvKhDsachGiaoSo.getMaNhom());
				if (qlnvDmNhom != null) {
					qlnvKhDsachGiaoSo.setTenNhom(qlnvDmNhom.getTenDm());
				}

				if (qlnvKhDsachGiaoSo.getTrangThai().equals(Constants.TT_DA_GIAO)) {
					qlnvKhDsachGiaoSo.setTenTrangThai("Đã giao");
				}

				QlnvDmKhoachVonPhi qlnvDmDviTien = qlnvDmService
						.getDmKhoachVonPhiById(qlnvKhDsachGiaoSo.getMaDviTien());
				if (!ObjectUtils.isEmpty(qlnvDmDviTien)) {
					qlnvKhDsachGiaoSo.setTenDviTien(qlnvDmDviTien.getTenDm());
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
		return resp;
	}

	@Override
	public Resp genMaGiaoSo() {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			int index = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.getMaGiaoSo();
			String maGiaoSo = "GS".concat(String.valueOf(index));
			resp.setData(maGiaoSo);
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
	public Resp genMaPa() {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			int index = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.getMaPa();
			String maPa = "PA".concat(String.valueOf(index));
			resp.setData(maPa);
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
	public Resp function(NhomNutChucNangReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			UserInfo userInfo = getUserInfo(req);

			Optional<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> optional = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository
					.findById(objReq.getId());
			if (!optional.isPresent()) {
				throw new UnsupportedOperationException("ID Sổ giao kiểm tra không tồn tại!");
			}
			QlnvKhvonphiPaGiaoSoKiemTraTcNsnn qlnKiemTraTcNsnn = optional.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnKiemTraTcNsnn.getMaDvi()));
			
			//kiểm tra quyền thao tác
			qlnKiemTraTcNsnn.setTrangThai(Utils.function(qlnKiemTraTcNsnn.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));

			// update trạng thái báo cáo
			qlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository.save(qlnKiemTraTcNsnn);

			resp.setData(qlnKiemTraTcNsnn);
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
	public Resp addQdAndCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			UserInfo userInfo = getUserInfo(req);

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

			resp.setData(qlnvKiemTraTcNsnn);
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
	public Resp deleteQdCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Resp resp = new Resp();
		try {
			UserInfo userInfo = getUserInfo(req);

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

			resp.setData(qlnvKiemTraTcNsnn);
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
