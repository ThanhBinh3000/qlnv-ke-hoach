package com.tcdt.qlnvkhoachphi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiKhoachBquanHnamMatHangEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiNxuatDtqgHnamVattuEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamEntity;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiTcKhoachBquanHnamEntity;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.repository.UserInfoRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiChiDtaiDanNckhGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiChiTxGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiChiUdungCnttGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDmVondtXdcbgd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiKhoachBquanHnamMatHangRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiKhoachBquanHnamThocGaoRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiKhoachCtaoSchuaGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiKhoachQuyTienLuongGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiKhoachQuyTienLuongHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiNcauChiNsnnGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiNcauPhiNhapXuatGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiNcauXuatDtqgVtroHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiNxuatDtqgHnamThocGaoRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiNxuatDtqgHnamVattuRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcCtietNcauChiTxGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcDtoanChiDtqgGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcKhoachBquanHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcKhoachcQuyLuongN1Repository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcNcauKhoachDtxdGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcThopDtoanChiTxHnamRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcThopNcauChiNsnnGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcThopNncauChiTxGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiVbanQphamPluatDtqgGd3nRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.TongHopReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvBaoCaoSearchReq;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiKhoachBquanHnamMatHangResp;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiNxuatDtqgHnamVattuResp;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiTcKhoachBquanHnamResp;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmDonViService;
import com.tcdt.qlnvkhoachphi.service.QlnvKhvonphiChiTxGd3nService;
import com.tcdt.qlnvkhoachphi.service.QlnvKhvonphiService;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import com.tcdt.qlnvkhoachphi.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphi;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiChiDtaiDanNckhGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiChiTxGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiChiUdungCnttGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDmVondtXdcbgd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachBquanHnamMatHang;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachBquanHnamThocGao;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachCtaoSchuaGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachDtaoBoiDuongGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachQuyTienLuongGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachQuyTienLuongHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNcauChiNsnnGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNcauPhiNhapXuatGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNcauXuatDtqgVtroHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNxuatDtqgHnamThocGao;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNxuatDtqgHnamVattu;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcCtietNcauChiTxGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanChiDtqgGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanChiUdungCnttGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachBquanHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachBquanTbiVtuHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachcQuyLuongN1;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcNcauKhoachDtxdGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcThopDtoanChiTxHnam;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcThopNcauChiNsnnGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcThopNncauChiTxGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiVbanQphamPluatDtqgGd3n;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PaginationSet;
import com.tcdt.qlnvkhoachphi.util.PathConstants;
import com.tcdt.qlnvkhoachphi.util.Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_BAO_CAO)
@Slf4j
@Api(tags = "Báo cáo")
public class QlnvKhvonphiController extends BaseController {
	@Autowired
	private QlnvKhvonphiRepository qlnvBaoCaoRepository;

	@Autowired
	private QlnvKhvonphiService baoCaoService;

	@Autowired
	private QlnvKhvonphiChiTxGd3nService cTietBCaoCTXGD03NService;

	@Autowired
	private QlnvKhvonphiChiTxGd3nRepository qlnvKhvonphiChiTxGd3nRepository;

	@Autowired
	private QlnvKhvonphiDmVondtXdcbgd3nRepository qlnvKhvonphiDmVondtXdcbgd3nRepository;

	@Autowired
	private QlnvKhvonphiChiDtaiDanNckhGd3nRepository qlnvKhvonphiChiDtaiDanNckhGd3nRepository;

	@Autowired
	private QlnvKhvonphiChiUdungCnttGd3nRepository qlnvKhvonphiChiUdungCnttGd3nRepository;

	@Autowired
	private QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository;

	@Autowired
	private QlnvKhvonphiKhoachBquanHnamMatHangRepository qlnvKhvonphiKhoachBquanHnamMatHangRepository;

	@Autowired
	private QlnvKhvonphiKhoachBquanHnamThocGaoRepository qlnvKhvonphiKhoachBquanHnamThocGaoRepository;

	@Autowired
	private QlnvKhvonphiKhoachCtaoSchuaGd3nRepository qlnvKhvonphiKhoachCtaoSchuaGd3nRepository;

	@Autowired
	private QlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository;

	@Autowired
	private QlnvKhvonphiKhoachQuyTienLuongGd3nRepository qlnvKhvonphiKhoachQuyTienLuongGd3nRepository;

	@Autowired
	private QlnvKhvonphiKhoachQuyTienLuongHnamRepository qlnvKhvonphiKhoachQuyTienLuongHnamRepository;

	@Autowired
	private QlnvKhvonphiNcauChiNsnnGd3nRepository qlnvKhvonphiNcauChiNsnnGd3nRepository;

	@Autowired
	private QlnvKhvonphiNcauPhiNhapXuatGd3nRepository qlnvKhvonphiNcauPhiNhapXuatGd3nRepository;

	@Autowired
	private QlnvKhvonphiNcauXuatDtqgVtroHnamRepository qlnvKhvonphiNcauXuatDtqgVtroHnamRepository;

	@Autowired
	private QlnvKhvonphiNxuatDtqgHnamThocGaoRepository qlnvKhvonphiNxuatDtqgHnamThocGaoRepository;

	@Autowired
	private QlnvKhvonphiNxuatDtqgHnamVattuRepository qlnvKhvonphiNxuatDtqgHnamVattuRepository;

	@Autowired
	private QlnvKhvonphiVbanQphamPluatDtqgGd3nRepository qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository;

	// TC
	@Autowired
	private QlnvKhvonphiTcCtietNcauChiTxGd3nRepository qlnvKhvonphiTcCtietNcauChiTxGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcDtoanChiDtqgGd3nRepository qlnvKhvonphiDtoanChiDtqgGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository;

	@Autowired
	private QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository;

	@Autowired
	private QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository;

	@Autowired
	private QlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository qlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository;

	@Autowired
	private QlnvKhvonphiTcKhoachBquanHnamRepository qlnvKhvonphiTcKhoachBquanHnamRepository;

	@Autowired
	private QlnvKhvonphiTcKhoachcQuyLuongN1Repository qlnvKhvonphiTcKhoachcQuyLuongN1Repository;

	@Autowired
	private QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcNcauKhoachDtxdGd3nRepository qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcThopDtoanChiTxHnamRepository qlnvKhvonphiTcThopDtoanChiTxHnamRepository;

	@Autowired
	private QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcThopNcauChiNsnnGd3nRepository qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcThopNncauChiTxGd3nRepository qlnvKhvonphiTcThopNncauChiTxGd3nRepository;

	@Autowired
	private QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private QlnvDmDonViService qlnvDmDonViService;

//	@Autowired
//	QlnvDmDonviClient qlnvDmDonviClientService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

	@ApiOperation(value = "Lấy chi tiết thông tin báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			QlnvKhvonphi qlnvKhvonphi = baoCaoService.getBaoCaoById(Long.parseLong(ids));
			String maLoaiBcao = qlnvKhvonphi.getMaLoaiBcao();
			Object lstCTietBcao = null;

			// check maLoaiBcao trả về object tương ứng
			if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
				lstCTietBcao = qlnvKhvonphiDmVondtXdcbgd3nRepository
						.findQlnvKhvonphiDmVondtXdcbgd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
				QlnvKhvonphiNxuatDtqgHnamVattuEntity qlnvKhvonphiEntity = new ModelMapper().map(
						qlnvKhvonphiNxuatDtqgHnamThocGaoRepository.findQlnvKhvonphiNxuatDtqgHnamThocGaoByQlnvKhvonphiId(
								qlnvKhvonphi.getId()),
						QlnvKhvonphiNxuatDtqgHnamVattuEntity.class);
				qlnvKhvonphiEntity.setLstCTiet(qlnvKhvonphiNxuatDtqgHnamVattuRepository
						.findQlnvKhvonphiNxuatDtqgHnamVattuByQlnvKhvonphiId(qlnvKhvonphi.getId()));
				lstCTietBcao = qlnvKhvonphiEntity;

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
				QlnvKhvonphiKhoachBquanHnamMatHangEntity qlnvKhvonphiEntity = new ModelMapper().map(
						qlnvKhvonphiKhoachBquanHnamThocGaoRepository
								.findQlnvKhvonphiKhoachBquanHnamThocGaoByQlnvKhvonphiId(qlnvKhvonphi.getId()),
						QlnvKhvonphiKhoachBquanHnamMatHangEntity.class);
				qlnvKhvonphiEntity.setLstCTiet(qlnvKhvonphiKhoachBquanHnamMatHangRepository
						.findQlnvKhvonphiKhoachBquanHnamMatHangByQlnvKhvonphiId(qlnvKhvonphi.getId()));
				lstCTietBcao = qlnvKhvonphiEntity;

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
				lstCTietBcao = qlnvKhvonphiNcauXuatDtqgVtroHnamRepository
						.findQlnvKhvonphiNcauXuatDtqgVtroHnamByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
				lstCTietBcao = qlnvKhvonphiKhoachQuyTienLuongGd3nRepository
						.findQlnvKhvonphiKhoachQuyTienLuongGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
				lstCTietBcao = qlnvKhvonphiKhoachQuyTienLuongHnamRepository
						.findQlnvKhvonphiKhoachQuyTienLuongHnamByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
				lstCTietBcao = qlnvKhvonphiChiDtaiDanNckhGd3nRepository
						.findQlnvKhvonphiChiDtaiDanNckhGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
				lstCTietBcao = qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository
						.findQlnvKhvonphiVbanQphamPluatDtqgGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
				lstCTietBcao = qlnvKhvonphiChiUdungCnttGd3nRepository
						.findQlnvKhvonphiChiUdungCnttGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
				lstCTietBcao = qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository
						.findQlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
				lstCTietBcao = qlnvKhvonphiNcauChiNsnnGd3nRepository
						.findQlnvKhvonphiNcauChiNsnnGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
				lstCTietBcao = qlnvKhvonphiChiTxGd3nRepository
						.findQlnvKhvonphiChiTxGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
				lstCTietBcao = qlnvKhvonphiNcauPhiNhapXuatGd3nRepository
						.findQlnvKhvonphiNcauPhiNhapXuatGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
				lstCTietBcao = qlnvKhvonphiKhoachCtaoSchuaGd3nRepository
						.findQlnvKhvonphiKhoachCtaoSchuaGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
				lstCTietBcao = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository
						.findQlnvKhvonphiKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			}
			// TC
			else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
				lstCTietBcao = qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository
						.findQlnvKhvonphiTcNcauKhoachDtxdGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
				lstCTietBcao = qlnvKhvonphiTcThopDtoanChiTxHnamRepository
						.findQlnvKhvonphiTcThopDtoanChiTxHnamByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18
				ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamEntity> dtqgHnamEntities = new ArrayList<>();
				ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam> dtoanPhiNxuatDtqgHnam = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository
						.findQlnvKhvonphiTcDtoanPhiNxuatDtqgHnamByQlnvKhvonphiId(qlnvKhvonphi.getId());

