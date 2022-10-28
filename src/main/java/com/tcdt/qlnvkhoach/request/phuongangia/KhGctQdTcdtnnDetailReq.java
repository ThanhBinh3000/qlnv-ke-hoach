package com.tcdt.qlnvkhoach.request.phuongangia;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhGctQdTcdtnnDetailReq {

    String trangThai;
    Long namKeHoach;
    String maDvi;
    String loaiVthh;
    String cloaiVthh;
}
