package com.tcdt.qlnvkhoach.table.btcgiaocacbonganh;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KH_QD_BTC_BO_NGANH")
@Data
public class KhQdBtcBoNganh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_BTC_BO_NGANH_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_BTC_BO_NGANH_SEQ",allocationSize = 1,name = "KH_QD_BTC_BO_NGANH_SEQ")
    private Long id;
    String soQd;
    @Temporal(TemporalType.DATE)
    Date ngayQd;
    Long idTtcpBoNganh;
    Integer namQd;
    String trichYeu;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    String trangThai;
    @Transient
    String tenTrangThai;
    String nguoiPduyet;
    Date ngayPduyet;



    @Transient
    List<KhQdBtcBoNganhCtiet> muaTangList;

    @Transient
    List<KhQdBtcBoNganhCtiet> xuatGiamList;

    @Transient
    List<KhQdBtcBoNganhCtiet> xuatBanList;

    @Transient
    List<KhQdBtcBoNganhCtiet> luanPhienList;

    @Transient
    private List<FileDinhKemChung> fileDinhkems =new ArrayList<>();


}
