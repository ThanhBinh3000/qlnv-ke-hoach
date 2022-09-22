package com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer nam;

    private String soThongTri;

    private LocalDate ngayLap;

    private String lyDoChi;

    private Long soDnCapPhi;

    private String maDvi;
    private String loai;

    private String khoan;
    private String chuong;

    private List<PhiTtDuyetYDuToanCtRequest> chiTietList;
    private List<FileDinhKemReq> fileDinhKems;
}
