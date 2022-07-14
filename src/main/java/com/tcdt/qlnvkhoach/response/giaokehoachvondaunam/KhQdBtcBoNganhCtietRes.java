package com.tcdt.qlnvkhoach.response.giaokehoachvondaunam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KhQdBtcBoNganhCtietRes {

    private Long id;

    private Long idDanhMuc;

    private Long sluongDtoan;

    private String loaiVthh;

    private String cloaiVthh;

    private Long idQdBtcNganh;

    private String type;
}