				for (QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam qlnvKhvonphiTcDtoanPhiNxuatDtqgHnam : dtoanPhiNxuatDtqgHnam) {
					QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamEntity qlnvKhvonphiEntity = new ModelMapper().map(
							qlnvKhvonphiTcDtoanPhiNxuatDtqgHnam,QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamEntity.class);
					qlnvKhvonphiEntity.setLstTcDtoanPhiNxuatDtqgVtuTbiHnam(qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository
						.findQlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamByNxuatDtqgHnamId(qlnvKhvonphiEntity.getId()));

					dtqgHnamEntities.add(qlnvKhvonphiEntity);
				}
				lstCTietBcao = dtqgHnamEntities;

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19
				ArrayList<QlnvKhvonphiTcKhoachBquanHnamEntity> bquanHnamEntities = new ArrayList<>();
				ArrayList<QlnvKhvonphiTcKhoachBquanHnam> khoachBquanHnams = qlnvKhvonphiTcKhoachBquanHnamRepository
						.findQlnvKhvonphiTcKhoachBquanHnamByQlnvKhvonphiId(qlnvKhvonphi.getId());

				for (QlnvKhvonphiTcKhoachBquanHnam qlnvKhvonphiTcKhoachBquanHnam : khoachBquanHnams) {
					QlnvKhvonphiTcKhoachBquanHnamEntity qlnvKhvonphiEntity = new ModelMapper().map(
							qlnvKhvonphiTcKhoachBquanHnam,QlnvKhvonphiTcKhoachBquanHnamEntity.class);
					qlnvKhvonphiEntity.setLstTcKhoachBquanTbiVtuHnam(qlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository
							.findQlnvKhvonphiTcKhoachBquanTbiVtuHnamByBquanHnamId(qlnvKhvonphiTcKhoachBquanHnam.getId()));;

					bquanHnamEntities.add(qlnvKhvonphiEntity);
				}
				lstCTietBcao = bquanHnamEntities;

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
				lstCTietBcao = qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository
						.findQlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
				lstCTietBcao = qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository
						.findQlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
				lstCTietBcao = qlnvKhvonphiTcKhoachcQuyLuongN1Repository
						.findQlnvKhvonphiTcKhoachcQuyLuongN1ByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
				lstCTietBcao = qlnvKhvonphiDtoanChiDtqgGd3nRepository
						.findQlnvKhvonphiTcDtoanChiDtqgGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
				lstCTietBcao = qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository
						.findQlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
				lstCTietBcao = qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository
						.findQlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
				lstCTietBcao = qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository
						.findQlnvKhvonphiTcDtoanChiUdungCnttGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
				lstCTietBcao = qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository
						.findQlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
				lstCTietBcao = qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository
						.findQlnvKhvonphiTcThopNcauChiNsnnGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
				lstCTietBcao = qlnvKhvonphiTcThopNncauChiTxGd3nRepository
						.findQlnvKhvonphiTcThopNncauChiTxGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
				lstCTietBcao = qlnvKhvonphiTcCtietNcauChiTxGd3nRepository
						.findQlnvKhvonphiTcCtietNcauChiTxGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
				lstCTietBcao = qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository
						.findQlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			}

			// mapping response
			QlnvKhvonphiResp qlnvBaoCaoResp = new ModelMapper().map(qlnvKhvonphi, QlnvKhvonphiResp.class);
			qlnvBaoCaoResp.setLstCTietBCao(lstCTietBcao);

			// lấy danh sách file đính kèm
			ArrayList<FileDinhKem> lstFile = new ArrayList<>();
			ArrayList<FileDinhKem> fileDinhKem = fileDinhKemRepository.findFileDinhKemByQlnvId(qlnvKhvonphi.getId());
			lstFile.addAll(fileDinhKem);
			qlnvBaoCaoResp.setLstFile(lstFile);

			if (ObjectUtils.isEmpty(qlnvBaoCaoResp)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}
			resp.setData(qlnvBaoCaoResp);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy danh sách bao cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvBaoCaoSearchReq objReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvKhvonphi> data = qlnvBaoCaoRepository.selectParams(objReq.getNamBcao(), objReq.getNgayTaoTu(),
					objReq.getNgayTaoDen(), objReq.getMaDvi(), objReq.getMaBcao(), objReq.getMaLoaiBcao(), pageable);

			for (QlnvKhvonphi qlnvKhvonphi : data) {
				QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(String.valueOf(qlnvKhvonphi.getMaDvi()));
				if (!ObjectUtils.isEmpty(qlnvDmDonvi)) {
					qlnvKhvonphi.setTenDvi(qlnvDmDonvi.getTenDvi());
				}
				qlnvKhvonphi.setTenTrangThai(Utils.getTenTrangThai(qlnvKhvonphi.getTrangThai()));
			}

			resp.setData(data);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

