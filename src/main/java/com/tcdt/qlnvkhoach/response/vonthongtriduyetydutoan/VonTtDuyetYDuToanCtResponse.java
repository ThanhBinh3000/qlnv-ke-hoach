package com.tcdt.qlnvkhoach.response.vonthongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VonTtDuyetYDuToanCtResponse {
    private Long id;

    private String muc;

    private String tieuMuc;

    private BigDecimal soTien;

    private String chuThich;

    private Long idTtdydt;
}
