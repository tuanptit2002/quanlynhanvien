package com.example.QuanLyNhanVien.Service.Implement;

import com.example.QuanLyNhanVien.Dto.ResponseDto;
import com.example.QuanLyNhanVien.Entity.User;
import com.example.QuanLyNhanVien.Repository.UserRepository;
import com.example.QuanLyNhanVien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseDto<?> addUser(User user){
            User useOld = userRepository.findByUsername(user.getUsername());
            if(useOld != null){
                return new ResponseDto<>("Tai khoan da ton tai","500");
            }
            else {
                User userNew = userRepository.save(user);
                return new ResponseDto<>("Tao thanh cong","200");
            }

    }

    public User getByMaNv(String maNv){
        User user = userRepository.findByMaNV(maNv);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = (UserDetails) userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
