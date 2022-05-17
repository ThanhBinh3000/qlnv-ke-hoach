package com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.*;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVt;
import com.tcdt.qlnvkhoach.enums.*;
import com.tcdt.qlnvkhoach.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoach.repository.KeHoachVatTuRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcLtVtRespository;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcLtVtReq;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.DxDcLtRes;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.DxDcMuoiRes;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.DxDcVtRes;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.util.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DxDcKeHoachNamServiceImpl implements DxDcKeHoachNamService {

    @Autowired
    private DxDcKeHoachNamRepository dxDcKeHoachNamRepository;

    @Autowired
    private DxDcLtVtRespository dxDcLtVtRespository;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    @Autowired
    private ChiTieuKeHoachNamRepository chiTieuKeHoachNamRepository;

    @Autowired
    private ChiTieuKeHoachNamService chiTieuKeHoachNamService;

    @Autowired
    private KeHoachVatTuRepository keHoachVatTuRepository;

    @Override
    public List<DxDcKeHoachNamRes> search(SearchDxDcKeHoachNamReq req) throws Exception {
        List<DxDcKeHoachNam> list = dxDcKeHoachNamRepository.findAll();
        List<DxDcKeHoachNamRes> responses = new ArrayList<>();
        for (DxDcKeHoachNam dxDc : list) {
            ChiTieuKeHoachNam chiTieuKeHoachNam = this.getChiTieuKeHoachNam(dxDc.getKeHoachNamId());
            this.retrieveDataChiTieuKeHoachNam(chiTieuKeHoachNam);
            dxDc.setKeHoachNam(chiTieuKeHoachNam);
            dxDc.setDxDcLtVtList(dxDcLtVtRespository.findByDxdckhnId(dxDc.getId()));
            responses.add(this.buildResponse(dxDc));
        }
        return responses;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public DxDcKeHoachNamRes create(DxDcKeHoachNamReq req) throws Exception {

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request");

        ChiTieuKeHoachNam chiTieuKeHoachNam = this.getChiTieuKeHoachNam(req.getKeHoachNamId());
        if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(chiTieuKeHoachNam.getTrangThai())) {
            throw new Exception("Không thể tạo đề xuất cho chỉ tiêu chưa ban hành.");
        }

        DxDcKeHoachNam exist = this.existDxDckhn(null, chiTieuKeHoachNam, req.getSoVanBan());
        if (exist != null) {
            throw new Exception("Đề xuất điều chỉnh đã tồn tại.");
        }

        DxDcKeHoachNam dxDc = new DxDcKeHoachNam();
        BeanUtils.copyProperties(req, dxDc, "id");
        dxDc.setNgayTao(LocalDate.now());
        dxDc.setNguoiTaoId(userInfo.getId());
        dxDc.setTrangThai(DxDcKeHoachNamStatusEnum.DU_THAO.getId());
        dxDc.setMaDvi(userInfo.getDvql());
        dxDc.setCapDvi(userInfo.getCapDvi());
        dxDc.setKeHoachNamId(chiTieuKeHoachNam.getId());
        dxDcKeHoachNamRepository.save(dxDc);

        List<DxDcLtVtReq> dxDcLtVtReqList = req.getDxDcLtVtReqList();
        dxDc.setDxDcLtVtList(this.saveListDxDcLtVt(dxDcLtVtReqList, dxDc.getId(), new HashMap<>()));

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), dxDc.getId(), ChiTieuKeHoachNam.TABLE_NAME);
        dxDc.setFileDinhKems(fileDinhKems);
        this.retrieveDataChiTieuKeHoachNam(chiTieuKeHoachNam);
        dxDc.setKeHoachNam(chiTieuKeHoachNam);

        return this.buildResponse(dxDc);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public DxDcKeHoachNamRes update(DxDcKeHoachNamReq req) throws Exception {
        if (req == null)
            return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<DxDcKeHoachNam> optional = dxDcKeHoachNamRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Chỉ tiêu kế hoạch năm không tồn tại");

        DxDcKeHoachNam dxDc = optional.get();

        ChiTieuKeHoachNam ctkhn = this.getChiTieuKeHoachNam(req.getKeHoachNamId());
        if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(ctkhn.getTrangThai())) {
            throw new Exception("Không thể tạo đề xuất cho chỉ tiêu chưa ban hành.");
        }

        DxDcKeHoachNam exist = this.existDxDckhn(dxDc, ctkhn, req.getSoVanBan());
        if (exist != null && !exist.getId().equals(dxDc.getId()))
            throw new Exception("Đề xuất điều chỉnh kế hoạch năm đã tồn tại");

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

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), dxDc.getId(), ChiTieuKeHoachNam.TABLE_NAME);
        dxDc.setFileDinhKems(fileDinhKems);
        this.retrieveDataChiTieuKeHoachNam(ctkhn);
        dxDc.setKeHoachNam(ctkhn);

        return this.buildResponse(dxDc);
    }

    private List<DxDcLtVt> saveListDxDcLtVt(List<DxDcLtVtReq> dxDcLtVtReqList,
                                                  Long dxDcId,
                                                  Map<Long, DxDcLtVt> mapDxDcLtVt) throws Exception {

        List<DxDcLtVt> ltVts = new ArrayList<>();
        for (DxDcLtVtReq ltVtReq : dxDcLtVtReqList) {
            DxDcLtVt ltVt = new DxDcLtVt();
            Long id = ltVtReq.getId();
            if (id != null && id > 0) {
                ltVt = mapDxDcLtVt.get(id);
                if (ltVt == null)
                    throw new Exception("Đề xuất điều chỉnh lương thực vật tư không tồn tại.");
            }

            BeanUtils.copyProperties(ltVtReq, ltVt, "id");
            ltVt.setDxdckhnId(dxDcId);
            ltVts.add(ltVt);
            mapDxDcLtVt.remove(id);
        }
        if (!CollectionUtils.isEmpty(ltVts))
            dxDcLtVtRespository.saveAll(ltVts);

        return ltVts;
    }

    private DxDcKeHoachNam existDxDckhn(DxDcKeHoachNam update, ChiTieuKeHoachNam chiTieuKeHoachNam, String soVanBan) throws Exception {
        if (update == null || !update.getSoVanBan().equalsIgnoreCase(soVanBan)) {
            DxDcKeHoachNam exist = dxDcKeHoachNamRepository.findFirstBySoVanBan(soVanBan);
            if (exist != null)
                throw new Exception("Số văn bản " + soVanBan + " đã tồn tại");
        }

        return dxDcKeHoachNamRepository.findByKeHoachNamId(chiTieuKeHoachNam.getId())
                .stream().filter(c -> !DxDcKeHoachNamStatusEnum.TU_CHOI.getId().equalsIgnoreCase(c.getTrangThai()))
                .findFirst().orElse(null);

    }

    private void retrieveDataChiTieuKeHoachNam(ChiTieuKeHoachNam chiTieuKeHoachNam) {
        List<KeHoachLuongThucMuoi> keHoachLuongThucMuois = chiTieuKeHoachNamService.retrieveKhltm(chiTieuKeHoachNam);

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

    private DxDcKeHoachNamRes buildResponse(DxDcKeHoachNam dxDc) throws Exception {

        ChiTieuKeHoachNamRes chiTieuKeHoachNamRes = chiTieuKeHoachNamService.buildDetailResponse(dxDc.getKeHoachNam());

        DxDcKeHoachNamRes response = new DxDcKeHoachNamRes();
        BeanUtils.copyProperties(dxDc, response);
        response.setSoQdKeHoachNam(dxDc.getKeHoachNam().getSoQuyetDinh());
        response.setFileDinhKems(dxDc.getFileDinhKems());
        response.setDxDcltList(this.buildListDxDcLtRes(dxDc, chiTieuKeHoachNamRes));
        response.setDxDcMuoiList(this.buildListDxDcMuoiRes(dxDc, chiTieuKeHoachNamRes));
        response.setDxDcVtList(this.buildListDxDcVatTuRes(dxDc, chiTieuKeHoachNamRes));

        return response;
    }

    private List<DxDcVtRes> buildListDxDcVatTuRes(DxDcKeHoachNam dxDc,  ChiTieuKeHoachNamRes chiTieuKeHoachNamRes) {

        List<DxDcLtVt> vtList = dxDc.getDxDcLtVtList().stream()
                .filter(ltVt -> DxDcKeHoachNamLoaiEnum.VAT_TU.getValue().equals(ltVt.getLoai()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(vtList))
            return Collections.emptyList();

        List<KeHoachVatTuRes> khVatTuList = chiTieuKeHoachNamRes.getKhVatTu();
        List<VatTuThietBiRes> allVatTuThietBiRes = khVatTuList.stream()
                .flatMap(k -> k.getVatTuThietBi().stream())
                .collect(Collectors.toList());

        List<DxDcVtRes> vtResponseList = new ArrayList<>();
        vtList.forEach(vt -> {
            DxDcVtRes dxDcVtRes = new DxDcVtRes();
            dxDcVtRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.NHAP_TRONG_NAM.getValue());
            dxDcVtRes.setDonViTinh(vt.getDonViTinh());
            dxDcVtRes.setSdcKeHoachNam(vt.getSoLuong());
            dxDcVtRes.setMaVatTu(vt.getMaVatTu());
            dxDcVtRes.setMaVatTuCha(vt.getMaVatTuCha());
            double tdcTongSo = 0D;
            double cacNamTruoc = 0D;
            double tdcKeHoachNam = 0D;

            List<VatTuThietBiRes> vatTuThietBiRes = allVatTuThietBiRes.stream()
                    .filter(i -> i.getMaVatTu().equals(vt.getMaVatTu())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(vatTuThietBiRes)) {
                tdcTongSo = vatTuThietBiRes.stream()
                        .map(VatTuThietBiRes::getTongNhap)
                        .mapToDouble(Double::doubleValue).sum();

                cacNamTruoc = vatTuThietBiRes.stream()
                        .flatMap(i -> i.getCacNamTruoc().stream())
                        .map(VatTuNhapRes::getSoLuong)
                        .mapToDouble(Double::doubleValue).sum();

                tdcKeHoachNam = vatTuThietBiRes.stream()
                        .map(VatTuThietBiRes::getNhapTrongNam)
                        .mapToDouble(Double::doubleValue).sum();
            }

            dxDcVtRes.setTdcTongSo(tdcTongSo);
            dxDcVtRes.setTdcCacNamTruoc(cacNamTruoc);
            dxDcVtRes.setTdcKeHoachNam(tdcKeHoachNam);

            dxDcVtRes.setSdcKeHoachNam(vt.getSoLuong());
            dxDcVtRes.setSdcCacNamTruoc(cacNamTruoc);
            dxDcVtRes.setSdcTongSo(dxDcVtRes.getSdcKeHoachNam() + cacNamTruoc);

            dxDcVtRes.setDc(dxDcVtRes.getSdcKeHoachNam() - dxDcVtRes.getTdcKeHoachNam());
        });
        return vtResponseList;
    }

    private List<DxDcMuoiRes> buildListDxDcMuoiRes(DxDcKeHoachNam dxDc,  ChiTieuKeHoachNamRes chiTieuKeHoachNamRes) {
        List<DxDcLtVt> muoiList = dxDc.getDxDcLtVtList().stream()
                .filter(ltVt -> DxDcKeHoachNamLoaiEnum.MUOI.getValue().equals(ltVt.getLoai()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(muoiList))
            return Collections.emptyList();

        List<KeHoachMuoiDuTruRes> khMuoi = chiTieuKeHoachNamRes.getKhMuoiDuTru();
        double tdcXtn = 0D;
        double tdcNtn = 0D;
        if (!CollectionUtils.isEmpty(khMuoi)) {
            tdcXtn = khMuoi.stream().map(KeHoachMuoiDuTruRes::getXtnTongSoMuoi).mapToDouble(Double::doubleValue).sum();
            tdcNtn = khMuoi.stream().map(KeHoachMuoiDuTruRes::getNtnTongSoMuoi).mapToDouble(Double::doubleValue).sum();
        }

        List<DxDcMuoiRes> muoiResponseList = new ArrayList<>();
        DxDcMuoiRes muoiXuatRes = new DxDcMuoiRes();
        muoiXuatRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.XUAT_TRONG_NAM.getValue());
        muoiXuatRes.setMaVatTu(Constants.LuongThucMuoiConst.MUOI_MA_VT);
        muoiXuatRes.setTdc(tdcXtn);

        DxDcMuoiRes muoiNhapRes = new DxDcMuoiRes();
        muoiXuatRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.NHAP_TRONG_NAM.getValue());
        muoiXuatRes.setMaVatTu(Constants.LuongThucMuoiConst.MUOI_MA_VT);
        muoiXuatRes.setTdc(tdcNtn);

        muoiList.forEach(lt -> {
            if (DxDcKeHoachNamChiTieuEnum.XUAT_TRONG_NAM.getValue().equals(lt.getChiTieu())) {
                muoiXuatRes.setSdc(lt.getSoLuong());
            } else {
                muoiNhapRes.setSdc(lt.getSoLuong());
            }
        });
        muoiResponseList.add(muoiXuatRes);
        muoiResponseList.add(muoiNhapRes);

        muoiResponseList.forEach(muoi -> {
            muoi.setDc(muoi.getSdc() - muoi.getTdc());
        });
        return muoiResponseList;
    }

    private List<DxDcLtRes> buildListDxDcLtRes(DxDcKeHoachNam dxDc,  ChiTieuKeHoachNamRes chiTieuKeHoachNamRes) {
        List<DxDcLtVt> ltList = dxDc.getDxDcLtVtList().stream()
                .filter(ltVt -> DxDcKeHoachNamLoaiEnum.LUONG_THUC.getValue().equals(ltVt.getLoai()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(ltList))
            return Collections.emptyList();

        List<KeHoachLuongThucDuTruRes> khLuongThuc = chiTieuKeHoachNamRes.getKhLuongThuc();
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
        }

        List<DxDcLtRes> ltResponseList = new ArrayList<>();
        DxDcLtRes ltXuatRes = new DxDcLtRes();
        ltXuatRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.XUAT_TRONG_NAM.getValue());
        ltXuatRes.setMaVatTuGao(Constants.LuongThucMuoiConst.GAO_MA_VT);
        ltXuatRes.setMaVatTuThoc(Constants.LuongThucMuoiConst.THOC_MA_VT);
        ltXuatRes.setTdcGao(tdcXtnTongGao);
        ltXuatRes.setTdcThoc(tdcXtnTongThoc);
        ltXuatRes.setTdcTongSoQuyThoc(tdcXtnTongSoQuyThoc);

        DxDcLtRes ltNhapRes = new DxDcLtRes();
        ltNhapRes.setChiTieu(DxDcKeHoachNamChiTieuEnum.NHAP_TRONG_NAM.getValue());
        ltNhapRes.setMaVatTuGao(Constants.LuongThucMuoiConst.GAO_MA_VT);
        ltNhapRes.setMaVatTuThoc(Constants.LuongThucMuoiConst.THOC_MA_VT);
        ltNhapRes.setTdcGao(tdcNtnTongGao);
        ltNhapRes.setTdcThoc(tdcNtnTongThoc);
        ltNhapRes.setTdcTongSoQuyThoc(tdcNtnTongSoQuyThoc);

        ltList.forEach(lt -> {
            if (DxDcKeHoachNamChiTieuEnum.XUAT_TRONG_NAM.getValue().equals(lt.getChiTieu())) {
                if (Constants.LuongThucMuoiConst.GAO_MA_VT.equals(lt.getMaVatTu())) {
                    ltXuatRes.setSdcGao(lt.getSoLuong());
                } else {
                    ltXuatRes.setSdcThoc(lt.getSoLuong());
                }

            } else {
                if (Constants.LuongThucMuoiConst.GAO_MA_VT.equals(lt.getMaVatTu())) {
                    ltNhapRes.setSdcGao(lt.getSoLuong());
                } else {
                    ltNhapRes.setSdcThoc(lt.getSoLuong());
                }
            }
        });
        ltResponseList.add(ltXuatRes);
        ltResponseList.add(ltNhapRes);
        ltResponseList.forEach(lt -> {
            lt.setSdcTongSoQuyThoc(lt.getSdcGao() * 2 + lt.getSdcThoc());
            lt.setDcTongSoQuyThoc(lt.getSdcTongSoQuyThoc() - lt.getTdcTongSoQuyThoc());
            lt.setDcGao(lt.getSdcGao() - lt.getTdcGao());
            lt.setDcThoc(lt.getSdcThoc() - lt.getTdcGao());
        });
        return ltResponseList;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean delete(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<DxDcKeHoachNam> optional = dxDcKeHoachNamRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Đề xuất điều chỉnh không tồn tại");

        DxDcKeHoachNam dxDc = optional.get();

        if (DxDcKeHoachNamStatusEnum.BAN_HANH.getId().equals(dxDc.getTrangThai())) {
            throw new Exception("Không thể xóa đề xuất điều chỉnh đã ban hành");
        } else if (DxDcKeHoachNamStatusEnum.DU_THAO_TRINH_DUYET.getId().equals(dxDc.getTrangThai())) {
            throw new Exception("Không thể xóa đề xuất điều chỉnh trình duyệt");
        }

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

        ChiTieuKeHoachNam ctkhn = this.getChiTieuKeHoachNam(dxDc.getKeHoachNamId());
        if (!ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId().equals(ctkhn.getTrangThai())) {
            throw new Exception("Không thể tạo đề xuất cho chỉ tiêu chưa ban hành.");
        }

        this.retrieveDataChiTieuKeHoachNam(ctkhn);
        dxDc.setKeHoachNam(ctkhn);
        dxDc.setFileDinhKems(fileDinhKemService.search(dxDc.getId(), Collections.singleton(DxDcKeHoachNam.TABLE_NAME)));
        return this.buildResponse(dxDc);
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

    private ChiTieuKeHoachNam getChiTieuKeHoachNam(Long id) throws Exception {
        Optional<ChiTieuKeHoachNam> optionalKhn = chiTieuKeHoachNamRepository.findById(id);
        if (!optionalKhn.isPresent())
            throw new Exception("Không tồn tại chỉ tiêu kế hoạch năm.");

        return optionalKhn.get();
    }
}
