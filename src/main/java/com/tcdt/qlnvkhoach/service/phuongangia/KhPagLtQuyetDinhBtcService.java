package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagLtQuyetDinhBtc;
import com.tcdt.qlnvkhoach.enums.KhPagLtQuyetDinhBtcEnum;
import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagKetQuaRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhPagLtQuyetDinhBtcRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagLtQuyetDinhBtcReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagLtQuyetDinhBtcSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhPagLtQuyetDinhBtcService extends BaseService {
  @Autowired
  private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;
  @Autowired
  private FileDinhKemService fileDinhKemService;
  @Autowired
  private ModelMapper mapper;
  @Autowired
  private KhLtPagKetQuaRepository khLtPagKetQuaRepository;
  @Autowired
  KhPagLtQuyetDinhBtcRepository khPagLtQuyetDinhBtcRepository;


  public Page<KhPagLtQuyetDinhBtc> searchPage(KhPagLtQuyetDinhBtcSearchReq objReq) throws Exception {
    Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
        objReq.getPaggingReq().getLimit());
    Page<KhPagLtQuyetDinhBtc> data = khPagLtQuyetDinhBtcRepository.search(
        objReq,
        pageable);
    return data;
  }

  @Transactional(rollbackFor = Exception.class)
  public KhPagLtQuyetDinhBtc create(CustomUserDetails currentUser, KhPagLtQuyetDinhBtcReq req) throws Exception {
    if (currentUser == null) throw new Exception("Bad request.");
    KhPagLtQuyetDinhBtc newRow = new KhPagLtQuyetDinhBtc();
    BeanUtils.copyProperties(req, newRow, "id");
    newRow.setTrangThai(KhPagLtQuyetDinhBtcEnum.DU_THAO.getId());
    newRow.setMaDvi(currentUser.getDvql());
    newRow.setCapDvi(currentUser.getUser().getCapDvi());
    return khPagLtQuyetDinhBtcRepository.save(newRow);
  }

  @Transactional(rollbackFor = Exception.class)
  public KhPagLtQuyetDinhBtc update(CustomUserDetails currentUser, KhPagLtQuyetDinhBtcReq req) throws Exception {
    if (req.getId() == null) {
      throw new Exception("Tham số không hợp lệ.");
    }
    KhPagLtQuyetDinhBtc currentRow = khPagLtQuyetDinhBtcRepository.findById(req.getId()).orElse(null);
    if (currentRow == null) {
      throw new Exception("Không tìm thấy dữ liệu.");
    }

    if (!Constants.TONG_CUC.equals(currentUser.getUser().getCapDvi()))
      throw new Exception("Tài khoản không có quyền thực hiện.");


    if (!KhPagLtQuyetDinhBtcEnum.DU_THAO.getId().equals(currentRow.getTrangThai())) {
      throw new Exception("Chỉ được sửa quyết định dự thảo.");
    }
    BeanUtils.copyProperties(req, currentRow, "id", "trangThai");
    khPagLtQuyetDinhBtcRepository.save(currentRow);
    return currentRow;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean deleteMultiple(List<Long> ids) throws Exception {
    if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

    UserInfo userInfo = SecurityContextService.getUser();
    if (userInfo == null) throw new Exception("Bad request.");

    List<KhPagLtQuyetDinhBtc> listData = khPagLtQuyetDinhBtcRepository.findAllById(ids);
    List<KhPagLtQuyetDinhBtc> listDataValid = listData.stream()
        .filter(s -> s.getTrangThai().equals(KhPagLtQuyetDinhBtcEnum.DU_THAO.getId()))
        .collect(Collectors.toList());
    khPagLtQuyetDinhBtcRepository.deleteAll(listDataValid);
    return true;
  }

  public KhPagLtQuyetDinhBtc detail(String id) throws Exception {
    Optional<KhPagLtQuyetDinhBtc> data = khPagLtQuyetDinhBtcRepository.findById(Long.parseLong(id));
    if (!data.isPresent()) {
      throw new Exception("Bản ghi không tồn tại");
    }
    return data.get();

  }

  public void export(KhPagLtQuyetDinhBtcSearchReq objReq, HttpServletResponse response) throws Exception {
    PaggingReq paggingReq = new PaggingReq();
    paggingReq.setPage(0);
    paggingReq.setLimit(Integer.MAX_VALUE);
    objReq.setPaggingReq(paggingReq);
    Page<KhPagLtQuyetDinhBtc> page = this.searchPage(objReq);
    List<KhPagLtQuyetDinhBtc> data = page.getContent();

    String title = "Danh sách Quyết định giá mua tối đa, giá bán tối thiểu của BTC";
    String[] rowsName = new String[]{"STT", "Số quyết định", "Ngày ký", "Trích yếu", "Năm kế hoạch", "Loại giá", "Loại hàng hóa", "Trạng thái"};
    String fileName = "danh-sach-qd-gia-mua-toi-da-ban-toi-thieu-cua-btc.xlsx";
    List<Object[]> dataList = new ArrayList<Object[]>();
    Object[] objs = null;
    for (int i = 0; i < data.size(); i++) {
      KhPagLtQuyetDinhBtc dx = data.get(i);
      objs = new Object[rowsName.length];
      objs[0] = i;
      objs[1] = dx.getSoQd();
      objs[2] = dx.getNgayKy();
      objs[3] = dx.getTrichYeu();
      objs[4] = dx.getNamKeHoach();
      objs[6] = dx.getLoaiGia();
      objs[5] = dx.getLoaiVthh();
      objs[7] = KhPagLtQuyetDinhBtcEnum.getLabelById(dx.getTrangThai());
      dataList.add(objs);
    }
    ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
    ex.export();
  }
}