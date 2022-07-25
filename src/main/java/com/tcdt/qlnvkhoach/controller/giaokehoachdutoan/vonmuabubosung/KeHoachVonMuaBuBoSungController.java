package com.tcdt.qlnvkhoach.controller.giaokehoachdutoan.vonmuabubosung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdBtcBoNganhService;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdBtcTcdtService;
import com.tcdt.qlnvkhoach.service.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpService;
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
    KhQdBtcBoNganhService khQdBtcBoNganhService;

    @Autowired
    private KhMuaQdTtcpService khMuaQdTtcpService;

    @Autowired
    private KhQdBtcTcdtService khQdBtcTcdtService;

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

//    @ApiOperation(value = "Tra cứu kế hoạch quyết định bộ tài chính giao bộ ngành", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> searchBTC(@Valid @RequestBody KhQdBtBoNganhSearchReq objReq) {
//        Resp resp = new Resp();
//        try {
//            resp.setData(khQdBtcBoNganhService.searchPage(objReq));
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Tạo mới kế hoạch quyết định bộ tài chính giao bộ ngành", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> createBtc(@Valid @RequestBody KhQdBtcBoNganhReq objReq, HttpServletRequest req) {
//        Resp resp = new Resp();
//        try {
//            KhQdBtcBoNganh createCheck=khQdBtcBoNganhService.save(objReq,req);
//            resp.setData(createCheck);
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Cập nhật kế hoạch quyết định bộ tài chính giao bộ ngành", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> updateBtc(@Valid @RequestBody KhQdBtcBoNganhReq objReq, HttpServletRequest req) {
//        Resp resp = new Resp();
//        try {
//            KhQdBtcBoNganh createCheck = khQdBtcBoNganhService.update(objReq);
//            resp.setData(createCheck);
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Chi tiết kế hoạch quyết định bộ tài chính giao bộ ngành", response = List.class)
//    @GetMapping(value=PathConstants.URL_TTCP + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> detailBtc(@ApiParam(value = "ID quyết định kế hoạch quyết định bộ tài chính giao bộ ngành", example = "1", required = true) @PathVariable("ids") String ids) {
//        Resp resp = new Resp();
//        try {
//            KhQdBtcBoNganh khQdBtcBoNganh=khQdBtcBoNganhService.detailBtc(ids);
//            resp.setData(khQdBtcBoNganh);
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Xóa kế hoạch quyết định bộ tài chính giao bộ ngành", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> deleteBtc(@RequestBody DeleteRecordReq idSearchReq) {
//        Resp resp = new Resp();
//        try {
//            khQdBtcBoNganhService.delete(idSearchReq.getId());
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Xóa danh sách kế hoạch quyết định bộ tài chính giao bộ ngành", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> deleteMuti(@Valid @RequestBody KhQdBtBoNganhSearchReq objReq) {
//        Resp resp = new Resp();
//        try {
//            khQdBtcBoNganhService.deleteListId(objReq.getIdList());
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Kết xuất danh sách Kế hoạch quyết định BTC Bộ ngành", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public void exportListQdBtcBnToExcel(@Valid @RequestBody KhQdBtBoNganhSearchReq objReq,HttpServletResponse response) throws Exception{
//
//        try {
//            khQdBtcBoNganhService.exportDsQdBtc(objReq,response);
//        } catch (Exception e) {
//
//            log.error("Kết xuất danh sách đề xuất xuất danh sách quyết định của BTC trace: {}", e);
//            final Map<String, Object> body = new HashMap<>();
//            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            body.put("msg", e.getMessage());
//
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding("UTF-8");
//
//            final ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(response.getOutputStream(), body);
//        }
//
//    }
//
//    @ApiOperation(value = "Tạo mới-00/Ban hành-11 Quyết định phê duyệt TTCP ", response = List.class)
//    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> updateStatusBn(@Valid @RequestBody StatusReq statusReq, HttpServletRequest req) {
//        Resp resp = new Resp();
//        try {
//            resp.setData(khQdBtcBoNganhService.approve(statusReq));
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    //Bộ tài chính giao Tồng cục dự trữ
//    @ApiOperation(value = "Tạo mới quyết định của BTC giao TCDT", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> createBtcTcdt(@Valid @RequestBody KhQdBtcTcdtReq objReq, HttpServletRequest req) {
//        Resp resp = new Resp();
//        try {
//            KhQdBtcTcdt createCheck = khQdBtcTcdtService.save(objReq);
//            resp.setData(createCheck);
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Cập nhật quyết định của BTC giao TCDT", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> updateBtcTcdt(@Valid @RequestBody KhQdBtcTcdtReq objReq, HttpServletRequest req) {
//        Resp resp = new Resp();
//        try {
//            KhQdBtcTcdt createCheck = khQdBtcTcdtService.update(objReq);
//            resp.setData(createCheck);
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Chi tiết quyết định của BTC giao TCDT", response = List.class)
//    @GetMapping(value=PathConstants.URL_BTC + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> detailBtcTcdt(@ApiParam(value = "ID quyết định của BTC giao TCDT", example = "1", required = true) @PathVariable("ids") Long ids) {
//        Resp resp = new Resp();
//        try {
//            KhQdBtcTcdt khQdBtcTcdt=khQdBtcTcdtService.detail(ids);
//            resp.setData(khQdBtcTcdt);
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Xóa quyết định của BTC giao TCDT", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> deleteBtcTcdt(@RequestBody DeleteRecordReq idSearchReq) {
//        Resp resp = new Resp();
//        try {
//            khQdBtcTcdtService.delete(idSearchReq.getId());
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Tra cứu quyết định của BTC giao TCDT", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> selectPage(@Valid @RequestBody KhQdBtcTcdtSearchReq objReq) {
//        Resp resp = new Resp();
//        try {
//            resp.setData(khQdBtcTcdtService.searchPage(objReq));
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Xóa danh sách quyết định của BTC giao TCDT", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> deleteMulti(@Valid @RequestBody KhQdBtcTcdtSearchReq objReq) {
//        Resp resp = new Resp();
//        try {
//            khQdBtcTcdtService.deleteListId(objReq.getIdList());
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }
//
//    @ApiOperation(value = "Kết xuất danh sách Kế hoạch quyết định BTC giao TCDT", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public void exportListQdBtcTcdtToExcel(@Valid @RequestBody KhQdBtcTcdtSearchReq objReq,HttpServletResponse response) throws Exception{
//
//        try {
//            khQdBtcTcdtService.exportDsQdBtcTcdt(objReq,response);
//        } catch (Exception e) {
//
//            log.error("Kết xuất danh sách đề xuất xuất danh sách quyết BTC TCDT trace: {}", e);
//            final Map<String, Object> body = new HashMap<>();
//            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            body.put("msg", e.getMessage());
//
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding("UTF-8");
//
//            final ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(response.getOutputStream(), body);
//        }
//
//    }
//
//    @ApiOperation(value = "Tạo mới-00/Ban hành-11 Quyết định phê duyệt TTCP ", response = List.class)
//    @PostMapping(value=PathConstants.URL_BTC + PathConstants.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final ResponseEntity<Resp> updateStatusBtcTcdt(@Valid @RequestBody StatusReq statusReq, HttpServletRequest req) {
//        Resp resp = new Resp();
//        try {
//            resp.setData(khQdBtcTcdtService.approve(statusReq));
//            resp.setStatusCode(Constants.RESP_SUCC);
//            resp.setMsg("Thành công");
//        } catch (Exception e) {
//            resp.setStatusCode(Constants.RESP_FAIL);
//            resp.setMsg(e.getMessage());
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.ok(resp);
//    }



}


