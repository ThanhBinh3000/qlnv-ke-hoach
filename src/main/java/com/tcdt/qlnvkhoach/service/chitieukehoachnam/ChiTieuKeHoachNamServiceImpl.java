package com.tcdt.qlnvkhoach.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.*;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoach.query.dto.VatTuNhapQueryDTO;
import com.tcdt.qlnvkhoach.repository.*;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.KeHoachLuongThucDuTruReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.KeHoachMuoiDuTruReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.KeHoachNhapVatTuThietBiReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.VatTuNhapReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.VatTuThietBiReq;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SoLuongTruocDieuChinhSearchReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.SoLuongTruocDieuChinhRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.TonKhoDauNamRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	public static final int SO_NAM_LUU_KHO_THOC_MUOI = 3;
	public static final int SO_NAM_LUU_KHO_GAO = 2;

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

	@Autowired
	private FileDinhKemService fileDinhKemService;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception {
		ChiTieuKeHoachNam exist = this.existCtkhn(req.getNamKeHoach(), ChiTieuKeHoachEnum.QD.getValue());
		if (exist != null)
			throw new Exception("Chỉ tiêu kế hoạch năm đã tồn tại");

		this.validateCreateCtkhnRequest(req);
		return this.create(req, ChiTieuKeHoachEnum.QD.getValue(), null);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes createQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {
		Long qdGocId = req.getQdGocId();
		Optional<ChiTieuKeHoachNam> optionalQdGoc = chiTieuKeHoachNamRepository.findById(qdGocId);
		if (!optionalQdGoc.isPresent())
			throw new Exception("Không tìm thấy quyết định gốc.");

		ChiTieuKeHoachNam qdGoc = optionalQdGoc.get();
		if (!ChiTieuKeHoachNamStatus.BAN_HANH.getId().equals(qdGoc.getTrangThai())) {
			throw new Exception("Không thể điều chỉnh quyết định chưa duyệt");
		}
		chiTieuKeHoachNamRepository.save(qdGoc);

		ChiTieuKeHoachNam exist = this.existCtkhn(req.getQdDc().getNamKeHoach(), ChiTieuKeHoachEnum.QD_DC.getValue());
		if (exist != null)
			throw new Exception("Quyết định diều chỉnh chỉ tiêu kế hoạch năm đã tồn tại");

		this.validateCreateCtkhnRequest(req.getQdDc());
		ChiTieuKeHoachNamRes qdDc = this.create(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue(), qdGoc.getId());
		ChiTieuKeHoachNamRes qd = this.detailQd(qdGocId);

		this.setData(qdDc, qd);
		return qdDc;
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
		chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.DU_THAO.getId());
		chiTieuKeHoachNam.setLoaiQuyetDinh(loaiQd);
		chiTieuKeHoachNam.setLastest(true);
		chiTieuKeHoachNam.setQdGocId(qdGocId);
		chiTieuKeHoachNam.setMaDvi(userInfo.getDvql());
		chiTieuKeHoachNam.setCapDvi(userInfo.getCapDvi());
		chiTieuKeHoachNamRepository.save(chiTieuKeHoachNam);
		Long ctkhnId = chiTieuKeHoachNam.getId();

		List<KeHoachLuongThucMuoi> keHoachThocGaoList = new ArrayList<>();
		List<KeHoachLuongThucMuoi> keHoachMuoiList = new ArrayList<>();
		List<KeHoachVatTu> keHoachVatTuList = new ArrayList<>();

		for (KeHoachLuongThucDuTruReq khltReq : req.getKhLuongThuc()) {
			KeHoachLuongThucMuoi khGao = this.saveKeHoachLuongThuc(khltReq, ctkhnId,
					Constants.LuongThucMuoiConst.GAO_MA_VT, Constants.LuongThucMuoiConst.GAO_ID, khltReq.getNtnGao(),
					null, new HashMap<>());

			List<KeHoachXuatLuongThucMuoi> khXuatGaoList = this.saveListKeHoachXuat(khGao.getId(), khltReq.getXtnGao(), new HashMap<>());
			khGao.setKhxltms(khXuatGaoList);

			KeHoachLuongThucMuoi khThoc = this.saveKeHoachLuongThuc(khltReq, ctkhnId,
					Constants.LuongThucMuoiConst.THOC_MA_VT, Constants.LuongThucMuoiConst.THOC_ID, khltReq.getNtnThoc(),
					null, new HashMap<>());

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
			keHoachVatTuList.addAll(this.saveListKeHoachVatTu(khvtReq, ctkhnId, new HashMap<>()));
		}

		chiTieuKeHoachNam.setKhLuongThucList(keHoachThocGaoList);
		chiTieuKeHoachNam.setKhMuoiList(keHoachMuoiList);
		chiTieuKeHoachNam.setKhVatTuList(keHoachVatTuList);

		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), ctkhnId, ChiTieuKeHoachNam.TABLE_NAME);
		chiTieuKeHoachNam.setFileDinhKems(fileDinhKems);
		return this.buildDetailResponse(chiTieuKeHoachNam);
	}

	private List<VatTuNhapQueryDTO> getKeHoachVatTuThietBiCacNamTruoc(List<Long> vatTuIdList, Integer nameKeHoach) {
		int tuNam = nameKeHoach - 3;
		int denNam = nameKeHoach - 1;
		List<VatTuNhapQueryDTO> results = keHoachVatTuRepository.findKeHoachVatTuCacNamTruocByVatTuId(vatTuIdList, nameKeHoach - 3, nameKeHoach - 1, ChiTieuKeHoachNamStatus.BAN_HANH.getId(), ChiTieuKeHoachEnum.QD.getValue(), true);
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

	private List<TonKhoDauNamRes> getTonKhoDauNam(List<String> maDonViList,
												  List<String> maVatTuList,
												  Integer namKeHoach,
												  List<QlnvDmDonvi> list) throws Exception {
		List<String> namList = new ArrayList<>();
		for (int i = 1; i <= SO_NAM_LUU_KHO_THOC_MUOI; i++) {
			namList.add(String.valueOf(namKeHoach - i));
		}
		List<KtTrangthaiHienthoi> trangthaiHienthoiList = ktTrangthaiHienthoiRepository.findAllByMaDonViInAndMaVthhInAndNamIn(maDonViList, maVatTuList, namList);

		List<TonKhoDauNamRes> tonKhoDauNamResList = new ArrayList<>();
		for (KtTrangthaiHienthoi trangthaiHienthoi : trangthaiHienthoiList) {
			// Gao lay 2 nam
			boolean ignore = Constants.LuongThucMuoiConst.GAO_MA_VT.equalsIgnoreCase(trangthaiHienthoi.getMaVthh())
					&& (namKeHoach - Integer.parseInt(trangthaiHienthoi.getNam())) > SO_NAM_LUU_KHO_GAO;

			if (ignore)
				continue;

			QlnvDmDonvi donvi = list.stream()
					.filter(d -> d.getMaDvi().equalsIgnoreCase(trangthaiHienthoi.getMaDonVi()))
					.findFirst().orElse(null);

			if (donvi == null)
				throw new Exception("Đơn vị không tồn tại.");

			TonKhoDauNamRes res = tonKhoDauNamResList.stream()
					.filter(t -> trangthaiHienthoi.getMaDonVi().equals(t.getMaDonVi())
							&& trangthaiHienthoi.getMaVthh().equals(t.getMaVatTu()))
					.findFirst().orElse(null);

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
		return this.update(req, ChiTieuKeHoachEnum.QD.getValue());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {
		this.validateCreateCtkhnRequest(req.getQdDc());
		ChiTieuKeHoachNamRes qdDc = this.update(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue());
		ChiTieuKeHoachNamRes qd = this.detailQd(qdDc.getQdGocId());

		this.setData(qdDc, qd);
		return qdDc;
	}

	public ChiTieuKeHoachNamRes update(ChiTieuKeHoachNamReq req, String loaiQd) throws Exception {
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

		ChiTieuKeHoachNam exist = this.existCtkhn(req.getNamKeHoach(), loaiQd);
		if (exist != null && !exist.getId().equals(ctkhnId))
			throw new Exception(ChiTieuKeHoachEnum.QD.getValue().equals(loaiQd) ? "Chỉ tiêu kế hoạch năm không tồn tại"
					: "Quyết định diều chỉnh chỉ tiêu kế hoạch năm đã tồn tại");

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
			KeHoachLuongThucMuoi khGao = this.saveKeHoachLuongThuc(khltReq, ctkhnId,
					Constants.LuongThucMuoiConst.GAO_MA_VT, Constants.LuongThucMuoiConst.GAO_ID, khltReq.getNtnGao(),
					gaoId, mapKhltm);

			this.saveListKeHoachXuat(khGao.getId(), khltReq.getXtnGao(), mapKhxltm);

			Long thocId = khltReq.getKhThocId();
			KeHoachLuongThucMuoi khThoc = this.saveKeHoachLuongThuc(khltReq, ctkhnId,
					Constants.LuongThucMuoiConst.THOC_MA_VT, Constants.LuongThucMuoiConst.THOC_ID, khltReq.getNtnThoc(),
					thocId, mapKhltm);

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
			this.saveListKeHoachVatTu(khvtReq, ctkhnId, mapKhvt);
		}

		if (!CollectionUtils.isEmpty(mapKhxltm.values()))
			keHoachXuatLuongThucMuoiRepository.deleteAll(mapKhxltm.values());

		if (!CollectionUtils.isEmpty(mapKhltm.values()))
			keHoachLuongThucMuoiRepository.deleteAll(mapKhltm.values());

		if (!CollectionUtils.isEmpty(mapKhvt.values()))
			keHoachVatTuRepository.deleteAll(mapKhvt.values());

		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), ctkhnId, ChiTieuKeHoachNam.TABLE_NAME);
		ctkhn.setFileDinhKems(fileDinhKems);
		return this.buildDetailResponse(ctkhn);
	}

	private KeHoachLuongThucMuoi saveKeHoachLuongThuc(KeHoachLuongThucDuTruReq khltReq, Long ctkhnId, String maVatTu,
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
		keHoachLuongThucMuoi.setMaDvi(khltReq.getMaDvi());
		keHoachLuongThucMuoi.setVatTuId(vatuId);
		keHoachLuongThucMuoi.setMaVatTu(maVatTu);
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
		keHoachLuongThucMuoi.setMaDvi(khMuoiReq.getMaDvi());
		keHoachLuongThucMuoi.setVatTuId(Constants.LuongThucMuoiConst.MUOI_ID);
		keHoachLuongThucMuoi.setMaVatTu(Constants.LuongThucMuoiConst.MUOI_MA_VT);
		Double soLuongNhap = khMuoiReq.getNhapTrongNam() == null ? 0 : khMuoiReq.getNhapTrongNam();
		keHoachLuongThucMuoi.setSoLuongNhap(soLuongNhap);
		keHoachLuongThucMuoi.setDonViTinh(khMuoiReq.getDonViTinh());

		keHoachLuongThucMuoiRepository.save(keHoachLuongThucMuoi);
		return keHoachLuongThucMuoi;
	}

	private List<KeHoachVatTu> saveListKeHoachVatTu(KeHoachNhapVatTuThietBiReq khVatTuReq, Long ctkhnId,
													Map<Long, KeHoachVatTu> mapKhvt) throws Exception {

		List<KeHoachVatTu> keHoachVatTuList = new ArrayList<>();
		Set<Long> removeIds = new HashSet<>();
		for (VatTuThietBiReq vatTuReq : khVatTuReq.getVatTuThietBi()) {
			KeHoachVatTu keHoachVatTu = new KeHoachVatTu();
			keHoachVatTu.setTrangThai(Constants.MOI_TAO);
			Long id = vatTuReq.getId();
			if (id != null) {
				keHoachVatTu = mapKhvt.get(id);
				if (keHoachVatTu == null)
					throw new Exception("Kế hoạch nhập vật tư thiết bị không tồn tại.");

				removeIds.add(id);
			}

			keHoachVatTu.setSttDonVi(khVatTuReq.getStt());
			keHoachVatTu.setCtkhnId(ctkhnId);
			keHoachVatTu.setDonViId(khVatTuReq.getDonViId());
			keHoachVatTu.setMaDvi(khVatTuReq.getMaDvi());
			keHoachVatTu.setSttVatTu(vatTuReq.getStt());
			keHoachVatTu.setDonViTinh(vatTuReq.getDonViTinh());
			keHoachVatTu.setSoLuongNhap(vatTuReq.getNhapTrongNam());
			keHoachVatTu.setVatTuId(vatTuReq.getVatTuId());
			keHoachVatTu.setVatTuChaId(vatTuReq.getVatTuChaId());
			keHoachVatTu.setMaVatTu(vatTuReq.getMaVatTu());
			keHoachVatTu.setMaVatTuCha(vatTuReq.getMaVatTuCha());
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

		if (ChiTieuKeHoachNamStatus.BAN_HANH.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định đã ban hành");
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

		if (ChiTieuKeHoachNamStatus.BAN_HANH.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định điều chỉnh đã ban hành");
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
		fileDinhKemService.delete(chiTieuKeHoachNam.getId(), ChiTieuKeHoachNam.TABLE_NAME);
		return true;
	}

	@Override
	public ChiTieuKeHoachNamRes detailQd(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");
		return this.detail(id);
	}

	@Override
	public ChiTieuKeHoachNamRes detailQdDc(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		ChiTieuKeHoachNamRes qdDc = this.detail(id);
		ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamRepository.findByNamKeHoachAndLastestAndLoaiQuyetDinh(qdDc.getNamKeHoach(), true, ChiTieuKeHoachEnum.QD.getValue())
				.stream().filter(c -> !ChiTieuKeHoachNamStatus.TU_CHOI.getId().equalsIgnoreCase(c.getTrangThai()))
				.findFirst().orElse(null);
		ChiTieuKeHoachNamRes qd = this.detail(chiTieuKeHoachNam.getId());
		this.setData(qdDc, qd);
		return qdDc;
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
			keHoachLuongThucMuoi.setKhxltms(Optional.ofNullable(mapKhxltm.get(keHoachLuongThucMuoi.getId())).orElse(new ArrayList<>()));
		}

		ctkhn.setKhLuongThucList(keHoachLuongThucMuois.stream().filter(kh -> Constants.LuongThucMuoiConst.GAO_ID.equals(kh.getVatTuId()) || Constants.LuongThucMuoiConst.THOC_ID.equals(kh.getVatTuId())).collect(Collectors.toList()));
		ctkhn.setKhMuoiList(keHoachLuongThucMuois.stream().filter(kh -> Constants.LuongThucMuoiConst.MUOI_ID.equals(kh.getVatTuId())).collect(Collectors.toList()));
		ctkhn.setKhVatTuList(keHoachVatTus);

		ctkhn.setFileDinhKems(fileDinhKemService.search(ctkhn.getId(), ChiTieuKeHoachNam.TABLE_NAME));
		ChiTieuKeHoachNamRes response = buildDetailResponse(ctkhn);
		addEmptyDataToExport(response);
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
		}

		//Muối
		for (KeHoachMuoiDuTruRes keHoachMuoiDuTruRes : chiTieuKeHoachNamRes.getKhMuoiDuTru()) {
			//Tồn kho đầu năm
			List<Integer> tkdnMuoi = keHoachMuoiDuTruRes.getTkdnMuoi()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(tkdnMuoi, chiTieuKeHoachNamRes.getNamKeHoach(), keHoachMuoiDuTruRes.getTkdnMuoi(),
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
		if (!ChiTieuKeHoachNamStatus.BAN_HANH.getId().equalsIgnoreCase(dc.getTrangThai()))
			return true;

		Optional<ChiTieuKeHoachNam> qdGocOptional = chiTieuKeHoachNamRepository.findById(dc.getQdGocId());
		if (!qdGocOptional.isPresent())
			throw new Exception("Không tìm thấy dữ liệu.");

		ChiTieuKeHoachNam qdGoc = qdGocOptional.get();

		// Duyệt điều chỉnh -> tạo quyết đinh mới từ quyết định gốc
		this.mergeQdDcAndQd(dc, qdGoc);
		return true;
	}

	private void mergeQdDcAndQd(ChiTieuKeHoachNam dc, ChiTieuKeHoachNam qdGoc) throws Exception {
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

		ChiTieuKeHoachNam lastestExist = chiTieuKeHoachNamRepository.findByNamKeHoachAndLastestAndLoaiQuyetDinh(qdGoc.getNamKeHoach(), true, ChiTieuKeHoachEnum.QD.getValue())
				.stream().findFirst().orElse(null);
		if (lastestExist != null) {
			this.delete(lastestExist);
		}
		ChiTieuKeHoachNam lastest = ChiTieuKeHoachNam.builder()
				.namKeHoach(qdGoc.getNamKeHoach())
				.maDvi(qdGoc.getMaDvi())
				.capDvi(qdGoc.getCapDvi())
				.ngayKy(qdGoc.getNgayKy())
				.ngayHieuLuc(qdGoc.getNgayHieuLuc())
				.soQuyetDinh(qdGoc.getSoQuyetDinh())
				.trichYeu(qdGoc.getTrichYeu())
				.canCu(qdGoc.getCanCu())
				.ghiChu(qdGoc.getGhiChu())
				.qdGocId(qdGoc.getId())
				.lastest(true)
				.loaiQuyetDinh(ChiTieuKeHoachEnum.QD.getValue())
				.trangThai(qdGoc.getTrangThai())
				.build();
		chiTieuKeHoachNamRepository.save(lastest);

		for (KeHoachLuongThucMuoi kh : khltmListQd) {
			KeHoachLuongThucMuoi cloneKh = new KeHoachLuongThucMuoi();
			BeanUtils.copyProperties(kh, cloneKh, "id", "ctkhnId");
			cloneKh.setCtkhnId(lastest.getId());
			keHoachLuongThucMuoiRepository.save(cloneKh);

			List<KeHoachXuatLuongThucMuoi> khxList = new ArrayList<>();
			for (KeHoachXuatLuongThucMuoi khx : kh.getKhxltms()) {
				KeHoachXuatLuongThucMuoi cloneKhx = new KeHoachXuatLuongThucMuoi();
				BeanUtils.copyProperties(khx, cloneKhx, "id", "keHoachId");
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
		if (ChiTieuKeHoachNamStatus.DU_THAO_TRINH_DUYET.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.DU_THAO.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.DU_THAO_TRINH_DUYET.getId());
			chiTieuKeHoachNam.setNguoiGuiDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayGuiDuyet(LocalDate.now());

		} else if (ChiTieuKeHoachNamStatus.LANH_DAO_DUYET.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.DU_THAO_TRINH_DUYET.getId().equals(trangThai))
				return false;
			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.LANH_DAO_DUYET.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
		} else if (ChiTieuKeHoachNamStatus.BAN_HANH.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.LANH_DAO_DUYET.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.BAN_HANH.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
		} else if (ChiTieuKeHoachNamStatus.TU_CHOI.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatus.DU_THAO_TRINH_DUYET.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.TU_CHOI.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
			chiTieuKeHoachNam.setLyDoTuChoi(req.getLyDoTuChoi());
		}  else {
			throw new Exception("Bad request.");
		}

		chiTieuKeHoachNamRepository.save(chiTieuKeHoachNam);
		return true;
	}

	public ChiTieuKeHoachNamRes buildDetailResponse(ChiTieuKeHoachNam chiTieuKeHoachNam) throws Exception {
		List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAll());

		ChiTieuKeHoachNamRes response = new ChiTieuKeHoachNamRes();
		response.setId(chiTieuKeHoachNam.getId());
		response.setNamKeHoach(chiTieuKeHoachNam.getNamKeHoach());
		response.setSoQuyetDinh(chiTieuKeHoachNam.getSoQuyetDinh());
		response.setNgayHieuLuc(chiTieuKeHoachNam.getNgayHieuLuc());
		response.setNgayKy(chiTieuKeHoachNam.getNgayKy());
		response.setTrangThai(chiTieuKeHoachNam.getTrangThai());
		response.setTenTrangThai(ChiTieuKeHoachNamStatus.getTenById(chiTieuKeHoachNam.getTrangThai()));
		response.setTrangThaiDuyet(ChiTieuKeHoachNamStatus.getTrangThaiDuyetById(chiTieuKeHoachNam.getTrangThai()));
		response.setTrichYeu(chiTieuKeHoachNam.getTrichYeu());
		response.setQdGocId(chiTieuKeHoachNam.getQdGocId());
		response.setGhiChu(chiTieuKeHoachNam.getGhiChu());
		response.setCanCu(chiTieuKeHoachNam.getCanCu());
		response.setLyDoTuChoi(chiTieuKeHoachNam.getLyDoTuChoi());
		response.setFileDinhKems(chiTieuKeHoachNam.getFileDinhKems());

		List<KeHoachLuongThucMuoi> keHoachLuongThucList = chiTieuKeHoachNam.getKhLuongThucList();
		List<KeHoachLuongThucMuoi> keHoachMuoiList = chiTieuKeHoachNam.getKhMuoiList();
		List<KeHoachVatTu> keHoachVatTuList = chiTieuKeHoachNam.getKhVatTuList();

		Set<Long> donViIdSet = new HashSet<>();

		List<String> maDviLtm = new ArrayList<>();
		List<String> maVatTuLtm = new ArrayList<>();

		maVatTuLtm.add(Constants.LuongThucMuoiConst.MUOI_MA_VT);
		maVatTuLtm.add(Constants.LuongThucMuoiConst.GAO_MA_VT);
		maVatTuLtm.add(Constants.LuongThucMuoiConst.THOC_MA_VT);

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
			if (Constants.LuongThucMuoiConst.MUOI_ID.equals(vatTuId))
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
			if (Constants.LuongThucMuoiConst.GAO_ID.equals(vatTuId)) {
				res.setNtnGao(keHoachLuongThucMuoi.getSoLuongNhap());
				ntnTongSoQuyThoc = ntnTongSoQuyThoc + (keHoachLuongThucMuoi.getSoLuongNhap() * 2);
				res.setKhGaoId(keHoachLuongThucMuoi.getId());
			} else if (Constants.LuongThucMuoiConst.THOC_ID.equals(vatTuId)) {
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
				if (Constants.LuongThucMuoiConst.GAO_ID.equals(vatTuId)) {
					xtnGao.add(vatTuNhapRes);
					xtnTongGao = xtnTongGao + khxltm.getSoLuongXuat();
					xtnTongSoQuyThoc = xtnTongSoQuyThoc + (khxltm.getSoLuongXuat() * 2);
				} else if (Constants.LuongThucMuoiConst.THOC_ID.equals(vatTuId)) {
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
			if (!Constants.LuongThucMuoiConst.MUOI_ID.equals(vatTuId))
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
			QlnvDmDonvi donVi = dmDonviList.stream().filter(d -> d.getId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);

			if (donVi == null)
				throw new Exception("Đơn vị không tồn tại");

			String tenDvi = donVi.getTenDvi();
			String maDvi = donVi.getMaDvi();
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
			vatTuThietBiRes.setKyHieu(vattu.getKyHieu());
			vatTuThietBiRes.setId(keHoachVatTu.getId());

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
			TonKhoDauNamRes tonKhoDauNamGao = tonKhoDauNamResList.stream().filter(t -> k.getDonViId().equals(t.getDonViId()) && Constants.LuongThucMuoiConst.GAO_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			TonKhoDauNamRes tonKhoDauNamThoc = tonKhoDauNamResList.stream().filter(t -> k.getDonViId().equals(t.getDonViId()) && Constants.LuongThucMuoiConst.THOC_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);

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
			TonKhoDauNamRes tonKhoDauNamMuoi = tonKhoDauNamResList.stream().filter(t -> k.getDonViId().equals(t.getDonViId()) && Constants.LuongThucMuoiConst.MUOI_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			if (tonKhoDauNamMuoi != null && !CollectionUtils.isEmpty(tonKhoDauNamMuoi.getTonKho())) {
				k.setTkdnMuoi(tonKhoDauNamMuoi.getTonKho().stream().sorted(Comparator.comparing(VatTuNhapRes::getNam)).collect(Collectors.toList()));
				k.setTkdnTongSoMuoi(k.getTkdnMuoi().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			} else {
				k.setTkdnTongSoMuoi(0d);
			}

			k.setTkcnTongSoMuoi(k.getTkdnTongSoMuoi() - k.getXtnTongSoMuoi() + k.getNtnTongSoMuoi());
		});

		List<VatTuNhapQueryDTO> vatTuNhapQueryDTOList = this.getKeHoachVatTuThietBiCacNamTruoc(vatTuIdList, chiTieuKeHoachNam.getNamKeHoach());
		for (KeHoachVatTuRes k : khVatTuResList) {
			List<VatTuThietBiRes> vatTuThietBi = k.getVatTuThietBi();
			if (CollectionUtils.isEmpty(vatTuThietBi))
				continue;

			for (VatTuThietBiRes vatTuRes : vatTuThietBi) {
				List<VatTuNhapQueryDTO> khntList = vatTuNhapQueryDTOList.stream()
						.filter(kh -> kh.getVatTuId().equals(vatTuRes.getVatTuId()))
						.collect(Collectors.toList());
				for (VatTuNhapQueryDTO vtn : khntList) {
					vatTuRes.getCacNamTruoc().add(new VatTuNhapRes(vtn.getNam(), vtn.getSoLuong(), vtn.getVatTuId()));
				}
				Double tongCacNamTruoc = vatTuRes.getCacNamTruoc().stream().filter(v -> v.getSoLuong() != null).mapToDouble(VatTuNhapRes::getSoLuong).sum();
				vatTuRes.setTongCacNamTruoc(Optional.ofNullable(tongCacNamTruoc).orElse(0d));
				vatTuRes.setTongNhap(Optional.ofNullable(vatTuRes.getNhapTrongNam()).orElse(0d) + Optional.ofNullable(tongCacNamTruoc).orElse(0d));
			}
		}

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

			vatTuChaRes.setNhapTrongNam(Optional.ofNullable(vatTuChaRes.getNhapTrongNam()).orElse(0d) + Optional.ofNullable(vatTuRes.getNhapTrongNam()).orElse(0d));
			vatTuChaRes.setTongNhap(Optional.ofNullable(vatTuChaRes.getTongNhap()).orElse(0d) + Optional.ofNullable(vatTuRes.getTongNhap()).orElse(0d));
			vatTuChaRes.setTongCacNamTruoc(Optional.ofNullable(vatTuChaRes.getTongCacNamTruoc()).orElse(0d) + Optional.ofNullable(vatTuRes.getTongCacNamTruoc()).orElse(0d));

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
		} else {
			mapNhomVatTu.computeIfAbsent(vattu.getMa(), k -> new HashSet<>()).add(vatTuRes);
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
		String ghiChu = Optional.ofNullable(req.getGhiChu()).orElse("").trim();
		String soQd = Optional.ofNullable(req.getSoQuyetDinh()).orElse("").trim();
		if (StringUtils.isEmpty(ghiChu)) {
			throw new Exception("Ghi chú không được để trống.");
		}

		if (StringUtils.isEmpty(soQd)) {
			throw new Exception("Số quyết định không được để trống.");
		}
		Set<Long> donViIdSet = new HashSet<>();

		List<String> maVatTuLtm = new ArrayList<>();

		maVatTuLtm.add(Constants.LuongThucMuoiConst.MUOI_MA_VT);
		maVatTuLtm.add(Constants.LuongThucMuoiConst.GAO_MA_VT);
		maVatTuLtm.add(Constants.LuongThucMuoiConst.THOC_MA_VT);

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

			TonKhoDauNamRes tonKhoDauNamGao = tonKhoDauNamResList.stream().filter(t -> khReq.getDonViId().equals(t.getDonViId()) && Constants.LuongThucMuoiConst.GAO_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			TonKhoDauNamRes tonKhoDauNamThoc = tonKhoDauNamResList.stream().filter(t -> khReq.getDonViId().equals(t.getDonViId()) && Constants.LuongThucMuoiConst.THOC_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			for (VatTuNhapReq xtnReq : xtnGao) {
				this.validateXuatTrongNam(xtnReq, tonKhoDauNamGao);
			}

			for (VatTuNhapReq xtnReq : xtnThoc) {
				this.validateXuatTrongNam(xtnReq, tonKhoDauNamThoc);
			}
		}

		for (KeHoachMuoiDuTruReq khReq : req.getKhMuoi()) {
			List<VatTuNhapReq> xtnMuoi = khReq.getXuatTrongNam();

			TonKhoDauNamRes tonKhoDauNamMuoi = tonKhoDauNamResList.stream().filter(t -> khReq.getDonViId().equals(t.getDonViId()) && Constants.LuongThucMuoiConst.MUOI_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
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
	public Page<ChiTieuKeHoachNamRes> searchQd(SearchChiTieuKeHoachNamReq req) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		req.setDvql(userInfo.getDvql());
		req.setLoaiQuyetDinh(ChiTieuKeHoachEnum.QD.getValue());
		return chiTieuKeHoachNamRepository.search(req);
	}

	@Override
	public Page<ChiTieuKeHoachNamRes> searchQdDc(SearchChiTieuKeHoachNamReq req) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		req.setDvql(userInfo.getDvql());
		req.setLoaiQuyetDinh(ChiTieuKeHoachEnum.QD_DC.getValue());
		return chiTieuKeHoachNamRepository.search(req);
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
		for (int i = 1; i <= SO_NAM_LUU_KHO_THOC_MUOI; i++) {
			namList.add(nam - i);
		}
		List<VatTuNhapRes> tonKhoDauNam = new ArrayList<>();
		List<VatTuNhapRes> nhapTrongNam = new ArrayList<>();
		List<VatTuNhapRes> xuatTrongNam = new ArrayList<>();
		List<VatTuNhapRes> keHoachVatTuNamTruoc = new ArrayList<>();

		if (vatTuIds.contains(Constants.LuongThucMuoiConst.GAO_ID) || vatTuIds.contains(Constants.LuongThucMuoiConst.THOC_ID)|| vatTuIds.contains(Constants.LuongThucMuoiConst.MUOI_ID)) {
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
				for (Long id : Constants.LuongThucMuoiConst.THOC_GAO_MUOI_IDS) {
					if (vatTuIds.contains(id)) {
						// Gao lay 2 nam
						boolean ignore = Constants.LuongThucMuoiConst.GAO_ID.equals(id)
								&& (nam - n) > SO_NAM_LUU_KHO_GAO;

						if (ignore)
							continue;

						VatTuNhapRes res = tonKhoDauNam.stream()
								.filter(nhap -> nhap.getNam().equals(n) && nhap.getVatTuId().equals(id))
								.findFirst().orElse(null);

						if (res == null)
							tonKhoDauNam.add(new VatTuNhapRes(n, 0d, id));
					}
				}
			}
		}

		vatTuIds.removeAll(Stream.of(Constants.LuongThucMuoiConst.GAO_ID, Constants.LuongThucMuoiConst.THOC_ID, Constants.LuongThucMuoiConst.MUOI_ID).collect(Collectors.toSet()));

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
				for (Long id : Constants.LuongThucMuoiConst.THOC_GAO_MUOI_IDS) {
					if (vatTuIds.contains(id)) {
						VatTuNhapRes res = keHoachVatTuNamTruoc.stream()
								.filter(nhap -> nhap.getNam().equals(n) && nhap.getVatTuId().equals(Constants.LuongThucMuoiConst.GAO_ID))
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

	private ChiTieuKeHoachNam existCtkhn(Integer namKeHoach, String loaiQd) {
		return chiTieuKeHoachNamRepository.findByNamKeHoachAndLastestAndLoaiQuyetDinh(namKeHoach, true, loaiQd)
				.stream().filter(c -> !ChiTieuKeHoachNamStatus.TU_CHOI.getId().equalsIgnoreCase(c.getTrangThai()))
				.findFirst().orElse(null);

	}

	private void setData(ChiTieuKeHoachNamRes qdDc, ChiTieuKeHoachNamRes qd) {
		qdDc.setQdGocId(qd.getId());
		qdDc.setSoQdGoc(qd.getSoQuyetDinh());
		this.setDataTruocSauDieuChinh(qdDc, qd);
	}

	private void setDataTruocSauDieuChinh(ChiTieuKeHoachNamRes qdDc, ChiTieuKeHoachNamRes qd) {
		this.setDataTruocSauDieuChinhLuongThuc(qdDc, qd);
		this.setDataTruocSauDieuChinhMuoi(qdDc, qd);
		this.setDataTruocSauDieuChinhVatTu(qdDc, qd);
	}

	private void setDataTruocSauDieuChinhVatTu(ChiTieuKeHoachNamRes qdDc, ChiTieuKeHoachNamRes qd) {
		List<KeHoachVatTuRes> qdkh = qd.getKhVatTu();
		List<KeHoachVatTuRes> dcKh = qdDc.getKhVatTu();
		for (KeHoachVatTuRes dcRes : dcKh) {
			String maDvi = dcRes.getMaDonVi();

			if (StringUtils.isEmpty(maDvi))
				continue;

			Optional<KeHoachVatTuRes> optionalQd = qdkh.stream()
					.filter(l -> l.getMaDonVi().equals(maDvi))
					.findFirst();
			if (!optionalQd.isPresent())
				continue;

			KeHoachVatTuRes qdRes = optionalQd.get();
			List<VatTuThietBiRes> dcVatTuThietBi = dcRes.getVatTuThietBi();
			List<VatTuThietBiRes> qdVatTuThietBi = qdRes.getVatTuThietBi();

			for (VatTuThietBiRes dcVt : dcVatTuThietBi) {
				String maVatTu = dcVt.getMaVatTu();
				if (StringUtils.isEmpty(maVatTu))
					continue;

				Optional<VatTuThietBiRes> optionalVtQd = qdVatTuThietBi.stream()
						.filter(l -> l.getMaVatTu().equals(maVatTu))
						.findFirst();
				if (!optionalVtQd.isPresent())
					continue;
				VatTuThietBiRes qdVt = optionalVtQd.get();

				// Truoc dieu chinh Nhap Trong Nam
				dcVt.setTdcNhapTrongNam(qdVt.getNhapTrongNam());
				dcVt.setTdcTongNhap(qdVt.getTongNhap());
				dcVt.setTdcTongCacNamTruoc(qdVt.getTdcTongCacNamTruoc());
				dcVt.setTdcCacNamTruoc(qdVt.getCacNamTruoc());

				// Sau dieu chinh Nhap Trong Nam
				dcVt.setSdcNhapTrongNam(dcVt.getNhapTrongNam());
				dcVt.setSdcTongNhap(dcVt.getTongNhap());
				dcVt.setSdcTongCacNamTruoc(dcVt.getTongCacNamTruoc());
				dcVt.setSdcCacNamTruoc(qdVt.getCacNamTruoc());

				// Dieu chinh
				Double dcNhapTrongNam = Optional.ofNullable(dcVt.getSdcNhapTrongNam()).orElse(0D) - Optional.ofNullable(qdVt.getNhapTrongNam()).orElse(0D);
				dcVt.setDcNhapTrongNam(dcNhapTrongNam);
			}
		}
	}

	private void setDataTruocSauDieuChinhMuoi(ChiTieuKeHoachNamRes qdDc, ChiTieuKeHoachNamRes qd) {
		// Luong Thuc
		List<KeHoachMuoiDuTruRes> qdMuoi = qd.getKhMuoiDuTru();
		List<KeHoachMuoiDuTruRes> dcMuoi = qdDc.getKhMuoiDuTru();
		for (KeHoachMuoiDuTruRes dcRes : dcMuoi) {
			String maDvi = dcRes.getMaDonVi();
			if (StringUtils.isEmpty(maDvi))
				continue;

			Optional<KeHoachMuoiDuTruRes> optionalQd = qdMuoi.stream()
					.filter(l -> l.getMaDonVi().equals(maDvi))
					.findFirst();
			if (!optionalQd.isPresent())
				continue;

			KeHoachMuoiDuTruRes qdRes = optionalQd.get();

			// Nhap Trong Nam
			dcRes.setTdcNtnTongSoMuoi(qdRes.getNtnTongSoMuoi());
			dcRes.setSdcNtnTongSoMuoi(dcRes.getNtnTongSoMuoi());
			Double dcNtnTongSoMuoi = Optional.ofNullable(dcRes.getSdcNtnTongSoMuoi()).orElse(0D) - Optional.ofNullable(qdRes.getNtnTongSoMuoi()).orElse(0D);
			dcRes.setDcNtnTongSoMuoi(dcNtnTongSoMuoi);

			// Truoc dieu chinh Xuat Trong Nam
			dcRes.setTdcXtnTongSoMuoi(qdRes.getXtnTongSoMuoi());
			dcRes.setTdcXtnMuoi(qdRes.getXtnMuoi());

			// Sau dieu chinh Xuat Trong Nam
			dcRes.setSdcXtnTongSoMuoi(dcRes.getXtnTongSoMuoi());
			dcRes.setSdcXtnMuoi(dcRes.getSdcXtnMuoi());

			// Dieu chinh Xuat Trong Nam
			dcRes.setDcXtnMuoi(this.getDcXtnLuongThucMuoi(dcRes.getSdcXtnMuoi(), qdRes.getXtnMuoi()));
		}
	}

	private void setDataTruocSauDieuChinhLuongThuc(ChiTieuKeHoachNamRes qdDc, ChiTieuKeHoachNamRes qd) {
		// Luong Thuc
		List<KeHoachLuongThucDuTruRes> qdLuongThuc = qd.getKhLuongThuc();
		List<KeHoachLuongThucDuTruRes> dcLuongThuc = qdDc.getKhLuongThuc();
		for (KeHoachLuongThucDuTruRes dcRes : dcLuongThuc) {
			String maDvi = dcRes.getMaDonVi();
			if (StringUtils.isEmpty(maDvi))
				continue;

			Optional<KeHoachLuongThucDuTruRes> optionalQd = qdLuongThuc.stream()
					.filter(l -> l.getMaDonVi().equals(maDvi))
					.findFirst();
			if (!optionalQd.isPresent())
				continue;

			KeHoachLuongThucDuTruRes qdRes = optionalQd.get();

			// Truoc dieu chinh Nhap Trong Nam
			dcRes.setTdcNtnTongSoQuyThoc(qdRes.getNtnTongSoQuyThoc());
			dcRes.setTdcNtnGao(qdRes.getNtnGao());
			dcRes.setTdcNtnThoc(qdRes.getNtnThoc());

			// Sau dieu chinh Nhap Trong Nam
			dcRes.setSdcNtnTongSoQuyThoc(dcRes.getNtnTongSoQuyThoc());
			dcRes.setSdcNtnThoc(dcRes.getNtnThoc());
			dcRes.setSdcNtnGao(dcRes.getNtnGao());

			// Dieu chinh Nhap Trong Nam
			Double dcNtnGao = Optional.ofNullable(dcRes.getSdcNtnGao()).orElse(0D) - Optional.ofNullable(qdRes.getNtnGao()).orElse(0D);
			Double dcNtnThoc = Optional.ofNullable(dcRes.getSdcNtnThoc()).orElse(0D) - Optional.ofNullable(qdRes.getNtnThoc()).orElse(0D);
			dcRes.setDcNtnGao(dcNtnGao);
			dcRes.setDcNtnThoc(dcNtnThoc);

			// Truoc dieu chinh Xuat Trong Nam
			dcRes.setTdcXtnTongSoQuyThoc(qdRes.getXtnTongSoQuyThoc());
			dcRes.setTdcXtnThoc(qdRes.getXtnThoc());
			dcRes.setTdcXtnGao(qdRes.getXtnGao());
			dcRes.setTdcXtnThoc(qdRes.getXtnThoc());
			dcRes.setTdcXtnGao(qdRes.getXtnGao());

			// Sau dieu chinh Xuat Trong Nam
			dcRes.setSdcXtnTongSoQuyThoc(dcRes.getXtnTongSoQuyThoc());
			dcRes.setSdcXtnTongThoc(dcRes.getXtnTongThoc());
			dcRes.setSdcXtnTongGao(dcRes.getXtnTongGao());
			dcRes.setSdcXtnThoc(dcRes.getXtnThoc());
			dcRes.setSdcXtnGao(dcRes.getXtnGao());

			dcRes.setDcXtnThoc(this.getDcXtnLuongThucMuoi(dcRes.getSdcXtnThoc(), qdRes.getXtnThoc()));
			dcRes.setDcXtnGao(this.getDcXtnLuongThucMuoi(dcRes.getSdcXtnGao(), qdRes.getXtnGao()));

		}
	}

	private List<VatTuNhapRes> getDcXtnLuongThucMuoi(List<VatTuNhapRes> dcRes, List<VatTuNhapRes> qdRes) {
		List<VatTuNhapRes> dcXtns = new ArrayList<>();
		for (VatTuNhapRes dcVtRes : dcRes) {
			Integer nam = dcVtRes.getNam();
			if (nam == null)
				continue;

			Optional<VatTuNhapRes> optionalQdVt = qdRes.stream().filter(t -> nam.equals(t.getNam())).findFirst();
			if (!optionalQdVt.isPresent())
				continue;

			VatTuNhapRes qdVtRes = optionalQdVt.get();
			VatTuNhapRes dcXtn = new VatTuNhapRes();
			dcXtn.setNam(nam);
			Double soLuong = Optional.ofNullable(dcVtRes.getSoLuong()).orElse(0D) - Optional.ofNullable(qdVtRes.getSoLuong()).orElse(0D);
			dcXtn.setSoLuong(soLuong);
			dcXtn.setVatTuId(dcVtRes.getVatTuId());
			dcXtns.add(dcXtn);
		}
		return dcXtns;
	}
}
