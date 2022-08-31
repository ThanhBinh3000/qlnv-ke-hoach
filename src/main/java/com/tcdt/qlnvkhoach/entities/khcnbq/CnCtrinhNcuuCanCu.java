package com.tcdt.qlnvkhoach.entities.khcnbq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = CnCtrinhNcuuCanCu.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CnCtrinhNcuuCanCu extends BaseEntity implements Serializable {

    public static final String TABLE_NAME = "CN_CONGTRINH_NGHIENCUU_CANCU";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="NOI_DUNG")
    private String noiDung;

    @Column(name="CN_CT_NC_ID")
    private Long cnCtNcId;
}
