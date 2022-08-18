package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagCcPhapLy;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagKetQua;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhPagCcPhapLyRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagKetQuaRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagKetQuaReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class KhLtPhuongAnGiaServiceImpl implements KhLtPhuongAnGiaService {
	private final KhLtPhuongAnGiaRepository phuongAnGiaRepository;
	private final FileDinhKemService fileDinhKemService;

	private final KhLtPagKetQuaRepository khLtPagKetQuaRepository;
	private final KhPagCcPhapLyRepository khPagCcPhapLyRepository;

	private final ModelMapper mapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();

		if (userInfo == null) throw new Exception("Bad request.");

		log.info("Save: thông tin phương án giá");
		KhPhuongAnGia phuongAnGia = mapper.map(req, KhPhuongAnGia.class);
		phuongAnGia.setTrangThai(TrangThaiEnum.DU_THAO.getId());
		phuongAnGia.setMaDvi(userInfo.getDvql());
		phuongAnGia.setCapDvi(userInfo.getCapDvi());
		phuongAnGia.setNguoiTaoId(userInfo.getId());
		phuongAnGia.setNgayTao(LocalDateTime.now());

		phuongAnGia = phuongAnGiaRepository.save(phuongAnGia);

		log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
		KhPhuongAnGia finalPhuongAnGia = phuongAnGia;

		List<KhPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
			KhPagCcPhapLy canCuPhapLy = mapper.map(item, KhPagCcPhapLy.class);

			canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());
			log.info("Save file đính kèm");
			List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhPagCcPhapLy.TABLE_NAME);
			canCuPhapLy.setFileDinhKems(fileDinhKems);
			return canCuPhapLy;
		}).collect(Collectors.toList());

		phuongAnGia.setCanCuPhapLy(canCuPhapLyList);


		log.info("Save thông tin khảo sát giá");
		log.info("Save kết quả khảo sát giá thị trường");
		List<KhPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
		log.info("Save kết quả thẩm định giá");
		List<KhPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
		log.info("Save thông tin giá của hàng hóa tương tự");
		List<KhPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
		phuongAnGia.setThongTinGiaHangHoaTuongTu(thongTinGiaHangHoaTuongTu);

		log.info("Build phương án giá response");
		KhLtPhuongAnGiaRes phuongAnGiaRes = mapper.map(phuongAnGia, KhLtPhuongAnGiaRes.class);

		return phuongAnGiaRes;
	}

	/**
	 * Save Kết quả khảo sát giá thị trường
	 * Thông tin giá của hàng hóa tương tự
	 * kết quả thẩm định giá
	 */
	private List<KhPagKetQua> saveKetQua(List<KhLtPagKetQuaReq> reqs, String type, Long phuongAnGiaId) {
		List<KhPagKetQua> ketQuaList = reqs.stream().map(item -> {
			KhPagKetQua ketQua = mapper.map(item, KhPagKetQua.class);
			ketQua.setPhuongAnGiaId(phuongAnGiaId);
			ketQua = khLtPagKetQuaRepository.save(ketQua);
			ketQua.setType(type);

			List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), ketQua.getId(), KhPagKetQua.getFileDinhKemDataType(type));
			ketQua.setFileDinhKems(fileDinhKems);
			return ketQua;
		}).collect(Collectors.toList());
		ketQuaList = khLtPagKetQuaRepository.saveAll(ketQuaList);
		return ketQuaList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception {
		if (req == null) return null;

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");

		Optional<KhPhuongAnGia> optional = phuongAnGiaRepository.findById(req.getId());
		if (!optional.isPresent()) throw new Exception("Đề xuất phương án giá không tồn tại");

		KhPhuongAnGia phuongAnGia = optional.get();

		phuongAnGia = mapper.map(req, KhPhuongAnGia.class);
		phuongAnGia.setNguoiSuaId(userInfo.getId());
		phuongAnGia.setNgaySua(LocalDateTime.now());

		log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
		KhPhuongAnGia finalPhuongAnGia = phuongAnGia;

		List<KhPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
			KhPagCcPhapLy canCuPhapLy = mapper.map(item, KhPagCcPhapLy.class);

			canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());

			canCuPhapLy = khPagCcPhapLyRepository.save(canCuPhapLy);
			log.info("Save file đính kèm");
			List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhPagCcPhapLy.TABLE_NAME);
			canCuPhapLy.setFileDinhKems(fileDinhKems);
			return canCuPhapLy;
		}).collect(Collectors.toList());

		phuongAnGia.setCanCuPhapLy(canCuPhapLyList);
		phuongAnGia = phuongAnGiaRepository.save(phuongAnGia);

		log.info("Update kết quả khảo sát giá thị trường");
		List<KhPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
		log.info("Update kết quả thẩm định giá");
		List<KhPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
		log.info("Update thông tin giá của hàng hóa tương tự");
		List<KhPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
		phuongAnGia.setThongTinGiaHangHoaTuongTu(thongTinGiaHangHoaTuongTu);

		log.info("Build phương án giá response");
		KhLtPhuongAnGiaRes phuongAnGiaRes = mapper.map(phuongAnGia, KhLtPhuongAnGiaRes.class);

		return phuongAnGiaRes;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteMultiple(List<Long> ids) throws Exception {
		if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null) throw new Exception("Bad request.");

		List<KhPhuongAnGia> phuongAnGiaList = phuongAnGiaRepository.findByIdIn(ids);

		List<Long> phuongAnGiaIds = phuongAnGiaList.stream().map(KhPhuongAnGia::getId).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(phuongAnGiaList)) throw new Exception("Bad request.");

		log.info("Xóa căn cứ pháp lý và file đính kèm");
		List<KhPagCcPhapLy> khPagCcPhapLyList = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(phuongAnGiaIds);

		if (!CollectionUtils.isEmpty(khPagCcPhapLyList)) {
			List<Long> canCuPhapLyIds = khPagCcPhapLyList.stream().map(KhPagCcPhapLy::getId).collect(Collectors.toList());
			fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhPagCcPhapLy.TABLE_NAME));
			khPagCcPhapLyRepository.deleteAll(khPagCcPhapLyList);
		}
		log.info("Xóa kết quả");
		this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGiaIds);
		this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGiaIds);
		this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGiaIds);

		phuongAnGiaRepository.deleteAll(phuongAnGiaList);

		return true;
	}

	private void deleteKetQua(String type, List<Long> phuongAnGiaIds) {
		List<KhPagKetQua> ketQuaList = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(type, phuongAnGiaIds);
		if (CollectionUtils.isEmpty(ketQuaList)) return;
		List<Long> ketQuaIds = ketQuaList.stream().map(KhPagKetQua::getId).collect(Collectors.toList());
		fileDinhKemService.deleteMultiple(ketQuaIds, Collections.singleton(KhPagKetQua.getFileDinhKemDataType(type)));
		khLtPagKetQuaRepository.deleteAll(ketQuaList);
	}
}