//	@ApiOperation(value = "Lấy danh sách báo cáo", response = List.class)
//	@GetMapping(value = "/danh-sach/tat-ca", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<Resp> collect() {
//		Resp resp = new Resp();
//		try {
//			Iterable<QlnvBaoCao> qOptional = qlnvBaoCaoRepository.findAll();
//			resp.setData(qOptional);
//			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
//			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
//		} catch (Exception e) {
//			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
//			resp.setMsg(e.getMessage());
//			log.error(e.getMessage());
//		}
//		return ResponseEntity.ok(resp);
//	}

	@ApiOperation(value = "Thêm mới báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiReq objReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			// check role nhân viên mới được tạo
			UserInfo userInfo = getUserInfo(req);
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thêm mới!");
				}
			}
			List<QlnvKhvonphi> khvonphis = qlnvBaoCaoRepository.findByMaBcao(objReq.getMaBcao());
			if (khvonphis.size() > 0) {
				throw new UnsupportedOperationException("Mã báo cáo đã tồn tại!");
			}
			QlnvKhvonphi qlnvKhvonphi = new ModelMapper().map(objReq, QlnvKhvonphi.class);
			qlnvKhvonphi.setTrangThai(Constants.TT_BC_1);// Đang soạn
			qlnvKhvonphi.setNgayTao(new Date());
			qlnvKhvonphi.setNguoiTao(getUserName(req));
			String maloaiBcao = qlnvKhvonphi.getMaLoaiBcao();

			QlnvKhvonphi createCheck = qlnvBaoCaoRepository.save(qlnvKhvonphi);

			// thêm mới file đính kèm:
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			for (FileDinhKemReq dinhKemReq : objReq.getFileDinhKems()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KH_VON_PHI);
				dinhKem.setQlnvId(createCheck.getId());
				fileDinhKems.add(dinhKem);
			}
			fileDinhKemRepository.saveAll(fileDinhKems);

			// check insert theo loai bao cao
			// TODO thêm validate cho chi tiết báo cáo
			if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
				ArrayList<QlnvKhvonphiDmVondtXdcbgd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiDmVondtXdcbgd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiDmVondtXdcbgd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiDmVondtXdcbgd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
				ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu> lstVattu = new ArrayList<>();
				QlnvKhvonphiNxuatDtqgHnamVattuResp cTietBcao = new QlnvKhvonphiNxuatDtqgHnamVattuResp();

				for (Object item : objReq.getLstCTietBCao()) {

					// lấy chi tiết báo cáo
					cTietBcao = new ModelMapper().map(item, QlnvKhvonphiNxuatDtqgHnamVattuResp.class);
				}

				// set thoc gao
				QlnvKhvonphiNxuatDtqgHnamThocGao qlnvThocGao = new ModelMapper().map(cTietBcao,
						QlnvKhvonphiNxuatDtqgHnamThocGao.class);
				qlnvThocGao.setQlnvKhvonphiId(createCheck.getId());
				qlnvThocGao.setId(null);

				// set chi tiet
				for (Object item2 : cTietBcao.getLstCTiet()) {
					QlnvKhvonphiNxuatDtqgHnamVattu qlnvVattu = new ModelMapper().map(item2,
							QlnvKhvonphiNxuatDtqgHnamVattu.class);
					qlnvVattu.setQlnvKhvonphiId(createCheck.getId());
					qlnvVattu.setId(null);
					lstVattu.add(qlnvVattu);
				}

				qlnvKhvonphiNxuatDtqgHnamThocGaoRepository.save(qlnvThocGao);
				qlnvKhvonphiNxuatDtqgHnamVattuRepository.saveAll(lstVattu);

			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
				ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> lstQlnvMatHang = new ArrayList<>();
				QlnvKhvonphiKhoachBquanHnamMatHangResp cTietBcao = new QlnvKhvonphiKhoachBquanHnamMatHangResp();

				for (Object item : objReq.getLstCTietBCao()) {
					// lấy chi tiết báo cáo
					cTietBcao = new ModelMapper().map(item, QlnvKhvonphiKhoachBquanHnamMatHangResp.class);

				}

				// set thoc gao
				QlnvKhvonphiKhoachBquanHnamThocGao qlnvThocGao = new ModelMapper().map(cTietBcao,
						QlnvKhvonphiKhoachBquanHnamThocGao.class);
				qlnvThocGao.setQlnvKhvonphiId(createCheck.getId());
				qlnvThocGao.setId(null);

				// set chi tiet
				for (Object item2 : cTietBcao.getLstCTiet()) {
					QlnvKhvonphiKhoachBquanHnamMatHang qlnvMatHang = new ModelMapper().map(item2,
							QlnvKhvonphiKhoachBquanHnamMatHang.class);
					qlnvMatHang.setQlnvKhvonphiId(createCheck.getId());
					qlnvMatHang.setId(null);
					lstQlnvMatHang.add(qlnvMatHang);
				}

				qlnvKhvonphiKhoachBquanHnamThocGaoRepository.save(qlnvThocGao);
				qlnvKhvonphiKhoachBquanHnamMatHangRepository.saveAll(lstQlnvMatHang);

			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
				ArrayList<QlnvKhvonphiNcauXuatDtqgVtroHnam> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiNcauXuatDtqgVtroHnam cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiNcauXuatDtqgVtroHnam.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiNcauXuatDtqgVtroHnamRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
				ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachQuyTienLuongGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiKhoachQuyTienLuongGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiKhoachQuyTienLuongGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
				ArrayList<QlnvKhvonphiKhoachQuyTienLuongHnam> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachQuyTienLuongHnam cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiKhoachQuyTienLuongHnam.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiKhoachQuyTienLuongHnamRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
				ArrayList<QlnvKhvonphiChiDtaiDanNckhGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiChiDtaiDanNckhGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiChiDtaiDanNckhGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiChiDtaiDanNckhGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
				ArrayList<QlnvKhvonphiVbanQphamPluatDtqgGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiVbanQphamPluatDtqgGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiVbanQphamPluatDtqgGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
				ArrayList<QlnvKhvonphiChiUdungCnttGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiChiUdungCnttGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiChiUdungCnttGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiChiUdungCnttGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
				ArrayList<QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
				ArrayList<QlnvKhvonphiNcauChiNsnnGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiNcauChiNsnnGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiNcauChiNsnnGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiNcauChiNsnnGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
				ArrayList<QlnvKhvonphiChiTxGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiChiTxGd3n cTietBcao = new ModelMapper().map(item, QlnvKhvonphiChiTxGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiChiTxGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
				ArrayList<QlnvKhvonphiNcauPhiNhapXuatGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiNcauPhiNhapXuatGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiNcauPhiNhapXuatGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiNcauPhiNhapXuatGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
				ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachCtaoSchuaGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiKhoachCtaoSchuaGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiKhoachCtaoSchuaGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
				ArrayList<QlnvKhvonphiKhoachDtaoBoiDuongGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachDtaoBoiDuongGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiKhoachDtaoBoiDuongGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository.saveAll(dataMap);
			}

			// TC
			else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
				ArrayList<QlnvKhvonphiTcNcauKhoachDtxdGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcNcauKhoachDtxdGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcNcauKhoachDtxdGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
				ArrayList<QlnvKhvonphiTcThopDtoanChiTxHnam> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopDtoanChiTxHnam cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcThopDtoanChiTxHnam.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcThopDtoanChiTxHnamRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18

				QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp cTietBcao = new QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp();
				for (Object item : objReq.getLstCTietBCao()) {

					// lấy chi tiết báo cáo
					cTietBcao = new ModelMapper().map(item, QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp.class);

					QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam phiNxuatDtqgHnam = new ModelMapper().map(cTietBcao,
							QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam.class);
					phiNxuatDtqgHnam.setQlnvKhvonphiId(createCheck.getId());
					phiNxuatDtqgHnam.setId(null);
					QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam dtqgHnam = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository
							.save(phiNxuatDtqgHnam);
					// set chi tiet
					ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> nxuatDtqgVtuTbiHnams = new ArrayList<>();
					for (Object item2 : cTietBcao.getLstTcDtoanPhiNxuatDtqgVtuTbiHnams()) {
						QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam qlnvVattu = new ModelMapper().map(item2,
								QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam.class);
						qlnvVattu.setNxuatDtqgHnamId(dtqgHnam.getId());
						qlnvVattu.setId(null);
						nxuatDtqgVtuTbiHnams.add(qlnvVattu);
					}
					qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository.saveAll(nxuatDtqgVtuTbiHnams);
				}

			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19

				QlnvKhvonphiTcKhoachBquanHnamResp cTietBcao = new QlnvKhvonphiTcKhoachBquanHnamResp();
				for (Object item : objReq.getLstCTietBCao()) {
					// lấy chi tiết báo cáo
					cTietBcao = new ModelMapper().map(item, QlnvKhvonphiTcKhoachBquanHnamResp.class);

					QlnvKhvonphiTcKhoachBquanHnam bquanHnam = new ModelMapper().map(cTietBcao,
							QlnvKhvonphiTcKhoachBquanHnam.class);
					bquanHnam.setQlnvKhvonphiId(createCheck.getId());
					bquanHnam.setId(null);
					QlnvKhvonphiTcKhoachBquanHnam bquanHnam1 = qlnvKhvonphiTcKhoachBquanHnamRepository
							.save(bquanHnam);
					// set chi tiet
					ArrayList<QlnvKhvonphiTcKhoachBquanTbiVtuHnam> bquanTbiVtuHnams = new ArrayList<>();
					for (Object item2 : cTietBcao.getLstTcKhoachBquanTbiVtuHnams()) {
						QlnvKhvonphiTcKhoachBquanTbiVtuHnam qlnvVattu = new ModelMapper().map(item2,
								QlnvKhvonphiTcKhoachBquanTbiVtuHnam.class);
						qlnvVattu.setBquanHnamId(bquanHnam1.getId());
						qlnvVattu.setId(null);
						bquanTbiVtuHnams.add(qlnvVattu);
					}
					qlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository.saveAll(bquanTbiVtuHnams);
				}
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
				ArrayList<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
				ArrayList<QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
				ArrayList<QlnvKhvonphiTcKhoachcQuyLuongN1> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcKhoachcQuyLuongN1 cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcKhoachcQuyLuongN1.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcKhoachcQuyLuongN1Repository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
				ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanChiDtqgGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcDtoanChiDtqgGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiDtoanChiDtqgGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
				ArrayList<QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
				ArrayList<QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
				ArrayList<QlnvKhvonphiTcDtoanChiUdungCnttGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanChiUdungCnttGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcDtoanChiUdungCnttGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
				ArrayList<QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
				ArrayList<QlnvKhvonphiTcThopNcauChiNsnnGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopNcauChiNsnnGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcThopNcauChiNsnnGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
				ArrayList<QlnvKhvonphiTcThopNncauChiTxGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopNncauChiTxGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcThopNncauChiTxGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcThopNncauChiTxGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
				ArrayList<QlnvKhvonphiTcCtietNcauChiTxGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcCtietNcauChiTxGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcCtietNcauChiTxGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcCtietNcauChiTxGd3nRepository.saveAll(dataMap);
			} else if (maloaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
				ArrayList<QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n> dataMap = new ArrayList<>();
				for (Object item : objReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n.class);
					cTietBcao.setQlnvKhvonphiId(createCheck.getId());
					dataMap.add(cTietBcao);
				}
				qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository.saveAll(dataMap);
			}

//			resp.setData(qlnvKhvonphi);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tổng hợp báo cáo", response = List.class)
	@PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> synthesis(@Valid @RequestBody TongHopReq objReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			String maLoaiBcao = objReq.getMaLoaiBcao();
			String namHientai = objReq.getNamHienTai();
			String maDvi = objReq.getMaDvi();

			Object lstCTietBcao = null;
			// check loại báo cáo cần tổng hợp
			// Chi cục và cục khu vực
			if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
				lstCTietBcao = qlnvKhvonphiDmVondtXdcbgd3nRepository.synthesis(maDvi, namHientai);
			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
				QlnvKhvonphiNxuatDtqgHnamVattuEntity qlnvKhvonphiEntity = new ModelMapper().map(
						qlnvKhvonphiNxuatDtqgHnamThocGaoRepository.synthesis(maDvi, namHientai),
						QlnvKhvonphiNxuatDtqgHnamVattuEntity.class);
				qlnvKhvonphiEntity.setLstCTiet(qlnvKhvonphiNxuatDtqgHnamVattuRepository.synthesis(maDvi, namHientai));
				lstCTietBcao = qlnvKhvonphiEntity;

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
				QlnvKhvonphiKhoachBquanHnamMatHangEntity qlnvMatHangEntity = new ModelMapper().map(
						qlnvKhvonphiKhoachBquanHnamThocGaoRepository.synthesis(maDvi, namHientai),
						QlnvKhvonphiKhoachBquanHnamMatHangEntity.class);
				qlnvMatHangEntity
						.setLstCTiet(qlnvKhvonphiKhoachBquanHnamMatHangRepository.synthesis(maDvi, namHientai));
				lstCTietBcao = qlnvMatHangEntity;

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
				lstCTietBcao = qlnvKhvonphiNcauXuatDtqgVtroHnamRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
				lstCTietBcao = qlnvKhvonphiKhoachQuyTienLuongGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
				lstCTietBcao = qlnvKhvonphiKhoachQuyTienLuongHnamRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
				lstCTietBcao = qlnvKhvonphiChiDtaiDanNckhGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
				lstCTietBcao = qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
				lstCTietBcao = qlnvKhvonphiChiUdungCnttGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
				lstCTietBcao = qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
				lstCTietBcao = qlnvKhvonphiNcauChiNsnnGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
				// querry tổng chi tiết báo cáo theo đơ vị trực thuộc cục khu vực
				lstCTietBcao = qlnvKhvonphiChiTxGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
				lstCTietBcao = qlnvKhvonphiNcauPhiNhapXuatGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
				lstCTietBcao = qlnvKhvonphiKhoachCtaoSchuaGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
				lstCTietBcao = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository.synthesis(maDvi, namHientai);

			}
			// tổng cục
			else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
				//Sử dụng dữ liệu báo cáo 01
				lstCTietBcao = qlnvKhvonphiDmVondtXdcbgd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
				//TODO

				lstCTietBcao = qlnvKhvonphiTcThopDtoanChiTxHnamRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18
				//Sử dụng dữ liệu báo cáo 02
				//thống nhất lại với nghiệp vụ
				//TODO
				lstCTietBcao = qlnvKhvonphiNxuatDtqgHnamVattuRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19
				//Sử dụng dữ liệu báo cáo 03
				//thống nhất lại với nghiệp vụ
				//TODO
				lstCTietBcao = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
				//Sử dụng dữ liệu báo cáo 04
				lstCTietBcao = qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
				//Sử dụng dữ liệu báo cáo 14
				lstCTietBcao = qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
				//Sử dụng dữ liệu báo cáo 06
				lstCTietBcao = qlnvKhvonphiTcKhoachcQuyLuongN1Repository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
				//người dùng tự tổng hợp
