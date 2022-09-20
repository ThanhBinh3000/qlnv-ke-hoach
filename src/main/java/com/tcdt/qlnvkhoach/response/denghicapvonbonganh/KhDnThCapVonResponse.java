package com.tcdt.qlnvkhoach.response.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.response.CommonResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KhDnThCapVonResponse extends CommonResponse {
    private Long id;

    private String maTongHop;

    private Integer nam;

    private String nguonTongHop;

    private LocalDate ngayTongHop;

    private String noiDung;

    private String maDvi;

    private String capDvi;

    private BigDecimal tongTien;

    private BigDecimal kinhPhiDaCap;

    private BigDecimal ycCapThem;

    private List<KhDnThCapVonCtResponse> cts = new ArrayList<>();
}
