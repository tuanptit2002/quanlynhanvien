package com.example.QuanLyNhanVien.Repository;

import com.example.QuanLyNhanVien.Entity.TableTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TableTimeRepository extends JpaRepository<TableTime, Long> {


    @Query(value = "select * from table_time tt where tt.ma_nv = :maNv and tt.date_keep = :date_keep", nativeQuery = true)
    TableTime getTableTimeCheckOut(@Param("maNv") String maNv, @Param("date_keep")Date date_keep);

    @Query(value = "select * from table_time tt where tt.ma_nv = :maNv and month(date_keep) = :month and year(date_keep) = :year", nativeQuery = true)
    Page<TableTime> getTableTime(Pageable pageable, String maNv, int month, int year);
}
