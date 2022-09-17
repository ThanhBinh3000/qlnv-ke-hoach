package com.tcdt.qlnvkhoach.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Set;

@Data
public class BaseRequest {
	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_LIMIT = 10;

	public static final String ORDER_BY = "id";

	public static final String ORDER_TYPE = "asc";

	private PaggingReq paggingReq;
	private String trangThai;
	private String str;
	String orderBy;
	String orderDirection;
	Set<String> maDvis = new HashSet<>();
	Set<String> trangThais = new HashSet<>();
	Set<String> capDvis = new HashSet<>();

	public PaggingReq getPaggingReq() {
		if (this.paggingReq == null) {
			this.paggingReq = new PaggingReq(DEFAULT_LIMIT, DEFAULT_PAGE,ORDER_BY,ORDER_TYPE);
		}
		return this.paggingReq;
	}

	public Pageable getPageable() {
		return PageRequest.of(this.getPaggingReq().page, this.getPaggingReq().limit);
	}
}