package com.tcdt.qlnvkhoach.controller.giaokehoachvondaunam;


import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdBtcTcdtReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdBtcTcdtSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.KhQdTtcpRes;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdBtcTcdtService;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.KhQdTtcpService;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdt;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = PathConstants.URL_GIAO_CHI_TIEU_VA_VON_DAU_NAN+PathConstants.URL_QUYET_DINH)
@Slf4j
@Api(tags = "Giao kế hoạch vốn đầu năm, quyết định")
@RequiredArgsConstructor
public class QuyetDinhController {

    @Autowired
    private KhQdTtcpService khQdTtcpService;
    @Autowired
    private KhQdBtcTcdtService khQdBtcTcdtService;

    @ApiOperation(value = "Tạo mới quyết định của thủ tướng chính phủ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> createTtcp(@Valid @RequestBody  KhQdTtcpReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhQdTtcp createCheck = khQdTtcpService.save(objReq);
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
    public final ResponseEntity<Resp> searchTtcp(@Valid @RequestBody KhQdTtcpSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khQdTtcpService.searchPage(objReq));
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
    public final ResponseEntity<Resp> updateTtcp(@Valid @RequestBody KhQdTtcpReq objReq ,HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhQdTtcp createCheck=khQdTtcpService.update(objReq);
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
            KhQdTtcp khQdTtcp=khQdTtcpService.detailTtcp(ids);
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
    @GetMapping(value=PathConstants.URL_TTCP + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteTtcp(@ApiParam(value = "ID quyết định của thủ tướng chính phủ", example = "1", required = true) @PathVariable("ids") Long ids) {
        Resp resp = new Resp();
        try {
            khQdTtcpService.deleteTtcp(ids);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }


    //Bộ tài chính giao Tồng cục dự trữ

    @ApiOperation(value = "Tạo mới quyết định của BTC giao TCDT", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC_TCDT + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> createBtcTcdt(@Valid @RequestBody KhQdBtcTcdtReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhQdBtcTcdt createCheck = khQdBtcTcdtService.save(objReq);
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
    @ApiOperation(value = "Cập nhật quyết định của BTC giao TCDT", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC_TCDT + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> updateBtcTcdt(@Valid @RequestBody KhQdBtcTcdtReq objReq, HttpServletRequest req) {
        Resp resp = new Resp();
        try {
            KhQdBtcTcdt createCheck = khQdBtcTcdtService.update(objReq);
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
    @ApiOperation(value = "Chi tiết quyết định của BTC giao TCDT", response = List.class)
    @GetMapping(value=PathConstants.URL_BTC_TCDT + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> detailBtcTcdt(@ApiParam(value = "ID quyết định của BTC giao TCDT", example = "1", required = true) @PathVariable("ids") Long ids) {
        Resp resp = new Resp();
        try {
            KhQdBtcTcdt khQdBtcTcdt=khQdBtcTcdtService.detail(ids);
            resp.setData(khQdBtcTcdt);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
    @ApiOperation(value = "Xóa quyết định của BTC giao TCDT", response = List.class)
    @GetMapping(value=PathConstants.URL_BTC_TCDT + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> deleteBtcTcdt(@ApiParam(value = "ID quyết định của BTC giao TCDT", example = "1", required = true) @PathVariable("ids") Long ids) {
        Resp resp = new Resp();
        try {
            khQdBtcTcdtService.delete(ids);
            resp.setStatusCode(Constants.RESP_SUCC);
            resp.setMsg("Thành công");
        } catch (Exception e) {
            resp.setStatusCode(Constants.RESP_FAIL);
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
    @ApiOperation(value = "Xóa quyết định của BTC giao TCDT", response = List.class)
    @PostMapping(value=PathConstants.URL_BTC_TCDT + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> selectPage(@Valid @RequestBody KhQdBtcTcdtSearchReq objReq) {
        Resp resp = new Resp();
        try {
            resp.setData(khQdBtcTcdtService.searchPage(objReq));
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
