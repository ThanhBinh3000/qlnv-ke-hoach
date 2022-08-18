package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPagService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPhuongAnGiaService;
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
@RequestMapping(value = "/kh-lt-pag")
@Slf4j
@Api(tags = "Kế hoạch, đề xuất phương án giá")
public class KhLtPhuongAnGiaController extends BaseController {
    @Autowired
    private KhLtPhuongAnGiaService phuongAnGiaService;

    @Autowired
    private KhLtPagService khLtPagService;


    @ApiOperation(value = "Tra cứu đề xuất phương án giá", response = List.class)
    @PostMapping(value= PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> searchKhLtPAG(@Valid @RequestBody KhLtPhuongAnGiaSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtPagService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tạo mới đề xuất phương án giá", response = List.class)
    @PostMapping(value= PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> create(@Valid @RequestBody KhLtPhuongAnGiaReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtPagService.create(req));
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
    @PostMapping(value= PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updatePag(@Valid @RequestBody KhLtPhuongAnGiaReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtPagService.update(req));
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
    @PostMapping(value =PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG +PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(khLtPagService.deleteMultiple(req.getIds()));
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
    @PostMapping(value=PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH +  PathConstants.URL_DX_PAG + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deletePag(@RequestBody DeleteRecordReq idSearchReq) {
        Resp resp = new Resp();
        try {
            khLtPagService.delete(idSearchReq.getId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi tiết đề xuất phương án giá", response = List.class)
    @GetMapping(value=PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH +  PathConstants.URL_DX_PAG + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detailDxPag(@ApiParam(value = "ID đề xuất phương án giá", example = "1", required = true) @PathVariable("ids") String ids) {
        Resp resp = new Resp();
        try {
            KhPhuongAnGia khQdBtcBoNganh=khLtPagService.detailDxPag(ids);
            resp.setData(khQdBtcBoNganh);
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
    @PostMapping(value=PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH +  PathConstants.URL_DX_PAG + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListDxPagToExcel(@Valid @RequestBody KhLtPhuongAnGiaSearchReq objReq, HttpServletResponse response) throws Exception{
        try {
            khLtPagService.exportDxPag(objReq,response);
        } catch (Exception e) {

            log.error("Kết xuất danh sách đề xuất phương án giá: {}", e);
            final Map<String, Object> body = new HashMap<>();
            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            body.put("msg", e.getMessage());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        }

    }
}
