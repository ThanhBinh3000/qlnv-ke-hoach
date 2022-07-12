package com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganhCTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhQdTtcpBoNganhCtietRepository extends CrudRepository<KhQdTtcpBoNganhCTiet,Long> {

    List<KhQdTtcpBoNganhCTiet> findAllByIdBoNganh(Long idBoNganh);

}
