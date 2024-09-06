package com.example.QuanLyNhanVien.Service.Implement;

import com.example.QuanLyNhanVien.Dto.ResponseDto;
import com.example.QuanLyNhanVien.Entity.TableTime;
import com.example.QuanLyNhanVien.Entity.User;
import com.example.QuanLyNhanVien.Repository.TableTimeRepository;
import com.example.QuanLyNhanVien.Service.TableTimeService;
import com.example.QuanLyNhanVien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Service
public class TableTimeServiceImpl implements TableTimeService {

    @Autowired
    TableTimeRepository tableTimeRepository;

    @Autowired
    UserService userService;

    /***
     *
     * @param maNv
     * @return
     */
    public ResponseDto<?> CheckIn(String maNv) {

        TableTime tableTime = new TableTime();
        tableTime.setMaNv(maNv);
        LocalDate today = LocalDate.now();
        tableTime.setDateKeep(Date.valueOf(today));
        LocalTime localTime = LocalTime.now();
        tableTime.setTimeIn(Time.valueOf(localTime));
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime arrivalTime = LocalTime.now();
        if (arrivalTime.isAfter(startTime)) {
            long minutesLate = Duration.between(startTime, arrivalTime).toMinutes();
            tableTime.setTimeLate(minutesLate);
        } else {
            tableTime.setTimeLate(0L);
        }
        TableTime tableTimeNew = tableTimeRepository.save(tableTime);

        return new ResponseDto<>("Check in access", "200");
    }

    public ResponseDto<?> checkOut(String maNv) {
        try {
            LocalDate today = LocalDate.now();
            TableTime tableTime = tableTimeRepository.getTableTimeCheckOut(maNv, Date.valueOf(today));
            LocalTime localTime = LocalTime.now();
            tableTime.setTimeOut(Time.valueOf(localTime));
            LocalTime endTime = LocalTime.of(18, 0);
            LocalTime leaveTime = LocalTime.now();
            if (leaveTime.isBefore(endTime)) {
                long minutesLate = Duration.between(leaveTime, endTime).toMinutes();
                tableTime.setTimeEarly(minutesLate);
            } else {
                tableTime.setTimeEarly(0L);
            }
            TableTime tableTimeNew = tableTimeRepository.save(tableTime);

            return new ResponseDto<>("Check out access", "200");
        } catch (Exception e) {
            return new ResponseDto<>("Chưa có dữ liệu ngày hôm nay vui lòng chấm công trước ", "500");

        }
    }

    public ResponseDto<Page<TableTime>> getTableTimes(Pageable pageable, String maNv, int month, int year) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        User user = userService.getByMaNv(maNv);
        boolean isAdmin = false;
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            isAdmin = authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        }
        if (user.getUsername().equals(username) || isAdmin) {

            Page<TableTime> tableTimes = tableTimeRepository.getTableTime(pageable, maNv, month, year);
            return new ResponseDto<>(tableTimes, "Get data access", "200", tableTimes.getTotalPages(), tableTimes.getTotalElements());
        } else {
            return new ResponseDto<>("Unauthorized", "401");
        }
    }


    public ResponseDto<?> deleteTableTime(Long id) {
        TableTime tableTime = tableTimeRepository.findById(id).get();
        tableTimeRepository.delete(tableTime);
        return new ResponseDto<>("Delete access", "200");
    }
}
