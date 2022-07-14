package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import lombok.Data;

@Data
public class KhQdBtcBoNganhCtietReq {
    private Long id;

    private Long idDanhMuc;

    private Long sluongDtoan;

    private String loaiVthh;

    private String cloaiVthh;

    private Long idQdBtcNganh;

    private String type;

}
