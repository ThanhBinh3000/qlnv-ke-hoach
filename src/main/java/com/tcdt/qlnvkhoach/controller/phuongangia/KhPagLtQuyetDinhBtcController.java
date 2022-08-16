package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagLtQuyetDinhBtc;
import com.tcdt.qlnvkhoach.jwt.CurrentUser;
import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.request.DeleteRecordReq;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagLtQuyetDinhBtcReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagLtQuyetDinhBtcSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPagService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPhuongAnGiaService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhPagLtQuyetDinhBtcService;
import com.tcdt.qlnvkhoach.table.UserInfo;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kh-lt-pag-qd-btc")
@Slf4j
@Api(tags = "Kế hoạch, quyết định giá bộ tài chính")
public class KhPagLtQuyetDinhBtcController extends BaseController {
  @Autowired
  private KhLtPhuongAnGiaService phuongAnGiaService;

  @Autowired
  private KhLtPagService khLtPagService;

  @Autowired
  private KhPagLtQuyetDinhBtcService khPagLtQuyetDinhBtcService;

  @ApiOperation(value = "Tra cứu quyết định giá của BTC", response = List.class)
  @PostMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_QD_GIA_BTC + PathConstants.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> searchKhLtPAG(@CurrentUser CustomUserDetails currentUser, @Valid @RequestBody KhPagLtQuyetDinhBtcSearchReq objReq) {
    Resp resp = new Resp();
    try {
      resp.setData(khPagLtQuyetDinhBtcService.searchPage(objReq));
      resp.setStatusCode(Constants.RESP_SUCC);
      resp.setMsg("Thành công");
    } catch (Exception e) {
      resp.setStatusCode(Constants.RESP_FAIL);
      resp.setMsg(e.getMessage());
      log.error(e.getMessage());
    }
    return ResponseEntity.ok(resp);
  }

  @ApiOperation(value = "Tạo mới quyết định giá của BTC", response = List.class)
  @PostMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_QD_GIA_BTC + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> create(@CurrentUser CustomUserDetails currentUser, @Valid @RequestBody KhPagLtQuyetDinhBtcReq req) {
    Resp resp = new Resp();
    try {
      System.out.println(currentUser.getUser());
      resp.setData(khPagLtQuyetDinhBtcService.create(currentUser, req));
      resp.setStatusCode(Constants.RESP_SUCC);
      resp.setMsg("Thành công");
    } catch (Exception e) {
      resp.setStatusCode(Constants.RESP_FAIL);
      resp.setMsg(e.getMessage());
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return ResponseEntity.ok(resp);
  }


  @ApiOperation(value = "Sửa đề xuất phương án giá", response = List.class)
  @PostMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> updateQd(@Valid @RequestBody KhLtPhuongAnGiaReq req) {
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
  @PostMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
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
  @PostMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
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
  @GetMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> detailDxPag(@ApiParam(value = "ID đề xuất phương án giá", example = "1", required = true) @PathVariable("ids") String ids) {
    Resp resp = new Resp();
    try {
      KhLtPhuongAnGia khQdBtcBoNganh = khLtPagService.detailDxPag(ids);
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
  @PostMapping(value = PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_KIET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void exportListDxPagToExcel(@Valid @RequestBody KhLtPhuongAnGiaSearchReq objReq, HttpServletResponse response) throws Exception {
    try {
      khLtPagService.exportDxPag(objReq, response);
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
