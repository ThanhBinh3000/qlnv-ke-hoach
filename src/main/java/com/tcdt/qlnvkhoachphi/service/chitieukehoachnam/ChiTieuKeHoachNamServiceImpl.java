package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoachphi.entities.KeHoachLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KeHoachVatTu;
import com.tcdt.qlnvkhoachphi.entities.KeHoachXuatLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KtTrangthaiHienthoi;
import com.tcdt.qlnvkhoachphi.query.dto.KeHoachNamTruoc;
import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachVatTuRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachXuatLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KtTrangthaiHienthoiRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.KeHoachLuongThucDuTruReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.KeHoachMuoiDuTruReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.KeHoachNhapVatTuThietBiReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.VatTuNhapReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.VatTuThietBiReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	public ChiTieuKeHoachNamRes create(ChiTieuKeHoachNamReq req) throws Exception {
		if (req == null)
			return  null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		ChiTieuKeHoachNam chiTieuKeHoachNam = new ChiTieuKeHoachNam();
		BeanUtils.copyProperties(req, chiTieuKeHoachNam);
		chiTieuKeHoachNam.setNgayTao(LocalDate.now());
		chiTieuKeHoachNam.setNguoiTaoId(userInfo.getId());
		chiTieuKeHoachNam.setTrangThai(Constants.MOI_TAO);
		chiTieuKeHoachNamRepository.save(chiTieuKeHoachNam);

		Long chiTieuId = chiTieuKeHoachNam.getId();
		for (KeHoachLuongThucDuTruReq keHoachLuongThucDuTruReq : req.getKeHoachLuongThucDuTru()) {
			KeHoachLuongThucMuoi keHoachGao = new KeHoachLuongThucMuoi();
			keHoachGao.setTrangThai(Constants.MOI_TAO);
			keHoachGao.setNguoiTaoId(userInfo.getId());
			keHoachGao.setNgayTao(LocalDate.now());
			keHoachGao.setCtkhnId(chiTieuId);
			keHoachGao.setDonViId(keHoachLuongThucDuTruReq.getCucId());
			keHoachGao.setVatTuId(GAO_ID);
			Double soLuongNhapGao = keHoachLuongThucDuTruReq.getNtnGao() == null ? 0 : keHoachLuongThucDuTruReq.getNtnGao();
			keHoachGao.setSoLuongNhap(soLuongNhapGao);
			keHoachGao.setDonViTinh(keHoachLuongThucDuTruReq.getDonViTinh());
			keHoachLuongThucMuoiRepository.save(keHoachGao);

			List<KeHoachXuatLuongThucMuoi> keHoachXuatGaoList = this.buildKeHoachXuat(keHoachGao.getId(), keHoachLuongThucDuTruReq.getXtnGao());
			if (!CollectionUtils.isEmpty(keHoachXuatGaoList))
				keHoachXuatLuongThucMuoiRepository.saveAll(keHoachXuatGaoList);

			KeHoachLuongThucMuoi keHoachThoc = new KeHoachLuongThucMuoi();
			keHoachThoc.setTrangThai(Constants.MOI_TAO);
			keHoachThoc.setNguoiTaoId(userInfo.getId());
			keHoachThoc.setNgayTao(LocalDate.now());
			keHoachThoc.setCtkhnId(chiTieuId);
			keHoachThoc.setDonViId(keHoachLuongThucDuTruReq.getCucId());
			keHoachThoc.setVatTuId(THOC_ID);
			Double soLuongNhapThoc = keHoachLuongThucDuTruReq.getNtnThoc() == null ? 0 : keHoachLuongThucDuTruReq.getNtnThoc();
			keHoachThoc.setSoLuongNhap(soLuongNhapThoc);
			keHoachThoc.setDonViTinh(keHoachLuongThucDuTruReq.getDonViTinh());
			keHoachLuongThucMuoiRepository.save(keHoachThoc);

			List<KeHoachXuatLuongThucMuoi> keHoachXuatThocList = this.buildKeHoachXuat(keHoachThoc.getId(), keHoachLuongThucDuTruReq.getXtnThoc());
			if (!CollectionUtils.isEmpty(keHoachXuatThocList))
				keHoachXuatLuongThucMuoiRepository.saveAll(keHoachXuatThocList);
		}

		for (KeHoachMuoiDuTruReq keHoachMuoiDuTruReq : req.getKeHoachMuoiDuTru()) {
			KeHoachLuongThucMuoi keHoachMuoi = new KeHoachLuongThucMuoi();
			keHoachMuoi.setTrangThai(Constants.MOI_TAO);
			keHoachMuoi.setNguoiTaoId(userInfo.getId());
			keHoachMuoi.setNgayTao(LocalDate.now());
			keHoachMuoi.setCtkhnId(chiTieuId);
			keHoachMuoi.setDonViId(keHoachMuoiDuTruReq.getCucId());
			keHoachMuoi.setVatTuId(MUOI_ID);
			Double soLuongNhap = keHoachMuoiDuTruReq.getNhapTrongNam() == null ? 0 : keHoachMuoiDuTruReq.getNhapTrongNam();
			keHoachMuoi.setSoLuongNhap(soLuongNhap);
			keHoachMuoi.setDonViTinh(keHoachMuoiDuTruReq.getDonViTinh());
			keHoachLuongThucMuoiRepository.save(keHoachMuoi);

			List<KeHoachXuatLuongThucMuoi> keHoachXuatMuoiList = this.buildKeHoachXuat(keHoachMuoi.getId(), keHoachMuoiDuTruReq.getXuatTrongNam());
			if (!CollectionUtils.isEmpty(keHoachXuatMuoiList))
				keHoachXuatLuongThucMuoiRepository.saveAll(keHoachXuatMuoiList);
		}

		List<KeHoachVatTu> keHoachVatTuList = new ArrayList<>();
		for (KeHoachNhapVatTuThietBiReq keHoachNhapVatTuThietBiReq : req.getKeHoachNhapVatTuThietBi()) {
			for (VatTuThietBiReq nhapVatTuThietBiReq : keHoachNhapVatTuThietBiReq.getVatTuThietBi()) {
				KeHoachVatTu keHoachVatTu = new KeHoachVatTu();
				keHoachVatTu.setTrangThai(Constants.MOI_TAO);
				keHoachVatTu.setNguoiTaoId(userInfo.getId());
				keHoachVatTu.setNgayTao(LocalDate.now());
				keHoachVatTu.setCtkhnId(chiTieuId);
				keHoachVatTu.setDonViId(keHoachNhapVatTuThietBiReq.getCucId());
				keHoachVatTu.setDonViTinh(nhapVatTuThietBiReq.getDonViTinh());
				keHoachVatTu.setSoLuongNhap(nhapVatTuThietBiReq.getNhapTrongNam());
				keHoachVatTu.setVatTuId(nhapVatTuThietBiReq.getVatTuId());
				keHoachVatTu.setVatTuChaId(nhapVatTuThietBiReq.getVatTuChaId());
				keHoachVatTuList.add(keHoachVatTu);
			}
		}

		if (!CollectionUtils.isEmpty(keHoachVatTuList))
			keHoachVatTuRepository.saveAll(keHoachVatTuList);

		return this.buildDetailResponse(chiTieuKeHoachNam);
	}

	private ChiTieuKeHoachNamRes buildDetailResponse(ChiTieuKeHoachNam chiTieuKeHoachNam) {
		ChiTieuKeHoachNamRes response = new ChiTieuKeHoachNamRes();
		response.setId(chiTieuKeHoachNam.getId());
		response.setNamKeHoach(chiTieuKeHoachNam.getNamKeHoach());
		response.setSoQuyetDinh(chiTieuKeHoachNam.getSoQuyetDinh());
		response.setNgayHieuLuc(chiTieuKeHoachNam.getNgayHieuLuc());
		response.setNgayKy(chiTieuKeHoachNam.getNgayKy());
		response.setTrangThai(chiTieuKeHoachNam.getTrangThai());

		List<KeHoachLuongThucMuoi> keHoachLuongThucMuoiList = keHoachLuongThucMuoiRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		List<KeHoachVatTu> keHoachVatTuList = keHoachVatTuRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		List<KeHoachLuongThucDuTruRes> keHoachLuongThucDuTruResList = new ArrayList<>();
		List<KeHoachMuoiDuTruRes> keHoachMuoiDuTruResList = new ArrayList<>();

		Set<Long> donViIdSet = new HashSet<>();
		Set<Long> vatTuIdSet = new HashSet<>();
		List<String> maDviLtm = new ArrayList<>();
		List<String> maVatTuLtm = new ArrayList<>();

		keHoachLuongThucMuoiList.forEach(k -> {
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
		List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAllById(donViIdSet));
		List<QlnvDmDonvi> dmDonviList = Lists.newArrayList(qlnvDmDonviRepository.findAllById(vatTuIdSet));

		int sttLt = 1;
		int sttMuoi = 1;
		int sttVattu = 1;
		for (KeHoachLuongThucMuoi keHoachLuongThucMuoi : keHoachLuongThucMuoiList) {
			KeHoachLuongThucDuTruRes res = keHoachLuongThucDuTruResList.stream().filter(r -> r.getCucId().equals(keHoachLuongThucMuoi.getDonViId())).findFirst().orElse(null);

			if (res == null) {
				res = new KeHoachLuongThucDuTruRes();
				QlnvDmDonvi donVi = dmDonviList.stream().filter(d -> d.getId().equals(keHoachLuongThucMuoi.getDonViId())).findFirst().orElse(null);
				String tenDonVi = null;
				String maDonVi = null;
				if (donVi != null) {
					tenDonVi = donVi.getTenDvi();
					maDonVi = donVi.getMaDvi();
					res.setMaDonVi(donVi.getMaDvi());
				}
				if (!StringUtils.isEmpty(maDonVi)) {
					maDviLtm.add(maDonVi);
				}

				res.setId(keHoachLuongThucMuoi.getId());
				res.setCucId(keHoachLuongThucMuoi.getDonViId());
				res.setCucDTNNKhuVuc(tenDonVi);
			}

			List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuoiList = keHoachXuatLuongThucMuoiRepository.findByKeHoachId(keHoachLuongThucMuoi.getId());
			List<VatTuNhapRes> vatTuList = new ArrayList<>();
			Double tongXuat = 0D;
			for (KeHoachXuatLuongThucMuoi keHoachXuatLuongThucMuoi : keHoachXuatLuongThucMuoiList) {
				VatTuNhapRes vatTu = new VatTuNhapRes();
				vatTu.setNam(keHoachXuatLuongThucMuoi.getNamKeHoach());
				vatTu.setSoLuong(keHoachXuatLuongThucMuoi.getSoLuongXuat());
				vatTu.setId(keHoachXuatLuongThucMuoi.getId());
				vatTuList.add(vatTu);
				tongXuat += vatTu.getSoLuong();
			}
			res.setXtnTongSoQuyThoc(tongXuat);

			if (GAO_ID.equals(keHoachLuongThucMuoi.getVatTuId())) {
				res.setNtnGao(keHoachLuongThucMuoi.getSoLuongNhap());
				res.setXtnGao(vatTuList);
				res.setStt(sttLt++);
				// To do: set gaoId
			} else if (THOC_ID.equals(keHoachLuongThucMuoi.getVatTuId())) {
				res.setNtnThoc(keHoachLuongThucMuoi.getSoLuongNhap());
				res.setXtnThoc(vatTuList);
				res.setStt(sttLt++);
				// To do: set thocId
			} else if (MUOI_ID.equals(keHoachLuongThucMuoi.getVatTuId())) {
				KeHoachMuoiDuTruRes muoiDuTruRes = keHoachMuoiDuTruResList.stream().filter(r -> r.getCucId().equals(keHoachLuongThucMuoi.getDonViId())).findFirst().orElse(null);
				if (muoiDuTruRes == null)
					muoiDuTruRes = new KeHoachMuoiDuTruRes();
				muoiDuTruRes.setId(keHoachLuongThucMuoi.getId());
				muoiDuTruRes.setCucId(keHoachLuongThucMuoi.getDonViId());
				muoiDuTruRes.setNtnTongSoMuoi(keHoachLuongThucMuoi.getSoLuongNhap());
				muoiDuTruRes.setStt(sttMuoi++);
				keHoachMuoiDuTruResList.add(muoiDuTruRes);
				continue;
			}

			keHoachLuongThucDuTruResList.add(res);
		}

		List<KeHoachVatTuRes> keHoachVatTuResList = new ArrayList<>();
		for (KeHoachVatTu keHoachVatTu : keHoachVatTuList) {
			KeHoachVatTuRes keHoachVatTuRes = keHoachVatTuResList.stream().filter(k -> k.getCucId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);
			if (keHoachVatTuRes == null) {
				QlnvDmDonvi donVi = dmDonviList.stream().filter(d -> d.getId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);
				String tenDvi = donVi == null ? null : donVi.getTenDvi();
				String maDvi = donVi == null ? null : donVi.getMaDvi();
				keHoachVatTuRes = new KeHoachVatTuRes();
				keHoachVatTuRes.setId(keHoachVatTu.getId());
				keHoachVatTuRes.setMaDonVi(maDvi);
				keHoachVatTuRes.setCucId(keHoachVatTuRes.getCucId());
				keHoachVatTuRes.setCucDTNNKhuVuc(tenDvi);
				keHoachVatTuRes.setStt(sttVattu++);
			}

			QlnvDmVattu vattu = vattuList.stream().filter(v -> v.getId().equals(keHoachVatTu.getVatTuId())).findFirst().orElse(null);
			String tenVattu = vattu == null ? null : vattu.getTen();
			QlnvDmVattu vattuCha = vattuList.stream().filter(v -> v.getId().equals(keHoachVatTu.getVatTuChaId())).findFirst().orElse(null);
			String tenVattuCha = vattuCha == null ? null : vattuCha.getTen();

			VatTuThietBiRes vatTuThietBiRes = new VatTuThietBiRes();
			vatTuThietBiRes.setVatTuId(keHoachVatTu.getVatTuId());
			vatTuThietBiRes.setTenVatTu(tenVattu);
			vatTuThietBiRes.setVatTuChaId(keHoachVatTu.getVatTuChaId());
			vatTuThietBiRes.setTenVatTuCha(tenVattuCha);
			vatTuThietBiRes.setDonViTinh(keHoachVatTu.getDonViTinh());
			vatTuThietBiRes.setNhapTrongNam(keHoachVatTu.getSoLuongNhap());
			keHoachVatTuRes.getVatTuThietBi().add(vatTuThietBiRes);

			keHoachVatTuResList.add(keHoachVatTuRes);
		}

		List<TonKhoDauNamRes> tonKhoDauNamResList = this.getTonKhoDauNam(maDviLtm, maVatTuLtm);

		keHoachLuongThucDuTruResList.forEach(k -> {
			k.setNtnThoc(k.getNtnThoc() + k.getNtnGao());
			Double xtnTongGao = k.getXtnGao().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum();
			k.setXtnTongGao(xtnTongGao);
			Double xtnTongThoc = k.getXtnThoc().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum();
			k.setXtnTongThoc(xtnTongThoc);
			k.setXtnTongSoQuyThoc(xtnTongGao + xtnTongThoc);

			TonKhoDauNamRes tonKhoDauNamGao = tonKhoDauNamResList.stream().filter(t -> k.getCucId().equals(t.getDonViId()) && GAO_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			TonKhoDauNamRes tonKhoDauNamThoc = tonKhoDauNamResList.stream().filter(t -> k.getCucId().equals(t.getDonViId()) && THOC_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);

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
			k.setTkdnTongSoQuyThoc(k.getTkcnTongThoc() + k.getTkcnTongGao());
		});

		keHoachMuoiDuTruResList.forEach(k -> {
			k.setXtnTongSoMuoi(k.getXtnMuoi().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			TonKhoDauNamRes tonKhoDauNamMuoi = tonKhoDauNamResList.stream().filter(t -> k.getCucId().equals(t.getDonViId()) && MUOI_MA_VT.equals(t.getMaVatTu())).findFirst().orElse(null);
			if (tonKhoDauNamMuoi != null && !CollectionUtils.isEmpty(tonKhoDauNamMuoi.getTonKho())) {
				k.setTkdnMuoi(tonKhoDauNamMuoi.getTonKho().stream().sorted(Comparator.comparing(VatTuNhapRes::getNam)).collect(Collectors.toList()));
			}
			k.setTkdnTongSoMuoi(k.getTkdnMuoi().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum());
			k.setTkcnTongSoMuoi(k.getTkdnTongSoMuoi() - k.getXtnTongSoMuoi());
		});

		keHoachVatTuResList.forEach(k -> {
			for (VatTuThietBiRes vatTu : k.getVatTuThietBi()) {
				Double tongCacNamTruoc = vatTu.getCacNamTruoc().stream().mapToDouble(VatTuNhapRes::getSoLuong).sum();
				// To do: set list tong nhap cac nam truoc
				vatTu.setTongCacNamTruoc(tongCacNamTruoc);
				vatTu.setTongNhap(vatTu.getNhapTrongNam() + tongCacNamTruoc);
			}
		});

		response.setKeHoachLuongThuc(keHoachLuongThucDuTruResList);
		response.setKeHoachMuoiDuTru(keHoachMuoiDuTruResList);
		response.setKeHoachVatTu(keHoachVatTuResList);

		return response;
	}

	private List<KeHoachNamTruoc> getKeHoachVatTuThietBiCacNamTruoc(List<Long> vatTuIdList) {
		List<KeHoachNamTruoc> keHoachNamTruocList = new ArrayList<>();
		Integer nam = LocalDate.now().getYear();
		List<VatTuNhapRes> keHoachVatTuList = keHoachVatTuRepository.findKeHoachVatTuCacNamTruocByVatTuId(vatTuIdList, nam -3, nam - 1);
		return keHoachNamTruocList;
	}

	private List<TonKhoDauNamRes> getTonKhoDauNam(List<String> maDonViList, List<String> vatTuIdList) {
		List<String> namList = new ArrayList<>();
		Integer nam = LocalDate.now().getYear();
		for (int i = 1; i <= 3; i++ ) {
			namList.add(String.valueOf(nam - i));
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
			vatTuNhapRes.setSoLuong(trangthaiHienthoi.getSlHienthoi());
			res.getTonKho().add(vatTuNhapRes);
		}

		return tonKhoDauNamResList;
	}

	private List<KeHoachXuatLuongThucMuoi> buildKeHoachXuat(Long keHoachId, List<VatTuNhapReq> vatTuNhapReqList) {
		List<KeHoachXuatLuongThucMuoi> keHoachXuatList = new ArrayList<>();

		for (VatTuNhapReq vatTuNhapReq : vatTuNhapReqList) {
			KeHoachXuatLuongThucMuoi keHoachXuat = new KeHoachXuatLuongThucMuoi();
			keHoachXuat.setKeHoachId(keHoachId);
			Double soLuongXuat = vatTuNhapReq.getSoLuong() == null ? 0 : vatTuNhapReq.getSoLuong();
			keHoachXuat.setSoLuongXuat(soLuongXuat);
			keHoachXuat.setNamKeHoach(vatTuNhapReq.getNam());
			keHoachXuatList.add(keHoachXuat);
		}
		return keHoachXuatList;
	}

	@Override
	public ChiTieuKeHoachNamRes update(ChiTieuKeHoachNamReq req) {
		return null;
	}

	@Override
	public void approve(Long id) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public ChiTieuKeHoachNamRes detail(Long id) {
		return null;
	}
}
