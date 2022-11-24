package com.tcdt.qlnvkhoach.response.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhQdKhlcntHDRRes {
	private Long id;
	private String soQd;
}
