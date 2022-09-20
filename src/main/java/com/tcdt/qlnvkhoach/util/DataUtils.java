package com.tcdt.qlnvkhoach.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class DataUtils {

	public static LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		if (timestamp == null) return null;
		return timestamp.toLocalDateTime();
	}

	public static LocalDate convertToLocalDate(Timestamp timestamp) {
		if (timestamp == null) return null;
		return timestamp.toLocalDateTime().toLocalDate();
	}

	public static String toStringValue (Object obj) {
		return Optional.ofNullable(obj).map(Object::toString).orElse("");
	}
}
