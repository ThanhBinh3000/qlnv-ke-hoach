package com.tcdt.qlnvkhoach.response.phithongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
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
public class PhiTtDuyetYDuToanResponse {
    private Long id;
    private String soThongTri;
    private Integer nam;
    private LocalDate ngayLap;
    private String lyDoChi;
    private Long soDnCapPhi;
    private String maDvi;
    private String tenDvi;
    private String trangThai;
    private String tenTrangThai;
    private String loai;
    private String khoan;
    private String chuong;
    private String nhanXet;

    private List<PhiTtDuyetYDuToanCtResponse> chiTietList;

    private List<FileDinhKemChung> fileDinhKems;
}
