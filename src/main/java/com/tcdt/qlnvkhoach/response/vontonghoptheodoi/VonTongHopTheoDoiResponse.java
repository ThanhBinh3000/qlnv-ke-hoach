package com.tcdt.qlnvkhoach.response.vontonghoptheodoi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VonTongHopTheoDoiResponse {
    private Long id;
    private String soThongTri;
    private String maDviDuocDuyet;
    private String tenDviDuocDuyet;
    private Long soLenhChiTien;
    private String chuong;
    private String loai;
    private String khoan;
    private String lyDoChi;
    private BigDecimal soTien;
    private String maDviThuHuong;
    private String tenDviThuHuong;
    private String trangThai;
    private String tenTrangThai;

    private String taiKhoan;
    private String nganHang;
    private String canCu;
    private String dotTToan;

    private List<FileDinhKemChung> fileDinhKems;
}
