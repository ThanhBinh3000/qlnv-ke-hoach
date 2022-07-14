package com.tcdt.qlnvkhoach.repository.kehoachquyetdinhbotaichinhbonganh;

import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganhCtiet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhQdBtcBoNganhCtietRepository extends CrudRepository<KhQdBtcBoNganhCtiet,Long> {

    List<KhQdBtcBoNganhCtiet> findAllByIdQdBtcNganh(Long idQdBtcNganh);

    void deleteAllByIdQdBtcNganh(Long idQdBtcNganh);
}
