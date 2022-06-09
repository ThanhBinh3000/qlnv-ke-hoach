package com.tcdt.qlnvkhoach.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.*;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachNamStatusEnum;
import com.tcdt.qlnvkhoach.enums.DxDcKeHoachNamStatusTongCucEnum;
import com.tcdt.qlnvkhoach.query.dto.VatTuNhapQueryDTO;
import com.tcdt.qlnvkhoach.repository.*;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRepository;
import com.tcdt.qlnvkhoach.request.DeleteReq;
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
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamCount;
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

	@Autowired
	private DxDcKeHoachNamRepository dxDcKeHoachNamRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception {
		ChiTieuKeHoachNam exist = this.existCtkhn(null, req.getNamKeHoach(), ChiTieuKeHoachEnum.QD.getValue(), req.getSoQuyetDinh());
		if (exist != null)
			throw new Exception("Chỉ tiêu kế hoạch năm đã tồn tại");

		this.validateCreateCtkhnRequest(req, ChiTieuKeHoachEnum.QD.getValue());
		return this.create(req, ChiTieuKeHoachEnum.QD.getValue(), null, req.getNamKeHoach());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes createQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {
		Long qdGocId = req.getQdGocId();
		Optional<ChiTieuKeHoachNam> optionalQdGoc = chiTieuKeHoachNamRepository.findById(qdGocId);
		if (!optionalQdGoc.isPresent())
			throw new Exception("Không tìm thấy quyết định gốc.");

		ChiTieuKeHoachNam qdGoc = optionalQdGoc.get();
		if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(qdGoc.getTrangThai())) {
			throw new Exception("Không thể điều chỉnh quyết định chưa duyệt");
		}
		chiTieuKeHoachNamRepository.save(qdGoc);

		ChiTieuKeHoachNam exist = this.existCtkhn(null, req.getQdDc().getNamKeHoach(), ChiTieuKeHoachEnum.QD_DC.getValue(), req.getQdDc().getSoQuyetDinh());
		if (exist != null)
			throw new Exception("Quyết định diều chỉnh chỉ tiêu kế hoạch năm đã tồn tại");

		this.validateCreateCtkhnRequest(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue());
		ChiTieuKeHoachNamRes qdDc = this.create(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue(), qdGoc.getId(), qdGoc.getNamKeHoach());
		ChiTieuKeHoachNamRes qd = this.detailQd(qdGocId);

		this.setData(qdDc, qd);
		return qdDc;
	}

	private ChiTieuKeHoachNamRes create(ChiTieuKeHoachNamReq req, String loaiQd, Long qdGocId, Integer namKeHoach) throws Exception {
		if (req == null)
			return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		ChiTieuKeHoachNam chiTieuKeHoachNam = new ChiTieuKeHoachNam();
		BeanUtils.copyProperties(req, chiTieuKeHoachNam, "id");
		chiTieuKeHoachNam.setNgayTao(LocalDate.now());
		chiTieuKeHoachNam.setNguoiTaoId(userInfo.getId());
		chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatusEnum.DU_THAO.getId());
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

		List<FileDinhKemChung> canCus = fileDinhKemService.saveListFileDinhKem(req.getCanCus(), ctkhnId, ChiTieuKeHoachNam.FILE_DINH_KEM_DATA_TYPE_CAN_CU);
		chiTieuKeHoachNam.setCanCus(canCus);
		return this.buildDetailResponse(chiTieuKeHoachNam, namKeHoach);
	}

	private List<VatTuNhapQueryDTO> getKeHoachVatTuThietBiCacNamTruoc(List<Long> vatTuIdList, Integer nameKeHoach) {
		int tuNam = nameKeHoach - 3;
		int denNam = nameKeHoach - 1;
		List<VatTuNhapQueryDTO> results = keHoachVatTuRepository.findKeHoachVatTuCacNamTruocByVatTuId(vatTuIdList, nameKeHoach - 3, nameKeHoach - 1, ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId(), ChiTieuKeHoachEnum.QD.getValue(), true);
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
		this.validateCreateCtkhnRequest(req, ChiTieuKeHoachEnum.QD.getValue());
		return this.update(req, ChiTieuKeHoachEnum.QD.getValue());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {
		this.validateCreateCtkhnRequest(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue());

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
		Integer namKeHoach = ctkhn.getNamKeHoach();
		if (ctkhn.getQdGocId() != null) {
			ChiTieuKeHoachNam qdGoc = this.getChiTieuKeHoachNam(ctkhn.getQdGocId());
			namKeHoach = qdGoc.getNamKeHoach();
		}
		Long ctkhnId = ctkhn.getId();

		ChiTieuKeHoachNam exist = this.existCtkhn(ctkhn, req.getNamKeHoach(), loaiQd, req.getSoQuyetDinh());
		if (exist != null && !exist.getId().equals(ctkhnId))
			throw new Exception(ChiTieuKeHoachEnum.QD.getValue().equals(loaiQd) ? "Chỉ tiêu kế hoạch năm đã tồn tại"
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

		List<FileDinhKemChung> canCus = fileDinhKemService.saveListFileDinhKem(req.getCanCus(), ctkhnId, ChiTieuKeHoachNam.FILE_DINH_KEM_DATA_TYPE_CAN_CU);
		ctkhn.setCanCus(canCus);
		return this.buildDetailResponse(ctkhn, namKeHoach);
	}

	private KeHoachLuongThucMuoi saveKeHoachLuongThuc(KeHoachLuongThucDuTruReq khltReq, Long ctkhnId, String maVatTu,
													  Long vatuId, Double ntnSoLuong, Long id, Map<Long, KeHoachLuongThucMuoi> mapKhltm) throws Exception {

		KeHoachLuongThucMuoi keHoachLuongThucMuoi = new KeHoachLuongThucMuoi();
		keHoachLuongThucMuoi.setTrangThai(Constants.MOI_TAO);
		if (id != null && id > 0) {
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

			if (id != null && id > 0) {
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
		if (id != null && id > 0) {
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
			if (id != null && id > 0) {
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

		if (ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định đã ban hành");
		} else if (ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định trình duyệt");
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

		if (ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định đã ban hành");
		} else if (ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(item.getTrangThai())) {
			throw new Exception("Không thể xóa quyết định trình duyệt");
		}

		return this.delete(item);
	}

	public boolean delete(ChiTieuKeHoachNam chiTieuKeHoachNam) throws Exception {
		List<KeHoachLuongThucMuoi> keHoachLuongThucMuoiList = keHoachLuongThucMuoiRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuoiList = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdIn(keHoachLuongThucMuoiList.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toList()));
		if (!CollectionUtils.isEmpty(keHoachXuatLuongThucMuoiList))
			keHoachXuatLuongThucMuoiRepository.deleteAll(keHoachXuatLuongThucMuoiList);
		if (!CollectionUtils.isEmpty(keHoachLuongThucMuoiList))
			keHoachLuongThucMuoiRepository.deleteAll(keHoachLuongThucMuoiList);

		List<KeHoachVatTu> keHoachVatTuList = keHoachVatTuRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		if (!CollectionUtils.isEmpty(keHoachVatTuList))
			keHoachVatTuRepository.deleteAll(keHoachVatTuList);

		chiTieuKeHoachNamRepository.delete(chiTieuKeHoachNam);
		fileDinhKemService.delete(chiTieuKeHoachNam.getId(), Lists.newArrayList(ChiTieuKeHoachNam.TABLE_NAME, ChiTieuKeHoachNam.FILE_DINH_KEM_DATA_TYPE_CAN_CU));
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
		ChiTieuKeHoachNam chiTieuKeHoachNam = this.getChiTieuKeHoachNam(qdDc.getQdGocId());
		ChiTieuKeHoachNamRes qd = this.detail(chiTieuKeHoachNam.getId());
		this.setData(qdDc, qd);
		return qdDc;
	}

	public ChiTieuKeHoachNamRes detail(Long id) throws Exception {
		Optional<ChiTieuKeHoachNam> optional = chiTieuKeHoachNamRepository.findById(id);
		if (!optional.isPresent())
			throw new Exception("Chỉ tiêu kế hoạch năm không tồn tại");

		ChiTieuKeHoachNam ctkhn = optional.get();
		Integer namKeHoach = ctkhn.getNamKeHoach();
		if (ctkhn.getQdGocId() != null) {
			ChiTieuKeHoachNam qdGoc = this.getChiTieuKeHoachNam(ctkhn.getQdGocId());
			namKeHoach = qdGoc.getNamKeHoach();
		}

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

		ctkhn.setFileDinhKems(fileDinhKemService.search(ctkhn.getId(), Collections.singleton(ChiTieuKeHoachNam.TABLE_NAME)));

		if (ChiTieuKeHoachEnum.QD.getValue().equals(ctkhn.getLoaiQuyetDinh())) {
			ctkhn.setCanCus(fileDinhKemService.search(ctkhn.getId(), Collections.singleton(ChiTieuKeHoachNam.FILE_DINH_KEM_DATA_TYPE_CAN_CU)));
		}
		ChiTieuKeHoachNamRes response = buildDetailResponse(ctkhn, namKeHoach);
		addEmptyDataToExport(response, namKeHoach);
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

	private void addEmptyDataToExport(ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, Integer namKeHoachChiTieu) {
		//Luong thuc
		for (KeHoachLuongThucDuTruRes keHoachLuongThucDuTruRes : chiTieuKeHoachNamRes.getKhLuongThuc()) {
			//Tồn kho đầu năm
			List<Integer> namTkdnThoc = keHoachLuongThucDuTruRes.getTkdnThoc()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(namTkdnThoc, namKeHoachChiTieu,
					keHoachLuongThucDuTruRes.getTkdnThoc(), Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_THOC);


			List<Integer> namTkdnGao = keHoachLuongThucDuTruRes.getTkdnGao()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(namTkdnGao, namKeHoachChiTieu,
					keHoachLuongThucDuTruRes.getTkdnGao(), Constants.ChiTieuKeHoachNamExport.SO_NAM_LUU_KHO_GAO);
		}

		//Muối
		for (KeHoachMuoiDuTruRes keHoachMuoiDuTruRes : chiTieuKeHoachNamRes.getKhMuoiDuTru()) {
			//Tồn kho đầu năm
			List<Integer> tkdnMuoi = keHoachMuoiDuTruRes.getTkdnMuoi()
					.stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());

			this.addEmptyVatTuNhap(tkdnMuoi, namKeHoachChiTieu, keHoachMuoiDuTruRes.getTkdnMuoi(),
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
		Integer namKeHoach = null;
		if (ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equalsIgnoreCase(dc.getTrangThai())) {
			Optional<ChiTieuKeHoachNam> qdGocOptional = chiTieuKeHoachNamRepository.findById(dc.getQdGocId());
			if (!qdGocOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu.");

			ChiTieuKeHoachNam qdGoc = qdGocOptional.get();

			// Duyệt điều chỉnh -> tạo quyết đinh mới từ quyết định gốc
			this.mergeQdDcAndQd(dc, qdGoc);
			namKeHoach = qdGoc.getNamKeHoach();
		}

		// Update status de xuat dieu chinh
		this.updateStatusDxDc(dc, namKeHoach);
		return true;
	}

	private void updateStatusDxDc(ChiTieuKeHoachNam dc, Integer namKeHoach) {
		if (ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equalsIgnoreCase(dc.getTrangThai()) ||
				ChiTieuKeHoachNamStatusEnum.TU_CHOI.getId().equalsIgnoreCase(dc.getTrangThai())) {

			this.retrieveDataChiTieuKeHoachNam(dc);
			List<KeHoachVatTu> khVts = dc.getKhVatTuList();
			List<KeHoachLuongThucMuoi> khLts = dc.getKhLuongThucList();
			List<KeHoachLuongThucMuoi> khMuois = dc.getKhMuoiList();
			Set<String> maDvis = khVts.stream().map(KeHoachVatTu::getMaDvi).collect(Collectors.toSet());
			maDvis.addAll(khLts.stream().map(KeHoachLuongThucMuoi::getMaDvi).collect(Collectors.toSet()));
			maDvis.addAll(khMuois.stream().map(KeHoachLuongThucMuoi::getMaDvi).collect(Collectors.toSet()));
			if (CollectionUtils.isEmpty(maDvis))
				return;

			List<DxDcKeHoachNam> dxDcs = dxDcKeHoachNamRepository.findByMaDviInAndNamKeHoachAndTrangThaiTongCuc(maDvis, namKeHoach, DxDcKeHoachNamStatusTongCucEnum.CHUA_XU_LY.getId());
			if (CollectionUtils.isEmpty(dxDcs))
				return;

			dxDcs.forEach(dx -> {
				if (ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equalsIgnoreCase(dc.getTrangThai())) {
					dx.setTrangThaiTongCuc(DxDcKeHoachNamStatusTongCucEnum.DA_XU_LY.getId());
				} else {
					dx.setTrangThaiTongCuc(DxDcKeHoachNamStatusTongCucEnum.TU_CHOI.getId());
				}
			});

			dxDcKeHoachNamRepository.saveAll(dxDcs);
		}
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

		List<ChiTieuKeHoachNam> lastestExists = chiTieuKeHoachNamRepository.findByNamKeHoachAndLastestAndLoaiQuyetDinh(qdGoc.getNamKeHoach(), true, ChiTieuKeHoachEnum.QD.getValue());
		lastestExists.forEach(l -> {
			l.setLastest(false);
			chiTieuKeHoachNamRepository.save(l);
		});

		ChiTieuKeHoachNam lastest = ChiTieuKeHoachNam.builder()
				.namKeHoach(qdGoc.getNamKeHoach())
				.maDvi(qdGoc.getMaDvi())
				.capDvi(qdGoc.getCapDvi())
				.ngayKy(qdGoc.getNgayKy())
				.ngayHieuLuc(qdGoc.getNgayHieuLuc())
				.soQuyetDinh(qdGoc.getSoQuyetDinh())
				.trichYeu(qdGoc.getTrichYeu())
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

		// file dinh kem, can cu
		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.search(qdGoc.getId(), Lists.newArrayList(ChiTieuKeHoachNam.TABLE_NAME, ChiTieuKeHoachNam.FILE_DINH_KEM_DATA_TYPE_CAN_CU));
		for (FileDinhKemChung fileDinhKemChung : fileDinhKems) {
			FileDinhKemChung cloneFdk = new FileDinhKemChung();
			BeanUtils.copyProperties(fileDinhKemChung, cloneFdk, "id", "dataId");
			cloneFdk.setDataId(lastest.getId());
			fileDinhKemService.saveFileDinhKems(Collections.singletonList(cloneFdk));
		}

	}

	@Override
	public List<KeHoachLuongThucMuoi> retrieveKhltm(ChiTieuKeHoachNam chiTieuKeHoachNam) {
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
		if (ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatusEnum.DU_THAO.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId());
			chiTieuKeHoachNam.setNguoiGuiDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayGuiDuyet(LocalDate.now());

		} else if (ChiTieuKeHoachNamStatusEnum.LANH_DAO_DUYET.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(trangThai))
				return false;
			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatusEnum.LANH_DAO_DUYET.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
		} else if (ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatusEnum.LANH_DAO_DUYET.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
		} else if (ChiTieuKeHoachNamStatusEnum.TU_CHOI.getId().equals(req.getTrangThai())) {
			if (!ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(trangThai))
				return false;

			chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatusEnum.TU_CHOI.getId());
			chiTieuKeHoachNam.setNguoiPheDuyetId(userInfo.getId());
			chiTieuKeHoachNam.setNgayPheDuyet(LocalDate.now());
			chiTieuKeHoachNam.setLyDoTuChoi(req.getLyDoTuChoi());
		}  else {
			throw new Exception("Bad request.");
		}

		chiTieuKeHoachNamRepository.save(chiTieuKeHoachNam);
		return true;
	}

	@Override
	public ChiTieuKeHoachNamRes buildDetailResponse(ChiTieuKeHoachNam chiTieuKeHoachNam, Integer namKeHoachChiTieu) throws Exception {
		List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAll());

		ChiTieuKeHoachNamRes response = new ChiTieuKeHoachNamRes();
		response.setId(chiTieuKeHoachNam.getId());
		response.setNamKeHoach(chiTieuKeHoachNam.getNamKeHoach());
		response.setSoQuyetDinh(chiTieuKeHoachNam.getSoQuyetDinh());
		response.setNgayHieuLuc(chiTieuKeHoachNam.getNgayHieuLuc());
		response.setNgayKy(chiTieuKeHoachNam.getNgayKy());
		response.setTrangThai(chiTieuKeHoachNam.getTrangThai());
		response.setTenTrangThai(ChiTieuKeHoachNamStatusEnum.getTenById(chiTieuKeHoachNam.getTrangThai()));
		response.setTrangThaiDuyet(ChiTieuKeHoachNamStatusEnum.getTrangThaiDuyetById(chiTieuKeHoachNam.getTrangThai()));
		response.setTrichYeu(chiTieuKeHoachNam.getTrichYeu());
		response.setQdGocId(chiTieuKeHoachNam.getQdGocId());
		response.setGhiChu(chiTieuKeHoachNam.getGhiChu());
		response.setCanCus(chiTieuKeHoachNam.getCanCus());
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

		List<TonKhoDauNamRes> tonKhoDauNamResList = this.getTonKhoDauNam(maDviLtm, maVatTuLtm, namKeHoachChiTieu, dmDonviList);
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

		List<VatTuNhapQueryDTO> vatTuNhapQueryDTOList = this.getKeHoachVatTuThietBiCacNamTruoc(vatTuIdList, namKeHoachChiTieu);
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

	private void validateCreateCtkhnRequest(ChiTieuKeHoachNamReq req, String loaiQd) throws Exception {

		if (ChiTieuKeHoachEnum.QD.getValue().equals(loaiQd) && CollectionUtils.isEmpty(req.getCanCus())) {
			throw new Exception("Căn cứ không được để trống");
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

		this.prepareSearchReq(req, userInfo, req.getCapDvi());
		req.setLoaiQuyetDinh(ChiTieuKeHoachEnum.QD.getValue());

		return chiTieuKeHoachNamRepository.search(req);
	}

	@Override
	public Page<ChiTieuKeHoachNamRes> searchQdDc(SearchChiTieuKeHoachNamReq req) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		this.prepareSearchReq(req, userInfo, req.getCapDvi());
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
		if (optional.get().getQdGocId() != null) {
			ChiTieuKeHoachNam qdGoc = this.getChiTieuKeHoachNam(optional.get().getQdGocId());
			nam = qdGoc.getNamKeHoach();
		}

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

	private ChiTieuKeHoachNam existCtkhn(ChiTieuKeHoachNam update, Integer namKeHoach, String loaiQd, String soQd) throws Exception {
		if (update == null || !update.getSoQuyetDinh().equalsIgnoreCase(soQd)) {
			ChiTieuKeHoachNam exist = chiTieuKeHoachNamRepository.findFirstBySoQuyetDinhAndLoaiQuyetDinhAndLastestIsTrue(soQd, loaiQd);
			if (exist != null)
				throw new Exception("Số quyết định " + soQd + " đã tồn tại");
		}

		if (ChiTieuKeHoachEnum.QD.getValue().equals(loaiQd)) {
			return chiTieuKeHoachNamRepository.findByNamKeHoachAndLastestAndLoaiQuyetDinh(namKeHoach, true, loaiQd)
					.stream().filter(c -> !ChiTieuKeHoachNamStatusEnum.TU_CHOI.getId().equalsIgnoreCase(c.getTrangThai()))
					.findFirst().orElse(null);
		} else {
			return chiTieuKeHoachNamRepository.findByNamKeHoachAndLastestAndLoaiQuyetDinh(namKeHoach, true, loaiQd)
					.stream().filter(c -> ChiTieuKeHoachNamStatusEnum.DU_THAO.getId().equalsIgnoreCase(c.getTrangThai())
							|| ChiTieuKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equalsIgnoreCase(c.getTrangThai())
							|| ChiTieuKeHoachNamStatusEnum.LANH_DAO_DUYET.getId().equalsIgnoreCase(c.getTrangThai()))
					.findFirst().orElse(null);
		}
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

			KeHoachVatTuRes qdRes = optionalQd.orElseGet(KeHoachVatTuRes::new);

			List<VatTuThietBiRes> dcVatTuThietBi = dcRes.getVatTuThietBi();
			List<VatTuThietBiRes> qdVatTuThietBi = qdRes.getVatTuThietBi();

			for (VatTuThietBiRes dcVt : dcVatTuThietBi) {
				String maVatTu = dcVt.getMaVatTu();
				if (StringUtils.isEmpty(maVatTu))
					continue;

				Optional<VatTuThietBiRes> optionalVtQd = qdVatTuThietBi.stream()
						.filter(l -> l.getMaVatTu().equals(maVatTu))
						.findFirst();

				VatTuThietBiRes qdVt = optionalVtQd.orElseGet(VatTuThietBiRes::new);

				// Truoc dieu chinh Nhap Trong Nam
				dcVt.setTdcNhapTrongNam(Optional.ofNullable(qdVt.getNhapTrongNam()).orElse(0D));
				dcVt.setTdcTongNhap(Optional.ofNullable(qdVt.getTongNhap()).orElse(0D));
				dcVt.setTdcTongCacNamTruoc(Optional.ofNullable(qdVt.getTdcTongCacNamTruoc()).orElse(dcVt.getTongCacNamTruoc()));
				List<VatTuNhapRes> cacNamTruoc = qdVt.getCacNamTruoc();
				if (CollectionUtils.isEmpty(cacNamTruoc)) {
					cacNamTruoc = new ArrayList<>(dcVt.getCacNamTruoc());
				}
				dcVt.setTdcCacNamTruoc(cacNamTruoc);

				// Sau dieu chinh Nhap Trong Nam
				dcVt.setSdcNhapTrongNam(dcVt.getNhapTrongNam());
				dcVt.setSdcTongNhap(dcVt.getTongNhap());
				dcVt.setSdcTongCacNamTruoc(dcVt.getTongCacNamTruoc());
				dcVt.setSdcCacNamTruoc(dcVt.getCacNamTruoc());

				// Dieu chinh
				Double dcNhapTrongNam = Optional.ofNullable(dcVt.getSdcNhapTrongNam()).orElse(0D) - Optional.ofNullable(dcVt.getTdcNhapTrongNam()).orElse(0D);
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

			KeHoachMuoiDuTruRes qdRes = optionalQd.orElseGet(KeHoachMuoiDuTruRes::new);

			// Nhap Trong Nam
			dcRes.setTdcNtnTongSoMuoi(Optional.ofNullable(qdRes.getNtnTongSoMuoi()).orElse(0D));
			dcRes.setSdcNtnTongSoMuoi(Optional.ofNullable(dcRes.getNtnTongSoMuoi()).orElse(0D));
			Double dcNtnTongSoMuoi = Optional.ofNullable(dcRes.getSdcNtnTongSoMuoi()).orElse(0D) - Optional.ofNullable(qdRes.getNtnTongSoMuoi()).orElse(0D);
			dcRes.setDcNtnTongSoMuoi(dcNtnTongSoMuoi);

			// Truoc dieu chinh Xuat Trong Nam
			dcRes.setTdcXtnTongSoMuoi(Optional.ofNullable(qdRes.getXtnTongSoMuoi()).orElse(0D));

			List<VatTuNhapRes> tdcXtnMuoi = qdRes.getXtnMuoi();
			if (CollectionUtils.isEmpty(tdcXtnMuoi)) {
				tdcXtnMuoi = new ArrayList<>(dcRes.getXtnMuoi());
				tdcXtnMuoi.forEach(thoc -> {
					thoc.setSoLuong(0D);
				});
			}
			dcRes.setTdcXtnMuoi(tdcXtnMuoi);

			// Sau dieu chinh Xuat Trong Nam
			dcRes.setSdcXtnTongSoMuoi(Optional.ofNullable(dcRes.getXtnTongSoMuoi()).orElse(0D));
			dcRes.setSdcXtnMuoi(dcRes.getXtnMuoi());

			// Dieu chinh Xuat Trong Nam
			dcRes.setDcXtnMuoi(this.getDcXtnLuongThucMuoi(dcRes.getSdcXtnMuoi(), dcRes.getTdcXtnMuoi()));
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

			KeHoachLuongThucDuTruRes qdRes = optionalQd.orElseGet(KeHoachLuongThucDuTruRes::new);

			// Truoc dieu chinh Nhap Trong Nam
			dcRes.setTdcNtnTongSoQuyThoc(Optional.ofNullable(qdRes.getNtnTongSoQuyThoc()).orElse(0D));
			dcRes.setTdcNtnGao(Optional.ofNullable(qdRes.getNtnGao()).orElse(0D));
			dcRes.setTdcNtnThoc(Optional.ofNullable(qdRes.getNtnThoc()).orElse(0D));

			// Sau dieu chinh Nhap Trong Nam
			dcRes.setSdcNtnTongSoQuyThoc(Optional.ofNullable(dcRes.getNtnTongSoQuyThoc()).orElse(0D));
			dcRes.setSdcNtnThoc(Optional.ofNullable(dcRes.getNtnThoc()).orElse(0D));
			dcRes.setSdcNtnGao(Optional.ofNullable(dcRes.getNtnGao()).orElse(0D));

			// Dieu chinh Nhap Trong Nam
			Double dcNtnGao = Optional.ofNullable(dcRes.getSdcNtnGao()).orElse(0D) - Optional.ofNullable(qdRes.getNtnGao()).orElse(0D);
			Double dcNtnThoc = Optional.ofNullable(dcRes.getSdcNtnThoc()).orElse(0D) - Optional.ofNullable(qdRes.getNtnThoc()).orElse(0D);
			dcRes.setDcNtnGao(dcNtnGao);
			dcRes.setDcNtnThoc(dcNtnThoc);

			// Truoc dieu chinh Xuat Trong Nam
			dcRes.setTdcXtnTongSoQuyThoc(Optional.ofNullable(qdRes.getXtnTongSoQuyThoc()).orElse(0D));
			List<VatTuNhapRes> tdcXtnThoc = qdRes.getXtnThoc();
			if (CollectionUtils.isEmpty(tdcXtnThoc)) {
				tdcXtnThoc = new ArrayList<>(dcRes.getXtnThoc());
				tdcXtnThoc.forEach(thoc -> {
					thoc.setSoLuong(0D);
				});
			}

			List<VatTuNhapRes> tdcXtnGao = qdRes.getXtnGao();
			if (CollectionUtils.isEmpty(tdcXtnGao)) {
				tdcXtnGao = new ArrayList<>(dcRes.getXtnGao());
				tdcXtnGao.forEach(thoc -> {
					thoc.setSoLuong(0D);
				});
			}

			dcRes.setTdcXtnThoc(tdcXtnThoc);
			dcRes.setTdcXtnGao(tdcXtnGao);
			dcRes.setTdcXtnTongThoc(Optional.ofNullable(qdRes.getXtnTongThoc()).orElse(0D));
			dcRes.setTdcXtnTongGao(Optional.ofNullable(qdRes.getXtnTongGao()).orElse(0D));

			// Sau dieu chinh Xuat Trong Nam
			dcRes.setSdcXtnTongSoQuyThoc(dcRes.getXtnTongSoQuyThoc());
			dcRes.setSdcXtnTongThoc(dcRes.getXtnTongThoc());
			dcRes.setSdcXtnTongGao(dcRes.getXtnTongGao());
			dcRes.setSdcXtnThoc(dcRes.getXtnThoc());
			dcRes.setSdcXtnGao(dcRes.getXtnGao());

			dcRes.setDcXtnThoc(this.getDcXtnLuongThucMuoi(dcRes.getSdcXtnThoc(), dcRes.getTdcXtnThoc()));
			dcRes.setDcXtnGao(this.getDcXtnLuongThucMuoi(dcRes.getSdcXtnGao(), dcRes.getTdcXtnGao()));

		}
	}

	private List<VatTuNhapRes> getDcXtnLuongThucMuoi(List<VatTuNhapRes> sdcRes, List<VatTuNhapRes> tdcRes) {
		List<VatTuNhapRes> dcXtns = new ArrayList<>();
		for (VatTuNhapRes dcVtRes : sdcRes) {
			Integer nam = dcVtRes.getNam();
			if (nam == null)
				continue;

			Optional<VatTuNhapRes> optionalQdVt = tdcRes.stream().filter(t -> nam.equals(t.getNam())).findFirst();
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

	@Override
	public ChiTieuKeHoachNam getChiTieuKeHoachNam(Long id) throws Exception {
		Optional<ChiTieuKeHoachNam> optionalKhn = chiTieuKeHoachNamRepository.findById(id);
		if (!optionalKhn.isPresent())
			throw new Exception("Không tồn tại chỉ tiêu kế hoạch năm.");

		return optionalKhn.get();
	}

	@Override
	public void retrieveDataChiTieuKeHoachNam(ChiTieuKeHoachNam chiTieuKeHoachNam) {
		List<KeHoachLuongThucMuoi> keHoachLuongThucMuois = this.retrieveKhltm(chiTieuKeHoachNam);

		List<KeHoachLuongThucMuoi> keHoachLuongThucList = keHoachLuongThucMuois.stream()
				.filter(k -> Constants.LuongThucMuoiConst.GAO_MA_VT.equals(k.getMaVatTu())
						|| Constants.LuongThucMuoiConst.THOC_MA_VT.equals(k.getMaVatTu()))
				.collect(Collectors.toList());

		List<KeHoachLuongThucMuoi> keHoachMuoiList = keHoachLuongThucMuois.stream()
				.filter(k -> Constants.LuongThucMuoiConst.MUOI_MA_VT.equals(k.getMaVatTu()))
				.collect(Collectors.toList());
		chiTieuKeHoachNam.setKhLuongThucList(keHoachLuongThucList);
		chiTieuKeHoachNam.setKhMuoiList(keHoachMuoiList);

		List<KeHoachVatTu> khvtList = keHoachVatTuRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		chiTieuKeHoachNam.setKhVatTuList(khvtList);
	}

	@Override
	public ChiTieuKeHoachNamCount countCtkhn(String loaiQd) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		ChiTieuKeHoachNamCount count = new ChiTieuKeHoachNamCount();
		String capDvi = userInfo.getCapDvi();
		if (Constants.TONG_CUC.equalsIgnoreCase(capDvi)) {
			count.setChiTieuKeHoachNamTongCuc((long) this.countCtkh(loaiQd, null, userInfo));
			count.setChiTieuKeHoachNamCuc((long)this.countCtkh(loaiQd, Constants.CUC_KHU_VUC, userInfo));
		} else if (Constants.CUC_KHU_VUC.equalsIgnoreCase(capDvi)) {
			count.setChiTieuKeHoachNamCuc((long) this.countCtkh(loaiQd, null, userInfo));
			count.setChiTieuKeHoachNamTongCuc((long)this.countCtkh(loaiQd, Constants.TONG_CUC, userInfo));
		} else if (Constants.CHI_CUC.equalsIgnoreCase(capDvi)) {
			count.setChiTieuKeHoachNamCuc((long) this.countCtkh(loaiQd, null, userInfo));
		}
		return count;
	}

	private Integer countCtkh(String loaiQd, String capDviReq, UserInfo userInfo) {
		SearchChiTieuKeHoachNamReq req = new SearchChiTieuKeHoachNamReq();
		this.prepareSearchReq(req, userInfo, capDviReq);
		req.setLoaiQuyetDinh(loaiQd);
		return chiTieuKeHoachNamRepository.countCtkhn(req);
	}

	@Override
	public void prepareSearchReq(SearchChiTieuKeHoachNamReq req, UserInfo userInfo, String capDviReq) {
		String userCapDvi = userInfo.getCapDvi();
		req.setCapDvi(StringUtils.hasText(capDviReq) ? capDviReq : userCapDvi);
		boolean cucOrChiCuc = Constants.CUC_KHU_VUC.equals(userCapDvi) || Constants.CHI_CUC.equals(userCapDvi);

		if (StringUtils.hasText(capDviReq) && !capDviReq.equalsIgnoreCase(userCapDvi)) {
			req.setTrangThai(ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId());
			if (cucOrChiCuc) {
				req.setMaDvi(userInfo.getDvql());
			}
		} else if (Constants.CUC_KHU_VUC.equals(userCapDvi)) {
			req.setDvql(userInfo.getDvql());
		} else if (Constants.CHI_CUC.equals(userCapDvi)) {
			req.setTrangThai(ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId());
			req.setMaDvi(userInfo.getDvql());
			req.setCapDvi(null);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public boolean deleteMultiple(DeleteReq req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		if (CollectionUtils.isEmpty(req.getIds()))
			return false;

		List<KeHoachLuongThucMuoi> keHoachLuongThucMuoiList = keHoachLuongThucMuoiRepository.findByCtkhnIdIn(req.getIds());
		List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuoiList = keHoachXuatLuongThucMuoiRepository.findByKeHoachIdIn(keHoachLuongThucMuoiList.stream().map(KeHoachLuongThucMuoi::getId).collect(Collectors.toList()));
		if (!CollectionUtils.isEmpty(keHoachXuatLuongThucMuoiList))
			keHoachXuatLuongThucMuoiRepository.deleteAll(keHoachXuatLuongThucMuoiList);
		if (!CollectionUtils.isEmpty(keHoachLuongThucMuoiList))
			keHoachLuongThucMuoiRepository.deleteAll(keHoachLuongThucMuoiList);

		keHoachVatTuRepository.deleteByCtkhnIdIn(req.getIds());
		chiTieuKeHoachNamRepository.deleteByIdIn(req.getIds());
		fileDinhKemService.deleteMultiple(req.getIds(), Lists.newArrayList(ChiTieuKeHoachNam.TABLE_NAME, ChiTieuKeHoachNam.FILE_DINH_KEM_DATA_TYPE_CAN_CU));
		return true;
	}

	@Override
	public ChiTieuKeHoachNam getChiTieuDxKhLcnt(Long namKh) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null){
			throw new Exception("Bad request.");
		}
		ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamRepository.getChiTieuDxKhLcnt(namKh,userInfo.getDvql());

		List<KeHoachLuongThucMuoi> keHoachLuongThucMuois = keHoachLuongThucMuoiRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		List<KeHoachVatTu>  vatTu = keHoachVatTuRepository.findByCtkhnId(chiTieuKeHoachNam.getId());

		chiTieuKeHoachNam.setKhLuongThucList(keHoachLuongThucMuois.stream().filter(kh -> Constants.LuongThucMuoiConst.GAO_ID.equals(kh.getVatTuId()) || Constants.LuongThucMuoiConst.THOC_ID.equals(kh.getVatTuId())).collect(Collectors.toList()));
		chiTieuKeHoachNam.setKhMuoiList(keHoachLuongThucMuois.stream().filter(kh -> Constants.LuongThucMuoiConst.MUOI_ID.equals(kh.getVatTuId())).collect(Collectors.toList()));
		chiTieuKeHoachNam.setKhVatTuList(vatTu);

		return chiTieuKeHoachNam;
	}
}
