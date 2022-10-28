package com.tcdt.qlnvkhoach.request.search.catalog.phuongangia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import java.util.Date;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KhLtPhuongAnGiaSearchReq extends BaseRequest {

    Integer namKh;

    String loaiHh;

    String soDx;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayKyTu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayKyDen;

    String trichYeu;

    String type;

    @NotNull(message = "Không được để trống")
    String pagType;

    List<String> dsTrangThai;

    String maDvi;
}
