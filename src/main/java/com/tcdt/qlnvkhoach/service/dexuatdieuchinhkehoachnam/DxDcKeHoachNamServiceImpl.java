package com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.*;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVt;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVtCt;
import com.tcdt.qlnvkhoach.enums.*;
import com.tcdt.qlnvkhoach.query.dto.DxDcQueryDto;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcLtVtCtRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcLtVtRespository;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcLtVtCtReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcLtVtReq;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.*;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.ExcelUtils;
import com.tcdt.qlnvkhoach.util.LocalDateTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DxDcKeHoachNamServiceImpl implements DxDcKeHoachNamService {

    private static final String SHEET_DE_XUAT_DIEU_CHINH_KE_HOACH_NAM = "Đề xuất điều chỉnh kế hoạch năm";
    private static final String STT = "STT";
    private static final String SO_VAN_BAN = "Số Văn Bản";
    private static final String NGAY_HIEU_LUC = "Ngày Đề Xuất";
    private static final String SO_QUYET_DINH = "Số QD giao chỉ tiêu kế hoạch năm";
    private static final String TRANG_THAI = "Trạng Thái";
    private static final String NAM_KE_HOACH = "Năm kế hoạch";

    @Autowired
    private DxDcKeHoachNamRepository dxDcKeHoachNamRepository;

    @Autowired
    private DxDcLtVtRespository dxDcLtVtRespository;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    @Autowired
    private ChiTieuKeHoachNamService chiTieuKeHoachNamService;

    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private DxDcLtVtCtRepository dxDcLtVtCtRepository;

    @Override
    public Page<DxDcKeHoachNamRes> search(SearchDxDcKeHoachNamReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null || Constants.CHI_CUC.equals(userInfo.getCapDvi()))
         throw new Exception("Bad request");

        Set<String> trangThais = new HashSet<>();
        if (Constants.CUC_KHU_VUC.equals(userInfo.getCapDvi())) {
            req.setMaDonVi(userInfo.getDvql());
        } else if (Constants.TONG_CUC.equals(userInfo.getCapDvi())) {
            trangThais.add(DxDcKeHoachNamStatusEnum.BAN_HANH.getId());
        }

        List<DxDcQueryDto> data = dxDcKeHoachNamRepository.search(req, trangThais);
        Set<String> maDvis = data.stream().map(DxDcQueryDto::getDx).map(DxDcKeHoachNam::getMaDvi).collect(Collectors.toSet());
        Map<String, QlnvDmDonvi> mapDvi = qlnvDmService.getMapDonVi(maDvis);

        List<DxDcKeHoachNamRes> responses = new ArrayList<>();
        for (DxDcQueryDto item : data) {
            DxDcKeHoachNam dx = item.getDx();
            String soQuyetDinh = item.getSoQuyetDinh();
            QlnvDmDonvi donVi = mapDvi.get(dx.getMaDvi());
            if (donVi == null)
                throw new Exception("Không tìm thấy thông tin đơn vị");

            dx.setDonVi(donVi);
            DxDcKeHoachNamRes dxDcKeHoachNamRes = this.buildResponse(dx, userInfo.getDvql(), userInfo.getCapDvi(), null, null);
            dxDcKeHoachNamRes.setSoQdKeHoachNam(soQuyetDinh);
            responses.add(dxDcKeHoachNamRes);
        }

        return new PageImpl<>(responses, PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit()), dxDcKeHoachNamRepository.countDxDcKeHoachNam(req, trangThais));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public DxDcKeHoachNamRes create(DxDcKeHoachNamReq req) throws Exception {

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null || !Constants.CUC_KHU_VUC.equals(userInfo.getCapDvi()))
            throw new Exception("Bad request.");

        ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamService.getChiTieuKeHoachNam(req.getKeHoachNamId());
        if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(chiTieuKeHoachNam.getTrangThai())) {
            throw new Exception("Không thể tạo đề xuất cho chỉ tiêu chưa ban hành.");
        }

        DxDcKeHoachNam exist = this.existDxDckhn(null, chiTieuKeHoachNam, req.getSoVanBan(), userInfo.getDvql());
        if (exist != null) {
            throw new Exception("Đề xuất điều chỉnh đã tồn tại.");
        }

        ChiTieuKeHoachNam latest = chiTieuKeHoachNamService.getChiTieuKeHoachNamLatest(chiTieuKeHoachNam);
        DxDcKeHoachNam dxDc = new DxDcKeHoachNam();
        BeanUtils.copyProperties(req, dxDc, "id");
        dxDc.setNgayTao(LocalDate.now());
        dxDc.setNguoiTaoId(userInfo.getId());
        dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.DU_THAO.getId());
        dxDc.setTrangThaiTongCuc(DxDcKeHoachNamStatusTongCucEnum.CHUA_XU_LY.getId());
        dxDc.setMaDvi(userInfo.getDvql());
        dxDc.setCapDvi(userInfo.getCapDvi());
        dxDc.setKeHoachNamId(chiTieuKeHoachNam.getId());
        dxDcKeHoachNamRepository.save(dxDc);

        List<DxDcLtVtReq> dxDcLtVtReqList = req.getDxDcLtVtReqList();
        dxDc.setDxDcLtVtList(this.saveListDxDcLtVt(dxDcLtVtReqList, dxDc.getId(), new HashMap<>()));

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), dxDc.getId(), DxDcKeHoachNam.TABLE_NAME);
        dxDc.setFileDinhKems(fileDinhKems);
        chiTieuKeHoachNamService.retrieveDataChiTieuKeHoachNam(latest);
        dxDc.setDonVi(qlnvDmService.getDonViByMa(dxDc.getMaDvi()));
        return this.buildResponse(dxDc, userInfo.getDvql(), userInfo.getCapDvi(), chiTieuKeHoachNam, latest);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public DxDcKeHoachNamRes update(DxDcKeHoachNamReq req) throws Exception {
        if (req == null)
            return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null || !Constants.CUC_KHU_VUC.equals(userInfo.getCapDvi()))
            throw new Exception("Bad request.");

        Optional<DxDcKeHoachNam> optional = dxDcKeHoachNamRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Chỉ tiêu kế hoạch năm không tồn tại");

        DxDcKeHoachNam dxDc = optional.get();

        ChiTieuKeHoachNam ctkhn = chiTieuKeHoachNamService.getChiTieuKeHoachNam(req.getKeHoachNamId());
        if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(ctkhn.getTrangThai())) {
            throw new Exception("Không thể tạo đề xuất cho chỉ tiêu chưa ban hành.");
        }

        if (!userInfo.getDvql().equals(dxDc.getMaDvi()))
            throw new Exception("Bad request");

        DxDcKeHoachNam exist = this.existDxDckhn(dxDc, ctkhn, req.getSoVanBan(), userInfo.getDvql());
        if (exist != null && !exist.getId().equals(dxDc.getId()))
            throw new Exception("Đề xuất điều chỉnh kế hoạch năm đã tồn tại");

        ChiTieuKeHoachNam latest = chiTieuKeHoachNamService.getChiTieuKeHoachNamLatest(ctkhn);

        BeanUtils.copyProperties(req, dxDc, "id");
        dxDc.setNgayTao(LocalDate.now());
        dxDc.setNguoiTaoId(userInfo.getId());
        dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.DU_THAO.getId());
        dxDc.setMaDvi(userInfo.getDvql());
        dxDc.setCapDvi(userInfo.getCapDvi());
        dxDc.setKeHoachNamId(ctkhn.getId());

        dxDcKeHoachNamRepository.save(dxDc);

        Map<Long, DxDcLtVt> mapDxDcLtVt = dxDcLtVtRespository.findByDxdckhnId(dxDc.getId()).stream().collect(Collectors.toMap(DxDcLtVt::getId, Function.identity()));
        List<DxDcLtVtReq> dxDcLtVtReqList = req.getDxDcLtVtReqList();
        dxDc.setDxDcLtVtList(this.saveListDxDcLtVt(dxDcLtVtReqList, dxDc.getId(), mapDxDcLtVt));

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), dxDc.getId(), DxDcKeHoachNam.TABLE_NAME);
        dxDc.setFileDinhKems(fileDinhKems);
        chiTieuKeHoachNamService.retrieveDataChiTieuKeHoachNam(latest);
        dxDc.setDonVi(qlnvDmService.getDonViByMa(dxDc.getMaDvi()));
        return this.buildResponse(dxDc, userInfo.getDvql(), userInfo.getCapDvi(), ctkhn, latest);
    }

    private List<DxDcLtVt> saveListDxDcLtVt(List<DxDcLtVtReq> dxDcLtVtReqList,
                                                  Long dxDcId,
                                                  Map<Long, DxDcLtVt> mapDxDcLtVt) throws Exception {

        List<DxDcLtVt> ltVts = new ArrayList<>();
        Set<Long> dxDcLtVtIds = mapDxDcLtVt.keySet();

        Map<Long, DxDcLtVtCt> mapDxDcLtVtCt = new HashMap<>();
        if (!CollectionUtils.isEmpty(dxDcLtVtIds)) {
            mapDxDcLtVtCt = dxDcLtVtCtRepository.findByDxDcLtVtIdIn(dxDcLtVtIds)
                    .stream().collect(Collectors.toMap(DxDcLtVtCt::getId, Function.identity()));
        }

        for (DxDcLtVtReq ltVtReq : dxDcLtVtReqList) {
            DxDcLtVt ltVt = new DxDcLtVt();
            Long ltVtId = ltVtReq.getId();
            if (ltVtId != null && ltVtId > 0) {
                ltVt = mapDxDcLtVt.get(ltVtId);
                if (ltVt == null)
                    throw new Exception("Đề xuất điều chỉnh lương thực vật tư không tồn tại.");
            }

            BeanUtils.copyProperties(ltVtReq, ltVt, "id");
            ltVt.setDxdckhnId(dxDcId);
            ltVts.add(ltVt);
            mapDxDcLtVt.remove(ltVtId);
            dxDcLtVtRespository.save(ltVt);

            List<DxDcLtVtCt> dxDcLtVtCtList = new ArrayList<>();
            for (DxDcLtVtCtReq dxDcLtVtCtReq : ltVtReq.getDxDcLtVtCtList()) {
                Long ltVtCtId = dxDcLtVtCtReq.getId();
                if (ltVtCtId != null && ltVtCtId > 0) {
                    DxDcLtVtCt dxDcLtVtCt = mapDxDcLtVtCt.get(ltVtCtId);
                    if (dxDcLtVtCt == null)
                        throw new Exception("Đề xuất điều chỉnh lương thực vật tư chi tiết không tồn tại.");

                    BeanUtils.copyProperties(dxDcLtVtCtReq, dxDcLtVtCt, "id");
                    dxDcLtVtCt.setDxDcLtVtId(ltVt.getId());

                    dxDcLtVtCtList.add(dxDcLtVtCt);
                    mapDxDcLtVtCt.remove(ltVtCtId);
                } else {
                    DxDcLtVtCt dxDcLtVtCt = new DxDcLtVtCt();
                    BeanUtils.copyProperties(dxDcLtVtCtReq, dxDcLtVtCt, "id");
                    dxDcLtVtCt.setDxDcLtVtId(ltVt.getId());
                    dxDcLtVtCtList.add(dxDcLtVtCt);
                }
            }
            if (!CollectionUtils.isEmpty(dxDcLtVtCtList)) {
                dxDcLtVtCtRepository.saveAll(dxDcLtVtCtList);
            }

            ltVt.setDxDcLtVtCtList(dxDcLtVtCtList);
        }

        if (!CollectionUtils.isEmpty(mapDxDcLtVt.values()))
            dxDcLtVtRespository.deleteAll(mapDxDcLtVt.values());

        if (!CollectionUtils.isEmpty(mapDxDcLtVtCt.values()))
            dxDcLtVtCtRepository.deleteAll(mapDxDcLtVtCt.values());

        return ltVts;
    }

    private DxDcKeHoachNam existDxDckhn(DxDcKeHoachNam update, ChiTieuKeHoachNam chiTieuKeHoachNam, String soVanBan, String dvql) throws Exception {
        if (update == null || (!StringUtils.hasText(update.getSoVanBan()) && !update.getSoVanBan().equalsIgnoreCase(soVanBan))) {
            DxDcKeHoachNam exist = dxDcKeHoachNamRepository.findFirstBySoVanBan(soVanBan);
            if (exist != null)
                throw new Exception("Số văn bản " + soVanBan + " đã tồn tại");
        }

        return dxDcKeHoachNamRepository.findByKeHoachNamIdAndMaDvi(chiTieuKeHoachNam.getId(), dvql)
                .stream().filter(c -> !DxDcKeHoachNamStatusEnum.TU_CHOI.getId().equalsIgnoreCase(c.getTrangThai()))
                .findFirst().orElse(null);

    }

    private DxDcKeHoachNamRes buildResponse(DxDcKeHoachNam dxDc, String dvql, String cap, ChiTieuKeHoachNam qdGoc, ChiTieuKeHoachNam qdLatest) throws Exception {

        DxDcKeHoachNamRes response = new DxDcKeHoachNamRes();
        BeanUtils.copyProperties(dxDc, response);
        if (qdLatest != null) {
            ChiTieuKeHoachNamRes latest = chiTieuKeHoachNamService.buildDetailResponse(qdLatest, qdLatest.getNamKeHoach());
            response.setSoQdKeHoachNam(qdGoc.getSoQuyetDinh());


            response.setDxDcltList(this.buildListDxDcLtRes(dxDc, latest, dvql));
            response.setDxDcMuoiList(this.buildListDxDcMuoiRes(dxDc, latest, dvql));
            response.setDxDcVtList(this.buildListDxDcVatTuRes(dxDc, latest, dvql));
        }
        if (Constants.TONG_CUC.equals(cap)) {
            response.setTrangThai(dxDc.getTrangThaiTongCuc());
            response.setTenTrangThai(DxDcKeHoachNamStatusTongCucEnum.getTenById(dxDc.getTrangThaiTongCuc()));
            response.setTrangThaiDuyet(DxDcKeHoachNamStatusTongCucEnum.getTenById(dxDc.getTrangThaiTongCuc()));
        } else {
            response.setTenTrangThai(DxDcKeHoachNamStatusEnum.getTenById(dxDc.getTrangThai()));
            response.setTrangThaiDuyet(DxDcKeHoachNamStatusEnum.getTrangThaiDuyetById(dxDc.getTrangThai()));
        }

        response.setKeHoachNamId(qdGoc != null ? qdGoc.getId() : null);
        response.setSoQdKeHoachNam(qdGoc != null ? qdGoc.getSoQuyetDinh() : null);

        response.setFileDinhKems(dxDc.getFileDinhKems());
        response.setTenDonVi(dxDc.getDonVi().getTenDvi());
        response.setMaDonVi(dxDc.getDonVi().getMaDvi());
        return response;
    }

    private List<DxDcVtRes> buildListDxDcVatTuRes(DxDcKeHoachNam dxDc,  ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, String dvql) throws Exception {

        List<DxDcLtVt> vtList = dxDc.getDxDcLtVtList().stream()
                .filter(ltVt -> Constants.LOAI_VTHH_VATTU.equals(ltVt.getLoai()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(vtList))
            return Collections.emptyList();

        Set<String> maVatTus = new HashSet<>();
        for (DxDcLtVt vt : vtList) {
            if (StringUtils.hasText(vt.getMaVatTu()))
                maVatTus.add(vt.getMaVatTu());

            if (StringUtils.hasText(vt.getMaVatTuCha()))
                maVatTus.add(vt.getMaVatTuCha());
        }
        List<KeHoachVatTuRes> khVatTuList = chiTieuKeHoachNamRes.getKhVatTu();
        List<VatTuThietBiRes> vatTuThietBiResList = khVatTuList.stream()
                .filter(k -> k.getMaDonVi().equals(dvql))
                .flatMap(k -> k.getVatTuThietBi().stream())
                .collect(Collectors.toList());

        Map<String, QlnvDmVattu> mapVatTu = qlnvDmService.getMapVatTu(maVatTus);
        List<DxDcVtRes> dxDcVtResList = new ArrayList<>();
        for (DxDcLtVt vt : vtList) {
            List<VatTuThietBiRes> vtTbCtkhns = vatTuThietBiResList.stream().filter(res -> res.getMaVatTu().equals(vt.getMaVatTu())).collect(Collectors.toList());
            DxDcVtRes dxDcVtRes = this.buildDxDcVtResTruocDieuChinh(vt.getMaVatTu(), vtTbCtkhns, mapVatTu);

            List<DxDcLtVtCt> dxDcLtVtCtList = vt.getDxDcLtVtCtList();
            dxDcLtVtCtList.forEach(ct -> {
                DxDcLtVtCtRes dxDcLtVtCtRes = new DxDcLtVtCtRes();
                BeanUtils.copyProperties(ct, dxDcLtVtCtRes);
                dxDcVtRes.getDxDcLtVtCtList().add(dxDcLtVtCtRes);
            });

            Double tongSoLuongTang = dxDcLtVtCtList.stream()
                    .filter(t -> t.getSoLuongTang() != null)
                    .mapToDouble(DxDcLtVtCt::getSoLuongTang).sum();

            Double tongSoLuongGiam = dxDcLtVtCtList.stream()
                    .filter(t -> t.getSoLuongGiam() != null)
                    .mapToDouble(DxDcLtVtCt::getSoLuongGiam).sum();

            Double sDc = Math.abs(tongSoLuongTang - tongSoLuongGiam);

            dxDcVtRes.setDonViTinh(vt.getDonViTinh());
            dxDcVtRes.setMaVatTu(vt.getMaVatTu());
            dxDcVtRes.setMaVatTuCha(vt.getMaVatTuCha());
            dxDcVtRes.setSdcKeHoachNam(sDc);
            dxDcVtRes.setSdcTongSo(dxDcVtRes.getSdcKeHoachNam() + dxDcVtRes.getSdcCacNamTruoc());
            dxDcVtRes.setDc(dxDcVtRes.getSdcKeHoachNam() - dxDcVtRes.getTdcKeHoachNam());
            dxDcVtRes.setId(vt.getId());
            dxDcVtRes.setDiaDiemKho(vt.getDiaDiemKho());
            dxDcVtResList.add(dxDcVtRes);
        }
        return dxDcVtResList;
    }

    private List<DxDcLtRes> buildListDxDcLtRes(DxDcKeHoachNam dxDc,  ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, String dvql) {
        List<DxDcLtVt> ltList = dxDc.getDxDcLtVtList().stream()
                .filter(ltVt -> Constants.LOAI_VTHH_LUONG_THUC.equals(ltVt.getLoai()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(ltList))
            return Collections.emptyList();

        List<DxDcLtRes> ltResponseList = this.buildListDxDcLtResTruocDieuChinh(chiTieuKeHoachNamRes, dvql, false);

        ltList.forEach(lt -> {
            DxDcLtRes dxDcLtRes = ltResponseList.stream().filter(res -> res.getChiTieu().equals(lt.getChiTieu())).findFirst().orElse(null);
            if (dxDcLtRes == null)
                return;

            List<DxDcLtVtCt> dxDcLtVtCtList = lt.getDxDcLtVtCtList();
            dxDcLtVtCtList.forEach(ct -> {
                DxDcLtVtCtRes dxDcLtVtCtRes = new DxDcLtVtCtRes();
                BeanUtils.copyProperties(ct, dxDcLtVtCtRes);
                dxDcLtRes.getDxDcLtVtCtList().add(dxDcLtVtCtRes);
            });

            Double tongSoLuongTang = dxDcLtVtCtList.stream()
                    .filter(t -> t.getSoLuongTang() != null)
                    .mapToDouble(DxDcLtVtCt::getSoLuongTang).sum();

            Double tongSoLuongGiam = dxDcLtVtCtList.stream()
                    .filter(t -> t.getSoLuongGiam() != null)
                    .mapToDouble(DxDcLtVtCt::getSoLuongGiam).sum();

            Double sDc = Math.abs(tongSoLuongTang - tongSoLuongGiam);
            if (Constants.LuongThucMuoiConst.GAO_MA_VT.equals(lt.getMaVatTu())) {
                dxDcLtRes.setSdcGao(sDc);
                dxDcLtRes.setGaoId(lt.getId());
            } else {
                dxDcLtRes.setSdcThoc(sDc);
                dxDcLtRes.setThocId(lt.getId());
            }
            dxDcLtRes.setDiaDiemKho(lt.getDiaDiemKho());
        });

        ltResponseList.forEach(lt -> {
            lt.setSdcTongSoQuyThoc(lt.getSdcGao() * 2 + lt.getSdcThoc());
            lt.setDcTongSoQuyThoc(lt.getSdcTongSoQuyThoc() - lt.getTdcTongSoQuyThoc());
            lt.setDcGao(lt.getSdcGao() - lt.getTdcGao());
            lt.setDcThoc(lt.getSdcThoc() - lt.getTdcGao());
        });
        return ltResponseList;
    }

    private List<DxDcMuoiRes> buildListDxDcMuoiRes(DxDcKeHoachNam dxDc,  ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, String dvql) {
        List<DxDcLtVt> muoiList = dxDc.getDxDcLtVtList().stream()
                .filter(ltVt -> Constants.LOAI_VTHH_MUOI.equals(ltVt.getLoai()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(muoiList))
            return Collections.emptyList();

        List<DxDcMuoiRes> muoiResponseList = this.buildListDxDcMuoiResTruocDieuChinh(chiTieuKeHoachNamRes, dvql, false);
        muoiList.forEach(muoi -> {
            DxDcMuoiRes dxDcMuoiRes = muoiResponseList.stream().filter(res -> res.getChiTieu().equals(muoi.getChiTieu())).findFirst().orElse(null);
            if (dxDcMuoiRes == null)
                return;

            List<DxDcLtVtCt> dxDcLtVtCtList = muoi.getDxDcLtVtCtList();
            dxDcLtVtCtList.forEach(ct -> {
                DxDcLtVtCtRes dxDcLtVtCtRes = new DxDcLtVtCtRes();
                BeanUtils.copyProperties(ct, dxDcLtVtCtRes);
                dxDcMuoiRes.getDxDcLtVtCtList().add(dxDcLtVtCtRes);
            });

            Double tongSoLuongTang = dxDcLtVtCtList.stream()
                    .filter(t -> t.getSoLuongTang() != null)
                    .mapToDouble(DxDcLtVtCt::getSoLuongTang).sum();

            Double tongSoLuongGiam = dxDcLtVtCtList.stream()
                    .filter(t -> t.getSoLuongGiam() != null)
                    .mapToDouble(DxDcLtVtCt::getSoLuongGiam).sum();

            Double sDc = Math.abs(tongSoLuongTang - tongSoLuongGiam);
            dxDcMuoiRes.setSdc(sDc);
            dxDcMuoiRes.setDc(sDc - dxDcMuoiRes.getTdc());
            dxDcMuoiRes.setId(muoi.getId());
            dxDcMuoiRes.setDiaDiemKho(muoi.getDiaDiemKho());
        });
        return muoiResponseList;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean delete(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null || !Constants.CUC_KHU_VUC.equals(userInfo.getCapDvi()))
            throw new Exception("Bad request.");

        Optional<DxDcKeHoachNam> optional = dxDcKeHoachNamRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Đề xuất điều chỉnh không tồn tại");

        DxDcKeHoachNam dxDc = optional.get();

        if (!userInfo.getDvql().equals(dxDc.getMaDvi()))
            throw new Exception("Bad request");

        if (DxDcKeHoachNamStatusEnum.BAN_HANH.getId().equals(dxDc.getTrangThai())) {
            throw new Exception("Không thể xóa đề xuất điều chỉnh đã ban hành");
        } else if (DxDcKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(dxDc.getTrangThai())) {
            throw new Exception("Không thể xóa đề xuất điều chỉnh trình duyệt");
        }

        List<Long> dxDcLtVtIds = dxDcLtVtRespository.findIdByDxdckhnIdIn(Collections.singletonList(dxDc.getId()));
        if (!CollectionUtils.isEmpty(dxDcLtVtIds))
            dxDcLtVtCtRepository.deleteByDxDcLtVtIdIn(dxDcLtVtIds);

        dxDcLtVtRespository.deleteByDxdckhnId(dxDc.getId());
        dxDcKeHoachNamRepository.delete(dxDc);
        fileDinhKemService.delete(dxDc.getId(), Lists.newArrayList(DxDcKeHoachNam.TABLE_NAME));
        return true;
    }

    @Override
    public DxDcKeHoachNamRes detail(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<DxDcKeHoachNam> optional = dxDcKeHoachNamRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Đề xuất điều chỉnh không tồn tại");

        DxDcKeHoachNam dxDc = optional.get();

        ChiTieuKeHoachNam ctkhn = chiTieuKeHoachNamService.getChiTieuKeHoachNam(dxDc.getKeHoachNamId());
        if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(ctkhn.getTrangThai())) {
            throw new Exception("Không thể tạo đề xuất cho chỉ tiêu chưa ban hành.");
        }

        ChiTieuKeHoachNam latest = chiTieuKeHoachNamService.getChiTieuKeHoachNamLatest(ctkhn);

        List<DxDcLtVt> dxDcLtVtList = dxDcLtVtRespository.findByDxdckhnId(dxDc.getId());
        List<Long> dxDcLtVtIds = dxDcLtVtList.stream().map(DxDcLtVt::getId).collect(Collectors.toList());
        Map<Long, List<DxDcLtVtCt>> mapDxDcLtVtCt = new HashMap<>();
        if (!CollectionUtils.isEmpty(dxDcLtVtIds)) {
            mapDxDcLtVtCt = dxDcLtVtCtRepository.findByDxDcLtVtIdIn(dxDcLtVtIds)
                    .stream().collect(Collectors.groupingBy(DxDcLtVtCt::getDxDcLtVtId));
        }
        for (DxDcLtVt dxDcLtVt : dxDcLtVtList ) {
            dxDcLtVt.setDxDcLtVtCtList(Optional.ofNullable(mapDxDcLtVtCt.get(dxDcLtVt.getId())).orElse(new ArrayList<>()));
        }

        dxDc.setDxDcLtVtList(dxDcLtVtList);
        chiTieuKeHoachNamService.retrieveDataChiTieuKeHoachNam(latest);
        dxDc.setFileDinhKems(fileDinhKemService.search(dxDc.getId(), Collections.singleton(DxDcKeHoachNam.TABLE_NAME)));
        dxDc.setDonVi(qlnvDmService.getDonViByMa(dxDc.getMaDvi()));
        return this.buildResponse(dxDc, userInfo.getDvql(), userInfo.getCapDvi(), ctkhn, latest);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateStatus(StatusReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<DxDcKeHoachNam> optional = dxDcKeHoachNamRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Không tìm thấy dữ liệu.");

        DxDcKeHoachNam dxDc = optional.get();

        String trangThai = dxDc.getTrangThai();
        if (DxDcKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(req.getTrangThai())) {
            if (!DxDcKeHoachNamStatusEnum.DU_THAO.getId().equals(trangThai))
                return false;

            dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId());
            dxDc.setNguoiGuiDuyetId(userInfo.getId());
            dxDc.setNgayGuiDuyet(LocalDate.now());

        } else if (DxDcKeHoachNamStatusEnum.LANH_DAO_DUYET.getId().equals(req.getTrangThai())) {
            if (!DxDcKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(trangThai))
                return false;
            dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.LANH_DAO_DUYET.getId());
            dxDc.setNguoiPheDuyetId(userInfo.getId());
            dxDc.setNgayPheDuyet(LocalDate.now());
        } else if (DxDcKeHoachNamStatusEnum.BAN_HANH.getId().equals(req.getTrangThai())) {
            if (!DxDcKeHoachNamStatusEnum.LANH_DAO_DUYET.getId().equals(trangThai))
                return false;

            dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.BAN_HANH.getId());
            dxDc.setNguoiPheDuyetId(userInfo.getId());
            dxDc.setNgayPheDuyet(LocalDate.now());
        } else if (DxDcKeHoachNamStatusEnum.TU_CHOI.getId().equals(req.getTrangThai())) {
            if (!DxDcKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(trangThai))
                return false;

            dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.TU_CHOI.getId());
            dxDc.setNguoiPheDuyetId(userInfo.getId());
            dxDc.setNgayPheDuyet(LocalDate.now());
            dxDc.setLyDoTuChoi(req.getLyDoTuChoi());
        }  else {
            throw new Exception("Bad request.");
        }

        dxDcKeHoachNamRepository.save(dxDc);
        return true;
    }

    @Override
    public DxDcKeHoachNamRes getSoLuongTruocDieuChinh(Long ctkhnId) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        String dvql = userInfo.getDvql();

        ChiTieuKeHoachNam ctkhn = chiTieuKeHoachNamService.getChiTieuKeHoachNam(ctkhnId);
        chiTieuKeHoachNamService.retrieveDataChiTieuKeHoachNam(ctkhn);
        ChiTieuKeHoachNamRes chiTieuKeHoachNamRes = chiTieuKeHoachNamService.buildDetailResponse(ctkhn, ctkhn.getNamKeHoach());
        DxDcKeHoachNamRes dxDcKeHoachNamRes = new DxDcKeHoachNamRes();

        dxDcKeHoachNamRes.setDxDcltList(this.buildListDxDcLtResTruocDieuChinh(chiTieuKeHoachNamRes, dvql, true));
        dxDcKeHoachNamRes.setDxDcMuoiList(this.buildListDxDcMuoiResTruocDieuChinh(chiTieuKeHoachNamRes, dvql, true));
        dxDcKeHoachNamRes.setDxDcVtList(this.buildListDxDcVtResTruocDieuChinh(chiTieuKeHoachNamRes, dvql));
        dxDcKeHoachNamRes.setNamKeHoach(ctkhn.getNamKeHoach());
        dxDcKeHoachNamRes.setKeHoachNamId(ctkhnId);
        dxDcKeHoachNamRes.setSoQdKeHoachNam(ctkhn.getSoQuyetDinh());
        return dxDcKeHoachNamRes;
    }

    private List<DxDcVtRes> buildListDxDcVtResTruocDieuChinh(ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, String dvql) throws Exception {
        List<KeHoachVatTuRes> khVatTuList = chiTieuKeHoachNamRes.getKhVatTu();
        Map<String, List<VatTuThietBiRes>> mapVatTuThietBiRes = khVatTuList.stream()
                .filter(k -> k.getMaDonVi().equals(dvql))
                .flatMap(k -> k.getVatTuThietBi().stream())
                .collect(Collectors.groupingBy(VatTuThietBiRes::getMaVatTu));

        Set<String> maVatTus =  new HashSet<>();
        for (List<VatTuThietBiRes> value : mapVatTuThietBiRes.values()) {
            for (VatTuThietBiRes vtTbRes : value) {
                if (StringUtils.hasText(vtTbRes.getMaVatTu()))
                    maVatTus.add(vtTbRes.getMaVatTu());

                if (StringUtils.hasText(vtTbRes.getMaVatTuCha()))
                    maVatTus.add(vtTbRes.getMaVatTuCha());
            }
        }
        Map<String, QlnvDmVattu> mapVatTu = qlnvDmService.getMapVatTu(maVatTus);
        List<DxDcVtRes> dxDcVtResList = new ArrayList<>();
        for (Map.Entry<String, List<VatTuThietBiRes>> entry : mapVatTuThietBiRes.entrySet()) {
            String maVatTu = entry.getKey();
            List<VatTuThietBiRes> vatTuThietBiResList = entry.getValue();
            dxDcVtResList.add(this.buildDxDcVtResTruocDieuChinh(maVatTu, vatTuThietBiResList, mapVatTu));
        }
        dxDcVtResList.sort(Comparator.comparing(DxDcVtRes::getTenVatTu));
        return dxDcVtResList;
    }

    private DxDcVtRes buildDxDcVtResTruocDieuChinh(String maVatTu, List<VatTuThietBiRes> vatTuThietBiResList, Map<String, QlnvDmVattu> mapVatTu) throws Exception {
        QlnvDmVattu vatTu = StringUtils.hasText(maVatTu) ? mapVatTu.get(maVatTu) : null;
        if (vatTu == null)
            throw new Exception("Vật tư không tồn tại.");

        QlnvDmVattu vatTuCha = StringUtils.hasText(vatTu.getMaCha()) ? mapVatTu.get(vatTu.getMaCha()) : null;

        double tdcTongSo = 0D;
        double cacNamTruoc = 0D;
        double tdcKeHoachNam = 0D;

        String donViTinh = null;
        if (!CollectionUtils.isEmpty(vatTuThietBiResList)) {
            tdcTongSo = vatTuThietBiResList.stream()
                    .map(VatTuThietBiRes::getTongNhap)
                    .mapToDouble(Double::doubleValue).sum();

            cacNamTruoc = vatTuThietBiResList.stream()
                    .flatMap(i -> i.getCacNamTruoc().stream())
                    .map(VatTuNhapRes::getSoLuong)
                    .mapToDouble(Double::doubleValue).sum();

            tdcKeHoachNam = vatTuThietBiResList.stream()
                    .map(VatTuThietBiRes::getNhapTrongNam)
                    .mapToDouble(Double::doubleValue).sum();

            donViTinh = vatTuThietBiResList.get(0).getDonViTinh();
        }
        DxDcVtRes dxDcVtRes = new DxDcVtRes();
        dxDcVtRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.NHAP_TRONG_NAM.getValue());
        dxDcVtRes.setTdcTongSo(tdcTongSo);
        dxDcVtRes.setTdcCacNamTruoc(cacNamTruoc);
        dxDcVtRes.setTdcKeHoachNam(tdcKeHoachNam);

        dxDcVtRes.setSdcCacNamTruoc(cacNamTruoc);
        dxDcVtRes.setSdcTongSo(cacNamTruoc);
        dxDcVtRes.setDc(0D);
        dxDcVtRes.setSdcKeHoachNam(0D);
        dxDcVtRes.setDonViTinh(donViTinh);
        dxDcVtRes.setMaVatTu(maVatTu);
        dxDcVtRes.setTenVatTu(vatTu.getTen());
        dxDcVtRes.setKyHieu(vatTu.getKyHieu());
        if (vatTuCha != null) {
            dxDcVtRes.setTenVatTuCha(vatTuCha.getTen());
            dxDcVtRes.setMaVatTuCha(vatTuCha.getMaCha());
        }
        return dxDcVtRes;
    }

    private List<DxDcMuoiRes> buildListDxDcMuoiResTruocDieuChinh(ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, String dvql,
                                                                 boolean apiSoLuongTdc) {
        List<KeHoachMuoiDuTruRes> khMuoi = chiTieuKeHoachNamRes.getKhMuoiDuTru().stream()
                .filter(k -> k.getMaDonVi().equals(dvql))
                .collect(Collectors.toList());

        double tdcXtn = 0D;
        double tdcNtn = 0D;
        if (!CollectionUtils.isEmpty(khMuoi)) {
            tdcXtn = khMuoi.stream().map(KeHoachMuoiDuTruRes::getXtnTongSoMuoi).mapToDouble(Double::doubleValue).sum();
            tdcNtn = khMuoi.stream().map(KeHoachMuoiDuTruRes::getNtnTongSoMuoi).mapToDouble(Double::doubleValue).sum();
        } else if (apiSoLuongTdc) {
            return Collections.emptyList();
        }

        List<DxDcMuoiRes> muoiResponseList = new ArrayList<>();
        DxDcMuoiRes muoiXuatRes = new DxDcMuoiRes();
        muoiXuatRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.XUAT_TRONG_NAM.getValue());
        muoiXuatRes.setMaVatTu(Constants.LuongThucMuoiConst.MUOI_MA_VT);
        muoiXuatRes.setTdc(tdcXtn);
        muoiXuatRes.setSdc(0D);
        muoiXuatRes.setDc(0D);

        DxDcMuoiRes muoiNhapRes = new DxDcMuoiRes();
        muoiNhapRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.NHAP_TRONG_NAM.getValue());
        muoiNhapRes.setMaVatTu(Constants.LuongThucMuoiConst.MUOI_MA_VT);
        muoiNhapRes.setTdc(tdcNtn);
        muoiNhapRes.setSdc(0D);
        muoiNhapRes.setDc(0D);

        muoiResponseList.add(muoiXuatRes);
        muoiResponseList.add(muoiNhapRes);
        return muoiResponseList;
    }

    private List<DxDcLtRes> buildListDxDcLtResTruocDieuChinh(ChiTieuKeHoachNamRes chiTieuKeHoachNamRes, String dvql,
                                                             boolean apiSoLuongTdc) {
        // Luong Thuc
        List<KeHoachLuongThucDuTruRes> khLuongThuc = chiTieuKeHoachNamRes.getKhLuongThuc().stream()
                .filter(k -> k.getMaDonVi().equals(dvql))
                .collect(Collectors.toList());

        double tdcXtnTongGao = 0D;
        double tdcXtnTongThoc = 0D;
        double tdcXtnTongSoQuyThoc = 0D;
        double tdcNtnTongGao = 0D;
        double tdcNtnTongThoc = 0D;
        double tdcNtnTongSoQuyThoc = 0D;
        if (!CollectionUtils.isEmpty(khLuongThuc)) {
            tdcXtnTongGao = khLuongThuc.stream().map(KeHoachLuongThucDuTruRes::getXtnTongGao).mapToDouble(Double::doubleValue).sum();
            tdcXtnTongThoc = khLuongThuc.stream().map(KeHoachLuongThucDuTruRes::getXtnTongThoc).mapToDouble(Double::doubleValue).sum();
            tdcXtnTongSoQuyThoc = khLuongThuc.stream().map(KeHoachLuongThucDuTruRes::getXtnTongSoQuyThoc).mapToDouble(Double::doubleValue).sum();

            tdcNtnTongGao = khLuongThuc.stream().map(KeHoachLuongThucDuTruRes::getNtnGao).mapToDouble(Double::doubleValue).sum();
            tdcNtnTongThoc = khLuongThuc.stream().map(KeHoachLuongThucDuTruRes::getNtnThoc).mapToDouble(Double::doubleValue).sum();
            tdcNtnTongSoQuyThoc = khLuongThuc.stream().map(KeHoachLuongThucDuTruRes::getNtnTongSoQuyThoc).mapToDouble(Double::doubleValue).sum();
        } else if (apiSoLuongTdc) {
            return Collections.emptyList();
        }

        List<DxDcLtRes> ltResponseList = new ArrayList<>();
        DxDcLtRes ltXuatRes = new DxDcLtRes();
        ltXuatRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.XUAT_TRONG_NAM.getValue());
        ltXuatRes.setMaVatTuGao(Constants.LuongThucMuoiConst.GAO_MA_VT);
        ltXuatRes.setMaVatTuThoc(Constants.LuongThucMuoiConst.THOC_MA_VT);
        ltXuatRes.setTdcGao(tdcXtnTongGao);
        ltXuatRes.setTdcThoc(tdcXtnTongThoc);
        ltXuatRes.setTdcTongSoQuyThoc(tdcXtnTongSoQuyThoc);
        ltXuatRes.setDcTongSoQuyThoc(0D);
        ltXuatRes.setSdcTongSoQuyThoc(0D);
        ltXuatRes.setDcThoc(0D);
        ltXuatRes.setDcGao(0D);
        ltXuatRes.setSdcThoc(0D);
        ltXuatRes.setSdcGao(0D);

        DxDcLtRes ltNhapRes = new DxDcLtRes();
        ltNhapRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.NHAP_TRONG_NAM.getValue());
        ltNhapRes.setMaVatTuGao(Constants.LuongThucMuoiConst.GAO_MA_VT);
        ltNhapRes.setMaVatTuThoc(Constants.LuongThucMuoiConst.THOC_MA_VT);
        ltNhapRes.setTdcGao(tdcNtnTongGao);
        ltNhapRes.setTdcThoc(tdcNtnTongThoc);
        ltNhapRes.setTdcTongSoQuyThoc(tdcNtnTongSoQuyThoc);
        ltNhapRes.setDcTongSoQuyThoc(0D);
        ltNhapRes.setSdcTongSoQuyThoc(0D);
        ltNhapRes.setDcThoc(0D);
        ltNhapRes.setDcGao(0D);
        ltNhapRes.setSdcThoc(0D);
        ltNhapRes.setSdcGao(0D);

        ltResponseList.add(ltXuatRes);
        ltResponseList.add(ltNhapRes);
        return ltResponseList;
    }
    @Override
    public Boolean exportListToExcel(SearchDxDcKeHoachNamReq req, HttpServletResponse response) throws Exception {
        req.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
        List<DxDcKeHoachNamRes> list = this.search(req).get().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list))
            return true;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            //STYLE
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(11);
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            XSSFSheet sheet = workbook.createSheet(SHEET_DE_XUAT_DIEU_CHINH_KE_HOACH_NAM);
            Row row0 = sheet.createRow(0);
            //STT

            ExcelUtils.createCell(row0, 0, STT, style, sheet);
            ExcelUtils.createCell(row0, 1, SO_VAN_BAN, style, sheet);
            ExcelUtils.createCell(row0, 2, NGAY_HIEU_LUC, style, sheet);
            ExcelUtils.createCell(row0, 3, SO_QUYET_DINH, style, sheet);
            ExcelUtils.createCell(row0, 4, NAM_KE_HOACH, style, sheet);
            ExcelUtils.createCell(row0, 5, TRANG_THAI, style, sheet);

            style = workbook.createCellStyle();
            font = workbook.createFont();
            font.setFontHeight(11);
            style.setFont(font);

            Row row;
            int startRowIndex = 1;

            for (DxDcKeHoachNamRes item : list) {
                row = sheet.createRow(startRowIndex);
                ExcelUtils.createCell(row, 0, startRowIndex, style, sheet);
                ExcelUtils.createCell(row, 1, item.getSoVanBan(), style, sheet);
                ExcelUtils.createCell(row, 2, LocalDateTimeUtils.localDateToString(item.getNgayHieuLuc()), style, sheet);
                ExcelUtils.createCell(row, 3, item.getSoQdKeHoachNam(), style, sheet);
                ExcelUtils.createCell(row, 4, item.getNamKeHoach(), style, sheet);
                ExcelUtils.createCell(row, 5, DxDcKeHoachNamStatusEnum.getTrangThaiDuyetById(item.getTrangThai()), style, sheet);
                startRowIndex++;
            }

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            log.error("Error export", e);
            return false;
        }
        return null;
    }

    @Override
    public Long countDxDc() throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request");

        if (Constants.CUC_KHU_VUC.equals(userInfo.getCapDvi())) {
            return dxDcKeHoachNamRepository.countByMaDvi(userInfo.getDvql());
        } else if (Constants.TONG_CUC.equals(userInfo.getCapDvi())){
            return dxDcKeHoachNamRepository.countDxDcKeHoachNam(new SearchDxDcKeHoachNamReq(), Collections.singleton(DxDcKeHoachNamStatusEnum.BAN_HANH.getId()));
        }

       return 0L;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean deleteMultiple(DeleteReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request");

        if (CollectionUtils.isEmpty(req.getIds()))
            return false;

        List<Long> dxDcLtVtIds = dxDcLtVtRespository.findIdByDxdckhnIdIn(req.getIds());
        if (!CollectionUtils.isEmpty(dxDcLtVtIds))
            dxDcLtVtCtRepository.deleteByDxDcLtVtIdIn(dxDcLtVtIds);

        dxDcLtVtRespository.deleteByDxdckhnIdIn(req.getIds());
        dxDcKeHoachNamRepository.deleteByIdIn(req.getIds());
        fileDinhKemService.deleteMultiple(req.getIds(), Lists.newArrayList(DxDcKeHoachNam.TABLE_NAME));
        return true;
    }
}
