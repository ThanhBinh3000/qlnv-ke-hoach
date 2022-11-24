package com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DxDcLtVtCt.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DxDcLtVtCt implements Serializable {

    public static final String TABLE_NAME = "KH_DX_DC_LT_VT_CT";
    private static final long serialVersionUID = 1834736356743949473L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DX_DC_LT_VT_CT_SEQ")
    @SequenceGenerator(sequenceName = "DX_DC_LT_VT_CT_SEQ", allocationSize = 1, name = "DX_DC_LT_VT_CT_SEQ")
    @Column(name = "ID")
    private Long id;

    private Long dxDcLtVtId; // DxDcLtVt
    private String maDvi;
    private String maDiemKho;
    private String maNhaKho;
    private String maNganKho;
    private String maLoKho;
    private Double soLuongTang;
    private Double soLuongGiam;
}
