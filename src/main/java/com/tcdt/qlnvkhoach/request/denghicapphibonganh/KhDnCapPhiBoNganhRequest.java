package com.tcdt.qlnvkhoach.request.denghicapphibonganh;

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
public class KhDnCapPhiBoNganhRequest {
    private Long id;
    private Long nam;
    private String maBoNganh;
    private String soDeNghi;
    private LocalDate ngayDeNghi;
    private String ghiChu;
    private String maDvi;
    private String capDvi;
    private List<FileDinhKemReq> fileDinhKemReqs;
    private List<KhDnCapPhiBoNganhCt1Request> ct1List;

}
