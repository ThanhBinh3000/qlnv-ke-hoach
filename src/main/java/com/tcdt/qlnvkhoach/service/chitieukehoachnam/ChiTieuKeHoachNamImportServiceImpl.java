package com.tcdt.qlnvkhoach.service.chitieukehoachnam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ListKeHoachRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.StringHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ChiTieuKeHoachNamImportServiceImpl implements ChiTieuKeHoachNamImportService {

    private static final int DATA_ROW_INDEX = 6;
    private static final int LUONG_THUC_DATA_ROW_NHAP_INDEX = 4;
    private static final int MUOI_DATA_ROW_NHAP_INDEX = 3;

    private static final int VAT_TU_DATA_ROW_NHAP_INDEX = 3;
    private static final int VAT_TU_DATA_ROW_NHAP_4_INDEX = 2;

    // Ke hoach luong thuc
    private static final Integer STT_INDEX = 0;
    private static final Integer KHU_VUC_INDEX = 1;

    private static final Integer TKDN_TONG_SO_QUY_THOC_INDEX = 2;
    private static final Integer TKDN_TONG_THOC_INDEX = 3;
    private static final Integer TKDN_THOC_NHAP_1_INDEX = 4;
    private static final Integer TKDN_THOC_NHAP_2_INDEX = 5;
    private static final Integer TKDN_THOC_NHAP_3_INDEX = 6;
    private static final Integer TKDN_TONG_GAO_INDEX = 7;
    private static final Integer TKDN_GAO_NHAP_1_INDEX = 8;
    private static final Integer TKDN_GAO_NHAP_2_INDEX = 9;

    private static final Integer NTN_TONG_SO_QUY_THOC_INDEX = 10;
    private static final Integer NTN_THOC_INDEX = 11;
    private static final Integer NTN_GAO_INDEX = 12;

    private static final Integer XTN_TONG_SO_QUY_THOC_INDEX = 13;
    private static final Integer XTN_TONG_THOC_INDEX = 14;
    private static final Integer XTN_THOC_NHAP_1_INDEX = 15;
    private static final Integer XTN_THOC_NHAP_2_INDEX = 16;
    private static final Integer XTN_THOC_NHAP_3_INDEX = 17;
    private static final Integer XTN_TONG_GAO_INDEX = 18;
    private static final Integer XTN_GAO_NHAP_1_INDEX = 19;
    private static final Integer XTN_GAO_NHAP_2_INDEX = 20;

    private static final Integer TKCN_TONG_SO_QUY_THOC_INDEX = 21;
    private static final Integer TKCN_THOC_INDEX = 22;
    private static final Integer TKCN_GAO_INDEX = 23;


    // Ke hoach muoi
    private static final Integer TKDN_TONG_SO_MUOI_INDEX = 2;
    private static final Integer TKDN_MUOI_NHAP_1_INDEX = 3;
    private static final Integer TKDN_MUOI_NHAP_2_INDEX = 4;
    private static final Integer TKDN_MUOI_NHAP_3_INDEX = 5;

    private static final Integer NTN_TONG_SO_MUOI_INDEX = 6;

    private static final Integer XTN_TONG_SO_MUOI_INDEX = 9;
    private static final Integer XTN_MUOI_NHAP_1_INDEX = 10;
    private static final Integer XTN_MUOI_NHAP_2_INDEX = 11;
    private static final Integer XTN_MUOI_NHAP_3_INDEX = 12;

    private static final Integer TKCN_TONG_SO_MUOI_INDEX = 13;

    // Ke hoach vat tu
    private static final Integer MA_HANG_INDEX = 2;
    private static final Integer MAT_HANG_INDEX = 3;
    private static final Integer DON_VI_TINH_INDEX = 6;
    private static final Integer TKCN_TONG_SO_INDEX = 7;
    private static final Integer TKCN_TONG_INDEX = 8;
    private static final Integer TKCN_KE_HOACH_NHAP_1_INDEX = 9;
    private static final Integer TKCN_KE_HOACH_NHAP_2_INDEX = 10;
    private static final Integer TKCN_KE_HOACH_NHAP_3_INDEX = 11;
    private static final Integer TKCN_KE_HOACH_NHAP_4_INDEX = 12;


    @Autowired
    private QlnvDmVattuRepository qlnvDmVattuRepository;

    @Autowired
    private QlnvDmDonviRepository qlnvDmDonviRepository;

    private static Integer getNamNhap(Row row, Integer index) throws Exception {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null)
            throw new Exception(String.format("Column %d error", index));

        String value = cell.getStringCellValue();
        return Integer.valueOf(StringHelper.extract(value, "(\\d{4})").trim());
    }

    private static Double getSoLuong(Row row, Integer index) throws Exception {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

        if (cell == null)
            return 0d;

        return cell.getNumericCellValue();
    }

    private static String getString(Row row, Integer index) throws Exception {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null)
            return null;
        DataFormatter dataFormatter = new DataFormatter();
