package com.tcdt.qlnvkhoach.controller.phuongangia;

import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPagService;
import com.tcdt.qlnvkhoach.service.phuongangia.KhLtPhuongAnGiaService;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.PathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.xpath.XPathConstants;
import java.util.List;

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
    @PutMapping(value= PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @DeleteMapping(value =PathConstants.URL_LUONG_THUC + PathConstants.URL_GIA_LH + PathConstants.URL_DX_PAG + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
