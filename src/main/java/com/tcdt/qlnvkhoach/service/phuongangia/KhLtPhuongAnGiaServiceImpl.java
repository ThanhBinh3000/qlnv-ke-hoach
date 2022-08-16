package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagCcPhapLy;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagCcPhapLyRepository;
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
	private final KhLtPagCcPhapLyRepository khLtPagCcPhapLyRepository;

	private final ModelMapper mapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception {
		UserInfo userInfo = SecurityContextService.getUser();

		if (userInfo == null) throw new Exception("Bad request.");

		log.info("Save: thông tin phương án giá");
		KhLtPhuongAnGia phuongAnGia = mapper.map(req, KhLtPhuongAnGia.class);
		phuongAnGia.setTrangThai(TrangThaiEnum.DU_THAO.getId());
		phuongAnGia.setMaDvi(userInfo.getDvql());
		phuongAnGia.setCapDvi(userInfo.getCapDvi());
		phuongAnGia.setNguoiTaoId(userInfo.getId());
		phuongAnGia.setNgayTao(LocalDate.now());

		phuongAnGia = phuongAnGiaRepository.save(phuongAnGia);

		log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
		KhLtPhuongAnGia finalPhuongAnGia = phuongAnGia;

		List<KhLtPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
			KhLtPagCcPhapLy canCuPhapLy = mapper.map(item, KhLtPagCcPhapLy.class);

			canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());
			log.info("Save file đính kèm");
			List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhLtPagCcPhapLy.TABLE_NAME);
			canCuPhapLy.setFileDinhKems(fileDinhKems);
			return canCuPhapLy;
		}).collect(Collectors.toList());

		phuongAnGia.setCanCuPhapLy(canCuPhapLyList);


		log.info("Save thông tin khảo sát giá");
		log.info("Save kết quả khảo sát giá thị trường");
		List<KhLtPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
		log.info("Save kết quả thẩm định giá");
		List<KhLtPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
		log.info("Save thông tin giá của hàng hóa tương tự");
		List<KhLtPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
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
	private List<KhLtPagKetQua> saveKetQua(List<KhLtPagKetQuaReq> reqs, String type, Long phuongAnGiaId) {
		List<KhLtPagKetQua> ketQuaList = reqs.stream().map(item -> {
			KhLtPagKetQua ketQua = mapper.map(item, KhLtPagKetQua.class);
			ketQua.setPhuongAnGiaId(phuongAnGiaId);
			ketQua = khLtPagKetQuaRepository.save(ketQua);
			ketQua.setType(type);

			List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), ketQua.getId(), KhLtPagKetQua.getFileDinhKemDataType(type));
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

		Optional<KhLtPhuongAnGia> optional = phuongAnGiaRepository.findById(req.getId());
		if (!optional.isPresent()) throw new Exception("Đề xuất phương án giá không tồn tại");

		KhLtPhuongAnGia phuongAnGia = optional.get();

		phuongAnGia = mapper.map(req, KhLtPhuongAnGia.class);
		phuongAnGia.setNguoiSuaId(userInfo.getId());
		phuongAnGia.setNgaySua(LocalDate.now());

		log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
		KhLtPhuongAnGia finalPhuongAnGia = phuongAnGia;

		List<KhLtPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
			KhLtPagCcPhapLy canCuPhapLy = mapper.map(item, KhLtPagCcPhapLy.class);

			canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());

			canCuPhapLy = khLtPagCcPhapLyRepository.save(canCuPhapLy);
			log.info("Save file đính kèm");
			List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhLtPagCcPhapLy.TABLE_NAME);
			canCuPhapLy.setFileDinhKems(fileDinhKems);
			return canCuPhapLy;
		}).collect(Collectors.toList());

		phuongAnGia.setCanCuPhapLy(canCuPhapLyList);
		phuongAnGia = phuongAnGiaRepository.save(phuongAnGia);

		log.info("Update kết quả khảo sát giá thị trường");
		List<KhLtPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
		log.info("Update kết quả thẩm định giá");
		List<KhLtPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
		phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
		log.info("Update thông tin giá của hàng hóa tương tự");
		List<KhLtPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
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

		List<KhLtPhuongAnGia> phuongAnGiaList = phuongAnGiaRepository.findByIdIn(ids);

		List<Long> phuongAnGiaIds = phuongAnGiaList.stream().map(KhLtPhuongAnGia::getId).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(phuongAnGiaList)) throw new Exception("Bad request.");

		log.info("Xóa căn cứ pháp lý và file đính kèm");
		List<KhLtPagCcPhapLy> khLtPagCcPhapLyList = khLtPagCcPhapLyRepository.findByPhuongAnGiaIdIn(phuongAnGiaIds);

		if (!CollectionUtils.isEmpty(khLtPagCcPhapLyList)) {
			List<Long> canCuPhapLyIds = khLtPagCcPhapLyList.stream().map(KhLtPagCcPhapLy::getId).collect(Collectors.toList());
			fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhLtPagCcPhapLy.TABLE_NAME));
			khLtPagCcPhapLyRepository.deleteAll(khLtPagCcPhapLyList);
		}
		log.info("Xóa kết quả");
		this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGiaIds);
		this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGiaIds);
		this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGiaIds);

		phuongAnGiaRepository.deleteAll(phuongAnGiaList);

		return true;
	}

	private void deleteKetQua(String type, List<Long> phuongAnGiaIds) {
		List<KhLtPagKetQua> ketQuaList = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(type, phuongAnGiaIds);
		if (CollectionUtils.isEmpty(ketQuaList)) return;
		List<Long> ketQuaIds = ketQuaList.stream().map(KhLtPagKetQua::getId).collect(Collectors.toList());
		fileDinhKemService.deleteMultiple(ketQuaIds, Collections.singleton(KhLtPagKetQua.getFileDinhKemDataType(type)));
		khLtPagKetQuaRepository.deleteAll(ketQuaList);
	}
}
