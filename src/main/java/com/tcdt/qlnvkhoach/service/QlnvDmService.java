package com.tcdt.qlnvkhoach.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmVattuRepository;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.Request;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmKhoachVonPhiRepository;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.client.QlnvDmClient;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoach.util.Constants;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class QlnvDmService {
    @Autowired
    private QlnvDmClient qlnvDmClient;
    @Autowired
    private Gson gson;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QlnvDmDonviRepository qlnvDmDonviRepository;

    @Autowired
    private QlnvDmVattuRepository qlnvDmVattuRepository;

    @Autowired
    private QlnvDmKhoachVonPhiRepository qlnvDmKhoachVonPhiRepository;

    @Cacheable(value = "dmDonviCache", key = "#id", unless = "#result==null")
    public QlnvDmDonvi getDonViById(Long id) {
        Optional<QlnvDmDonvi> qOptional = qlnvDmDonviRepository.findById(id);
        return qOptional.get();
    }

    @Cacheable(value = "dmKhoachVonPhiCache", key = "#id", unless = "#result==null")
    public QlnvDmKhoachVonPhi getDmKhoachVonPhiById(Long id) throws Exception {
        Optional<QlnvDmKhoachVonPhi> qOptional = qlnvDmKhoachVonPhiRepository.findById(id);
        return qOptional.get();
    }

    @Deprecated
    @Cacheable(value = "dmDonviCache", key = "#maDvi", unless = "#result==null")
    public QlnvDmDonvi getDonViById(String maDvi) throws Exception {
        QlnvDmDonvi qlnvDmDonvi = null;
        try {
            Resp resp = qlnvDmClient.getDmDviByIdDetail(maDvi);
            log.info("Kết quả danh mục client: {}", gson.toJson(resp));
            if (resp.getStatusCode() != Constants.RESP_SUCC) {
                throw new NotFoundException(resp.getMsg());
            }
            qlnvDmDonvi = modelMapper.map(resp.getData(), QlnvDmDonvi.class);
        } catch (Exception e) {
            log.error("Không thể lấy thông tin danh mục đơn vị", e);
            throw e;
        }
        return qlnvDmDonvi;
    }

    @Deprecated
    @Cacheable(value = "dmKhoachVonPhiCache", key = "#maDmKhoachVonPhi", unless = "#result==null")
    public QlnvDmKhoachVonPhi getDmKhoachVonPhiById(String maDmKhoachVonPhi) throws Exception {
        QlnvDmKhoachVonPhi qlnvDmVonPhi = null;
        try {
            Resp resp = qlnvDmClient.getDmKhoachVphiByIdDetail(maDmKhoachVonPhi);
            log.info("Kết quả danh mục client: {}", gson.toJson(resp));
            if (resp.getStatusCode() != Constants.RESP_SUCC) {
                throw new NotFoundException(resp.getMsg());
            }
            if (resp == null || resp.getData() == null) {
                return null;
            }
            qlnvDmVonPhi = modelMapper.map(resp.getData(), QlnvDmKhoachVonPhi.class);
        } catch (Exception e) {
            log.error("Không thể lấy thông tin danh mục kế hoạch phí", e);
            throw e;
        }
        return qlnvDmVonPhi;
    }

    public Map<String, QlnvDmVattu> getMapVatTu(Collection<String> maVatTus) {
        if (CollectionUtils.isEmpty(maVatTus))
            return Collections.emptyMap();
        return qlnvDmVattuRepository.findByMaIn(maVatTus).stream()
                .collect(Collectors.toMap(QlnvDmVattu::getMa, Function.identity()));
    }

    public Map<String, QlnvDmDonvi> getMapDonVi(Collection<String> maDvis) {
        if (CollectionUtils.isEmpty(maDvis))
            return Collections.emptyMap();

        return qlnvDmDonviRepository.findByMaDviIn(maDvis).stream()
                .collect(Collectors.toMap(QlnvDmDonvi::getMaDvi, Function.identity()));
    }

    public Map<String, String> getListDanhMucDonVi(String capDvi) {
        ResponseEntity<String> response = qlnvDmClient.getAllDanhMucDonVi(capDvi);
        String str = Request.getAttrFromJson(response.getBody(), "data");
        HashMap<String, String> data = new HashMap<String, String>();
        List<Map<String, Object>> retMap = new Gson().fromJson(str, new TypeToken<List<HashMap<String, Object>>>() {
        }.getType());
        for (Map<String, Object> map : retMap) {
            data.put(String.valueOf(map.get("maDvi")), String.valueOf(map.get("tenDvi")));
        }
        return data;
    }

    public Map<String, String> getListDanhMucHangHoa() {
        ResponseEntity<String> response = qlnvDmClient.getDanhMucHangHoa();
        String str = Request.getAttrFromJson(response.getBody(), "data");
        HashMap<String, String> data = new HashMap<String, String>();
        List<Map<String, Object>> retMap = new Gson().fromJson(str, new TypeToken<List<HashMap<String, Object>>>() {
        }.getType());
        for (Map<String, Object> map : retMap) {
            data.put(String.valueOf(map.get("ma")), String.valueOf(map.get("ten")));
        }
        return data;
    }

    public Map<String, String> getListDanhMucChung(String loai) {
        ResponseEntity<String> response = qlnvDmClient.getDanhMucChung(loai);
        String str = Request.getAttrFromJson(response.getBody(), "data");
        HashMap<String, String> data = new HashMap<String, String>();
        List<Map<String, Object>> retMap = new Gson().fromJson(str, new TypeToken<List<HashMap<String, Object>>>() {
        }.getType());
        for (Map<String, Object> map : retMap) {
            data.put(String.valueOf(map.get("ma")), String.valueOf(map.get("giaTri")));
        }
        return data;
    }

    public QlnvDmDonvi getDonViByMa(String maDvi) {
        return qlnvDmDonviRepository.findByMaDvi(maDvi);
    }


    public String getTieuChuanCluongByMaLoaiVthh(String maLoai) {
        String tentieuChuanCluong = null;
        try {
            ResponseEntity<String> response = qlnvDmClient.getTchuanCluong(maLoai);
            String str = Request.getAttrFromJson(response.getBody(), "data");
            if (str != null && !str.equals("") && !str.equals("null")) {
                JSONObject object = new JSONObject(str);
                if (object.has("tenQchuan")) {
                    tentieuChuanCluong = object.getString("tenQchuan");
                }
            }
        } catch (Exception e) {
            return tentieuChuanCluong;
        }
        return tentieuChuanCluong;
    }
}
