package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;


import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.QdDcChiTieuKeHoachRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmVattu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChiTieuKeHoachNamService {
	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	QdDcChiTieuKeHoachRes createQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes updateQd(ChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	QdDcChiTieuKeHoachRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception;

	boolean deleteQd(Long id) throws Exception;

	boolean deleteQdDc(Long id) throws Exception;

	ChiTieuKeHoachNamRes detailQd(Long id) throws Exception;

	QdDcChiTieuKeHoachRes detailQdDc(Long id) throws Exception;

	boolean updateStatusQd(StatusReq req) throws Exception;

	boolean updateStatusQdDc(StatusReq req) throws Exception;

    void addVatTuThietBiChaRes(VatTuThietBiRes vatTuRes, Map<String, QlnvDmVattu> mapMaVatTu, Map<String, Set<VatTuThietBiRes>> mapNhomVatTu);

	List<VatTuThietBiRes> tinhTongVatTuThietBiCha(Map<String, Set<VatTuThietBiRes>> mapNhomVatTu);

	Page<ChiTieuKeHoachNamRes> searchQd(SearchChiTieuKeHoachNamReq req, Pageable pageable) throws Exception;

	Page<ChiTieuKeHoachNamRes> searchQdDc(SearchChiTieuKeHoachNamReq req, Pageable pageable) throws Exception;
}
