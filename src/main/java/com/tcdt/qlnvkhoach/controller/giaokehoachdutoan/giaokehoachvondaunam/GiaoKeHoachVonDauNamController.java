package com.tcdt.qlnvkhoach.controller.giaokehoachdutoan.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.service.giaokehoachvondaunam.GiaoKeHoachVonDauNamService;
import com.tcdt.qlnvkhoach.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/giao-kh-von-dau-nam")
@Slf4j
@Api(tags = "Giao kế hoạch vốn đầu năm")
@RequiredArgsConstructor
public class GiaoKeHoachVonDauNamController {
    private final GiaoKeHoachVonDauNamService giaoKeHoachVonDauNamService;

    @ApiOperation(value = "Count Giao kế hoạch vốn đầu năm", response = List.class)
    @GetMapping("/count")
    public final ResponseEntity<Resp> count() {
        Resp resp = new Resp();
        try {
            resp.setData(giaoKeHoachVonDauNamService.count());
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
