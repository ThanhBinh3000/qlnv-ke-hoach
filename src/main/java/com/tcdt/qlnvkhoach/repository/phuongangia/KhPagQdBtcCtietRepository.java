package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQdBtcCtiet;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQdTcdtnnCtiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.Transient;
import java.util.List;

public interface KhPagQdBtcCtietRepository extends JpaRepository<KhPagQdBtcCtiet,Long> {

    @Transient
    List<KhPagQdTcdtnnCtiet> findAllByQdBtcId(Long qdBtcId);

}
