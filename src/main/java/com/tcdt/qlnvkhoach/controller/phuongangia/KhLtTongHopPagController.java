package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopFilterReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/phuong-an-gia")
@Slf4j
@Api(tags = "Kế hoạch, đề xuất phương án giá")
public class KhLtTongHopPagController extends BaseController {
    @Autowired
    private KhLtPhuongAnGiaService phuongAnGiaService;
    @Autowired
    private KhLtTongHopPagService khLtTongHopPagService;
    @Autowired
    private KhLtPagService khLtPagService;

    @ApiOperation(value = "Tổng hợp đề xuất phương án giá", response = List.class)
    @PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> sumarryData(HttpServletRequest request,
                                            @Valid @RequestBody KhLtPagTongHopFilterReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.tongHopData(objReq));
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

    @ApiOperation(value = "Tạo mới tổng hợp đề xuất phương án giá", response = List.class)
    @PostMapping(value = PathConstants.URL_TONG_HOP + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> create(@Valid @RequestBody KhLtPagTongHopFilterReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.create(req));
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

    @ApiOperation(value = "Cập nhật thông tin tờ trình tổng hợp phương án giá", response = List.class)
    @PostMapping(value = PathConstants.URL_TO_TRINH + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateToTrinhToThPag(@Valid @RequestBody KhLtPagTongHopReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.updateToTrinhToThPag(req));
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

    @ApiOperation(value = "00-Dự thảo,18-Chờ duyệt LDV – TP,19-Từ chối – LĐ Vụ,20-Đã duyệt - LĐ Vụ", response = List.class)
    @PostMapping(value =  PathConstants.URL_TO_TRINH + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> approve(HttpServletRequest req, @Valid @RequestBody StatusReq stReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.approved(stReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu đề xuất phương án giá", response = List.class)
    @PostMapping(value = PathConstants.URL_TONG_HOP + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> searchKhLtPAGTh(@Valid @RequestBody KhLtPagTongHopSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Kết xuất danh sách đề xuất phương án giá", response = List.class)
    @PostMapping(value = PathConstants.URL_TONG_HOP + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListPagTHToExcel(@Valid @RequestBody KhLtPagTongHopSearchReq objReq, HttpServletResponse response) throws Exception {
        try {
            khLtTongHopPagService.exportPagTH(objReq, response);
        } catch (Exception e) {
            log.error("Kết xuất danh sách tổng hợp phương án giá: {}", e);
            final Map<String, Object> body = new HashMap<>();
            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            body.put("msg", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        }
    }

    @ApiOperation(value = "Chi tiết tổng hợp phương án giá", response = List.class)
    @GetMapping(value = PathConstants.URL_TONG_HOP + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detailPagTH(@ApiParam(value = "ID tổng hợp phương án giá", example = "1", required = true) @PathVariable("ids") String ids) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtTongHopPagService.detailPagTH(ids));
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

    @ApiOperation(value = "Xóa tổng hợp phương án giá", response = List.class)
    @PostMapping(value = PathConstants.URL_TONG_HOP + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deletePagTH(@RequestBody DeleteRecordReq idSearchReq) {
        Resp resp = new Resp();
        try {
            khLtTongHopPagService.delete(idSearchReq.getId());
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
