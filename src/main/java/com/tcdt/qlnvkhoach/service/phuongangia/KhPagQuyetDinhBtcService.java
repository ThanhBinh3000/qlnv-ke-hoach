package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQuyetDinhBtc;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHop;
import com.tcdt.qlnvkhoach.enums.KhPagQuyetDinhBtcEnum;
import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.repository.phuongangia.*;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagQuyetDinhBtcReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagQuyetDinhBtcSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhPagQuyetDinhBtcService extends BaseService {
  @Autowired
  private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;
  @Autowired
  private FileDinhKemService fileDinhKemService;
  @Autowired
  private ModelMapper mapper;
  @Autowired
  private KhLtPagKetQuaRepository khLtPagKetQuaRepository;
  @Autowired
  KhPagQuyetDinhBtcRepository khPagLtQuyetDinhBtcRepository;
  @Autowired
  KhLtPagTongHopRepository khLtPagTongHopRepository;
  @Autowired
  KhLtPagTongHopCTietRepository khLtPagTongHopCTietRepository;


  public Page<KhPagQuyetDinhBtc> searchPage(KhPagQuyetDinhBtcSearchReq objReq) throws Exception {
    Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
        objReq.getPaggingReq().getLimit());
    Page<KhPagQuyetDinhBtc> data = khPagLtQuyetDinhBtcRepository.search(
        objReq,
        pageable);
    return data;
  }

  @Transactional(rollbackFor = Exception.class)
  public KhPagQuyetDinhBtc create(CustomUserDetails currentUser, KhPagQuyetDinhBtcReq req) throws Exception {
    if (currentUser == null) {
      throw new Exception("Bad request.");
    }
    if (req.getSoToTrinh() == null) {
      throw new Exception("Số tờ trình thiếu hoặc không hợp lệ.");
    }
    if (req.getThongTinGia().size() == 0) {
      throw new Exception("Thông tin giá thiếu hoặc không hợp lệ.");
    }
    KhPagQuyetDinhBtc newRow = new KhPagQuyetDinhBtc();
    BeanUtils.copyProperties(req, newRow, "id");
    newRow.setTrangThai(KhPagQuyetDinhBtcEnum.DU_THAO.getId());
    newRow.setMaDvi(currentUser.getDvql());
    newRow.setCapDvi(currentUser.getUser().getCapDvi());

    //luu thong tin gia
    req.getThongTinGia().forEach(s -> {
      System.out.println(s.getGiaQd());
      s.setQdBtcId(newRow.getId());
    });
    khLtPagTongHopCTietRepository.saveAll(req.getThongTinGia());
    return khPagLtQuyetDinhBtcRepository.save(newRow);
  }

  @Transactional(rollbackFor = Exception.class)
  public KhPagQuyetDinhBtc update(CustomUserDetails currentUser, KhPagQuyetDinhBtcReq req) throws Exception {
    if (req.getId() == null)
      throw new Exception("Tham số không hợp lệ.");
    KhPagQuyetDinhBtc currentRow = khPagLtQuyetDinhBtcRepository.findById(req.getId()).orElse(null);
    if (currentRow == null)
      throw new Exception("Không tìm thấy dữ liệu.");
    if (!Constants.TONG_CUC.equals(currentUser.getUser().getCapDvi()))
      throw new Exception("Tài khoản không có quyền thực hiện.");
    if (!KhPagQuyetDinhBtcEnum.DU_THAO.getId().equals(currentRow.getTrangThai()))
      throw new Exception("Chỉ được sửa quyết định dự thảo.");
    if (req.getSoToTrinh() == null)
      throw new Exception("Số tờ trình thiếu hoặc không hợp lệ.");
    if (req.getThongTinGia().size() == 0)
      throw new Exception("Thông tin giá thiếu hoặc không hợp lệ.");

    BeanUtils.copyProperties(req, currentRow, "id", "trangThai");
    khPagLtQuyetDinhBtcRepository.save(currentRow);

    //luu thong tin gia
    req.getThongTinGia().forEach(s -> {
      s.setQdBtcId(currentRow.getId());
    });
    khLtPagTongHopCTietRepository.saveAll(req.getThongTinGia());
    return currentRow;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean deleteMultiple(CustomUserDetails currentUser,List<Long> ids) throws Exception {
    if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

    List<KhPagQuyetDinhBtc> listData = khPagLtQuyetDinhBtcRepository.findAllById(ids);
    List<KhPagQuyetDinhBtc> listDataValid = listData.stream()
        .filter(s -> s.getTrangThai().equals(KhPagQuyetDinhBtcEnum.DU_THAO.getId()))
        .collect(Collectors.toList());
    khPagLtQuyetDinhBtcRepository.deleteAll(listDataValid);
    return true;
  }

  public KhPagQuyetDinhBtc detail(String id) throws Exception {
    Optional<KhPagQuyetDinhBtc> data = khPagLtQuyetDinhBtcRepository.findById(Long.parseLong(id));
    if (!data.isPresent()) {
      throw new Exception("Bản ghi không tồn tại");
    }
    return data.get();

  }

  public void export(KhPagQuyetDinhBtcSearchReq objReq, HttpServletResponse response) throws Exception {
    PaggingReq paggingReq = new PaggingReq();
    paggingReq.setPage(0);
    paggingReq.setLimit(Integer.MAX_VALUE);
    objReq.setPaggingReq(paggingReq);
    Page<KhPagQuyetDinhBtc> page = this.searchPage(objReq);
    List<KhPagQuyetDinhBtc> data = page.getContent();

    String title = "Danh sách Quyết định giá mua tối đa, giá bán tối thiểu của BTC";
    String[] rowsName = new String[]{"STT", "Số quyết định", "Ngày ký", "Trích yếu", "Năm kế hoạch", "Loại giá", "Loại hàng hóa", "Trạng thái"};
    String fileName = "danh-sach-qd-gia-mua-toi-da-ban-toi-thieu-cua-btc.xlsx";
    List<Object[]> dataList = new ArrayList<Object[]>();
    Object[] objs = null;
    for (int i = 0; i < data.size(); i++) {
      KhPagQuyetDinhBtc dx = data.get(i);
      objs = new Object[rowsName.length];
      objs[0] = i;
      objs[1] = dx.getSoQd();
      objs[2] = dx.getNgayKy();
      objs[3] = dx.getTrichYeu();
      objs[4] = dx.getNamKeHoach();
      objs[6] = dx.getLoaiGia();
      objs[5] = dx.getLoaiVthh();
      objs[7] = KhPagQuyetDinhBtcEnum.getLabelById(dx.getTrangThai());
      dataList.add(objs);
    }
    ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
    ex.export();
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatus(CustomUserDetails currentUser,StatusReq req) throws Exception {
    Optional<KhPagQuyetDinhBtc> currentRow = khPagLtQuyetDinhBtcRepository.findById(req.getId());
    if (!currentRow.isPresent())
      throw new Exception("Không tìm thấy dữ liệu.");
    if (req.getTrangThai().equals(KhPagQuyetDinhBtcEnum.BAN_HANH.getId())) {
      Optional<KhPagTongHop> toTrinh = khLtPagTongHopRepository.findById(currentRow.get().getSoToTrinh());
      if (toTrinh.isPresent()) {
        toTrinh.get().setTtToTrinh(KhPagQuyetDinhBtcEnum.DABANHANH_QD.getId());
        khLtPagTongHopRepository.save(toTrinh.get());
      }
      currentRow.get().setTrangThai(KhPagQuyetDinhBtcEnum.BAN_HANH.getId());
      khPagLtQuyetDinhBtcRepository.save(currentRow.get());
      return true;
    }
    return false;
  }
}