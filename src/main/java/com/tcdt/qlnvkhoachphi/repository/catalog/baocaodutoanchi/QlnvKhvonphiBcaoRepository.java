package com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcao;

@Repository
public interface QlnvKhvonphiBcaoRepository extends CrudRepository<QlnvKhvonphiBcao, Long> {
	String value = "SELECT * FROM QLNV_KHVONPHI_BCAO t "
			+ "WHERE (:namBcao is null or lower(t.NAM_BCAO) like lower(concat(concat('%', :namBcao),'%'))) "
			+ "AND (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:thangBcao is null or lower(t.THANG_BCAO) like lower(concat(concat('%', :thangBcao),'%'))) "
			+ "AND (:dotBcao is null or lower(t.DOT_BCAO) like lower(concat(concat('%', :dotBcao),'%'))) "
			+ "AND (:maBcao is null or lower(t.MA_BCAO) like lower(concat(concat('%', :maBcao),'%'))) "
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ "AND (:maLoaiBcao is null or lower(t.MA_LOAI_BCAO) like lower(concat(concat('%', :maLoaiBcao),'%')) "
			+ " or lower(t.MA_LOAI_BCAO) like lower(concat(concat('%', :maLoaiBcao),'%')) and lower(t.LOAI_BAO_CAO) like lower(concat(concat('%', :loaiBaoCao),'%'))  )";

	String countQuery = "SELECT * FROM QLNV_KHVONPHI_BCAO t "
			+ "WHERE (:namBcao is null or lower(t.NAM_BCAO) like lower(concat(concat('%', :namBcao),'%'))) "
			+ "AND (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:thangBcao is null or lower(t.THANG_BCAO) like lower(concat(concat('%', :thangBcao),'%'))) "
			+ "AND (:dotBcao is null or lower(t.DOT_BCAO) like lower(concat(concat('%', :dotBcao),'%'))) "
			+ "AND (:maBcao is null or lower(t.MA_BCAO) like lower(concat(concat('%', :maBcao),'%'))) "
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ "AND (:maLoaiBcao is null or lower(t.MA_LOAI_BCAO) like lower(concat(concat('%', :maLoaiBcao),'%')) "
			+ " or lower(t.MA_LOAI_BCAO) like lower(concat(concat('%', :maLoaiBcao),'%')) and lower(t.LOAI_BAO_CAO) like lower(concat(concat('%', :loaiBaoCao),'%'))  )";

	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiBcao> selectParams(String maDvi, String ngayTaoTu, String ngayTaoDen, String trangThai,
			Long namBcao, Long thangBcao, String maLoaiBcao, String maBcao,Long dotBcao,Long loaiBaoCao, Pageable pageable);

	@Override
	Iterable<QlnvKhvonphiBcao> findAll();

	List<QlnvKhvonphiBcao> findByMaBcao(String maBcao);

	String genMaBCao = "SELECT MA_QLNV_KHVONPHI_BCAO_SEQ.NEXTVAL FROM DUAL";

	@Query(value = genMaBCao, nativeQuery = true)
	int getMaBaoCao();
}