package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPhuongAnGiaService;
import com.tcdt.qlnvkhoach.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/kh-lt-phuong-an-gia")
@Slf4j
@Api(tags = "Kế hoạch, đề xuất phương án giá")
public class KhLtPhuongAnGiaController extends BaseController {
    @Autowired
    private KhLtPhuongAnGiaService phuongAnGiaService;


    @ApiOperation(value = "Tạo mới đề xuất phương án giá", response = List.class)
    @PostMapping
    public final ResponseEntity<Resp> create(@Valid @RequestBody KhLtPhuongAnGiaReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(phuongAnGiaService.create(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }



    @ApiOperation(value = "Sửa đề xuất phương án giá", response = List.class)
    @PutMapping()
    public final ResponseEntity<Resp> updateQd(@Valid @RequestBody KhLtPhuongAnGiaReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(phuongAnGiaService.update(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }


    @ApiOperation(value = "Xóa đề xuất phương án giá", response = List.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(phuongAnGiaService.deleteMultiple(req.getIds()));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
}
