package com.tcdt.qlnvkhoach.request.search.catalog.khcnbq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class CnCtrinhNcuuSearchReq extends BaseRequest {

    String maDt;

    String tenDt;

    String capDt;

    Integer namTu;

    Integer namDen;

    String trangThai;

}
