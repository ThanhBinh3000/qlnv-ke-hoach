package com.tcdt.qlnvkhoach.controller.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam.DxDcKeHoachNamService;
import com.tcdt.qlnvkhoach.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/de-xuat-dieu-chinh-ke-hoach-nam")
@Slf4j
@Api(tags = "Quản lý đề xuất điều chỉnh kế hoạch năm")
public class DxDcKeHoachNamController extends BaseController {
    @Autowired
    private DxDcKeHoachNamService dxDcKeHoachNamService;

    @ApiOperation(value = "Tạo mới đề xuất điều chỉnh kế hoạch năm", response = List.class)
    @PostMapping
    public final ResponseEntity<Resp> create(@Valid @RequestBody DxDcKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.create(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi tiết đề xuất điều chỉnh kế hoạch năm", response = List.class)
    @GetMapping("/{id}")
    public final ResponseEntity<Resp> getDetail(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.detail(id));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
            log.error("error", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Sửa đề xuất điều chỉnh kế hoạch năm", response = List.class)
    @PutMapping()
    public final ResponseEntity<Resp> update(@Valid @RequestBody DxDcKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.update(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Gửi duyệt/Phê duyệt/Từ chối đề xuất điều chỉnh kế hoạch năm: 00 Mới Tạo, 01 Chờ Duyệt, 02 Đã duyệt, 03 Từ chối", response = List.class)
    @PutMapping("/status")
    public final ResponseEntity<Resp> updateStatus(@Valid @RequestBody StatusReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.updateStatus(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }


    @ApiOperation(value = "Tra cứu đề xuất điều chỉnh kế hoạch năm", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public final ResponseEntity<Resp> search(SearchDxDcKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.search(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa đề xuất điều chỉnh kế hoạch năm", response = List.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> delete(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.delete(id));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Lấy số lượng trước điều chỉnh", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/so-luong-truoc-dieu-chinh/{ctkhnId}")
    public final ResponseEntity<Resp> getSoLuongTruocDieuChinh(@PathVariable("ctkhnId") Long ctkhnId) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.getSoLuongTruocDieuChinh(ctkhnId));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Lấy số lượng trước điều chỉnh lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Export đề xuất điều chỉnh kế hoạch năm ra excel", response = List.class)
    @PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdToExcel(HttpServletResponse response, @RequestBody SearchDxDcKeHoachNamReq req) {

        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=de-xuat-dieu-chinh-ke-hoach-nam_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            dxDcKeHoachNamService.exportListToExcel(req, response);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }

    }

    @ApiOperation(value = "Delete multiple đề xuất điều chỉnh", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/multiple")
    public final ResponseEntity<Resp> deleteMultiple(DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(dxDcKeHoachNamService.deleteMultiple(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Delete multiple đề xuất điều chỉnh lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }
}
