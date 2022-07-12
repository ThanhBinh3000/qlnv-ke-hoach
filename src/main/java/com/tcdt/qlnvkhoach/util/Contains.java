package com.tcdt.qlnvkhoach.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Contains {
    public static final String FORMAT_DATE_STR = "yyyy-MM-dd";
    public static String convertDateToString(Date date) throws Exception {
        if (Objects.isNull(date)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(Contains.FORMAT_DATE_STR);
        return df.format(date);
    }
}
