package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = KhPagCcPhapLy.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KhPagCcPhapLy implements Serializable {

    private static final long serialVersionUID = -9158383107212840699L;
    public static final String TABLE_NAME = "KH_PAG_CC_PHAP_LY";

    //	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PAG_CC_PHAP_LY_SEQ")
//	@SequenceGenerator(sequenceName = "KH_LT_PAG_CC_PHAP_LY_SEQ", allocationSize = 1, name = "KH_LT_PAG_CC_PHAP_LY_SEQ")
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STT")
    private Long stt;

    @Column(name = "MO_TA")
    private String moTa;

    @Transient
    private  FileDinhKemChung fileDinhKem;
    /**
     * {@link KhPhuongAnGia}
     */
    @Column(name = "PAG_ID")
    private Long phuongAnGiaId;
}
