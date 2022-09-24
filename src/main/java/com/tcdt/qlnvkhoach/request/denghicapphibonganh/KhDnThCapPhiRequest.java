package com.tcdt.qlnvkhoach.request.denghicapphibonganh;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KhDnThCapPhiRequest {

    private Long id;

    private String maTongHop;

    private Integer nam;

    private String nguonTongHop;

    private LocalDate ngayTongHop;

    private String noiDung;

    private String maDvi;

    private String capDvi;

    private String maToTrinh;

    private List<KhDnThCapPhiCt1Request> cts = new ArrayList<>();

    private List<KhDnThCapPhiCt1Request> ct1s = new ArrayList<>();

    private FileDinhKemReq fileDinhKem;
}
