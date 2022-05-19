package com.tcdt.qlnvkhoach.query.dto;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DxDcQueryDto {
    private DxDcKeHoachNam dx;
    private String soQuyetDinh;
}
