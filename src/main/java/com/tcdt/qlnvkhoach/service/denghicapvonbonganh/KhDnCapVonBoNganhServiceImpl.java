package com.tcdt.qlnvkhoach.service.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.enums.NhapXuatHangTrangThaiEnum;
import com.tcdt.qlnvkhoach.mapper.denghicapvonbonganh.KhDnCapVonBoNganhCtRequestMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapvonbonganh.KhDnCapVonBoNganhRequestMapper;
import com.tcdt.qlnvkhoach.mapper.denghicapvonbonganh.KhDnCapVonBoNganhResponseMapper;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnCapVonBoNganhCtRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnCapVonBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.khotang.KtDiemKhoRepository;
import com.tcdt.qlnvkhoach.repository.khotang.KtNganKhoRepository;
import com.tcdt.qlnvkhoach.repository.khotang.KtNganLoRepository;
import com.tcdt.qlnvkhoach.repository.khotang.KtNhaKhoRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhCtResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
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
public class KhDnCapVonBoNganhServiceImpl extends BaseServiceImpl implements KhDnCapVonBoNganhService {
	private final KhDnCapVonBoNganhRepository khDnCapVonBoNganhRepository;
	private final KhDnCapVonBoNganhRequestMapper khDnCapVonBoNganhRequestMapper;
	private final KhDnCapVonBoNganhResponseMapper khDnCapVonBoNganhResponseMapper;

	private final KhDnCapVonBoNganhCtRequestMapper ctRequestMapper;
	private final FileDinhKemService fileDinhKemService;
	private final QlnvDmVattuRepository dmVattuRepository;

	private final KhDnCapVonBoNganhCtRepository ctRepository;

	private final KtNganLoRepository ktNganLoRepository;
	private final KtDiemKhoRepository ktDiemKhoRepository;
	private final KtNhaKhoRepository ktNhaKhoRepository;
	private final KtNganKhoRepository ktNganKhoRepository;

	private static final String SHEET_NAME = "Đề nghị cấp vốn bộ ngành";

