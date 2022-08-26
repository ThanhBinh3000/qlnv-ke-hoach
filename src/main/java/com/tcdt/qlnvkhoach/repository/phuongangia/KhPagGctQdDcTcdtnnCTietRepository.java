package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagGctQdDcTcdtnn;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQdDcTcdtnnCTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhPagGctQdDcTcdtnnCTietRepository extends JpaRepository<KhPagQdDcTcdtnnCTiet,Long> {

    List<KhPagQdDcTcdtnnCTiet> findAllByQdDcTcdtnnIdIn(List<Long> ids);

    @Transactional
    void deleteAllByQdDcTcdtnnIdIn(List<Long> ids);


    @Transactional
    void deleteAllByQdDcTcdtnnId(Long qdDcTcdtnnId);

}
