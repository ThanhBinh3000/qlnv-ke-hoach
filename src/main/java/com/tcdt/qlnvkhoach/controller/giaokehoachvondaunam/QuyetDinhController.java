package com.tcdt.qlnvkhoach.controller.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.GiaoKeHoachVonDauNamService;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = PathConstants.URL_GIAO_CHI_TIEU_VA_VON_DAU_NAN+PathConstants.URL_QUYET_DINH)
@Slf4j
@Api(tags = "Giao kế hoạch vốn đầu năm, quyết định")
@RequiredArgsConstructor
public class QuyetDinhController {



    @ApiOperation(value = "Tạo mới quyết định của thủ tướng chính phủ", response = List.class)
    @PostMapping(value=PathConstants.URL_TTCP + PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Resp> createTtcp(@Valid @RequestBody DxDcKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
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
    public final ResponseEntity<Resp> searchTtcp(@Valid @RequestBody DxDcKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
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
    public final ResponseEntity<Resp> updateTtcp(@Valid @RequestBody DxDcKeHoachNamReq req) {
        Resp resp = new Resp();
        try {
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
