package com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDxDcKeHoachNamReq extends BaseRequest {
	private String soVanBan;
}