	@Override
	public KhDnCapVonBoNganhResponse create(KhDnCapVonBoNganhRequest req) throws Exception {
		if (req == null) return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		KhDnCapVonBoNganh theEntity = khDnCapVonBoNganhRequestMapper.toEntity(req);
		theEntity.setTrangThai(NhapXuatHangTrangThaiEnum.DUTHAO.getId());
		theEntity.setNgayTao(LocalDate.now());
		theEntity.setNguoiTaoId(userInfo.getId());
		theEntity.setMaDvi(userInfo.getDvql());
		theEntity.setCapDvi(userInfo.getCapDvi());

		theEntity = khDnCapVonBoNganhRepository.save(theEntity);

		log.info("Save file dinh kem");
		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), theEntity.getId(), KhDnCapVonBoNganh.TABLE_NAME);
		theEntity.setFileDinhKems(fileDinhKems);


		if (!CollectionUtils.isEmpty(req.getChiTietList())) {
			theEntity.setChiTietList(this.saveChiTietList(req, theEntity));
		}
		KhDnCapVonBoNganhResponse response = khDnCapVonBoNganhResponseMapper.toDto(theEntity);
		return response;
	}

	private List<KhDnCapVonBoNganhCt> saveChiTietList(KhDnCapVonBoNganhRequest req, KhDnCapVonBoNganh theEntity) {
		//Clean data before save
		this.deleteChiTiets(Collections.singleton(theEntity.getId()));
		//Save chi tiết
		List<KhDnCapVonBoNganhCt> chiTietList = ctRequestMapper.toEntity(req.getChiTietList());

		for (KhDnCapVonBoNganhCt entry : chiTietList) {
			entry.setDeNghiCapVonBoNganhId(theEntity.getId());
		}
		chiTietList = ctRepository.saveAll(chiTietList);
		return chiTietList;
	}

	private void deleteChiTiets(Set<Long> khDnCapVonBoNganhIds) {
		List<KhDnCapVonBoNganhCt> ctList = ctRepository.findByDeNghiCapVonBoNganhIdIn(khDnCapVonBoNganhIds);
		if (!CollectionUtils.isEmpty(ctList)) {
			ctRepository.deleteAll(ctList);
		}
	}


	@Override
	public KhDnCapVonBoNganhResponse update(KhDnCapVonBoNganhRequest req) throws Exception {
		if (req == null) return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");

		Optional<KhDnCapVonBoNganh> optional = khDnCapVonBoNganhRepository.findById(req.getId());
		if (!optional.isPresent())
			throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
		KhDnCapVonBoNganh theEntity = optional.get();

		log.info("Update Đề nghị cấp vốn bộ ngành");
		khDnCapVonBoNganhRequestMapper.partialUpdate(theEntity, req);

		theEntity.setNgaySua(LocalDate.now());
		theEntity.setNguoiSuaId(userInfo.getId());
		theEntity = khDnCapVonBoNganhRepository.save(theEntity);

		log.info("Save file dinh kem");
		List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), theEntity.getId(), KhDnCapVonBoNganh.TABLE_NAME);
		theEntity.setFileDinhKems(fileDinhKems);

		theEntity.setChiTietList(this.saveChiTietList(req, theEntity));

		return khDnCapVonBoNganhResponseMapper.toDto(theEntity);
	}

	@Override
	public boolean delete(Long id) throws Exception {
		return this.deleteMultiple(Collections.singletonList(id));
	}

	@Override
	public boolean deleteMultiple(List<Long> ids) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		List<KhDnCapVonBoNganh> khDnCapVonBoNganhList = khDnCapVonBoNganhRepository.findByIdIn(ids);
		if (CollectionUtils.isEmpty(khDnCapVonBoNganhList)) {
			throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
		}

		if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");
		log.info("Delete file dinh kem");
		fileDinhKemService.deleteMultiple(ids, Collections.singleton(KhDnCapVonBoNganh.TABLE_NAME));

		log.info("Delete Chi tiết");
		this.deleteChiTiets(khDnCapVonBoNganhList.stream().map(KhDnCapVonBoNganh::getId).collect(Collectors.toSet()));

		log.info("Delete Đề nghị cấp vốn bộ ngành");
		khDnCapVonBoNganhRepository.deleteAll(khDnCapVonBoNganhList);
		return true;
	}

	@Override
	public Page<KhDnCapVonBoNganhSearchResponse> search(KhDnCapVonBoNganhSearchRequest req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		return khDnCapVonBoNganhRepository.search(req, req.getPageable());
	}

	@Override
	public KhDnCapVonBoNganhResponse detail(Long id) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");


		Optional<KhDnCapVonBoNganh> optional = khDnCapVonBoNganhRepository.findById(id);
		if (!optional.isPresent())
			throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
		KhDnCapVonBoNganh khDnCapVonBoNganh = optional.get();

		//Chi tiết
		List<KhDnCapVonBoNganhCt> ctList = ctRepository.findByDeNghiCapVonBoNganhIdIn(Collections.singleton(khDnCapVonBoNganh.getId()));
		if (!CollectionUtils.isEmpty(ctList)) {
			khDnCapVonBoNganh.setChiTietList(ctList);
		}

		KhDnCapVonBoNganhResponse response = khDnCapVonBoNganhResponseMapper.toDto(khDnCapVonBoNganh);
		//Trạng thái
		response.setTenTrangThai(NhapXuatHangTrangThaiEnum.getTenById(khDnCapVonBoNganh.getTrangThai()));
		response.setTrangThaiDuyet(NhapXuatHangTrangThaiEnum.getTrangThaiDuyetById(khDnCapVonBoNganh.getTrangThai()));

		//Đơn vị
		if (!StringUtils.isEmpty(response.getMaBoNganh())) {
			response.setTenBoNganh(this.getMapTenDvi().get(response.getMaBoNganh()));
		}

		if (!CollectionUtils.isEmpty(response.getChiTietList())) {
			this.buildChiTietResponse(response.getChiTietList());
		}

		response.setFileDinhKems(fileDinhKemService.search(khDnCapVonBoNganh.getId(), Collections.singleton(KhDnCapVonBoNganh.TABLE_NAME)));

		return response;
	}

	private void buildChiTietResponse(List<KhDnCapVonBoNganhCtResponse> chiTiets) {
		Set<String> maVatTus = new HashSet<>();
		chiTiets.forEach(entry -> {
			maVatTus.add(entry.getMaVatTu());
			maVatTus.add(entry.getMaVatTuCha());
		});
		if (CollectionUtils.isEmpty(maVatTus)) return;
		//Vật tư
		Set<QlnvDmVattu> vatTus = dmVattuRepository.findByMaIn(maVatTus);
		//Map vật tư: key=mã vật tư, value = tên vật tư
		Map<String, String> vatTuMap = vatTus.stream().collect(Collectors.toMap(QlnvDmVattu::getMa, QlnvDmVattu::getTen));

		chiTiets.forEach(item -> {
			item.setTenVatTuCha(vatTuMap.get(item.getMaVatTuCha()));
			item.setTenVatTu(vatTuMap.get(item.getMaVatTu()));
		});

	}


	@Override
	public KhDnCapVonBoNganhResponse updateTrangThai(Long id, String trangThaiId) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");
		if (StringUtils.isEmpty(trangThaiId)) throw new Exception("trangThaiId không được để trống");

		Optional<KhDnCapVonBoNganh> optional = khDnCapVonBoNganhRepository.findById(id);
		if (!optional.isPresent())
			throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
		KhDnCapVonBoNganh khDnCapVonBoNganh = optional.get();
		//validate Trạng Thái
		String trangThai = NhapXuatHangTrangThaiEnum.getTrangThaiDuyetById(trangThaiId);
		if (StringUtils.isEmpty(trangThai)) throw new Exception("Trạng thái không tồn tại");
		khDnCapVonBoNganh.setTrangThai(trangThaiId);
		khDnCapVonBoNganh = khDnCapVonBoNganhRepository.save(khDnCapVonBoNganh);
		return khDnCapVonBoNganhResponseMapper.toDto(khDnCapVonBoNganh);
	}

	@Override
	public boolean exportToExcel(KhDnCapVonBoNganhSearchRequest req, HttpServletResponse response) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");		this.prepareSearchReq(req, userInfo, req.getCapDvis(), req.getTrangThais());
		req.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
		List<KhDnCapVonBoNganhSearchResponse> list = this.search(req).get().collect(Collectors.toList());

		if (CollectionUtils.isEmpty(list))
			return true;

		String[] rowsName = new String[]{ExcelHeaderConst.STT,
				ExcelHeaderConst.SO_BIEN_BAN,
				ExcelHeaderConst.NGAY_LAY_MAU,
				ExcelHeaderConst.DIEM_KHO,
				ExcelHeaderConst.NHA_KHO,
				ExcelHeaderConst.NGAN_KHO,
				ExcelHeaderConst.LO_KHO,
				ExcelHeaderConst.TRANG_THAI
		};
		String filename = "bien_ban_lay_mau.xlsx";

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
