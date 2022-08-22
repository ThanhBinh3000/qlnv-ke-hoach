package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQuyetDinhBtc;
import com.tcdt.qlnvkhoach.jwt.CurrentUser;
import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagQuyetDinhBtcReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagQuyetDinhBtcSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPagService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPhuongAnGiaService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhPagQuyetDinhBtcService;
import com.tcdt.qlnvkhoach.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tcdt.qlnvkhoach.util.PathConstants.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kh-lt-pag-qd-btc")
@Slf4j
@Api(tags = "Kế hoạch, quyết định giá bộ tài chính")
public class KhPagQuyetDinhBtcController extends BaseController {
  @Autowired
  private KhLtPhuongAnGiaService phuongAnGiaService;

  @Autowired
  private KhLtPagService khLtPagService;

  @Autowired
  private KhPagQuyetDinhBtcService khPagLtQuyetDinhBtcService;

  @ApiOperation(value = "Tra cứu quyết định giá của BTC", response = List.class)
  @PostMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> searchKhLtPAG(@CurrentUser CustomUserDetails currentUser, @Valid @RequestBody KhPagQuyetDinhBtcSearchReq objReq) {
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
  @PostMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> create(@CurrentUser CustomUserDetails currentUser, @Valid @RequestBody KhPagQuyetDinhBtcReq req) {
    Resp resp = new Resp();
    try {
      resp.setData(khPagLtQuyetDinhBtcService.create(currentUser, req));
      resp.setStatusCode(Constants.RESP_SUCC);
      resp.setMsg("Thành công");
    } catch (Exception e) {
      resp.setStatusCode(Constants.RESP_FAIL);
      resp.setMsg(e.getMessage());
      if (e.getMessage().contains("ConstraintViolationException")) {
        resp.setMsg("Số quyết định đã tồn tại.");
      }
      log.error(e.getMessage());
    }
    return ResponseEntity.ok(resp);
  }


  @ApiOperation(value = "Sửa quyết định giá của BTC", response = List.class)
  @PostMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> update(@CurrentUser CustomUserDetails currentUser, @Valid @RequestBody KhPagQuyetDinhBtcReq req) {
    Resp resp = new Resp();
    try {
      resp.setData(khPagLtQuyetDinhBtcService.update(currentUser, req));
      resp.setStatusCode(Constants.RESP_SUCC);
      resp.setMsg("Thành công");
    } catch (Exception e) {
      resp.setStatusCode(Constants.RESP_FAIL);
      resp.setMsg(e.getMessage());
      if (e.getMessage().contains("ConstraintViolationException")) {
        resp.setMsg("Số quyết định đã tồn tại.");
      }
      log.error(e.getMessage());
    }
    return ResponseEntity.ok(resp);
  }


  @ApiOperation(value = "Xóa quyết định giá của BTC", response = List.class)
  @PostMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_XOA_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Resp> deleteMultiple(@CurrentUser CustomUserDetails currentUser, @RequestBody @Valid DeleteReq req) {
    Resp resp = new Resp();
    try {
      resp.setData(khPagLtQuyetDinhBtcService.deleteMultiple(currentUser, req.getIds()));
      resp.setStatusCode(Constants.RESP_SUCC);
      resp.setMsg("Thành công");
    } catch (Exception e) {
      resp.setStatusCode(Constants.RESP_FAIL);
      resp.setMsg(e.getMessage());
      log.error(e.getMessage());
    }
    return ResponseEntity.ok(resp);
  }

  @ApiOperation(value = "Chi tiết quyết định giá của BTC", response = List.class)
  @GetMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> detail(@ApiParam(value = "ID quyết định giá của BTC", example = "1", required = true) @PathVariable("ids") String ids) {
    Resp resp = new Resp();
    try {
      KhPagQuyetDinhBtc data = khPagLtQuyetDinhBtcService.detail(ids);
      resp.setData(data);
      resp.setStatusCode(Constants.RESP_SUCC);
      resp.setMsg("Thành công");
    } catch (Exception e) {
      resp.setStatusCode(Constants.RESP_FAIL);
      resp.setMsg(e.getMessage());
      log.error(e.getMessage());
    }
    return ResponseEntity.ok(resp);
  }

  @ApiOperation(value = "Kết xuất danh sách quyết định giá của BTC", response = List.class)
  @PostMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_KET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void export(@Valid @RequestBody KhPagQuyetDinhBtcSearchReq objReq, HttpServletResponse response) throws Exception {
    try {
      khPagLtQuyetDinhBtcService.export(objReq, response);
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

  @ApiOperation(value = "Ban hành quyết định giá của BTC", response = List.class)
  @PostMapping(value = URL_LUONG_THUC + URL_GIA_LH + URL_QD_GIA_BTC + URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<Resp> updateStatus(@CurrentUser CustomUserDetails currentUser, @Valid @RequestBody StatusReq req) {
    Resp resp = new Resp();
    try {
      resp.setData(khPagLtQuyetDinhBtcService.updateStatus(currentUser, req));
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
}
