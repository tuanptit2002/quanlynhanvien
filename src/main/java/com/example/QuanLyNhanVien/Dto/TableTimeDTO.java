package com.example.QuanLyNhanVien.Dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class TableTimeDTO {

    private Long id;

    private Date dateKeep;
    private Time timeIn;
    private Time timeOut;
    private Long timeLate;
    private Long timeEarly;

    private UserDTO userDTO;
}
