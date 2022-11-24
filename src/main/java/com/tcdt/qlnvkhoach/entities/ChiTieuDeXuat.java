package com.tcdt.qlnvkhoach.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = ChiTieuDeXuat.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTieuDeXuat implements Serializable {
    private static final long serialVersionUID = 8195707546894058286L;
    public static final String TABLE_NAME = "KH_CHI_TIEU_DE_XUAT";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHI_TIEU_DE_XUAT_SEQ")
    @SequenceGenerator(sequenceName = "CHI_TIEU_DE_XUAT_SEQ", allocationSize = 1, name = "CHI_TIEU_DE_XUAT_SEQ")
    private Long id;

    private Long chiTieuId; //ChiTieuKeHoachNam

    /**
     * Tong cuc tao dieu chinh -> dx của cục
     * Cục tạo điều chỉnh -> dx của cục
     *
     */
    private Long dxDcKhnId; // DxDcKeHoachNam
}
