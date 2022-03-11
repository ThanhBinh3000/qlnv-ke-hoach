package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import javax.servlet.http.HttpServletRequest;

import com.tcdt.qlnvkhoachphi.request.object.catalog.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;

public interface QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService {
	public Resp detailPa(String ids);
	
	public Resp findAll();
	
	public Resp detailGiaoSo(String maGiao);
	
	public Resp create(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq,
			HttpServletRequest req);
	
	public Resp delete(String ids, HttpServletRequest req);
	
	public Resp update(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq qlnvReq,
			HttpServletRequest req);
	
	public Resp colectionPa(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq,
			HttpServletRequest req);
	
	public Resp giaoSo(LstQlnvKhvonphiDsachGiaoSoReq objReq,
			HttpServletRequest req);
	
	public Resp colection(QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq,
			HttpServletRequest req);
	
	public Resp genMaGiaoSo();
	
	public Resp genMaPa();
	
	public Resp function(NhomNutChucNangReq objReq, HttpServletRequest req);
	
	public Resp addQdAndCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq,
			HttpServletRequest req);
	
	public Resp deleteQdCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq,
			HttpServletRequest req);
}
