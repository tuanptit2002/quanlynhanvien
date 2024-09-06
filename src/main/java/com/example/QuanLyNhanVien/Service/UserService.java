package com.example.QuanLyNhanVien.Service;

import com.example.QuanLyNhanVien.Dto.ResponseDto;
import com.example.QuanLyNhanVien.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    public ResponseDto<?> addUser(User user);
    public User getByMaNv(String maNv);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
