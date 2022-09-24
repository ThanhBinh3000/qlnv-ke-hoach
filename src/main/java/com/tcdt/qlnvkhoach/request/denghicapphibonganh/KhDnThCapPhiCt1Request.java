package com.tcdt.qlnvkhoach.request.denghicapphibonganh;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KhDnThCapPhiCt1Request {
    private Integer stt;
    private String loai;
    private String maBoNganh;
    private List<KhDnThCapPhiCt2Request> ct2s = new ArrayList<>();
}
