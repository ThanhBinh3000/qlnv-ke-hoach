package com.tcdt.qlnvkhoach.controller.phitonghoptheodoi;


import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.enums.EnumResponse;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiRequest;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.response.phitonghoptheodoi.PhiTongHopTheoDoiResponse;
import com.tcdt.qlnvkhoach.service.phitonghoptheodoi.PhiTongHopTheoDoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/phi-tong-hop-theo-doi")
@Slf4j
@Api(tags = "Tổng hợp theo dõi cấp phí ")
@RequiredArgsConstructor
public class PhiTongHopTheoDoiController extends BaseController {
    private final PhiTongHopTheoDoiService service;

    @ApiOperation(value = "Tạo mới Tổng hợp theo dõi cấp phí ", response = List.class)
    @PostMapping()
    public ResponseEntity<Resp> create(@RequestBody PhiTongHopTheoDoiRequest req) {
        Resp resp = new Resp();
        try {
            PhiTongHopTheoDoiResponse res = service.create(req);
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Create error", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Sửa Tổng hợp theo dõi cấp phí ", response = List.class)
    @PutMapping()
    public ResponseEntity<Resp> update(@Valid @RequestBody PhiTongHopTheoDoiRequest req) {
        Resp resp = new Resp();
        try {
            PhiTongHopTheoDoiResponse res = service.update(req);
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Update error", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xoá Tổng hợp theo dõi cấp phí ", response = Boolean.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> delete(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            Boolean res = service.delete(id);
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xoá danh sách Tổng hợp theo dõi cấp phí ", response = Boolean.class)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/multiple")
    public ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            Boolean res = service.deleteMultiple(req.getIds());
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Search Tổng hợp theo dõi cấp phí ", response = Page.class)
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> search(@Valid @RequestBody PhiTongHopTheoDoiSearchRequest req) {
        Resp resp = new Resp();
        try {
            Page<PhiTongHopTheoDoiResponse> res = service.search(req);
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Search Error", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Thông tin chi tiết Tổng hợp theo dõi cấp phí ", response = List.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> detail(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            PhiTongHopTheoDoiResponse res = service.detail(id);
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Update trạng thái Tổng hợp theo dõi cấp phí ", response = List.class)
    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> updateTrangThai(@Valid @RequestBody StatusReq req) {
        Resp resp = new Resp();
        try {
            Boolean res = service.updateStatus(req);
            resp.setData(res);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Export danh sách Tổng hợp theo dõi cấp phí ", response = List.class)
    @PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportToExcel(HttpServletResponse response, @RequestBody PhiTongHopTheoDoiSearchRequest req) {
        try {
            service.exportToExcel(req, response);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }
    }

}



