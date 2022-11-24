package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KhQdBtcTcdtCtietReq {

    Long id;

    Long idDanhMuc;

    String loaiVthh;

    String cloaiVthh;

    Long soLuong;

    Long donGia;

    Long tongTien;

    String type;

    Long idQdBtcTcdt;

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
