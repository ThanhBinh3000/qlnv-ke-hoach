package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTtChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhLtPagTongHopCTietRepository extends JpaRepository<KhPagTongHopCTiet, Long> {
	List<KhPagTongHopCTiet> findByIdIn(List<Long> ids);
	List<KhPagTongHopCTiet> findByPagThIdIn(List<Long> ids);

	List<KhPagTongHopCTiet> findAllByQdTcdtnnId(Long idQd);
	List<KhPagTongHopCTiet> findAllByQdTcdtnnIdIn(List<Long> idsQd);

	@Transactional
	void deleteAllByQdTcdtnnId(Long qdTcdtnnId);

	@Transactional
	void deleteAllByQdTcdtnnIdIn(List<Long> qdTcdtnnIdList);

	@Query(value = "select ct.* from KH_PAG_TONG_HOP_CTIET ct,KH_PAG_GCT_QD_TCDTNN qd where qd.id = ct.QD_TCDTNN_ID and qd.trang_thai =:trangThai and \n" +
			" ct.ma_dvi = :maDvi and qd.nam_ke_hoach = :namKhoach and qd.LOAI_VTHH = :loaiVthh and qd.cloai_vthh = :cloaiVthh " ,
			nativeQuery = true)
	KhPagTongHopCTiet getKhPagTtcDetail(String trangThai,String maDvi,Long namKhoach,String loaiVthh,String cloaiVthh);
}
