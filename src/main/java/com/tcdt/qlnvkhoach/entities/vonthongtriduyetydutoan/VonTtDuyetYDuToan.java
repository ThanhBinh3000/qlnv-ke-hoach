package com.tcdt.qlnvkhoach.entities.vonthongtriduyetydutoan;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = VonTtDuyetYDuToan.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VonTtDuyetYDuToan extends TrangThaiBaseEntity implements Serializable {
    // Cấp vốn thông tri duyệt y dự toán
    public static final String TABLE_NAME = "KH_VON_TT_DY_DTOAN";
    private static final long serialVersionUID = -4074438189820672619L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_VON_TT_DY_DTOAN_SEQ")
    @SequenceGenerator(sequenceName = "KH_VON_TT_DY_DTOAN_SEQ", allocationSize = 1, name = "KH_VON_TT_DY_DTOAN_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAM")
    private Integer nam;

    @Column(name = "SO_THONG_TRI")
    private String soThongTri;

    @Column(name = "NGAY_LAP")
    private LocalDate ngayLap;

    @Column(name = "LY_DO_CHI")
    private String lyDoChi;

    @Column(name = "SO_DN_CAP_VON")
    private Long soDnCapVon;

    @Column(name = "LOAI")
    private String loai;

    @Column(name = "KHOAN")
    private String khoan;

    @Column(name = "CHUONG")
    private String chuong;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Transient
    private List<VonTtDuyetYDuToanCt> chiTietList;

    @Transient
    private List<FileDinhKemChung> fileDinhKems;
}
