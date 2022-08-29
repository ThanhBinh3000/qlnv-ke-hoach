package com.tcdt.qlnvkhoach.entities.khcnbq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = CnCtNc.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CnCtNc extends BaseEntity implements Serializable {

    public static final String TABLE_NAME = "CN_CONGTRINH_NGHIENCUU";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CN_CONGTRINH_NGHIENCUU_SEQ")
    @SequenceGenerator(sequenceName = "CN_CONGTRINH_NGHIENCUU_SEQ", allocationSize = 1, name = "CN_CONGTRINH_NGHIENCUU_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name="MA_DT")
    private String maDt;

    @Column(name="TEN_DT")
    private String tenDt;

    @Column(name="CAP_DT")
    private String capDt;

    @Column(name="NAM_TU")
    private Long namTu;

    @Column(name="NAM_DEN")
    private Long namDen;

    @Column(name="CHU_NHIEM_DT")
    private String chuNhiemDt;

    @Column(name="CHUC_VU")
    private String chucVu;

    @Column(name="EMAIL")
    private String email;

    @Column(name="SO_DT")
    private String soDt;

    @Column(name="NOI_DUNG")
    private String noiDung;

    @Column(name="TONG_CHI_PHI")
    private BigDecimal tongChiPhi;

    @Column(name="TRANG_THAI")
    private String trangThai;

    @Column(name="NGAY_NGHIEM_THU")
    private LocalDate ngayNghiemThu;

    @Column(name="DIA_DIEM")
    private String diaDiem;

    @Column(name="THANH_VIEN_HD")
    private String thanhVienHd;

    @Column(name="TVIEN_HD_DVI")
    private Long tvienHdDvi;

    @Column(name="Y_KIEN_DG")
    private String yKienDg;

    @Column(name="TONG_DIEM")
    private BigDecimal tongDiem;

    @Column(name="LOAI")
    private String loai;

    @Transient
    private List<CnCtNcCanCu> cnCtNcCanCus;

    @Transient
    private List<CnCtNcTienDo> cnCtNcTienDos;
}
