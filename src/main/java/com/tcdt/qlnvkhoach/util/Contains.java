package com.tcdt.qlnvkhoach.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public static<T> void  updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Contains.FORMAT_DATE_STR));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.updateValue(source, objectEdit);
    }
}
