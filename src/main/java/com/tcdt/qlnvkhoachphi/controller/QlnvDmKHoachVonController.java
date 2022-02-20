package com.tcdt.qlnvkhoachphi.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvDmKHoachVonRepository;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKHoachVon;
import com.tcdt.qlnvkhoachphi.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/dmuc-khoach-von")
@Slf4j
@Api(tags = "Danh mục kế hoạch vốn")
public class QlnvDmKHoachVonController extends BaseController {

	@Autowired
	private QlnvDmKHoachVonRepository qlnvDmKHoachVonRepository;

	@ApiOperation(value = "Lấy chi tiết danh mục", response = List.class)
	@GetMapping(value = "/chi-tiet/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvDmKHoachVon> qlnvDmKHoachVon = qlnvDmKHoachVonRepository.findById(Long.parseLong(ids));
			if (!qlnvDmKHoachVon.isPresent())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			resp.setData(qlnvDmKHoachVon);
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