package com.tcdt.qlnvkhoachphi.request.search.catalog.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SoLuongTruocDieuChinhSearchReq {
    private Long donViId;
    private Long ctkhnId;
    private Set<Long> vatTuIds = new HashSet<>();
}
