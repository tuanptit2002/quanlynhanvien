package com.example.QuanLyNhanVien.Controller;

import com.example.QuanLyNhanVien.Config.JwtTokenProvider;
import com.example.QuanLyNhanVien.Dto.AuthenticationResponse;
import com.example.QuanLyNhanVien.Dto.ResponseDto;
import com.example.QuanLyNhanVien.Dto.RoleDTO;
import com.example.QuanLyNhanVien.Dto.UserDTO;
import com.example.QuanLyNhanVien.Entity.Role;
import com.example.QuanLyNhanVien.Entity.User;
import com.example.QuanLyNhanVien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody UserDTO userDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccess_token(jwt);
        authenticationResponse.setEmail(user.getUsername());
        authenticationResponse.setFullName(user.getFullName());
        return authenticationResponse;

    }

    @PostMapping("/add")
    public ResponseDto<?> addUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setMaNv(userDTO.getMaNV());
        user.setAddress(userDTO.getAddress());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setBirthDate(userDTO.getBirthDate());
        user.setDateStart(userDTO.getDateStart());
        List<Role> roles = new ArrayList<>();
        for (RoleDTO roleDTO : userDTO.getRolesDtos()) {
            Role role = new Role();
            role.setId(roleDTO.getId());
            roles.add(role);
        }
        user.setRoles(roles);
        return userService.addUser(user);
    }
}
