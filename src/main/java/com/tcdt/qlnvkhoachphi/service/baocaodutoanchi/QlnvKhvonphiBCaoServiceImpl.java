package com.tcdt.qlnvkhoachphi.service.baocaodutoanchi;

import java.text.SimpleDateFormat;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi.QlnvKhvonphiBcaoRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl1Repository;
import com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl2Repository;
import com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl3Repository;
import com.tcdt.qlnvkhoachphi.repository.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaCtietThienRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienNhapMuaHangRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienXuatHangRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.QlnvKhvonphiBcaoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.TongHopBcaoReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.baocaodutoanchi.QlnvKhvonphiBcaoSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.baocaodutoanchi.QlnvKhvonphiBcaoResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcao;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl1;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl2;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl3;
import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaCtietThien;
import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienNhapMuaHang;
import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienXuatHang;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiBCaoServiceImpl implements IQlnvKhvonphiBCaoService {
	@Autowired
	private QlnvKhvonphiBcaoRepository qlnvKhvonphiBcaoRepository;

	@Autowired
	private QlnvKhvonphiBcaoThinhSdungDtoanPl1Repository qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository;

	@Autowired
	private QlnvKhvonphiBcaoThinhSdungDtoanPl2Repository qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository;

	@Autowired
	private QlnvKhvonphiBcaoThinhSdungDtoanPl3Repository qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository;

	@Autowired
	private QlnvKhvonphiBcaoKquaThienNhapMuaHangRepository qlnvKhvonphiBcaoKquaThienNhapMuaHangRepository;

	@Autowired
	private QlnvKhvonphiBcaoKquaThienXuatHangRepository qlnvKhvonphiBcaoKquaThienXuatHangRepository;

	@Autowired
	private QlnvKhvonphiBcaoKquaCtietThienRepository qlnvKhvonphiBcaoKquaCtietThienRepository;


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
			Optional<QlnvKhvonphiBcao> qOptional = qlnvKhvonphiBcaoRepository.findById(Long.parseLong(ids));
			QlnvKhvonphiBcao qlnvKhvonphiBcao = null;
			if (qOptional.isPresent()) {
				qlnvKhvonphiBcao = qOptional.get();
			} else {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			String maLoaiBcao = qlnvKhvonphiBcao.getMaLoaiBcao();
			Long loaiBaoCao = qlnvKhvonphiBcao.getLoaiBaoCao();
			Object lstCTietBcao = null;

			// check maLoaiBcao trả về object tương ứng
			if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1)) {// 50
				lstCTietBcao = qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository
						.findQlnvKhvonphiBcaoThinhSdungDtoanPl1ByQlnvKhvonphiBcaoId(qlnvKhvonphiBcao.getId());

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2)) {// 51
				lstCTietBcao = qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository
						.findQlnvKhvonphiBcaoThinhSdungDtoanPl2ByQlnvKhvonphiBcaoId(qlnvKhvonphiBcao.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3)) {// 52
				lstCTietBcao = qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository
						.findQlnvKhvonphiBcaoThinhSdungDtoanPl3ByQlnvKhvonphiBcaoId(qlnvKhvonphiBcao.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG)) {// 90
				lstCTietBcao = qlnvKhvonphiBcaoKquaThienNhapMuaHangRepository
						.findQlnvKhvonphiBcaoKquaThienNhapMuaHangByQlnvKhvonphiBcaoId(qlnvKhvonphiBcao.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG)) {// 91
				lstCTietBcao = qlnvKhvonphiBcaoKquaThienXuatHangRepository
						.findQlnvKhvonphiBcaoKquaThienXuatHangByQlnvKhvonphiBcaoId(qlnvKhvonphiBcao.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_NHAP_MUA)
					|| maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_XUAT_HANG)
					|| maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_BQUAN_LAN_DAU)) {//92,93,94
				lstCTietBcao = qlnvKhvonphiBcaoKquaCtietThienRepository
						.findQlnvKhvonphiBcaoKquaCtietThienByQlnvKhvonphiBcaoKquaThienId(qlnvKhvonphiBcao.getId(),loaiBaoCao);
			}
			// mapping response
			QlnvKhvonphiBcaoResp qlnvKhvonphiBcaoResp = modelMapper.map(qlnvKhvonphiBcao, QlnvKhvonphiBcaoResp.class);
			qlnvKhvonphiBcaoResp.setLstCTietBCao(lstCTietBcao);

			// lấy danh sách file đính kèm
			ArrayList<FileDinhKem> lstFile = new ArrayList<>();
			ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository
					.findFileDinhKemByQlnvId(qlnvKhvonphiBcao.getId());
			lstFile.addAll(fileDinhKem);
			qlnvKhvonphiBcaoResp.setLstFile(lstFile);

			if (ObjectUtils.isEmpty(qlnvKhvonphiBcaoResp)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}
			resp.setData(qlnvKhvonphiBcaoResp);
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
	public Resp getList(QlnvKhvonphiBcaoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphiBcao> data = qlnvKhvonphiBcaoRepository.selectParams(objReq.getMaDvi(),
					objReq.getNgayTaoTu(), objReq.getNgayTaoDen(), objReq.getTrangThai(), objReq.getNamBcao(),
					objReq.getThangBcao(), objReq.getMaLoaiBcao(), objReq.getMaBcao(), objReq.getDotBcao(),
					objReq.getLoaiBaoCao(), pageable);

			for (QlnvKhvonphiBcao qlnvKhvonphi : data) {
				QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnvKhvonphi.getMaDvi()));
				if (!ObjectUtils.isEmpty(qlnvDmDonvi)) {
					qlnvKhvonphi.setTenDvi(qlnvDmDonvi.getTenDvi());
				}
				qlnvKhvonphi.setTenTrangThai(Utils.getTenTrangThai(qlnvKhvonphi.getTrangThai()));
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
	public Resp add(QlnvKhvonphiBcaoReq objReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			// check role nhân viên mới được tạo
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
				}
			}
			List<QlnvKhvonphiBcao> khvonphis = qlnvKhvonphiBcaoRepository.findByMaBcao(objReq.getMaBcao());
			if (!khvonphis.isEmpty()) {
				throw new UnsupportedOperationException("Mã báo cáo đã tồn tại!");
			}
			QlnvKhvonphiBcao qlnvKhvonphiBcao = modelMapper.map(objReq, QlnvKhvonphiBcao.class);
			qlnvKhvonphiBcao.setTrangThai(Constants.TT_BC_1);// Đang soạn
			qlnvKhvonphiBcao.setNgayTao(new Date());
			qlnvKhvonphiBcao.setNguoiTao(userInfo.getUsername());
			String maLoaiBcao = qlnvKhvonphiBcao.getMaLoaiBcao();
			Long loaiBcao = qlnvKhvonphiBcao.getLoaiBaoCao();
			QlnvKhvonphiBcao createCheck = qlnvKhvonphiBcaoRepository.save(qlnvKhvonphiBcao);

			// thêm mới file đính kèm:
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			if (objReq.getFileDinhKems() != null) {
				for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_BC_DU_TOAN_CHI);
					dinhKem.setQlnvId(createCheck.getId());
					fileDinhKems.add(dinhKem);
				}
				fileDinhKemRepository.saveAll(fileDinhKems);
			}

			if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1)) {// 50
				ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl1> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoThinhSdungDtoanPl1 cTietBcao = modelMapper.map(item,
							QlnvKhvonphiBcaoThinhSdungDtoanPl1.class);
					cTietBcao.setQlnvKhvonphiBcaoId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository.saveAll(dataMap);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2)) {// 51
				ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl2> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoThinhSdungDtoanPl2 cTietBcao = modelMapper.map(item,
							QlnvKhvonphiBcaoThinhSdungDtoanPl2.class);
					cTietBcao.setQlnvKhvonphiBcaoId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository.saveAll(dataMap);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3)) {// 52
				ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl3> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoThinhSdungDtoanPl3 cTietBcao = modelMapper.map(item,
							QlnvKhvonphiBcaoThinhSdungDtoanPl3.class);
					cTietBcao.setQlnvKhvonphiBcaoId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository.saveAll(dataMap);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG)) {//90
				ArrayList<QlnvKhvonphiBcaoKquaThienNhapMuaHang> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoKquaThienNhapMuaHang cTietBcao = modelMapper.map(item,
							QlnvKhvonphiBcaoKquaThienNhapMuaHang.class);
					cTietBcao.setQlnvKhvonphiBcaoId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiBcaoKquaThienNhapMuaHangRepository.saveAll(dataMap);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG)) {//91
				ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoKquaThienXuatHang cTietBcao = modelMapper.map(item,
							QlnvKhvonphiBcaoKquaThienXuatHang.class);
					cTietBcao.setQlnvKhvonphiBcaoId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiBcaoKquaThienXuatHangRepository.saveAll(dataMap);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_NHAP_MUA)
					|| maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_XUAT_HANG)
					|| maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_BQUAN_LAN_DAU)) {
				ArrayList<QlnvKhvonphiBcaoKquaCtietThien> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoKquaCtietThien cTietBcao = modelMapper.map(item,
							QlnvKhvonphiBcaoKquaCtietThien.class);
					cTietBcao.setQlnvKhvonphiBcaoId(createCheck.getId());
					cTietBcao.setLoaiBaoCao(loaiBcao);
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiBcaoKquaCtietThienRepository.saveAll(dataMap);
			}
			objReq.setId(createCheck.getId());
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
	public Resp synthetic(TongHopBcaoReq objReq) {
		Resp resp = new Resp();
		try {
			String maDvi = objReq.getMaDvi();
			String maLoaiBcao = objReq.getMaLoaiBcao();
			Long namBcao = objReq.getNamBcao();
			Long thangBcao = objReq.getThangBcao();
			Object lstCTietBcao = null;
			// check loại báo cáo cần tổng hợp
			// Chi cục và cục khu vực
			if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1)) {// 50
				lstCTietBcao = qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository.synthesis(maDvi, namBcao, thangBcao);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2)) {// 51
				lstCTietBcao = qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository.synthesis(maDvi, namBcao, thangBcao);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3)) {// 52
				lstCTietBcao = qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository.synthesis(maDvi, namBcao, thangBcao);
			}
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
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphiBcao> optionalBC = qlnvKhvonphiBcaoRepository.findById(Long.parseLong(ids));

			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
			}
			QlnvKhvonphiBcao baoCao = optionalBC.get();

			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(baoCao.getMaDvi()));
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
					if (!baoCao.getTrangThai().equals(Constants.TT_BC_1)) {
						throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			}
			baoCao.setTrangThai(Constants.TT_BC_0);// // Đã xóa
