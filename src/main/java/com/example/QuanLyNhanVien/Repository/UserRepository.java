package com.example.QuanLyNhanVien.Repository;

import com.example.QuanLyNhanVien.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {


    @Query(value = "select * from user u where u.ma_nv = :manv", nativeQuery = true)
    User findByMaNV(String manv);


    @Query(value = "select * from user u where u.username = :username or u.ma_nv = :username", nativeQuery = true)
    User findByUsername(String username);

//    @Query(value = "select * from user u where u.ma_nv = :manv", nativeQuery = true)
//    User getBy(String manv);
}
