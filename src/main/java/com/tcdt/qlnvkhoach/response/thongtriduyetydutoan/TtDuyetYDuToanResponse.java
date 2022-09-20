package com.tcdt.qlnvkhoach.response.thongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.thongtriduyetydutoan.TtDuyetYDuToanCt;
import com.tcdt.qlnvkhoach.response.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TtDuyetYDuToanResponse {
    private Long id;
    private String soThongTri;
    private Integer nam;
    private LocalDate ngayLap;
    private String lyDoChi;
    private Integer soDnCapVon;
    private String maDvi;
    private String tenDvi;
    private String trangThai;
    private String tenTrangThai;

    private List<TtDuyetYDuToanCtResponse> chiTietList;

    private List<FileDinhKemChung> fileDinhKems;
}
