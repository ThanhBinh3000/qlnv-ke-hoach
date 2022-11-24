package com.tcdt.qlnvkhoach.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RequestInfo {
	Long userId;
	String userName;
	LocalDate date;
	Long donViId;
	String tenDonVi;
}
