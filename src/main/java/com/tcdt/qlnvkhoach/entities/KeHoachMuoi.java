package com.tcdt.qlnvkhoach.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KH_CHI_TIEU_MUOI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachMuoi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KE_HOACH_MUOI_SEQ")
    @SequenceGenerator(sequenceName = "KE_HOACH_MUOI_SEQ", allocationSize = 1, name = "KE_HOACH_MUOI_SEQ")
    private Long id;
    private Long ctkhnId;

    private Long donViId;
    private String maDvi;
    @Transient
    private String tenDonVi;
    private String maVatTu;
    private Double soLuongNhap;
    private Double soLuongXuat;
    private Double tonKhoDauNam;
    private String donViTinh;
    private Double tonKhoCuoiNam;
    private Integer stt;

    public String groupByDonViIdAndVatTuId() {
        return String.format("%s_%s", maDvi, maVatTu);
    }
}