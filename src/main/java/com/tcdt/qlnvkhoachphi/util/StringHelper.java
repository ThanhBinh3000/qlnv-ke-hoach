package com.tcdt.qlnvkhoachphi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static String extract(String source, String regex) {
        String result = null;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        if (m.find()) {
            result = m.group(1);
            result = result.replace("\u00a0", " ").trim();
        }
        return result;
    }
}
