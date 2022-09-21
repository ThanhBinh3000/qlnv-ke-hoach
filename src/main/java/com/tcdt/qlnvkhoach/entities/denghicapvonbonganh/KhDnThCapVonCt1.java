package com.tcdt.qlnvkhoach.entities.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KH_DN_TH_CAP_VON_CT1")
@EqualsAndHashCode(callSuper = false)
public class KhDnThCapVonCt1 extends TrangThaiBaseEntity implements Serializable {

    private static final long serialVersionUID = 6532917948914821538L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_TH_CAP_VON_CT1_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_TH_CAP_VON_CT1_SEQ", allocationSize = 1, name = "KH_DN_TH_CAP_VON_CT1_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "KH_DN_TH_ID")
    private Long khDnThId;

    @Column(name = "KH_DN_CAP_VON_ID")
    private Long khDnCapVonId;

    @Column(name = "TC_CAP_THEM")
    private BigDecimal tcCapThem;
}
