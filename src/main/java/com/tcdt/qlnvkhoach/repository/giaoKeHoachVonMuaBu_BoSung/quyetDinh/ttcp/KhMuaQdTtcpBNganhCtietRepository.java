package com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp;

import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganhCTiet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhMuaQdTtcpBNganhCtietRepository extends CrudRepository<KhMuaQdTtcpBNganhCTiet, Long> {
    List<KhMuaQdTtcpBNganhCTiet> findAllByIdBoNganh(Long idBoNganh);

    void deleteAllByIdBoNganh(Long idBoNganh);

    @Transactional
    void deleteAllByIdBoNganhIn(List<Long> idBoNganhList);

}