//				lstCTietBcao = qlnvKhvonphiDtoanChiDtqgGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
				//Sử dụng dữ liệu báo cáo 07
				lstCTietBcao = qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
				//Sử dụng dữ liệu báo cáo 08
				lstCTietBcao = qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
				//Sử dụng dữ liệu báo cáo 09
				lstCTietBcao = qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
				//Sử dụng dữ liệu báo cáo 10
				lstCTietBcao = qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
				//Sử dụng dữ liệu báo cáo 11
				lstCTietBcao = qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
				//Sử dụng dữ liệu báo cáo 12
				lstCTietBcao = qlnvKhvonphiTcThopNncauChiTxGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
				//Sử dụng dữ liệu báo cáo 12, cộng dồn cột Nhu cầu
				lstCTietBcao = qlnvKhvonphiTcCtietNcauChiTxGd3nRepository.synthesis(maDvi, namHientai);

			} else if (maLoaiBcao.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
				//người dùng tự tổng hợp
//				lstCTietBcao = qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository.synthesis(maDvi, namHientai);
			}

			resp.setData(lstCTietBcao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (

		Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xóa báo cáo", response = List.class)
	@GetMapping(value = "/xoa/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids)) {
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}

			Optional<QlnvKhvonphi> optionalBC = qlnvBaoCaoRepository.findById(Long.parseLong(ids));

			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("Mã ID: " + ids + " của báo cáo không tồn tại");
			}
			QlnvKhvonphi baoCao = optionalBC.get();
			UserInfo userInfo = getUserInfo(req);

//			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonviRepository.findByMaDvi(baoCao.getMaDvi());
			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(String.valueOf(baoCao.getMaDvi()));
			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			// check khác đơn vị
			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			// check với trạng thái hiện tại không được xóa
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!baoCao.getTrangThai().equals(Constants.TT_BC_1)) {
						throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền xóa!");
				}
			}

			qlnvBaoCaoRepository.delete(optionalBC.get());

			// Xóa file dinh kem
			ArrayList<FileDinhKem> fileDinhKems = fileDinhKemRepository
					.findFileDinhKemByQlnvId(optionalBC.get().getId());

			if (!fileDinhKems.isEmpty()) {
				fileDinhKemRepository.deleteAll(fileDinhKems);
			}

			// check loại báo cáo cần tổng hợp
			if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
				ArrayList<QlnvKhvonphiDmVondtXdcbgd3n> arrayList = qlnvKhvonphiDmVondtXdcbgd3nRepository
						.findQlnvKhvonphiDmVondtXdcbgd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiDmVondtXdcbgd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
				QlnvKhvonphiNxuatDtqgHnamThocGao nxuatDtqgHnamThocGao = qlnvKhvonphiNxuatDtqgHnamThocGaoRepository
						.findQlnvKhvonphiNxuatDtqgHnamThocGaoByQlnvKhvonphiId(optionalBC.get().getId());

				if (nxuatDtqgHnamThocGao != null) {
					qlnvKhvonphiNxuatDtqgHnamThocGaoRepository.delete(nxuatDtqgHnamThocGao);
				}

				ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu> arrayList = qlnvKhvonphiNxuatDtqgHnamVattuRepository
						.findQlnvKhvonphiNxuatDtqgHnamVattuByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiNxuatDtqgHnamVattuRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
				QlnvKhvonphiKhoachBquanHnamThocGao khoachBquanHnamThocGao = qlnvKhvonphiKhoachBquanHnamThocGaoRepository
						.findQlnvKhvonphiKhoachBquanHnamThocGaoByQlnvKhvonphiId(optionalBC.get().getId());

				if (khoachBquanHnamThocGao != null) {
					qlnvKhvonphiKhoachBquanHnamThocGaoRepository.delete(khoachBquanHnamThocGao);
				}

				ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> arrayList = qlnvKhvonphiKhoachBquanHnamMatHangRepository
						.findQlnvKhvonphiKhoachBquanHnamMatHangByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiKhoachBquanHnamMatHangRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
				ArrayList<QlnvKhvonphiNcauXuatDtqgVtroHnam> arrayList = qlnvKhvonphiNcauXuatDtqgVtroHnamRepository
						.findQlnvKhvonphiNcauXuatDtqgVtroHnamByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiNcauXuatDtqgVtroHnamRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
				ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n> arrayList = qlnvKhvonphiKhoachQuyTienLuongGd3nRepository
						.findQlnvKhvonphiKhoachQuyTienLuongGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiKhoachQuyTienLuongGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
				ArrayList<QlnvKhvonphiKhoachQuyTienLuongHnam> arrayList = qlnvKhvonphiKhoachQuyTienLuongHnamRepository
						.findQlnvKhvonphiKhoachQuyTienLuongHnamByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiKhoachQuyTienLuongHnamRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
				ArrayList<QlnvKhvonphiChiDtaiDanNckhGd3n> arrayList = qlnvKhvonphiChiDtaiDanNckhGd3nRepository
						.findQlnvKhvonphiChiDtaiDanNckhGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiChiDtaiDanNckhGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
				ArrayList<QlnvKhvonphiVbanQphamPluatDtqgGd3n> arrayList = qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository
						.findQlnvKhvonphiVbanQphamPluatDtqgGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
				ArrayList<QlnvKhvonphiChiUdungCnttGd3n> arrayList = qlnvKhvonphiChiUdungCnttGd3nRepository
						.findQlnvKhvonphiChiUdungCnttGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiChiUdungCnttGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
				ArrayList<QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n> arrayList = qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository
						.findQlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
				ArrayList<QlnvKhvonphiNcauChiNsnnGd3n> arrayList = qlnvKhvonphiNcauChiNsnnGd3nRepository
						.findQlnvKhvonphiNcauChiNsnnGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiNcauChiNsnnGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
				ArrayList<QlnvKhvonphiChiTxGd3n> arrayList = qlnvKhvonphiChiTxGd3nRepository
						.findQlnvKhvonphiChiTxGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiChiTxGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
				ArrayList<QlnvKhvonphiNcauPhiNhapXuatGd3n> arrayList = qlnvKhvonphiNcauPhiNhapXuatGd3nRepository
						.findQlnvKhvonphiNcauPhiNhapXuatGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiNcauPhiNhapXuatGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
				ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n> arrayList = qlnvKhvonphiKhoachCtaoSchuaGd3nRepository
						.findQlnvKhvonphiKhoachCtaoSchuaGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiKhoachCtaoSchuaGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
				ArrayList<QlnvKhvonphiKhoachDtaoBoiDuongGd3n> arrayList = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository
						.findQlnvKhvonphiKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(optionalBC.get().getId());

				if (!arrayList.isEmpty()) {
					qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository.deleteAll(arrayList);
				}
			}
			// TC
			else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
				ArrayList<QlnvKhvonphiTcNcauKhoachDtxdGd3n> arrayList = qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository
						.findQlnvKhvonphiTcNcauKhoachDtxdGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
				ArrayList<QlnvKhvonphiTcThopDtoanChiTxHnam> arrayList = qlnvKhvonphiTcThopDtoanChiTxHnamRepository
						.findQlnvKhvonphiTcThopDtoanChiTxHnamByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcThopDtoanChiTxHnamRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18
//				lstCTietBcao = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository
//						.findQlnvKhvonphiKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19
//				lstCTietBcao = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository
//						.findQlnvKhvonphiKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
				ArrayList<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam> arrayList = qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository
						.findQlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
				ArrayList<QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n> arrayList = qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository
						.findQlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nByQlnvKhvonphiId(
								optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
				ArrayList<QlnvKhvonphiTcKhoachcQuyLuongN1> arrayList = qlnvKhvonphiTcKhoachcQuyLuongN1Repository
						.findQlnvKhvonphiTcKhoachcQuyLuongN1ByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcKhoachcQuyLuongN1Repository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
				ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n> arrayList = qlnvKhvonphiDtoanChiDtqgGd3nRepository
						.findQlnvKhvonphiTcDtoanChiDtqgGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiDtoanChiDtqgGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
				ArrayList<QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n> arrayList = qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository
						.findQlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
				ArrayList<QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n> arrayList = qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository
						.findQlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
				ArrayList<QlnvKhvonphiTcDtoanChiUdungCnttGd3n> arrayList = qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository
						.findQlnvKhvonphiTcDtoanChiUdungCnttGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
				ArrayList<QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n> arrayList = qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository
						.findQlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
				ArrayList<QlnvKhvonphiTcThopNcauChiNsnnGd3n> arrayList = qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository
						.findQlnvKhvonphiTcThopNcauChiNsnnGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
				ArrayList<QlnvKhvonphiTcThopNncauChiTxGd3n> arrayList = qlnvKhvonphiTcThopNncauChiTxGd3nRepository
						.findQlnvKhvonphiTcThopNncauChiTxGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcThopNncauChiTxGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
				ArrayList<QlnvKhvonphiTcCtietNcauChiTxGd3n> arrayList = qlnvKhvonphiTcCtietNcauChiTxGd3nRepository
						.findQlnvKhvonphiTcCtietNcauChiTxGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcCtietNcauChiTxGd3nRepository.deleteAll(arrayList);
				}
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
				ArrayList<QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n> arrayList = qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository
						.findQlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nByQlnvKhvonphiId(optionalBC.get().getId());
				if (!arrayList.isEmpty()) {
					qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository.deleteAll(arrayList);
				}
			}

			resp.setData(optionalBC);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa báo cáo chi tiết", response = List.class)
	@PutMapping(value = "/cap-nhat", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiReq qlnvBaoCaoReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			Optional<QlnvKhvonphi> optionalBC = qlnvBaoCaoRepository.findById(qlnvBaoCaoReq.getId());


			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException(
						"Mã ID: " + qlnvBaoCaoReq.getId() + " của báo cáo không tồn tại");
			}
			QlnvKhvonphi baoCao = optionalBC.get();
			UserInfo userInfo = getUserInfo(req);

