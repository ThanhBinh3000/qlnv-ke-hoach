package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.LstQlnvKhvonphiDsachGiaoSoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.lapthamdinhdutoan.QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp;
import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnn;

public interface QlnvKhvonphiPaGiaoSoKiemTraTcNsnnService {

	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp detailPa(String ids);

	public Iterable<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> findAll();

	public QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp detailGiaoSo(String maGiao) throws Exception;

	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp create(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq objReq) throws Exception;

	public void delete(String ids) throws Exception;

	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp update(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq qlnvReq) throws Exception;

	public Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> colectionPa(QlnvKhvonphiPaGiaoSoKiemTraTcNsnnSearchReq objReq)
			throws Exception;

	public ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> giaoSo(LstQlnvKhvonphiDsachGiaoSoReq objReq)
			throws Exception;

	public Page<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> colection(QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnSearchReq objReq)
			throws Exception;

	public String genMaGiaoSo();

	public String genMaPa();

	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnn function(NhomNutChucNangReq objReq) throws Exception;

	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnn addQdAndCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq)
			throws Exception;

	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnn deleteQdCv(QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq objReq)
			throws Exception;
}
