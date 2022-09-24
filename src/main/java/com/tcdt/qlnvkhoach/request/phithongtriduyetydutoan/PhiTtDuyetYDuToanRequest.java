package com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhiTtDuyetYDuToanRequest {
    private Long id;
    @NotNull(message = "Không được để trống")
    private Integer nam;
    @NotNull(message = "Không được để trống")
    private String soThongTri;
    @NotNull(message = "Không được để trống")
    private LocalDate ngayLap;
    @NotNull(message = "Không được để trống")
    private String lyDoChi;
    @NotNull(message = "Không được để trống")
    private Long soDnCapPhi;
    @NotNull(message = "Không được để trống")
    private String maDvi;
    @NotNull(message = "Không được để trống")
    private String loai;
    @NotNull(message = "Không được để trống")
    private String khoan;
    @NotNull(message = "Không được để trống")
    private String chuong;

    private List<PhiTtDuyetYDuToanCtRequest> chiTietList;
    private List<FileDinhKemReq> fileDinhKems;
}
