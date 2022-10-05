package com.tcdt.qlnvkhoach.controller.denghicapphibonganh;

import com.tcdt.qlnvkhoach.enums.EnumResponse;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiRequest;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiSearchRequest;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.denghicapphibonganh.KhDnThCapPhiService;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = PathConstants.TONG_HOP_DE_NGHI_CAP_PHI)
@Slf4j
@Api(tags = "Tổng hợp đề nghị cấp phí DTQG")
@RequiredArgsConstructor
public class KhDnThCapPhiController {

    @Autowired
    private KhDnThCapPhiService service;

    @ApiOperation(value = "Tạo mới Quản lý Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @PostMapping
    public ResponseEntity<Resp> insert(@Valid @RequestBody KhDnThCapPhiRequest request) {
        Resp resp = new Resp();
        try {
            resp.setData(service.create(request));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Tạo mới Quản lý Tổng hợp đề nghị cấp phí DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Sửa Quản lý Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @PutMapping
    public ResponseEntity<Resp> update(@Valid @RequestBody KhDnThCapPhiRequest request) {
        Resp resp = new Resp();
        try {
            resp.setData(service.update(request));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Sửa Quản lý Tổng hợp đề nghị cấp phí DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa Quản lý Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> delete(@PathVariable Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(service.delete(id));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Xóa Quản lý Tổng hợp đề nghị cấp phí DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi tiết Quản lý Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @GetMapping("/{id}")
    public ResponseEntity<Resp> detail(@PathVariable Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(service.detail(id));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Chi tiết Quản lý Tổng hợp đề nghị cấp phí DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Phê duyệt/ từ chối Quản lý Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @PutMapping("/status")
    public ResponseEntity<Resp> updateStatus(@Valid @RequestBody StatusReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(service.updateStatusQd(req));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Phê duyệt/ từ chối Quản lý Tổng hợp đề nghị cấp phí DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu Quản lý Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @GetMapping()
    public ResponseEntity<Resp> search(KhDnThCapPhiSearchRequest req) {
        Resp resp = new Resp();
        try {
            resp.setData(service.search(req));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Tra cứu Quản lý Tổng hợp đề nghị cấp phí DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Delete multiple Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/multiple")
    public final ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(service.deleteMultiple(req));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            resp.setMsg(e.getMessage());
            log.error("Delete multiple Tổng hợp đề nghị cấp phí DTQG lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Export Tổng hợp đề nghị cấp phí DTQG", response = List.class)
    @PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdDcToExcel(HttpServletResponse response, @RequestBody KhDnThCapPhiSearchRequest req) {

        try {
            service.exportToExcel(req, response);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }
    }
}
