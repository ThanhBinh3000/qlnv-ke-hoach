package com.tcdt.qlnvkhoach.request.khcnbq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuCanCu;
import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuTienDo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CnCtrinhNcuuReq {

    private Long id;

    private String maDt;

    private String tenDt;

    private String capDt;

    private String tenCapDt;

    private Long namTu;

    private Long namDen;

    private String chuNhiemDt;

    private String chucVu;

    private String email;

    private String soDt;

    private String noiDung;

    private BigDecimal tongChiPhi;

    private String trangThai;

    private LocalDate ngayNghiemThu;

    private String diaDiem;

    private String thanhVienHd;

    private Long tvienHdDvi;

    private String yKienDg;

    private BigDecimal tongDiem;

    private String loai;

    private List<CnCtrinhNcuuCanCuReq> cnCtrinhNcuuCanCuuReqs;

    private List<CnCtrinhNcuuTienDoReq> cnCtrinhNcuuTienDoReqs;
}
