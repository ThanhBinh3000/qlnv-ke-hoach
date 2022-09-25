package com.tcdt.qlnvkhoach.request.denghicapphibonganh;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KhDnThCapPhiCt1Request {
    private Integer stt;
    private String maBoNganh;
    private String maVatTuCha;
    private String maVatTu;
    private String tenHangHoa;
    private String soTaiKhoan;
    private String nganHang;
    private String tenDvCungCap;
    private List<KhDnThCapPhiCt2Request> ct2s = new ArrayList<>();
}
