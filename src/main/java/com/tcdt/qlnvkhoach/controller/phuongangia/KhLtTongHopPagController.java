package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPagService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPhuongAnGiaService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtTongHopPagService;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kh-lt-pag")
@Slf4j
@Api(tags = "Kế hoạch, đề xuất phương án giá")
public class KhLtTongHopPagController extends BaseController {
    @Autowired
    private KhLtPhuongAnGiaService phuongAnGiaService;
    @Autowired
    private KhLtTongHopPagService khLtTongHopPagService;
    @Autowired
    private KhLtPagService khLtPagService;



    @ApiOperation(value = "Tổng hợp đề xuất kế hoạch lựa chọn nhà thầu Gạo", response = List.class)
    @PostMapping(value= PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> sumarryData(HttpServletRequest request,
                                                    @Valid @RequestBody KhLtPagTongHopReq objReq) {
       System.out.println("HAAAAAAAAAAAAAAAAAAAAA");
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.tongHopData(objReq, request));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Tổng hợp phương án giá thóc gạo muối: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

}
