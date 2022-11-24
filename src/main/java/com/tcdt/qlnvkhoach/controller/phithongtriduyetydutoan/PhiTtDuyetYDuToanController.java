package com.tcdt.qlnvkhoach.controller.phithongtriduyetydutoan;


import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.enums.EnumResponse;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan.PhiTtDuyetYDuToanRequest;
import com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan.PhiTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.response.phithongtriduyetydutoan.PhiTtDuyetYDuToanResponse;
import com.tcdt.qlnvkhoach.service.phithongtriduyetydutoan.PhiTtDuyetYDuToanService;
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
@RequestMapping(value = "/phi-thong-tri-duyet-y-du-toan")
@Slf4j
@Api(tags = "Phí Thông tri duyệt y dự toán")
@RequiredArgsConstructor
public class PhiTtDuyetYDuToanController extends BaseController {
    private final PhiTtDuyetYDuToanService service;

    @ApiOperation(value = "Tạo mới Phí Thông tri duyệt y dự toán", response = List.class)
    @PostMapping()
    public ResponseEntity<Resp> create(@RequestBody PhiTtDuyetYDuToanRequest req) {
        Resp resp = new Resp();
        try {
            PhiTtDuyetYDuToanResponse res = service.create(req);
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

    @ApiOperation(value = "Sửa Phí Thông tri duyệt y dự toán", response = List.class)
    @PutMapping()
    public ResponseEntity<Resp> update(@Valid @RequestBody PhiTtDuyetYDuToanRequest req) {
        Resp resp = new Resp();
        try {
            PhiTtDuyetYDuToanResponse res = service.update(req);
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

    @ApiOperation(value = "Xoá Phí Thông tri duyệt y dự toán", response = Boolean.class)
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

    @ApiOperation(value = "Xoá danh sách Phí Thông tri duyệt y dự toán", response = Boolean.class)
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

    @ApiOperation(value = "Search Phí Thông tri duyệt y dự toán", response = Page.class)
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> search(@Valid @RequestBody PhiTtDuyetYDuToanSearchRequest req) {
        Resp resp = new Resp();
        try {
            Page<PhiTtDuyetYDuToanResponse> res = service.search(req);
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

    @ApiOperation(value = "Thông tin chi tiết Phí Thông tri duyệt y dự toán", response = List.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> detail(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            PhiTtDuyetYDuToanResponse res = service.detail(id);
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

    @ApiOperation(value = "Update trạng thái Phí Thông tri duyệt y dự toán", response = List.class)
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

    @ApiOperation(value = "Export danh sách Phí Thông tri duyệt y dự toán", response = List.class)
    @PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportToExcel(HttpServletResponse response, @RequestBody PhiTtDuyetYDuToanSearchRequest req) {
        try {
            service.exportToExcel(req, response);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }
    }

}


