package com.tcdt.qlnvkhoach.controller.khcnbq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.khcnbq.CnCtrinhNcuuReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.khcnbq.CnCtrinhNcuuSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.khcnbq.CnCtrinhNcuuService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cn-ctrinh-ncuu")
@Slf4j
@Api(tags = "Công nghệ, công trình, nghiên cứu")
public class CnCtrinhNcuuController extends BaseController {
    @Autowired
    private CnCtrinhNcuuService cnCtrinhNcuuService;


    @ApiOperation(value = "Tra cứu công nghệ, công trình, bảo quản", response = List.class)
    @PostMapping(value=  PathConstants.URL_CN_CT_NC + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> search(@Valid @RequestBody CnCtrinhNcuuSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(cnCtrinhNcuuService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tạo mới công nghệ công trình nghiên cứu", response = List.class)
    @PostMapping(value=  PathConstants.URL_CN_CT_NC + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> create(@Valid @RequestBody CnCtrinhNcuuReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(cnCtrinhNcuuService.create(req));
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



    @ApiOperation(value = "Sửa công nghệ công trình nghiên cứu", response = List.class)
    @PostMapping(value=   PathConstants.URL_CN_CT_NC + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> update(@Valid @RequestBody CnCtrinhNcuuReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(cnCtrinhNcuuService.update(req));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }


    @ApiOperation(value = "Xóa nhiều công nghệ công trình nghiên cứu", response = List.class)
    @PostMapping(value =  PathConstants.URL_CN_CT_NC +PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
        Resp resp = new Resp();
        try {
            resp.setData(cnCtrinhNcuuService.deleteMultiple(req.getIds()));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa công nghệ công trình nghiên cứu", response = List.class)
    @PostMapping(value=   PathConstants.URL_CN_CT_NC + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> delete(@RequestBody DeleteRecordReq idSearchReq) {
        Resp resp = new Resp();
        try {
            cnCtrinhNcuuService.delete(idSearchReq.getId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi công nghệ công trình nghiên cứu", response = List.class)
    @GetMapping(value=  PathConstants.URL_CN_CT_NC + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detail(@ApiParam(value = "ID bản ghi", example = "1", required = true) @PathVariable("id") String id) {
        Resp resp = new Resp();
        try {
            resp.setData(cnCtrinhNcuuService.detail(id));
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
    @PostMapping(value= PathConstants.URL_CN_CT_NC + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListDxPagToExcel(@Valid @RequestBody CnCtrinhNcuuSearchReq objReq, HttpServletResponse response) throws Exception{
        try {
            cnCtrinhNcuuService.exportCnCtrinhNcuu(objReq,response);
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


}
