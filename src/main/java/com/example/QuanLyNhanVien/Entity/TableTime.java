package com.example.QuanLyNhanVien.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
public class TableTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateKeep;
    private Time timeIn;
    private Time timeOut;
    private Long timeLate;
    private Long timeEarly;


    private String maNv;
}