//        if (CellType.NUMERIC.equals(cell.getCellType())) {
//            return String.valueOf((int) cell.getNumericCellValue()).trim();
//        }
        return dataFormatter.formatCellValue(cell).trim();
    }

    @Override
    public ListKeHoachRes importKeHoach(MultipartFile file) {
        ListKeHoachRes response = new ListKeHoachRes();
        Map<String, QlnvDmDonvi> mapDonVi = Lists.newArrayList(qlnvDmDonviRepository.findAll())
                .stream().collect(Collectors.toMap(QlnvDmDonvi::getTenDvi, Function.identity()));

        try {
            InputStream excelFile = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(excelFile);
            int numberOfSheet = workbook.getNumberOfSheets();
            if (numberOfSheet >= 1) {
                Sheet khLuongThuc = workbook.getSheetAt(0);
                if (khLuongThuc != null) {
                    List<KeHoachLuongThucDuTruRes> keHoachLuongThucDuTruResList = this.importKeHoachLuongThuc(khLuongThuc, mapDonVi);
                    response.setKhluongthuc(keHoachLuongThucDuTruResList);
                }
            }

            if (numberOfSheet >= 2) {
                Sheet khMuoi = workbook.getSheetAt(1);
                if (khMuoi != null) {
                    List<KeHoachMuoiDuTruRes> keHoachMuoiDuTruRes = this.importKeHoachMuoi(khMuoi, mapDonVi);
                    response.setKhMuoi(keHoachMuoiDuTruRes);
                }
            }

            if (numberOfSheet >= 3) {
                Sheet khVatTu = workbook.getSheetAt(2);
                if (khVatTu != null) {
                    List<KeHoachVatTuRes> keHoachVatTuRes = this.importKeHoachVatTu(khVatTu, mapDonVi);
                    response.setKhVatTu(keHoachVatTuRes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private List<KeHoachVatTuRes> importKeHoachVatTu(Sheet sheet, Map<String, QlnvDmDonvi> mapDonVi) throws Exception {

        List<QlnvDmVattu> vattuList = Lists.newArrayList(qlnvDmVattuRepository.findAll());
        Map<String, QlnvDmVattu> mapMaVatTu = vattuList.stream().collect(Collectors.toMap(QlnvDmVattu::getMa, Function.identity()));

        List<KeHoachVatTuRes> responses = new ArrayList<>();
        Row headerNam = sheet.getRow(VAT_TU_DATA_ROW_NHAP_INDEX);
        Integer tkcnNamNhap1 = getNamNhap(headerNam, TKCN_KE_HOACH_NHAP_1_INDEX);
        Integer tkcnNamNhap2 = getNamNhap(headerNam, TKCN_KE_HOACH_NHAP_2_INDEX);
        Integer tkcnNamNhap3 = getNamNhap(headerNam, TKCN_KE_HOACH_NHAP_3_INDEX);

        int count = 0;

        Double currentStt = null;
        String currentKhuVuc = null;
        Map<Double, KeHoachVatTuRes> map = new HashMap<>();
        for (Row currentRow : sheet) {
            if (count < DATA_ROW_INDEX) {
                count++;
                continue;
            }
            count++;

            Cell sttCell = currentRow.getCell(STT_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Double stt = sttCell != null ? sttCell.getNumericCellValue() : null;
            String khuVuc = getString(currentRow, KHU_VUC_INDEX);
            if (stt != null)
                currentStt = stt;

            if (!StringUtils.isEmpty(khuVuc))
                currentKhuVuc = khuVuc;

            String maHang = getString(currentRow, MA_HANG_INDEX);
            String matHang = getString(currentRow, MAT_HANG_INDEX);
            String donViTinh = getString(currentRow, DON_VI_TINH_INDEX);
            Double tkcnTongSo = getSoLuong(currentRow, TKCN_TONG_SO_INDEX);
            Double tkcnTong = getSoLuong(currentRow, TKCN_TONG_INDEX);

            Double tkcnNhap1 = getSoLuong(currentRow, TKCN_KE_HOACH_NHAP_1_INDEX);
            Double tkcnNhap2 = getSoLuong(currentRow, TKCN_KE_HOACH_NHAP_2_INDEX);
            Double tkcnNhap3 = getSoLuong(currentRow, TKCN_KE_HOACH_NHAP_3_INDEX);
            Double tkcnNhap4 = getSoLuong(currentRow, TKCN_KE_HOACH_NHAP_4_INDEX);

            if ("Cộng".equalsIgnoreCase(khuVuc)) {
                break;
            }

            KeHoachVatTuRes response = map.get(currentStt);
            if (response == null) {
                response = new KeHoachVatTuRes();
                map.put(currentStt, response);
                responses.add(response);
            }

            response.setStt(currentStt != null ? currentStt.intValue() : null);
            response.setTenDonVi(currentKhuVuc);

            QlnvDmDonvi donvi = mapDonVi.get(currentKhuVuc);
            if (donvi == null)
                throw new Exception("Đơn vị không tồn tại");
            response.setDonViId(donvi.getId());
            response.setMaDonVi(donvi.getMaDvi());

            List<VatTuThietBiRes> vatTuThietBi = response.getVatTuThietBi();
            VatTuThietBiRes vatTuThietBiRes = new VatTuThietBiRes();
            vatTuThietBiRes.setMaVatTu(maHang);
            vatTuThietBiRes.setTenVatTu(matHang);
            vatTuThietBiRes.setDonViTinh(donViTinh);
            vatTuThietBiRes.setTongNhap(tkcnTongSo);
            vatTuThietBiRes.setTongCacNamTruoc(tkcnTong);
            vatTuThietBiRes.setNhapTrongNam(tkcnNhap4);

            QlnvDmVattu vattu = mapMaVatTu.get(maHang);
            if (vattu == null)
                throw new Exception("Vật tư không tồn tại");

            vatTuThietBiRes.setMaVatTuCha(vattu.getMaCha());
            List<VatTuNhapRes> cacNamTruoc = new ArrayList<>();
            cacNamTruoc.add(new VatTuNhapRes(null, tkcnNamNhap1, tkcnNhap1, null));
            cacNamTruoc.add(new VatTuNhapRes(null, tkcnNamNhap2, tkcnNhap2, null));
            cacNamTruoc.add(new VatTuNhapRes(null, tkcnNamNhap3, tkcnNhap3, null));
            vatTuThietBiRes.setCacNamTruoc(cacNamTruoc);
            vatTuThietBi.add(vatTuThietBiRes);
        }

        if (CollectionUtils.isEmpty(responses))
            return Collections.emptyList();

        int vatTuStt = 0;

        for (KeHoachVatTuRes res : responses) {
            List<VatTuThietBiRes> vatTuThietBiRes = res.getVatTuThietBi();

            for (VatTuThietBiRes vtRes : vatTuThietBiRes) {
                if (StringUtils.isEmpty(vtRes.getMaVatTu()))
                    continue;

                QlnvDmVattu vattu = mapMaVatTu.get(vtRes.getMaVatTu());
                if (vattu == null)
                    throw new Exception("Vật tư không tồn tại");

                vatTuStt++;
                if (!StringUtils.isEmpty(vattu.getMaCha())) {
                    QlnvDmVattu vattuCha = mapMaVatTu.get(vattu.getMaCha());

                    if (vattuCha == null)
                        throw new Exception("Vật tư không tồn tại");

                    vtRes.setMaVatTuCha(vattuCha.getMa());
                    vtRes.setVatTuChaId(vattuCha.getId());
                    vtRes.setTenVatTuCha(vattuCha.getTen());
                }
                vtRes.setStt(vatTuStt);
                vtRes.setVatTuId(vattu.getId());
                vtRes.setTenVatTu(vattu.getTen());
                vtRes.setMaVatTu(vattu.getMa());
                vtRes.setKyHieu(vattu.getKyHieu());
                for (VatTuNhapRes vtNamTruocRes : vtRes.getCacNamTruoc()) {
                    vtNamTruocRes.setVatTuId(vattu.getId());
                }
            }
        }
        return responses;
    }

    private List<KeHoachMuoiDuTruRes> importKeHoachMuoi(Sheet sheet, Map<String, QlnvDmDonvi> mapDonVi) throws Exception {
        List<KeHoachMuoiDuTruRes> responses = new ArrayList<>();
        Row headerNam = sheet.getRow(MUOI_DATA_ROW_NHAP_INDEX);
        Integer tkdnNamMuoiNhap1 = getNamNhap(headerNam, TKDN_MUOI_NHAP_1_INDEX);
        Integer tkdnNamMuoiNhap2 = getNamNhap(headerNam, TKDN_MUOI_NHAP_2_INDEX);
        Integer tkdnNamMuoiNhap3 = getNamNhap(headerNam, TKDN_MUOI_NHAP_3_INDEX);

        Integer xtnNamMuoiNhap1 = getNamNhap(headerNam, XTN_MUOI_NHAP_1_INDEX);
        Integer xtnNamMuoiNhap2 = getNamNhap(headerNam, XTN_MUOI_NHAP_2_INDEX);
        Integer xtnNamMuoiNhap3 = getNamNhap(headerNam, XTN_MUOI_NHAP_3_INDEX);

        int count = 0;
        for (Row currentRow : sheet) {
            if (count < DATA_ROW_INDEX) {
                count++;
                continue;
            }
            count++;

            Cell sttCell = currentRow.getCell(STT_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Double stt = sttCell != null ? sttCell.getNumericCellValue() : null;

            String khuvuc = currentRow.getCell(KHU_VUC_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim();
            if ("Cộng".equalsIgnoreCase(khuvuc)) {
                break;
            }
            Double tkdnTongSoMuoi = getSoLuong(currentRow, TKDN_TONG_SO_MUOI_INDEX);
            Double tkdnMuoiNhap1 = getSoLuong(currentRow, TKDN_MUOI_NHAP_1_INDEX);
            Double tkdnMuoiNhap2 = getSoLuong(currentRow, TKDN_MUOI_NHAP_2_INDEX);
            Double tkdnMuoiNhap3 = getSoLuong(currentRow, TKDN_MUOI_NHAP_3_INDEX);

            Double ntnTongSoMuoi = getSoLuong(currentRow, NTN_TONG_SO_MUOI_INDEX);

            Double xtnTongSoMuoi = getSoLuong(currentRow, XTN_TONG_SO_MUOI_INDEX);
            Double xtnMuoiNhap1 = getSoLuong(currentRow, XTN_MUOI_NHAP_1_INDEX);
            Double xtnMuoiNhap2 = getSoLuong(currentRow, XTN_MUOI_NHAP_2_INDEX);
            Double xtnMuoiNhap3 = getSoLuong(currentRow, XTN_MUOI_NHAP_3_INDEX);

            Double tkcnTongSoMuoi = getSoLuong(currentRow, TKCN_TONG_SO_MUOI_INDEX);

            KeHoachMuoiDuTruRes response = new KeHoachMuoiDuTruRes();
            response.setStt(stt != null ? stt.intValue() : null);
            response.setTenDonVi(khuvuc);
            response.setTonKhoDauNam(tkdnTongSoMuoi);
            response.setNtnTongSoMuoi(ntnTongSoMuoi);
            response.setXtnTongSoMuoi(xtnTongSoMuoi);
            response.setTonKhoCuoiNam(tkcnTongSoMuoi);

            QlnvDmDonvi donvi = mapDonVi.get(khuvuc);
            if (donvi == null)
                throw new Exception("Đơn vị không tồn tại");
            response.setDonViId(donvi.getId());
            response.setMaDonVi(donvi.getMaDvi());

            List<VatTuNhapRes> tkdnMuoi = new ArrayList<>();
            tkdnMuoi.add(new VatTuNhapRes(null, tkdnNamMuoiNhap1, tkdnMuoiNhap1, Constants.LuongThucMuoiConst.MUOI_ID));
            tkdnMuoi.add(new VatTuNhapRes(null, tkdnNamMuoiNhap2, tkdnMuoiNhap2, Constants.LuongThucMuoiConst.MUOI_ID));
            tkdnMuoi.add(new VatTuNhapRes(null, tkdnNamMuoiNhap3, tkdnMuoiNhap3, Constants.LuongThucMuoiConst.MUOI_ID));
            response.setTkdnMuoi(tkdnMuoi);

            List<VatTuNhapRes> xtnMuoi = new ArrayList<>();
            xtnMuoi.add(new VatTuNhapRes(null, xtnNamMuoiNhap1, xtnMuoiNhap1, Constants.LuongThucMuoiConst.MUOI_ID));
            xtnMuoi.add(new VatTuNhapRes(null, xtnNamMuoiNhap2, xtnMuoiNhap2, Constants.LuongThucMuoiConst.MUOI_ID));
            xtnMuoi.add(new VatTuNhapRes(null, xtnNamMuoiNhap3, xtnMuoiNhap3, Constants.LuongThucMuoiConst.MUOI_ID));
            response.setXtnMuoi(xtnMuoi);

            responses.add(response);
        }

        return responses;
    }

    private List<KeHoachLuongThucDuTruRes> importKeHoachLuongThuc(Sheet sheet, Map<String, QlnvDmDonvi> mapDonVi) throws Exception {
        List<KeHoachLuongThucDuTruRes> responses = new ArrayList<>();
        Row headerNam = sheet.getRow(LUONG_THUC_DATA_ROW_NHAP_INDEX);
        Integer tkdnNamThocNhap1 = getNamNhap(headerNam, TKDN_THOC_NHAP_1_INDEX);
        Integer tkdnNamThocNhap2 = getNamNhap(headerNam, TKDN_THOC_NHAP_2_INDEX);
        Integer tkdnNamThocNhap3 = getNamNhap(headerNam, TKDN_THOC_NHAP_3_INDEX);

        Integer tkdnNamGaoNhap1 = getNamNhap(headerNam, TKDN_GAO_NHAP_1_INDEX);
        Integer tkdnNamGaoNhap2 = getNamNhap(headerNam, TKDN_GAO_NHAP_2_INDEX);

        Integer xtnNamThocNhap1 = getNamNhap(headerNam, XTN_THOC_NHAP_1_INDEX);
        Integer xtnNamThocNhap2 = getNamNhap(headerNam, XTN_THOC_NHAP_2_INDEX);
        Integer xtnNamThocNhap3 = getNamNhap(headerNam, XTN_THOC_NHAP_3_INDEX);

        Integer xtnNamGaoNhap1 = getNamNhap(headerNam, XTN_GAO_NHAP_1_INDEX);
        Integer xtnNamGaoNhap2 = getNamNhap(headerNam, XTN_GAO_NHAP_2_INDEX);

        int count = 0;
        for (Row currentRow : sheet) {
            if (count < DATA_ROW_INDEX) {
                count++;
                continue;
            }
            count++;

            Cell sttCell = currentRow.getCell(STT_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Double stt = sttCell != null ? sttCell.getNumericCellValue() : null;

            String khuvuc = currentRow.getCell(KHU_VUC_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().trim();
            if ("Cộng".equalsIgnoreCase(khuvuc)) {
                break;
            }
            Double tkdnTongSoQuyThoc = getSoLuong(currentRow, TKDN_TONG_SO_QUY_THOC_INDEX);

            Double tkdnTongThoc = getSoLuong(currentRow, TKDN_TONG_THOC_INDEX);
            Double tkdnThocNhap1 = getSoLuong(currentRow, TKDN_THOC_NHAP_1_INDEX);
            Double tkdnThocNhap2 = getSoLuong(currentRow, TKDN_THOC_NHAP_2_INDEX);
            Double tkdnThocNhap3 = getSoLuong(currentRow, TKDN_THOC_NHAP_3_INDEX);

            Double tkdnTongGao = getSoLuong(currentRow, TKDN_TONG_GAO_INDEX);
            Double tkdnGaoNhap1 = getSoLuong(currentRow, TKDN_GAO_NHAP_1_INDEX);
            Double tkdnGaoNhap2 = getSoLuong(currentRow, TKDN_GAO_NHAP_2_INDEX);


            Double ntnTongSoQuyThoc = getSoLuong(currentRow, NTN_TONG_SO_QUY_THOC_INDEX);
            Double ntnThoc = getSoLuong(currentRow, NTN_THOC_INDEX);
            Double ntnGao = getSoLuong(currentRow, NTN_GAO_INDEX);

            Double xtnTongSoQuyThoc = getSoLuong(currentRow, XTN_TONG_SO_QUY_THOC_INDEX);
            Double xtnTongThoc = getSoLuong(currentRow, XTN_TONG_THOC_INDEX);
            Double xtnThocNhap1 = getSoLuong(currentRow, XTN_THOC_NHAP_1_INDEX);
            Double xtnThocNhap2 = getSoLuong(currentRow, XTN_THOC_NHAP_2_INDEX);
            Double xtnThocNhap3 = getSoLuong(currentRow, XTN_THOC_NHAP_3_INDEX);
            Double xtnTongGao = getSoLuong(currentRow, XTN_TONG_GAO_INDEX);
            Double xtnGaoNhap1 = getSoLuong(currentRow, XTN_GAO_NHAP_1_INDEX);
            Double xtnGaoNhap2 = getSoLuong(currentRow, XTN_GAO_NHAP_2_INDEX);

            Double tkcnTongSoQuyThoc = getSoLuong(currentRow, TKCN_TONG_SO_QUY_THOC_INDEX);
            Double tkcnThoc = getSoLuong(currentRow, TKCN_THOC_INDEX);
            Double tkcnGao = getSoLuong(currentRow, TKCN_GAO_INDEX);

            KeHoachLuongThucDuTruRes response = new KeHoachLuongThucDuTruRes();
            response.setStt(stt != null ? stt.intValue() : null);
            response.setTenDonvi(khuvuc);
            response.setTkdnTongSoQuyThoc(tkdnTongSoQuyThoc);
            response.setTkdnTongThoc(tkdnTongThoc);
            response.setTkdnTongGao(tkdnTongGao);

            QlnvDmDonvi donvi = mapDonVi.get(khuvuc);
            if (donvi == null)
                throw new Exception("Đơn vị không tồn tại");
            response.setDonViId(donvi.getId());
            response.setMaDonVi(donvi.getMaDvi());

            List<VatTuNhapRes> tkdnThoc = new ArrayList<>();
            tkdnThoc.add(new VatTuNhapRes(null, tkdnNamThocNhap1, tkdnThocNhap1, Constants.LuongThucMuoiConst.THOC_ID));
            tkdnThoc.add(new VatTuNhapRes(null, tkdnNamThocNhap2, tkdnThocNhap2, Constants.LuongThucMuoiConst.THOC_ID));
            tkdnThoc.add(new VatTuNhapRes(null, tkdnNamThocNhap3, tkdnThocNhap3, Constants.LuongThucMuoiConst.THOC_ID));
            response.setTkdnThoc(tkdnThoc);

            List<VatTuNhapRes> tkdnGao = new ArrayList<>();
            tkdnGao.add(new VatTuNhapRes(null, tkdnNamGaoNhap1, tkdnGaoNhap1, Constants.LuongThucMuoiConst.GAO_ID));
            tkdnGao.add(new VatTuNhapRes(null, tkdnNamGaoNhap2, tkdnGaoNhap2, Constants.LuongThucMuoiConst.GAO_ID));
            response.setTkdnGao(tkdnGao);

            response.setNtnTongSoQuyThoc(ntnTongSoQuyThoc);
            response.setNtnThoc(ntnThoc);
            response.setNtnGao(ntnGao);

            response.setXtnTongSoQuyThoc(xtnTongSoQuyThoc);
            response.setXtnTongThoc(xtnTongThoc);
            response.setXtnTongGao(xtnTongGao);

            List<VatTuNhapRes> xtnThoc = new ArrayList<>();
            xtnThoc.add(new VatTuNhapRes(null, xtnNamThocNhap1, xtnThocNhap1, Constants.LuongThucMuoiConst.THOC_ID));
            xtnThoc.add(new VatTuNhapRes(null, xtnNamThocNhap2, xtnThocNhap2, Constants.LuongThucMuoiConst.THOC_ID));
            xtnThoc.add(new VatTuNhapRes(null, xtnNamThocNhap3, xtnThocNhap3, Constants.LuongThucMuoiConst.THOC_ID));
            response.setXtnThoc(xtnThoc);
            List<VatTuNhapRes> xtnGao = new ArrayList<>();
            xtnGao.add(new VatTuNhapRes(null, xtnNamGaoNhap1, xtnGaoNhap1, Constants.LuongThucMuoiConst.GAO_ID));
            xtnGao.add(new VatTuNhapRes(null, xtnNamGaoNhap2, xtnGaoNhap2, Constants.LuongThucMuoiConst.GAO_ID));
            response.setXtnGao(xtnGao);

            response.setTkcnTongSoQuyThoc(tkcnTongSoQuyThoc);
            response.setTkcnTongThoc(tkcnThoc);
            response.setTkcnTongGao(tkcnGao);
            responses.add(response);
        }

        return responses;
    }
}
