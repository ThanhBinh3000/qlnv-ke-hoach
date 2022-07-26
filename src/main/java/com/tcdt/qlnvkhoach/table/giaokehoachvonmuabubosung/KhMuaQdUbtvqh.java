package com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KH_MUA_QD_UBTVQH")
@Data
public class KhMuaQdUbtvqh implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_UBTVQH_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_UBTVQH_SEQ",allocationSize = 1,name = "KH_MUA_QD_UBTVQH_SEQ")
    private Long id;
    Integer namQd;
    String soQd;
    @Temporal(TemporalType.DATE)
    Date ngayQd;
    String trichYeu;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    String trangThai;
    String nguoiPduyet;
    Date ngayPduyet;

    @Transient
    private List<KhMuaQdUbtvqhBnganh> listBoNganh;
}
