package com.tcdt.qlnvkhoach.request.thongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TtDuyetYDuToanSearchRequest extends BaseRequest {
    private String soThongTri;
    private Integer nam;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tuNgay;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate denNgay;

    private String lyDoChi;
}
