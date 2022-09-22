package com.tcdt.qlnvkhoach.request.denghicapphibonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhDnCapPhiBoNganhCt1Request {
    private Long id;
    private String tenDvCungCap;
    private Long soTaiKhoan;
    private String nganHang;
    private String maVatTuCha;
    private String maVatTu;
    private String tenHangHoa;
    private Long dnCapPhiId;
    private String ycCapThemPhi;
    private List<KhDnCapPhiBoNganhCt2Request> ct2List;
}
