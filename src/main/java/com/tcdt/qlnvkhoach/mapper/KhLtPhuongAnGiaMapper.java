package com.tcdt.qlnvkhoach.mapper;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KhLtPhuongAnGiaMapper extends BaseMapper<KhLtPhuongAnGia, KhLtPhuongAnGiaRes, KhLtPhuongAnGiaReq> {
	KhLtPhuongAnGiaMapper INSTANCE = Mappers.getMapper(KhLtPhuongAnGiaMapper.class);
}
