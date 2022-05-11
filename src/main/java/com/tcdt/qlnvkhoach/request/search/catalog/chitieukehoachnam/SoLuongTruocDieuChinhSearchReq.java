package com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SoLuongTruocDieuChinhSearchReq {
    private Long donViId;
    private Long ctkhnId;
    private Set<Long> vatTuIds = new HashSet<>();
}
