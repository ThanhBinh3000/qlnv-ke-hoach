package com.tcdt.qlnvkhoachphi.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataUtils {

	public static LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		if (timestamp == null) return null;
		return timestamp.toLocalDateTime();
	}

	public static LocalDate convertToLocalDate(Timestamp timestamp) {
		if (timestamp == null) return null;
		return timestamp.toLocalDateTime().toLocalDate();
	}
}
