package com.tcdt.qlnvkhoach.service.chitieukehoachnam;


import com.tcdt.qlnvkhoach.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoach.entities.KeHoachLuongThucMuoi;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SoLuongTruocDieuChinhSearchReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamCount;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.SoLuongTruocDieuChinhRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChiTieuKeHoachNamService {
	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes createQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes updateQd(ChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception;

	boolean deleteQd(Long id) throws Exception;

	boolean deleteQdDc(Long id) throws Exception;

	ChiTieuKeHoachNamRes detailQd(Long id) throws Exception;

	ChiTieuKeHoachNamRes detailQdDc(Long id) throws Exception;

	boolean updateStatusQd(StatusReq req) throws Exception;

	boolean updateStatusQdDc(StatusReq req) throws Exception;

	List<KeHoachLuongThucMuoi> retrieveKhltm(ChiTieuKeHoachNam chiTieuKeHoachNam);

	ChiTieuKeHoachNamRes buildDetailResponse(ChiTieuKeHoachNam chiTieuKeHoachNam, Integer namKeHoachChiTieu) throws Exception;

	void addVatTuThietBiChaRes(VatTuThietBiRes vatTuRes, Map<String, QlnvDmVattu> mapMaVatTu, Map<String, Set<VatTuThietBiRes>> mapNhomVatTu);

	List<VatTuThietBiRes> tinhTongVatTuThietBiCha(Map<String, Set<VatTuThietBiRes>> mapNhomVatTu);

	Page<ChiTieuKeHoachNamRes> searchQd(SearchChiTieuKeHoachNamReq req) throws Exception;

	Page<ChiTieuKeHoachNamRes> searchQdDc(SearchChiTieuKeHoachNamReq req) throws Exception;

	SoLuongTruocDieuChinhRes getSoLuongTruocDc(SoLuongTruocDieuChinhSearchReq req) throws Exception;

	ChiTieuKeHoachNam getChiTieuKeHoachNam(Long id) throws Exception;

	ChiTieuKeHoachNam getChiTieuKeHoachNamLatest(ChiTieuKeHoachNam qdGoc) throws Exception;

	void retrieveDataChiTieuKeHoachNam(ChiTieuKeHoachNam chiTieuKeHoachNam);

	ChiTieuKeHoachNamCount countCtkhn(String loaiQd) throws Exception;

	void prepareSearchReq(SearchChiTieuKeHoachNamReq req, UserInfo userInfo, String capDviReq);

    boolean deleteMultiple(DeleteReq req) throws Exception;

	ChiTieuKeHoachNam getChiTieuDxKhLcnt(Long namKh) throws Exception;


	ChiTieuKeHoachNam getChiTieuDxKhLcntByDvi(Long namKh,String maDvi) throws Exception;
}
