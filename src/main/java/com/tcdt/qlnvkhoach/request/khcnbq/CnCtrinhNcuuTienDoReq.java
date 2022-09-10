package com.tcdt.qlnvkhoach.request.khcnbq;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CnCtrinhNcuuTienDoReq {

	private Long id;

	private String noiDung;

	private String sanPham;

	private LocalDate tuNgay;

	private LocalDate denNgay;

	private String nguoiTh;

	private String trangThai;

	private Long cnCtNcId;
}
