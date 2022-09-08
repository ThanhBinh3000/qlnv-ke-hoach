package com.tcdt.qlnvkhoach.request.search.catalog.phuongangia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class KhLtPagTongHopSearchReq extends BaseRequest {

    Integer namKh;

    String loaiHh;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayKyTu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayKyDen;

    String noiDung;

    String trangThai;

    String type;

    String trangThaiTh;

    String pagType;

    String trangThaiTt;

    List<String> dsTrangThai;
}
