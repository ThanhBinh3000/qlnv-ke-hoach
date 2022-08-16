package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagTongHopCTiet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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
    private String loaiHangHoa;
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

    private List<KhLtPagTongHopCTiet> pagChitiets;
}
