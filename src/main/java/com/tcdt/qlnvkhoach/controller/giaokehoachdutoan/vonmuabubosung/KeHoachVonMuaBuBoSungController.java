package com.tcdt.qlnvkhoach.controller.giaokehoachdutoan.vonmuabubosung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabubosung.KhMuaQdUbtvqhReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabubosung.KhMuaQdUbtvqhSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.giaokehoachvonmuabubosung.KhMuaQdUbtvqhService;
import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqh;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = PathConstants.URL_GIAO_KE_HOACH_MUA_BU_BO_SUNG+PathConstants.URL_QUYET_DINH)
@Slf4j
@Api(tags = "Giao kế hoạch vốn mua bù bổ sung, quyết định (/giao-ke-hoach-mua-bu-bo-sung/quyet-dinh)")
@RequiredArgsConstructor
public class KeHoachVonMuaBuBoSungController {

    @Autowired
    KhMuaQdUbtvqhService khMuaQdUbtvqhService;

    @ApiOperation(value = "Tra cứu nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> searchUbtvqh(@Valid @RequestBody KhMuaQdUbtvqhSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khMuaQdUbtvqhService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tạo mới nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> createUbtvqh(@Valid @RequestBody KhMuaQdUbtvqhReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhMuaQdUbtvqh createCheck = khMuaQdUbtvqhService.save(objReq);
            resp.setData(createCheck);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Cập nhật nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateUbtvqh(@Valid @RequestBody KhMuaQdUbtvqhReq objReq ,HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhMuaQdUbtvqh createCheck=khMuaQdUbtvqhService.update(objReq);
            resp.setData(createCheck);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Chi tiết nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @GetMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detailUbtvqh(@ApiParam(value = "ID quyết định nghị quyết của Ủy ban thường vụ Quốc hội", example = "1", required = true) @PathVariable("ids") String ids) {
        Resp resp = new Resp();
        try {
            KhMuaQdUbtvqh khMuaQdUbtvqh=khMuaQdUbtvqhService.detail(ids);
            resp.setData(khMuaQdUbtvqh);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteUbtvqh(@RequestBody DeleteRecordReq idSearchReq) {
        Resp resp = new Resp();
        try {
            khMuaQdUbtvqhService.delete(idSearchReq.getId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa danh sách nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteMulti(@Valid @RequestBody KhMuaQdUbtvqhSearchReq objReq) {
        Resp resp = new Resp();
        try {
            khMuaQdUbtvqhService.deleteListId(objReq.getIdList());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Kết xuất danh sách nghị quyết của Ủy ban thường vụ Quốc hội", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdTtcpToExcel(@Valid @RequestBody KhMuaQdUbtvqhSearchReq objReq,HttpServletResponse response) throws Exception{

        try {
            khMuaQdUbtvqhService.export(objReq,response);
        } catch (Exception e) {

            log.error("Kết xuất danh sách đề xuất xuất danh sách quyết định của Thủ Tướng trace: {}", e);
            final Map<String, Object> body = new HashMap<>();
            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            body.put("msg", e.getMessage());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        }

    }

    @ApiOperation(value = "Tạo mới-00/Ban hành-11 nghị quyết của Ủy ban thường vụ Quốc hội ", response = List.class)
    @PostMapping(value=PathConstants.URL_UB_TV_QH + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateStatusTtcp(@Valid @RequestBody StatusReq statusReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
           resp.setData(khMuaQdUbtvqhService.approve(statusReq));
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


