package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagTongHopCTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhLtPagTongHopCTietRepository extends JpaRepository<KhLtPagTongHopCTiet, Long> {
	List<KhLtPagTongHopCTiet> findByIdIn(List<Long> ids);
	List<KhLtPagTongHopCTiet> findByPagThIdIn(List<Long> ids);


}
