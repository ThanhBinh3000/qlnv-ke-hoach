package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ListKeHoachRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ImportServiceImpl implements ImportService {

    private static final int DATA_ROW_INDEX = 6;
    private static final int LUONG_THUC_DATA_ROW_NHAP_INDEX = 4;
    private static final int MUOI_DATA_ROW_NHAP_INDEX = 3;

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

    private static Long getNamNhap(Row row, Integer index) throws Exception {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null)
            throw new Exception(String.format("Column %d error", index));

        String value = cell.getStringCellValue().replaceAll(" ", "");
        return Long.valueOf(value.replace("Nhập\n", "").trim());
    }

    private static Double getSoLuong(Row row, Integer index) throws Exception {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

        if (cell == null)
            return 0d;

        return cell.getNumericCellValue();
    }

    @Override
    public ListKeHoachRes importKeHoach(MultipartFile file) {
        ListKeHoachRes response = new ListKeHoachRes();
        try {
            InputStream excelFile = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(excelFile);
            int numberOfSheet = workbook.getNumberOfSheets();
            if (numberOfSheet >= 1) {
                Sheet khLuongThuc = workbook.getSheetAt(0);
                if (khLuongThuc != null) {
                    List<KeHoachLuongThucDuTruRes> keHoachLuongThucDuTruResList = this.importKeHoachLuongThuc(khLuongThuc);
                    response.setKhluongthuc(keHoachLuongThucDuTruResList);
                }
            }

            if (numberOfSheet >= 2) {
                Sheet khMuoi = workbook.getSheetAt(1);
                if (khMuoi != null) {
                    List<KeHoachMuoiDuTruRes> keHoachMuoiDuTruRes = this.importKeHoachMuoi(khMuoi);
                    response.setKhMuoi(keHoachMuoiDuTruRes);
                }
            }

            if (numberOfSheet >= 3) {
                Sheet khVatTu = workbook.getSheetAt(2);
                if (khVatTu != null) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private List<KeHoachMuoiDuTruRes> importKeHoachMuoi(Sheet sheet) {
        List<KeHoachMuoiDuTruRes> responses = new ArrayList<>();
        try {
            Row headerNam = sheet.getRow(MUOI_DATA_ROW_NHAP_INDEX);
            Long tkdnNamMuoiNhap1 = getNamNhap(headerNam, TKDN_MUOI_NHAP_1_INDEX);
            Long tkdnNamMuoiNhap2 = getNamNhap(headerNam, TKDN_MUOI_NHAP_2_INDEX);
            Long tkdnNamMuoiNhap3 = getNamNhap(headerNam, TKDN_MUOI_NHAP_3_INDEX);

            Long xtnNamMuoiNhap1 = getNamNhap(headerNam, XTN_MUOI_NHAP_1_INDEX);
            Long xtnNamMuoiNhap2 = getNamNhap(headerNam, XTN_MUOI_NHAP_2_INDEX);
            Long xtnNamMuoiNhap3 = getNamNhap(headerNam, XTN_MUOI_NHAP_3_INDEX);

            int count = 0;
            for (Row currentRow : sheet) {
                if (count < DATA_ROW_INDEX) {
                    count++;
                    continue;
                }
                count++;

                Cell sttCell = currentRow.getCell(STT_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Double stt = sttCell != null ? sttCell.getNumericCellValue() : null;

                String khuvuc = currentRow.getCell(KHU_VUC_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
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
                response.setCucDTNNKhuVuc(khuvuc);
                response.setTkdnTongSoMuoi(tkdnTongSoMuoi);
                response.setNtnTongSoMuoi(ntnTongSoMuoi);
                response.setXtnTongSoMuoi(xtnTongSoMuoi);
                response.setTkcnTongSoMuoi(tkcnTongSoMuoi);

                List<VatTuNhapRes> tkdnMuoi = new ArrayList<>();
                tkdnMuoi.add(new VatTuNhapRes(tkdnNamMuoiNhap1, tkdnMuoiNhap1, null));
                tkdnMuoi.add(new VatTuNhapRes(tkdnNamMuoiNhap2, tkdnMuoiNhap2, null));
                tkdnMuoi.add(new VatTuNhapRes(tkdnNamMuoiNhap3, tkdnMuoiNhap3, null));
                response.setTkdnMuoi(tkdnMuoi);

                List<VatTuNhapRes> xtnMuoi = new ArrayList<>();
                xtnMuoi.add(new VatTuNhapRes(xtnNamMuoiNhap1, xtnMuoiNhap1, null));
                xtnMuoi.add(new VatTuNhapRes(xtnNamMuoiNhap2, xtnMuoiNhap2, null));
                xtnMuoi.add(new VatTuNhapRes(xtnNamMuoiNhap3, xtnMuoiNhap3, null));
                response.setXtnMuoi(xtnMuoi);

                responses.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }

    private List<KeHoachLuongThucDuTruRes> importKeHoachLuongThuc(Sheet sheet) {
        List<KeHoachLuongThucDuTruRes> responses = new ArrayList<>();
        try {
            Row headerNam = sheet.getRow(LUONG_THUC_DATA_ROW_NHAP_INDEX);
            Long tkdnNamThocNhap1 = getNamNhap(headerNam, TKDN_THOC_NHAP_1_INDEX);
            Long tkdnNamThocNhap2 = getNamNhap(headerNam, TKDN_THOC_NHAP_2_INDEX);
            Long tkdnNamThocNhap3 = getNamNhap(headerNam, TKDN_THOC_NHAP_3_INDEX);

            Long tkdnNamGaoNhap1 = getNamNhap(headerNam, TKDN_GAO_NHAP_1_INDEX);
            Long tkdnNamGaoNhap2 = getNamNhap(headerNam, TKDN_GAO_NHAP_2_INDEX);

            Long xtnNamThocNhap1 = getNamNhap(headerNam, XTN_THOC_NHAP_1_INDEX);
            Long xtnNamThocNhap2 = getNamNhap(headerNam, XTN_THOC_NHAP_2_INDEX);
            Long xtnNamThocNhap3 = getNamNhap(headerNam, XTN_THOC_NHAP_3_INDEX);

            Long xtnNamGaoNhap1 = getNamNhap(headerNam, XTN_GAO_NHAP_1_INDEX);
            Long xtnNamGaoNhap2 = getNamNhap(headerNam, XTN_GAO_NHAP_2_INDEX);

            int count = 0;
            for (Row currentRow : sheet) {
                if (count < DATA_ROW_INDEX) {
                    count++;
                    continue;
                }
                count++;

                Cell sttCell = currentRow.getCell(STT_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Double stt = sttCell != null ? sttCell.getNumericCellValue() : null;

                String khuvuc = currentRow.getCell(KHU_VUC_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
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
                response.setCucDTNNKhuVuc(khuvuc);
                response.setTkdnTongSoQuyThoc(tkdnTongSoQuyThoc);
                response.setTkdnTongThoc(tkdnTongThoc);
                response.setTkcnTongGao(tkdnTongGao);

                List<VatTuNhapRes> tkdnThoc = new ArrayList<>();
                tkdnThoc.add(new VatTuNhapRes(tkdnNamThocNhap1, tkdnThocNhap1, null));
                tkdnThoc.add(new VatTuNhapRes(tkdnNamThocNhap2, tkdnThocNhap2, null));
                tkdnThoc.add(new VatTuNhapRes(tkdnNamThocNhap3, tkdnThocNhap3, null));
                response.setTkdnThoc(tkdnThoc);

                List<VatTuNhapRes> tkdnGao = new ArrayList<>();
                tkdnGao.add(new VatTuNhapRes(tkdnNamGaoNhap1, tkdnGaoNhap1, null));
                tkdnGao.add(new VatTuNhapRes(tkdnNamGaoNhap2, tkdnGaoNhap2, null));
                response.setTkdnGao(tkdnGao);

                response.setNtnTongSoQuyThoc(ntnTongSoQuyThoc);
                response.setNtnThoc(ntnThoc);
                response.setNtnGao(ntnGao);

                response.setXtnTongSoQuyThoc(xtnTongSoQuyThoc);
                response.setXtnTongThoc(xtnTongThoc);
                response.setXtnTongGao(xtnTongGao);

                List<VatTuNhapRes> xtnThoc = new ArrayList<>();
                xtnThoc.add(new VatTuNhapRes(xtnNamThocNhap1, xtnThocNhap1, null));
                xtnThoc.add(new VatTuNhapRes(xtnNamThocNhap2, xtnThocNhap2, null));
                xtnThoc.add(new VatTuNhapRes(xtnNamThocNhap3, xtnThocNhap3, null));
                response.setXtnThoc(xtnThoc);
                List<VatTuNhapRes> xtnGao = new ArrayList<>();
                xtnGao.add(new VatTuNhapRes(xtnNamGaoNhap1, xtnGaoNhap1, null));
                xtnGao.add(new VatTuNhapRes(xtnNamGaoNhap2, xtnGaoNhap2, null));
                response.setXtnGao(xtnGao);

                response.setTkcnTongSoQuyThoc(tkcnTongSoQuyThoc);
                response.setTkcnTongThoc(tkcnThoc);
                response.setTkcnTongGao(tkcnGao);
                responses.add(response);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }

    public static void main(String[] args) {
        try {
            FileInputStream excelFile = new FileInputStream(new File("D:\\Book1.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet workSheet = workbook.getSheetAt(0);
            int dataIndexRow = 6;
            int dataNhapIndexRow = 4;

            Row headerNam = workSheet.getRow(dataNhapIndexRow);
            Long tkdnNamThocNhap1 = getNamNhap(headerNam, TKDN_THOC_NHAP_1_INDEX);
            Long tkdnNamThocNhap2 = getNamNhap(headerNam, TKDN_THOC_NHAP_2_INDEX);
            Long tkdnNamThocNhap3 = getNamNhap(headerNam, TKDN_THOC_NHAP_3_INDEX);

            Long tkdnNamGaoNhap1 = getNamNhap(headerNam, TKDN_GAO_NHAP_1_INDEX);
            Long tkdnNamGaoNhap2 = getNamNhap(headerNam, TKDN_GAO_NHAP_2_INDEX);

            Long xtnNamThocNhap1 = getNamNhap(headerNam, XTN_THOC_NHAP_1_INDEX);
            Long xtnNamThocNhap2 = getNamNhap(headerNam, XTN_THOC_NHAP_2_INDEX);
            Long xtnNamThocNhap3 = getNamNhap(headerNam, XTN_THOC_NHAP_3_INDEX);

            Long xtnNamGaoNhap1 = getNamNhap(headerNam, XTN_GAO_NHAP_1_INDEX);
            Long xtnNamGaoNhap2 = getNamNhap(headerNam, XTN_GAO_NHAP_2_INDEX);

            List<KeHoachLuongThucDuTruRes> responses = new ArrayList<>();
            int count = 0;
            for (Row currentRow : workSheet) {
                if (count < dataIndexRow) {
                    count++;
                    continue;
                }
                count++;

                Cell sttCell = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Double stt = sttCell != null ? sttCell.getNumericCellValue() : null;

                String khuvuc = currentRow.getCell(KHU_VUC_INDEX, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
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
                response.setCucDTNNKhuVuc(khuvuc);
                response.setTkdnTongSoQuyThoc(tkdnTongSoQuyThoc);
                response.setTkdnTongThoc(tkdnTongThoc);
                response.setTkcnTongGao(tkdnTongGao);

                List<VatTuNhapRes> tkdnThoc = new ArrayList<>();
                tkdnThoc.add(new VatTuNhapRes(tkdnNamThocNhap1, tkdnThocNhap1, null));
                tkdnThoc.add(new VatTuNhapRes(tkdnNamThocNhap2, tkdnThocNhap2, null));
                tkdnThoc.add(new VatTuNhapRes(tkdnNamThocNhap3, tkdnThocNhap3, null));
                response.setTkdnThoc(tkdnThoc);

                List<VatTuNhapRes> tkdnGao = new ArrayList<>();
                tkdnGao.add(new VatTuNhapRes(tkdnNamGaoNhap1, tkdnGaoNhap1, null));
                tkdnGao.add(new VatTuNhapRes(tkdnNamGaoNhap2, tkdnGaoNhap2, null));
                response.setTkdnGao(tkdnGao);

                response.setNtnTongSoQuyThoc(ntnTongSoQuyThoc);
                response.setNtnThoc(ntnThoc);
                response.setNtnGao(ntnGao);

                response.setXtnTongSoQuyThoc(xtnTongSoQuyThoc);
                response.setXtnTongThoc(xtnTongThoc);
                response.setXtnTongGao(xtnTongGao);

                List<VatTuNhapRes> xtnThoc = new ArrayList<>();
                xtnThoc.add(new VatTuNhapRes(xtnNamThocNhap1, xtnThocNhap1, null));
                xtnThoc.add(new VatTuNhapRes(xtnNamThocNhap2, xtnThocNhap2, null));
                xtnThoc.add(new VatTuNhapRes(xtnNamThocNhap3, xtnThocNhap3, null));
                response.setXtnThoc(xtnThoc);
                List<VatTuNhapRes> xtnGao = new ArrayList<>();
                xtnGao.add(new VatTuNhapRes(xtnNamGaoNhap1, xtnGaoNhap1, null));
                xtnGao.add(new VatTuNhapRes(xtnNamGaoNhap2, xtnGaoNhap2, null));
                response.setXtnGao(xtnGao);

                response.setTkcnTongSoQuyThoc(tkcnTongSoQuyThoc);
                response.setTkcnTongThoc(tkcnThoc);
                response.setTkcnTongGao(tkcnGao);
                responses.add(response);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
