package com.tcdt.qlnvkhoach.response.denghicapphibonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.response.CommonResponse;
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
public class KhDnCapPhiBoNganhResponse extends CommonResponse {
    private Long id;
    private String maBoNganh;
    private String soDeNghi;
    private LocalDate ngayDeNghi;
    private String ghiChu;
    private String maDvi;
    private String capDvi;
    private List<FileDinhKemChung> fileDinhKems;
    private List<KhDnCapPhiBoNganhCt1Response> ct1List;
    private String tenBoNganh;
}
