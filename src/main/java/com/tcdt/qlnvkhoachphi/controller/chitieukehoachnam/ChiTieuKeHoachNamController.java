package com.tcdt.qlnvkhoachphi.controller.chitieukehoachnam;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamExportService;
import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamImportService;
import com.tcdt.qlnvkhoachphi.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.FileResourcesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/chi-tieu-ke-hoach-nam")
@Slf4j
@Api(tags = "Quản lý chỉ tiêu kế hoạch năm")
public class ChiTieuKeHoachNamController extends BaseController {
    @Autowired
    private ChiTieuKeHoachNamService chiTieuKeHoachNamService;

    @Autowired
    private ChiTieuKeHoachNamImportService importSv;

    @Autowired
    private ChiTieuKeHoachNamExportService chiTieuKeHoachNamExportSv;

    @ApiOperation(value = "Tạo mới Chỉ tiêu kế hoạch năm", response = List.class)
    @PostMapping
    public final ResponseEntity<Resp> create(@Valid @RequestBody ChiTieuKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.createQd(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi tiết chỉ tiêu kế hoạch năm", response = List.class)
    @GetMapping("/{id}")
    public final ResponseEntity<Resp> getDetailQd(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.detailQd(id));
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

    @ApiOperation(value = "Sửa chỉ tiêu kế hoạch năm", response = List.class)
    @PutMapping()
    public final ResponseEntity<Resp> updateQd(@Valid @RequestBody ChiTieuKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.updateQd(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Import file kế hoạch lương thực, muối, vật tư", response = List.class)
    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> importKh(@RequestPart("file") MultipartFile file) {
        Resp resp = new Resp();
        try {
            resp.setData(importSv.importKeHoach(file));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Gửi duyệt/Phê duyệt/Từ chối chỉ tiêu kế hoạch năm: 00 Mới Tạo, 01 Chờ Duyệt, 02 Đã duyệt, 03 Từ chối", response = List.class)
    @PutMapping("/status")
    public final ResponseEntity<Resp> updateStatusQd(@Valid @RequestBody StatusReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.updateStatusQd(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Gửi duyệt/Phê duyệt/Từ chối quyết định điều chỉnh chỉ tiêu kế hoạch năm: 00 Mới Tạo, 01 Chờ Duyệt, 02 Đã duyệt, 03 Từ chối", response = List.class)
    @PutMapping("/quyet-dinh-dieu-chinh/status")
    public final ResponseEntity<Resp> updateStatusQdDc(@Valid @RequestBody StatusReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.updateStatusQdDc(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tạo mới quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
    @PostMapping("/quyet-dinh-dieu-chinh")
    public final ResponseEntity<Resp> createQdDc(@Valid @RequestBody QdDcChiTieuKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.createQdDc(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Sửa quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
    @PutMapping("/quyet-dinh-dieu-chinh")
    public final ResponseEntity<Resp> updateQdDc(@Valid @RequestBody QdDcChiTieuKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.updateQdDc(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Import file điều chỉnh kế hoạch lương thực, muối, vật tư", response = List.class)
    @PostMapping(value = "/quyet-dinh-dieu-chinh/import", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> importQdDc(@RequestPart("file") MultipartFile file) {
        Resp resp = new Resp();
        try {
            resp.setData(importSv.importKeHoach(file));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi tiết quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
    @GetMapping("/quyet-dinh-dieu-chinh/{id}")
    public final ResponseEntity<Resp> getDetailQdDc(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.detailQdDc(id));
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

    @ApiOperation(value = "Tra cứu chỉ tiêu kế hoạch năm", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public final ResponseEntity<Resp> searchQd(SearchChiTieuKeHoachNamReq req, Pageable pageable) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.searchQd(req, pageable));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/quyet-dinh-dieu-chinh")
    public final ResponseEntity<Resp> searchQdDc(SearchChiTieuKeHoachNamReq req, Pageable pageable) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.searchQdDc(req, pageable));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Export chi tiết chỉ tiêu kế hoạch năm ra excel", response = List.class)
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam(required = false) List<String> type,
                              @RequestParam Long id) {

        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=chi-tieu-ke-hoach-nam_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            chiTieuKeHoachNamExportSv.exportToExcel(response, type, id);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }

    }

    @ApiOperation(value = "Export Chỉ tiêu kế hoạch năm ra excel", response = List.class)
    @PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdToExcel(HttpServletResponse response, SearchChiTieuKeHoachNamReq req) {

        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=chi-tieu-ke-hoach-nam_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            chiTieuKeHoachNamExportSv.exportListQdToExcel(response, req);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }

    }

    @ApiOperation(value = "Export quyết định điều chỉnh chỉ tiêu kế hoạch năm ra excel", response = List.class)
    @PostMapping(value = "/quyet-dinh-dieu-chinh/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdDcToExcel(HttpServletResponse response, SearchChiTieuKeHoachNamReq req) {

        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=quyent_dinh_dieu_chinh_chi-tieu-ke-hoach-nam_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            chiTieuKeHoachNamExportSv.exportListQdDcToExcel(response, req);
        } catch (Exception e) {
            log.error("Error can not export", e);
        }

    }

    @ApiOperation(value = "Xóa chỉ tiêu kế hoạch năm lương thực, muối, vật tư", response = List.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> deleteQd(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.deleteQd(id));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa quyết định điều chỉnh kế hoạch lương thực, muối, vật tư", response = List.class)
    @DeleteMapping(value = "/quyet-dinh-dieu-chinh/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> deleteQdDc(@PathVariable("id") Long id) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.deleteQdDc(id));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/download/import-template")
    @ApiOperation(value = "Download template import chỉ tiêu kế hoạch năm", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> downloadTemplateImportCtkhn(HttpServletResponse response){

        InputStreamResource inputStreamResource = null;
        File file = null;
        try {
            String filename = "excel/chi_tieu_ke_hoach_nam_import.xlsx";
            file = FileResourcesUtils.getFileFromResource(filename);
            inputStreamResource = new InputStreamResource(new FileInputStream(file));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentLength(file.length()) //
                .body(inputStreamResource);
    }
}
