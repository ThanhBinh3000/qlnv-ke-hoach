package com.tcdt.qlnvkhoach.table.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "FILE_DINH_KEM_KHVONPHI")
@Data
public class FileDinhKem implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_DINH_KEM_KHVONPHI_SEQ")
	@SequenceGenerator(sequenceName = "FILE_DINH_KEM_KHVONPHI_SEQ", allocationSize = 1, name = "FILE_DINH_KEM_KHVONPHI_SEQ")
	private Long id;
	String fileName;
	String fileSize;
	String fileUrl;
	Date fileType;
	String dataType;
	Long qlnvId;
	Date createDate;

}
