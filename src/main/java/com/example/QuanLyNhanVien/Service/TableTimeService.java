package com.example.QuanLyNhanVien.Service;

import com.example.QuanLyNhanVien.Dto.ResponseDto;
import com.example.QuanLyNhanVien.Entity.TableTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TableTimeService {

    public ResponseDto<?> CheckIn(String maNv);

    public ResponseDto<?> checkOut(String maNv);

    public ResponseDto<Page<TableTime>> getTableTimes(Pageable pageable, String maNv, int month, int year);

    public ResponseDto<?> deleteTableTime(Long id);
}
