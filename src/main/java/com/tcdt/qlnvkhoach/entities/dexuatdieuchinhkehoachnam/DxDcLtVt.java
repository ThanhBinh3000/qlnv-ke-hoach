package com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = DxDcLtVt.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DxDcLtVt implements Serializable {
    private static final long serialVersionUID = 8879872838648310353L;

    public static final String TABLE_NAME = "DX_DC_LT_VT";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DX_DC_LT_VT_SEQ")
    @SequenceGenerator(sequenceName = "DX_DC_LT_VT_SEQ", allocationSize = 1, name = "DX_DC_LT_VT_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DXDCKHN_ID")
    private Long dxdckhnId; // DX_DC_KE_HOACH_NAM

    @Column(name = "MA_VAT_TU")
    private String maVatTu; // DM_VATTU

    @Column(name = "MA_VAT_TU_CHA")
    private String maVatTuCha; // DM_VATTU

    @Column(name = "SO_LUONG")
    private Double soLuong;

    @Column(name = "DON_VI_TINH")
    private String donViTinh;

    @Column(name = "CHI_TIEU")
    private String chiTieu;

    @Column(name = "LOAI")
    private String loai;

    @Transient
    private List<DxDcLtVtCt> dxDcLtVtCtList = new ArrayList<>();
}
