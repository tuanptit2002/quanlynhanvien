package com.example.QuanLyNhanVien.Service;

import com.example.QuanLyNhanVien.Entity.User;
import com.example.QuanLyNhanVien.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public String addUser(User user){
        User userNew = userRepository.save(user);
        if(userNew != null) return "Add user access";
        return "Add user error";
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
