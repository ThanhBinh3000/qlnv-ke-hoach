package com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhQdTtcpBoNganhRepository extends CrudRepository<KhQdTtcpBoNganh,Long> {

    List<KhQdTtcpBoNganh> findAllByIdQdTtcp(Long idQdTtcp);

    void deleteAllByIdQdTtcp(Long idQdTtcp);

}
