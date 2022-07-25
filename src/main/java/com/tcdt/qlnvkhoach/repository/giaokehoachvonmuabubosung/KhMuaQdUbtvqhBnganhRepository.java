package com.tcdt.qlnvkhoach.repository.giaokehoachvonmuabubosung;

import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganh;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;

public interface KhMuaQdUbtvqhBnganhRepository extends CrudRepository<KhMuaQdUbtvqhBnganh,Long> {
    void deleteAllByIdMuaQdUbtvqh(Long idMuaQdUbtvqh);

    List<KhMuaQdUbtvqhBnganh> findAllByIdMuaQdUbtvqh(Long idMuaQdUbtvqh);

    @Transactional
    void deleteAllByIdMuaQdUbtvqhIn(List<Long> idMuaQdUbtvqdList);
}
