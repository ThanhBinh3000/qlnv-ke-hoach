package com.tcdt.qlnvkhoach.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FILE_DINH_KEM")
@Data
public class FileDinhKemChung implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_DINH_KEM_SEQ")
	@SequenceGenerator(sequenceName = "FILE_DINH_KEM_SEQ", allocationSize = 1, name = "FILE_DINH_KEM_SEQ")
	private Long id;
	String fileName;
	String fileSize;
	String fileUrl;
	Date fileType;
	String dataType;
	Long dataId;
	Date createDate;
	String noiDung;
}
