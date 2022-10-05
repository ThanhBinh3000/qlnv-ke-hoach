package com.tcdt.qlnvkhoach.request.vontonghoptheodoi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VonTongHopTheoDoiSearchRequest extends BaseRequest {
    private String soThongTri;
    private String maDviDuocDuyet;
    private String soLenhChiTien;
    private String chuong;
    private String loai;
    private String khoan;
}
