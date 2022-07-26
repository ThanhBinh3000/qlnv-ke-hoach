package com.tcdt.qlnvkhoach.repository.giaokehoachvonmuabubosung;

import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganhCtiet;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;

public interface KhMuaQdUbtvqhBnganhCtietRepository extends CrudRepository<KhMuaQdUbtvqhBnganhCtiet,Long> {
    void deleteAllByIdBoNganh(Long idBoNganh);

    List<KhMuaQdUbtvqhBnganhCtiet> findAllByIdBoNganh(Long idBoNganh);

    @Transactional
    void deleteAllByIdBoNganhIn(List<Long> idBoNganhList);

}
