package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class KhLtPagTongHopReq {

    private Long id;
    private String soTT;
    private LocalDate ngayTongHop;
    private String noiDung;
    private Long namTongHop;
    private String loaiVthh;
    private String cloaiVthh;
    private String loaiGia;
    private String trangThai;
    private BigDecimal giaKsTtTu;
    private BigDecimal giaKsTtDen;
    private BigDecimal giaKsTtVatTu;
    private BigDecimal giaKsTtVatDen;
    private BigDecimal giaTdTu;
    private BigDecimal giaTdDen;

    private BigDecimal giaTdVatTu;

    private BigDecimal giaTdVatDen;

    private BigDecimal giaDnTu;

    private BigDecimal giaDnDen;

    private BigDecimal giaDnVatTu;

    private BigDecimal giaDnVatDen;

    private String ghiChu;

    private List<KhPagTongHopCTiet> pagChitiets;

    private String trichYeu;

    /**
     * thông tin tờ trình
     */
    private String maToTrinh;

    private BigDecimal ttGiaDn;

    private BigDecimal ttGiaDnVat;

}
