package com.tcdt.qlnvkhoach.request.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KhDnThCapVonRequest {

    private Long id;

    private String maTongHop;

    private Integer nam;

    private String nguonTongHop;

    private LocalDate ngayTongHop;

    private String noiDung;

    private String maDvi;

    private String capDvi;

    private List<Long> khDnCapVonIds = new ArrayList<>();

    private List<KhDnThCapVonCt1Request> ct1s = new ArrayList<>();

    private FileDinhKemReq fileDinhKem;
}
