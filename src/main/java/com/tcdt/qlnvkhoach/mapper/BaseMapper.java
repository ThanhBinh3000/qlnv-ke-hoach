package com.tcdt.qlnvkhoach.mapper;

public interface BaseMapper <E, RES, REQ>{
	E toEntity(REQ request);

	RES toResponse (E entity);
}
