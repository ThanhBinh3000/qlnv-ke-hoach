package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoachphi.entities.KeHoachLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KeHoachVatTu;
import com.tcdt.qlnvkhoachphi.entities.KeHoachXuatLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KtTrangthaiHienthoi;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoachphi.query.dto.VatTuNhapQueryDTO;
import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachVatTuRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachXuatLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KtTrangthaiHienthoiRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoachphi.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.KeHoachLuongThucDuTruReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.KeHoachMuoiDuTruReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.KeHoachNhapVatTuThietBiReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.VatTuNhapReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.VatTuThietBiReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.chitieukehoachnam.SoLuongTruocDieuChinhSearchReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.QdDcChiTieuKeHoachRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.SoLuongTruocDieuChinhRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.TonKhoDauNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoachphi.util.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChiTieuKeHoachNamServiceImpl implements ChiTieuKeHoachNamService {

	private static final Integer SO_NAM_CU = 3;
	@Autowired
	private ChiTieuKeHoachNamRepository chiTieuKeHoachNamRepository;

	@Autowired
	private KeHoachLuongThucMuoiRepository keHoachLuongThucMuoiRepository;

	@Autowired
	private KeHoachXuatLuongThucMuoiRepository keHoachXuatLuongThucMuoiRepository;

	@Autowired
	private KeHoachVatTuRepository keHoachVatTuRepository;

	@Autowired
	private QlnvDmDonviRepository qlnvDmDonviRepository;

	@Autowired
	private QlnvDmVattuRepository qlnvDmVattuRepository;

	@Autowired
	private KtTrangthaiHienthoiRepository ktTrangthaiHienthoiRepository;

	public static final Long THOC_ID = 2L;
	public static final String THOC_MA_VT = "0101";
	public static final Long GAO_ID = 6L;
	public static final String GAO_MA_VT = "0102";
	public static final Long MUOI_ID = 78L;
	public static final String MUOI_MA_VT = "04";
	private static final Integer MAX_CAP_VAT_TU = 3;

	private static final List<Long> THOC_GAO_MUOI_IDS = Arrays.asList(THOC_ID, GAO_ID, MUOI_ID);
	// TODO: FIXME: Fix cứng id đơn vị để dùng tạm, sẽ update sử dũng mã đơn vị thay vì id đơn vị
	private static final Long DON_VI_ID_1 = 1L;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception {
		ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamRepository.findByNamKeHoachAndLastest(req.getNamKeHoach(), true);
		if (chiTieuKeHoachNam != null && !ChiTieuKeHoachNamStatus.TU_CHOI.getId().equalsIgnoreCase(chiTieuKeHoachNam.getTrangThai()))
			throw new Exception("Chỉ tiêu kế hoạch năm đã tồn tại");

		this.validateCreateCtkhnRequest(req);
		return this.create(req, ChiTieuKeHoachEnum.QD.getValue(), null);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public QdDcChiTieuKeHoachRes createQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {
		Long qdGocId = req.getQdGocId();
		Optional<ChiTieuKeHoachNam> optionalQdGoc = chiTieuKeHoachNamRepository.findById(qdGocId);
		if (!optionalQdGoc.isPresent())
			throw new Exception("Không tìm thấy quyết định gốc.");

		ChiTieuKeHoachNam qdGoc = optionalQdGoc.get();
		if (!ChiTieuKeHoachNamStatus.DA_DUYET.getId().equals(qdGoc.getTrangThai())) {
			throw new Exception("Không thể điều chỉnh quyết định chưa duyệt");
		}
		qdGoc.setLastest(false);
		chiTieuKeHoachNamRepository.save(qdGoc);

		ChiTieuKeHoachNamRes qdDc = this.create(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue(), qdGoc.getId());
		QdDcChiTieuKeHoachRes response = new QdDcChiTieuKeHoachRes();
		response.setQdDc(qdDc);
		response.setQd(this.detailQd(qdGocId));
		return response;
	}

	private ChiTieuKeHoachNamRes create(ChiTieuKeHoachNamReq req, String loaiQd, Long qdGocId) throws Exception {
		if (req == null)
			return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		ChiTieuKeHoachNam chiTieuKeHoachNam = new ChiTieuKeHoachNam();
		BeanUtils.copyProperties(req, chiTieuKeHoachNam, "id");
		chiTieuKeHoachNam.setNgayTao(LocalDate.now());
		chiTieuKeHoachNam.setNguoiTaoId(userInfo.getId());
		chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.MOI_TAO.getId());
		chiTieuKeHoachNam.setDonViId(DON_VI_ID_1);
		chiTieuKeHoachNam.setLoaiQuyetDinh(loaiQd);
		chiTieuKeHoachNam.setLastest(true);
		chiTieuKeHoachNam.setQdGocId(qdGocId);
		chiTieuKeHoachNamRepository.save(chiTieuKeHoachNam);
		Long ctkhnId = chiTieuKeHoachNam.getId();

		List<KeHoachLuongThucMuoi> keHoachThocGaoList = new ArrayList<>();
		List<KeHoachLuongThucMuoi> keHoachMuoiList = new ArrayList<>();
		List<KeHoachVatTu> keHoachVatTuList = new ArrayList<>();
		for (KeHoachLuongThucDuTruReq khltReq : req.getKhLuongThuc()) {
			KeHoachLuongThucMuoi khGao = this.saveKeHoachLuongThuc(khltReq, userInfo, ctkhnId, GAO_ID, khltReq.getNtnGao(), null, new HashMap<>());
			List<KeHoachXuatLuongThucMuoi> khXuatGaoList = this.saveListKeHoachXuat(khGao.getId(), khltReq.getXtnGao(), new HashMap<>());
			khGao.setKhxltms(khXuatGaoList);

			KeHoachLuongThucMuoi khThoc = this.saveKeHoachLuongThuc(khltReq, userInfo, ctkhnId, THOC_ID, khltReq.getNtnThoc(), null, new HashMap<>());
			List<KeHoachXuatLuongThucMuoi> khXuatThocList = this.saveListKeHoachXuat(khThoc.getId(), khltReq.getXtnThoc(), new HashMap<>());
			khThoc.setKhxltms(khXuatThocList);

			keHoachThocGaoList.add(khGao);
			keHoachThocGaoList.add(khThoc);
		}

		for (KeHoachMuoiDuTruReq khMuoiReq : req.getKhMuoi()) {
			KeHoachLuongThucMuoi khMuoi = this.saveKeHoachMuoi(khMuoiReq, userInfo, ctkhnId, new HashMap<>());
			List<KeHoachXuatLuongThucMuoi> khXuatMuoiList = this.saveListKeHoachXuat(khMuoi.getId(), khMuoiReq.getXuatTrongNam(), new HashMap<>());
			khMuoi.setKhxltms(khXuatMuoiList);
			keHoachMuoiList.add(khMuoi);
		}

		for (KeHoachNhapVatTuThietBiReq khvtReq : req.getKhVatTu()) {
			keHoachVatTuList.addAll(this.saveListKeHoachVatTu(khvtReq, userInfo, ctkhnId, new HashMap<>()));
		}

		chiTieuKeHoachNam.setKhLuongThucList(keHoachThocGaoList);
		chiTieuKeHoachNam.setKhMuoiList(keHoachMuoiList);
		chiTieuKeHoachNam.setKhVatTuList(keHoachVatTuList);
		return this.buildDetailResponse(chiTieuKeHoachNam);
	}

	private List<VatTuNhapQueryDTO> getKeHoachVatTuThietBiCacNamTruoc(List<Long> vatTuIdList, Integer nameKeHoach) {
		int tuNam = nameKeHoach - 3;
		int denNam = nameKeHoach - 1;
		List<VatTuNhapQueryDTO> results = keHoachVatTuRepository.findKeHoachVatTuCacNamTruocByVatTuId(vatTuIdList, nameKeHoach - 3, nameKeHoach - 1);
		Map<String, VatTuNhapQueryDTO> map = results.stream().collect(Collectors.toMap(VatTuNhapQueryDTO::groupByNamAndVatTuId, Function.identity()));
		for (Long vatuId : vatTuIdList) {
			for (int i = tuNam; i <= denNam; i++) {
				VatTuNhapQueryDTO dto = map.get(String.format("%s_%s", String.valueOf(i), vatuId.toString()));
				if (dto == null) {
					dto = new VatTuNhapQueryDTO();
					dto.setNam(i);
					dto.setVatTuId(vatuId);
					dto.setSoLuong(0d);
					results.add(dto);
				}
			}
		}
		return results;
	}

	private List<TonKhoDauNamRes> getTonKhoDauNam(List<String> maDonViList, List<String> maVatTuList, Integer namKeHoach, List<QlnvDmDonvi> list) throws Exception {
		List<String> namList = new ArrayList<>();
		for (int i = 1; i <= SO_NAM_CU; i++) {
			namList.add(String.valueOf(namKeHoach - i));
		}
		List<KtTrangthaiHienthoi> trangthaiHienthoiList = ktTrangthaiHienthoiRepository.findAllByMaDonViInAndMaVthhInAndNamIn(maDonViList, maVatTuList, namList);

		List<TonKhoDauNamRes> tonKhoDauNamResList = new ArrayList<>();
		for (KtTrangthaiHienthoi trangthaiHienthoi : trangthaiHienthoiList) {
			QlnvDmDonvi donvi = list.stream().filter(d -> d.getMaDvi().equalsIgnoreCase(trangthaiHienthoi.getMaDonVi())).findFirst().orElse(null);
			if (donvi == null)
				throw new Exception("Đơn vị không tồn tại.");

			TonKhoDauNamRes res = tonKhoDauNamResList.stream().filter(t -> trangthaiHienthoi.getMaDonVi().equals(t.getMaDonVi()) && trangthaiHienthoi.getMaVthh().equals(t.getMaVatTu())).findFirst().orElse(null);
			if (res == null) {
				res = new TonKhoDauNamRes();
				res.setDonViId(donvi.getId());
				res.setMaDonVi(trangthaiHienthoi.getMaDonVi());
				res.setTenDonVi(trangthaiHienthoi.getTenDonVi());
				res.setMaVatTu(trangthaiHienthoi.getMaVthh());
				tonKhoDauNamResList.add(res);
			}

			VatTuNhapRes vatTuNhapRes = new VatTuNhapRes();
			vatTuNhapRes.setNam(Integer.valueOf(trangthaiHienthoi.getNam()));
			vatTuNhapRes.setSoLuong(trangthaiHienthoi.getSlHienThoi());
			res.getTonKho().add(vatTuNhapRes);
		}

		return tonKhoDauNamResList;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes updateQd(ChiTieuKeHoachNamReq req) throws Exception {
		this.validateCreateCtkhnRequest(req);
		return this.update(req);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public QdDcChiTieuKeHoachRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {

		ChiTieuKeHoachNamRes qdDc = this.update(req.getQdDc());
		QdDcChiTieuKeHoachRes response = new QdDcChiTieuKeHoachRes();
		response.setQdDc(qdDc);
		response.setQd(this.detailQd(qdDc.getQdGocId()));
		return response;
	}

	public ChiTieuKeHoachNamRes update(ChiTieuKeHoachNamReq req) throws Exception {
		if (req == null)
			return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<ChiTieuKeHoachNam> optional = chiTieuKeHoachNamRepository.findById(req.getId());
		if (!optional.isPresent())
			throw new Exception("Chỉ tiêu kế hoạch năm không tồn tại");

		ChiTieuKeHoachNam ctkhn = optional.get();
		Long ctkhnId = ctkhn.getId();

		ctkhn.setSoQuyetDinh(req.getSoQuyetDinh());
		ctkhn.setNgayHieuLuc(req.getNgayHieuLuc());
		ctkhn.setNgayKy(req.getNgayKy());
		ctkhn.setNamKeHoach(req.getNamKeHoach());
		ctkhn.setTrichYeu(req.getTrichYeu());
		ctkhn.setGhiChu(req.getGhiChu());
		ctkhn.setNgaySua(LocalDate.now());
		ctkhn.setNguoiSuaId(userInfo.getId());
		chiTieuKeHoachNamRepository.save(ctkhn);

		List<KeHoachLuongThucMuoi> keHoachLuongThucMuois = keHoachLuongThucMuoiRepository.findByCtkhnId(ctkhn.getId());

		// Key: khId, value : KeHoachLuongThucMuoi
		Map<Long, KeHoachLuongThucMuoi> mapKhltm = keHoachLuongThucMuois
				.stream().collect(Collectors.toMap(KeHoachLuongThucMuoi::getId, Function.identity()));

		// Key: khxId, value : KeHoachXuatLuongThucMuoi
		Map<Long, KeHoachXuatLuongThucMuoi> mapKhxltm = new HashMap<>();
		Set<Long> khLthmIds = keHoachLuongThucMuois.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(khLthmIds)) {
			List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuois = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdIn(khLthmIds);
			mapKhxltm = keHoachXuatLuongThucMuois.stream().collect(Collectors.toMap(KeHoachXuatLuongThucMuoi::getId, Function.identity()));
		}

		for (KeHoachLuongThucDuTruReq khltReq : req.getKhLuongThuc()) {
			Long gaoId = khltReq.getKhGaoId();
			KeHoachLuongThucMuoi khGao = this.saveKeHoachLuongThuc(khltReq, userInfo, ctkhnId, GAO_ID, khltReq.getNtnGao(), gaoId, mapKhltm);
			this.saveListKeHoachXuat(khGao.getId(), khltReq.getXtnGao(), mapKhxltm);

			Long thocId = khltReq.getKhThocId();
			KeHoachLuongThucMuoi khThoc = this.saveKeHoachLuongThuc(khltReq, userInfo, ctkhnId, THOC_ID, khltReq.getNtnThoc(), thocId, mapKhltm);
			this.saveListKeHoachXuat(khThoc.getId(), khltReq.getXtnThoc(), mapKhxltm);
		}

		for (KeHoachMuoiDuTruReq khMuoiReq : req.getKhMuoi()) {
			KeHoachLuongThucMuoi khMuoi = this.saveKeHoachMuoi(khMuoiReq, userInfo, ctkhnId, mapKhltm);
			this.saveListKeHoachXuat(khMuoi.getId(), khMuoiReq.getXuatTrongNam(), mapKhxltm);
		}

		List<KeHoachVatTu> keHoachVatTus = keHoachVatTuRepository.findByCtkhnId(ctkhn.getId());
		// Key: khId, value : KeHoachVatTu
		Map<Long, KeHoachVatTu> mapKhvt = keHoachVatTus
				.stream().collect(Collectors.toMap(KeHoachVatTu::getId, Function.identity()));

		for (KeHoachNhapVatTuThietBiReq khvtReq : req.getKhVatTu()) {
			this.saveListKeHoachVatTu(khvtReq, userInfo, ctkhnId, mapKhvt);
		}

		if (!CollectionUtils.isEmpty(mapKhxltm.values()))
			keHoachXuatLuongThucMuoiRepository.deleteAll(mapKhxltm.values());

		if (!CollectionUtils.isEmpty(mapKhltm.values()))
			keHoachLuongThucMuoiRepository.deleteAll(mapKhltm.values());

		if (!CollectionUtils.isEmpty(mapKhvt.values()))
			keHoachVatTuRepository.deleteAll(mapKhvt.values());

		return this.buildDetailResponse(ctkhn);
	}

	private KeHoachLuongThucMuoi saveKeHoachLuongThuc(KeHoachLuongThucDuTruReq khltReq, UserInfo userInfo, Long ctkhnId,
													  Long vatuId, Double ntnSoLuong, Long id, Map<Long, KeHoachLuongThucMuoi> mapKhltm) throws Exception {

		KeHoachLuongThucMuoi keHoachLuongThucMuoi = new KeHoachLuongThucMuoi();
		keHoachLuongThucMuoi.setTrangThai(Constants.MOI_TAO);
		if (id != null) {
			keHoachLuongThucMuoi = mapKhltm.get(id);
			if (keHoachLuongThucMuoi == null)
				throw new Exception("Kế hoạch lương thực không tồn tại.");
		}

		keHoachLuongThucMuoi.setStt(khltReq.getStt());
		keHoachLuongThucMuoi.setCtkhnId(ctkhnId);
		keHoachLuongThucMuoi.setDonViId(khltReq.getDonViId());
		keHoachLuongThucMuoi.setVatTuId(vatuId);
		Double soLuongNhap = ntnSoLuong == null ? 0 : ntnSoLuong;
		keHoachLuongThucMuoi.setSoLuongNhap(soLuongNhap);
		keHoachLuongThucMuoi.setDonViTinh(khltReq.getDonViTinh());
		mapKhltm.remove(id);
		return keHoachLuongThucMuoiRepository.save(keHoachLuongThucMuoi);
	}

	private List<KeHoachXuatLuongThucMuoi> saveListKeHoachXuat(Long keHoachId,
															   List<VatTuNhapReq> vatTuNhapReqList,
															   Map<Long, KeHoachXuatLuongThucMuoi> mapKhxltm) throws Exception {
		List<KeHoachXuatLuongThucMuoi> keHoachXuatList = new ArrayList<>();

		for (VatTuNhapReq vatTuNhapReq : vatTuNhapReqList) {
			Long id = vatTuNhapReq.getId();
			KeHoachXuatLuongThucMuoi khxltm = new KeHoachXuatLuongThucMuoi();

			if (id != null) {
				khxltm = mapKhxltm.get(id);
				if (khxltm == null)
					throw new Exception("Kế hoạch xuất lương thực muối không tồn tại.");
				mapKhxltm.remove(id);
			}

			khxltm.setKeHoachId(keHoachId);
			Double soLuongXuat = vatTuNhapReq.getSoLuong() == null ? 0 : vatTuNhapReq.getSoLuong();
			khxltm.setSoLuongXuat(soLuongXuat);
			khxltm.setNamKeHoach(vatTuNhapReq.getNam());
			keHoachXuatList.add(khxltm);
		}

		if (!CollectionUtils.isEmpty(keHoachXuatList))
			keHoachXuatLuongThucMuoiRepository.saveAll(keHoachXuatList);

		return keHoachXuatList;
	}

	private KeHoachLuongThucMuoi saveKeHoachMuoi(KeHoachMuoiDuTruReq khMuoiReq, UserInfo userInfo, Long ctkhnId,
												 Map<Long, KeHoachLuongThucMuoi> mapKhltm) throws Exception {

		KeHoachLuongThucMuoi keHoachLuongThucMuoi = new KeHoachLuongThucMuoi();
		keHoachLuongThucMuoi.setTrangThai(Constants.MOI_TAO);
		Long id = khMuoiReq.getId();
		if (id != null) {
			keHoachLuongThucMuoi = mapKhltm.get(id);
			if (keHoachLuongThucMuoi == null)
				throw new Exception("Kế hoạch muối không tồn tại.");

			mapKhltm.remove(id);
		}

		keHoachLuongThucMuoi.setStt(khMuoiReq.getStt());
		keHoachLuongThucMuoi.setCtkhnId(ctkhnId);
		keHoachLuongThucMuoi.setDonViId(khMuoiReq.getDonViId());
		keHoachLuongThucMuoi.setVatTuId(MUOI_ID);
		Double soLuongNhap = khMuoiReq.getNhapTrongNam() == null ? 0 : khMuoiReq.getNhapTrongNam();
		keHoachLuongThucMuoi.setSoLuongNhap(soLuongNhap);
		keHoachLuongThucMuoi.setDonViTinh(khMuoiReq.getDonViTinh());

		keHoachLuongThucMuoiRepository.save(keHoachLuongThucMuoi);
		return keHoachLuongThucMuoi;
	}

	private List<KeHoachVatTu> saveListKeHoachVatTu(KeHoachNhapVatTuThietBiReq khVatTuReq, UserInfo userInfo, Long ctkhnId,
													Map<Long, KeHoachVatTu> mapKhvt) throws Exception {

		List<KeHoachVatTu> keHoachVatTuList = new ArrayList<>();
		Set<Long> removeIds = new HashSet<>();
		for (VatTuThietBiReq vatTuReq : khVatTuReq.getVatTuThietBi()) {
			KeHoachVatTu keHoachVatTu = new KeHoachVatTu();
			keHoachVatTu.setTrangThai(Constants.MOI_TAO);
			Long id = khVatTuReq.getId();
			if (id != null) {
				keHoachVatTu = mapKhvt.get(id);
				if (keHoachVatTu == null)
					throw new Exception("Kế hoạch nhập vật tư thiết bị không tồn tại.");

				removeIds.add(id);
			}

			keHoachVatTu.setSttDonVi(khVatTuReq.getStt());
			keHoachVatTu.setCtkhnId(ctkhnId);
			keHoachVatTu.setDonViId(khVatTuReq.getDonViId());

			keHoachVatTu.setSttVatTu(vatTuReq.getStt());
			keHoachVatTu.setDonViTinh(vatTuReq.getDonViTinh());
			keHoachVatTu.setSoLuongNhap(vatTuReq.getNhapTrongNam());
			keHoachVatTu.setVatTuId(vatTuReq.getVatTuId());
			keHoachVatTu.setVatTuChaId(vatTuReq.getVatTuChaId());
			keHoachVatTuRepository.save(keHoachVatTu);
			keHoachVatTuList.add(keHoachVatTu);
		}

		removeIds.forEach(mapKhvt::remove);
		return keHoachVatTuList;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean deleteQd(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		ChiTieuKeHoachNam item = chiTieuKeHoachNamRepository.findByIdAndLoaiQuyetDinh(id, ChiTieuKeHoachEnum.QD.getValue());
		if (item == null)
			throw new Exception("Không tìm thấy dữ liệu.");

		if (ChiTieuKeHoachNamStatus.DA_DUYET.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định đã duyệt");
		}

		return this.delete(item);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean deleteQdDc(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		ChiTieuKeHoachNam item = chiTieuKeHoachNamRepository.findByIdAndLoaiQuyetDinh(id, ChiTieuKeHoachEnum.QD_DC.getValue());
		if (item == null)
			throw new Exception("Không tìm thấy dữ liệu.");

		if (ChiTieuKeHoachNamStatus.DA_DUYET.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định điều chỉnh đã duyệt");
		}

		return this.delete(item);
	}

	public boolean delete(ChiTieuKeHoachNam chiTieuKeHoachNam) throws Exception {
		List<KeHoachLuongThucMuoi> keHoachLuongThucMuoiList = keHoachLuongThucMuoiRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuoiList = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdIn(keHoachLuongThucMuoiList.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toList()));
		keHoachXuatLuongThucMuoiRepository.deleteAll(keHoachXuatLuongThucMuoiList);
		keHoachLuongThucMuoiRepository.deleteAll(keHoachLuongThucMuoiList);
		List<KeHoachVatTu> keHoachVatTuList = keHoachVatTuRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		keHoachVatTuRepository.deleteAll(keHoachVatTuList);
		chiTieuKeHoachNamRepository.delete(chiTieuKeHoachNam);
		return true;
	}

	@Override
	public ChiTieuKeHoachNamRes detailQd(Long id) throws Exception {
		return this.detail(id);
	}

	@Override
	public QdDcChiTieuKeHoachRes detailQdDc(Long id) throws Exception {
		ChiTieuKeHoachNamRes qdDc = this.detail(id);
		ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamRepository.findByNamKeHoachAndLastest(qdDc.getNamKeHoach(), true);
		ChiTieuKeHoachNamRes qd = this.detail(chiTieuKeHoachNam.getId());

		QdDcChiTieuKeHoachRes response = new QdDcChiTieuKeHoachRes();
		response.setQdDc(qdDc);
		response.setQd(qd);
		return response;
	}

	public ChiTieuKeHoachNamRes detail(Long id) throws Exception {
		Optional<ChiTieuKeHoachNam> optional = chiTieuKeHoachNamRepository.findById(id);
		if (!optional.isPresent())
			throw new Exception("Chỉ tiêu kế hoạch năm không tồn tại");

		ChiTieuKeHoachNam ctkhn = optional.get();

		List<KeHoachLuongThucMuoi> keHoachLuongThucMuois = keHoachLuongThucMuoiRepository.findByCtkhnId(ctkhn.getId());
		List<KeHoachVatTu> keHoachVatTus = keHoachVatTuRepository.findByCtkhnId(ctkhn.getId());

		// Key: khId, value : List<KeHoachXuatLuongThucMuoi>
		Map<Long, List<KeHoachXuatLuongThucMuoi>> mapKhxltm = new HashMap<>();
		Set<Long> khLthmIds = keHoachLuongThucMuois.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(khLthmIds)) {
			List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuois = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdIn(khLthmIds);
			mapKhxltm = keHoachXuatLuongThucMuois.stream().collect(Collectors.groupingBy(KeHoachXuatLuongThucMuoi::getKeHoachId));
		}

		for (KeHoachLuongThucMuoi keHoachLuongThucMuoi : keHoachLuongThucMuois) {
			keHoachLuongThucMuoi.setKhxltms(mapKhxltm.get(keHoachLuongThucMuoi.getId()));
		}

		ctkhn.setKhLuongThucList(keHoachLuongThucMuois.stream().filter(kh -> GAO_ID.equals(kh.getVatTuId()) || THOC_ID.equals(kh.getVatTuId())).collect(Collectors.toList()));
		ctkhn.setKhMuoiList(keHoachLuongThucMuois.stream().filter(kh -> MUOI_ID.equals(kh.getVatTuId())).collect(Collectors.toList()));
		ctkhn.setKhVatTuList(keHoachVatTus);
		ChiTieuKeHoachNamRes response = buildDetailResponse(ctkhn);

		return response;
	}

	private void addEmptyVatTuNhap(List<Integer> namCoData, Integer namKeHoach, List<VatTuNhapRes> vatTuNhapRes, Integer soNamLuuVatTu) {
		List<String> namList = new ArrayList<>();

		for (int i = 1; i <= soNamLuuVatTu; i++) {
			namList.add(String.valueOf(namKeHoach - i));
		}


		for (String namStr : namList) {
			Integer nam = Integer.parseInt(namStr);
			if (namCoData.contains(nam)) continue;
			vatTuNhapRes.add(VatTuNhapRes.builder()
					.nam(nam)
					.soLuong(0D)
					.build());
		}
	}

	private void addEmptyDataToExport(ChiTieuKeHoachNamRes chiTieuKeHoachNamRes) {
		//Luong thuc
		for (KeHoachLuongThucDuTruRes keHoachLuongThucDuTruRes : chiTieuKeHoachNamRes.getKhLuongThuc()) {
			//Tồn kho đầu năm
			List<Integer> namTkdnThoc = keHoachLuongThucDuTruRes.getTkdnThoc()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(namTkdnThoc, chiTieuKeHoachNamRes.getNamKeHoach(),
					keHoachLuongThucDuTruRes.getTkdnThoc(), Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_THOC);


			List<Integer> namTkdnGao = keHoachLuongThucDuTruRes.getTkdnGao()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(namTkdnGao, chiTieuKeHoachNamRes.getNamKeHoach(),
					keHoachLuongThucDuTruRes.getTkdnGao(), Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_GAO);


			//Xuất trong năm
			List<Integer> namXtnThoc = keHoachLuongThucDuTruRes.getXtnThoc()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(namXtnThoc, chiTieuKeHoachNamRes.getNamKeHoach(), keHoachLuongThucDuTruRes.getXtnThoc(),
					Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_THOC);


			List<Integer> namXtnGao = keHoachLuongThucDuTruRes.getXtnGao()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(namXtnGao, chiTieuKeHoachNamRes.getNamKeHoach(),
					keHoachLuongThucDuTruRes.getXtnGao(), Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_GAO);
		}

		//Muối
		for (KeHoachMuoiDuTruRes keHoachMuoiDuTruRes : chiTieuKeHoachNamRes.getKhMuoiDuTru()) {
			//Tồn kho đầu năm
			List<Integer> tkdnMuoi = keHoachMuoiDuTruRes.getTkdnMuoi()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(tkdnMuoi, chiTieuKeHoachNamRes.getNamKeHoach(), keHoachMuoiDuTruRes.getTkdnMuoi(),
					Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_MUOI);

			//Tồn kho đầu năm
			List<Integer> xtnMuoi = keHoachMuoiDuTruRes.getXtnMuoi()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(xtnMuoi, chiTieuKeHoachNamRes.getNamKeHoach(), keHoachMuoiDuTruRes.getXtnMuoi(),
					Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_MUOI);
		}

//		//Vat tu
//		for (KeHoachVatTuRes keHoachVatTuRes : chiTieuKeHoachNamRes.getKhVatTu()) {
//			for (NhomVatTuThietBiRes nhomVatTuThietBi : keHoachVatTuRes.getNhomVatTuThietBi()) {
//				for (VatTuThietBiRes vatTuThietBiRes : nhomVatTuThietBi.getVatTuThietBi()) {
//					List<Integer> cacNamTruoc = vatTuThietBiRes.getCacNamTruoc()
//							.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());
//					this.addEmptyVatTuNhap(cacNamTruoc, chiTieuKeHoachNamRes.getNamKeHoach(), vatTuThietBiRes.getCacNamTruoc(),
//							Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_VAT_TU);
//				}
//			}
//
//		}
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean updateStatusQd(StatusReq req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");
		Optional<ChiTieuKeHoachNam> optionalChiTieuKeHoachNam = chiTieuKeHoachNamRepository.findById(req.getId());
		if (!optionalChiTieuKeHoachNam.isPresent())
			throw new Exception("Không tìm thấy dữ liệu.");

		ChiTieuKeHoachNam chiTieuKeHoachNam = optionalChiTieuKeHoachNam.get();
		return this.updateStatus(req, chiTieuKeHoachNam, userInfo);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean updateStatusQdDc(StatusReq req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<ChiTieuKeHoachNam> optionalChiTieuKeHoachNam = chiTieuKeHoachNamRepository.findById(req.getId());
		if (!optionalChiTieuKeHoachNam.isPresent())
			throw new Exception("Không tìm thấy dữ liệu.");

		ChiTieuKeHoachNam dc = optionalChiTieuKeHoachNam.get();
		this.updateStatus(req, dc, userInfo);
		if (!ChiTieuKeHoachNamStatus.DA_DUYET.getId().equalsIgnoreCase(dc.getTrangThai()))
			return true;

		Optional<ChiTieuKeHoachNam> qdGocOptional = chiTieuKeHoachNamRepository.findById(dc.getQdGocId());
		if (!qdGocOptional.isPresent())
			throw new Exception("Không tìm thấy dữ liệu.");

		ChiTieuKeHoachNam qdGoc = optionalChiTieuKeHoachNam.get();

		// Duyệt điều chỉnh -> tạo quyết đinh mới từ quyết định gốc
		this.mergeQdDcAndQd(dc, qdGoc);
		return true;
	}

	private void mergeQdDcAndQd(ChiTieuKeHoachNam dc, ChiTieuKeHoachNam qdGoc) {
		// QdDc
		List<KeHoachLuongThucMuoi> khltmListDc = this.retrieveKhltm(dc);
		List<KeHoachVatTu> khvtListDc = keHoachVatTuRepository.findByCtkhnId(dc.getId());

		// Qd
		List<KeHoachLuongThucMuoi> khltmListQd = this.retrieveKhltm(qdGoc);
		List<KeHoachVatTu> khvtListQd = keHoachVatTuRepository.findByCtkhnId(qdGoc.getId());

		if (!CollectionUtils.isEmpty(khltmListDc)) {
			Set<String> groupDviAndVatTu = khltmListDc.stream().map(KeHoachLuongThucMuoi::groupByDonViIdAndVatTuId).collect(Collectors.toSet());
			khltmListQd.removeIf(kh -> groupDviAndVatTu.contains(kh.groupByDonViIdAndVatTuId()));
			khltmListQd.addAll(khltmListDc);
		}

		if (!CollectionUtils.isEmpty(khvtListDc)) {
			Set<String> groupDviAndVatTu = khvtListDc.stream().map(KeHoachVatTu::groupByDonViIdAndVatTuId).collect(Collectors.toSet());
			khvtListQd.removeIf(kh -> groupDviAndVatTu.contains(kh.groupByDonViIdAndVatTuId()));
			khvtListQd.addAll(khvtListDc);
		}

		qdGoc.setLastest(false);
		chiTieuKeHoachNamRepository.save(qdGoc);

		ChiTieuKeHoachNam lastest = ChiTieuKeHoachNam.builder()
				.namKeHoach(qdGoc.getNamKeHoach())
				.donViId(qdGoc.getDonViId())
				.ngayKy(qdGoc.getNgayKy())
				.ngayHieuLuc(qdGoc.getNgayHieuLuc())
				.soQuyetDinh(qdGoc.getSoQuyetDinh())
				.trichYeu(qdGoc.getTrichYeu())
				.qdGocId(qdGoc.getId())
				.lastest(true).build();
		chiTieuKeHoachNamRepository.save(lastest);

		for (KeHoachLuongThucMuoi kh : khltmListQd) {
			KeHoachLuongThucMuoi cloneKh = new KeHoachLuongThucMuoi();
			BeanUtils.copyProperties(kh, cloneKh, "id", "ctkhnId");
			cloneKh.setCtkhnId(lastest.getId());
			keHoachLuongThucMuoiRepository.save(cloneKh);

			List<KeHoachXuatLuongThucMuoi> khxList = new ArrayList<>();
			for (KeHoachXuatLuongThucMuoi khx : kh.getKhxltms()) {
				KeHoachXuatLuongThucMuoi cloneKhx = new KeHoachXuatLuongThucMuoi();
				BeanUtils.copyProperties(kh, cloneKh, "id", "keHoachId");
				cloneKhx.setKeHoachId(cloneKh.getId());
				khxList.add(cloneKhx);
			}
			keHoachXuatLuongThucMuoiRepository.saveAll(khxList);
		}

		for (KeHoachVatTu kh : khvtListQd) {
			KeHoachVatTu cloneKh = new KeHoachVatTu();
			BeanUtils.copyProperties(kh, cloneKh, "id", "ctkhnId");
			cloneKh.setCtkhnId(lastest.getId());
			keHoachVatTuRepository.save(cloneKh);
		}
	}
	private List<KeHoachLuongThucMuoi> retrieveKhltm(ChiTieuKeHoachNam chiTieuKeHoachNam) {
		List<KeHoachLuongThucMuoi> khltmList = keHoachLuongThucMuoiRepository.findByCtkhnId(chiTieuKeHoachNam.getId());

		Set<Long> khIdList = khltmList.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toSet());
		List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuois = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdIn(khIdList);
		for (KeHoachLuongThucMuoi kh : khltmList) {
			List<KeHoachXuatLuongThucMuoi> keHoachXuat = keHoachXuatLuongThucMuois.stream()
					.filter(khx -> khx.getKeHoachId().equals(kh.getId()))
					.collect(Collectors.toList());
			kh.setKhxltms(keHoachXuat);
		}

		return khltmList;
	}

	public boolean updateStatus(StatusReq req, ChiTieuKeHoachNam chiTieuKeHoachNam, UserInfo userInfo) throws Exception {
		String trangThai = chiTieuKeHoachNam.getTrangThai();
		if (ChiTieuKeHoachNamStatus.CHO_DUYET.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.MOI_TAO.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.CHO_DUYET.getId());
			chiTieuKeHoachNam.setNguoiGuiDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayGuiDuyet(LocalDate.now());

		} else if (ChiTieuKeHoachNamStatus.DA_DUYET.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.CHO_DUYET.getId().equals(trangThai))
				return false;
			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.DA_DUYET.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
		} else if (ChiTieuKeHoachNamStatus.TU_CHOI.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.CHO_DUYET.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.TU_CHOI.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
			chiTieuKeHoachNam.setLyDoTuChoi(req.getLyDoTuChoi());
		} else {
			throw new Exception("Bad request.");
		}

		chiTieuKeHoachNamRepository.save(chiTieuKeHoachNam);
		return true;
	}

	public ChiTieuKeHoachNamRes buildDetailResponse(ChiTieuKeHoachNam chiTieuKeHoachNam) throws Exception {
		List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAll());
		Map<String, QlnvDmVattu> mapMaVatTu = vattuList.stream().collect(Collectors.toMap(QlnvDmVattu::getMa, Function.identity()));

		ChiTieuKeHoachNamRes response = new ChiTieuKeHoachNamRes();
		response.setId(chiTieuKeHoachNam.getId());
		response.setNamKeHoach(chiTieuKeHoachNam.getNamKeHoach());
		response.setSoQuyetDinh(chiTieuKeHoachNam.getSoQuyetDinh());
		response.setNgayHieuLuc(chiTieuKeHoachNam.getNgayHieuLuc());
		response.setNgayKy(chiTieuKeHoachNam.getNgayKy());
		response.setTrangThai(chiTieuKeHoachNam.getTrangThai());
		response.setTenTrangThai(ChiTieuKeHoachNamStatus.getTenById(chiTieuKeHoachNam.getTrangThai()));
		response.setTrichYeu(chiTieuKeHoachNam.getTrichYeu());
		response.setQdGocId(chiTieuKeHoachNam.getQdGocId());
		response.setGhiChu(chiTieuKeHoachNam.getGhiChu());

		List<KeHoachLuongThucMuoi> keHoachLuongThucList = chiTieuKeHoachNam.getKhLuongThucList();
		List<KeHoachLuongThucMuoi> keHoachMuoiList = chiTieuKeHoachNam.getKhMuoiList();
		List<KeHoachVatTu> keHoachVatTuList = chiTieuKeHoachNam.getKhVatTuList();

		Set<Long> donViIdSet = new HashSet<>();

		List<String> maDviLtm = new ArrayList<>();
		List<String> maVatTuLtm = new ArrayList<>();

		maVatTuLtm.add(MUOI_MA_VT);
		maVatTuLtm.add(GAO_MA_VT);
		maVatTuLtm.add(THOC_MA_VT);

		keHoachLuongThucList.forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());
		});

		keHoachMuoiList.forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());
		});

		keHoachVatTuList.forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());
		});

		List<QlnvDmDonvi> dmDonviList = Lists.newArrayList(qlnvDmDonviRepository.findAllById(donViIdSet));

		Map<Long, QlnvDmDonvi> mapDonVi = dmDonviList.stream().collect(Collectors.toMap(QlnvDmDonvi::getId, Function.identity()));

		// THOC GAO
		List<KeHoachLuongThucDuTruRes> khLtResList = new ArrayList<>();
		for (KeHoachLuongThucMuoi keHoachLuongThucMuoi : keHoachLuongThucList) {
			Long vatTuId = keHoachLuongThucMuoi.getVatTuId();
			if (MUOI_ID.equals(vatTuId))
				continue;

			KeHoachLuongThucDuTruRes res = khLtResList.stream()
					.filter(kh -> kh.getDonViId().equals(keHoachLuongThucMuoi.getDonViId()))
					.findFirst().orElse(null);

			if (res == null) {
				res = new KeHoachLuongThucDuTruRes();
				khLtResList.add(res);
			}

			res.setStt(keHoachLuongThucMuoi.getStt());
			QlnvDmDonvi donVi = mapDonVi.get(keHoachLuongThucMuoi.getDonViId());
			if (donVi == null)
				throw new Exception("Đơn vị không tồn tại");


			// set donvi
			res.setDonViId(donVi.getId());
			res.setTenDonvi(donVi.getTenDvi());
			res.setMaDonVi(donVi.getMaDvi());
			res.setDonViTinh(keHoachLuongThucMuoi.getDonViTinh());
			maDviLtm.add(donVi.getMaDvi());

			// Nhap trong nam
			double ntnTongSoQuyThoc = Optional.ofNullable(res.getNtnTongSoQuyThoc()).orElse(0d);
			if (GAO_ID.equals(vatTuId)) {
				res.setNtnGao(keHoachLuongThucMuoi.getSoLuongNhap());
				ntnTongSoQuyThoc = ntnTongSoQuyThoc + (keHoachLuongThucMuoi.getSoLuongNhap() * 2);
				res.setKhGaoId(keHoachLuongThucMuoi.getId());
			} else if (THOC_ID.equals(vatTuId)) {
				res.setNtnThoc(keHoachLuongThucMuoi.getSoLuongNhap());
				ntnTongSoQuyThoc = ntnTongSoQuyThoc + keHoachLuongThucMuoi.getSoLuongNhap();
				res.setKhThocId(keHoachLuongThucMuoi.getId());
			}
			res.setNtnTongSoQuyThoc(ntnTongSoQuyThoc);

			// Xuat Trong nam
			List<KeHoachXuatLuongThucMuoi> khxltms = keHoachLuongThucMuoi.getKhxltms();
			double xtnTongSoQuyThoc = Optional.ofNullable(res.getXtnTongSoQuyThoc()).orElse(0d);
			double xtnTongThoc = Optional.ofNullable(res.getXtnTongThoc()).orElse(0d);
			double xtnTongGao = Optional.ofNullable(res.getXtnTongGao()).orElse(0d);

			List<VatTuNhapRes> xtnGao = new ArrayList<>();
			List<VatTuNhapRes> xtnThoc = new ArrayList<>();

			for (KeHoachXuatLuongThucMuoi khxltm : khxltms) {
				VatTuNhapRes vatTuNhapRes = new VatTuNhapRes();
				vatTuNhapRes.setId(khxltm.getId());
				vatTuNhapRes.setNam(khxltm.getNamKeHoach());
				vatTuNhapRes.setSoLuong(khxltm.getSoLuongXuat());
				vatTuNhapRes.setVatTuId(vatTuId);
				if (GAO_ID.equals(vatTuId)) {
					xtnGao.add(vatTuNhapRes);
					xtnTongGao = xtnTongGao + khxltm.getSoLuongXuat();
					xtnTongSoQuyThoc = xtnTongSoQuyThoc + (khxltm.getSoLuongXuat() * 2);
				} else if (THOC_ID.equals(vatTuId)) {
					xtnThoc.add(vatTuNhapRes);
					xtnTongThoc = xtnTongThoc + khxltm.getSoLuongXuat();
					xtnTongSoQuyThoc = xtnTongSoQuyThoc + khxltm.getSoLuongXuat();
				}
			}

			if (!CollectionUtils.isEmpty(xtnGao)) {
				res.setXtnGao(xtnGao);
			}

			if (!CollectionUtils.isEmpty(xtnThoc)) {
				res.setXtnThoc(xtnThoc);
			}

			res.setXtnTongSoQuyThoc(xtnTongSoQuyThoc);
			res.setXtnTongThoc(xtnTongThoc);
			res.setXtnTongGao(xtnTongGao);
		}

		// Muoi
		List<KeHoachMuoiDuTruRes> khMuoiResList = new ArrayList<>();
		for (KeHoachLuongThucMuoi keHoachLuongThucMuoi : keHoachMuoiList) {
			Long vatTuId = keHoachLuongThucMuoi.getVatTuId();
			if (!MUOI_ID.equals(vatTuId))
				continue;

			KeHoachMuoiDuTruRes res = khMuoiResList.stream()
					.filter(kh -> kh.getDonViId().equals(keHoachLuongThucMuoi.getDonViId()))
					.findFirst().orElse(null);

			if (res == null) {
				res = new KeHoachMuoiDuTruRes();
				khMuoiResList.add(res);
			}

			res.setId(keHoachLuongThucMuoi.getId());
			res.setStt(keHoachLuongThucMuoi.getStt());
			QlnvDmDonvi donVi = mapDonVi.get(keHoachLuongThucMuoi.getDonViId());
			if (donVi == null)
				throw new Exception("Đơn vị không tồn tại");

			// set donvi
			res.setDonViId(donVi.getId());
			res.setTenDonVi(donVi.getTenDvi());
			res.setMaDonVi(donVi.getMaDvi());
			res.setDonViTinh(keHoachLuongThucMuoi.getDonViTinh());
			maDviLtm.add(donVi.getMaDvi());

			// Nhap trong nam
			res.setNtnTongSoMuoi(keHoachLuongThucMuoi.getSoLuongNhap());

			// Xuat Trong nam
			List<KeHoachXuatLuongThucMuoi> khxltms = keHoachLuongThucMuoi.getKhxltms();
			double xtnTongSoMuoi = Optional.ofNullable(res.getXtnTongSoMuoi()).orElse(0d);

			List<VatTuNhapRes> xtnMuoi = new ArrayList<>();

			for (KeHoachXuatLuongThucMuoi khxltm : khxltms) {
				VatTuNhapRes vatTuNhapRes = new VatTuNhapRes();
				vatTuNhapRes.setId(khxltm.getId());
				vatTuNhapRes.setNam(khxltm.getNamKeHoach());
				vatTuNhapRes.setSoLuong(khxltm.getSoLuongXuat());
				vatTuNhapRes.setVatTuId(vatTuId);
				xtnMuoi.add(vatTuNhapRes);
				xtnTongSoMuoi = xtnTongSoMuoi + khxltm.getSoLuongXuat();
			}

			if (!CollectionUtils.isEmpty(xtnMuoi)) {
				res.setXtnMuoi(xtnMuoi);
			}
		}

		List<Long> vatTuIdList = new ArrayList<>();
		List<KeHoachVatTuRes> khVatTuResList = new ArrayList<>();
		for (KeHoachVatTu keHoachVatTu : keHoachVatTuList) {
			KeHoachVatTuRes keHoachVatTuRes = khVatTuResList.stream().filter(k -> k.getDonViId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);
			if (keHoachVatTuRes == null) {
				keHoachVatTuRes = new KeHoachVatTuRes();
				khVatTuResList.add(keHoachVatTuRes);
			}

			keHoachVatTuRes.setStt(keHoachVatTu.getSttDonVi());
			keHoachVatTuRes.setId(keHoachVatTu.getId());
			QlnvDmDonvi donVi = dmDonviList.stream().filter(d -> d.getId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);

			if (donVi == null)
				throw new Exception("Đơn vị không tồn tại");

			String tenDvi = donVi.getTenDvi();
			String maDvi = donVi.getMaDvi();
			keHoachVatTuRes.setId(keHoachVatTu.getId());
			keHoachVatTuRes.setMaDonVi(maDvi);
			keHoachVatTuRes.setDonViId(keHoachVatTu.getDonViId());
			keHoachVatTuRes.setTenDonVi(tenDvi);
			QlnvDmVattu vattu = vattuList.stream().filter(v -> v.getId().equals(keHoachVatTu.getVatTuId())).findFirst().orElse(null);
			if (vattu == null)
				throw new Exception("Vật tư không tồn tại");

			vatTuIdList.add(vattu.getId());
			String tenVattu = vattu.getTen();
			VatTuThietBiRes vatTuThietBiRes = new VatTuThietBiRes();
			vatTuThietBiRes.setStt(keHoachVatTu.getSttVatTu());
			vatTuThietBiRes.setVatTuId(keHoachVatTu.getVatTuId());
			vatTuThietBiRes.setTenVatTu(tenVattu);
			vatTuThietBiRes.setMaVatTu(vattu.getMa());
			vatTuThietBiRes.setDonViTinh(keHoachVatTu.getDonViTinh());
			vatTuThietBiRes.setNhapTrongNam(keHoachVatTu.getSoLuongNhap());

			if (!StringUtils.isEmpty(vattu.getMaCha())) {
				QlnvDmVattu vattuCha = vattuList.stream().filter(v -> v.getMa().equalsIgnoreCase(vattu.getMaCha())).findFirst().orElse(null);
				if (vattuCha == null)
					throw new Exception("Vật tư không tồn tại");

				vatTuThietBiRes.setMaVatTuCha(vattuCha.getMa());
				vatTuThietBiRes.setVatTuChaId(vattuCha.getId());
				vatTuThietBiRes.setTenVatTuCha(vattuCha.getTen());
			}
			keHoachVatTuRes.getVatTuThietBi().add(vatTuThietBiRes);
		}

		List<TonKhoDauNamRes> tonKhoDauNamResList = this.getTonKhoDauNam(maDviLtm, maVatTuLtm, chiTieuKeHoachNam.getNamKeHoach(), dmDonviList);
		khLtResList.forEach(k -> {
			TonKhoDauNamRes tonKhoDauNamGao = tonKhoDauNamResList.stream().filter(t -> k.getDonViId().equals(t.getDonViId()) && GAO_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			TonKhoDauNamRes tonKhoDauNamThoc = tonKhoDauNamResList.stream().filter(t -> k.getDonViId().equals(t.getDonViId()) && THOC_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);

			if (tonKhoDauNamThoc != null && !CollectionUtils.isEmpty(tonKhoDauNamThoc.getTonKho())) {
				k.setTkdnThoc(tonKhoDauNamThoc.getTonKho().stream().sorted(Comparator.comparing(VatTuNhapRes::getNam)).collect(Collectors.toList()));
			}

			if (tonKhoDauNamGao != null && !CollectionUtils.isEmpty(tonKhoDauNamGao.getTonKho())) {
				k.setTkdnGao(tonKhoDauNamGao.getTonKho().stream().sorted(Comparator.comparing(VatTuNhapRes::getNam)).collect(Collectors.toList()));
			}

			k.setTkdnTongThoc(k.getTkdnThoc().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			k.setTkdnTongGao(k.getTkdnGao().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			k.setTkdnTongSoQuyThoc((k.getTkdnTongGao() * 2) + k.getTkdnTongThoc());

			k.setTkcnTongGao(k.getTkdnTongGao() - k.getXtnTongGao() + k.getNtnGao());
			k.setTkcnTongThoc(k.getTkdnTongThoc() - k.getXtnTongThoc() + k.getNtnThoc());
			k.setTkcnTongSoQuyThoc(k.getTkcnTongThoc() + (k.getTkcnTongGao() * 2));
		});

		khMuoiResList.forEach(k -> {
			k.setXtnTongSoMuoi(k.getXtnMuoi().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			TonKhoDauNamRes tonKhoDauNamMuoi = tonKhoDauNamResList.stream().filter(t -> k.getDonViId().equals(t.getDonViId()) && MUOI_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			if (tonKhoDauNamMuoi != null && !CollectionUtils.isEmpty(tonKhoDauNamMuoi.getTonKho())) {
				k.setTkdnMuoi(tonKhoDauNamMuoi.getTonKho().stream().sorted(Comparator.comparing(VatTuNhapRes::getNam)).collect(Collectors.toList()));
				k.setTkdnTongSoMuoi(k.getTkdnMuoi().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			} else {
				k.setTkdnTongSoMuoi(0d);
			}

			k.setTkcnTongSoMuoi(k.getTkdnTongSoMuoi() - k.getXtnTongSoMuoi() + k.getNtnTongSoMuoi());
		});

		List<VatTuNhapQueryDTO> vatTuNhapQueryDTOList = this.getKeHoachVatTuThietBiCacNamTruoc(vatTuIdList, chiTieuKeHoachNam.getNamKeHoach());
		khVatTuResList.forEach(k -> {
			Map<String, Set<VatTuThietBiRes>> mapNhomVatTu = new HashMap<>();
			List<VatTuThietBiRes> vatTuThietBi = k.getVatTuThietBi();
			for (VatTuThietBiRes vatTuRes : vatTuThietBi) {
				List<VatTuNhapQueryDTO> khntList = vatTuNhapQueryDTOList.stream()
						.filter(kh -> kh.getVatTuId().equals(vatTuRes.getVatTuId()))
						.collect(Collectors.toList());
				for (VatTuNhapQueryDTO vtn : khntList) {
					vatTuRes.getCacNamTruoc().add(new VatTuNhapRes(vtn.getNam(), vtn.getSoLuong(), vtn.getVatTuId()));
				}
				Double tongCacNamTruoc = vatTuRes.getCacNamTruoc().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum();
				vatTuRes.setTongCacNamTruoc(tongCacNamTruoc);
				vatTuRes.setTongNhap(vatTuRes.getNhapTrongNam() + tongCacNamTruoc);

				this.addVatTuThietBiChaRes(vatTuRes, mapMaVatTu, mapNhomVatTu);
			}

			List<VatTuThietBiRes> finalVatTuThietBiRes = this.tinhTongVatTuThietBiCha(mapNhomVatTu);
			k.setVatTuThietBi(finalVatTuThietBiRes);
		});

		khLtResList.sort(Comparator.comparing(KeHoachLuongThucDuTruRes::getStt));
		khMuoiResList.sort(Comparator.comparing(KeHoachMuoiDuTruRes::getStt));
		khVatTuResList.sort(Comparator.comparing(KeHoachVatTuRes::getStt));

		response.setKhLuongThuc(khLtResList);
		response.setKhMuoiDuTru(khMuoiResList);
		response.setKhVatTu(khVatTuResList);

		return response;
	}

	@Override
	public void addVatTuThietBiChaRes(VatTuThietBiRes vatTuRes, Map<String, QlnvDmVattu> mapMaVatTu, Map<String, Set<VatTuThietBiRes>> mapNhomVatTu) {
		List<VatTuNhapRes> cacNamTruoc = vatTuRes.getCacNamTruoc();
		QlnvDmVattu vattu = mapMaVatTu.get(vatTuRes.getMaVatTu());
		VatTuThietBiRes vatTuChaRes = null;
		if (!StringUtils.isEmpty(vattu.getMaCha())) {
			QlnvDmVattu vattuCha = mapMaVatTu.get(vattu.getMaCha());
			vatTuChaRes = new VatTuThietBiRes();
			vatTuChaRes.setVatTuId(vattuCha.getId());
			vatTuChaRes.setTenVatTu(vattuCha.getTen());
			vatTuChaRes.setMaVatTu(vattuCha.getMa());
			vatTuChaRes.setMaVatTuCha(vattuCha.getMaCha());
			vatTuChaRes.setDonViTinh(vatTuRes.getDonViTinh());

			vatTuChaRes.setNhapTrongNam(Optional.ofNullable(vatTuChaRes.getNhapTrongNam()).orElse(0d) + vatTuRes.getNhapTrongNam());
			vatTuChaRes.setTongNhap(Optional.ofNullable(vatTuChaRes.getTongNhap()).orElse(0d) + vatTuRes.getTongNhap());
			vatTuChaRes.setTongCacNamTruoc(Optional.ofNullable(vatTuChaRes.getTongCacNamTruoc()).orElse(0d) + vatTuRes.getTongCacNamTruoc());

			List<VatTuNhapRes> vatTuNhapResList = new ArrayList<>();
			for (VatTuNhapRes vatTuNhapRes : cacNamTruoc) {
				VatTuNhapRes tongNam = vatTuNhapResList.stream().filter(v -> vatTuNhapRes.getNam().equals(v.getNam())).findFirst().orElse(null);
				if (tongNam == null) {
					tongNam = new VatTuNhapRes();
					tongNam.setNam(vatTuNhapRes.getNam());
					tongNam.setSoLuong(0d);
					tongNam.setVatTuId(vattuCha.getId());
					vatTuNhapResList.add(tongNam);
				}
				tongNam.setSoLuong(tongNam.getSoLuong() + vatTuNhapRes.getSoLuong());
			}
			vatTuChaRes.setCacNamTruoc(vatTuNhapResList);
			Set<VatTuThietBiRes> vatTuSet = mapNhomVatTu.get(vattu.getMa()) != null ? mapNhomVatTu.get(vattu.getMa()) : new HashSet<>();
			vatTuSet.add(vatTuRes);
			vatTuSet.add(vatTuChaRes);
			mapNhomVatTu.remove(vattu.getMa());
			mapNhomVatTu.computeIfAbsent(vattu.getMaCha(), k -> new HashSet<>()).addAll(vatTuSet);
		}

		if (vatTuChaRes != null)
			this.addVatTuThietBiChaRes(vatTuChaRes, mapMaVatTu, mapNhomVatTu);
	}

	@Override
	public List<VatTuThietBiRes> tinhTongVatTuThietBiCha(Map<String, Set<VatTuThietBiRes>> mapNhomVatTu) {
		Map<String, VatTuThietBiRes> mapVatu = mapNhomVatTu.values().stream().flatMap(Collection::stream).collect(Collectors.toMap(VatTuThietBiRes::getMaVatTu, Function.identity(), (o1,o2) -> o2));
		List<VatTuThietBiRes> vatTuThietBiResList = new ArrayList<>(mapVatu.values());
		vatTuThietBiResList.sort(Comparator.comparing(VatTuThietBiRes::getMaVatTu, Comparator.reverseOrder()));
		for (VatTuThietBiRes res : vatTuThietBiResList) {
			String mvt = res.getMaVatTu();
			List<VatTuThietBiRes> vattuConList = vatTuThietBiResList.stream().filter(r -> !StringUtils.isEmpty(r.getMaVatTuCha()) && r.getMaVatTuCha().equalsIgnoreCase(mvt)).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(vattuConList))
				continue;

			res.setTongNhap(vattuConList.stream().mapToDouble(VatTuThietBiRes::getTongNhap).sum());
			res.setTongCacNamTruoc(vattuConList.stream().mapToDouble(VatTuThietBiRes::getTongCacNamTruoc).sum());
			res.setNhapTrongNam(vattuConList.stream().mapToDouble(VatTuThietBiRes::getNhapTrongNam).sum());

			List<VatTuNhapRes> vatTuNhapResList = new ArrayList<>();
			for (VatTuThietBiRes vtConRes : vattuConList) {
				for (VatTuNhapRes vatTuNhapRes : vtConRes.getCacNamTruoc()) {
					VatTuNhapRes tongNam = vatTuNhapResList.stream().filter(v -> vatTuNhapRes.getNam().equals(v.getNam())).findFirst().orElse(null);
					if (tongNam == null) {
						tongNam = new VatTuNhapRes();
						tongNam.setNam(vatTuNhapRes.getNam());
						tongNam.setSoLuong(0d);
						tongNam.setVatTuId(res.getVatTuId());
						vatTuNhapResList.add(tongNam);
					}
					tongNam.setSoLuong(tongNam.getSoLuong() + vatTuNhapRes.getSoLuong());
				}
			}
			res.setCacNamTruoc(vatTuNhapResList);
		}

		return vatTuThietBiResList;
	}

	private void validateCreateCtkhnRequest(ChiTieuKeHoachNamReq req) throws Exception {
		Set<Long> donViIdSet = new HashSet<>();

		List<String> maVatTuLtm = new ArrayList<>();

		maVatTuLtm.add(MUOI_MA_VT);
		maVatTuLtm.add(GAO_MA_VT);
		maVatTuLtm.add(THOC_MA_VT);

		req.getKhLuongThuc().forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());
		});

		req.getKhMuoi().forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());
		});

		req.getKhVatTu().forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());
		});

		List<QlnvDmDonvi> listDonVi = Lists.newArrayList(qlnvDmDonviRepository.findAllById(donViIdSet));
		if (CollectionUtils.isEmpty(listDonVi))
			throw new Exception("Đơn vị không tồn tại.");

		List<String> maDvis = listDonVi.stream().map(QlnvDmDonvi::getMaDvi).collect(Collectors.toList());
		List<TonKhoDauNamRes> tonKhoDauNamResList = this.getTonKhoDauNam(maDvis, maVatTuLtm, req.getNamKeHoach(), listDonVi);

		for (KeHoachLuongThucDuTruReq khReq : req.getKhLuongThuc()) {
			List<VatTuNhapReq> xtnGao = khReq.getXtnGao();
			List<VatTuNhapReq> xtnThoc = khReq.getXtnThoc();

			TonKhoDauNamRes tonKhoDauNamGao = tonKhoDauNamResList.stream().filter(t -> khReq.getDonViId().equals(t.getDonViId()) && GAO_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			TonKhoDauNamRes tonKhoDauNamThoc = tonKhoDauNamResList.stream().filter(t -> khReq.getDonViId().equals(t.getDonViId()) && THOC_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			for (VatTuNhapReq xtnReq : xtnGao) {
				this.validateXuatTrongNam(xtnReq, tonKhoDauNamGao);
			}

			for (VatTuNhapReq xtnReq : xtnThoc) {
				this.validateXuatTrongNam(xtnReq, tonKhoDauNamThoc);
			}
		}

		for (KeHoachMuoiDuTruReq khReq : req.getKhMuoi()) {
			List<VatTuNhapReq> xtnMuoi = khReq.getXuatTrongNam();

			TonKhoDauNamRes tonKhoDauNamMuoi = tonKhoDauNamResList.stream().filter(t -> khReq.getDonViId().equals(t.getDonViId()) && MUOI_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			for (VatTuNhapReq xtnReq : xtnMuoi) {
				this.validateXuatTrongNam(xtnReq, tonKhoDauNamMuoi);
			}
		}
	}

	private void validateXuatTrongNam(VatTuNhapReq xtnReq, TonKhoDauNamRes tonKhoDauNam) throws Exception {
		Integer nam = xtnReq.getNam();
		if (tonKhoDauNam == null && xtnReq.getSoLuong() > 0) {
			throw new Exception("Xuất trong năm không được lớn hơn tồn đầu năm.");
		} else if (tonKhoDauNam != null) {
			List<VatTuNhapRes> tonKho = tonKhoDauNam.getTonKho();

			if (CollectionUtils.isEmpty(tonKho) && xtnReq.getSoLuong() > 0) {
				throw new Exception("Xuất trong năm không được lớn hơn tồn đầu năm.");
			}

			VatTuNhapRes tkdn = tonKho.stream().filter(tk -> tk.getNam().equals(nam)).findFirst().orElse(null);
			if ((tkdn == null && xtnReq.getSoLuong() > 0) || (tkdn != null && xtnReq.getSoLuong() > tkdn.getSoLuong())) {
				throw new Exception("Xuất trong năm không được lớn hơn tồn đầu năm.");
			}
		}
	}

	@Override
	public Page<ChiTieuKeHoachNamRes> searchQd(SearchChiTieuKeHoachNamReq req, Pageable pageable) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		req.setDonViId(DON_VI_ID_1);
		req.setLoaiQuyetDinh(ChiTieuKeHoachEnum.QD.getValue());
		return chiTieuKeHoachNamRepository.search(req, pageable);
	}

	@Override
	public Page<ChiTieuKeHoachNamRes> searchQdDc(SearchChiTieuKeHoachNamReq req, Pageable pageable) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		req.setDonViId(DON_VI_ID_1);
		req.setLoaiQuyetDinh(ChiTieuKeHoachEnum.QD_DC.getValue());
		return chiTieuKeHoachNamRepository.search(req, pageable);
	}

	@Override
	public SoLuongTruocDieuChinhRes getSoLuongTruocDc(SoLuongTruocDieuChinhSearchReq req) throws Exception {

		Long ctkhnId = req.getCtkhnId();
		Long donViId = req.getDonViId();
		Set<Long> vatTuIds = req.getVatTuIds();

		if (ctkhnId == null || CollectionUtils.isEmpty(vatTuIds) || donViId == null)
			return null;

		Optional<ChiTieuKeHoachNam> optional = chiTieuKeHoachNamRepository.findById(ctkhnId);
		if (!optional.isPresent())
			throw new Exception("Chỉ tiêu kế hoạch không tồn tại.");

		Optional<QlnvDmDonvi> qDonVi = qlnvDmDonviRepository.findById(donViId);

		if (!qDonVi.isPresent())
			throw new Exception("Đơn vị không tồn tại.");
		QlnvDmDonvi donVi = qDonVi.get();

		List<String> maVatTuList = qlnvDmVattuRepository.findByIdIn(vatTuIds).stream().map(QlnvDmVattu::getMa).collect(Collectors.toList());

		Integer nam = optional.get().getNamKeHoach();

		List<Integer> namList = new ArrayList<>();
		for (int i = 1; i <= SO_NAM_CU; i++) {
			namList.add(nam - i);
		}
		List<VatTuNhapRes> tonKhoDauNam = new ArrayList<>();
		List<VatTuNhapRes> nhapTrongNam = new ArrayList<>();
		List<VatTuNhapRes> xuatTrongNam = new ArrayList<>();
		List<VatTuNhapRes> keHoachVatTuNamTruoc = new ArrayList<>();

		if (vatTuIds.contains(GAO_ID) || vatTuIds.contains(THOC_ID)|| vatTuIds.contains(MUOI_ID)) {
			List<KeHoachLuongThucMuoi> khltmList = keHoachLuongThucMuoiRepository.findByCtkhnIdAndDonViIdAndVatTuIdIn(ctkhnId, donViId, vatTuIds);
			Set<Long> khxltmIds = khltmList.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toSet());
			List<KeHoachXuatLuongThucMuoi> khxltmList = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdInAndNamKeHoachIn(khxltmIds, namList);
			for (KeHoachLuongThucMuoi khltm : khltmList) {
				VatTuNhapRes nhap = new VatTuNhapRes();
				nhap.setSoLuong(khltm.getSoLuongNhap());
				nhap.setVatTuId(khltm.getVatTuId());
				nhapTrongNam.add(nhap);

				List<KeHoachXuatLuongThucMuoi> khxltmByKeHoach = khxltmList.stream().filter(k -> k.getKeHoachId().equals(khltm.getId())).collect(Collectors.toList());
				for (KeHoachXuatLuongThucMuoi khxltm : khxltmByKeHoach) {
					VatTuNhapRes xuat = new VatTuNhapRes();
					xuat.setSoLuong(khxltm.getSoLuongXuat());
					xuat.setNam(khxltm.getNamKeHoach());
					xuat.setVatTuId(khltm.getVatTuId());
					xuatTrongNam.add(xuat);
				}
			}

			List<TonKhoDauNamRes> tonKhoDauNamList = this.getTonKhoDauNam(Collections.singletonList(donVi.getMaDvi()), maVatTuList, nam, Collections.singletonList(donVi));
			tonKhoDauNam = tonKhoDauNamList.stream().flatMap(t -> t.getTonKho().stream()).distinct().collect(Collectors.toList());
			for (Integer n : namList) {
				for (Long id : THOC_GAO_MUOI_IDS) {
					if (vatTuIds.contains(id)) {
						VatTuNhapRes res = tonKhoDauNam.stream()
								.filter(nhap -> nhap.getNam().equals(n) && nhap.getVatTuId().equals(GAO_ID))
								.findFirst().orElse(null);

						if (res == null)
							tonKhoDauNam.add(new VatTuNhapRes(n, 0d, id));
					}
				}
			}
		}

		vatTuIds.removeAll(Stream.of(GAO_ID, THOC_ID, MUOI_ID).collect(Collectors.toSet()));

		if (!CollectionUtils.isEmpty(vatTuIds)) {

			List<KeHoachVatTu> khvtList = keHoachVatTuRepository.findByCtkhnIdAndVatTuIdInAndDonViId(ctkhnId, vatTuIds, donViId);
			for (KeHoachVatTu khvt : khvtList) {
				VatTuNhapRes nhap = new VatTuNhapRes();
				nhap.setSoLuong(khvt.getSoLuongNhap());
				nhap.setVatTuId(khvt.getVatTuId());
				nhapTrongNam.add(nhap);
			}

			List<VatTuNhapQueryDTO> vatTuNhapQueryDTOs = this.getKeHoachVatTuThietBiCacNamTruoc(Lists.newArrayList(vatTuIds), nam);
			vatTuNhapQueryDTOs.forEach(dto -> keHoachVatTuNamTruoc.add(new VatTuNhapRes(dto.getNam(), dto.getSoLuong(), dto.getVatTuId())));

			for (Integer n : namList) {
				for (Long id : THOC_GAO_MUOI_IDS) {
					if (vatTuIds.contains(id)) {
						VatTuNhapRes res = keHoachVatTuNamTruoc.stream()
								.filter(nhap -> nhap.getNam().equals(n) && nhap.getVatTuId().equals(GAO_ID))
								.findFirst().orElse(null);

						if (res == null)
							keHoachVatTuNamTruoc.add(new VatTuNhapRes(n, 0d, id));
					}
				}
			}
		}
		SoLuongTruocDieuChinhRes response = new SoLuongTruocDieuChinhRes();
		response.setTonKhoDauNam(tonKhoDauNam);
		response.setNhapTrongNam(nhapTrongNam);
		response.setXuatTrongNam(xuatTrongNam);
		response.setKeHoachVatTuNamTruoc(keHoachVatTuNamTruoc);
		response.setDonViId(req.getDonViId());

		return response;
	}
}
