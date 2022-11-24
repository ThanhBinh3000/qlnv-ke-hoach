package com.tcdt.qlnvkhoach.response;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class UserInfoResp {
	Long id;
	String username;
	String fullName;
	String status;
	Long dvql;
	String token;
	String sysType;
	Long groupId;
	Timestamp createTime;
	String createBy;
	String email;
	String idcard;
	Long chucvuId;
	String phoneNo;
	String title;
	String description;
	String region;
	String department;
	long notifyViewId;
	String groupsArr;
	String maDvi;
	String capDvi;
}
