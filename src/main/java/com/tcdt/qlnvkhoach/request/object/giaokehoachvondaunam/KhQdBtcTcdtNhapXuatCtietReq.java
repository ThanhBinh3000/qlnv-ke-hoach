package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KhQdBtcTcdtNhapXuatCtietReq {

    Long soLuongMuaThoc ;

    Long donGiaMuaThoc;

    Long soLuongMuaGaoLpdh;

    Long donGiaMuaGaoLqdh;

    Long soLuongMuaGaoXcht;

    Long donGiaMuaGaoXcht;

    Long soLuongBanThoc;

    Long donGiaBanThoc;

    Long soLuongBanGao;

    Long donGiaBanGao;

    Long soLuongGaoCtro;

    Long donGiaGaoCtro;

    Long tongTienVonNsnn;

    Long tongTienVonTx;
}
