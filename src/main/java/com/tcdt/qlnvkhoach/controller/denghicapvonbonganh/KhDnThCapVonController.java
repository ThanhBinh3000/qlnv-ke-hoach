package com.tcdt.qlnvkhoach.controller.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.enums.EnumResponse;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonRequest;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.denghicapvonbonganh.KhDnThCapVonService;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = PathConstants.TONG_HOP_DE_NGHI_CAP_VON)
@Slf4j
@Api(tags = "Tổng hợp đề nghị cấp vốn DTQG")
@RequiredArgsConstructor
public class KhDnThCapVonController {

    @Autowired
    private KhDnThCapVonService service;

    @ApiOperation(value = "Tạo mới Quản lý Tổng hợp đề nghị cấp vốn DTQG", response = List.class)
    @PostMapping
    public ResponseEntity<Resp> insert(@Valid @RequestBody KhDnThCapVonRequest request) {
        Resp resp = new Resp();
        try {
            resp.setData(service.create(request));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Tạo mới Quản lý Tổng hợp đề nghị cấp vốn DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Sửa Quản lý Tổng hợp đề nghị cấp vốn DTQG", response = List.class)
    @PutMapping
    public ResponseEntity<Resp> update(@Valid @RequestBody KhDnThCapVonRequest request) {
        Resp resp = new Resp();
        try {
            resp.setData(service.update(request));
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error("Sửa Quản lý Tổng hợp đề nghị cấp vốn DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa Quản lý Tổng hợp đề nghị cấp vốn DTQG", response = List.class)
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
            log.error("Xóa Quản lý Tổng hợp đề nghị cấp vốn DTQG lỗi: {}", e);
        }
        return ResponseEntity.ok(resp);
    }
}
