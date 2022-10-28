package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagCcPhapLy;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTtChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface KhPagTtChungRepository extends JpaRepository<KhPagTtChung, Long> {
	List<KhPagTtChung> findByPhuongAnGiaIdIn(List<Long> ids);


	List<KhPagTtChung> findByQdTcdtnnId(Long ids);
	List<KhPagTtChung> findALlByQdBtcId(Long ids);

	@Query(value = "select  ttc.* from KH_PAG_TT_CHUNG ttc,KH_PAG_GCT_QD_TCDTNN qd where " +
			" qd.trang_thai = :trangThai and ttc.QD_TCDTNN_ID = qd.id and\n" +
			" qd.ma_dvi = :maDvi and qd.nam_ke_hoach = :namKhoach and qd.LOAI_VTHH = :loaiVthh and qd.cloai_vthh = :cloaiVthh" ,
			nativeQuery = true)
	KhPagTtChung getKhPagTtcDetail(String trangThai,String maDvi,Long namKhoach,String loaiVthh,String cloaiVthh);

}
