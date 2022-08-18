package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpRepository;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.util.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface KhLtPhuongAnGiaService {
	KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception;

	KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception;
	boolean deleteMultiple(List<Long> ids) throws Exception;


}