//			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonviRepository.findByMaDvi(baoCao.getMaDvi());
			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(String.valueOf(baoCao.getMaDvi()));
			if (qlnvDmDonvi == null) {
				throw new UnsupportedOperationException("Mã đơn vị không tồn tại");
			}

			// check khác đơn vị
			if (!qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			// check với trạng thái hiện tại không được sửa
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!baoCao.getTrangThai().equals(Constants.TT_BC_1)
							&& !baoCao.getTrangThai().equals(Constants.TT_BC_3)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					if (!baoCao.getTrangThai().equals(Constants.TT_BC_5)) {
						throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
					}
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					throw new UnsupportedOperationException("Người dùng không có quyền sửa!");
				}
			}

			baoCao.setNguoiSua(userInfo.getUsername());
			baoCao.setNgaySua(new Date());

			// Cập nhật file theo chỉnh sửa
			if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdFiles())) {
				List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdFiles().split(",")).stream()
						.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				fileDinhKemRepository.deleteWithIds(items);
			}
			ArrayList<FileDinhKem> fileDinhKems = new ArrayList<>();
			for (FileDinhKemReq dinhKemReq : qlnvBaoCaoReq.getFileDinhKems()) {
				FileDinhKem dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKem.class);
				dinhKem.setCreateDate(new Date());
				dinhKem.setDataType(Constants.QLNV_KH_VON_PHI);
				dinhKem.setQlnvId(baoCao.getId());
				fileDinhKems.add(dinhKem);
			}
			fileDinhKemRepository.saveAll(fileDinhKems);

			// check loại báo cáo cần sửa
			if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N)) {// 01
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiDmVondtXdcbgd3nRepository.deleteWithIds(items);
				}
				ArrayList<QlnvKhvonphiDmVondtXdcbgd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiDmVondtXdcbgd3n dmVondtXdcbgd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiDmVondtXdcbgd3n.class);
					// Check id tồn tại thì update
					if (dmVondtXdcbgd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiDmVondtXdcbgd3n> optional = qlnvKhvonphiDmVondtXdcbgd3nRepository
								.findById(dmVondtXdcbgd3nUpdate.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiDmVondtXdcbgd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
									QlnvKhvonphiDmVondtXdcbgd3n.class);
							cTietBcao.setQlnvKhvonphiId(baoCao.getId());
							arrayList.add(cTietBcao);
						}
						QlnvKhvonphiDmVondtXdcbgd3n dmVondtXdcbgd3n = optional.get();
						dmVondtXdcbgd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(dmVondtXdcbgd3n, dmVondtXdcbgd3nUpdate);
						arrayList.add(dmVondtXdcbgd3n);
					} else {
						// thêm mới
						QlnvKhvonphiDmVondtXdcbgd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiDmVondtXdcbgd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);

					}
				}
				qlnvKhvonphiDmVondtXdcbgd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU)) {// 02
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiNxuatDtqgHnamVattuRepository.deleteWithIds(items);
				}

				ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu> lstVattu = new ArrayList<>();
				QlnvKhvonphiNxuatDtqgHnamThocGao qlnvThocGao = new QlnvKhvonphiNxuatDtqgHnamThocGao();
				int i = 0;
				for (Object item : qlnvBaoCaoReq.getLstCTietBCao()) {
					i++;
					// lấy chi tiết báo cáo
					QlnvKhvonphiNxuatDtqgHnamVattuResp cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiNxuatDtqgHnamVattuResp.class);
					if (i == 1) {
						// set thoc gao
						QlnvKhvonphiNxuatDtqgHnamThocGao qlnvThocGaoUpdate = new ModelMapper().map(cTietBcao,
								QlnvKhvonphiNxuatDtqgHnamThocGao.class);
						Optional<QlnvKhvonphiNxuatDtqgHnamThocGao> optional = qlnvKhvonphiNxuatDtqgHnamThocGaoRepository
								.findById(cTietBcao.getId());
						if (!optional.isPresent()) {
							throw new UnsupportedOperationException(
									"Mã ID: " + cTietBcao.getId() + "của báo cáo chi tiết không tồn tại");
						}
						qlnvThocGao = optional.get();
						qlnvThocGao.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(qlnvThocGao, qlnvThocGaoUpdate);
						qlnvThocGao.setId(null);
					}
					// set chi tiet
					for (Object item2 : cTietBcao.getLstCTiet()) {
						QlnvKhvonphiNxuatDtqgHnamVattu nxuatDtqgHnamVattuUpdate = new ModelMapper().map(item2,
								QlnvKhvonphiNxuatDtqgHnamVattu.class);
						// Check id tồn tại thì update
						if (nxuatDtqgHnamVattuUpdate.getId() != null) {
							Optional<QlnvKhvonphiNxuatDtqgHnamVattu> optional1 = qlnvKhvonphiNxuatDtqgHnamVattuRepository
									.findById(nxuatDtqgHnamVattuUpdate.getId());
							QlnvKhvonphiNxuatDtqgHnamVattu nxuatDtqgHnamVattu = optional1.get();
							nxuatDtqgHnamVattu.setQlnvKhvonphiId(baoCao.getId());
							updateObjectToObject(nxuatDtqgHnamVattu, nxuatDtqgHnamVattuUpdate);
							lstVattu.add(nxuatDtqgHnamVattu);
						} else {
							// thêm mới
							QlnvKhvonphiNxuatDtqgHnamVattu qNxuatDtqgHnamVattu = new ModelMapper().map(item2,
									QlnvKhvonphiNxuatDtqgHnamVattu.class);
							qNxuatDtqgHnamVattu.setQlnvKhvonphiId(baoCao.getId());
							lstVattu.add(qNxuatDtqgHnamVattu);
						}
					}
				}
				qlnvKhvonphiNxuatDtqgHnamThocGaoRepository.save(qlnvThocGao);
				qlnvKhvonphiNxuatDtqgHnamVattuRepository.saveAll(lstVattu);

			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG)) {// 03
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiKhoachBquanHnamMatHangRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> lstMatHangs = new ArrayList<>();
				QlnvKhvonphiKhoachBquanHnamThocGao qlnvThocGao = new QlnvKhvonphiKhoachBquanHnamThocGao();
				int i = 0;
				for (Object item : qlnvBaoCaoReq.getLstCTietBCao()) {
					i++;
					// lấy chi tiết báo cáo
					QlnvKhvonphiKhoachBquanHnamMatHangResp cTietBcao = new ModelMapper().map(item,
							QlnvKhvonphiKhoachBquanHnamMatHangResp.class);
					if (i == 1) {
						QlnvKhvonphiKhoachBquanHnamThocGao qlnvThocGaoUpdate = new ModelMapper().map(cTietBcao,
								QlnvKhvonphiKhoachBquanHnamThocGao.class);
						Optional<QlnvKhvonphiKhoachBquanHnamThocGao> optional = qlnvKhvonphiKhoachBquanHnamThocGaoRepository
								.findById(cTietBcao.getId());
						if (!optional.isPresent()) {
							throw new UnsupportedOperationException(
									"Mã ID: " + cTietBcao.getId() + "của báo cáo chi tiết không tồn tại");
						}
						// set thoc gao
						qlnvThocGao = optional.get();
						qlnvThocGao.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(qlnvThocGao, qlnvThocGaoUpdate);
						qlnvThocGao.setId(null);
					}
					// set chi tiet
					for (Object item2 : cTietBcao.getLstCTiet()) {
						QlnvKhvonphiKhoachBquanHnamMatHang nxuatDtqgHnamVattuUpdate = new ModelMapper().map(item2,
								QlnvKhvonphiKhoachBquanHnamMatHang.class);
						// Check id tồn tại thì update
						if (nxuatDtqgHnamVattuUpdate.getId() != null) {
							Optional<QlnvKhvonphiKhoachBquanHnamMatHang> optional1 = qlnvKhvonphiKhoachBquanHnamMatHangRepository
									.findById(nxuatDtqgHnamVattuUpdate.getId());
							QlnvKhvonphiKhoachBquanHnamMatHang hnamMatHang = optional1.get();
							hnamMatHang.setQlnvKhvonphiId(baoCao.getId());
							updateObjectToObject(hnamMatHang, nxuatDtqgHnamVattuUpdate);
							lstMatHangs.add(hnamMatHang);
						} else {
							// thêm mới
							QlnvKhvonphiKhoachBquanHnamMatHang qNxuatDtqgHnamVattu = new ModelMapper().map(item2,
									QlnvKhvonphiKhoachBquanHnamMatHang.class);
							qNxuatDtqgHnamVattu.setQlnvKhvonphiId(baoCao.getId());
							lstMatHangs.add(qNxuatDtqgHnamVattu);
						}

					}
				}
				qlnvKhvonphiKhoachBquanHnamThocGaoRepository.save(qlnvThocGao);
				qlnvKhvonphiKhoachBquanHnamMatHangRepository.saveAll(lstMatHangs);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM)) {// 04
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiNcauXuatDtqgVtroHnamRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiNcauXuatDtqgVtroHnam> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiNcauXuatDtqgVtroHnam ncauXuatDtqgVtroHnamUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiNcauXuatDtqgVtroHnam.class);
					// Check id tồn tại thì update
					if (ncauXuatDtqgVtroHnamUpdate.getId() != null) {
						Optional<QlnvKhvonphiNcauXuatDtqgVtroHnam> optional = qlnvKhvonphiNcauXuatDtqgVtroHnamRepository
								.findById(ncauXuatDtqgVtroHnamUpdate.getId());
						QlnvKhvonphiNcauXuatDtqgVtroHnam ncauXuatDtqgVtroHnam = optional.get();
						ncauXuatDtqgVtroHnam.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(ncauXuatDtqgVtroHnam, ncauXuatDtqgVtroHnamUpdate);
						arrayList.add(ncauXuatDtqgVtroHnam);
					} else {
						// thêm mới
						QlnvKhvonphiNcauXuatDtqgVtroHnam cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiNcauXuatDtqgVtroHnam.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiNcauXuatDtqgVtroHnamRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N)) {// 05
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiKhoachQuyTienLuongGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {

					QlnvKhvonphiKhoachQuyTienLuongGd3n quyTienLuongGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiKhoachQuyTienLuongGd3n.class);
					// Check id tồn tại thì update
					if (quyTienLuongGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiKhoachQuyTienLuongGd3n> optional = qlnvKhvonphiKhoachQuyTienLuongGd3nRepository
								.findById(quyTienLuongGd3nUpdate.getId());
						QlnvKhvonphiKhoachQuyTienLuongGd3n quyTienLuongGd3n = optional.get();
						quyTienLuongGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(quyTienLuongGd3n, quyTienLuongGd3nUpdate);
						arrayList.add(quyTienLuongGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiKhoachQuyTienLuongGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiKhoachQuyTienLuongGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiKhoachQuyTienLuongGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM)) {// 06
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiKhoachQuyTienLuongHnamRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiKhoachQuyTienLuongHnam> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachQuyTienLuongHnam quyTienLuongHnamUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiKhoachQuyTienLuongHnam.class);
					// Check id tồn tại thì update
					if (quyTienLuongHnamUpdate.getId() != null) {
						Optional<QlnvKhvonphiKhoachQuyTienLuongHnam> optional = qlnvKhvonphiKhoachQuyTienLuongHnamRepository
								.findById(quyTienLuongHnamUpdate.getId());
						QlnvKhvonphiKhoachQuyTienLuongHnam quyTienLuongHnam = optional.get();
						quyTienLuongHnam.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(quyTienLuongHnam, quyTienLuongHnamUpdate);
						arrayList.add(quyTienLuongHnam);
					} else {
						// thêm mới
						QlnvKhvonphiKhoachQuyTienLuongHnam cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiKhoachQuyTienLuongHnam.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiKhoachQuyTienLuongHnamRepository.saveAll(arrayList);

			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N)) {// 07
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiChiDtaiDanNckhGd3nRepository.deleteWithIds(items);

				}
				ArrayList<QlnvKhvonphiChiDtaiDanNckhGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiChiDtaiDanNckhGd3n chiDtaiDanNckhGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiChiDtaiDanNckhGd3n.class);
					// Check id tồn tại thì update
					if (chiDtaiDanNckhGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiChiDtaiDanNckhGd3n> optional = qlnvKhvonphiChiDtaiDanNckhGd3nRepository
								.findById(chiDtaiDanNckhGd3nUpdate.getId());
						QlnvKhvonphiChiDtaiDanNckhGd3n chiDtaiDanNckhGd3n = optional.get();
						chiDtaiDanNckhGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(chiDtaiDanNckhGd3n, chiDtaiDanNckhGd3nUpdate);
						arrayList.add(chiDtaiDanNckhGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiChiDtaiDanNckhGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiChiDtaiDanNckhGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiChiDtaiDanNckhGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 08
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiVbanQphamPluatDtqgGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiVbanQphamPluatDtqgGd3n vbanQphamPluatDtqgGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiVbanQphamPluatDtqgGd3n.class);
					// Check id tồn tại thì update
					if (vbanQphamPluatDtqgGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiVbanQphamPluatDtqgGd3n> optional = qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository
								.findById(vbanQphamPluatDtqgGd3nUpdate.getId());
						QlnvKhvonphiVbanQphamPluatDtqgGd3n vbanQphamPluatDtqgGd3n = optional.get();
						vbanQphamPluatDtqgGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(vbanQphamPluatDtqgGd3n, vbanQphamPluatDtqgGd3nUpdate);
						arrayList.add(vbanQphamPluatDtqgGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiVbanQphamPluatDtqgGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiVbanQphamPluatDtqgGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiVbanQphamPluatDtqgGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N)) {// 09
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiChiUdungCnttGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiChiUdungCnttGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiChiUdungCnttGd3n chiUdungCnttGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiChiUdungCnttGd3n.class);
					// Check id tồn tại thì update
					if (chiUdungCnttGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiChiUdungCnttGd3n> optional = qlnvKhvonphiChiUdungCnttGd3nRepository
								.findById(chiUdungCnttGd3nUpdate.getId());
						QlnvKhvonphiChiUdungCnttGd3n chiUdungCnttGd3n = optional.get();
						chiUdungCnttGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(chiUdungCnttGd3n, chiUdungCnttGd3nUpdate);
						arrayList.add(chiUdungCnttGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiChiUdungCnttGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiChiUdungCnttGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiChiUdungCnttGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N)) {// 10
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n dtoanChiMuasamMaymocTbiGd3nUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n.class);
					// Check id tồn tại thì update
					if (dtoanChiMuasamMaymocTbiGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n> optional = qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository
								.findById(dtoanChiMuasamMaymocTbiGd3nUpdate.getId());
						QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n dtoanChiMuasamMaymocTbiGd3n = optional.get();
						dtoanChiMuasamMaymocTbiGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(dtoanChiMuasamMaymocTbiGd3n, dtoanChiMuasamMaymocTbiGd3nUpdate);
						arrayList.add(dtoanChiMuasamMaymocTbiGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiDtoanChiMuasamMaymocTbiGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiDtoanChiMuasamMaymocTbiGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N)) {// 11
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiNcauChiNsnnGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiNcauChiNsnnGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiNcauChiNsnnGd3n ncauChiNsnnGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiNcauChiNsnnGd3n.class);
					// Check id tồn tại thì update
					if (ncauChiNsnnGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiNcauChiNsnnGd3n> optional = qlnvKhvonphiNcauChiNsnnGd3nRepository
								.findById(ncauChiNsnnGd3nUpdate.getId());
						QlnvKhvonphiNcauChiNsnnGd3n ncauChiNsnnGd3n = optional.get();
						ncauChiNsnnGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(ncauChiNsnnGd3n, ncauChiNsnnGd3nUpdate);
						arrayList.add(ncauChiNsnnGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiNcauChiNsnnGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiNcauChiNsnnGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiNcauChiNsnnGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_CHI_TX_GD3N)) {// 12
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiChiTxGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiChiTxGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiChiTxGd3n chiTxGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiChiTxGd3n.class);
					// Check id tồn tại thì update
					if (chiTxGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiChiTxGd3n> optional = qlnvKhvonphiChiTxGd3nRepository
								.findById(chiTxGd3nUpdate.getId());
						QlnvKhvonphiChiTxGd3n chiTxGd3n = optional.get();
						chiTxGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(chiTxGd3n, chiTxGd3nUpdate);
						arrayList.add(chiTxGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiChiTxGd3n cTietBcao = new ModelMapper().map(qlnvCTiet, QlnvKhvonphiChiTxGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiChiTxGd3nRepository.saveAll(arrayList);

			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N)) {// 13
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiNcauPhiNhapXuatGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiNcauPhiNhapXuatGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiNcauPhiNhapXuatGd3n ncauPhiNhapXuatGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiNcauPhiNhapXuatGd3n.class);
					// Check id tồn tại thì update
					if (ncauPhiNhapXuatGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiNcauPhiNhapXuatGd3n> optional = qlnvKhvonphiNcauPhiNhapXuatGd3nRepository
								.findById(ncauPhiNhapXuatGd3nUpdate.getId());
						QlnvKhvonphiNcauPhiNhapXuatGd3n ncauPhiNhapXuatGd3n = optional.get();
						ncauPhiNhapXuatGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(ncauPhiNhapXuatGd3n, ncauPhiNhapXuatGd3nUpdate);
						arrayList.add(ncauPhiNhapXuatGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiNcauPhiNhapXuatGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiNcauPhiNhapXuatGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiNcauPhiNhapXuatGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N)) {// 14
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiKhoachCtaoSchuaGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachCtaoSchuaGd3n khoachCtaoSchuaGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiKhoachCtaoSchuaGd3n.class);
					// Check id tồn tại thì update
					if (khoachCtaoSchuaGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiKhoachCtaoSchuaGd3n> optional = qlnvKhvonphiKhoachCtaoSchuaGd3nRepository
								.findById(khoachCtaoSchuaGd3nUpdate.getId());
						QlnvKhvonphiKhoachCtaoSchuaGd3n khoachCtaoSchuaGd3n = optional.get();
						khoachCtaoSchuaGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khoachCtaoSchuaGd3n, khoachCtaoSchuaGd3nUpdate);
						arrayList.add(khoachCtaoSchuaGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiKhoachCtaoSchuaGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiKhoachCtaoSchuaGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiKhoachCtaoSchuaGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N)) {// 15
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiKhoachDtaoBoiDuongGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiKhoachDtaoBoiDuongGd3n khoachDtaoBoiDuongGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiKhoachDtaoBoiDuongGd3n.class);
					// Check id tồn tại thì update
					if (khoachDtaoBoiDuongGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiKhoachDtaoBoiDuongGd3n> optional = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository
								.findById(khoachDtaoBoiDuongGd3nUpdate.getId());
						QlnvKhvonphiKhoachDtaoBoiDuongGd3n khoachDtaoBoiDuongGd3n = optional.get();
						khoachDtaoBoiDuongGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khoachDtaoBoiDuongGd3n, khoachDtaoBoiDuongGd3nUpdate);
						arrayList.add(khoachDtaoBoiDuongGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiKhoachDtaoBoiDuongGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiKhoachDtaoBoiDuongGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository.saveAll(arrayList);
			}

			// TC
			else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N)) {// 16
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcNcauKhoachDtxdGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcNcauKhoachDtxdGd3n tcNcauKhoachDtxdGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcNcauKhoachDtxdGd3n.class);
					// Check id tồn tại thì update
					if (tcNcauKhoachDtxdGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcNcauKhoachDtxdGd3n> optional = qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository
								.findById(tcNcauKhoachDtxdGd3nUpdate.getId());
						QlnvKhvonphiTcNcauKhoachDtxdGd3n khvonphiTcNcauKhoachDtxdGd3n = optional.get();
						khvonphiTcNcauKhoachDtxdGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcNcauKhoachDtxdGd3n, tcNcauKhoachDtxdGd3nUpdate);
						arrayList.add(khvonphiTcNcauKhoachDtxdGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcNcauKhoachDtxdGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcNcauKhoachDtxdGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcNcauKhoachDtxdGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM)) {// 17
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcThopDtoanChiTxHnamRepository.deleteWithIds(items);
				}

				ArrayList<QlnvKhvonphiTcThopDtoanChiTxHnam> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopDtoanChiTxHnam tcThopDtoanChiTxHnamUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcThopDtoanChiTxHnam.class);
					// Check id tồn tại thì update
					if (tcThopDtoanChiTxHnamUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcThopDtoanChiTxHnam> optional = qlnvKhvonphiTcThopDtoanChiTxHnamRepository
								.findById(tcThopDtoanChiTxHnamUpdate.getId());
						QlnvKhvonphiTcThopDtoanChiTxHnam khvonphiTcNcauKhoachDtxdGd3n = optional.get();
						khvonphiTcNcauKhoachDtxdGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcNcauKhoachDtxdGd3n, tcThopDtoanChiTxHnamUpdate);
						arrayList.add(khvonphiTcNcauKhoachDtxdGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcThopDtoanChiTxHnam cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcThopDtoanChiTxHnam.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcThopDtoanChiTxHnamRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM)) {// 18

				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
//					qlnvKhvonphiTcRepository.deleteWithIds(items);
					for (Long long1 : items) {
						Optional<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam> tCnxuatDHnam = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository
								.findById(long1);

						if (tCnxuatDHnam.isPresent()) {
							qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository.deleteById(tCnxuatDHnam.get().getId());

							ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> arrayList = qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository
									.findQlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamByNxuatDtqgHnamId(
											tCnxuatDHnam.get().getId());

							if (!arrayList.isEmpty()) {
								qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository.deleteAll(arrayList);
							}
						}
					}
				}


				QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp cTietBcao = new QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp();
				for (Object item : qlnvBaoCaoReq.getLstCTietBCao()) {

					// lấy chi tiết báo cáo
					cTietBcao = new ModelMapper().map(item, QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp.class);

					QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam phiNxuatDtqgHnamUpdate = new ModelMapper().map(cTietBcao,
							QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam.class);

					// Check id tồn tại thì update
					if (phiNxuatDtqgHnamUpdate.getId() != null) {

//						QlnvKhvonphiNxuatDtqgHnamThocGao qlnvThocGaoUpdate = new ModelMapper().map(cTietBcao,
//								QlnvKhvonphiNxuatDtqgHnamThocGao.class);

						Optional<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam> optional = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository
								.findById(cTietBcao.getId());
						if (!optional.isPresent()) {
							// thêm mới
							QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam phiNxuatDtqgHnam = new ModelMapper().map(cTietBcao,
									QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam.class);
							phiNxuatDtqgHnam.setQlnvKhvonphiId(baoCao.getId());
							phiNxuatDtqgHnam.setId(null);
							QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam dtqgHnam = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository
									.save(phiNxuatDtqgHnam);
							// set chi tiet
							ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> nxuatDtqgVtuTbiHnams = new ArrayList<>();
							for (Object item2 : cTietBcao.getLstTcDtoanPhiNxuatDtqgVtuTbiHnams()) {
								QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam qlnvVattu = new ModelMapper().map(item2,
										QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam.class);
								qlnvVattu.setNxuatDtqgHnamId(dtqgHnam.getId());
								qlnvVattu.setId(null);
								nxuatDtqgVtuTbiHnams.add(qlnvVattu);
							}
							qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository.saveAll(nxuatDtqgVtuTbiHnams);
						} else {
							// update
							QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam nxuatDtqgHnam = optional.get();
							nxuatDtqgHnam.setQlnvKhvonphiId(baoCao.getId());
							updateObjectToObject(nxuatDtqgHnam, phiNxuatDtqgHnamUpdate);
							qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository.save(nxuatDtqgHnam);

							for (Object item2 : cTietBcao.getLstTcDtoanPhiNxuatDtqgVtuTbiHnams()) {
								QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam nxuatDtqgVtuTbiHnamupdate = new ModelMapper()
										.map(item2, QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam.class);
								Optional<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> optional1 = qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository
										.findById(nxuatDtqgVtuTbiHnamupdate.getId());
								QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam nxuatDtqgVtuTbiHnam = optional1.get();
								nxuatDtqgVtuTbiHnam.setNxuatDtqgHnamId(nxuatDtqgHnam.getId());
								updateObjectToObject(nxuatDtqgVtuTbiHnam, nxuatDtqgVtuTbiHnamupdate);
							}

						}

					} else {
						// thêm mới
						QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam phiNxuatDtqgHnam = new ModelMapper().map(cTietBcao,
								QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam.class);
						phiNxuatDtqgHnam.setQlnvKhvonphiId(baoCao.getId());
						phiNxuatDtqgHnam.setId(null);
						QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam dtqgHnam = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository
								.save(phiNxuatDtqgHnam);
						// set chi tiet
						ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> nxuatDtqgVtuTbiHnams = new ArrayList<>();
						for (Object item2 : cTietBcao.getLstTcDtoanPhiNxuatDtqgVtuTbiHnams()) {
							QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam qlnvVattu = new ModelMapper().map(item2,
									QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam.class);
							qlnvVattu.setNxuatDtqgHnamId(dtqgHnam.getId());
							qlnvVattu.setId(null);
							nxuatDtqgVtuTbiHnams.add(qlnvVattu);
						}
						qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository.saveAll(nxuatDtqgVtuTbiHnams);
					}

				}

			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM)) {// 19
//				lstCTietBcao = qlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository
//						.findQlnvKhvonphiKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(qlnvKhvonphi.getId());




			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM)) {// 20
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam tcDtoanPhiXuatDtqgVtroCtroHnamUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam.class);
					// Check id tồn tại thì update
					if (tcDtoanPhiXuatDtqgVtroCtroHnamUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam> optional = qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository
								.findById(tcDtoanPhiXuatDtqgVtroCtroHnamUpdate.getId());
						QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam khvonphiTcDtoanPhiXuatDtqgVtroCtroHnam = optional
								.get();
						khvonphiTcDtoanPhiXuatDtqgVtroCtroHnam.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcDtoanPhiXuatDtqgVtroCtroHnam,
								tcDtoanPhiXuatDtqgVtroCtroHnamUpdate);
						arrayList.add(khvonphiTcDtoanPhiXuatDtqgVtroCtroHnam);
					} else {
						// thêm mới
						QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N)) {// 21
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n tcKhoachDtoanCtaoSchuaHthongKhoTangGd3nUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n.class);
					// Check id tồn tại thì update
					if (tcKhoachDtoanCtaoSchuaHthongKhoTangGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n> optional = qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository
								.findById(tcKhoachDtoanCtaoSchuaHthongKhoTangGd3nUpdate.getId());
						QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n khvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n = optional
								.get();
						khvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n,
								tcKhoachDtoanCtaoSchuaHthongKhoTangGd3nUpdate);
						arrayList.add(khvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1)) {// 22
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcKhoachcQuyLuongN1Repository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcKhoachcQuyLuongN1> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcKhoachcQuyLuongN1 tcKhoachcQuyLuongN1Update = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcKhoachcQuyLuongN1.class);
					// Check id tồn tại thì update
					if (tcKhoachcQuyLuongN1Update.getId() != null) {
						Optional<QlnvKhvonphiTcKhoachcQuyLuongN1> optional = qlnvKhvonphiTcKhoachcQuyLuongN1Repository
								.findById(tcKhoachcQuyLuongN1Update.getId());
						QlnvKhvonphiTcKhoachcQuyLuongN1 khvonphiTcKhoachcQuyLuongN1 = optional.get();
						khvonphiTcKhoachcQuyLuongN1.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcKhoachcQuyLuongN1, tcKhoachcQuyLuongN1Update);
						arrayList.add(khvonphiTcKhoachcQuyLuongN1);
					} else {
						// thêm mới
						QlnvKhvonphiTcKhoachcQuyLuongN1 cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcKhoachcQuyLuongN1.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcKhoachcQuyLuongN1Repository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N)) {// 23
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiDtoanChiDtqgGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanChiDtqgGd3n tcDtoanChiDtqgGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcDtoanChiDtqgGd3n.class);
					// Check id tồn tại thì update
					if (tcDtoanChiDtqgGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcDtoanChiDtqgGd3n> optional = qlnvKhvonphiDtoanChiDtqgGd3nRepository
								.findById(tcDtoanChiDtqgGd3nUpdate.getId());
						QlnvKhvonphiTcDtoanChiDtqgGd3n khvonphiTcDtoanChiDtqgGd3n = optional.get();
						khvonphiTcDtoanChiDtqgGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcDtoanChiDtqgGd3n, tcDtoanChiDtqgGd3nUpdate);
						arrayList.add(khvonphiTcDtoanChiDtqgGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcDtoanChiDtqgGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcDtoanChiDtqgGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiDtoanChiDtqgGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N)) {// 24
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n tcTminhChiCacDtaiDanNckhGd3nUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n.class);
					// Check id tồn tại thì update
					if (tcTminhChiCacDtaiDanNckhGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n> optional = qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository
								.findById(tcTminhChiCacDtaiDanNckhGd3nUpdate.getId());
						QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n khvonphiTcTminhChiCacDtaiDanNckhGd3n = optional.get();
						khvonphiTcTminhChiCacDtaiDanNckhGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcTminhChiCacDtaiDanNckhGd3n, tcTminhChiCacDtaiDanNckhGd3nUpdate);
						arrayList.add(khvonphiTcTminhChiCacDtaiDanNckhGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N)) {// 25
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n tcKhoachXdungVbanQphamPluatDtqgGd3nUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n.class);
					// Check id tồn tại thì update
					if (tcKhoachXdungVbanQphamPluatDtqgGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n> optional = qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository
								.findById(tcKhoachXdungVbanQphamPluatDtqgGd3nUpdate.getId());
						QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n khvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n = optional
								.get();
						khvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n,
								tcKhoachXdungVbanQphamPluatDtqgGd3nUpdate);
						arrayList.add(khvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N)) {// 26
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcDtoanChiUdungCnttGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanChiUdungCnttGd3n tcDtoanChiUdungCnttGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcDtoanChiUdungCnttGd3n.class);
					// Check id tồn tại thì update
					if (tcDtoanChiUdungCnttGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcDtoanChiUdungCnttGd3n> optional = qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository
								.findById(tcDtoanChiUdungCnttGd3nUpdate.getId());
						QlnvKhvonphiTcDtoanChiUdungCnttGd3n khvonphiTcDtoanChiUdungCnttGd3n = optional.get();
						khvonphiTcDtoanChiUdungCnttGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcDtoanChiUdungCnttGd3n, tcDtoanChiUdungCnttGd3nUpdate);
						arrayList.add(khvonphiTcDtoanChiUdungCnttGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcDtoanChiUdungCnttGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcDtoanChiUdungCnttGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N)) {// 27
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n tcDtoanChiMsamMmocTbiChuyenDungGd3nUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n.class);
					// Check id tồn tại thì update
					if (tcDtoanChiMsamMmocTbiChuyenDungGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n> optional = qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository
								.findById(tcDtoanChiMsamMmocTbiChuyenDungGd3nUpdate.getId());
						QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n khvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n = optional
								.get();
						khvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n,
								tcDtoanChiMsamMmocTbiChuyenDungGd3nUpdate);
						arrayList.add(khvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}

				qlnvKhvonphiTcDtoanChiMsamMmocTbiChuyenDungGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N)) {// 28
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository.deleteWithIds(items);
				}

				ArrayList<QlnvKhvonphiTcThopNcauChiNsnnGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopNcauChiNsnnGd3n tcThopNcauChiNsnnGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcThopNcauChiNsnnGd3n.class);
					// Check id tồn tại thì update
					if (tcThopNcauChiNsnnGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcThopNcauChiNsnnGd3n> optional = qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository
								.findById(tcThopNcauChiNsnnGd3nUpdate.getId());
						QlnvKhvonphiTcThopNcauChiNsnnGd3n khvonphiTcThopNcauChiNsnnGd3n = optional.get();
						khvonphiTcThopNcauChiNsnnGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcThopNcauChiNsnnGd3n, tcThopNcauChiNsnnGd3nUpdate);
						arrayList.add(khvonphiTcThopNcauChiNsnnGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcThopNcauChiNsnnGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcThopNcauChiNsnnGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcThopNcauChiNsnnGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N)) {// 29
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcThopNncauChiTxGd3nRepository.deleteWithIds(items);
				}

				ArrayList<QlnvKhvonphiTcThopNncauChiTxGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopNncauChiTxGd3n tcThopNncauChiTxGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcThopNncauChiTxGd3n.class);
					// Check id tồn tại thì update
					if (tcThopNncauChiTxGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcThopNncauChiTxGd3n> optional = qlnvKhvonphiTcThopNncauChiTxGd3nRepository
								.findById(tcThopNncauChiTxGd3nUpdate.getId());
						QlnvKhvonphiTcThopNncauChiTxGd3n khvonphiTcThopNncauChiTxGd3n = optional.get();
						khvonphiTcThopNncauChiTxGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcThopNncauChiTxGd3n, tcThopNncauChiTxGd3nUpdate);
						arrayList.add(khvonphiTcThopNncauChiTxGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcThopNncauChiTxGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcThopNncauChiTxGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcThopNncauChiTxGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao().equals(Constants.QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N)) {// 30
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcCtietNcauChiTxGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcCtietNcauChiTxGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcCtietNcauChiTxGd3n tcCtietNcauChiTxGd3nUpdate = new ModelMapper().map(qlnvCTiet,
							QlnvKhvonphiTcCtietNcauChiTxGd3n.class);
					// Check id tồn tại thì update
					if (tcCtietNcauChiTxGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcCtietNcauChiTxGd3n> optional = qlnvKhvonphiTcCtietNcauChiTxGd3nRepository
								.findById(tcCtietNcauChiTxGd3nUpdate.getId());
						QlnvKhvonphiTcCtietNcauChiTxGd3n khvonphiTcCtietNcauChiTxGd3n = optional.get();
						khvonphiTcCtietNcauChiTxGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcCtietNcauChiTxGd3n, tcCtietNcauChiTxGd3nUpdate);
						arrayList.add(khvonphiTcCtietNcauChiTxGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcCtietNcauChiTxGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcCtietNcauChiTxGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}

				qlnvKhvonphiTcCtietNcauChiTxGd3nRepository.saveAll(arrayList);
			} else if (optionalBC.get().getMaLoaiBcao()
					.equals(Constants.QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N)) {// 31
				if (!StringUtils.isEmpty(qlnvBaoCaoReq.getListIdDeletes())) {
					List<Long> items = Arrays.asList(qlnvBaoCaoReq.getListIdDeletes().split(",")).stream()
							.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
					qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository.deleteWithIds(items);

				}

				ArrayList<QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n> arrayList = new ArrayList<>();
				for (Object qlnvCTiet : qlnvBaoCaoReq.getLstCTietBCao()) {
					QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n tcThopMtieuNvuCyeuNcauChiMoiGd3nUpdate = new ModelMapper()
							.map(qlnvCTiet, QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n.class);
					// Check id tồn tại thì update
					if (tcThopMtieuNvuCyeuNcauChiMoiGd3nUpdate.getId() != null) {
						Optional<QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n> optional = qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository
								.findById(tcThopMtieuNvuCyeuNcauChiMoiGd3nUpdate.getId());
						QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n khvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n = optional
								.get();
						khvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n.setQlnvKhvonphiId(baoCao.getId());
						updateObjectToObject(khvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n,
								tcThopMtieuNvuCyeuNcauChiMoiGd3nUpdate);
						arrayList.add(khvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n);
					} else {
						// thêm mới
						QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n cTietBcao = new ModelMapper().map(qlnvCTiet,
								QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n.class);
						cTietBcao.setQlnvKhvonphiId(baoCao.getId());
						arrayList.add(cTietBcao);
					}
				}
				qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository.saveAll(arrayList);
			}

			baoCao = qlnvBaoCaoRepository.save(baoCao);

