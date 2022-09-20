package com.tcdt.qlnvkhoach.request.thongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.thongtriduyetydutoan.TtDuyetYDuToanCt;
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
public class TtDuyetYDuToanRequest {
    private Long id;

    private Integer nam;

    private String soThongTri;

    private LocalDate ngayLap;

    private String lyDoChi;

    private Integer soDnCapVon;

    private String loai;

    private String khoan;

    private List<TtDuyetYDuToanCtRequest> chiTietList;
    private List<FileDinhKemReq> fileDinhKems;
}
