package com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc;

import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganh;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhMuaQdBtcBNganhRepository extends CrudRepository<KhMuaQdBtcBNganh, Long> {
    List<KhMuaQdBtcBNganh> findAllByIdMuaQdBtc(Long idMuaQdBtc);

    void deleteAllByIdMuaQdBtc(Long idMuaQdBtc);

    @Transactional
    void deleteAllByIdMuaQdBtcIn(List<Long> idMuaQdBtcList);

}
