package com.tcdt.qlnvkhoach.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
	public static Long safeToLong(Object obj1) {
		return safeToLong(obj1, 0L);
	}

	public static Long safeToLong(Object obj1, Long defaultValue) {
		Long result = defaultValue;
		if (obj1 != null) {
			if (obj1 instanceof BigDecimal) {
				return ((BigDecimal) obj1).longValue();
			}
			if (obj1 instanceof BigInteger) {
				return ((BigInteger) obj1).longValue();
			}
			try {
				result = Long.parseLong(obj1.toString());
			} catch (Exception ignored) {
			}
		}

		return result;
	}

	public static int safeToInt(Object obj1) {
		return safeToInt(obj1, 0);
	}

	public static int safeToInt(Object obj1, int defaultValue) {
		int result = defaultValue;
		if (obj1 != null) {
			try {
				result = Integer.parseInt(obj1.toString());
			} catch (Exception ignored) {
			}
		}

		return result;
	}

	public static boolean isNullOrEmpty(final Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNullOrEmpty(String st) {
		return st == null || st.isEmpty() || st.equals("null");
	}

	public static boolean isNull(String s) {
		return s == null || s.equals("null");
	}

	public static boolean isEmpty(String s) {
		return s.equals("");
	}

	public static boolean isNullObject(Object obj1) {
		if (obj1 == null) {
			return true;
		}
		if (obj1 instanceof String) {
			return isNullOrEmpty(obj1.toString());
		}
		return false;
	}

	public static String safeToString(Object obj1, String defaultValue) {
		if (obj1 == null) {
			return defaultValue;
		}

		return obj1.toString();
	}

	public static boolean isNotBlank(Object st) {
		return !DataUtils.isNullObject(st);
	}

	public static String trim(String st) {
		return st.trim();
	}

	public static String safeToString(Object obj1) {
		return safeToString(obj1, "");
	}

	public static Double safeToDouble(Object obj1, Double defaultValue) {
		Double result = defaultValue;
		if (obj1 != null) {
			try {
				result = Double.parseDouble(obj1.toString());
			} catch (Exception ignored) {
			}
		}

		return result;
	}

	public static Double safeToDouble(Object obj1) {
		return safeToDouble(obj1, 0.0);
	}

	public static void copyProperties(Object src, Object target,String... ignore) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src,ignore));
	}
	public static String[] getNullPropertyNames(Object source,String... ignore) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		Collections.addAll(emptyNames, ignore);
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
