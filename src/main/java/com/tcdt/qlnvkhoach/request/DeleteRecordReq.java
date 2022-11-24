package com.tcdt.qlnvkhoach.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeleteRecordReq {

	String ids;

	Long id;
	private List<Long> listId = new ArrayList<>();

}