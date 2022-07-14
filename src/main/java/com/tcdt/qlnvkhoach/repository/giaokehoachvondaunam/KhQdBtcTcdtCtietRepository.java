package com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdtCtiet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhQdBtcTcdtCtietRepository extends CrudRepository<KhQdBtcTcdtCtiet, Long> {
    List<KhQdBtcTcdtCtiet> findAllByIdQdBtcTcdt(Long idQdBtcTcdt);
    void deleteAllByIdQdBtcTcdt(Long idQdBtcTcdt);
}
