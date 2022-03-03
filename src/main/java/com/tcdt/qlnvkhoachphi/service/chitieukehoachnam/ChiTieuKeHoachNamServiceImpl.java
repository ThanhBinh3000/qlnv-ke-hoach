package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoachphi.entities.KeHoachLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.entities.KeHoachVatTu;
import com.tcdt.qlnvkhoachphi.entities.KeHoachXuatLuongThucMuoi;
import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachLuongThucMuoiRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachVatTuRepository;
import com.tcdt.qlnvkhoachphi.repository.KeHoachXuatLuongThucMuoiRepository;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private final Long THOC_ID = 3L;
	private final Long GAO_ID = 4L;
	private final Long MUOI_ID = 481L;


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
		BeanUtils.copyProperties(chiTieuKeHoachNam, response);
		List<KeHoachLuongThucMuoi> keHoachLuongThucMuoiList = keHoachLuongThucMuoiRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		List<KeHoachLuongThucDuTruRes> keHoachLuongThucDuTruResList = new ArrayList<>();
		List<KeHoachMuoiDuTruRes> keHoachMuoiDuTruResList = new ArrayList<>();

		for (KeHoachLuongThucMuoi keHoachLuongThucMuoi : keHoachLuongThucMuoiList) {
			KeHoachLuongThucDuTruRes res = keHoachLuongThucDuTruResList.stream().filter(r -> r.getCucId().equals(keHoachLuongThucMuoi.getDonViId())).findFirst().orElse(null);
			if (res == null) {
				res = new KeHoachLuongThucDuTruRes();
				res.setId(keHoachLuongThucMuoi.getId());
				res.setCucId(keHoachLuongThucMuoi.getDonViId());
			}

			List<KeHoachXuatLuongThucMuoi> keHoachXuatLuongThucMuoiList = keHoachXuatLuongThucMuoiRepository.findByKeHoachId(keHoachLuongThucMuoi.getId());
			List<VatTuNhapRes> vatTuList = new ArrayList<>();
			for (KeHoachXuatLuongThucMuoi keHoachXuatLuongThucMuoi : keHoachXuatLuongThucMuoiList) {
				VatTuNhapRes vatTu = new VatTuNhapRes();
				vatTu.setNam(keHoachXuatLuongThucMuoi.getNamKeHoach());
				vatTu.setSoLuong(keHoachXuatLuongThucMuoi.getSoLuongXuat());
				vatTu.setId(keHoachXuatLuongThucMuoi.getId());
				vatTuList.add(vatTu);
			}

			if (GAO_ID.equals(keHoachLuongThucMuoi.getVatTuId())) {
				res.setNtnGao(keHoachLuongThucMuoi.getSoLuongNhap());
				res.setXtnGao(vatTuList);
			} else if (THOC_ID.equals(keHoachLuongThucMuoi.getVatTuId())) {
				res.setNtnThoc(keHoachLuongThucMuoi.getSoLuongNhap());
				res.setXtnThoc(vatTuList);
			} else if (MUOI_ID.equals(keHoachLuongThucMuoi.getVatTuId())) {
				KeHoachMuoiDuTruRes muoiDuTruRes = keHoachMuoiDuTruResList.stream().filter(r -> r.getCucId().equals(keHoachLuongThucMuoi.getDonViId())).findFirst().orElse(null);
				if (muoiDuTruRes == null)
					muoiDuTruRes = new KeHoachMuoiDuTruRes();
				muoiDuTruRes.setId(keHoachLuongThucMuoi.getId());
				muoiDuTruRes.setCucId(keHoachLuongThucMuoi.getDonViId());
				muoiDuTruRes.setNtnTongSoMuoi(keHoachLuongThucMuoi.getSoLuongNhap());
				keHoachMuoiDuTruResList.add(muoiDuTruRes);
				continue;
			}

			keHoachLuongThucDuTruResList.add(res);
		}

		List<KeHoachVatTu> keHoachVatTuList = keHoachVatTuRepository.findByCtkhnId(chiTieuKeHoachNam.getId());
		Set<Long> donViIdSet = new HashSet<>();
		Set<Long> vatTuIdSet = new HashSet<>();
		keHoachVatTuList.forEach(k -> {
			if (k.getDonViId() != null)
				donViIdSet.add(k.getDonViId());

			if (k.getVatTuId() != null)
				vatTuIdSet.add(k.getVatTuId());

			if (k.getVatTuChaId() != null)
				vatTuIdSet.add(k.getVatTuChaId());
		});
		List<KeHoachVatTuRes> keHoachVatTuResList = new ArrayList<>();
		List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAllById(vatTuIdSet));
		List<QlnvDmDonvi> dmDonviList = Lists.newArrayList(qlnvDmDonviRepository.findAllById(donViIdSet));
		for (KeHoachVatTu keHoachVatTu : keHoachVatTuList) {
			KeHoachVatTuRes keHoachVatTuRes = keHoachVatTuResList.stream().filter(k -> k.getCucId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);
			if (keHoachVatTuRes == null) {
				QlnvDmDonvi donVi = dmDonviList.stream().filter(d -> d.getId().equals(keHoachVatTu.getDonViId())).findFirst().orElse(null);
				String tenDvi = donVi == null ? null : donVi.getTenDvi();
				keHoachVatTuRes = new KeHoachVatTuRes();
				keHoachVatTuRes.setId(keHoachVatTu.getId());
				keHoachVatTuRes.setCucId(keHoachVatTuRes.getCucId());
				keHoachVatTuRes.setCucDTNNKhuVuc(tenDvi);
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

		response.setKeHoachLuongThuc(keHoachLuongThucDuTruResList);
		response.setKeHoachMuoiDuTru(keHoachMuoiDuTruResList);
		response.setKeHoachVatTu(keHoachVatTuResList);

		return response;
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