//			resp.setData(lstCTietBcao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());

		}catch (

				Exception e) {
					resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
					resp.setMsg(e.getMessage());
					log.error(e.getMessage());
				}
				return resp;

	}

	@Override
	public Resp update(QlnvKhvonphiBcaoReq qlnvBaoCaoReq, UserInfo userInfo) {
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphiBcao> optionalBC = qlnvKhvonphiBcaoRepository.findById(qlnvBaoCaoReq.getId());
			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + qlnvBaoCaoReq.getId() + " của báo cáo không tồn tại");
			}
			QlnvKhvonphiBcao khvonphiBcao = optionalBC.get();

			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(khvonphiBcao.getMaDvi()));
			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}
			// check khác đơn vị
			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

//			 check với trạng thái hiện tại không được sửa
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!khvonphiBcao.getTrangThai().equals(Constants.TT_BC_1)
							&& !khvonphiBcao.getTrangThai().equals(Constants.TT_BC_3)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					if (!khvonphiBcao.getTrangThai().equals(Constants.TT_BC_5)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			}

			khvonphiBcao.setNguoiSua(userInfo.getUsername());
			khvonphiBcao.setNgaySua(new Date());

			// Cập nhật file theo chỉnh sửa
			if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdFiles())) {
				List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdFiles().split(",")).stream()
						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				fileDinhKemRepository.deleteWithIds(items);
			}
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			if (qlnvBaoCaoReq.getFileDinhKems() != null) {
				for (FileDinhKemReq dinhKemReq : qlnvBaoCaoReq.getFileDinhKems()) {
					FileDinhKem dinhKem = modelMapper.map(dinhKemReq, FileDinhKem.class);
					dinhKem.setCreateDate(new Date());
					dinhKem.setDataType(Constants.QLNV_KHVONPHI_TD_DU_TOAN);
					dinhKem.setQlnvId(khvonphiBcao.getId());
					fileDinhKems.add(dinhKem);
				}
				fileDinhKemRepository.saveAll(fileDinhKems);
			}

			if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1)) {
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl1> listUpdates = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoThinhSdungDtoanPl1 bcaoThinhSdungDtoanPl1Update = modelMapper.map(qlnvCTiet,
							QlnvKhvonphiBcaoThinhSdungDtoanPl1.class);
					// Check id tồn tại thì update
					if (bcaoThinhSdungDtoanPl1Update.getId() != null) {
						Optional<QlnvKhvonphiBcaoThinhSdungDtoanPl1> optional = qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository
								.findById(bcaoThinhSdungDtoanPl1Update.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiBcaoThinhSdungDtoanPl1 cTietBcao = modelMapper.map(qlnvCTiet,
									QlnvKhvonphiBcaoThinhSdungDtoanPl1.class);
							cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							listUpdates.add(cTietBcao);
						} else {
							// update
							QlnvKhvonphiBcaoThinhSdungDtoanPl1 bcaoThinhSdungDtoanPl1 = optional.get();
							bcaoThinhSdungDtoanPl1.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							updateObjectToObject(bcaoThinhSdungDtoanPl1, bcaoThinhSdungDtoanPl1Update);
							listUpdates.add(bcaoThinhSdungDtoanPl1);
						}
					} else {
						// thêm mới
						QlnvKhvonphiBcaoThinhSdungDtoanPl1 cTietBcao = modelMapper.map(qlnvCTiet,
								QlnvKhvonphiBcaoThinhSdungDtoanPl1.class);
						cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
						listUpdates.add(cTietBcao);

					}
				}
				qlnvKhvonphiBcaoThinhSdungDtoanPl1Repository.saveAll(listUpdates);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2)) {
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl2> listUpdates = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoThinhSdungDtoanPl2 bcaoThinhSdungDtoanPl2Update = modelMapper.map(qlnvCTiet,
							QlnvKhvonphiBcaoThinhSdungDtoanPl2.class);
					// Check id tồn tại thì update
					if (bcaoThinhSdungDtoanPl2Update.getId() != null) {
						Optional<QlnvKhvonphiBcaoThinhSdungDtoanPl2> optional = qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository
								.findById(bcaoThinhSdungDtoanPl2Update.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiBcaoThinhSdungDtoanPl2 cTietBcao = modelMapper.map(qlnvCTiet,
									QlnvKhvonphiBcaoThinhSdungDtoanPl2.class);
							cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							listUpdates.add(cTietBcao);
						} else {
							// update
							QlnvKhvonphiBcaoThinhSdungDtoanPl2 bcaoThinhSdungDtoanPl2 = optional.get();
							bcaoThinhSdungDtoanPl2.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							updateObjectToObject(bcaoThinhSdungDtoanPl2, bcaoThinhSdungDtoanPl2Update);
							listUpdates.add(bcaoThinhSdungDtoanPl2);
						}
					} else {
						// thêm mới
						QlnvKhvonphiBcaoThinhSdungDtoanPl2 cTietBcao = modelMapper.map(qlnvCTiet,
								QlnvKhvonphiBcaoThinhSdungDtoanPl2.class);
						cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
						listUpdates.add(cTietBcao);

					}
				}
				qlnvKhvonphiBcaoThinhSdungDtoanPl2Repository.saveAll(listUpdates);

			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3)) {
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl3> listUpdates = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoThinhSdungDtoanPl3 bcaoThinhSdungDtoanPl3Update = modelMapper.map(qlnvCTiet,
							QlnvKhvonphiBcaoThinhSdungDtoanPl3.class);
					// Check id tồn tại thì update
					if (bcaoThinhSdungDtoanPl3Update.getId() != null) {
						Optional<QlnvKhvonphiBcaoThinhSdungDtoanPl3> optional = qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository
								.findById(bcaoThinhSdungDtoanPl3Update.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiBcaoThinhSdungDtoanPl3 cTietBcao = modelMapper.map(qlnvCTiet,
									QlnvKhvonphiBcaoThinhSdungDtoanPl3.class);
							cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							listUpdates.add(cTietBcao);
						} else {
							// update
							QlnvKhvonphiBcaoThinhSdungDtoanPl3 bcaoThinhSdungDtoanPl3 = optional.get();
							bcaoThinhSdungDtoanPl3.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							updateObjectToObject(bcaoThinhSdungDtoanPl3, bcaoThinhSdungDtoanPl3Update);
							listUpdates.add(bcaoThinhSdungDtoanPl3);
						}
					} else {
						// thêm mới
						QlnvKhvonphiBcaoThinhSdungDtoanPl3 cTietBcao = modelMapper.map(qlnvCTiet,
								QlnvKhvonphiBcaoThinhSdungDtoanPl3.class);
						cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
						listUpdates.add(cTietBcao);

					}
				}
				qlnvKhvonphiBcaoThinhSdungDtoanPl3Repository.saveAll(listUpdates);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG)) {//90
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiBcaoKquaThienNhapMuaHangRepository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiBcaoKquaThienNhapMuaHang> listUpdates = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoKquaThienNhapMuaHang bcaoKquaThienNhapMuaHangUpdate = modelMapper.map(qlnvCTiet,
							QlnvKhvonphiBcaoKquaThienNhapMuaHang.class);
					// Check id tồn tại thì update
					if (bcaoKquaThienNhapMuaHangUpdate.getId() != null) {
						Optional<QlnvKhvonphiBcaoKquaThienNhapMuaHang> optional = qlnvKhvonphiBcaoKquaThienNhapMuaHangRepository
								.findById(bcaoKquaThienNhapMuaHangUpdate.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiBcaoKquaThienNhapMuaHang cTietBcao = modelMapper.map(qlnvCTiet,
									QlnvKhvonphiBcaoKquaThienNhapMuaHang.class);
							cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							listUpdates.add(cTietBcao);
						} else {
							// update
							QlnvKhvonphiBcaoKquaThienNhapMuaHang bcaoKquaThienNhapMuaHang = optional.get();
							bcaoKquaThienNhapMuaHang.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							updateObjectToObject(bcaoKquaThienNhapMuaHang, bcaoKquaThienNhapMuaHangUpdate);
							listUpdates.add(bcaoKquaThienNhapMuaHang);
						}
					} else {
						// thêm mới
						QlnvKhvonphiBcaoKquaThienNhapMuaHang cTietBcao = modelMapper.map(qlnvCTiet,
								QlnvKhvonphiBcaoKquaThienNhapMuaHang.class);
						cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
						listUpdates.add(cTietBcao);
					}
				}
				qlnvKhvonphiBcaoKquaThienNhapMuaHangRepository.saveAll(listUpdates);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG)) {//91
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiBcaoKquaThienXuatHangRepository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> listUpdates = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoKquaThienXuatHang bcaoKquaThienXuatHanUpdate = modelMapper.map(qlnvCTiet,
							QlnvKhvonphiBcaoKquaThienXuatHang.class);
					// Check id tồn tại thì update
					if (bcaoKquaThienXuatHanUpdate.getId() != null) {
						Optional<QlnvKhvonphiBcaoKquaThienXuatHang> optional = qlnvKhvonphiBcaoKquaThienXuatHangRepository
								.findById(bcaoKquaThienXuatHanUpdate.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiBcaoKquaThienXuatHang cTietBcao = modelMapper.map(qlnvCTiet,
									QlnvKhvonphiBcaoKquaThienXuatHang.class);
							cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							listUpdates.add(cTietBcao);
						} else {
							// update
							QlnvKhvonphiBcaoKquaThienXuatHang bcaoKquaThienXuatHang = optional.get();
							bcaoKquaThienXuatHang.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							updateObjectToObject(bcaoKquaThienXuatHang, bcaoKquaThienXuatHanUpdate);
							listUpdates.add(bcaoKquaThienXuatHang);
						}
					} else {
						// thêm mới
						QlnvKhvonphiBcaoKquaThienXuatHang cTietBcao = modelMapper.map(qlnvCTiet,
								QlnvKhvonphiBcaoKquaThienXuatHang.class);
						cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
						listUpdates.add(cTietBcao);
					}
				}
				qlnvKhvonphiBcaoKquaThienXuatHangRepository.saveAll(listUpdates);

			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_NHAP_MUA)
					|| optionalBC.get().getMaLoaiBcao()
							.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_XUAT_HANG)
					|| optionalBC.get().getMaLoaiBcao()
							.equals(Constants.QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_BQUAN_LAN_DAU)) {// 92,93,94

				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiBcaoKquaCtietThienRepository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiBcaoKquaCtietThien> listUpdates = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiBcaoKquaCtietThien bcaoKquaCtietThienUpdate = modelMapper.map(qlnvCTiet,
							QlnvKhvonphiBcaoKquaCtietThien.class);
					// Check id tồn tại thì update
					if (bcaoKquaCtietThienUpdate.getId() != null) {
						Optional<QlnvKhvonphiBcaoKquaCtietThien> optional = qlnvKhvonphiBcaoKquaCtietThienRepository
								.findById(bcaoKquaCtietThienUpdate.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiBcaoKquaCtietThien cTietBcao = modelMapper.map(qlnvCTiet,
									QlnvKhvonphiBcaoKquaCtietThien.class);
							cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							listUpdates.add(cTietBcao);
						} else {
							// update
							QlnvKhvonphiBcaoKquaCtietThien bcaoKquaCtietThien = optional.get();
							bcaoKquaCtietThien.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
							updateObjectToObject(bcaoKquaCtietThien, bcaoKquaCtietThienUpdate);
							listUpdates.add(bcaoKquaCtietThien);
						}
					} else {
						// thêm mới
						QlnvKhvonphiBcaoKquaCtietThien cTietBcao = modelMapper.map(qlnvCTiet,
								QlnvKhvonphiBcaoKquaCtietThien.class);
						cTietBcao.setQlnvKhvonphiBcaoId(khvonphiBcao.getId());
						listUpdates.add(cTietBcao);

					}
				}
				qlnvKhvonphiBcaoKquaCtietThienRepository.saveAll(listUpdates);
			}
			khvonphiBcao = qlnvKhvonphiBcaoRepository.save(khvonphiBcao);