//			QlnvKhvonphiResp qlnvBaoCao = new ModelMapper().map(baoCao, QlnvKhvonphiResp.class);
//			qlnvBaoCao.setLstCTietBCao(object);

//			resp.setData(qlnvBaoCao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sinh mã báo cáo", response = List.class)
	@GetMapping(value = PathConstants.URL_SINH_MA_BCAO, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> genMaBcao() {
		Resp resp = new Resp();
		try {
			int index = qlnvBaoCaoRepository.getMaBaoCao();
			String maBaoCao = "BC".concat(String.valueOf(index));
			resp.setData(maBaoCao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}

	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq, HttpServletRequest req) {
		Resp resp = new Resp();
		try {
			UserInfo userInfo = getUserInfo(req);

			Optional<QlnvKhvonphi> optionalBC = qlnvBaoCaoRepository.findById(objReq.getId());
			if (!optionalBC.isPresent()) {
				throw new UnsupportedOperationException("ID Báo cáo không tồn tại!");
			}
			QlnvKhvonphi qlnvBaoCao = optionalBC.get();
//			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonviRepository.findByMaDvi(qlnvBaoCao.getMaDvi());
			QlnvDmDonvi qlnvDmDonvi = qlnvDmDonViService.getDonViById(String.valueOf(qlnvBaoCao.getMaDvi()));
			// nếu là tổng cục set type
			boolean isTongCuc = false;
			if (qlnvDmDonvi.getCapDvi().equals(1l)) {
				isTongCuc = true;
			}

			if (qlnvBaoCao.getTrangThai().equals(Constants.TT_BC_6) && isTongCuc) {
				// trạng thái cuối của Tổng cục
				throw new UnsupportedOperationException("Báo cáo đã ở trạng thái: Lãnh đạo duyệt!");
			} else if (qlnvBaoCao.getTrangThai().equals(Constants.TT_BC_9) && !isTongCuc) {
				// trạng thái cuối của chi cục và cục khu vực
				throw new UnsupportedOperationException("Báo cáo đã ở trạng thái: Đơn vị cấp trên duyệt!");
			}

			// check user thuộc đơn vị hoặc đơn vị cha
			// user cùng đơn vị
			if (qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
				// check người dùng có quyền thao tác với action tương ứng hay ko
				for (Role role : userInfo.getRoles()) {
					if (role.getId().equals(Constants.NHAN_VIEN)) {
						if (!objReq.getMaChucNang().equals(Constants.TT_BC_1)
								&& !objReq.getMaChucNang().equals(Constants.TT_BC_2)) {
							throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
						}
					} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
						if (!objReq.getMaChucNang().equals(Constants.TT_BC_3)
								&& !objReq.getMaChucNang().equals(Constants.TT_BC_4)) {
							throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
						}
					} else if (role.getId().equals(Constants.LANH_DAO)) {
						if (!objReq.getMaChucNang().equals(Constants.TT_BC_5)
								&& !objReq.getMaChucNang().equals(Constants.TT_BC_6)) {
							throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
						}
					}
				}
			} else if (qlnvDmDonvi.getParent().getId().equals(userInfo.getDvql()) && !isTongCuc) {
				// user đơn vị cấp trên
				for (Role role : userInfo.getRoles()) {
					if (!role.getId().equals(Constants.NHAN_VIEN)) {
						throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
					} else {
						if (!objReq.getMaChucNang().equals(Constants.TT_BC_8)
								&& !objReq.getMaChucNang().equals(Constants.TT_BC_9)) {
							throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
						}
					}
				}
			} else {
				throw new UnsupportedOperationException("Người dùng không hợp lệ!");
			}

			// check trạng thái hiện tại của báo cáo, xem action có đúng là trạng thái tiếp
			// theo hoặc tiếp sau
			int stt = Integer.parseInt(qlnvBaoCao.getTrangThai());
			int sttNext = stt + 1;
			int sttPrevious = stt - 1;
			int maChucNangInt = Integer.parseInt(objReq.getMaChucNang());

			if (sttPrevious == 0) {
				if (sttNext != maChucNangInt) {
					throw new UnsupportedOperationException("Mã chức năng: " + objReq.getMaChucNang()
							+ " không khớp với trạng thái: " + qlnvBaoCao.getTrangThai() + " của Báo cáo!");
				}
			} else if ((sttNext != maChucNangInt && sttPrevious != maChucNangInt)) {
				throw new UnsupportedOperationException("Mã chức năng: " + objReq.getMaChucNang()
						+ " không khớp với trạng thái: " + qlnvBaoCao.getTrangThai() + " của Báo cáo!");
			}

			switch (objReq.getMaChucNang()) {
			case Constants.TT_BC_2:// Trình duyệt

				qlnvBaoCao.setTrangThai(Constants.TT_BC_2);
				break;
			case Constants.TT_BC_3:// Trưởng BP từ chối

				qlnvBaoCao.setTrangThai(Constants.TT_BC_3);
				break;
			case Constants.TT_BC_4:// Trưởng BP duyệt

				qlnvBaoCao.setTrangThai(Constants.TT_BC_4);
				break;
			case Constants.TT_BC_5:// Lãnh đạo từ chối

				qlnvBaoCao.setTrangThai(Constants.TT_BC_5);
				break;
			case Constants.TT_BC_6:// Lãnh đạo duyệt

				qlnvBaoCao.setTrangThai(Constants.TT_BC_6);
				break;
			case Constants.TT_BC_7:// Gửi ĐV cấp trên

				qlnvBaoCao.setTrangThai(Constants.TT_BC_7);
				break;
			case Constants.TT_BC_8:// ĐV cấp trên từ chối

				qlnvBaoCao.setTrangThai(Constants.TT_BC_8);
				break;
			case Constants.TT_BC_9:// Đv cấp trên duyệt

				qlnvBaoCao.setTrangThai(Constants.TT_BC_9);
				break;
			default:
				break;
			}

			// update trạng thái báo cáo
			qlnvBaoCaoRepository.save(qlnvBaoCao);

			resp.setData(qlnvBaoCao);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);

	}
}