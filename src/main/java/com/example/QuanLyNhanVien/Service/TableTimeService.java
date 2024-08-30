package com.example.QuanLyNhanVien.Service;

import com.example.QuanLyNhanVien.Entity.TableTime;
import com.example.QuanLyNhanVien.Entity.User;
import com.example.QuanLyNhanVien.Repository.TableTimeRepository;
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
public class TableTimeService {

    @Autowired
    TableTimeRepository tableTimeRepository;

    @Autowired
    UserService userService;

    public String CheckIn(String maNv) {

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

        if (tableTimeNew != null) return "Check in access";
        return "Check in access";
    }

    public String checkOut(String maNv) {
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

        if (tableTimeNew != null) return "Check out access";
        return "Check out access";
    }

    public Page<TableTime> getTableTimes(Pageable pageable, String maNv) {

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

            Page<TableTime> tableTimes = tableTimeRepository.getTableTime(pageable, maNv);
            return tableTimes;
        } else throw new RuntimeException("Bạn không có quyền truy cập thông tin người dùng này!");
    }


    public String deleteTableTime(Long id){

        TableTime tableTime = tableTimeRepository.findById(id).get();
        tableTimeRepository.delete(tableTime);
        return "Xoa thanh cong";
    }
}
