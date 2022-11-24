package com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc;

import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganhCTiet;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganhCTiet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhMuaQdBtcBNganhCtietRepository extends CrudRepository<KhMuaQdBtcBNganhCTiet, Long> {
    List<KhMuaQdBtcBNganhCTiet> findAllByIdBoNganh(Long idBoNganh);

    void deleteAllByIdBoNganh(Long idBoNganh);

    @Transactional
    void deleteAllByIdBoNganhIn(List<Long> idBoNganhList);
}
