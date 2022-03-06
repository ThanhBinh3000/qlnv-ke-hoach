package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoachphi.entities.KeHoachLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KeHoachVatTu;
import com.tcdt.qlnvkhoachphi.entities.KeHoachXuatLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KtTrangthaiHienthoi;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoachphi.query.dto.VatTuNhapQueryDTO;
import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachVatTuRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachXuatLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KtTrangthaiHienthoiRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.*;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.QdDcChiTieuKeHoachRes;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChiTieuKeHoachNamServiceImpl implements ChiTieuKeHoachNamService {
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

	public static final Long THOC_ID = 3L;
	public static final String THOC_MA_VT = "010101";
	public static final Long GAO_ID = 4L;
	public static final String GAO_MA_VT = "010103";
	public static final Long MUOI_ID = 481L;
	public static final String MUOI_MA_VT = "04";

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception {
		ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamRepository.findByNamKeHoachAndLastest(req.getNamKeHoach(), true);
		if (chiTieuKeHoachNam != null)
			throw new Exception("Chỉ tiêu kế hoạch năm đã tồn tại");

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
		qdGoc.setLastest(false);
		chiTieuKeHoachNamRepository.save(qdGoc);

		ChiTieuKeHoachNamRes qdDc = this.create(req.getQdDc(), ChiTieuKeHoachEnum.QD_DC.getValue(), qdGoc.getId());
		ChiTieuKeHoachNamRes qd = this.create(req.getQd(), ChiTieuKeHoachEnum.QD.getValue(), qdGoc.getId());
		QdDcChiTieuKeHoachRes response = new QdDcChiTieuKeHoachRes();
		response.setQdDc(qdDc);
		response.setQd(qd);
		return response;
	}

	private ChiTieuKeHoachNamRes create(ChiTieuKeHoachNamReq req, String loaiQd, Long qdGocId) throws Exception {
		if (req == null)
			return  null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Integer namKeHoach = req.getNamKeHoach();
		ChiTieuKeHoachNam chiTieuKeHoachNam = new ChiTieuKeHoachNam();
		BeanUtils.copyProperties(req, chiTieuKeHoachNam);
		chiTieuKeHoachNam.setNgayTao(LocalDate.now());
		chiTieuKeHoachNam.setNguoiTaoId(userInfo.getId());
		chiTieuKeHoachNam.setTrangThai(ChiTieuKeHoachNamStatus.MOI_TAO.getId());
		chiTieuKeHoachNam.setDonViId(userInfo.getDvql());
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
		return keHoachVatTuRepository.findKeHoachVatTuCacNamTruocByVatTuId(vatTuIdList, nameKeHoach -3, nameKeHoach - 1);
	}

	private List<TonKhoDauNamRes> getTonKhoDauNam(List<String> maDonViList, List<String> vatTuIdList, Integer namKeHoach) {
		List<String> namList = new ArrayList<>();
		for (int i = 1; i <= 3; i++ ) {
			namList.add(String.valueOf(namKeHoach - i));
		}
		List<KtTrangthaiHienthoi> trangthaiHienthoiList = ktTrangthaiHienthoiRepository.findAllByMaDonViInAndMaVthhInAndNamIn(maDonViList, vatTuIdList, namList);

		List<TonKhoDauNamRes> tonKhoDauNamResList = new ArrayList<>();
		for (KtTrangthaiHienthoi trangthaiHienthoi : trangthaiHienthoiList) {
			TonKhoDauNamRes res = tonKhoDauNamResList.stream().filter(t -> trangthaiHienthoi.getMaDonVi().equals(t.getMaDonVi()) && trangthaiHienthoi.getMaVthh().equals(t.getMaVatTu())).findFirst().orElse(null);
			if (res == null) {
				res = new TonKhoDauNamRes();
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
		return this.update(req);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public QdDcChiTieuKeHoachRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception {

		ChiTieuKeHoachNamRes qdDc = this.update(req.getQdDc());
		ChiTieuKeHoachNamRes qd = this.update(req.getQd());
		QdDcChiTieuKeHoachRes response = new QdDcChiTieuKeHoachRes();
		response.setQdDc(qdDc);
		response.setQd(qd);
		return response;
	}

	public ChiTieuKeHoachNamRes update(ChiTieuKeHoachNamReq req) throws Exception {
		if (req == null)
			return  null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<ChiTieuKeHoachNam> optional = chiTieuKeHoachNamRepository.findById(req.getId());
		if (!optional.isPresent())
			throw new Exception("Chỉ tiêu kế hoạch năm không tồn tại");

		ChiTieuKeHoachNam ctkhn = optional.get();
		Long ctkhnId = ctkhn.getId();

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

		return null;
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
				throw new Exception("Kế hoạch lương thực không tồn tại.");

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
	public boolean delete(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<ChiTieuKeHoachNam> optionalChiTieuKeHoachNam = chiTieuKeHoachNamRepository.findById(id);
		if (!optionalChiTieuKeHoachNam.isPresent())
			throw new Exception("Không tìm thấy dữ liệu.");

		ChiTieuKeHoachNam chiTieuKeHoachNam = optionalChiTieuKeHoachNam.get();

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
		return buildDetailResponse(ctkhn);
	}

	@Override
	public boolean updateStatus(StatusReq req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<ChiTieuKeHoachNam> optionalChiTieuKeHoachNam = chiTieuKeHoachNamRepository.findById(req.getId());
		if (!optionalChiTieuKeHoachNam.isPresent())
			throw new Exception("Không tìm thấy dữ liệu.");

		ChiTieuKeHoachNam chiTieuKeHoachNam = optionalChiTieuKeHoachNam.get();
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

		ChiTieuKeHoachNamRes response = new ChiTieuKeHoachNamRes();
		response.setId(chiTieuKeHoachNam.getId());
		response.setNamKeHoach(chiTieuKeHoachNam.getNamKeHoach());
		response.setSoQuyetDinh(chiTieuKeHoachNam.getSoQuyetDinh());
		response.setNgayHieuLuc(chiTieuKeHoachNam.getNgayHieuLuc());
		response.setNgayKy(chiTieuKeHoachNam.getNgayKy());
		response.setTrangThai(chiTieuKeHoachNam.getTrangThai());
		response.setTenTrangThai(ChiTieuKeHoachNamStatus.getTenById(chiTieuKeHoachNam.getTrangThai()));
		response.setTrichYeu(chiTieuKeHoachNam.getTrichYeu());

		List<KeHoachLuongThucMuoi> keHoachLuongThucList = chiTieuKeHoachNam.getKhLuongThucList();
		List<KeHoachLuongThucMuoi> keHoachMuoiList = chiTieuKeHoachNam.getKhMuoiList();
		List<KeHoachVatTu> keHoachVatTuList = chiTieuKeHoachNam.getKhVatTuList();

		Set<Long> donViIdSet = new HashSet<>();
		Set<Long> vatTuIdSet = new HashSet<>();

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

		vatTuIdSet.add(MUOI_ID);
		vatTuIdSet.add(GAO_ID);
		vatTuIdSet.add(THOC_ID);

		keHoachVatTuList.forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());

			if (k.getVatTuId() != null)
				vatTuIdSet.add(k.getVatTuId());

			if (k.getVatTuChaId() != null)
				vatTuIdSet.add(k.getVatTuChaId());
		});

		List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAllById(vatTuIdSet));
		List<QlnvDmDonvi> dmDonviList = Lists.newArrayList(qlnvDmDonviRepository.findAllById(donViIdSet));

		Map<Long, QlnvDmVattu> mapVatTu = vattuList.stream().collect(Collectors.toMap(QlnvDmVattu::getId, Function.identity()));
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

		List<TonKhoDauNamRes> tonKhoDauNamResList = this.getTonKhoDauNam(maDviLtm, maVatTuLtm, chiTieuKeHoachNam.getNamKeHoach());
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
			k.setTkdnTongSoQuyThoc(k.getTkdnTongGao() + k.getTkdnTongThoc());

			k.setTkcnTongGao(k.getTkdnTongGao() - k.getXtnTongGao());
			k.setTkcnTongThoc(k.getTkdnTongThoc() - k.getXtnTongThoc());
			k.setTkcnTongSoQuyThoc(k.getTkcnTongThoc() + k.getTkcnTongGao());
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

			k.setTkcnTongSoMuoi(k.getTkdnTongSoMuoi() - k.getXtnTongSoMuoi());
		});

		List<VatTuNhapQueryDTO> vatTuNhapQueryDTOList = this.getKeHoachVatTuThietBiCacNamTruoc(vatTuIdList, chiTieuKeHoachNam.getNamKeHoach());
		System.out.println();
		khVatTuResList.forEach(k -> {
			for (VatTuThietBiRes vatTu : k.getVatTuThietBi()) {
				List<VatTuNhapQueryDTO> khntList = vatTuNhapQueryDTOList.stream()
						.filter(kh -> kh.getVatTuId().equals(vatTu.getVatTuId()))
						.collect(Collectors.toList());
				for (VatTuNhapQueryDTO vtn : khntList) {
					vatTu.getCacNamTruoc().add(new VatTuNhapRes(vtn.getNam(), vtn.getSoLuong(), vtn.getVatTuId()));
				}
				Double tongCacNamTruoc = vatTu.getCacNamTruoc().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum();
				vatTu.setTongCacNamTruoc(tongCacNamTruoc);
				vatTu.setTongNhap(vatTu.getNhapTrongNam() + tongCacNamTruoc);
			}
		});

		khLtResList.sort(Comparator.comparing(KeHoachLuongThucDuTruRes::getStt));
		khMuoiResList.sort(Comparator.comparing(KeHoachMuoiDuTruRes::getStt));
		khVatTuResList.sort(Comparator.comparing(KeHoachVatTuRes::getStt));

		response.setKhLuongThuc(khLtResList);
		response.setKhMuoiDuTru(khMuoiResList);
		response.setKhVatTu(khVatTuResList);

		return response;
	}

}
