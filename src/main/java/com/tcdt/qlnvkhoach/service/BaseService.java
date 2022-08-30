package com.tcdt.qlnvkhoach.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.util.Contains;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public abstract class BaseService {
    @Autowired
    private com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository QlnvDmDonviRepository;

    protected <T> void updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Contains.FORMAT_DATE_STR));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.updateValue(source, objectEdit);
    }


    public QlnvDmDonvi getDviByMa(String maDvi)  {
        // Call feign get dvql
        Iterable<QlnvDmDonvi> dvs = QlnvDmDonviRepository.findByTrangThai(Contains.HOAT_DONG);
        List<QlnvDmDonvi> lDvi = StreamSupport.stream(dvs.spliterator(), false)
                .collect(Collectors.toList());
        Map<String, QlnvDmDonvi> listDvi = lDvi.stream()
                .collect(Collectors.toMap(QlnvDmDonvi::getMaDvi, Function.identity()));
        QlnvDmDonvi dvi = listDvi.get(maDvi);
        return dvi;
    }
}
