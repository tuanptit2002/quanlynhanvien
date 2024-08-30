package com.example.QuanLyNhanVien.Dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data

public class UserDTO {

    private String maNV;
    private String username;
    private String password;
    private String fullName;
    private Date birthDate;
    private String address;
    private Date dateStart;

    private List<RoleDTO> rolesDtos;
}
