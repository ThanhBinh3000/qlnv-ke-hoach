package com.tcdt.qlnvkhoach.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Contains {
    public static final String FORMAT_DATE_STR = "yyyy-MM-dd";
    public static String convertDateToString(Date date) throws Exception {
        if (Objects.isNull(date)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(Contains.FORMAT_DATE_STR);
        return df.format(date);
    }
    public static<T> void  updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Contains.FORMAT_DATE_STR));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.updateValue(source, objectEdit);
    }
    // Trang thai
    public static final String MOI_TAO = "00";
    public static final String HOAT_DONG = "01";
    // Trang thai tờ tr
    public static final String CHO_DUYET = "01";
    public static final String DUYET = "02";
    public static final String TU_CHOI = "03";
    public static final String HUY = "04";
    public static final String TONG_HOP = "05";
    public static final String CCUC_DUYET = "06";
    public static final String CUC_DUYET = "07";
    public static final String TCUC_DUYET = "08";
    public static final String TK_DUYET = "06";// Trang thai trung gian, thu kho phe duyet
    public static final String KTV_DUYET = "07";// Trang thai trung gian, ky thuat vien phe duyet
    public static final String KTT_DUYET = "08";// Trang thai trung gian, ke toan truong phe duyet
    public static final String TPHONG_DUYET = "09"; // Trang thai Truong phong duyet
    public static final String LANHDAO_DUYET = "10"; // Trang thai Lanh dao duyet
    //Trạng thái đề xuất PAG
    public static final String DUTHAO = "00";
    public static final String CHODUYET_TP = "01";
    public static final String TUCHOI_TP = "02";
    public static final String CHODUYET_LDC  = "03";
    public static final String TUCHOI_LDC = "04";
    public static final String DADUYET_LDC = "05";
    // Trạng thái tổng hợp
    public static final String CHUATAO_QD = "26";
    public static final String DADUTHAO_QD = "27";
    public static final String DABANHANH_QD = "28";
    public static final String BAN_HANH = "29";
    // Trạng thái tổng hợp bản ghi gốc
    public static final String CHUA_TH = "24";
    public static final String DA_TH = "25";
    //Trạng thái tạo tờ trình (Phương án giá)
    public static final String CHUATAOTOTRINH = "31";
    public static final String DATAOTOTRINH = "32";

    //Trạng thái của tờ trình (Phương án giá)
    public static final String CHODUYET_LDV = "18";
    public static final String TUCHOI_LDV = "19";
    public static final String DADUYET_LDV = "20";

    public static final Map<String, String> mpTrangThaiTT;
    static {
        mpTrangThaiTT = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap().put(Contains.CHUATAOTOTRINH, "Chưa tạo tờ trình")
                .put(Contains.DATAOTOTRINH, "Đã tạo tờ trình").get();
    }
    public static String getTrangThaiTT(String key) {
        return Contains.mpTrangThaiTT.get(key);
    }

    // Trang thai response
    public static final int RESP_SUCC = 0;
    public static final int RESP_FAIL = 1;
    // Loai dieu chinh quyet dinh
    public static final String QD_GOC = "00";
    public static final String DC_GIA = "01";
    public static final String DC_SO_LUONG = "02";
    public static final String DC_THOI_GIAN = "03";
    public static final String DC_KHAC = "04";
    // Loai de xuat
    public static final String DX_THANH_LY = "00";
    public static final String DX_TIEU_HUY = "01";
    // Loai hang
    @Deprecated
    public static final String VAT_TU = "00";
    @Deprecated
    public static final String LUONG_THUC_MUOI = "01";
    // Loai vat tu hang hoa
    public static final String LOAI_VTHH_GAO = "0102";
    public static final String LOAI_VTHH_THOC = "0101";
    public static final String LOAI_VTHH_MUOI = "04";
    public static final String LOAI_VTHH_VATTU = "02";

    // Don vi tinh
    public static final String DVT_KG = "kg";
    public static final String DVT_YEN = "yen";
    public static final String DVT_TA = "ta";
    public static final String DVT_TAN = "tan";
    public static final String COLUMN_CONVERT = "SoLuong";
    //Ke hoach
    public static final String KH_NHAP_XUAT_LT = "NX_LT";
    public static final String KH_MUA_TANG = "MT";
    public static final String KH_XUAT_GIAM = "XG";
    public static final String KH_XUAT_BAN = "XB";
    public static final String KH_LUAN_PHIEN_DOI_HANG = "LP_DH";

    public static final String KH_MUA_BU= "MB";

    public static final String KH_MUA_BO_SUNG= "MBS";

    public static final Map<String, String> mappingLoaiDx;
    static {
        mappingLoaiDx = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap().put(Contains.DX_THANH_LY, "Thanh lý")
                .put(Contains.DX_TIEU_HUY, "Tiêu hủy").get();
    }

    public static final Map<String, String> mappingLoaiDc;
    static {
        mappingLoaiDc = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap().put(Contains.DC_GIA, "Điều chỉnh giá")
                .put(Contains.DC_SO_LUONG, "Điều chỉnh số lượng").put(Contains.DC_THOI_GIAN, "Điều chỉnh thời gian")
                .put(Contains.DC_KHAC, "Điều chỉnh khác").get();
    }
    public static String getLoaiDc(String key) {
        return Contains.mappingLoaiDc.get(key);
    }

    public static final Map<String, String> mpLoaiVthh;
    static {
        mpLoaiVthh = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap().put(Contains.LOAI_VTHH_GAO, "Gạo")
                .put(Contains.LOAI_VTHH_THOC, "Thóc").put(Contains.LOAI_VTHH_MUOI, "Muối")
                .put(Contains.LOAI_VTHH_VATTU, "Vật tư").get();
    }
    public static String getLoaiVthh(String key) {
        return Contains.mpLoaiVthh.get(key);
    }
    public static final Map<String, String> mpTrangThaiTH;
    static {
        mpTrangThaiTH = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap().put(Contains.DA_TH, "Đã tổng hợp")
                .put(Contains.CHUA_TH, "Chưa tổng hợp").get();
    }
    public static String getThTongHop(String key) {
        return Contains.mpTrangThaiTH.get(key);
    }
    public static final Map<String, String> mpTrangThaiPagTH;
    static {
        mpTrangThaiPagTH = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap().put(Contains.CHUATAO_QD, "Chưa tạo quyết định")
                .put(Contains.DADUTHAO_QD, "Dự thảo quyết định").put(Contains.DABANHANH_QD, "Ban hành quyết định").put(Contains.BAN_HANH, "Ban hành").get();
    }
    public static String getThPagTongHop(String key) {
        return Contains.mpTrangThaiPagTH.get(key);
    }


    public static final Map<String, BigDecimal> mpDVTinh;
    static {
        mpDVTinh = com.tcdt.qlnvhang.util.Maps.<String, BigDecimal>buildMap().put(Contains.DVT_KG, new BigDecimal(1))
                .put(Contains.DVT_YEN, new BigDecimal(10)).put(Contains.DVT_TA, new BigDecimal(100))
                .put(Contains.DVT_TAN, new BigDecimal(1000)).get();
    }

    public static BigDecimal getDVTinh(String key) {
        return Contains.mpDVTinh.get(key);
    }

    public static final Map<String, String> mapTrangThaiPheDuyet;
    static {
        mapTrangThaiPheDuyet = com.tcdt.qlnvhang.util.Maps.<String, String>buildMap()
                .put(Contains.MOI_TAO, "Mới tạo")
                .put(Contains.CHO_DUYET, "Chờ duyệt")
                .put(Contains.DUYET, "Đã duyệt")
                .put(Contains.TU_CHOI, "Từ chối")
                .put(Contains.HUY, "Hủy")
                .put(Contains.TONG_HOP, "Tổng hợp")
                .put(Contains.CCUC_DUYET, "Chi cục duyệt")
                .put(Contains.CUC_DUYET, "Cục duyệt")
                .put(Contains.TCUC_DUYET, "Tổng cục duyệt")
                .put(Contains.TK_DUYET, "Thủ kho duyệt")
                .put(Contains.KTV_DUYET, "Kỹ thuật viên duyệt")
                .put(Contains.KTT_DUYET, "Kế toán trưởng duyệt")
                .put(Contains.TPHONG_DUYET, "Trưởng phòng duyệt")
                .put(Contains.LANHDAO_DUYET, "Lãnh đạo duyệt")
                .get();
    }


}
