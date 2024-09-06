package com.example.QuanLyNhanVien.Controller;

import com.example.QuanLyNhanVien.Dto.ResponseDto;
import com.example.QuanLyNhanVien.Entity.TableTime;
import com.example.QuanLyNhanVien.Service.TableTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/table-time")
public class TableTimeController {

    @Autowired
    TableTimeService tableTimeService;

    @PostMapping("/check-in/{maNv}")
    public ResponseDto<?> checkIn(@PathVariable String maNv) {
        return tableTimeService.CheckIn(maNv);
    }

    @PostMapping("/check-out/{maNv}")
    public ResponseDto<?> checkOut(@PathVariable String maNv) {
        return tableTimeService.checkOut(maNv);
    }

    @GetMapping("/get/table-time/{maNv}")
    public ResponseDto<Page<TableTime>> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam int month,
                                    @RequestParam int year,
                                    @PathVariable String maNv) {
        Pageable pageable = PageRequest.of(page, size);
        return tableTimeService.getTableTimes(pageable, maNv, month, year);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> delete(@PathVariable Long id) {
        return tableTimeService.deleteTableTime(id);
    }
}