//			resp.setData(khvonphiBcao);
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
	public Resp validate(QlnvKhvonphiBcaoReq qlnvBaoCaoReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resp genMaBcao() {
		Resp resp = new Resp();
		try {
			int index = qlnvKhvonphiBcaoRepository.getMaBaoCao();
			String maBaoCao = "BCGNNS".concat(String.valueOf(index));
			resp.setData(maBaoCao);
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

			Optional<QlnvKhvonphiBcao> optionalBC = qlnvKhvonphiBcaoRepository.findById(objReq.getId());
			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("ID Báo cáo không tồn tại!");
			}
			QlnvKhvonphiBcao qlnvBaoCao = optionalBC.get();
			QlnvDmDonvi qlnvDmDonvi = qlnvDmService.getDonViById(String.valueOf(qlnvBaoCao.getMaDvi()));
			// kiểm tra quyền thao tác
			qlnvBaoCao.setTrangThai(
					Utils.function(qlnvBaoCao.getTrangThai(), qlnvDmDonvi, userInfo, objReq.getMaChucNang()));
			// update trạng thái báo cáo
			qlnvKhvonphiBcaoRepository.save(qlnvBaoCao);
			resp.setData(qlnvBaoCao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return resp;
	}

	public <T> void updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(Constants.FORMAT_DATE_STR));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.updateValue(source, objectEdit);
	}


}






