package com.tcdt.qlnvkhoach.controller.giaokehoachdutoan.vonmuabubosung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabubosung.KhMuaQdUbtvqhReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabubosung.KhMuaQdUbtvqhSearchReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc.KhMuaQdBtcReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdBtcSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.giaokehoachvonmuabubosung.KhMuaQdUbtvqhService;
import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqh;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdBtcBoNganhService;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdBtcTcdtService;
import com.tcdt.qlnvkhoach.service.giaokehoachvonmuabu_bosung.quyetdinh.btc.KhMuaQdBtcService;
import com.tcdt.qlnvkhoach.service.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpService;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtc;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcp;
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
import java.util.ArrayList;
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

    @Autowired
    KhMuaQdBtcService khMuaQdBtcService;

    @Autowired
    KhMuaQdTtcpService khMuaQdTtcpService;

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

    @ApiOperation(value = "Tạo mới quyết định của thủ tướng chính phủ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> createTtcp(@Valid @RequestBody KhMuaQdTtcpReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhMuaQdTtcp createCheck = khMuaQdTtcpService.save(objReq);
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

    @ApiOperation(value = "Tra cứu quyết định của thủ tướng chính phủ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> searchTtcp(@Valid @RequestBody KhMuaQdTtcpSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khMuaQdTtcpService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Cập nhật quyết định của thủ tướng chính phủ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateTtcp(@Valid @RequestBody KhMuaQdTtcpReq objReq ,HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhMuaQdTtcp createCheck=khMuaQdTtcpService.update(objReq);
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

    @ApiOperation(value = "Chi tiết quyết định của thủ tướng chính phủ", response = List.class)
    @GetMapping(value=PathConstants.URL_TTCP + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detailTtcp(@ApiParam(value = "ID quyết định của thủ tướng chính phủ", example = "1", required = true) @PathVariable("ids") String ids) {
        Resp resp = new Resp();
        try {
            KhMuaQdTtcp khQdTtcp=khMuaQdTtcpService.detailTtcp(ids);
            resp.setData(khQdTtcp);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa quyết định của thủ tướng chính phủ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteTtcp(@RequestBody DeleteRecordReq idSearchReq) {
        Resp resp = new Resp();
        try {
            khMuaQdTtcpService.deleteTtcp(idSearchReq.getId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa danh sách quyết định của TTCP", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteMulti(@Valid @RequestBody KhMuaQdTtcpSearchReq objReq) {
        Resp resp = new Resp();
        try {
            List<Long> ids = new ArrayList<>();
            List<KhMuaQdTtcp> khMuaQdTtcps = khMuaQdTtcpService.searchPage(objReq).getContent();
            for (KhMuaQdTtcp khMuaQdTtcp : khMuaQdTtcps) {
                ids.add(khMuaQdTtcp.getId());
            }
            khMuaQdTtcpService.deleteListId(ids);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Kết xuất danh sách kế hoạch quyết định của Thủ Tướng", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdTtcpToExcel(@Valid @RequestBody KhMuaQdTtcpSearchReq objReq,HttpServletResponse response) throws Exception{

        try {
            khMuaQdTtcpService.exportDsQdTtcp(objReq,response);
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

    @ApiOperation(value = "Tạo mới-00/Ban hành-11 Quyết định phê duyệt TTCP ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateStatusTtcp(@Valid @RequestBody StatusReq statusReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            resp.setData(khMuaQdTtcpService.approve(statusReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu kế hoạch quyết định bộ tài chính", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> searchBTC(@Valid @RequestBody KhMuaQdBtcSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khMuaQdBtcService.searchPage(objReq));
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tạo mới kế hoạch quyết định bộ tài chính ", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> createBtc(@Valid @RequestBody KhMuaQdBtcReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhMuaQdBtc createCheck=khMuaQdBtcService.save(objReq);
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

    @ApiOperation(value = "Cập nhật kế hoạch quyết định bộ tài chính", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateBtc(@Valid @RequestBody KhMuaQdBtcReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhMuaQdBtc createCheck = khMuaQdBtcService.update(objReq);
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

    @ApiOperation(value = "Chi tiết kế hoạch quyết định bộ tài chính ", response = List.class)
    @GetMapping(value=PathConstants.URL_BTC + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detailBtc(@ApiParam(value = "ID quyết định kế hoạch quyết định bộ tài chính ", example = "1", required = true) @PathVariable("ids") String ids) {
        Resp resp = new Resp();
        try {
            KhMuaQdBtc khMuaQdBtc=khMuaQdBtcService.detailBtc(ids);
            resp.setData(khMuaQdBtc);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa kế hoạch quyết định bộ tài chính", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteBtc(@RequestBody DeleteRecordReq idSearchReq) {
        Resp resp = new Resp();
        try {
            khMuaQdBtcService.deleteBtc(idSearchReq.getId());
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xóa danh sách kế hoạch quyết định bộ tài chính", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteMuti(@Valid @RequestBody KhMuaQdBtcSearchReq objReq) {
        Resp resp = new Resp();
        try {
            List<Long> listId = new ArrayList<>();
            for (KhMuaQdBtc khMuaQdBtc : khMuaQdBtcService.searchPage(objReq)) {
                listId.add(khMuaQdBtc.getId());
            }
            khMuaQdBtcService.deleteListId(listId);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Kết xuất danh sách Kế hoạch quyết định BTC", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportListQdBtcBnToExcel(@Valid @RequestBody KhMuaQdBtcSearchReq objReq,HttpServletResponse response) throws Exception{

        try {
            khMuaQdBtcService.exportDsQdTtcp(objReq,response);
        } catch (Exception e) {

            log.error("Kết xuất danh sách đề xuất xuất danh sách quyết định của BTC trace: {}", e);
            final Map<String, Object> body = new HashMap<>();
            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            body.put("msg", e.getMessage());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        }

    }

    @ApiOperation(value = "Tạo mới-00/Ban hành-11 Quyết định phê duyệt BTC ", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateStatusBn(@Valid @RequestBody StatusReq statusReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            resp.setData(khMuaQdBtcService.approve(statusReq));
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


