package com.example.QuanLyNhanVien.Repository;

import com.example.QuanLyNhanVien.Entity.Role;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Id> {
}
