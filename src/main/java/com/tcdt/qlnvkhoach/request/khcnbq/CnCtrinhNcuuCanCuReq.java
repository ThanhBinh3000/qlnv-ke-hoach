package com.tcdt.qlnvkhoach.request.khcnbq;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CnCtrinhNcuuCanCuReq {

	private String noiDung;

	private  FileDinhKemReq fileDinhKem;

	private Long cnCtNcId;

}
