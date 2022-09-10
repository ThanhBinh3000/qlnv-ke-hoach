package com.tcdt.qlnvkhoach.entities.khcnbq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = CnCtrinhNcuuTienDo.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CnCtrinhNcuuTienDo extends BaseEntity implements Serializable {

    public static final String TABLE_NAME = "CN_CONGTRINH_NGHIENCUU_TD";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="NOI_DUNG")
    private String noiDung;

    @Column(name="SAN_PHAM")
    private String sanPham;

    @Column(name="TU_NGAY")
    private LocalDate tuNgay;

    @Column(name="DEN_NGAY")
    private LocalDate denNgay;

    @Column(name="NGUOI_TH")
    private String nguoiTh;

    @Column(name="TRANG_THAI")
    private String trangThai;

    @Column(name="CN_CT_NC_ID")
    private Long cnCtNcId;
}
