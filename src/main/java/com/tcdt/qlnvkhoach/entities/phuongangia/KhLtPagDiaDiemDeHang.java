package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "KH_LT_PAG_DIA_DIEM_DE_HANG")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhLtPagDiaDiemDeHang {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="MA_CHI_CUC")
    private String maChiCuc;

    @Column(name="TEN_CHI_CUC")
    private String tenChiCuc;

    @Column(name="SO_LUONG")
    private BigDecimal soLuong;

    @Column(name="PAG_ID")
    private Long pagId;
}
