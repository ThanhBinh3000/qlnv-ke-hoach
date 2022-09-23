package com.tcdt.qlnvkhoach.service.denghicapphibonganh;


import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganhCt1;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganhCt2;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.mapper.denghicapphibonganh.KhDnCapPhiBoNganhRequestMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapphibonganh.KhDnCapPhiBoNganhResponseMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapphibonganh.ct1.KhDnCapPhiBoNganhCt1RequestMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapphibonganh.ct1.KhDnCapPhiBoNganhCt1ResponseMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapphibonganh.ct2.KhDnCapPhiBoNganhCt2RequestMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapphibonganh.ct2.KhDnCapPhiBoNganhCt2ResponseMapper;
import com.tcdt.qlnvkhoach.repository.DanhMucRepository;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoach.repository.denghicapphibonganh.KhDnCapPhiBoNganhCt1Repository;
import com.tcdt.qlnvkhoach.repository.denghicapphibonganh.KhDnCapPhiBoNganhCt2Repository;
import com.tcdt.qlnvkhoach.repository.denghicapphibonganh.KhDnCapPhiBoNganhRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhCt2Request;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhRequest;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhCt1Response;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhCt2Response;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.ExcelHeaderConst;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class KhDnCapPhiBoNganhServiceImpl extends BaseServiceImpl implements KhDnCapPhiBoNganhService {
	private final KhDnCapPhiBoNganhRepository khDnCapPhiBoNganhRepository;
	private final KhDnCapPhiBoNganhRequestMapper khDnCapPhiBoNganhRequestMapper;
	private final KhDnCapPhiBoNganhResponseMapper khDnCapPhiBoNganhResponseMapper;

	private final KhDnCapPhiBoNganhCt1RequestMapper ct1RequestMapper;
	private final KhDnCapPhiBoNganhCt2RequestMapper ct2RequestMapper;

	private final KhDnCapPhiBoNganhCt1ResponseMapper ct1ResponseMapper;
	private final KhDnCapPhiBoNganhCt2ResponseMapper ct2ResponseMapper;

	private final FileDinhKemService fileDinhKemService;
	private final QlnvDmVattuRepository dmVattuRepository;
	private final DanhMucRepository danhMucRepository;

	private final KhDnCapPhiBoNganhCt1Repository ct1Repository;
	private final KhDnCapPhiBoNganhCt2Repository ct2Repository;

	private final QlnvDmService qlnvDmService;

	private static final String SHEET_NAME = "Đề nghị cấp phí bộ ngành";

	@Override
	public KhDnCapPhiBoNganhResponse create(KhDnCapPhiBoNganhRequest req) throws Exception {
		if (req == null) return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		KhDnCapPhiBoNganh theEntity = khDnCapPhiBoNganhRequestMapper.toEntity(req);
		theEntity.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
		theEntity.setNgayTao(LocalDate.now());
		theEntity.setNguoiTaoId(userInfo.getId());
		theEntity.setMaDvi(userInfo.getDvql());
		theEntity.setCapDvi(userInfo.getCapDvi());

		theEntity = khDnCapPhiBoNganhRepository.save(theEntity);

		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), theEntity.getId(), KhDnCapPhiBoNganh.TABLE_NAME);
		theEntity.setFileDinhKems(fileDinhKems);

		KhDnCapPhiBoNganhResponse response = khDnCapPhiBoNganhResponseMapper.toDto(theEntity);

		if (!CollectionUtils.isEmpty(req.getCt1List())) {
			response.setCt1List(this.saveChiTietList(req, theEntity));
		}
		response.setTenTrangThai(TrangThaiDungChungEnum.getTenById(response.getTrangThai()));

		return response;
	}

	private Map<String, QlnvDmVattu> createMapVatTu(KhDnCapPhiBoNganhRequest req) {
		Set<String> maVatTuIds = new HashSet<>();
		req.getCt1List().forEach(v -> {
			if (CollectionUtils.isEmpty(v.getCt2List())) return;
			v.getCt2List().forEach(item -> {
				maVatTuIds.add(item.getMaVatTu());
				maVatTuIds.add(item.getMaVatTuCha());
			});
		});

		if (CollectionUtils.isEmpty(maVatTuIds)) return null;

		return qlnvDmService.getMapVatTu(maVatTuIds);
	}

	private List<KhDnCapPhiBoNganhCt1Response> saveChiTietList(KhDnCapPhiBoNganhRequest req, KhDnCapPhiBoNganh theEntity) {
		//Clean data before save
		this.deleteChiTiets(Collections.singleton(theEntity.getId()));

		Map<String, QlnvDmVattu> vattuMap = this.createMapVatTu(req);

		//Save chi tiết
		List<KhDnCapPhiBoNganhCt1Response> chiTietList = req.getCt1List().stream().map(entry -> {
			KhDnCapPhiBoNganhCt1 ct1 = ct1RequestMapper.toEntity(entry);
			ct1.setDnCapPhiId(theEntity.getId());
			ct1 = ct1Repository.save(ct1);
			KhDnCapPhiBoNganhCt1Response ct1Response = ct1ResponseMapper.toDto(ct1);
			List<KhDnCapPhiBoNganhCt2Response> ct2List = saveCt2(entry.getCt2List(), ct1, vattuMap);
			ct1Response.setCt2List(ct2List);
			return ct1Response;
		}).collect(Collectors.toList());

		return chiTietList;
	}

	private List<KhDnCapPhiBoNganhCt2Response> saveCt2(List<KhDnCapPhiBoNganhCt2Request> reqs, KhDnCapPhiBoNganhCt1 ct1, Map<String, QlnvDmVattu> vattuMap) {
		if (CollectionUtils.isEmpty(reqs)) return null;
		List<KhDnCapPhiBoNganhCt2Response> chiTietList = reqs.stream().map(entry -> {
			KhDnCapPhiBoNganhCt2 ct2 = ct2RequestMapper.toEntity(entry);
			ct2.setCapPhiBoNghanhCt1Id(ct1.getId());
			ct2 = ct2Repository.save(ct2);
			KhDnCapPhiBoNganhCt2Response ct2Response = ct2ResponseMapper.toDto(ct2);
			ct2Response.setTenVatTu(Optional.ofNullable(vattuMap.get(ct2Response.getMaVatTu())).map(QlnvDmVattu::getTen).orElse(null));
			ct2Response.setTenVatTuCha(Optional.ofNullable(vattuMap.get(ct2Response.getMaVatTuCha())).map(QlnvDmVattu::getTen).orElse(null));
			return ct2Response;
		}).collect(Collectors.toList());
		return chiTietList;
	}

	private void deleteChiTiets(Set<Long> khDnCapPhiBoNganhIds) {
		List<KhDnCapPhiBoNganhCt1> ct1List = ct1Repository.findByDnCapPhiIdIn(khDnCapPhiBoNganhIds);
		if (CollectionUtils.isEmpty(ct1List)) return;

		Set<Long> ct2Ids = ct1List.stream().map(KhDnCapPhiBoNganhCt1::getId).collect(Collectors.toSet());

		if (CollectionUtils.isEmpty(ct2Ids)) return;

		List<KhDnCapPhiBoNganhCt2> ct2List = ct2Repository.findByCapPhiBoNghanhCt1IdIn(ct2Ids);

		if (!CollectionUtils.isEmpty(ct2List)) ct2Repository.deleteAll(ct2List);

		ct1Repository.deleteAll(ct1List);
	}


	@Override
	public KhDnCapPhiBoNganhResponse update(KhDnCapPhiBoNganhRequest req) throws Exception {
		if (req == null) return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");

		Optional<KhDnCapPhiBoNganh> optional = khDnCapPhiBoNganhRepository.findById(req.getId());
		if (!optional.isPresent())
			throw new Exception("Đề nghị cấp phí bộ ngành không tồn tại");
		KhDnCapPhiBoNganh theEntity = optional.get();

		log.info("Update Đề nghị cấp phí bộ ngành");
		khDnCapPhiBoNganhRequestMapper.partialUpdate(theEntity, req);

		theEntity.setNgaySua(LocalDate.now());
		theEntity.setNguoiSuaId(userInfo.getId());
		theEntity = khDnCapPhiBoNganhRepository.save(theEntity);

		log.info("Save file dinh kem");
		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), theEntity.getId(), KhDnCapPhiBoNganh.TABLE_NAME);
		theEntity.setFileDinhKems(fileDinhKems);
		KhDnCapPhiBoNganhResponse response = khDnCapPhiBoNganhResponseMapper.toDto(theEntity);

		if (!CollectionUtils.isEmpty(req.getCt1List())) {
			response.setCt1List(this.saveChiTietList(req, theEntity));
		}

		return response;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		return this.deleteMultiple(Collections.singletonList(id));
	}

	@Override
	public boolean deleteMultiple(List<Long> ids) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		List<KhDnCapPhiBoNganh> khDnCapPhiBoNganhList = khDnCapPhiBoNganhRepository.findByIdIn(ids);
		if (CollectionUtils.isEmpty(khDnCapPhiBoNganhList)) {
			throw new Exception("Đề nghị cấp phí bộ ngành không tồn tại");
		}

		if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");
		log.info("Delete file dinh kem");
		fileDinhKemService.deleteMultiple(ids, Collections.singleton(KhDnCapPhiBoNganh.TABLE_NAME));

		log.info("Delete Chi tiết");
		this.deleteChiTiets(khDnCapPhiBoNganhList.stream().map(KhDnCapPhiBoNganh::getId).collect(Collectors.toSet()));

		log.info("Delete Đề nghị cấp phí bộ ngành");
		khDnCapPhiBoNganhRepository.deleteAll(khDnCapPhiBoNganhList);
		return true;
	}

	@Override
	public Page<KhDnCapPhiBoNganhSearchResponse> search(KhDnCapPhiBoNganhSearchRequest req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		return khDnCapPhiBoNganhRepository.search(req, req.getPageable());
	}

	@Override
	public KhDnCapPhiBoNganhResponse detail(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");


		Optional<KhDnCapPhiBoNganh> optional = khDnCapPhiBoNganhRepository.findById(id);
		if (!optional.isPresent())
			throw new Exception("Đề nghị cấp phí bộ ngành không tồn tại");
		KhDnCapPhiBoNganh khDnCapPhiBoNganh = optional.get();

		//Chi tiết 1
		List<KhDnCapPhiBoNganhCt1> ct1List = ct1Repository.findByDnCapPhiIdIn(Collections.singleton(khDnCapPhiBoNganh.getId()));

		//Chi tiết 2
		List<KhDnCapPhiBoNganhCt2> ct2List =
				ct2Repository.findByCapPhiBoNghanhCt1IdIn(ct1List.stream().map(KhDnCapPhiBoNganhCt1::getId).collect(Collectors.toSet()));
		//Map ct2 key = ct1Id, value = List<KhDnCapPhiBoNganhCt2>
		Map<Long, List<KhDnCapPhiBoNganhCt2>> ct2Map = ct2List.stream().collect(Collectors.groupingBy(KhDnCapPhiBoNganhCt2::getCapPhiBoNghanhCt1Id));

		//Get mã vật tư
		Set<String> maVatTuList = new HashSet<>();
		ct2List.forEach(entry -> {
			maVatTuList.add(entry.getMaVatTu());
			maVatTuList.add(entry.getMaVatTuCha());
		});

		Map<String, QlnvDmVattu> vatTuMap = qlnvDmService.getMapVatTu(maVatTuList);

		List<KhDnCapPhiBoNganhCt1Response> ct1Responses = ct1List.stream().map(item -> {
			KhDnCapPhiBoNganhCt1Response ct1Response = ct1ResponseMapper.toDto(item);
			if (ct2Map.get(ct1Response.getId()) != null) {
				ct1Response.setCt2List(createCt2Response(ct2Map, vatTuMap, ct1Response));
			}
			return ct1Response;
		}).collect(Collectors.toList());


		KhDnCapPhiBoNganhResponse response = khDnCapPhiBoNganhResponseMapper.toDto(khDnCapPhiBoNganh);
		response.setCt1List(ct1Responses);
		//Trạng thái
		response.setTenTrangThai(TrangThaiDungChungEnum.getTenById(khDnCapPhiBoNganh.getTrangThai()));
		response.setTrangThaiDuyet(TrangThaiDungChungEnum.getTrangThaiDuyetById(khDnCapPhiBoNganh.getTrangThai()));

		//Bộ nghành
		if (!StringUtils.isEmpty(response.getMaBoNganh())) {
			QlnvDanhMuc danhMuc = danhMucRepository.findByMa(response.getMaBoNganh());
			response.setTenBoNganh(danhMuc != null ? danhMuc.getGiaTri() : null);
		}

		response.setFileDinhKems(fileDinhKemService.search(khDnCapPhiBoNganh.getId(), Collections.singleton(KhDnCapPhiBoNganh.TABLE_NAME)));

		return response;
	}

	private List<KhDnCapPhiBoNganhCt2Response> createCt2Response(Map<Long, List<KhDnCapPhiBoNganhCt2>> ct2Map, Map<String, QlnvDmVattu> vatTuMap, KhDnCapPhiBoNganhCt1Response ct1Response) {
		List<KhDnCapPhiBoNganhCt2Response> ct2ResponseList = ct2ResponseMapper.toDto(ct2Map.get(ct1Response.getId()));
		ct2ResponseList.forEach(ct2 -> {
			ct2.setTenVatTu(Optional.ofNullable(vatTuMap.get(ct2.getMaVatTu())).map(QlnvDmVattu::getTen).orElse(null));
			ct2.setTenVatTuCha(Optional.ofNullable(vatTuMap.get(ct2.getMaVatTuCha())).map(QlnvDmVattu::getTen).orElse(null));
		});
		return ct2ResponseList;
	}


	@Override
	public KhDnCapPhiBoNganhResponse updateTrangThai(Long id, String trangThaiId) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		if (StringUtils.isEmpty(trangThaiId)) throw new Exception("trangThaiId không được để trống");

		Optional<KhDnCapPhiBoNganh> optional = khDnCapPhiBoNganhRepository.findById(id);
		if (!optional.isPresent())
			throw new Exception("Đề nghị cấp phí bộ ngành không tồn tại");
		KhDnCapPhiBoNganh khDnCapPhiBoNganh = optional.get();
		//validate Trạng Thái
		String trangThai = TrangThaiDungChungEnum.getTrangThaiDuyetById(trangThaiId);
		if (StringUtils.isEmpty(trangThai)) throw new Exception("Trạng thái không tồn tại");
		khDnCapPhiBoNganh.setTrangThai(trangThaiId);
		khDnCapPhiBoNganh = khDnCapPhiBoNganhRepository.save(khDnCapPhiBoNganh);
		return khDnCapPhiBoNganhResponseMapper.toDto(khDnCapPhiBoNganh);
	}

	@Override
	public boolean exportToExcel(KhDnCapPhiBoNganhSearchRequest req, HttpServletResponse response) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		this.prepareSearchReq(req, userInfo, req.getCapDvis(), req.getTrangThais());
		req.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
		List<KhDnCapPhiBoNganhSearchResponse> list = this.search(req).get().collect(Collectors.toList());

		if (CollectionUtils.isEmpty(list))
			return true;

		String[] rowsName = new String[]{ExcelHeaderConst.STT,
				ExcelHeaderConst.SO_DE_NGHI,
				ExcelHeaderConst.BO_NGHANH,
				ExcelHeaderConst.NGAY_DE_NGHI,
				ExcelHeaderConst.NAM,
				ExcelHeaderConst.TONG_TIEN,
				ExcelHeaderConst.KINH_PHI_DA_CAP,
				ExcelHeaderConst.YEU_CAU_CAP_THEM,
				ExcelHeaderConst.TRANG_THAI
		};
		String filename = "de_nghi_cap_von_bo_nghanh.xlsx";

		List<Object[]> dataList = new ArrayList<>();

		try {
			for (int i = 0; i < list.size(); i++) {
				dataList.add(list.get(i).toExcel(rowsName, i));
			}

			ExportExcel ex = new ExportExcel(SHEET_NAME, filename, rowsName, dataList, response);
			ex.export();
		} catch (Exception e) {
			log.error("Error export", e);
			return false;
		}
		return true;
	}
}
