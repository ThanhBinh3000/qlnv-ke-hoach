package com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "KH_MUA_QD_UBTVQH_BNGANH")
@Data
public class KhMuaQdUbtvqhBnganh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_UBTVQH_BN_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_UBTVQH_BN_SEQ",allocationSize = 1,name = "KH_MUA_QD_UBTVQH_BN_SEQ")
    private Long id;
    String maBoNganh;
    Long tongTien;
    Long idMuaQdUbtvqh;
    Long ttMuaBu;
    Long ttMuaBsung;

    @Transient
    String tenBoNganh;

    @Transient
    List<KhMuaQdUbtvqhBnganhCtiet> muaBuList;

    @Transient
    List<KhMuaQdUbtvqhBnganhCtiet> muaBsungList;

    }

