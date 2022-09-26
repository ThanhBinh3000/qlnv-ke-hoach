package com.tcdt.qlnvkhoach.request.phitonghoptheodoi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhiTongHopTheoDoiRequest {
    private Long id;
    @NotNull(message = "Không được để trống")
    private String soThongTri;
    private String maDviDuocDuyet;
    @NotNull(message = "Không được để trống")
    private String soLenhChiTien;
    private String chuong;
    private String loai;
    private String khoan;
    private String lyDoChi;
    private BigDecimal soTien;
    @NotNull(message = "Không được để trống")
    private String dviThuHuong;
    @NotNull(message = "Không được để trống")
    private String taiKhoan;
    @NotNull(message = "Không được để trống")
    private String nganHang;
    private String canCu;
    private String dotTToan;
    private List<FileDinhKemReq> fileDinhKems;
}
