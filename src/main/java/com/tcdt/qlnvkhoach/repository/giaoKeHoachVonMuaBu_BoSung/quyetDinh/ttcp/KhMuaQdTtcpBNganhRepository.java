package com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp;

import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganh;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganh;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhMuaQdTtcpBNganhRepository extends CrudRepository<KhMuaQdTtcpBNganh, Long> {
    List<KhMuaQdTtcpBNganh> findAllByIdMuaQdTtcp(Long idMuaQdTtcp);

    void deleteAllByIdMuaQdTtcp(Long idMuaQdTtcp);

    @Transactional
    void deleteAllByIdMuaQdTtcpIn(List<Long> idMuaQdTtcpList );

}
