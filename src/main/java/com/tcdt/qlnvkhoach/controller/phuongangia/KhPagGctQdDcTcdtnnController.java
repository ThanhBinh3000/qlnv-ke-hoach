package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagGctQdDcTcdtnnReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagGctQdTcdtnnReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagGctQdDcTcdtnnSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagGctQdTcdtnnSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.phuongangia.KhPagGctQdDcTcdtnnService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhPagGctQdTcdtnnService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/phuong-an-gia")
@Slf4j
@Api(tags = "Kế hoạch, đề xuất phương án giá")
public class KhPagGctQdDcTcdtnnController extends BaseController {

    @Autowired
    private KhPagGctQdDcTcdtnnService khPagGctQdDcTcdtnnService;

    @ApiOperation(value = "Giá cụ thể Tra cứu Quyết định điều chỉnh giá của TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> search(@Valid @RequestBody KhPagGctQdDcTcdtnnSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khPagGctQdDcTcdtnnService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Giá cụ thể Tạo mới Quyết định điều chỉnh giá của TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> create(@Valid @RequestBody KhPagGctQdDcTcdtnnReq  objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khPagGctQdDcTcdtnnService.create(objReq));
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

    @ApiOperation(value = "Giá cụ thể Cập nhật Quyết định điều chỉnh giá của TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> update(@Valid @RequestBody KhPagGctQdDcTcdtnnReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khPagGctQdDcTcdtnnService.update(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Giá cụ thể Chi tiết Quyết định điều chỉnh giá của TCDTNN", response = List.class)
    @GetMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detail(@ApiParam(value = "ID quyết định giá của BTC", example = "1", required = true) @PathVariable("ids") String ids) {
        Resp resp = new Resp();
        try {
            resp.setData(khPagGctQdDcTcdtnnService.detail(ids));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Giá cụ thể Xóa Quyết định điều chỉnh giá của TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> delete(@RequestBody DeleteRecordReq idReq) {
        Resp resp = new Resp();
        try {
            khPagGctQdDcTcdtnnService.delete(idReq.getId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Giá cụ thể Xóa danh sách Quyết định điều chỉnh giá của TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteMulti(@Valid @RequestBody DeleteRecordReq idReq) {
        Resp resp = new Resp();
        try {
            khPagGctQdDcTcdtnnService.deleteListId(idReq.getListId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Giá cụ thể Kết xuất danh sách quyết định điều chỉnh giá TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_KET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void export(@Valid @RequestBody KhPagGctQdDcTcdtnnSearchReq objReq, HttpServletResponse response) throws Exception {
        try {
            khPagGctQdDcTcdtnnService.export(objReq, response);
        } catch (Exception e) {
            final Map<String, Object> body = new HashMap<>();
            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            body.put("msg", e.getMessage());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        }
    }

    @ApiOperation(value = "Giá cụ thể ban hành quyết định điều chỉnh giá TCDTNN", response = List.class)
    @PostMapping(value =PathConstants.URL_QD_DC_GIA_TCDTNN + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public final ResponseEntity<Resp> banHanh(@Valid @RequestBody StatusReq objReq) throws Exception {
        Resp resp = new Resp();
        try {
            resp.setData(khPagGctQdDcTcdtnnService.approve(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        }  catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }


}