package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQdBtcCtiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhPagQdBtcCtietRepository extends JpaRepository<KhPagQdBtcCtiet,Long> {

    List<KhPagQdBtcCtiet> findAllByQdBtcId(Long qdBtcId);

    List<KhPagQdBtcCtiet> findAllByQdBtcIdIn(List<Long> ids);

}
