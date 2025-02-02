package com.tcdt.qlnvkhoach.controller.chitieukehoachnam;

import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.CtKhNamChiTietDviReq;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SoLuongTruocDieuChinhSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamExportService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamImportService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamServiceImpl;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdBtcTcdtService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.Contains;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.scanner.Constant;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
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

    @Autowired
    private KhQdBtcTcdtService khQdBtcTcdtService;

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
    public final ResponseEntity<Resp> searchQd(SearchChiTieuKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.searchQd(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu quyết định điều chỉnh chỉ tiêu kế hoạch năm", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/quyet-dinh-dieu-chinh")
    public final ResponseEntity<Resp> searchQdDc(SearchChiTieuKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.searchQdDc(req));
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
    public void exportListQdToExcel(HttpServletResponse response, @RequestBody SearchChiTieuKeHoachNamReq req) {

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
    public void exportListQdDcToExcel(HttpServletResponse response, @RequestBody SearchChiTieuKeHoachNamReq req) {

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
    public void downloadTemplateImportCtkhn(HttpServletResponse response) throws IOException {

        String folder = "excel";
        String filename = "chi_tieu_ke_hoach_nam_import.xlsx";
        InputStream inputStream = new ClassPathResource(folder + "/" + filename).getInputStream();

        try {
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" + filename;
            response.setHeader(headerKey, headerValue);

            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ApiOperation(value = "Lấy số lượng trước điều chỉnh", response = List.class)
    @PostMapping(value = "/quyet-dinh-dieu-chinh/so-luong-truoc-dieu-chinh", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> soLuongTruocDc(@RequestBody SoLuongTruocDieuChinhSearchReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.getSoLuongTruocDc(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Delete multiple chỉ tiêu kế hoạch năm", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/multiple")
    public final ResponseEntity<Resp> deleteMultipleQd(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.deleteMultiple(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Delete multiple chỉ tiêu kế hoạch năm lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Delete multiple quyết định điều chỉnh", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/quyet-dinh-dieu-chinh/delete/multiple")
    public final ResponseEntity<Resp> deleteMultipleQdDc(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.deleteMultiple(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Delete multiple quyết định điều chỉnh lỗi ", e);
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Count chỉ tiêu kế hoạch năm", response = List.class)
    @GetMapping("/count")
    public final ResponseEntity<Resp> countQd() {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.countCtkhn(ChiTieuKeHoachEnum.QD.getValue()));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Count điều chỉnh kế hoạch năm", response = List.class)
    @GetMapping("/quyet-dinh-dieu-chinh/count")
    public final ResponseEntity<Resp> countQdDc() {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.countCtkhn(ChiTieuKeHoachEnum.QD_DC.getValue()));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu chỉ tiêu kế hoạch năm và theo cục user login", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dxkh-lcnt/{namKh}")
    public final ResponseEntity<Resp> searchChiTieuDxKhLcnt(@PathVariable("namKh") Long namKh) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.getChiTieuDxKhLcnt(namKh));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
        }
        return ResponseEntity.ok(resp);

    }

    @ApiOperation(value = "Danh sách quyết định BTC giao TCDT", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/qd-btc-giao-tcdt/{namKh}")
    public final ResponseEntity<Resp> getQdBtcGiaoTcdt(@PathVariable("namKh") Integer namKh) {
        Resp resp = new Resp();
        try {
            resp.setData(khQdBtcTcdtService.getQdBtcTcdtByNam(namKh));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }


    @ApiOperation(value = "Danh sách chỉ tiêu kế hoạch năm Tổng cục giao các cục", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ct-kh-tc/{namKh}")
    public final ResponseEntity<Resp> getChiTieuKeHoach(@PathVariable("namKh") Long namKh) {
        Resp resp = new Resp();
        UserInfo userInfo = SecurityContextService.getUser();
        try {
            ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamService.getChiTieuDxKhLcntByDvi(namKh,userInfo.getDvql());
            if(chiTieuKeHoachNam != null && chiTieuKeHoachNam.getTrangThai().equals(Contains.BAN_HANH)){
                resp.setData(chiTieuKeHoachNam);
            }
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Danh sách chỉ tiêu kế hoạch phương án giá", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ct-kh-tc-pag/{namKh}")
    public final ResponseEntity<Resp> getCtkhPag(@PathVariable("namKh") Long namKh) {
        Resp resp = new Resp();
        UserInfo userInfo = SecurityContextService.getUser();
        try {
            ChiTieuKeHoachNam chiTieuKeHoachNam = chiTieuKeHoachNamService.getCtkhNamPag(namKh,userInfo.getDvql());
            if(chiTieuKeHoachNam != null && chiTieuKeHoachNam.getTrangThai().equals(Contains.BAN_HANH)){
                resp.setData(chiTieuKeHoachNam);
            }
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Lấy chi tiết số chỉ tiêu kế hoạch theo đơn vị", response = List.class)
    @PostMapping("/chi-tiet-kehoach-donvi")
    public final ResponseEntity<Resp> detailKhNamDvi(@Valid @RequestBody CtKhNamChiTietDviReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(chiTieuKeHoachNamService.getDetailKhByDonVi(req.getNamKh(),req.getMaDvi(),req.getLoaiVthh()));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
}
