package com.tcdt.qlnvkhoach.entities.vontonghoptheodoi;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = VonTongHopTheoDoi.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VonTongHopTheoDoi extends TrangThaiBaseEntity implements Serializable {
    // Cấp vốn thông tri duyệt y dự toán
    public static final String TABLE_NAME = "KH_VON_TH_TDOI";
    private static final long serialVersionUID = -4074438189820672619L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_VON_TH_TDOI_SEQ")
    @SequenceGenerator(sequenceName = "KH_VON_TH_TDOI_SEQ", allocationSize = 1, name = "KH_VON_TH_TDOI_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "SO_THONG_TRI")
    private String soThongTri;

    @Column(name = "MA_DVI_DUOC_DUYET")
    private String maDviDuocDuyet;

    @Column(name = "SO_LENH_CHI_TIEN")
    private Long soLenhChiTien;

    @Column(name = "CHUONG")
    private String chuong;

    @Column(name = "LOAI")
    private String loai;

    @Column(name = "KHOAN")
    private String khoan;

    @Column(name = "LY_DO_CHI")
    private String lyDoChi;

    @Column(name = "SO_TIEN")
    private BigDecimal soTien;

    @Column(name = "MA_DVI_THU_HUONG")
    private String maDviThuHuong;

    @Column(name = "TAI_KHOAN")
    private String taiKhoan;

    @Column(name = "NGAN_HANG")
    private String nganHang;

    @Column(name = "CAN_CU")
    private String canCu;

    @Column(name = "DOT_TTOAN")
    private String dotTToan;

    @Transient
    private List<FileDinhKemChung> fileDinhKems;
}
