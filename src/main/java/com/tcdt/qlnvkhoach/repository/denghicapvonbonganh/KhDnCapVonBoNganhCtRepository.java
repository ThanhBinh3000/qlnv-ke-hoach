package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnCapVonBoNganhCtRepository extends BaseRepository<KhDnCapVonBoNganhCt, Long> {
	void deleteAllByIdIn(Collection<Long> ids);

	List<KhDnCapVonBoNganhCt> findByIdIn(List<Long> ids);

	List<KhDnCapVonBoNganhCt> findByDeNghiCapVonBoNganhIdIn(Collection<Long> deNghiIds);


	//    Lấy thông tin từ bảng của HDV (KH_VP_THOP_DNCV,KH_VP_DNGHI_CAPVON) bảng đề nghị cấp vốn và tổng hợp đề nghị cấp vốn.
	@Query(value = "select sum(ct.tong_tien_thoc + ct.tong_tien_gao + ct.tong_tien_muoi) tong_tien," +
			" sum(ct.kphi_da_cap_thoc + ct.kphi_da_cap_gao + ct.kphi_da_cap_muoi) kp_da_cap," +
			" sum(ct.ycau_cap_them_thoc + ct.ycau_cap_them_gao + ct.ycau_cap_them_muoi) yc_cap_them," +
			" hdr.MA_DNGHI," +
			" hdr.THUYET_MINH," +
			" 'LT' loai, " +
			" to_char(hdr.ngay_tao,'yyyy-MM-dd') ngay_dn" +
			" from KH_VP_THOP_DNCV hdr,KH_VP_THOP_DNCV_CKV_CTIET ct where  hdr.id = ct.THOP_DNGHI_CAPVON_ID and to_number(hdr.nam_dn) = to_number(:nam) GROUP BY hdr.MA_DNGHI,hdr.THUYET_MINH,hdr.ngay_tao" +
			" union " +
			" select dn.tong_tien,dn.kphi_da_cap,dn.ycau_cap_them,dn.MA_DNGHI,dn.THUYET_MINH, 'VT' loai,to_char(dn.ngay_tao,'yyyy-MM-dd') ngay_dn from KH_VP_DNGHI_CAPVON dn where LOAI_DNGHI = 3 and TRANG_THAI = 7 and to_number(dn.nam_dn) = to_number(:nam)",
			nativeQuery = true)
	List<Object[]> getDnCapVonDonViByNam(Integer nam);
}
